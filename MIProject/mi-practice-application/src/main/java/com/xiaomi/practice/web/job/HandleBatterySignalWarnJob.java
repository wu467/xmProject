package com.xiaomi.practice.web.job;

import com.xiaomi.common.base.dto.BatterySignalMsgDTO;
import com.xiaomi.domain.battery.repository.BatteryMsgRepository;
import com.xiaomi.domain.battery.service.BatteryMsgDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class HandleBatterySignalWarnJob {

    @Autowired
    private BatteryMsgRepository batteryMsgRepository;

    @Autowired
    private BatteryMsgDomainService batteryMsgDomainService;

    @Scheduled(cron = "0/60 * * * * ?")
    public void handleBatterySignalWarn() {
        log.info("开始处理电池信号记录");
        // 读取电池信号表，获取前一天的数据
        List<BatterySignalMsgDTO> yesterBatteryMsgList = batteryMsgRepository.getYesterBatteryMsgList(LocalDate.now());
        // 将数据推送到mq
        batteryMsgDomainService.sendBatterySignalToMq(yesterBatteryMsgList);
    }




}
