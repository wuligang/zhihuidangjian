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
 * @Description: data_manuscript
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */

@Data
@TableName("data_manuscript")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_manuscript对象", description="data_manuscript")
public class DataManuscript implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**稿件类型*/
	@Excel(name = "稿件类型", width = 15)
    @ApiModelProperty(value = "稿件类型")
    @Dict(dicCode = "script_categoryId")
    private Integer categoryId;
	/**稿件名称*/
	@Excel(name = "稿件名称", width = 15)
    @ApiModelProperty(value = "稿件名称")
    private String name;
	/**稿件内容*/
	@Excel(name = "稿件内容", width = 15)
    @ApiModelProperty(value = "稿件内容")
    private String content;
	/**作者*/
	@Excel(name = "作者", width = 15)
    @ApiModelProperty(value = "作者")
    private String author;
	/**稿件状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架*/
	@Excel(name = "稿件状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架", width = 15)
    @ApiModelProperty(value = "稿件状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架")
    @Dict(dicCode = "manuscript_status")
    private String manuscriptStatus;
	/**删除 0或空：否 1：是*/
	@Excel(name = "删除 0或空：否 1：是", width = 15)
    @ApiModelProperty(value = "删除 0或空：否 1：是")
    @Dict(dicCode = "isDelete")
    private String isDelete;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
    private String createBy;
	/**修改时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;

    /**稿件封面图片*/
    @Excel(name = "稿件封面图片", width = 15)
    @ApiModelProperty(value = "稿件封面图片")
    private String fileImageId;
    /**稿件视频*/
    @Excel(name = "稿件视频", width = 15)
    @ApiModelProperty(value = "稿件视频")
    private String fileVideoId;
    /**驳回理由*/
    @Excel(name = "驳回理由", width = 15)
    @ApiModelProperty(value = "驳回理由")
    private String rejectReason;

    /**阅读数量*/
    @Excel(name = "阅读数量", width = 15)
    @ApiModelProperty(value = "阅读数量")
    private Integer readNo;
    /**点赞数量*/
    @Excel(name = "点赞数量", width = 15)
    @ApiModelProperty(value = "点赞数量")
    private Integer supportNo;

    private String orgCode;

    private String userId;
}
