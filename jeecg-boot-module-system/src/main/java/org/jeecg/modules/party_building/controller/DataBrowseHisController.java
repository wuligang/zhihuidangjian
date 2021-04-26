package org.jeecg.modules.party_building.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.party_building.entity.DataBrowseHis;
import org.jeecg.modules.party_building.entity.DataCollectSupport;
import org.jeecg.modules.party_building.entity.DataManuscript;
import org.jeecg.modules.party_building.service.IDataBrowseHisService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.party_building.entity.DataBrowseHis;
import org.jeecg.modules.party_building.vo.DataBrowseHisGroupByTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_browse_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="浏览历史")
@RestController
@RequestMapping("/com/dataBrowseHis")
@Slf4j
public class DataBrowseHisController extends JeecgController<DataBrowseHis, IDataBrowseHisService> {
	@Autowired
	private IDataBrowseHisService dataBrowseHisService;

	/**
	 * 分页列表查询
	 *
	 * @param dataBrowseHis
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_browse_his-分页列表查询")
	@ApiOperation(value="data_browse_his-分页列表查询", notes="data_browse_his-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataBrowseHis dataBrowseHis,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DataBrowseHis> queryWrapper = QueryGenerator.initQueryWrapper(dataBrowseHis, req.getParameterMap());
		Page<DataBrowseHis> page = new Page<DataBrowseHis>(pageNo, pageSize);
		IPage<DataBrowseHis> pageList = dataBrowseHisService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	 /**
	  * 回显指定日期所有
	  *
	  * @param dataBrowseHis
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "data_browse_his-回显指定日期所有")
	 @ApiOperation(value="data_browse_his-回显指定日期所有", notes="data_browse_his-回显指定日期所有")
	 @GetMapping(value = "/listAll")
	 public Result<?> listAllByDate(DataBrowseHis dataBrowseHis,String byBrowseTime,
							  HttpServletRequest req) {
		 List<DataBrowseHis> pageList = null;
		 try {
			 pageList = dataBrowseHisService.listAll(dataBrowseHis.getUserId(),byBrowseTime);
		 } catch (InvocationTargetException e) {
			 e.printStackTrace();
			 return Result.error("查询失败");
		 } catch (IllegalAccessException e) {
			 e.printStackTrace();
		 }
		 return Result.ok(pageList);
	 }
	 /**
	  * 回显指定日期分页
	  *
	  * @param dataBrowseHis
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "data_browse_his-回显指定日期分页")
	 @ApiOperation(value="data_browse_his-回显指定日期分页", notes="data_browse_his-回显指定日期分页")
	 @GetMapping(value = "/listPageByDate")
	 public Result<?> listPageByDate(DataBrowseHis dataBrowseHis,String byBrowseTime,
									 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
	 	Page<DataBrowseHis> page = new Page<>(pageNo, pageSize);
		 IPage<DataBrowseHis>  pageList  = dataBrowseHisService.listPageByDate(page,dataBrowseHis.getUserId(),byBrowseTime);

		 return Result.ok(pageList);
	 }

	 @AutoLog(value = "data_browse_his-首页党建学习浏览数统计")
	 @ApiOperation(value = "data_browse_his-首页党建学习浏览数统计", notes = "data_browse_his-首页党建学习浏览数统计")
	 @GetMapping(value = "/browseHisCountByUser")
	 public Result<?> browseHisCountByUser(String userId) {
		 QueryWrapper<DataBrowseHis> queryWrapper =new QueryWrapper<>();
		 queryWrapper.eq("user_id",userId);
		 queryWrapper.eq("module","2");
		 int count = dataBrowseHisService.count(queryWrapper);
		 return Result.ok(count);
	 }
	/**
	 *   添加
	 *
	 * @param dataBrowseHis
	 * @return
	 */
	@AutoLog(value = "data_browse_his-添加")
	@ApiOperation(value="data_browse_his-添加", notes="data_browse_his-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataBrowseHis dataBrowseHis) {
		dataBrowseHisService.save(dataBrowseHis);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param dataBrowseHis
	 * @return
	 */
	@AutoLog(value = "data_browse_his-编辑")
	@ApiOperation(value="data_browse_his-编辑", notes="data_browse_his-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataBrowseHis dataBrowseHis) {
		dataBrowseHisService.updateById(dataBrowseHis);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_browse_his-通过id删除")
	@ApiOperation(value="data_browse_his-通过id删除", notes="data_browse_his-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataBrowseHisService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_browse_his-批量删除")
	@ApiOperation(value="data_browse_his-批量删除", notes="data_browse_his-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids,String browseTime) {
		this.dataBrowseHisService.remove(new QueryWrapper<DataBrowseHis>().in("info_id",Arrays.asList(ids.split(","))).eq("Date(browse_time)",browseTime));
//		this.dataBrowseHisService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_browse_his-通过id查询")
	@ApiOperation(value="data_browse_his-通过id查询", notes="data_browse_his-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataBrowseHis dataBrowseHis = dataBrowseHisService.getById(id);
		if(dataBrowseHis==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataBrowseHis);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataBrowseHis
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataBrowseHis dataBrowseHis) {
        return super.exportXls(request, dataBrowseHis, DataBrowseHis.class, "data_browse_his");
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
        return super.importExcel(request, response, DataBrowseHis.class);
    }

}
