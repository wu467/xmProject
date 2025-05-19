package com.xiaomi.domain.warn.service;

import com.xiaomi.domain.warn.service.entity.BatterySignalDTO;
import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;
import com.xiaomi.domain.warn.service.entity.WarnResult;

import java.util.List;

/**
 * 电池信号解析服务
 */
public interface BatteryWarnDomainService {

    /**
     * 解析电池信号
     *
     * @param signalList 信号列表
     * @param allRules   所有电池预警规则
     * @return 电池预警结果
     */
    List<WarnResult> parseSignals(List<BatterySignalDTO> signalList, List<BatteryWarnRule> allRules);

}
