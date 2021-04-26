package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.party_building.entity.IntegralDetail;
import org.jeecg.modules.party_building.mapper.IntegralDetailMapper;
import org.jeecg.modules.party_building.model.DailyUserIntegralModel;
import org.jeecg.modules.party_building.model.IntegralDetailRank;
import org.jeecg.modules.party_building.model.MallExchHisResponseModel;
import org.jeecg.modules.party_building.model.UserIntegralDate;
import org.jeecg.modules.party_building.service.IIntegralDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: integral_detail
 * @Author: jeecg-boot
 * @Date: 2020-07-02
 * @Version: V1.0
 */
@Service
public class IntegralDetailServiceImpl extends ServiceImpl<IntegralDetailMapper, IntegralDetail> implements IIntegralDetailService {

    @Autowired
    private IntegralDetailMapper integralDetailMapper;

    @Override
    public boolean haveRecordToday(String userId, String ruleNo, String videoName) {
        return 1 <= integralDetailMapper.haveRecordToday(userId, ruleNo, videoName);
    }

    @Override
    public int sumIntegralToday(String userId, String ruleNo) {
        return integralDetailMapper.sumIntegralToday(userId, ruleNo);
    }

    @Override
    public List<DailyUserIntegralModel> dailyUserIntegralGroupRuleNo(String userId, String date) {
        return integralDetailMapper.dailyUserIntegralGroupRuleNo(userId, date);
    }

    @Override
    public IPage<UserIntegralDate> queryUserIntegralGroupDate(Page<UserIntegralDate> page, String userId) {
        return integralDetailMapper.queryUserIntegralGroupDate(page, userId);
    }

    @Override
    public IPage<IntegralDetailRank> integralRankByDate(Page<IntegralDetailRank> page, IntegralDetailRank integralDetailRank) {
        return integralDetailMapper.integralRankByDate(page, integralDetailRank);
    }

    @Override
    public IPage<IntegralDetailRank> integralRankDepartByDate(Page<IntegralDetailRank> page, IntegralDetailRank integralDetailRank) {
        return integralDetailMapper.integralRankDepartByDate(page, integralDetailRank);
    }

    @Override
    public List<UserIntegralDate> yearIntegral(String userId) {
        return integralDetailMapper.yearIntegral(userId);
    }
}
