package org.jeecg.modules.party_building.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataManuscript;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_manuscript
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataManuscriptMapper extends BaseMapper<DataManuscript> {

//    List<DataManuscript> selectPageByCondition(@Param("queryCondition") String queryCondition, @Param("start") int i,
//                                               @Param("size") Integer pageSize);

//    int countPageByCondition(@Param("queryCondition")String queryCondition);

    int countDataManuscriptAndImagelist(Map<String, Object> map);

    List<Map<String, Object>> selectDataManuscriptAndImagelist(Map<String, Object> map);

    IPage<DataManuscript> selectPageByCondition(Page<DataManuscript> page, @Param("map") Map<String, Object> map);

//    IPage<DataManuscript> selectPageByCondition(Page<DataManuscript> page,@Param("queryCondition")String queryCondition);
}
