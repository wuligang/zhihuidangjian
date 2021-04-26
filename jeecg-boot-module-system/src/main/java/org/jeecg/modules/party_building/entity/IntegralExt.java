package org.jeecg.modules.party_building.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class IntegralExt extends Integral {
    /**排名*/
    @Excel(name = "排名", width = 15)
    @ApiModelProperty(value = "排名")
    private Integer rank;
    /**头像*/
    @Excel(name = "头像", width = 15)
    @ApiModelProperty(value = "头像")
    private String avatar;
}
