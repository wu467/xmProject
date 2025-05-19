package com.xiaomi.practice.api.base.request;

import lombok.Data;


/**
 * 查询告警记录请求
 */

@Data
public class QueryWarnRecordRequest {
    private Long carId;
}
