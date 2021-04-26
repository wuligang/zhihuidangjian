package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.MallProduct;

/**
 * @Description: mall_product
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IMallProductService extends IService<MallProduct> {

    /**
     * 查询有效的商品
     * 1)商品状态上架 2)删除标志不是1
     * @return
     */
    MallProduct getValidProdById(String id);

}
