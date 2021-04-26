package org.jeecg.modules.party_building.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.party_building.entity.DataCollectSupport;
import org.jeecg.modules.party_building.service.IDataCollectSupportService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_collect_support
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="收藏点赞")
@RestController
@RequestMapping("/com/dataCollectSupport")
@Slf4j
public class DataCollectSupportController extends JeecgController<DataCollectSupport, IDataCollectSupportService> {
	@Autowired
	private IDataCollectSupportService dataCollectSupportService;

	/**
	 * 分页列表查询
	 *
	 * @param dataCollectSupport
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_collect_support-分页列表查询")
	@ApiOperation(value="data_collect_support-分页列表查询", notes="data_collect_support-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataCollectSupport dataCollectSupport,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DataCollectSupport> queryWrapper = QueryGenerator.initQueryWrapper(dataCollectSupport, req.getParameterMap());
		Page<DataCollectSupport> page = new Page<DataCollectSupport>(pageNo, pageSize);
		IPage<DataCollectSupport> pageList = dataCollectSupportService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	 /**
	  * 回显所有
	  *
	  * @param dataCollectSupport
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "data_collect_support-回显所有")
	 @ApiOperation(value="data_collect_support-回显所有", notes="data_collect_support-回显所有")
	 @GetMapping(value = "/listAll")
	 public Result<?> listAll(DataCollectSupport dataCollectSupport,
							  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
							  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,HttpServletRequest req) {
		 QueryWrapper<DataCollectSupport> queryWrapper = QueryGenerator.initQueryWrapper(dataCollectSupport, req.getParameterMap());
		 List<DataCollectSupport> pageList = dataCollectSupportService.list(queryWrapper);
		 return Result.ok(pageList);
	 }

	/**
	 *   添加
	 *
	 * @param dataCollectSupport
	 * @return
	 */
	@AutoLog(value = "data_collect_support-添加")
	@ApiOperation(value="data_collect_support-添加", notes="data_collect_support-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataCollectSupport dataCollectSupport) {
		dataCollectSupportService.save(dataCollectSupport);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param dataCollectSupport
	 * @return
	 */
	@AutoLog(value = "data_collect_support-编辑")
	@ApiOperation(value="data_collect_support-编辑", notes="data_collect_support-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataCollectSupport dataCollectSupport) {
		dataCollectSupportService.updateById(dataCollectSupport);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_collect_support-通过id删除")
	@ApiOperation(value="data_collect_support-通过id删除", notes="data_collect_support-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataCollectSupportService.removeById(id);
		return Result.ok("删除成功!");
	}

	 /**
	  *
	  * @param infoId
	  * @param userId
	  * @return
	  */
	 @AutoLog(value = "data_collect_support-通过infoid、userId删除")
	 @ApiOperation(value="data_collect_support-通过infoid、userId删除", notes="data_collect_support-通过infoid、userId删除")
	 @DeleteMapping(value = "/deleteByInfoIdAndUserId")
	 public Result<?> deleteByInfoIdAndUserId(String infoId,String userId,String type) {
		 Map<String,Object> map=new HashMap<>();
		 map.put("info_id",infoId);
		 map.put("user_id",userId);
		 map.put("type",type);
		 dataCollectSupportService.removeByMap(map);
		 return Result.ok("删除成功!");
	 }


	 /**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_collect_support-批量删除")
	@ApiOperation(value="data_collect_support-批量删除", notes="data_collect_support-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dataCollectSupportService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_collect_support-通过id查询")
	@ApiOperation(value="data_collect_support-通过id查询", notes="data_collect_support-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataCollectSupport dataCollectSupport = dataCollectSupportService.getById(id);
		if(dataCollectSupport==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataCollectSupport);
	}
	 /**
	  * 通过info_id和user_id查询
	  *
	  * @param dataCollectSupport
	  * @return
	  */
	 @AutoLog(value = "data_collect_support-通过id查询")
	 @ApiOperation(value="data_collect_support-通过id查询", notes="data_collect_support-通过id查询")
	 @GetMapping(value = "/queryByDataCollectSupport")
	 public Result<?> queryByDataCollectSupport(DataCollectSupport dataCollectSupport,HttpServletRequest req) {
		 QueryWrapper<DataCollectSupport> queryWrapper = QueryGenerator.initQueryWrapper(dataCollectSupport, req.getParameterMap());
		 List<DataCollectSupport> list = dataCollectSupportService.list(queryWrapper);
		 if(list.isEmpty()) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.ok("已经收藏或者点赞");
	 }
    /**
    * 导出excel
    *
    * @param request
    * @param dataCollectSupport
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataCollectSupport dataCollectSupport) {
        return super.exportXls(request, dataCollectSupport, DataCollectSupport.class, "data_collect_support");
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
        return super.importExcel(request, response, DataCollectSupport.class);
    }

}
