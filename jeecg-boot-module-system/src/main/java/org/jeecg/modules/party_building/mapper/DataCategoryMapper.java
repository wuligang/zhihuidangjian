package org.jeecg.modules.party_building.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: 分类表（资讯，微课堂，活动）
 * @Author: jeecg-boot
 * @Date:   2020-07-01
 * @Version: V1.0
 */
public interface DataCategoryMapper extends BaseMapper<DataCategory> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

	/**
	 * 根据pid name查询
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> listByPid(Map<String, Object> map);
}
