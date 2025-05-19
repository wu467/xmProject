package com.xiaomi.practice.api.base.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 根据车架id 查询电池信息
 */
@Data
public class QueryBatteryMsgRequest {

    /**
     * 车架id
     */
    @NotNull(message = "查询电池信息时，车架编号不能为空")
    private Long carId;

    /**
     * 当前页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页显示的数据量，默认10
     */
    private Integer pageSize = 10;

}
