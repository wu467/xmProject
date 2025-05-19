package com.xiaomi.domain.warn.consumer;

import com.alibaba.fastjson.JSON;

import com.xiaomi.common.base.exception.BusinessException;
import com.xiaomi.domain.car.repository.CarMessageRepository;
import com.xiaomi.domain.rule.RuleDomainService;
import com.xiaomi.domain.warn.repository.WarningRecordRepository;
import com.xiaomi.domain.warn.service.BatteryWarnDomainServiceImpl;
import com.xiaomi.domain.warn.service.entity.BatterySignalDTO;
import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;
import com.xiaomi.domain.warn.service.entity.WarnResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "MyConsumerGroup", topic = "TestTopic",consumeMode= ConsumeMode.CONCURRENTLY,messageModel= MessageModel.BROADCASTING)
public class BatteryMsgConsumer implements RocketMQListener<String> {

    // 阻塞队列存储dto
    private final BlockingQueue<BatterySignalDTO> dtoQueue = new LinkedBlockingQueue<>();

    // 批量处理数量
    private static final int BATCH_SIZE = 2;

    // 预警规则列表
    List<BatteryWarnRule> allRules;

    @Autowired
    private CarMessageRepository carMessageRepository;

    @Autowired
    private BatteryWarnDomainServiceImpl batteryWarnDomainService;

    @Autowired
    private RuleDomainService ruleDomainService;

    @Autowired
    private WarningRecordRepository warningRecordRepository;


    /**
     * 消费者实例化后，启动后台线程，处理队列中的dto
     */
    public BatteryMsgConsumer() {
        new Thread(this::batchProcessLoop).start();
    }


    /**
     * 接收消息
     */
    @Override
    public void onMessage(String message) {
        try {
            BatterySignalDTO dto = JSON.parseObject(message, BatterySignalDTO.class);
            // 将消息放入队列中，后台异步线程批量处理,避免阻塞消费线程
            dtoQueue.put(dto);
        } catch (Exception e) {
            log.error("消息消费失败: {}", message, e);
        }
    }


    /**
     * 后台线程循环批量处理队列中的dto，
     * 批量处理是为了减少数据库访问次数，提高性能
     */
    private void batchProcessLoop() {
        while (true) {
            try {
                List<BatterySignalDTO> dtoList = new ArrayList<>(BATCH_SIZE);
                // 从队列中取出BATCH_SIZE个dto
                for (int i = 0; i < BATCH_SIZE; i++) {
                    BatterySignalDTO dto = dtoQueue.take();

                    // 根据carId获取电池类型
                    String batteryType = getBatteryTypeByCarId(dto.getCarId());
                    dto.setBatteryType(batteryType);
                    dtoList.add(dto);
                }
                batchConsumerBatteryMessage(dtoList);
            } catch (InterruptedException e) {
                log.error("批量处理线程被中断", e);
                // 恢复中断状态
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error("批量处理失败", e);
            }
        }
    }


    /**
     * 批量处理dto列表
     * @param dtoList 电池信号消息DTO列表
     */
    private void batchConsumerBatteryMessage(List<BatterySignalDTO> dtoList) {
        log.info("开始批量消费数据");
        allRules = Optional.ofNullable(allRules).orElseGet(ruleDomainService::getAllRules);

        if (CollectionUtils.isEmpty(allRules)) {
            throw new BusinessException(501, "未查询到电池预警规则");
        }

        // 批量解析电池信号，获得告警结果,并批量保存到数据库中
        List<WarnResult> warnResultList = batteryWarnDomainService.parseSignals(dtoList, allRules);
        warningRecordRepository.batchSaveWarnResult(warnResultList);

        //打印结果
        warnResultList.forEach(warnResult -> {
            log.info("告警结果: carId={}, warnName={}, BatteryType={},warnLevel={}, ", warnResult.getCarId(), warnResult.getWarnName(), warnResult.getBatteryType(), warnResult.getWarnLevel());
        });

        log.info("批量数据处理完成，处理数量{}", warnResultList.size());
    }



    /**
     * 根据车架id获取电池类型
     *
     * @param carId 车架id
     * @return 电池类型 （枚举）
     */
    public String getBatteryTypeByCarId(Long carId){
        return carMessageRepository.getBatteryTypeByCarId(carId);
    }
}