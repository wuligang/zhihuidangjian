package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataCollectSupport;

import java.util.List;

/**
 * @Description: data_collect_support
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataCollectSupportMapper extends BaseMapper<DataCollectSupport> {

    String selectTypeByUserIdAndInfoId(@Param("id") String id, @Param("userId") String userId,
                                                    @Param("type") String type);

}
