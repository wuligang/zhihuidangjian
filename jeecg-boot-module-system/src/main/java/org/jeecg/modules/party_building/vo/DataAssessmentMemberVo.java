package org.jeecg.modules.party_building.vo;

import lombok.Data;
import org.jeecg.modules.party_building.entity.DataAssessmentMember;

import java.util.Date;

@Data
public class DataAssessmentMemberVo extends DataAssessmentMember {
    //考核内容
    private String content;
    //对应分值
    private String score;
    //考核明细
    private String detail;
    //考核开始时间
    private Date startTime;
    //考核结束时间
    private Date endTime;
    //当前考核人
    private String username;
    //未考核人数
    private Integer notAssessCount;
    //已考核人数
    private Integer assessCount;

}
