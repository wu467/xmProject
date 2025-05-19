package com.xiaomi.practice.api.base.convert;

import com.xiaomi.domain.battery.model.UploadBatteryMsgDTO;
import com.xiaomi.domain.car.model.SaveCarMsgRequestDTO;
import com.xiaomi.practice.api.base.request.QueryBatteryMsgRequest;
import com.xiaomi.practice.api.base.request.SaveCarMsgRequest;
import com.xiaomi.practice.api.base.request.UploadBatteryMsgRequest;
import com.xiaomi.practice.web.dto.QueryBatteryMsgRequestDTO;

/**
 * 转换类，将请求参数 request  转换成 DTO
 */
public interface RequestConvert {
    SaveCarMsgRequestDTO convertRequestToDTO(SaveCarMsgRequest saveCarMsgRequest);


    /**
     * 转换上传的电池信号信息
     * @param batteryMsgRequest 请求
     * @return 请求的dto
     */
    UploadBatteryMsgDTO convertUploadBatteryMsgRequest(UploadBatteryMsgRequest batteryMsgRequest);

    /**
     * 转换查询的电池信息 request -> dto
     */
    QueryBatteryMsgRequestDTO convert(QueryBatteryMsgRequest queryBatteryMsgRequest);
}
