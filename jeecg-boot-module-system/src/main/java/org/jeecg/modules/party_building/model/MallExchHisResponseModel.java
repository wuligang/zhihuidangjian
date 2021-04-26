package org.jeecg.modules.party_building.model;

import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

import java.util.Date;

@Data
public class MallExchHisResponseModel {
    private String id;

    private String userId;

    private String userName;

    private String productId;

    private String proTitle;

    private String fileId;

    private Date exchangeTime;

    private Integer proIntegral;

    @Dict(dicCode = "product_status")
    private String status;

    @Dict(dicCode = "isDelete")
    private String isDelete;

    private Integer stock;
}
