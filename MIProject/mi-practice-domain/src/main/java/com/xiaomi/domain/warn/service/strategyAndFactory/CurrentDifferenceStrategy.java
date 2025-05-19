package com.xiaomi.domain.warn.service.strategyAndFactory;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 电流差值计算策略
 */
public class CurrentDifferenceStrategy implements SignalCalculationStrategy {
    @Override
    public BigDecimal calculate(Map<String, Object> signalMap) {
        BigDecimal ix = new BigDecimal(signalMap.get("Ix").toString());
        BigDecimal ii = new BigDecimal(signalMap.get("Ii").toString());
        return ix.subtract(ii);
    }

    @Override
    public boolean canHandle(Map<String, Object> signalMap) {
        return signalMap.containsKey("Ix") && signalMap.containsKey("Ii");
    }

    @Override
    public Integer getWarnRuleId() {
        return 2;
    }
}