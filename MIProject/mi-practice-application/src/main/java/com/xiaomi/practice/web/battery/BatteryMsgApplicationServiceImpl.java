package com.xiaomi.practice.web.battery;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.domain.battery.model.BatteryMessage;
import com.xiaomi.domain.battery.model.QueryBatteryMsgDTO;
import com.xiaomi.domain.battery.model.UploadBatteryMsgDTO;
import com.xiaomi.domain.battery.repository.BatteryMsgRepository;
import com.xiaomi.domain.battery.service.BatteryMsgDomainService;
import com.xiaomi.domain.car.service.CarMessageDomainService;
import com.xiaomi.practice.infrastructure.entity.BatteryMessageEntity;
import com.xiaomi.practice.web.dto.BatteryMsgResponseDTO;
import com.xiaomi.practice.web.dto.QueryBatteryMsgRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * application serviceImpl 重点在于编排业务逻辑
 */
@Slf4j
@Service
public class BatteryMsgApplicationServiceImpl implements BatteryMsgApplicationService {

    @Autowired
    private BatteryMsgRepository batteryMsgRepository;

    @Autowired
    private BatteryMsgDomainService batteryMsgDomainService;

    @Autowired
    private CarMessageDomainService carMessageDomainService;

    /**
     * 上传电池信息
     */
    @Override
    public void uploadBatteryMessage(UploadBatteryMsgDTO uploadBatteryMsgDTO) {

        // 领域职责，创建领域对象
        BatteryMessage batteryMessage = BatteryMessage.create(uploadBatteryMsgDTO);

        // 业务校验
        // 1.校验汽车是否存在
        carMessageDomainService.validateCarExist(batteryMessage.getCarId());
        // 2.校验预警规则是否存在
        if (Objects.nonNull(batteryMessage.getWarnId())) {
            // TODO 如果上传了规则，则校验规则是否存在
        }

        // 保存电池信息
        batteryMsgDomainService.save(batteryMessage);
    }


    /**
     * 查询车辆电池信息
     */
    @Override
    public BatteryMsgDTO queryBatteryMsgByCarId(QueryBatteryMsgRequestDTO requestDTO) {
        // 校验车辆是否存在
        carMessageDomainService.validateCarExist(requestDTO.getCarId());

        // dto 转换
        QueryBatteryMsgDTO queryBatteryMsgDTO = QueryBatteryMsgDTO.builder()
                .carId(requestDTO.getCarId())
                .pageNum(requestDTO.getPageNum())
                .pageSize(requestDTO.getPageSize())
                .build();

        // 查询车辆电池信息
        return batteryMsgDomainService.queryBatteryMsgListByCarId(queryBatteryMsgDTO);
    }

    /**
     * 删除电池信息
     * @param id 信息id
     * @param carId 车辆id
     */
    @Override
    public void deleteBatteryMsg(Long id, Long carId) {
        // 校验车辆是否存在
        carMessageDomainService.validateCarExist(carId);
        // 删除电池信息
        batteryMsgDomainService.deleteBatteryMsgById(id, carId);
    }
}
