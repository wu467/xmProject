package com.xiaomi.practice.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi.practice.infrastructure.entity.BatteryMessageEntity;
import org.mapstruct.Mapper;

@Mapper
public interface BatteryMsgMapper extends BaseMapper<BatteryMessageEntity> {


}
