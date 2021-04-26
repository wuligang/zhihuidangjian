package org.jeecg.modules.party_building.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataInformation;
import org.jeecg.modules.party_building.vo.DataInformationVo;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_information
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataInformationMapper extends BaseMapper<DataInformation> {
    List<Map<String,Object>> queryCleanByCategoryId(@Param("categoryId")String categoryId,
                                                 @Param("articleStates")String articleStates, @Param("isLimit") String isLimit);

    /**
     *
     * @param categoryId 党建资讯分类id
     * @param articleStates 2 审核通过
     * @param start 分页开始
     * @param size  每页条数
     * @return
     */
    List<Map<String,Object>> listByCategoryId(@Param("categoryId")String categoryId, @Param("articleStates")String articleStates,
                                           @Param("start") Integer start, @Param("size") Integer size);

    IPage<DataInformationVo> selectPageVo(Page<DataInformation> page,  @Param(Constants.WRAPPER)QueryWrapper<DataInformation> queryWrapper);

    List<Map<String, Object>> selectInformationAndImagelist(Map<String, Object> map);

    int countinformationAndImagelist(Map<String, Object> map);

    int updateStatusByIds(@Param("asList") List<String> asList, @Param("status") String status,@Param("reason")String reason);

    List<Map<String, Object>> listRotationChartByMap(Map<String, Object> map);
}
