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
 * @Description: data_information_file
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("data_information_file")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_information_file对象", description="data_information_file")
public class DataInformationFile implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**关联内容id*/
	@Excel(name = "关联内容id", width = 15)
    @ApiModelProperty(value = "关联内容id")
    private Integer relatedId;
	/**两学一做、三会一课、党建动态、党风廉政等*/
	@Excel(name = "两学一做、三会一课、党建动态、党风廉政等", width = 15)
    @ApiModelProperty(value = "两学一做、三会一课、党建动态、党风廉政等")
    private String categoryBelong;
	/**文件名称*/
	@Excel(name = "文件名称", width = 15)
    @ApiModelProperty(value = "文件名称")
    private String fileName;
	/**文件url*/
	@Excel(name = "文件url", width = 15)
    @ApiModelProperty(value = "文件url")
    private String fileUrl;
    /**关联分类表父级pid*/
    @Excel(name = "关联分类表父级pid", width = 15)
    @ApiModelProperty(value = "关联分类表父级pid")
    private String categoryPid;

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
    /*党建资讯id*/
    private String informationId;

}
