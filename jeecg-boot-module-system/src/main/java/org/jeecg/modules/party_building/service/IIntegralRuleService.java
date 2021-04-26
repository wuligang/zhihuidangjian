package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.IntegralRule;

/**
 * @Description: integral_rule
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IIntegralRuleService extends IService<IntegralRule> {

    /**
     * 根据规则编号获取规则
     * @param ruleNo
     * @return
     */
    IntegralRule getRuleByRuleNo(String ruleNo);

}
