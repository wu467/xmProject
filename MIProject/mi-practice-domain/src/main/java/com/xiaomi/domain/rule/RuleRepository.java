package com.xiaomi.domain.rule;

import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;

import java.util.List;

/**
 * 预警规则仓库
 */
public interface RuleRepository {


    List<BatteryWarnRule> getAllRules();

}
