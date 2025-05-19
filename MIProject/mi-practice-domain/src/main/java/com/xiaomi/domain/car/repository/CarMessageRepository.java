package com.xiaomi.domain.car.repository;

import com.xiaomi.domain.car.model.CarMessage;

public interface CarMessageRepository{

    void save(CarMessage carMessage);

    void validateCarExist(Long carId);

    /**
     * 获取电池类型
     * @param carId 车架id
     * @return batteryType
     */
    String getBatteryTypeByCarId(Long carId);
}
