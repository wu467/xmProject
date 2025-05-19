package com.xiaomi.practice.infrastructure.battery;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaomi.common.base.dto.BatteryMsgDTO;
import com.xiaomi.common.base.dto.BatterySignalMsgDTO;
import com.xiaomi.common.base.exception.BusinessException;
import com.xiaomi.domain.battery.model.BatteryMessage;
import com.xiaomi.domain.battery.model.QueryBatteryMsgDTO;
import com.xiaomi.domain.battery.repository.BatteryMsgRepository;
import com.xiaomi.practice.infrastructure.mapper.BatteryMsgMapper;
import com.xiaomi.practice.infrastructure.convert.BatteryMessageConvert;
import com.xiaomi.practice.infrastructure.entity.BatteryMessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@Slf4j
public class BatteryMsgRepositoryImpl implements BatteryMsgRepository {

    @Autowired
    private BatteryMsgMapper batteryMsgMapper;

    /**
     * 保存电池信息信息
     * @param batteryMessage 电池信息信息
     */
    @Override
    public void save(BatteryMessage batteryMessage) {
        BatteryMessageConvert batteryMsgConvert = new BatteryMessageConvert();
        BatteryMessageEntity batteryMessageEntity = batteryMsgConvert.convertToEntity(batteryMessage);
        batteryMsgMapper.insert(batteryMessageEntity);
        log.info("保存电池信息成功, carId{}", batteryMessage.getCarId());
    }


    /**
     * 根据carId分页查询电池信息
     */
    @Override
    public BatteryMsgDTO queryBatteryMsgListByCarId(QueryBatteryMsgDTO queryBatteryMsgDTO) {

        LambdaQueryWrapper<BatteryMessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BatteryMessageEntity::getCarId, queryBatteryMsgDTO.getCarId());

        Page<BatteryMessageEntity> page = new Page<>(queryBatteryMsgDTO.getPageNum(), queryBatteryMsgDTO.getPageSize());
        List<BatteryMessageEntity> records = batteryMsgMapper.selectPage(page, queryWrapper).getRecords();

        // 数据转换为dto
        BatteryMsgDTO batteryMsgDTO = new BatteryMsgDTO();
        batteryMsgDTO.setCarId(queryBatteryMsgDTO.getCarId());
        batteryMsgDTO.setPageNum((int) page.getCurrent());
        batteryMsgDTO.setPageSize((int) page.getSize());
        batteryMsgDTO.setTotalPage((int) page.getPages());
        batteryMsgDTO.setBatterySignalList(records.stream().map(BatteryMessageConvert::convertToBatteryMessage).collect(Collectors.toList()));

        return batteryMsgDTO;
    }

    /**
     * 删除电池信息记录
     *
     * @param id 记录id
     */
    @Override
    public void deleteById(Long id) {
        int deleteResult = batteryMsgMapper.deleteById(id);
        if (deleteResult <= 0) {
            throw new BusinessException(410, "该条数据记录已删除");
        }
    }

    /**
     * 获取昨天的电池信息记录
     *
     * @param today 今日
     * @return 电池信息记录列表
     */
    @Override
    public List<BatterySignalMsgDTO> getYesterBatteryMsgList(LocalDate today) {
        // TODO 考虑分批查询
        // 查询昨天的电池信息记录
        LambdaQueryWrapper<BatteryMessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(BatteryMessageEntity::getCreateTime, today);
        queryWrapper.ge(BatteryMessageEntity::getCreateTime, today.minusDays(1));
        List<BatteryMessageEntity> entityList = batteryMsgMapper.selectList(queryWrapper);

        return entityList.stream().parallel()
                .map(this::convertEntityToDTO).collect(Collectors.toList());
    }


    /**
     * 将电池信号实体类转换成DTO
     * @param entity 实体类
     * @return dto
     */
    private BatterySignalMsgDTO convertEntityToDTO(BatteryMessageEntity entity) {
        BatterySignalMsgDTO dto = new BatterySignalMsgDTO();
        dto.setCarId(entity.getCarId());
        dto.setWarnRuleId(entity.getWarnRuleId());
        dto.setBatterySignal(entity.getBatterySignal());
        return dto;
    }

}
