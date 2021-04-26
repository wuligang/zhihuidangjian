package org.jeecg.modules.party_building.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataSmallClass;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_small_class
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataSmallClassMapper extends BaseMapper<DataSmallClass> {

    List<DataSmallClass> querySmallClassByCategoryId(@Param("categoryId") String categoryId, @Param("classStates") String classStates);

    List<Map<String, Object>> getSmallClassByMap(Map<String, Object> map);

    List<Map<String, Object>> selectSmallClassAppPageByCondition(Map<String, Object> map);

    int countSmallClassAppPageByCondition(Map<String, Object> map);
}


