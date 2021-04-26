package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.party_building.entity.DataCollectSupport;
import org.jeecg.modules.party_building.mapper.DataCollectSupportMapper;
import org.jeecg.modules.party_building.service.IDataCollectSupportService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: data_collect_support
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataCollectSupportServiceImpl extends ServiceImpl<DataCollectSupportMapper, DataCollectSupport> implements IDataCollectSupportService {


    @Override
    public String selectTypeByUserIdAndInfoId(String id, String userId, String supportType) {
        return baseMapper.selectTypeByUserIdAndInfoId(id,userId,supportType);
    }

}
