package com.xiaomi.practice.api.base.convert;

import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.practice.api.base.response.BatteryMsgResponse;


public class ResponseConvertImpl implements ResponseConvert {

    /**
     * 将BatteryMsgDTO转换成BatteryMsgResponse
     */
    @Override
    public BatteryMsgResponse convertTOResponseList(BatteryMsgDTO batteryMsgDTO) {

        return BatteryMsgResponse.builder()
                .pageNum(batteryMsgDTO.getPageNum())
                .pageSize(batteryMsgDTO.getPageSize())
                .totalPage(batteryMsgDTO.getTotalPage())
                .batterySignalList(batteryMsgDTO.getBatterySignalList())
                .carId(batteryMsgDTO.getCarId())
                .build();
    }


}
