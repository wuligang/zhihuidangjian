package org.jeecg.modules.party_building.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.party_building.entity.DataCategory;
import org.jeecg.modules.party_building.entity.DataInformation;
import org.jeecg.modules.party_building.entity.DataManuscript;
import org.jeecg.modules.party_building.mapper.DataCategoryMapper;
import org.jeecg.modules.party_building.mapper.DataInformationMapper;
import org.jeecg.modules.party_building.mapper.DataManuscriptMapper;
import org.jeecg.modules.party_building.service.IDataCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 分类表（资讯，微课堂，活动）
 * @Author: jeecg-boot
 * @Date:   2020-07-01
 * @Version: V1.0
 */
@Service
public class DataCategoryServiceImpl extends ServiceImpl<DataCategoryMapper, DataCategory> implements IDataCategoryService {
	@Autowired
	private DataInformationMapper dataInformationMapper;
	@Autowired
	private DataManuscriptMapper dataManuscriptMapper;
	@Override
	public void addDataCategory(DataCategory dataCategory) {
		if(oConvertUtils.isEmpty(dataCategory.getPid())){
			dataCategory.setPid(IDataCategoryService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			DataCategory parent = baseMapper.selectById(dataCategory.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(dataCategory);
	}
	
	@Override
	public void updateDataCategory(DataCategory dataCategory) {
		DataCategory entity = this.getById(dataCategory.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = dataCategory.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				dataCategory.setPid(IDataCategoryService.ROOT_PID_VALUE);
			}
			if(!IDataCategoryService.ROOT_PID_VALUE.equals(dataCategory.getPid())) {
				baseMapper.updateTreeNodeStatus(dataCategory.getPid(), IDataCategoryService.HASCHILD);
			}
		}
		baseMapper.updateById(dataCategory);
	}
	
	@Override
	public Result deleteDataCategory(String id) throws JeecgBootException {
		DataCategory dataCategory = this.getById(id);
		if(dataCategory==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		List<DataInformation> category_id = dataInformationMapper.selectList(new QueryWrapper<DataInformation>().eq("category_Id", id));
		if(category_id.size()>0){
			return Result.error("此节点关联的有数据，不能删除");
		}
		List<DataManuscript> DataManuscriptList = dataManuscriptMapper.selectList(new QueryWrapper<DataManuscript>().eq("category_Id", id));
		if(DataManuscriptList.size()>0){
			return Result.error("此节点关联的有稿件数据，不能删除");
		}
		updateOldParentNode(dataCategory.getPid());
		baseMapper.deleteById(id);
		return Result.ok("删除成功");
	}

	/**
	 * 根据pid name查找
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String,Object>> listByPid(Map<String, Object> map) {
		return baseMapper.listByPid(map);
	}


	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IDataCategoryService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<DataCategory>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IDataCategoryService.NOCHILD);
			}
		}
	}

}
