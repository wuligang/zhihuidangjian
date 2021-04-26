package org.jeecg.modules.party_building.model;

import lombok.Data;

/**
 * 用户当天积分学习情况
 */
@Data
public class DailyUserIntegralModel {

    private Integer dayCurrIntegral;

    private String userId;

    private String ruleNo;

    private String section;

    private Integer dailyLimit;
}
