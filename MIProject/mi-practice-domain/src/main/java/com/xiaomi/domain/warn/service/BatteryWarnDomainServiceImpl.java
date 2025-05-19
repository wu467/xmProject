package com.xiaomi.domain.warn.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.domain.warn.service.entity.BatterySignalDTO;
import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;
import com.xiaomi.domain.warn.service.entity.WarnResult;
import com.xiaomi.domain.warn.service.strategyAndFactory.RuleStrategyFactory;
import com.xiaomi.domain.warn.service.strategyAndFactory.SignalCalculationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 电池告警服务实现类
 * 负责解析电池信号数据，并根据预警规则匹配对应的告警等级和规则名称
 * 使用策略模式解耦信号计算逻辑，工厂模式管理策略实例
 */
@Slf4j
@Service
public class BatteryWarnDomainServiceImpl implements BatteryWarnDomainService {


    // Jackson JSON解析器，用于反序列化信号字符串
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 解析电池信号列表，匹配预警规则
     * @param signalList 电池信号列表（包含车辆ID、电池类型、信号数据等）
     * @param allRules 所有预警规则列表（从数据库查询获取）
     * @return 匹配到的告警结果列表
     */
    @Override
    public List<WarnResult> parseSignals(List<BatterySignalDTO> signalList, List<BatteryWarnRule> allRules) {
        log.info("开始解析电池信号列表, signalList{}, allRules{}", signalList.size(), allRules.size());
        // 1. 构建规则索引：按电池类型和规则ID分组，便于快速查询
        Map<String, Map<Integer, List<BatteryWarnRule>>> ruleIndex = indexRules(allRules);

        List<WarnResult> results = new ArrayList<>();

        for (BatterySignalDTO signalDTO : signalList) {
            try {
                log.debug("准备解析信号: carId={}, signal={}", signalDTO.getCarId(), signalDTO.getBatterySignal());

                // 2. 反序列化信号JSON字符串为Map（键值对形式）
                Map<String, Object> signalMap = mapper.readValue(signalDTO.getBatterySignal(), new TypeReference<Map<String, Object>>() {});

                log.debug("信号解析成功: carId={}, 解析后内容={}", signalDTO.getCarId(), signalMap);

                // 3. 获取适用的计算策略（根据warnId或信号字段自动推断）
                List<SignalCalculationStrategy> strategies = getStrategies(signalDTO, signalMap);

                // 4. 对每个策略执行计算和规则匹配
                for (SignalCalculationStrategy strategy : strategies) {
                    processStrategy(signalDTO, signalMap, strategy, ruleIndex, results);
                }
            } catch (IOException e) {
                log.warn("解析信号JSON失败，信号数据：{}", signalDTO.getBatterySignal(), e);
            }
        }

        return results;
    }



    /**
     * 构建规则索引
     * 将规则按 [电池类型 -> 规则ID -> 规则列表] 结构索引，提升查询效率
     * @param allRules 所有预警规则
     * @return 规则索引映射表
     */
    private Map<String, Map<Integer, List<BatteryWarnRule>>> indexRules(List<BatteryWarnRule> allRules) {
        Map<String, Map<Integer, List<BatteryWarnRule>>> index = new HashMap<>();

        // 遍历所有规则，按电池类型和规则ID分组
        for (BatteryWarnRule rule : allRules) {
            index.computeIfAbsent(rule.getBatteryType(), k -> new HashMap<>()) // 获取或创建电池类型分组
                    .computeIfAbsent(rule.getWarnRuleId(), k -> new ArrayList<>()) // 获取或创建规则ID分组
                    .add(rule); // 将规则添加到对应分组
        }

        return index;
    }

    /**
     * 获取适用的信号计算策略
     * @param signalDTO 当前处理的信号对象
     * @param signalMap 解析后的信号键值对
     * @return 适用的计算策略列表
     */
    private List<SignalCalculationStrategy> getStrategies(BatterySignalDTO signalDTO, Map<String, Object> signalMap) {
        List<SignalCalculationStrategy> strategies = new ArrayList<>();

        if (signalDTO.getWarnRuleId() != null) {
            // 情况1：信号包含warnId，直接根据ID获取策略
            SignalCalculationStrategy strategy = RuleStrategyFactory.getStrategy(signalDTO.getWarnRuleId());
            if (strategy != null && strategy.canHandle(signalMap)) {
                strategies.add(strategy); // 校验信号字段是否支持该策略
            }
        } else {
            // 情况2：信号不包含warnId，自动检测支持的策略（如同时存在Mx/Mi和Ix/Ii则匹配多个策略）
            strategies.addAll(RuleStrategyFactory.getApplicableStrategies(signalMap));
        }

        return strategies;
    }

    /**
     * 处理单个计算策略的匹配逻辑
     * @param signalDTO 当前信号对象
     * @param signalMap 解析后的信号键值对
     * @param strategy 当前计算策略（如电压差、电流差计算）
     * @param ruleIndex 规则索引映射表
     * @param results 结果集（用于添加匹配到的告警结果）
     */
    private void processStrategy(BatterySignalDTO signalDTO, Map<String, Object> signalMap,
                                 SignalCalculationStrategy strategy,
                                 Map<String, Map<Integer, List<BatteryWarnRule>>> ruleIndex,
                                 List<WarnResult> results) {
        // 1. 根据电池类型和策略对应的规则ID获取规则列表
        Map<Integer, List<BatteryWarnRule>> typeRules = ruleIndex.getOrDefault(
                signalDTO.getBatteryType(), Collections.emptyMap() // 不存在该电池类型的规则时返回空Map
        );
        List<BatteryWarnRule> rules = typeRules.getOrDefault(
                strategy.getWarnRuleId(), Collections.emptyList() // 不存在该规则ID的规则时返回空列表
        );

        // 2. 使用策略计算信号值（如电压差、电流差）
        BigDecimal signalValue = strategy.calculate(signalMap);

        // 3. 遍历规则列表，匹配第一个符合条件的规则
        for (BatteryWarnRule rule : rules) {
            if (isValueInRange(signalValue, rule.getWarnMinValue(), rule.getWarnMaxValue())) {
                // 创建结果对象并添加到结果集
                WarnResult warnResult = new WarnResult();
                warnResult.setCarId(signalDTO.getCarId());
                warnResult.setWarnLevel(rule.getWarnLevel());
                warnResult.setWarnName(rule.getWarnName());
                warnResult.setBatteryType(rule.getBatteryType());
                results.add(warnResult);

                break; // 找到匹配规则后不再继续匹配
            }
        }
    }

    /**
     * 判断数值是否在指定范围内（支持null值，表示不限制边界）
     * 范围为 [min, max)，即大于等于min，小于max
     * @param value 待检查的数值
     * @param min 最小值（null表示无下限）
     * @param max 最大值（null表示无上限）
     * @return 是否在范围内
     */
    private boolean isValueInRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        boolean aboveMin = min == null || value.compareTo(min) >= 0; // 大于等于最小值（min为null时自动通过）
        boolean belowMax = max == null || value.compareTo(max) < 0; // 小于最大值（max为null时自动通过）
        return aboveMin && belowMax; // 必须同时满足上下限条件
    }
}