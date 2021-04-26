package org.jeecg.modules.party_building.service.impl;

import org.jeecg.modules.party_building.entity.MallProduct;
import org.jeecg.modules.party_building.mapper.MallProductMapper;
import org.jeecg.modules.party_building.service.IMallProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: mall_product
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class MallProductServiceImpl extends ServiceImpl<MallProductMapper, MallProduct> implements IMallProductService {

    @Autowired
    private MallProductMapper mallProductMapper;

    @Override
    public MallProduct getValidProdById(String id) {
        return mallProductMapper.getValidProdById(id);
    }
}
