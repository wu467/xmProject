package com.xiaomi.domain.car.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveCarMsgRequestDTO {

    /**
     * 车辆识别码
     */
    private Long vid;

    /**
     * 车架编号
     */
    private String frameNumber;

    /**
     * 电池类型
     */
    private String batteryType;

    /**
     * 总里程
     */
    private String totalMileage;

    /**
     * 电池健康度百分比（%）
     */
    private Integer batteryHealthPercent;
}
