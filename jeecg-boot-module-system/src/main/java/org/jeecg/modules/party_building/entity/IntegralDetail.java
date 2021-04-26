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
 * @Description: integral_detail
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("integral_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral_detail对象", description="integral_detail")
public class IntegralDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private String userId;
	/**积分变动值*/
	@Excel(name = "积分变动值", width = 15)
    @ApiModelProperty(value = "积分变动值")
    private Integer changeValue;
	/**积分变动原因*/
	@Excel(name = "积分变动原因", width = 15)
    @ApiModelProperty(value = "积分变动原因")
    private String changeReason;
	/**视频名称*/
	@Excel(name = "视频名称", width = 15)
    @ApiModelProperty(value = "视频名称")
    private String videoName;
	/**规则编号*/
	@Excel(name = "规则编号", width = 15)
    @ApiModelProperty(value = "规则编号")
    private String ruleNo;
	/**变动时间*/
	@Excel(name = "变动时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "变动时间")
    private Date changeTime;
}
