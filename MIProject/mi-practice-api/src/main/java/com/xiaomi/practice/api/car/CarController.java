package com.xiaomi.practice.api.car;

import com.xiaomi.common.base.Response;
import com.xiaomi.practice.api.base.convert.CarConvert;
import com.xiaomi.practice.api.base.convert.CarConvertImpl;
import com.xiaomi.practice.api.base.request.SaveCarMsgRequest;
import com.xiaomi.practice.web.car.CarApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CarController {

    @Autowired
    private CarApplicationService carApplicationService;

    @PostMapping("/saveCarMessage")
    public Response<Void> saveCarMessage(@Valid @RequestBody SaveCarMsgRequest saveCarMsgRequest) {

        // 2.请求参数转换为DTO
        CarConvert carConvert = new CarConvertImpl();

        // 3.调用业务逻辑
        carApplicationService.saveCarMessage(carConvert.convertRequestToDTO(saveCarMsgRequest));
        return Response.success();
    }
}
