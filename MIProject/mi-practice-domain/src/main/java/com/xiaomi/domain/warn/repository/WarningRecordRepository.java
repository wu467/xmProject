package com.xiaomi.domain.warn.repository;

import com.xiaomi.domain.warn.service.entity.WarnResult;

import java.util.List;

/**
 * 告警记录仓库
 */
public interface WarningRecordRepository {

    /**
     * 批量保存告警结果
     * @param warnResultList 待保存的告警结果
     */
    void batchSaveWarnResult(List<WarnResult> warnResultList);

    /**
     * 查询车辆指定车辆下的所有告警记录
     * @param carId 车辆ID
     * @return 告警结果列表
     */
    List<WarnResult> queryWarnRecords(Long carId);
}
