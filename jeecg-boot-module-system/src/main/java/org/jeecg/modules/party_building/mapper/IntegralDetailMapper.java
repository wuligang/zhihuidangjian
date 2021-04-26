package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.IntegralDetail;
import org.jeecg.modules.party_building.model.*;

import java.util.List;

/**
 * @Description: integral_detail
 * @Author: jeecg-boot
 * @Date: 2020-07-02
 * @Version: V1.0
 */
public interface IntegralDetailMapper extends BaseMapper<IntegralDetail> {

    /**
     * 获取当天是否观看过微课堂视频
     *
     * @param videoName
     * @return
     */
    int haveRecordToday(@Param("userId") String userId, @Param("ruleNo") String ruleNo, @Param("videoName") String videoName);

    /**
     * 获取当天指定规则的总积分
     *
     * @param userId
     * @param ruleNo
     * @return
     */
    int sumIntegralToday(@Param("userId") String userId, @Param("ruleNo") String ruleNo);

    /**
     * 当天学习积分情况
     * 按照规则编号分组汇总用户积分，时间某天
     * @param userId
     * @return
     */
    List<DailyUserIntegralModel> dailyUserIntegralGroupRuleNo(@Param("userId") String userId, @Param("date") String date);

    /**
     * 按照日期汇总当前用户的积分
     * @param page
     * @param userId
     * @return
     */
    IPage<UserIntegralDate> queryUserIntegralGroupDate(Page<UserIntegralDate> page, String userId);

    /**
     * 时间段查询根据用户分组汇总积分排名
     * @param page
     * @param integralDetailRank
     * @return
     */
    IPage<IntegralDetailRank> integralRankByDate(Page<IntegralDetailRank> page, IntegralDetailRank integralDetailRank);

    /**
     * 时间段查询根据用户分组汇总积分排名
     * @param page
     * @param integralDetailRank
     * @return
     */
    IPage<IntegralDetailRank> integralRankDepartByDate(Page<IntegralDetailRank> page, IntegralDetailRank integralDetailRank);

    /**
     * 查询当年月度积分
     * @param userId
     * @return
     */
    List<UserIntegralDate> yearIntegral(@Param("userId") String userId);

}
