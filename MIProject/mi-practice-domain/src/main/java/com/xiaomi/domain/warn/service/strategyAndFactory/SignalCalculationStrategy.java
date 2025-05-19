package com.xiaomi.domain.warn.service.strategyAndFactory;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 信号计算策略接口
 * @author 吴文棋
 */
public interface SignalCalculationStrategy {

    BigDecimal calculate(Map<String, Object> signalMap);

    boolean canHandle(Map<String, Object> signalMap);

    Integer getWarnRuleId();

}