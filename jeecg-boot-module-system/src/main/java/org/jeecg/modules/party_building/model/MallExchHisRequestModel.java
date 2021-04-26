package org.jeecg.modules.party_building.model;

import lombok.Data;
import org.jeecg.modules.party_building.entity.MallProduct;
import org.jeecg.modules.system.entity.SysUser;

import java.util.Date;

@Data
public class MallExchHisRequestModel {
    private String id;

    private String userId;

    private String userName;

    private String proId;

    private String proTitle;
}
