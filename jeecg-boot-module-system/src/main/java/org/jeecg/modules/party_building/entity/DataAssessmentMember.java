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
 * @Description: data_assessment_member
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_assessment_member")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_assessment_member对象", description="data_assessment_member")
public class DataAssessmentMember implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**assessment_id*/
	@Excel(name = "assessment_id", width = 15)
    @ApiModelProperty(value = "assessment_id")
    private String assessmentId;
	/**
     *  当前考核人
     * */
    @Excel(name = "当前考核人", width = 15)
    @ApiModelProperty(value = "当前考核人")
	private String assessmentUserId;
	/**自评分数*/
	@Excel(name = "自评分数", width = 15)
    @ApiModelProperty(value = "自评分数")
    private BigDecimal selfScore;
	/**自评状态  0:未考核 1：待审核 2：审核通过 3：驳回*/
	@Excel(name = "自评状态 0：未考核 1：待审核 2：审核通过 3：驳回", width = 15)
    @ApiModelProperty(value = "自评状态 0：未考核1：待审核 2：审核通过 3：驳回")
    @Dict(dicCode = "memberstatus")
    private String status;
	/**原因*/
	@Excel(name = "原因", width = 15)
    @ApiModelProperty(value = "原因")
    private String reason;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
}
