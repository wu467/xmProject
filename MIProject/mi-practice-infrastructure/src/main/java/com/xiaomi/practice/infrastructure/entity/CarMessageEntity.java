package com.xiaomi.practice.infrastructure.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据库表实体类
 */
@TableName("car_message")
@Data
public class CarMessageEntity {

    /**
     * 车辆识别码
     */
    @TableId(value = "vid", type = IdType.INPUT)
    @TableField(value = "vid")
    private Long vid;

    /**
     * 车架编号
     */
    @TableField(value = "frame_number")
    private String frameNumber;

    /**
     * 电池类型
     */
    @TableField(value = "battery_type")
    private String batteryType;

    /**
     * 总里程
     */
    @TableField(value = "total_mileage")
    private String totalMileage;

    /**
     * 电池健康度百分比（%）
     */
    @TableField(value = "battery_health_percent")
    private Integer batteryHealthPercent;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
