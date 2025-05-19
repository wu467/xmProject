package com.xiaomi.practice.api.battery;

import com.xiaomi.common.base.Response;
import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.practice.api.base.convert.RequestConvert;
import com.xiaomi.practice.api.base.convert.RequestConvertImpl;
import com.xiaomi.practice.api.base.convert.ResponseConvert;
import com.xiaomi.practice.api.base.convert.ResponseConvertImpl;
import com.xiaomi.practice.api.base.request.DeleteBatteryMsgRequest;
import com.xiaomi.practice.api.base.request.QueryBatteryMsgRequest;
import com.xiaomi.practice.api.base.request.UploadBatteryMsgRequest;
import com.xiaomi.practice.api.base.response.BatteryMsgResponse;
import com.xiaomi.practice.web.battery.BatteryMsgApplicationService;
import com.xiaomi.practice.web.dto.QueryBatteryMsgRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BatteryMessageController {

    @Autowired
    private BatteryMsgApplicationService batteryMsgApplicationService;


    /**
     * 保存电池信息
     * @param batteryMsgRequest
     * @return
     */
    @PostMapping("/battery/uploadBatteryMessage")
    public Response<Void> uploadBatteryMessage(@Valid @RequestBody UploadBatteryMsgRequest batteryMsgRequest) {

        // 创建转换对象,将request转换为dto
        RequestConvert convert = new RequestConvertImpl();
        batteryMsgApplicationService.uploadBatteryMessage(convert.convertUploadBatteryMsgRequest(batteryMsgRequest));
        return Response.success();
    }

    /**
     * 根据车架id查询电池信息，分页查询
     * @param queryBatteryMsgRequest 查询请求
     */
    @PostMapping("/queryBatteryMessageByCarId")
    public Response<BatteryMsgResponse> queryBatteryMessageByCarId(@Valid @RequestBody QueryBatteryMsgRequest queryBatteryMsgRequest) {
        // 创建转换对象,将request转换为dto
        RequestConvert requestConvert = new RequestConvertImpl();
        QueryBatteryMsgRequestDTO requestDTO = requestConvert.convert(queryBatteryMsgRequest);

        // 查询电池信息
        BatteryMsgDTO batteryMsgDTO = batteryMsgApplicationService.queryBatteryMsgByCarId(requestDTO);

        // 创建转换对象,将dto转换为response
        ResponseConvert responseConvert = new ResponseConvertImpl();
        BatteryMsgResponse response = responseConvert.convertTOResponseList(batteryMsgDTO);

        return Response.success(response);
    }


    /**
     * 根据 gid 和 carId 删除电池信息
     */
    @PostMapping("/deleteBatteryMsgById")
    public Response<Void> deleteBatteryMsgById(@Valid @RequestBody DeleteBatteryMsgRequest request) {
        batteryMsgApplicationService.deleteBatteryMsg(request.getId(), request.getCarId());
        return Response.success();
    }

}
