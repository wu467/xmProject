package com.xiaomi.practice.api;

import com.xiaomi.domain.rule.RuleDomainService;
import com.xiaomi.domain.warn.service.BatteryWarnDomainServiceImpl;
import com.xiaomi.domain.warn.service.entity.BatterySignalDTO;
import com.xiaomi.domain.warn.service.entity.BatteryWarnRule;
import com.xiaomi.domain.warn.service.entity.WarnResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRuleTest {

    @Autowired
    private RuleDomainService ruleDomainService;

    @Autowired
    private BatteryWarnDomainServiceImpl batteryWarnDomainService;


    /**
     * 测试动态配置预警规则并解析
     */
    @Test
    public void testDynamicRule() {
        // 1.从数据库中获取所有电池预警规则
//        RuleDomainService ruleDomainService = new RuleDomainServiceImpl();
        List<BatteryWarnRule> allRules = ruleDomainService.getAllRules();

        // 2.模拟电池专家 添加预警规则
        BatteryWarnRule rule = new BatteryWarnRule();
        rule.setWarnRuleId(1);
        rule.setWarnName("电池电压差值--预警");
        // 设置预警范围值
        rule.setWarnMaxValue(new BigDecimal("100.00"));
        rule.setWarnMinValue(new BigDecimal("80.00"));
        // 设置报警等级
        rule.setWarnLevel(6);
        // 设置电池类型
        rule.setBatteryType("超威电池");
        allRules.add(rule);

        System.out.println(allRules.size());

        // 2.模拟电池信号数据
        BatterySignalDTO signalDTO = new BatterySignalDTO();
        signalDTO.setCarId(1L);
        signalDTO.setBatteryType("超威电池");
        signalDTO.setBatterySignal("{\"Mx\":200.0,\"Mi\":110,\"Ix\":12.0,\"Ii\":11.7}");
        List<BatterySignalDTO> signalDTOS = new ArrayList<>();
        signalDTOS.add(signalDTO);

        List<WarnResult> warnResultList = batteryWarnDomainService.parseSignals(signalDTOS, allRules);

        System.out.println(warnResultList.get(0));
    }

}
