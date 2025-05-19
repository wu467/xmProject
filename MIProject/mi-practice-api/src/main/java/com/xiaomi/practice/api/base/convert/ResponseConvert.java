package com.xiaomi.practice.api.base.convert;

import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.practice.api.base.response.BatteryMsgResponse;

public interface ResponseConvert {


    BatteryMsgResponse convertTOResponseList(BatteryMsgDTO batteryMsgDTO);

}
