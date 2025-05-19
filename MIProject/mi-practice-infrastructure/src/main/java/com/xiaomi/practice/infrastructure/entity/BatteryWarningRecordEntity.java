package com.xiaomi.practice.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 电池预警信息记录表实体类
 */
@Data
@TableName("battery_warning_records")
public class BatteryWarningRecordEntity {

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 车架编号 */
    @TableField("car_id")
    private Long carId;

    /** 电池类型 */
    @TableField("battery_type")
    private String batteryType;

    /** 预警规则名称 */
    @TableField("warn_name")
    private String warnName;

    /** 报警等级(0 - 4) */
    @TableField("warn_level")
    private Integer warnLevel;

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