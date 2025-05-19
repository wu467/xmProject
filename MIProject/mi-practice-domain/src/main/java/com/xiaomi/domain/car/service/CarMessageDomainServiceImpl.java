package com.xiaomi.domain.car.service;

import com.xiaomi.domain.car.model.CarMessage;
import com.xiaomi.domain.car.repository.CarMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 领域服务实现类
 */
@Service
public class CarMessageDomainServiceImpl implements CarMessageDomainService {

    @Autowired
    private CarMessageRepository carMessageRepository;

    @Override
    public void saveCarMessage(CarMessage carMessage) {
        // CarMessage 转换为 Entity、

        carMessageRepository.save(carMessage);
    }


    /**
     * 验证车辆是否存在
     * @param carId 车架id
     */
    @Override
    public void validateCarExist(Long carId) {
        carMessageRepository.validateCarExist(carId);
    }


}
