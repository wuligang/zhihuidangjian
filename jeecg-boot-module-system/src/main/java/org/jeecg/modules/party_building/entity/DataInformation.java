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
 * @Description: data_information
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_information")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_information对象", description="data_information")
public class DataInformation implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**关联分类表ID*/
	@Excel(name = "关联分类表ID", width = 15)
    @ApiModelProperty(value = "关联分类表ID")
    private String categoryId;
	/**文章标题*/
    @Excel(name = "文章标题", width = 15)
    @ApiModelProperty(value = "文章标题")
    private String title;
    /**文章内容*/
    @Excel(name = "文章内容", width = 15)
    @ApiModelProperty(value = "文章内容")
    private String content;

    /**文章内容类型  0：富文本  1：url*/
    @Excel(name = "文章内容类型", width = 15)
    @ApiModelProperty(value = "文章内容类型")
    private String contentType;

    /**文章内容链接 0：富文本  1：url*/
    @Excel(name = "文章内容类型", width = 15)
    @ApiModelProperty(value = "文章内容类型")
    private String url;

    /**文章来源*/
    @Excel(name = "文章来源", width = 15)
    @ApiModelProperty(value = "文章来源")
    private String source;
    /**文章状态 0：待提交 1：待审核 2：审核通过 3：驳回 4：下架*/
    @Excel(name = "文章状态 1：待提交 2：待审核 3：审核通过 4：驳回 5：下架", width = 15)
    @ApiModelProperty(value = "文章状态 1：待提交 2：待审核 3：审核通过 4：驳回 5：下架")
    @Dict(dicCode = "manuscript_status")
    private String status;
    /**模块大类*/
    @Dict(dicCode = "module")
    @ApiModelProperty(value = "模块分类 1：资讯，2：微课堂")
    private String module;
    /**操作理由*/
    @Excel(name = "操作理由", width = 15)
    @ApiModelProperty(value = "操作理由")
    private String reason;
    /**阅读数量*/
    @Excel(name = "阅读数量", width = 15)
    @ApiModelProperty(value = "阅读数量")
    private Integer readNo;
    /**文章封面图片*/
    @Excel(name = "文章封面图片", width = 15)
    @ApiModelProperty(value = "文章封面图片")
    private String fileImageId;
    /**文章视频*/
    @Excel(name = "文章视频", width = 15)
    @ApiModelProperty(value = "文章视频")
    private String fileVideoId;
    /**点赞数量*/
    @Excel(name = "点赞数量", width = 15)
    @ApiModelProperty(value = "点赞数量")
    private Integer supportNo;

    /**机构代码*/
    @Excel(name = "机构代码", width = 15)
    @ApiModelProperty(value = "机构代码")
    private String orgCode;

    /**删除 0或空：否 1：是*/
    @Excel(name = "删除 0或空：否 1：是", width = 15)
    @ApiModelProperty(value = "删除 0或空：否 1：是")
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
}
