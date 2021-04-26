package org.jeecg.modules.party_building.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.party_building.entity.DataAssessment;
import org.jeecg.modules.party_building.qo.DataAssessmentQo;
import org.jeecg.modules.party_building.service.IDataAssessmentService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.party_building.vo.DataAssessmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_assessment
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="考核管理")
@RestController
@RequestMapping("/com/dataAssessment")
@Slf4j
public class DataAssessmentController extends JeecgController<DataAssessment, IDataAssessmentService> {
	@Autowired
	private IDataAssessmentService dataAssessmentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dataAssessment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_assessment-分页列表查询")
	@ApiOperation(value="data_assessment-分页列表查询", notes="data_assessment-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataAssessment dataAssessment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DataAssessment> queryWrapper = QueryGenerator.initQueryWrapper(dataAssessment, req.getParameterMap());
		Page<DataAssessment> page = new Page<DataAssessment>(pageNo, pageSize);
		IPage<DataAssessment> pageList = dataAssessmentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dataAssessment
	 * @return
	 */
	@AutoLog(value = "data_assessment-添加")
	@ApiOperation(value="data_assessment-添加", notes="data_assessment-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataAssessment dataAssessment) {
 		return dataAssessmentService.add(dataAssessment);
	}
	
	/**
	 *  编辑
	 *
	 * @param dataAssessment
	 * @return
	 */
	@AutoLog(value = "data_assessment-编辑")
	@ApiOperation(value="data_assessment-编辑", notes="data_assessment-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataAssessment dataAssessment) {
		List<DataAssessment> assess_content = dataAssessmentService.list(new QueryWrapper<DataAssessment>().eq("assess_Content", dataAssessment.getAssessContent()));
		if(assess_content.size()>0){
			for (DataAssessment d: assess_content
				 ) {
				if(d.getId().equals(dataAssessment.getId())){
					dataAssessmentService.updateById(dataAssessment);
					return Result.ok("编辑成功!");
				}
			}
			return Result.error("数据中已有此考核内容信息!");
		}
		dataAssessmentService.updateById(dataAssessment);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_assessment-通过id删除")
	@ApiOperation(value="data_assessment-通过id删除", notes="data_assessment-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		return dataAssessmentService.delete(id);
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_assessment-批量删除")
	@ApiOperation(value="data_assessment-批量删除", notes="data_assessment-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		return dataAssessmentService.deleteBatchIds(ids);
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_assessment-通过id查询")
	@ApiOperation(value="data_assessment-通过id查询", notes="data_assessment-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataAssessment dataAssessment = dataAssessmentService.getById(id);
		if(dataAssessment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataAssessment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataAssessment
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataAssessment dataAssessment) {
        return super.exportXls(request, dataAssessment, DataAssessment.class, "data_assessment");
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
        return super.importExcel(request, response, DataAssessment.class);
    }
    /**
	 *  考核自评查询
	 * */
	@AutoLog(value = "data_assessment-分页列表查询")
	@ApiOperation(value="data_assessment-分页列表查询", notes="data_assessment-分页列表查询")
	@GetMapping(value = "/assessMentlist")
	public Result<?> assessMentlist(DataAssessment dataAssessment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<DataAssessment> page = new Page<DataAssessment>(pageNo, pageSize);
		IPage<DataAssessment> pageList = dataAssessmentService.assessMentlist(page,dataAssessment);
		return Result.ok(pageList);
	}

	 /**
	  *  查看我的考核
	  * */
	 @AutoLog(value = "data_assessment-分页列表查询")
	 @ApiOperation(value="data_assessment-分页列表查询", notes="data_assessment-分页列表查询")
	 @GetMapping(value = "/mykaohelist")
	 public Result<?> mykaohelist(String status,@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,String userid,String assessmentId,
								  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								  HttpServletRequest req) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if(sysUser==null){
			 sysUser.setId(userid);
		 }
		 Page<DataAssessmentVo> page = new Page<DataAssessmentVo>(pageNo, pageSize);
		 IPage<DataAssessmentVo> pageList = dataAssessmentService.mykaohelist(page,sysUser.getId(),status,assessmentId);
		 return Result.ok(pageList);
	 }

	 /**
	  *  批量发布
	  * */
	 @RequestMapping("updateIds")
	 public Result updateIds(@RequestBody DataAssessmentQo dataAssessmentQo){
		return dataAssessmentService.updateIds(dataAssessmentQo.getIds(),dataAssessmentQo.getIssue());
	 }

	 /**
	  *  对已发布的考核进行撤回变成未发布状态
	  * */
	 @RequestMapping("recallIds")
	 public Result recallIds(@RequestParam(name="id",required=true) String id){
		 return dataAssessmentService.recallIds(id);
	 }

}
