package com.xiaomi.domain.warn.service.entity;

import lombok.Data;

/**
 * 电池预警结果
 */
@Data
public class WarnResult {

    private Long carId;

    private Integer warnLevel;

    private String warnName;

    private String batteryType;

}