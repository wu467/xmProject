package com.xiaomi.domain.battery.service;

import com.alibaba.fastjson.JSON;
import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.common.base.dto.BatterySignalMsgDTO;
import com.xiaomi.common.base.utils.RedisKeyUtil;
import com.xiaomi.common.base.utils.RedisTemplateUtil;
import com.xiaomi.domain.battery.model.BatteryMessage;
import com.xiaomi.domain.battery.model.QueryBatteryMsgDTO;
import com.xiaomi.domain.battery.repository.BatteryMsgRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 电池信息领域服务
 */
@Service
@Slf4j
public class BatteryMsgDomainServiceImpl implements BatteryMsgDomainService {

    @Autowired
    private BatteryMsgRepository batteryMsgRepository;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 查询车辆所有电池上报信息
     */
    @Override
    public BatteryMsgDTO queryBatteryMsgListByCarId(QueryBatteryMsgDTO queryBatteryMsgDTO) {
        // 1.查缓存
        Long carId = queryBatteryMsgDTO.getCarId();
        Integer pageNum = queryBatteryMsgDTO.getPageNum();
        Integer pageSize = queryBatteryMsgDTO.getPageSize();
        // 1.1 获取缓存key，根据carId、pageNum、pageSize生成
        String batteryMsgPageKey = RedisKeyUtil.getBatteryMsgPageKey(carId, pageNum, pageSize);
        log.info("查询缓存{}", batteryMsgPageKey);
        String batteryMsgPageCache = redisTemplateUtil.get(batteryMsgPageKey);
        if (Objects.nonNull(batteryMsgPageCache)) {
            log.info("缓存命中， carId{}， pageNum{}， pageSize{}", carId, pageNum, pageSize);

            return JSON.parseObject(batteryMsgPageCache, BatteryMsgDTO.class);
        }

        // 2.缓存没有从数据库查询
        String lockKey = RedisKeyUtil.getBatteryMsgLockKey(carId);
        String lockValue = UUID.randomUUID().toString();
        try {
            // 尝试获取锁，失败则等待一秒，尝试三次
            boolean isLock = redisTemplateUtil.tryLock(lockKey, lockValue, 2, TimeUnit.SECONDS, 3, 1);
            if (isLock) {
                BatteryMsgDTO batteryMsgDTOList = batteryMsgRepository.queryBatteryMsgListByCarId(queryBatteryMsgDTO);
                log.info("从数据库查询， carId{}， pageNum{}， pageSize{}", carId, pageNum, pageSize);

                // 序列化后存入Redis
                String cacheValue = JSON.toJSONString(batteryMsgDTOList);
                redisTemplateUtil.set(batteryMsgPageKey, cacheValue, 60, TimeUnit.SECONDS);
                return batteryMsgDTOList;
            }
        } catch (Exception e) {
            log.error("获取锁失败， carId{}， pageNum{}， pageSize{}", carId, pageNum, pageSize);
        } finally {
            // 释放锁时校验锁值
            if (lockValue.equals(redisTemplateUtil.get(lockKey))) {
                redisTemplateUtil.delete(lockKey);
            }
            log.info("释放锁成功");
        }

        return new BatteryMsgDTO();
    }


    /**
     * 根据 记录 id 删除消息
     * @param id 记录 id
     */
    @Override
    public void deleteBatteryMsgById(Long id, Long carId) {
        String lockKey = RedisKeyUtil.getBatteryMsgLockKey(carId);
        String lockValue = UUID.randomUUID().toString();

        // 尝试获取操作电池信息的锁,尝试三次，每次暂停一秒
        boolean isLocked = redisTemplateUtil.tryLock(lockKey, lockValue, 1, TimeUnit.SECONDS, 3,1);
        if (isLocked) {
            try {
                // 1.先删除数据库
                batteryMsgRepository.deleteById(id);
                // 2.删缓存，删除该carId缓存的所有电池状态信息，即前缀为 battery:msg:carId
                String batteryMsgCacheKeyPrefix = RedisKeyUtil.getBatteryMsgPaginatedKeyPrefix(carId);
                redisTemplateUtil.deleteKeysByPrefix(batteryMsgCacheKeyPrefix);
            } catch (Exception e) {
                log.error("删除电池信息失败", e);
            } finally {
                // 3.释放锁时校验锁值
                if (lockValue.equals(redisTemplateUtil.get(lockKey))) {
                    redisTemplateUtil.delete(lockKey);
                }
                log.info("删除信息时，释放锁成功");
            }
        }
    }


    /**
     * 保存电池信息
     * @param batteryMessage 电池信息
     */
    @Override
    public void save(BatteryMessage batteryMessage) {
        String lockKey = RedisKeyUtil.getBatteryMsgLockKey(batteryMessage.getCarId());
        String lockValue = UUID.randomUUID().toString();

        // 尝试获取操作电池信息的锁
        boolean isLocked = redisTemplateUtil.tryLock(lockKey, lockValue, 1, TimeUnit.SECONDS, 3, 1);
        if (isLocked) {
            try {
                // 1. 先保存到数据库
                batteryMsgRepository.save(batteryMessage);

                // 2. 删缓存（与删除逻辑保持一致）
                String batteryMsgCacheKeyPrefix = RedisKeyUtil.getBatteryMsgPaginatedKeyPrefix(batteryMessage.getCarId());
                redisTemplateUtil.deleteKeysByPrefix(batteryMsgCacheKeyPrefix);
                log.info("保存电池信息成功，已清理缓存，carId={}", batteryMessage.getCarId());
            } catch (Exception e) {
                throw new RuntimeException("保存电池信息失败", e);
            } finally {
                // 3. 释放锁时校验锁值
                if (lockValue.equals(redisTemplateUtil.get(lockKey))) {
                    redisTemplateUtil.delete(lockKey);
                }
            }
        } else {
            log.error("获取锁失败，保存电池信息失败，carId={}", batteryMessage.getCarId());
        }
    }



    /**
     * 发送电池信号消息到 RocketMQ
     * @param messageList 消息列表
     */
    @Override
    public void sendBatterySignalToMq(List<BatterySignalMsgDTO> messageList) {
        for (BatterySignalMsgDTO message : messageList) {
            // 序列化消息后发送到 mq
            String jsonMessage = JSON.toJSONString(message);
            rocketMQTemplate.convertAndSend("TestTopic" ,jsonMessage);
        }
        log.info("mq消息发送完毕");
    }
}
