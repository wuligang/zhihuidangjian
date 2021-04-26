package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.MallProduct;

/**
 * @Description: mall_product
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface MallProductMapper extends BaseMapper<MallProduct> {

    /**
     * 查询有效的商品
     * 1)商品状态上架 2)删除标志不是1
     * @param id
     * @return
     */
    MallProduct getValidProdById(@Param("id") String id);

}
