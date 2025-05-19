package com.xiaomi.practice.infrastructure.warn;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaomi.domain.warn.repository.WarningRecordRepository;
import com.xiaomi.domain.warn.service.entity.WarnResult;
import com.xiaomi.practice.infrastructure.entity.BatteryWarningRecordEntity;
import com.xiaomi.practice.infrastructure.mapper.WarningRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class WarningRecordRepositoryImpl implements WarningRecordRepository {

    @Autowired
    private WarningRecordMapper warningRecordMapper;


    @Override
    public void batchSaveWarnResult(List<WarnResult> warnResultList) {
        // 将 WarnResult 转换为 entity
        List<BatteryWarningRecordEntity> entityList = warnResultList.stream().map(warnResult -> {
            BatteryWarningRecordEntity entity = new BatteryWarningRecordEntity();
            entity.setCarId(warnResult.getCarId());
            entity.setBatteryType(warnResult.getBatteryType());
            entity.setWarnName(warnResult.getWarnName());
            entity.setWarnLevel(warnResult.getWarnLevel());
            return entity;
        }).collect(Collectors.toList());
        int saveSize = warningRecordMapper.batchSave(entityList);
        log.info("保存{}条预警信息数据成功", saveSize);
    }


    @Override
    public List<WarnResult> queryWarnRecords(Long carId) {
        LambdaQueryWrapper<BatteryWarningRecordEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BatteryWarningRecordEntity::getCarId, carId);
        List<BatteryWarningRecordEntity> entityList = warningRecordMapper.selectList(queryWrapper);

        // entity 转 dto
        return entityList.stream().parallel()
                .map(this::convertRecordEntityToDTO).collect(Collectors.toList());
    }

    private WarnResult convertRecordEntityToDTO(BatteryWarningRecordEntity entity) {
        WarnResult warnResult = new WarnResult();
        warnResult.setCarId(entity.getCarId());
        warnResult.setWarnName(entity.getWarnName());
        warnResult.setWarnLevel(entity.getWarnLevel());
        warnResult.setBatteryType(entity.getBatteryType());
        return warnResult;
    }
}
