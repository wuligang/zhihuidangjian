package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.DataManuscript;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_manuscript
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IDataManuscriptService extends IService<DataManuscript> {

//    List<DataManuscript> selectPageByCondition(String queryCondition, Integer pageNo, Integer pageSize);
//
//    int countPageByCondition(String queryCondition);

    int countDataManuscriptAndImagelist(Map<String, Object> map);

    List<Map<String, Object>> selectDataManuscriptAndImagelist(Map<String, Object> map);

    IPage<DataManuscript> selectPageByCondition(Page<DataManuscript> page, Map<String, Object> map);

    /**
     * 根据条件模糊查询稿件
     * @param queryCondition
     * @return
     */
//    IPage<DataManuscript> selectPageByCondition(Page<DataManuscript> page, String queryCondition);
}
