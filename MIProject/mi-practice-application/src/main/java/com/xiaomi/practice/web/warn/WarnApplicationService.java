package com.xiaomi.practice.web.warn;


import com.xiaomi.domain.warn.service.entity.WarnResult;

import java.util.List;

public interface WarnApplicationService {

    /**
     * 查询告警记录
     * @param carId 车架id
     */
    List<WarnResult> queryWarnRecords(Long carId);
}
