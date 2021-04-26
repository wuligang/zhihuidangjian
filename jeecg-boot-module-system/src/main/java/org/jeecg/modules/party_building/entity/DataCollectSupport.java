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
 * @Description: data_collect_support
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_collect_support")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_collect_support对象", description="data_collect_support")
public class DataCollectSupport implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private String userId;
	/**资讯表ID/微课堂表ID*/
	@Excel(name = "资讯表ID/微课堂表ID", width = 15)
    @ApiModelProperty(value = "资讯表ID/微课堂表ID")
    private String infoId;
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private String title;
	/**模块大类 1：资讯 2：微课堂*/
	@Excel(name = "模块大类 1：资讯 2：微课堂", width = 15)
    @ApiModelProperty(value = "模块大类 1：资讯 2：微课堂")
    private String module;
	/**操作时间*/
	@Excel(name = "操作时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date operateTime;
	/**类别 1：收藏 2：点赞*/
	@Excel(name = "类别 1：收藏 2：点赞", width = 15)
    @ApiModelProperty(value = "类别 1：收藏 2：点赞")
    private String type;
}
