package org.jeecg.modules.party_building.controller;

import java.util.Arrays;
import java.util.Date;
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
import org.jeecg.modules.party_building.entity.DataAssessmentMember;
import org.jeecg.modules.party_building.qo.DataAssessmentMemberQo;
import org.jeecg.modules.party_building.service.IDataAssessmentMemberService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.party_building.service.IDataAssessmentService;
import org.jeecg.modules.party_building.vo.DataAssessmentMemberVo;
import org.jeecg.modules.party_building.vo.DataAssessmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_assessment_member
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="考核填报")
@RestController
@RequestMapping("/com/dataAssessmentMember")
@Slf4j
public class DataAssessmentMemberController extends JeecgController<DataAssessmentMember, IDataAssessmentMemberService> {
	@Autowired
	private IDataAssessmentMemberService dataAssessmentMemberService;
	 @Autowired
	 private IDataAssessmentService dataAssessmentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dataAssessmentMember
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_assessment_member-分页列表查询")
	@ApiOperation(value="data_assessment_member-分页列表查询", notes="data_assessment_member-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataAssessmentMember dataAssessmentMember,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<DataAssessmentMemberVo> page = new Page<DataAssessmentMemberVo>(pageNo, pageSize);
		IPage<DataAssessmentMemberVo> pageList = dataAssessmentMemberService.pageList(page,dataAssessmentMember);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dataAssessmentMember
	 * @return
	 */
	@AutoLog(value = "data_assessment_member-添加")
	@ApiOperation(value="data_assessment_member-添加", notes="data_assessment_member-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataAssessmentMember dataAssessmentMember) {
		Result<?> result=new Result<>();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		List<DataAssessmentMember> list = dataAssessmentMemberService.list(new QueryWrapper<DataAssessmentMember>().eq("assessment_User_Id", sysUser.getId()).eq("assessment_Id", dataAssessmentMember.getAssessmentId()));
		if(list.size()<1){
			dataAssessmentMember.setStatus("1");
			dataAssessmentMemberService.save(dataAssessmentMember);
			result.success("添加成功");
		}else {
			result.error500("已经添加过");
		}

		return result;
	}
	
	/**
	 *  考核填写
	 *
	 * @param dataAssessmentMember
	 * @return
	 */
	@AutoLog(value = "data_assessment_member-编辑")
	@ApiOperation(value="data_assessment_member-编辑", notes="data_assessment_member-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataAssessmentMember dataAssessmentMember) {
		//自评状态 0：未考核 1：待审核 2：审核通过 3：驳回
		Result<?> result=new Result<>();
		DataAssessment byId = dataAssessmentService.getById(dataAssessmentMember.getAssessmentId());
		if(byId.getStartTime().getTime() > new  Date().getTime()){
			return result.error500("还不到考核时间");
		}
		if(byId.getEndTime().getTime() < new Date().getTime()){
			return result.error500("此次考核已结束");
		}
		if(dataAssessmentMember.getAssessmentUserId()==null){
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			DataAssessmentMember assessmentUserId = dataAssessmentMemberService.getOne(new QueryWrapper<DataAssessmentMember>().eq("assessment_User_Id", sysUser.getId()).eq("assessment_Id",dataAssessmentMember.getAssessmentId()));
			if(assessmentUserId.getStatus().equals("0")){
				dataAssessmentMember.setStatus("1");
				dataAssessmentMemberService.updateById(dataAssessmentMember);
			}
			if(assessmentUserId.getStatus().equals("3")){
				dataAssessmentMember.setId(assessmentUserId.getId());
				dataAssessmentMember.setStatus("1");
				dataAssessmentMember.setReason(null);
				dataAssessmentMemberService.updateById(dataAssessmentMember);
			}
			if(assessmentUserId.getStatus().equals("1")){
				result.setMessage("已考核");
			}
			if(assessmentUserId.getStatus().equals("2")){
				result.setMessage("已考核");
			}
		}else {
			DataAssessmentMember assessmentUserId = dataAssessmentMemberService.getOne(new QueryWrapper<DataAssessmentMember>().eq("assessment_User_Id", dataAssessmentMember.getAssessmentUserId()).eq("assessment_Id",dataAssessmentMember.getAssessmentId()));
			if(assessmentUserId.getStatus().equals("0")){
				dataAssessmentMember.setStatus("1");
				dataAssessmentMemberService.updateById(dataAssessmentMember);
			}
			if(assessmentUserId.getStatus().equals("3")){
				dataAssessmentMember.setId(assessmentUserId.getId());
				dataAssessmentMember.setStatus("1");
				dataAssessmentMember.setReason(null);
				dataAssessmentMemberService.updateById(dataAssessmentMember);
			}
			if(assessmentUserId.getStatus().equals("1")){
				result.setMessage("已考核");
			}
			if(assessmentUserId.getStatus().equals("2")){
				result.setMessage("已考核");
			}
		}
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_assessment_member-通过id删除")
	@ApiOperation(value="data_assessment_member-通过id删除", notes="data_assessment_member-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataAssessmentMemberService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_assessment_member-批量删除")
	@ApiOperation(value="data_assessment_member-批量删除", notes="data_assessment_member-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dataAssessmentMemberService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_assessment_member-通过id查询")
	@ApiOperation(value="data_assessment_member-通过id查询", notes="data_assessment_member-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataAssessmentMember dataAssessmentMember = dataAssessmentMemberService.getById(id);
		if(dataAssessmentMember==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataAssessmentMember);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataAssessmentMember
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataAssessmentMember dataAssessmentMember) {
        return super.exportXls(request, dataAssessmentMember, DataAssessmentMember.class, "data_assessment_member");
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
        return super.importExcel(request, response, DataAssessmentMember.class);
    }

	 /**
	  *  考核审批页面展示
	  * */
	 @AutoLog(value = "data_assessment-分页列表查询")
	 @ApiOperation(value="data_assessment-分页列表查询", notes="data_assessment-分页列表查询")
	 @GetMapping(value = "/shenpilist")
	 public Result<?> shenpilist(DataAssessmentMember dataAssessmentMember,String content,
								 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								 HttpServletRequest req) {
		 Page<DataAssessmentMemberVo> page = new Page<DataAssessmentMemberVo>(pageNo, pageSize);
		   IPage<DataAssessmentMemberVo> pageList = dataAssessmentMemberService.shenpilist(page, dataAssessmentMember,content);
		 return Result.ok(pageList);
	 }

	  /**
	   *  考核审核编辑 ||批量
	   * */
	  @AutoLog(value = "data_assessment-分页列表查询")
	  @ApiOperation(value="data_assessment-分页列表查询", notes="data_assessment-分页列表查询")
	  @PutMapping(value = "/shenpiUpdate")
	  public Result<?> shenpiUpdate(@RequestBody DataAssessmentMemberQo dataAssessmentMemberQo) {
		  return  dataAssessmentMemberService.shenpiUpdate(dataAssessmentMemberQo.getIds(),dataAssessmentMemberQo.getStatus(),dataAssessmentMemberQo.getReason());
	  }

}
