package com.xiaomi.practice.infrastructure.convert;

import com.xiaomi.domain.car.model.CarMessage;
import com.xiaomi.practice.infrastructure.entity.CarMessageEntity;

public class CarMessageConvert {

    public CarMessageEntity convertToEntity(CarMessage carMessage) {
        CarMessageEntity carMessageEntity = new CarMessageEntity();
        carMessageEntity.setVid(carMessage.getVid());
        carMessageEntity.setFrameNumber(carMessage.getFrameNumber());
        carMessageEntity.setBatteryType(carMessage.getBatteryType().getType());
        carMessageEntity.setTotalMileage(carMessage.getTotalMileage());
        carMessageEntity.setBatteryHealthPercent(carMessage.getBatteryHealthPercent());
       return carMessageEntity;
    }

}
