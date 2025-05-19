package com.xiaomi.domain.battery.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatteryMessage {

    /**
     *  车架编号
     */
    private Long carId;

    /**
     * 预警规则编号
     */
    private Integer warnId;

    /**
     * 电池信号
     */
    private String batterySignal;


    /**
     * 创建领域对象
     */
    public static BatteryMessage create(UploadBatteryMsgDTO uploadBatteryMsgDTO) {

        return BatteryMessage.builder()
                .carId(uploadBatteryMsgDTO.getCarId())
                .warnId(uploadBatteryMsgDTO.getWarnId())
                .batterySignal(uploadBatteryMsgDTO.getBatterySignal())
                .build();
    }


}
