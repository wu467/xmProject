package com.xiaomi.domain.car.model;

import com.xiaomi.domain.car.model.valueObj.BatteryType;
import lombok.*;

/**
 * 车辆信息-聚合根
 */

@Data
@Builder
public class CarMessage {

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
    private BatteryType batteryType;

    /**
     * 总里程
     */
    private String totalMileage;

    /**
     * 电池健康度百分比（%）
     */
    private Integer batteryHealthPercent;



    // 领域行为
    public static CarMessage create(SaveCarMsgRequestDTO saveCarMsgRequestDTO) {

        //TODO 待校验电池类型是否为 enum
        return CarMessage.builder()
                .vid(saveCarMsgRequestDTO.getVid())
                .frameNumber(saveCarMsgRequestDTO.getFrameNumber())
                .batteryType(BatteryType.getBatteryType(saveCarMsgRequestDTO.getBatteryType()))
                .totalMileage(saveCarMsgRequestDTO.getTotalMileage())
                .batteryHealthPercent(saveCarMsgRequestDTO.getBatteryHealthPercent())
                .build();
    }

}
