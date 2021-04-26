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
 * @Description: data_activity_sign
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_activity_sign")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_activity_sign对象", description="data_activity_sign")
public class DataActivitySign implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**
     *  活动报名id
     * */
    @Excel(name = "关联活动报名id", width = 15)
    @ApiModelProperty(value = "关联活动报名id")
	private String actitySignUpId;
	/**活动名称*/
	@Excel(name = "活动名称", width = 15)
    @ApiModelProperty(value = "活动名称")
    private String name;
	/**关联用户ID*/
	@Excel(name = "关联用户ID", width = 15)
    @ApiModelProperty(value = "关联用户ID")
    private String userId;
	/**用户名称*/
	@Excel(name = "用户名称", width = 15)
    @ApiModelProperty(value = "用户名称")
    private String userName;
	/**签到时间*/
	@Excel(name = "签到时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "签到时间")
    private Date signTime;
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
}
