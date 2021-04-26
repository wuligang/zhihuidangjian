package org.jeecg.modules.party_building.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.party_building.entity.DataActivity;
import org.jeecg.modules.party_building.entity.DataCategory;
import org.jeecg.modules.party_building.qo.DataActivityQo;
import org.jeecg.modules.party_building.service.IDataActivityService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.party_building.service.IDataCategoryService;
import org.jeecg.modules.party_building.vo.DataActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_activity
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="党建活动管理")
@RestController
@RequestMapping("/com/dataActivity")
@Slf4j
public class DataActivityController extends JeecgController<DataActivity, IDataActivityService> {
	@Autowired
	private IDataActivityService dataActivityService;

	 @Autowired
	 private IDataCategoryService dataCategoryService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dataActivity
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_activity-分页列表查询")
	@ApiOperation(value="data_activity-分页列表查询", notes="data_activity-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataActivity dataActivity,String time,String userid,String shenpihuodong,String bohui,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
	// 	QueryWrapper<DataActivity> queryWrapper = QueryGenerator.initQueryWrapper(dataActivity, req.getParameterMap());
		Page<DataActivityVo> page = new Page<DataActivityVo>(pageNo, pageSize);
		IPage<DataActivityVo> pageList = dataActivityService.pageList(page,dataActivity,time,userid,shenpihuodong,bohui);
		return Result.ok(pageList);
	}

	 @AutoLog(value = "data_activity-分页列表查询更多")
	 @ApiOperation(value="data_activity-分页列表查询更多", notes="data_activity-分页列表查询更多")
	 @GetMapping(value = "/listMore")
	 public Result<?> queryPageListMore(DataActivity dataActivity,String time,String userid,String shenpihuodong,String bohui,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 // 	QueryWrapper<DataActivity> queryWrapper = QueryGenerator.initQueryWrapper(dataActivity, req.getParameterMap());
		 Page<DataActivityVo> page = new Page<DataActivityVo>(pageNo, pageSize);
		 IPage<DataActivityVo> pageList = dataActivityService.pageList(page,dataActivity,time,userid,shenpihuodong,bohui);

		 Map<String,Object> map=new HashMap<>();
		 map.put("current",pageList.getCurrent());
		 map.put("pages",pageList.getPages());
		 map.put("searchCount",true);
		 map.put("size",pageList.getSize());
		 map.put("total",pageList.getTotal());
		 map.put("records",pageList.getRecords());
		 map.put("name","党建活动");

		 return Result.ok(map);
	 }

	 /**
	  * 列表查询前4条
	  * @return
	  */
	 @AutoLog(value = "data_activity列表查询前4条")
	 @ApiOperation(value="data_activity列表查询前4条", notes="data_activity列表查询前四条")
	 @GetMapping(value = "/listTop")
	 public Result<?> queryList() {
		int start=0,end=4;
		 List<Map<String,Object>>  dataActivityList = dataActivityService.getTop(start,end);
		 if(dataActivityList==null) {
			 return Result.error("未找到对应数据");
		 }
		 Map<String,Object> map=new HashMap<>();
		 map.put("name","党建活动");
		 map.put("records",dataActivityList);
		 return Result.ok(map);
	 }
	
	/**
	 *   添加
	 *
	 * @param dataActivity
	 * @return
	 */
	@AutoLog(value = "data_activity-添加")
	@ApiOperation(value="data_activity-添加", notes="data_activity-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataActivity dataActivity) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if (sysUser!=null||!"".equals(sysUser)){
			dataActivity.setCreateBy(sysUser.getRealname());
		}
		return dataActivityService.add(dataActivity);
	}
	
	/**
	 *  编辑
	 *
	 * @param dataActivity
	 * @return
	 */
	@AutoLog(value = "data_activity-编辑")
	@ApiOperation(value="data_activity-编辑", notes="data_activity-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataActivity dataActivity) {
		List<DataActivity> name = dataActivityService.list(new QueryWrapper<DataActivity>().eq("name", dataActivity.getName()));
		if(name.size()>0){
			if(!dataActivity.getId().equals(name.get(0).getId())){
				return Result.ok("已有此活动名称的数据!");
			}
		}
		DataActivity byId = dataActivityService.getById(dataActivity.getId());
		if(byId.getActiveStatus().equals("3")){
			dataActivity.setActiveStatus("0");
		}
		if(byId.getActiveStatus().equals("4")){
			dataActivity.setActiveStatus("0");
		}
		dataActivityService.updateById(dataActivity);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_activity-通过id删除")
	@ApiOperation(value="data_activity-通过id删除", notes="data_activity-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataActivityService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_activity-批量删除")
	@ApiOperation(value="data_activity-批量删除", notes="data_activity-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		return dataActivityService.deleteIds(ids);
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_activity-通过id查询")
	@ApiOperation(value="data_activity-通过id查询", notes="data_activity-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataActivity dataActivity = dataActivityService.getById(id);
		if(dataActivity==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataActivity);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataActivity
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataActivity dataActivity) {
        return super.exportXls(request, dataActivity, DataActivity.class, "data_activity");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DataActivity.class);
    }
    /**
	 *  批量审批
	 * */
	@AutoLog(value = "data_activity-批量审批")
	@ApiOperation(value="data_activity-批量审批", notes="data_activity-批量审批")
	@PutMapping(value = "/editIds")
	public Result<?> editIds(@RequestBody DataActivityQo dataActivityQo) {
		return dataActivityService.updateByIds(dataActivityQo.getIds(),dataActivityQo.getActiveStatus(),dataActivityQo.getReason());
	}
}
