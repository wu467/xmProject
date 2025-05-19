package com.xiaomi.domain.warn.service.entity;

import lombok.Data;

import java.math.BigDecimal;

// 电池预警规则实体类
@Data
public class BatteryWarnRule {

    private Long id;

    private Integer warnRuleId;

    private String warnName;

    private BigDecimal warnMaxValue;

    private BigDecimal warnMinValue;

    private Integer warnLevel;

    private String batteryType;

    public BatteryWarnRule() {

    }

    public BatteryWarnRule(Integer warnRuleId, String warnName, BigDecimal warnMaxValue, BigDecimal warnMinValue, Integer warnLevel, String batteryType) {
        this.warnRuleId = warnRuleId;
        this.warnName = warnName;
        this.warnMaxValue = warnMaxValue;
        this.warnMinValue = warnMinValue;
        this.warnLevel = warnLevel;
        this.batteryType = batteryType;
    }


}
