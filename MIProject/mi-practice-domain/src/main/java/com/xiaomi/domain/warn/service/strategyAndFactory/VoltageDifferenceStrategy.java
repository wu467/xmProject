package com.xiaomi.domain.warn.service.strategyAndFactory;

import com.xiaomi.common.base.Constant;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 电压差计算策略
 * @author 吴文棋
 */
public class VoltageDifferenceStrategy implements SignalCalculationStrategy {
    @Override
    public BigDecimal calculate(Map<String, Object> signalMap) {
        BigDecimal mx = new BigDecimal(signalMap.get("Mx").toString());
        BigDecimal mi = new BigDecimal(signalMap.get("Mi").toString());
        return mx.subtract(mi);
    }

    @Override
    public boolean canHandle(Map<String, Object> signalMap) {
        return signalMap.containsKey("Mx") && signalMap.containsKey("Mi");
    }

    @Override
    public Integer getWarnRuleId() {
        return Constant.ONE; // 电压差规则ID
    }
}