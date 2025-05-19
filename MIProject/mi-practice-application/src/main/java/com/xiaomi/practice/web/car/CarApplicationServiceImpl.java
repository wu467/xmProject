package com.xiaomi.practice.web.car;


import com.xiaomi.domain.car.model.CarMessage;
import com.xiaomi.domain.car.repository.CarMessageRepository;
import com.xiaomi.domain.car.model.SaveCarMsgRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * application service impl   主要作用是调度不同的 domain 和 domain 的 service 完成业务流程的编排
 *
 */
@Service
@Slf4j
public class CarApplicationServiceImpl implements CarApplicationService {

    @Autowired
    private CarMessageRepository carMessageRepository;


    @Override
    public void saveCarMessage(SaveCarMsgRequestDTO saveCarMsgRequestDTO) {
        // 业务校验
        saveCarMsgRequestDTO.getBatteryType();

        // TODO 待校验电车类型
        CarMessage carMessage = CarMessage.create(saveCarMsgRequestDTO);
        carMessageRepository.save(carMessage);
    }
}
