package org.jeecg.modules.party_building.entity;

import java.io.Serializable;
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

/**
 * @Description: 分类表（资讯，微课堂，活动）
 * @Author: jeecg-boot
 * @Date:   2020-07-01
 * @Version: V1.0
 */
@Data
@TableName("data_category")
@ApiModel(value="data_category对象", description="分类表（资讯，微课堂，活动）")
public class DataCategory implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "ID")
	private String id;
	/**类目名称*/
	@Excel(name = "类目名称", width = 15)
	@ApiModelProperty(value = "类目名称")
	private String name;
	/**父类*/
	@Excel(name = "父类", width = 15)
	@ApiModelProperty(value = "父类")
	private String pid;
	/**排序*/
	@Excel(name = "排序", width = 15)
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**是否支持投稿*/
	@Excel(name = "是否支持投稿", width = 15, dicCode = "whether")
	@Dict(dicCode = "whether")
	@ApiModelProperty(value = "是否支持投稿")
	private String isManuscript;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
	@ApiModelProperty(value = "是否有子节点")
	private String hasChild;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	/**文章封面图片*/
	@Excel(name = "图片", width = 15)
	@ApiModelProperty(value = "图片")
	private String fileId;
}
