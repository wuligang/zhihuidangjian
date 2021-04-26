package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.DataSmallClass;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_small_class
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IDataSmallClassService extends IService<DataSmallClass> {
    /**
     * 根据categoryId、classStates查询微课堂
     * @param categoryId
     * @param classStates
     * @return
     */
    List<DataSmallClass> querySmallClassByCategoryId(String categoryId, String classStates);

    List<Map<String, Object>> getSmallClassByMap(Map<String, Object> map);

    List<Map<String, Object>> selectSmallClassAppPageByCondition(Map<String, Object> map);

    int countSmallClassAppPageByCondition(Map<String, Object> map);
}
