package com.xiaomi.practice.infrastructure.car;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaomi.common.base.exception.BusinessException;
import com.xiaomi.domain.car.model.CarMessage;
import com.xiaomi.domain.car.repository.CarMessageRepository;
import com.xiaomi.practice.infrastructure.mapper.CarMessageMapper;
import com.xiaomi.practice.infrastructure.convert.CarMessageConvert;
import com.xiaomi.practice.infrastructure.entity.CarMessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
@Slf4j
public class CarMessageRepositoryImpl implements CarMessageRepository {

    @Autowired
    private CarMessageMapper carMessageMapper;


    /**
     * 保存车辆信息
     * @param carMessage 待保存的车辆信息
     */
    @Override
    public void save(CarMessage carMessage) {
        CarMessageConvert convert = new CarMessageConvert();
        CarMessageEntity carMessageEntity = convert.convertToEntity(carMessage);

        // 判断车辆信息是否存在
        LambdaQueryWrapper<CarMessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarMessageEntity::getVid, carMessageEntity.getVid());
        if (carMessageMapper.exists(queryWrapper)) {
            log.info("车辆信息已存在, {}", carMessage);
            return;
        }
        // 保存车辆信息
        int rows = carMessageMapper.insert(carMessageEntity);
        if (rows < 0) {
            throw new BusinessException(451, "汽车信息保存失败");
        }
        log.info("汽车信息保存成功, {}", carMessage);
    }

    /**
     * 查询车辆信息是否存在
     *
     * @param carId 车架id
     */
    @Override
    public void validateCarExist(Long carId) {
        LambdaQueryWrapper<CarMessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarMessageEntity::getFrameNumber, carId);

        if (carMessageMapper.exists(queryWrapper)) {
            return;
        }
        log.warn("车辆信息不存在, 车架id为{}", carId);
        throw new BusinessException(467, "车辆信息不存在");
    }


    /**
     * 获取电池类型
     * @param carId 车架id
     * @return batteryType
     */
    @Override
    public String getBatteryTypeByCarId(Long carId) {
        LambdaQueryWrapper<CarMessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarMessageEntity::getFrameNumber, carId);
        queryWrapper.select(CarMessageEntity::getBatteryType);
        CarMessageEntity carMessageEntity = carMessageMapper.selectOne(queryWrapper);
        return carMessageEntity.getBatteryType();
    }
}
