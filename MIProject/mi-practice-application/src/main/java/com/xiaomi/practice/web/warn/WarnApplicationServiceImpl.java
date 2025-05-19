package com.xiaomi.practice.web.warn;

import com.xiaomi.domain.warn.repository.WarningRecordRepository;
import com.xiaomi.domain.warn.service.entity.WarnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * 告警服务实现
 */
@Slf4j
@Service
public class WarnApplicationServiceImpl implements WarnApplicationService {

    @Autowired
    private WarningRecordRepository warningRecordRepository;

    /**
     * 查询告警记录
     * @param carId 车架id
     */
    @Override
    public List<WarnResult> queryWarnRecords(Long carId) {
        if (Objects.isNull(carId)) {
            log.warn("查询车辆告警记录失败, 车架id为{}", carId);
            return Collections.emptyList();
        }

        return warningRecordRepository.queryWarnRecords(carId);
    }
}
