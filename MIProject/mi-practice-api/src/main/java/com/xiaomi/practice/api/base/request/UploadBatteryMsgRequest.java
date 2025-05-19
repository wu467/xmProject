package com.xiaomi.practice.api.base.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UploadBatteryMsgRequest {

    /**
     *  车架编号
     */
    @NotNull(message = "车架编号不能为空")
    private Long carId;

    /**
     * 预警规则编号
     */
    private Integer warnId;

    /**
     * 电池信号
     */
    @NotBlank(message = "电池信号不能为空")
    private String batterySignal;
}
