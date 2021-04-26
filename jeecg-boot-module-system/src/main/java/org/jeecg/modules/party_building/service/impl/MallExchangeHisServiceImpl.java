package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.party_building.entity.MallExchangeHis;
import org.jeecg.modules.party_building.mapper.MallExchangeHisMapper;
import org.jeecg.modules.party_building.model.MallExchHisRequestModel;
import org.jeecg.modules.party_building.model.MallExchHisResponseModel;
import org.jeecg.modules.party_building.service.IMallExchangeHisService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: mall_exchange_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class MallExchangeHisServiceImpl extends ServiceImpl<MallExchangeHisMapper, MallExchangeHis> implements IMallExchangeHisService {

    @Override
    public IPage<MallExchHisResponseModel> queryExchangeHisList(Page<MallExchHisResponseModel> page, MallExchHisRequestModel mallExchHisRequestModel) {
        return baseMapper.queryExchangeHisList(page, mallExchHisRequestModel);
    }
}
