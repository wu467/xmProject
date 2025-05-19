package com.xiaomi.practice.infrastructure.rule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaomi.domain.rule.RuleRepository;
import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;
import com.xiaomi.practice.infrastructure.entity.BatteryWarnRuleEntity;
import com.xiaomi.practice.infrastructure.mapper.RuleMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class RuleRepositoryImpl implements RuleRepository {

    @Autowired
    private RuleMapper ruleMapper;


    @Override
    public List<BatteryWarnRule> getAllRules() {
        QueryWrapper<BatteryWarnRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("warn_rule_id", "battery_type", "warn_max_value", "warn_min_value", "warn_name", "warn_level");
        List<BatteryWarnRuleEntity> batteryWarnRuleEntities = ruleMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(batteryWarnRuleEntities)) {
            return Collections.emptyList();
        }

        return batteryWarnRuleEntities.stream().
                map( entity -> new BatteryWarnRule(entity.getWarnRuleId(), entity.getWarnName(), entity.getWarnMaxValue(), entity.getWarnMinValue(), entity.getWarnLevel(), entity.getBatteryType()))
                .collect(Collectors.toList());
    }

}
