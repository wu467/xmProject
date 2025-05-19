package com.xiaomi.common.base.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BatteryMsgDTO {

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
    private List<SignalRecord> batterySignalList;


    /**
     * 电池信号记录
     */
    @Data
    public static class SignalRecord {
        /**
         * 主键id
         */
        private Long id;

        /**
         * 告警id
         */
        private Integer warnId;

        /**
         * 信号值
         */
        private String batterySignal;

        /**
         * 信号记录时间
         */
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime recordTime;
    }
}