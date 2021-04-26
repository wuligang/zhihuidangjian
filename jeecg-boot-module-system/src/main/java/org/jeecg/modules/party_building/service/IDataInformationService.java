package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
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
public interface IDataInformationService extends IService<DataInformation> {

    List<Map<String,Object>> queryCleanByCategoryId(String categoryId, String articleStates, String isLimit);

    /**
     * 党建资讯按照分类id查询
     * @param categoryId 分类关联id
     * @param articleStates 状态
     * @param start 分页开始
     * @param size 每页条数
     * @return
     */
    List<Map<String,Object>> listByCategoryId(String categoryId,String articleStates, Integer start, Integer size);

    List<Map<String, Object>> selectInformationAndImagelist(Map<String, Object> map);

    int countinformationAndImagelist(Map<String, Object> map);

    IPage<DataInformationVo> pageList(Page<DataInformation> page, QueryWrapper<DataInformation> queryWrapper);

    Result updateStatusByIds(List<String> asList, String status, String reason);

    List<Map<String, Object>> listRotationChartByMap(Map<String, Object> map);
}
