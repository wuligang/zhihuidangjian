package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.DataCollectSupport;

import java.util.List;

/**
 * @Description: data_collect_support
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IDataCollectSupportService extends IService<DataCollectSupport> {


    String selectTypeByUserIdAndInfoId(String id, String userId, String supportType);

}
