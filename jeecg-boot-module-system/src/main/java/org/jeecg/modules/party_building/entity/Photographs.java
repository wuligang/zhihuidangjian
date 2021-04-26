package org.jeecg.modules.party_building.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("data_photographs")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Photographs {
    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 外网地址
     * */
    @Excel(name = "外网地址")
    private String urlPath;
    /**
     *  图库id
     * */
    private String ossId;
    /**
     *  app图库id
     * */
    private String appOssId;

    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**类型  0：pc端  1：小程序*/
    @Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String softType;

    /**文章标题*/
    @Excel(name = "文章标题", width = 15)
    @ApiModelProperty(value = "文章标题")
    private String title;
    /**文章内容*/
    @Excel(name = "文章内容", width = 15)
    @ApiModelProperty(value = "文章内容")
    private String content;

    /**文章来源*/
    @Excel(name = "文章来源", width = 15)
    @ApiModelProperty(value = "文章来源")
    private String source;

    /**创建者*/
    @ApiModelProperty(value = "创建者")
    private String createBy;


}
