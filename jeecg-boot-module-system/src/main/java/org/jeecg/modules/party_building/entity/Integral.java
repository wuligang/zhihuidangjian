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
 * @Description: integral
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("integral")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral对象", description="integral")
public class Integral implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private String userId;
    /**用户姓名*/
    @Excel(name = "用户姓名", width = 15)
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    /**用户姓名*/
    @Excel(name = "真实姓名", width = 15)
    @ApiModelProperty(value = "真实姓名")
    private String realName;
	/**累计积分*/
	@Excel(name = "累计积分", width = 15)
    @ApiModelProperty(value = "累计积分")
    private Integer totalIntegral;
	/**当前积分*/
	@Excel(name = "当前积分", width = 15)
    @ApiModelProperty(value = "当前积分")
    private Integer currentIntegral;

    /**
     * 部门code(当前选择登录部门)
     */
    @Excel(name = "部门", width = 50)
    private String orgCode;

    /**排名*/
    @Excel(name = "排名", width = 15)
    @ApiModelProperty(value = "排名")
    private Integer rankNew;
}
