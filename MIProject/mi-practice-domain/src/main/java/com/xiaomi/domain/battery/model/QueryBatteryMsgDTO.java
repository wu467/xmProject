package com.xiaomi.domain.battery.model;

import lombok.Builder;
import lombok.Data;


/**
 * 查询电池信息DTO
 */
@Data
@Builder
public class QueryBatteryMsgDTO {

    /**
     * 车架id
     */
    private Long carId;

    /**
     * 当前页码，默认1
     */
    private Integer pageNum;

    /**
     * 每页显示的数据量，默认10
     */
    private Integer pageSize;

}