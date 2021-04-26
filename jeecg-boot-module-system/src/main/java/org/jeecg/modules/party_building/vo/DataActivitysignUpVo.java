package org.jeecg.modules.party_building.vo;

import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.party_building.entity.DataActivitysignUp;

import java.util.Date;
@Data
public class DataActivitysignUpVo extends DataActivitysignUp {
    //活动类型
    @Dict(dicCode = "categoryId")
    private String categoryId;
    //活动发起人
    private String organizerUserId;
    //活动简述
    private String description;
    //报名开始时间
    private Date signUpStartTime;
    //报名结束时间
    private Date signUpEndTime;
    //举办开始时间
    private Date holdingStartTime;
    //举办结束时间
    private Date holdingEndTime;
    //活动签到开始时间
    private Date qanDaoStarTime;
    //活动地点
    private String place;
    //主办部门
    private String hostDepartId;
    //经度
    private String longitude;
    //维度
    private String dimension;
    //参会人数
    private Integer attendance;
    //参会党员
    private String partyMember;
    //用户签到时间
    private Date signTime;
    //活动亲到时间范围
    private String betweenTime;
    //部门名称
    private String hostDepartName;
}
