package com.xiaomi.practice.web.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class BatteryMsgResponseDTO {

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页显示的数据量
     */
    private Integer pageSize;


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
        private LocalDateTime recordTime;
    }

}