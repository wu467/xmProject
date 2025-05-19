package com.xiaomi.common.base.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 电池信号查询dto
 */
@Data
@NoArgsConstructor
public class BatterySignalMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 车架ID
     */
    private Long carId;

    /**
     * 告警规则ID
     */
    private Integer warnRuleId;

    /**
     * 电池信号
     */
    private String batterySignal;

    /**
     * 电池类型
     */
    private String batteryType;
}
