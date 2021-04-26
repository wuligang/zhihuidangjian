package org.jeecg.modules.party_building.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("data_activity_signUp")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_activity_signUp对象", description="data_activity_signUp")
public class DataActivitysignUp {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
    /**关联活动表ID*/
    @Excel(name = "关联活动表ID", width = 15)
    @ApiModelProperty(value = "关联活动表ID")
    private String activityId;
    /**活动名称*/
    @Excel(name = "活动名称", width = 15)
    @ApiModelProperty(value = "活动名称")
    private String name;
    /**报名状态 1 未报名 2 已报名*/
    @Excel(name = "报名状态", width = 15)
    @ApiModelProperty(value = "报名状态")
    @Dict(dicCode = "activitysignUpStatus")
    private String activitysignUpStatus;
    /**关联用户ID*/
    @Excel(name = "关联用户ID", width = 15)
    @ApiModelProperty(value = "关联用户ID")
    private String userId;
    /**用户报名时间*/
    @Excel(name = "用户报名时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "用户报名时间")
    private Date signUpTime;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**创建者*/
    @ApiModelProperty(value = "创建者")
    private String createBy;
    /**修改时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**修改者*/
    @ApiModelProperty(value = "修改者")
    private String updateBy;
}
