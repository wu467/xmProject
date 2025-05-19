package com.xiaomi.domain.car.model.valueObj;

import com.xiaomi.common.base.CarBatteryEnum;
import lombok.Getter;

/**
 * 电池类型 - 值对象
 */
@Getter
public class BatteryType {

    /**
     * 电池类型
     */
    private String type;


    private BatteryType(String type) {
        this.type = type;
    }


    public static BatteryType getBatteryType(String batteryType) {
        // 根据batteryType获取对应的电池类型
        String type = CarBatteryEnum.getDesByCode(batteryType);
        return new BatteryType(type);
    }
}
