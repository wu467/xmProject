package com.xiaomi.practice.api.base.response;

import com.xiaomi.common.base.dto.BatteryMsgDTO;
import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data
@Builder
public class BatteryMsgResponse {

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页显示的数据量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;


    /**
     * 车架编号
     */
    private Long carId;

    /**
     * 电池信号列表
     */
    private List<BatteryMsgDTO.SignalRecord> batterySignalList;

}
