package org.jeecg.modules.party_building.service.impl;

import org.jeecg.modules.party_building.entity.DataSmallClass;
import org.jeecg.modules.party_building.mapper.DataSmallClassMapper;
import org.jeecg.modules.party_building.service.IDataSmallClassService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_small_class
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataSmallClassServiceImpl extends ServiceImpl<DataSmallClassMapper, DataSmallClass> implements IDataSmallClassService {

    @Override
    public List<DataSmallClass> querySmallClassByCategoryId(String categoryId, String classStates) {
        return baseMapper.querySmallClassByCategoryId(categoryId,classStates);
    }

    @Override
    public List<Map<String, Object>> getSmallClassByMap(Map<String, Object> map) {
        return baseMapper.getSmallClassByMap(map);
    }

    @Override
    public List<Map<String, Object>> selectSmallClassAppPageByCondition(Map<String, Object> map) {
        return baseMapper.selectSmallClassAppPageByCondition(map);
    }

    @Override
    public int countSmallClassAppPageByCondition(Map<String, Object> map) {
        return baseMapper.countSmallClassAppPageByCondition(map);
    }

}
