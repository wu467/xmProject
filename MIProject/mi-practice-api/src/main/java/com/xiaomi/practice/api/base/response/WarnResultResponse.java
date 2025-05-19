package com.xiaomi.practice.api.base.response;

import lombok.Data;

/**
 * 电池预警结果Response
 */
@Data
public class WarnResultResponse {

    private Long carId;

    private Integer warnLevel;

    private String warnName;

    private String batteryType;

}