package com.xiaomi.practice.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 电池预警规则表实体类
 */
@Data
@TableName("battery_warn_rules")
public class BatteryWarnRuleEntity {

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 预警规则ID */
    @TableField("warn_rule_id")
    private Integer warnRuleId;

    /** 预警规则名称 */
    @TableField("warn_name")
    private String warnName;

    /** 报警最大值(Mx - Mi / Ix - Ii) */
    @TableField("warn_max_value")
    private BigDecimal warnMaxValue;

    /** 报警最小值(Mx - Mi / Ix - Ii) */
    @TableField("warn_min_value")
    private BigDecimal warnMinValue;

    /** 报警等级(0-4) */
    @TableField("warn_level")
    private Integer warnLevel;

    /** 电池类型 */
    @TableField("battery_type")
    private String batteryType;

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