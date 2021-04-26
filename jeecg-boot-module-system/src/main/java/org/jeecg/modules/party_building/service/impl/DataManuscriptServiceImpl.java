package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.party_building.entity.DataManuscript;
import org.jeecg.modules.party_building.mapper.DataManuscriptMapper;
import org.jeecg.modules.party_building.service.IDataManuscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_manuscript
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataManuscriptServiceImpl extends ServiceImpl<DataManuscriptMapper, DataManuscript> implements IDataManuscriptService {
        @Autowired
        private DataManuscriptMapper dataManuscriptMapper;
//    @Override
//    public List<DataManuscript> selectPageByCondition(String queryCondition, Integer pageNo, Integer pageSize) {
//        return baseMapper.selectPageByCondition(queryCondition,(pageNo-1)*pageSize,pageSize);
//    }
//
//    @Override
//    public int countPageByCondition(String queryCondition) {
//        return baseMapper.countPageByCondition(queryCondition);
//    }

    @Override
    public int countDataManuscriptAndImagelist(Map<String, Object> map) {
        return baseMapper.countDataManuscriptAndImagelist(map);
    }

    @Override
    public List<Map<String, Object>> selectDataManuscriptAndImagelist(Map<String, Object> map) {
        return baseMapper.selectDataManuscriptAndImagelist(map);
    }

    @Override
    public IPage<DataManuscript> selectPageByCondition(Page<DataManuscript> page, Map<String, Object> map) {
        return baseMapper.selectPageByCondition(page,map);
    }
    /**
     * 根据条件模糊查询稿件
     * @param queryCondition
     * @return
     */
//    @Override
//    public IPage<DataManuscript> selectPageByCondition(Page<DataManuscript> page, String queryCondition) {
//        return baseMapper.selectPageByCondition(page,queryCondition);
//    }


}
