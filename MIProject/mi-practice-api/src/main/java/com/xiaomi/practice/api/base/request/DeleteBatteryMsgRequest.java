package com.xiaomi.practice.api.base.request;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class DeleteBatteryMsgRequest {

    /**
     * 电池信息记录 id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 车架 id
     */
    @NotNull(message = "carId不能为空")
    Long carId;
}
