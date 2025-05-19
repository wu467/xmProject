package com.xiaomi.practice.infrastructure.convert;

import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.domain.battery.model.BatteryMessage;
import com.xiaomi.practice.infrastructure.entity.BatteryMessageEntity;

public class BatteryMessageConvert {

    /**
     * 将领域实体转换为实体对象
     *
     * @param batteryMessage
     * @return
     */
    public BatteryMessageEntity convertToEntity(BatteryMessage batteryMessage) {
        BatteryMessageEntity batteryMessageEntity = new BatteryMessageEntity();
        batteryMessageEntity.setCarId(batteryMessage.getCarId());
        batteryMessageEntity.setWarnRuleId(batteryMessage.getWarnId());
        batteryMessageEntity.setBatterySignal(batteryMessage.getBatterySignal());
        return batteryMessageEntity;
    }

    /**
     *
     *
     * @param batteryMessageEntity 实体表
     * @return
     */
    public static BatteryMsgDTO.SignalRecord convertToBatteryMessage(BatteryMessageEntity batteryMessageEntity) {
        BatteryMsgDTO.SignalRecord signalRecord = new BatteryMsgDTO.SignalRecord();
        signalRecord.setId(batteryMessageEntity.getId());
        signalRecord.setWarnId(batteryMessageEntity.getWarnRuleId());
        signalRecord.setBatterySignal(batteryMessageEntity.getBatterySignal());
        signalRecord.setRecordTime(batteryMessageEntity.getUpdateTime());


        return signalRecord;
    }
}
