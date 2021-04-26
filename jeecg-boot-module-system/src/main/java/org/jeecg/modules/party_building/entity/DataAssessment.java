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
 * @Description: data_assessment
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_assessment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_assessment对象", description="data_assessment")
public class DataAssessment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**考核内容*/
	@Excel(name = "考核内容", width = 15)
    @ApiModelProperty(value = "考核内容")
    private String assessContent;
	/**对应分值*/
	@Excel(name = "对应分值", width = 15)
    @ApiModelProperty(value = "对应分值")
    private BigDecimal score;
	/**考核明细*/
	@Excel(name = "考核明细", width = 15)
    @ApiModelProperty(value = "考核明细")
    private String detail;
	/**考核人 格式：|user_id1|user_id2|user_id3*/
	@Excel(name = "考核人 格式：|user_id1|user_id2|user_id3", width = 15)
    @ApiModelProperty(value = "考核人 格式：|user_id1|user_id2|user_id3")
    private String notAssessMembers;
	/**未考核人数*/
	@Excel(name = "未考核人数", width = 15)
    @ApiModelProperty(value = "未考核人数")
    private Integer notAssessCount;
	/**已考核人数*/
	@Excel(name = "已考核人数", width = 15)
    @ApiModelProperty(value = "已考核人数")
    private Integer assessCount;
	/**是否发布 1：是 2：否*/
	@Excel(name = "是否发布 1：是 2：否", width = 15)
    @ApiModelProperty(value = "是否发布 1：是 2：否")
    @Dict(dicCode = "whether")
    private String issue;
	/**删除 0或空：否 1：是*/
	@Excel(name = "删除 0或空：否 1：是", width = 15)
    @ApiModelProperty(value = "删除 0或空：否 1：是")
    @Dict(dicCode = "isDelete")
    private String isDelete;
	/**
     *  考核开始时间
     * */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "考核开始时间")
    private Date startTime;
    /**
     * 考核结束时间
     * */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "考核结束时间")
    private Date endTime;

	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
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
