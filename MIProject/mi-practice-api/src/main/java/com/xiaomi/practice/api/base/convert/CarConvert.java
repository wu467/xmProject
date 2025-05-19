package com.xiaomi.practice.api.base.convert;

import com.xiaomi.practice.api.base.request.SaveCarMsgRequest;
import com.xiaomi.domain.car.model.SaveCarMsgRequestDTO;

public interface CarConvert {


    SaveCarMsgRequestDTO convertRequestToDTO(SaveCarMsgRequest saveCarMsgRequest);
}
