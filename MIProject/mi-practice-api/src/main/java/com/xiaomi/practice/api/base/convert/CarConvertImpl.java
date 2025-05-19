package com.xiaomi.practice.api.base.convert;

import com.xiaomi.practice.api.base.request.SaveCarMsgRequest;
import com.xiaomi.domain.car.model.SaveCarMsgRequestDTO;

public class CarConvertImpl implements CarConvert {

    /**
     * 车辆信息保存请求转DTO
     *
     * @param saveCarMsgRequest
     * @return
     */
    @Override
    public SaveCarMsgRequestDTO convertRequestToDTO(SaveCarMsgRequest saveCarMsgRequest) {
        return SaveCarMsgRequestDTO.builder()
                .vid(saveCarMsgRequest.getVid())
                .frameNumber(saveCarMsgRequest.getFrameNumber())
                .batteryType(saveCarMsgRequest.getBatteryType())
                .totalMileage(saveCarMsgRequest.getTotalMileage())
                .batteryHealthPercent(saveCarMsgRequest.getBatteryHealthPercent())
                .build();
    }
}
