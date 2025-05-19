package com.xiaomi.domain.warn.service.strategyAndFactory;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则策略工厂类
 */
public class RuleStrategyFactory {

    /**
     * 计算规则 map， key 为规则 id， value 为规则对应的策略
     */
    private static final Map<Integer, SignalCalculationStrategy> STRATEGY_MAP = new HashMap<>();

    /**
     * 所有的计算策略 list
     */
    private static final List<SignalCalculationStrategy> ALL_STRATEGIES = new ArrayList<>();

    static {
        // 初始化计算策略
        registerStrategy(new VoltageDifferenceStrategy());
        registerStrategy(new CurrentDifferenceStrategy());
    }

    /**
     * 注册策略
     * @param strategy 预警规则策略注册
     */
    private static void registerStrategy(SignalCalculationStrategy strategy) {
        STRATEGY_MAP.put(strategy.getWarnRuleId(), strategy);
        ALL_STRATEGIES.add(strategy);
    }

    /**
     * 根据规则ID获取策略
     * @param warnRuleId 预警规则ID
     * @return 策略
     */
    public static SignalCalculationStrategy getStrategy(Integer warnRuleId) {
        return STRATEGY_MAP.get(warnRuleId);
    }

    /**
     * 获取所有适用策略
     */
    public static List<SignalCalculationStrategy> getApplicableStrategies(Map<String, Object> signalMap) {
        List<SignalCalculationStrategy> strategies = new ArrayList<>();
        for (SignalCalculationStrategy strategy : ALL_STRATEGIES) {
            if (strategy.canHandle(signalMap)) {
                strategies.add(strategy);
            }
        }
        return strategies;
    }
}