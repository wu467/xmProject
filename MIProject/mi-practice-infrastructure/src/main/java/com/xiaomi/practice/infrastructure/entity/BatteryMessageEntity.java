package com.xiaomi.practice.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.domain.battery.model.BatteryMessage;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 电池信息记录表实体
 */
@Data
@TableName("battery_signal_records")
public class BatteryMessageEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车架编号
     */
    @TableField("carId")
    private Long carId;

    /**
     * 预警规则ID
     */
    @TableField("warn_rule_id")
    private Integer warnRuleId;

    /**
     * 电池信号json字符串
     */
    @TableField("battery_signal")
    private String batterySignal;

    /**
     * 是否删除(0-未删除,1-已删除)
     */
    @TableLogic
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 记录创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}