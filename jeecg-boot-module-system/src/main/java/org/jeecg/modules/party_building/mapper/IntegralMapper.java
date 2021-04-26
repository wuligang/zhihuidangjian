package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.Integral;

import java.util.List;

/**
 * @Description: integral
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IntegralMapper extends BaseMapper<Integral> {

    /**
     * 获取指定用户的积分
     * @param userId
     */
    Integral getIntegralByUserId(@Param("userId") String userId);

    /**
     * 更新用户总积分
     * @param userId
     * @param changeValue
     * @return
     */
    int updateByUserId(@Param("userId") String userId, @Param("changeValue") int changeValue, @Param("ruleNo") String ruleNo);

    /**
     * 积分排序
     * @param integral
     * @return
     */
    List<Integral> listSort(Page<Integral> page, Integral integral);


    /**
     * 积分排序
     * @param integral
     * @return
     */
    List<Integral> listSortNew(Page<Integral> page, Integral integral);

}
