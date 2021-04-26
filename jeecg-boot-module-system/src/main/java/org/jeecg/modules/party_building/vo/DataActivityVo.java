package org.jeecg.modules.party_building.vo;

import lombok.Data;
import org.jeecg.modules.party_building.entity.DataActivity;

import java.util.Date;

@Data
public class DataActivityVo extends DataActivity {
    //签到时间
    private Date signTime;
    //报名状态
    private String activitysignUpStatus;
    //活动报名id
    private String activitysignUpId;
    //活动部门
    private String hostDepartName;
}
