package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.party_building.entity.MallExchangeHis;
import org.jeecg.modules.party_building.model.MallExchHisRequestModel;
import org.jeecg.modules.party_building.model.MallExchHisResponseModel;

/**
 * @Description: mall_exchange_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface MallExchangeHisMapper extends BaseMapper<MallExchangeHis> {

    /**
     * 查询商品兑换历史
     * @param page
     * @param mallExchHisRequestModel
     * @return
     */
    IPage<MallExchHisResponseModel> queryExchangeHisList(Page<MallExchHisResponseModel> page, MallExchHisRequestModel mallExchHisRequestModel);

}
