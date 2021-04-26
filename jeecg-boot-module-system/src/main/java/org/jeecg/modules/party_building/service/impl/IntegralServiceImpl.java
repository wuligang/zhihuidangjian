package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.party_building.entity.Integral;
import org.jeecg.modules.party_building.mapper.IntegralMapper;
import org.jeecg.modules.party_building.service.IIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: integral
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class IntegralServiceImpl extends ServiceImpl<IntegralMapper, Integral> implements IIntegralService {

    @Autowired
    private IntegralMapper integralMapper;

    @Override
    public Integral getIntegralByUserId(String userId) {
        return integralMapper.getIntegralByUserId(userId);
    }

    @Override
    public int updateByUserId(String userId, int changeValue, String ruleNo) {
        return integralMapper.updateByUserId(userId, changeValue, ruleNo);
    }

    @Override
    public Page<Integral> listSort(Page<Integral> page, Integral integral) {
        return page.setRecords(integralMapper.listSort(page, integral));
    }


    @Override
    public Page<Integral> listSortNew(Page<Integral> page, Integral integral) {
        return page.setRecords(integralMapper.listSortNew(page, integral));
    }

    @Override
    public List<Integral> getIntegralList(Page<Integral> page, Integral integral) {
        return integralMapper.listSort(page, integral);
    }
}
