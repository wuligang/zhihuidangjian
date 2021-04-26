package org.jeecg.modules.party_building.service.impl;

import org.jeecg.modules.party_building.entity.IntegralRule;
import org.jeecg.modules.party_building.mapper.IntegralRuleMapper;
import org.jeecg.modules.party_building.service.IIntegralRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: integral_rule
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class IntegralRuleServiceImpl extends ServiceImpl<IntegralRuleMapper, IntegralRule> implements IIntegralRuleService {

    @Autowired
    private IntegralRuleMapper integralRuleMapper;

    @Override
    public IntegralRule getRuleByRuleNo(String ruleNo) {
        return integralRuleMapper.getRuleByRuleNo(ruleNo);
    }
}
