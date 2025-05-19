package com.xiaomi.domain.battery.repository;


import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.common.base.dto.BatterySignalMsgDTO;
import com.xiaomi.domain.battery.model.BatteryMessage;
import com.xiaomi.domain.battery.model.QueryBatteryMsgDTO;

import java.time.LocalDate;
import java.util.List;


public interface BatteryMsgRepository {

    /**
     * 保存电池信号信息
     *
     * @param batteryMessage 电池信息信息
     */
    void save(BatteryMessage batteryMessage);

    /**
     * 根据车辆ID查询电池信息
     *
     * @param queryBatteryMsgDTO 查询参数
     * @return 电池信息列表
     */
    BatteryMsgDTO queryBatteryMsgListByCarId(QueryBatteryMsgDTO queryBatteryMsgDTO);

    /**
     * 根据消息记录id删除电池信息
     *
     * @param id 记录id
     */
    void deleteById(Long id);

    /**
     * 根据日期查询电池信息
     *
     * @param today 当天日期
     */
    List<BatterySignalMsgDTO> getYesterBatteryMsgList(LocalDate today);

}
