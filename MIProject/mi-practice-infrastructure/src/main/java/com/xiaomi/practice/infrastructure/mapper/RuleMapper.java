package com.xiaomi.practice.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi.practice.infrastructure.entity.BatteryMessageEntity;
import com.xiaomi.practice.infrastructure.entity.BatteryWarnRuleEntity;
import org.mapstruct.Mapper;

@Mapper
public interface RuleMapper extends BaseMapper<BatteryWarnRuleEntity> {


}
