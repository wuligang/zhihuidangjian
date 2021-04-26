package org.jeecg.modules.party_building.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.DataCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;

import java.util.List;
import java.util.Map;

/**
 * @Description: 分类表（资讯，微课堂，活动）
 * @Author: jeecg-boot
 * @Date: 2020-07-01
 * @Version: V1.0
 */
public interface IDataCategoryService extends IService<DataCategory> {

    /**
     * 根节点父ID的值
     */
    public static final String ROOT_PID_VALUE = "0";

    /**
     * 树节点有子节点状态值
     */
    public static final String HASCHILD = "1";

    /**
     * 树节点无子节点状态值
     */
    public static final String NOCHILD = "0";

    /**
     * 新增节点
     */
    void addDataCategory(DataCategory dataCategory);

    /**
     * 修改节点
     */
    void updateDataCategory(DataCategory dataCategory) throws JeecgBootException;

    /**
     * 删除节点
     */

    Result<?> deleteDataCategory(String id);
    /**
     * 根据pid name查找
     */
    List<Map<String,Object>> listByPid(Map<String, Object> map);


}
