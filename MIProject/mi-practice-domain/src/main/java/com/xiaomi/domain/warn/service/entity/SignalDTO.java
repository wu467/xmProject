package com.xiaomi.domain.warn.service.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 电池信号 反序列化
 */
@Data
public class SignalDTO {

    private BigDecimal Mx;

    private BigDecimal Mi;

    private BigDecimal Ix;

    private BigDecimal Ii;


}
