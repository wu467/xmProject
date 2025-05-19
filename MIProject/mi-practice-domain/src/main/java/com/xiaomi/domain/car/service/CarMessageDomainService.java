package com.xiaomi.domain.car.service;

import com.xiaomi.domain.car.model.CarMessage;

public interface CarMessageDomainService {

    void saveCarMessage(CarMessage carMessage);

    void validateCarExist(Long carId);
}
