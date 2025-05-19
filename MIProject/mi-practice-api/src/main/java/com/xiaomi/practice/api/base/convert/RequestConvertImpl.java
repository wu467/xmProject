package com.xiaomi.practice.api.base.convert;

import com.xiaomi.domain.battery.model.UploadBatteryMsgDTO;
import com.xiaomi.domain.car.model.SaveCarMsgRequestDTO;
import com.xiaomi.practice.api.base.request.QueryBatteryMsgRequest;
import com.xiaomi.practice.api.base.request.SaveCarMsgRequest;
import com.xiaomi.practice.api.base.request.UploadBatteryMsgRequest;
import com.xiaomi.practice.web.dto.QueryBatteryMsgRequestDTO;


public class RequestConvertImpl implements RequestConvert {

    /**
     *  汽车信息保存request 转化
     */
    @Override
    public SaveCarMsgRequestDTO convertRequestToDTO(SaveCarMsgRequest saveCarMsgRequest) {
        return null;
    }

    /**
     *  电池信息上传request 转化 dto
     */
    @Override
    public UploadBatteryMsgDTO convertUploadBatteryMsgRequest(UploadBatteryMsgRequest batteryMsgRequest) {
        return UploadBatteryMsgDTO.builder()
                .carId(batteryMsgRequest.getCarId())
                .warnId(batteryMsgRequest.getWarnId())
                .batterySignal(batteryMsgRequest.getBatterySignal())
                .build();
    }

    @Override
    public QueryBatteryMsgRequestDTO convert(QueryBatteryMsgRequest queryBatteryMsgRequest) {
        return QueryBatteryMsgRequestDTO.builder()
                .carId(queryBatteryMsgRequest.getCarId())
                .pageNum(queryBatteryMsgRequest.getPageNum())
                .pageSize(queryBatteryMsgRequest.getPageSize())
                .build();
    }
}
