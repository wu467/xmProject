package com.xiaomi.practice.web.battery;

import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.domain.battery.model.UploadBatteryMsgDTO;
import com.xiaomi.practice.web.dto.BatteryMsgResponseDTO;
import com.xiaomi.practice.web.dto.QueryBatteryMsgRequestDTO;

import java.util.List;

public interface BatteryMsgApplicationService {

    /**
     * 上传电池信息
     */
    void uploadBatteryMessage(UploadBatteryMsgDTO uploadBatteryMsgDTO);

    /**
     * 查询车辆电池信息
     */
    BatteryMsgDTO queryBatteryMsgByCarId(QueryBatteryMsgRequestDTO requestDTO);

    /**
     * 删除车辆电池信息
     */
    void deleteBatteryMsg(Long id, Long carId);
}
