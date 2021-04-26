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
 * @Description: data_small_class
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_small_class")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_small_class对象", description="data_small_class")
public class DataSmallClass implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private Integer id;
	/**微课堂分类*/
	@Excel(name = "微课堂分类", width = 15)
    @ApiModelProperty(value = "微课堂分类")
    private String categoryId;
	/**微课堂标题*/
	@Excel(name = "微课堂标题", width = 15)
    @ApiModelProperty(value = "微课堂标题")
    private String title;
    /**微课堂内容*/
    @Excel(name = "微课堂内容", width = 15)
    @ApiModelProperty(value = "微课堂内容")
	private String content;
	/**微课堂来源*/
	@Excel(name = "微课堂来源", width = 15)
    @ApiModelProperty(value = "微课堂来源")
    private String source;
	/**微课堂状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架*/
	@Excel(name = "微课堂状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架", width = 15)
    @ApiModelProperty(value = "微课堂状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架")
    private String classStates;
	/**操作理由*/
	@Excel(name = "操作理由", width = 15)
    @ApiModelProperty(value = "操作理由")
    private String reason;
	/**阅读数量*/
	@Excel(name = "阅读数量", width = 15)
    @ApiModelProperty(value = "阅读数量")
    private Integer readNo;
	/**点赞数量*/
	@Excel(name = "点赞数量", width = 15)
    @ApiModelProperty(value = "点赞数量")
    private Integer supportNo;
	/**删除 0或空：否 1：是*/
	@Excel(name = "删除 0或空：否 1：是", width = 15)
    @ApiModelProperty(value = "删除 0或空：否 1：是")
    private String isDelete;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
}
