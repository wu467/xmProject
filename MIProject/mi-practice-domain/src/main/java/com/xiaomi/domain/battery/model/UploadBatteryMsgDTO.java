package com.xiaomi.domain.battery.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadBatteryMsgDTO {

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
}
