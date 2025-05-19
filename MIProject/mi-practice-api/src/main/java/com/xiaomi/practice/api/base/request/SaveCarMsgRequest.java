package com.xiaomi.practice.api.base.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 保存车辆信息请求参数
 */
@Getter
@Setter
public class SaveCarMsgRequest {

    /**
     * 车辆识别码, 随机生成
     */
    @NotNull(message = "车辆识别码(vid)不能为空")
    private Long vid;

    /**
     * 车架编号
     */
    private String frameNumber;

    /**
     * 电池类型
     */
    @NotBlank(message = "电池类型不能为空")
    private String batteryType;

    /**
     * 总里程
     */
    private String totalMileage;

    /**
     * 电池健康度百分比（%）
     */
    @NotNull(message = "电池健康度百分比不能为空")
    @Max(value = 100, message = "电池健康度百分比不能大于100")
    @Min(value = 0, message = "电池健康度百分比不能小于0")
    private Integer batteryHealthPercent;
}
