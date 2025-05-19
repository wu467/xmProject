package com.xiaomi.practice.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi.practice.infrastructure.entity.BatteryMessageEntity;
import com.xiaomi.practice.infrastructure.entity.BatteryWarningRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 预警结果记录 Mapper
 */
@Mapper
public interface WarningRecordMapper extends BaseMapper<BatteryWarningRecordEntity> {


    int batchSave(@Param("list") List<BatteryWarningRecordEntity> entityList);
}
