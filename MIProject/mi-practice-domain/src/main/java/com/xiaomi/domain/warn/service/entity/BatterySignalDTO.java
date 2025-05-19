package com.xiaomi.domain.warn.service.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 电池信息DTO
@Data
public class BatterySignalDTO {

    private Long carId;

    private Integer warnRuleId;

    private String batteryType;

    private String batterySignal;
}
