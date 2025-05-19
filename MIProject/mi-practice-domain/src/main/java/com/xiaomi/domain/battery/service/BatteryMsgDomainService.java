package com.xiaomi.domain.battery.service;

import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.common.base.dto.BatterySignalMsgDTO;
import com.xiaomi.domain.battery.model.BatteryMessage;
import com.xiaomi.domain.battery.model.QueryBatteryMsgDTO;

import java.util.List;


public interface BatteryMsgDomainService {

    /**
     * 根据车辆id查询电池信息
     */
    BatteryMsgDTO queryBatteryMsgListByCarId(QueryBatteryMsgDTO queryBatteryMsgDTO);

    /**
     * 根据车辆id删除电池信息
     */
    void deleteBatteryMsgById(Long id ,Long carId);

    /**
     * 推送数据到mq
     * @param messageList  昨天的电池数据
     */
    void sendBatterySignalToMq(List<BatterySignalMsgDTO> messageList);

    /**
     * 保存电池信息
     */
    void save(BatteryMessage batteryMessage);
}
