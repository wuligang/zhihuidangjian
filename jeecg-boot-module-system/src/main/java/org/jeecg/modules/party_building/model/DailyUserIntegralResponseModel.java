package org.jeecg.modules.party_building.model;

import lombok.Data;

import java.util.List;

@Data
public class DailyUserIntegralResponseModel {
    private Integer dailySum;

    private List<DailyUserIntegralModel> dailyUserIntegralModels;
}
