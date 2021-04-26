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
 * @Description: integral_rule
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("integral_rule")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral_rule对象", description="integral_rule")
public class IntegralRule implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**规则编号*/
	@Excel(name = "规则编号", width = 15)
    @ApiModelProperty(value = "规则编号")
    private String ruleNo;
	/**栏目*/
	@Excel(name = "栏目", width = 15)
    @ApiModelProperty(value = "栏目")
    private String section;
	/**时长*/
	@Excel(name = "时长", width = 15)
    @ApiModelProperty(value = "时长")
    private Integer timeLength;
	/**时间单位 s：秒，m：分，h：时*/
	@Excel(name = "时间单位 s：秒，m：分，h：时", width = 15)
    @ApiModelProperty(value = "时间单位 s：秒，m：分，h：时")
    private String timeUnit;
	/**积分分值*/
	@Excel(name = "积分分值", width = 15)
    @ApiModelProperty(value = "积分分值")
    private Integer integralScore;
	/**每日上限*/
	@Excel(name = "每日上限", width = 15)
    @ApiModelProperty(value = "每日上限")
    private Integer dailyLimit;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private String integralExplain;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
    private String createBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBys;
}
