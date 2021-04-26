package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.IntegralRule;

/**
 * @Description: integral_rule
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IntegralRuleMapper extends BaseMapper<IntegralRule> {

    IntegralRule getRuleByRuleNo(@Param("ruleNo") String ruleNo);

}
