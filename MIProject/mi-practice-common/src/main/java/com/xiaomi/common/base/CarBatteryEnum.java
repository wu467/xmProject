package com.xiaomi.common.base;

import com.xiaomi.common.base.exception.BusinessException;

public enum CarBatteryEnum {
    
    /**
     * 三元电池
     */
    TERNARY(Constant.TERNARY, "三元电池"),

    /**
     * 铁锂电池（LFP）
     */
    LITHIUM_IRON_PHOSPHATE(Constant.LITHIUM_IRON_PHOSPHATE, "铁锂电池");
    

    private final String code;
    private final String desc;
    

    CarBatteryEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    /**
     * 根据code获取枚举值
     *
     * @param code
     * @return
     */
    public static String getDesByCode(String code) {
        for (CarBatteryEnum carBatteryEnum : values()) {
            if (carBatteryEnum.getCode().equals(code)) {
                return carBatteryEnum.getDesc();
            }
        }
        throw new BusinessException(402, "入参电池类型不存在");
    }
}