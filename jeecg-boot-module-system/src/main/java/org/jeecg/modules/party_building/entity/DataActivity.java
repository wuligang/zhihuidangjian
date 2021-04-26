package org.jeecg.modules.party_building.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: data_activity
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_activity")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_activity对象", description="党建活动管理")
public class  DataActivity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	 @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**活动名称*/
	@Excel(name = "活动名称", width = 15)
    @ApiModelProperty(value = "活动名称")
    private String name;
	/**活动发起人*/
	@Excel(name = "活动发起人", width = 15)
    @ApiModelProperty(value = "活动发起人")
    private String organizerUserId;
	/**主办部门*/
	@Excel(name = "主办部门", width = 15)
    @ApiModelProperty(value = "主办部门")
    private String hostDepartId;
	/**活动类型*/
	@Excel(name = "活动类型", width = 15)
    @ApiModelProperty(value = "活动类型")
    @Dict(dicCode = "categoryId")
    private String categoryId;
	/**活动简述*/
	@Excel(name = "活动简述", width = 15)
    @ApiModelProperty(value = "活动简述")
    private String description;
	/**报名开始时间*/
	@Excel(name = "报名开始时间", width = 15, format = "yyyy-MM-dd HH:mm")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "报名开始时间")
    private Date signUpStartTime;
	/**报名结束时间*/
	@Excel(name = "报名结束时间", width = 15, format = "yyyy-MM-dd HH:mm")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "报名结束时间")
    private Date signUpEndTime;
	/**举办开始时间*/
	@Excel(name = "举办开始时间", width = 15, format = "yyyy-MM-dd HH:mm")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "举办开始时间")
    private Date holdingStartTime;
	/**举办结束时间*/
	@Excel(name = "举办结束时间", width = 15, format = "yyyy-MM-dd HH:mm")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "举办结束时间")
    private Date holdingEndTime;
	/**活动地点*/
	@Excel(name = "活动地点", width = 15)
    @ApiModelProperty(value = "活动地点")
    private String place;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @Excel(name = "经度", width = 15,orderNum = "8")
    private BigDecimal longitude;

    /**
     * 维度
     */
    @ApiModelProperty(value = "维度")
    @Excel(name = "维度", width = 15,orderNum = "9")
    private BigDecimal dimension;
	/**参会党员*/
	@Excel(name = "参会党员", width = 15)
    @ApiModelProperty(value = "参会党员")
    private String partyMember;
	/**参会人数*/
	@Excel(name = "参会人数", width = 15)
    @ApiModelProperty(value = "参会人数")
    private Integer attendance;
	/**活动状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架*/
	@Excel(name = "活动状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架", width = 15)
    @ApiModelProperty(value = "活动状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架")
    @Dict(dicCode = "activeStatus")
    private String activeStatus;
    /**原因*/
    @Excel(name = "原因", width = 15)
    @ApiModelProperty(value = "原因")
    private String reason;
	/**删除 0或空：否 1：是*/
	@Excel(name = "删除 0或空：否 1：是", width = 15)
    @ApiModelProperty(value = "删除 0或空：否 1：是")
    @Dict(dicCode = "isDelete")
    private String isDelete;
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
    /**做数据清分*/
    @ApiModelProperty(value = "做数据清分")
    private String orgCode;

    /**
     *  活动签到区间时间
     * */
    private String betweenTime;

}
