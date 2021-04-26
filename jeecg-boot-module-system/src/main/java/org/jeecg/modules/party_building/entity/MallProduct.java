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
 * @Description: mall_product
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("mall_product")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mall_product对象", description="mall_product")
public class MallProduct implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private String id;
	/**商品编号*/
	@Excel(name = "商品编号", width = 15)
    @ApiModelProperty(value = "商品编号")
    private String proNo;
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
	/**兑换说明*/
	@Excel(name = "兑换说明", width = 15)
    @ApiModelProperty(value = "兑换说明")
    private String exchangeExplain;
	/**商品库存*/
	@Excel(name = "商品库存", width = 15)
    @ApiModelProperty(value = "商品库存")
    private Integer stock;
	/**商品状态 0：新增 1：上架 2：下架*/
	@Excel(name = "商品状态 0：新增 1：上架 2：下架", width = 15)
    @ApiModelProperty(value = "商品状态 0：新增 1：上架 2：下架")
    @Dict(dicCode = "product_status")
    private String status;
	/**删除 0或空：否 1：是*/
	@Excel(name = "删除 0或空：否 1：是", width = 15)
    @ApiModelProperty(value = "删除 0或空：否 1：是")
    @Dict(dicCode = "isDelete")
    private String isDelete;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
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
