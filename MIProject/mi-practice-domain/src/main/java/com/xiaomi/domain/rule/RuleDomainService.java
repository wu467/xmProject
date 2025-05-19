package com.xiaomi.domain.rule;

import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;

import java.util.List;

public interface RuleDomainService {

    /**
     * 获取所有电池预警规则
     *
     * @return 所有电池预警规则
     */
    List<BatteryWarnRule> getAllRules();
}
