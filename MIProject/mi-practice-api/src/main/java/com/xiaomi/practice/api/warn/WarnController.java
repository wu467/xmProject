package com.xiaomi.practice.api.warn;


import com.xiaomi.common.base.Response;
import com.xiaomi.domain.warn.service.entity.WarnResult;
import com.xiaomi.practice.api.base.request.QueryWarnRecordRequest;
import com.xiaomi.practice.api.base.response.WarnResultResponse;
import com.xiaomi.practice.web.warn.WarnApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WarnController {

    @Autowired
    private WarnApplicationService warnApplicationService;


    /**
     * 获取车辆告警记录
     */
    @PostMapping("/getWarnRecords")
    public Response<List<WarnResultResponse>> handleBatterySignalWarn(@RequestBody QueryWarnRecordRequest queryWarnRecordRequest) {
        List<WarnResult> warnResultList = warnApplicationService.queryWarnRecords(queryWarnRecordRequest.getCarId());

        // 转换为response
        List<WarnResultResponse> warnResultResponseList = warnResultList.stream().parallel()
                .map(this::convertToWarnResultResponse).collect(Collectors.toList());
        return Response.success(warnResultResponseList);
    }









    private WarnResultResponse convertToWarnResultResponse(WarnResult warnResult) {
        WarnResultResponse warnResultResponse = new WarnResultResponse();
        warnResultResponse.setCarId(warnResult.getCarId());
        warnResultResponse.setWarnLevel(warnResult.getWarnLevel());
        warnResultResponse.setWarnName(warnResult.getWarnName());
        warnResultResponse.setBatteryType(warnResult.getBatteryType());
        return warnResultResponse;
    }
}
