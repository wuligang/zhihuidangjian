package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.DataInformation;
import org.jeecg.modules.party_building.mapper.DataInformationMapper;
import org.jeecg.modules.party_building.service.IDataInformationService;
import org.jeecg.modules.party_building.vo.DataInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_information
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataInformationServiceImpl extends ServiceImpl<DataInformationMapper, DataInformation> implements IDataInformationService {
    @Autowired
    private DataInformationMapper dataInformationMapper;
    @Override
    public List<Map<String,Object>> queryCleanByCategoryId(String categoryId, String articleStates, String isLimit) {
        return baseMapper.queryCleanByCategoryId(categoryId,articleStates,isLimit);
    }

    @Override
    public List<Map<String,Object>> listByCategoryId(String categoryId,String articleStates, Integer start, Integer size) {
        return baseMapper.listByCategoryId(categoryId,articleStates,start,size);
    }

    @Override
    public IPage<DataInformationVo> pageList(Page<DataInformation> page, QueryWrapper<DataInformation> queryWrapper) {
        return baseMapper.selectPageVo(page,queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listRotationChartByMap(Map<String, Object> map) {
        return baseMapper.listRotationChartByMap(map);
    }

    @Override
    public Result updateStatusByIds(List<String> asList, String status, String reason) {
        return Result.ok("操作成功数:"+baseMapper.updateStatusByIds(asList,status,reason));
    }

    @Override
    public List<Map<String, Object>> selectInformationAndImagelist(Map<String, Object> map) {
        return baseMapper.selectInformationAndImagelist(map);
    }

    @Override
    public int countinformationAndImagelist(Map<String, Object> map) {
        return baseMapper.countinformationAndImagelist(map);
    }


}
