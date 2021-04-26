package org.jeecg.modules.party_building.service.impl;

import org.jeecg.modules.party_building.entity.DataActivitySign;
import org.jeecg.modules.party_building.mapper.DataActivitySignMapper;
import org.jeecg.modules.party_building.service.IDataActivitySignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: data_activity_sign
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataActivitySignServiceImpl extends ServiceImpl<DataActivitySignMapper, DataActivitySign> implements IDataActivitySignService {
    @Autowired
    private DataActivitySignMapper dataActivitySignMapper;
}
