package com.xiaomi.domain.rule;

import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleDomainServiceImpl implements RuleDomainService {

    @Autowired
    private RuleRepository ruleRepository;

    /**
     * 获取所有电池预警规则
     *
     * @return 所有电池预警规则
     */
    @Override
    public List<BatteryWarnRule> getAllRules() {
        return ruleRepository.getAllRules();
    }

}
