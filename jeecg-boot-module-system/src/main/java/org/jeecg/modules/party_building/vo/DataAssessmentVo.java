package org.jeecg.modules.party_building.vo;

import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

import java.util.Date;

@Data
public class DataAssessmentVo {
    //考核id
    private String id;
    //考核内容
    private String assessContent;
    //考核总成绩
    private String score;
    //考核明细
    private String detail;
    //自评状态
    @Dict(dicCode = "memberstatus")
    private String status;
    //自评分数
    private String selfScore;
    //考核结束时间
    private Date endTime;
    //考核开始时间
    private Date startTime;
    //考核自评时间
    private Date createTime;
    //驳回原因
    private String reason;

}
