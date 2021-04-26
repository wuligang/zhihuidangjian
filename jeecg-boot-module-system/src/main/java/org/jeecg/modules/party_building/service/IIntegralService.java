package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.Integral;

import java.util.List;

/**
 * @Description: integral
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IIntegralService extends IService<Integral> {

    /**
     * 获取指定用户的积分
     * @param userId
     */
    Integral getIntegralByUserId(String userId);

    /**
     * 更新用户总积分
     * @param userId
     * @param changeValue
     * @return
     */
    int updateByUserId(String userId, int changeValue, String ruleNo);

    /**
     * 积分排序
     * @param page
     * @param integral
     * @return
     */
    Page<Integral> listSort(Page<Integral> page, Integral integral);

    Page<Integral> listSortNew(Page<Integral> page, Integral integral);

    List<Integral> getIntegralList(Page<Integral> page, Integral integral);
}
