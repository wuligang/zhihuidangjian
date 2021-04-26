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
 * @Description: mall_exchange_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("mall_exchange_his")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mall_exchange_his对象", description="mall_exchange_his")
public class MallExchangeHis implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private String userId;
	/**商品表ID*/
	@Excel(name = "商品表ID", width = 15)
    @ApiModelProperty(value = "商品表ID")
    private String productId;
	/**兑换时间*/
	@Excel(name = "兑换时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "兑换时间")
    private Date exchangeTime;
    /**商品名称*/
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String proTitle;
    /**商品主图的ftp路径*/
    @Excel(name = "商品主图，附件表id", width = 15)
    @ApiModelProperty(value = "商品主图，附件表id")
    private String fileId;
    /**商品详情*/
    @Excel(name = "商品详情", width = 15)
    @ApiModelProperty(value = "商品详情")
    private String proContent;
    /**兑换积分*/
    @Excel(name = "兑换积分", width = 15)
    @ApiModelProperty(value = "兑换积分")
    private Integer proIntegral;
}
