package org.jeecg.modules.party_building.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.party_building.atom.AutoRecordIntegralAtom;
import org.jeecg.modules.party_building.entity.Integral;
import org.jeecg.modules.party_building.entity.IntegralDetail;
import org.jeecg.modules.party_building.enums.BooleanEnum;
import org.jeecg.modules.party_building.enums.IntegralRuleEnum;
import org.jeecg.modules.party_building.model.*;
import org.jeecg.modules.party_building.service.IIntegralDetailService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: integral_detail
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="积分详情")
@RestController
@RequestMapping("/com/integralDetail")
@Slf4j
public class IntegralDetailController extends JeecgController<IntegralDetail, IIntegralDetailService> {
	@Autowired
	private IIntegralDetailService integralDetailService;

	@Autowired
	private AutoRecordIntegralAtom autoRecordIntegralAtom;

	@Autowired
	private ISysUserService sysUserService;

	 @Autowired
	 private ISysDepartService departService;

	 /**
	  * 当天学习积分情况
	  * 按照规则编号分组汇总用户积分，时间当天
	  *
	  * @param userId
	  * @return
	  */
	 @AutoLog(value = "dailyUserIntegral-指定日期学习积分情况")
	 @ApiOperation(value = "dailyUserIntegral-指定日期学习积分情况", notes = "dailyUserIntegral-指定日期学习积分情况")
	 @GetMapping(value = "/dailyUserIntegral")
	 public Result<?> dailyUserIntegralGroupRuleNo(@RequestParam(name = "userId", required = true) String userId, String date) {
		 List<DailyUserIntegralModel> dailyUserIntegralModels = integralDetailService.dailyUserIntegralGroupRuleNo(userId, date);
		 DailyUserIntegralResponseModel dailyUserIntegralResponseModel = new DailyUserIntegralResponseModel();
		 dailyUserIntegralResponseModel.setDailySum(dailyUserIntegralModels.stream().mapToInt(DailyUserIntegralModel::getDayCurrIntegral).sum());
		 dailyUserIntegralResponseModel.setDailyUserIntegralModels(dailyUserIntegralModels);
		 return Result.ok(dailyUserIntegralResponseModel);
	 }

	 /**
	  * 按照日期汇总当前用户的积分(包括所有积分变动)
	  * @param userId
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @AutoLog(value = "userIntegralGroupDate-日期汇总当前用户的积分查询")
	 @ApiOperation(value = "userIntegralGroupDate-日期汇总当前用户的积分查询", notes = "userIntegralGroupDate-日期汇总当前用户的积分查询")
	 @GetMapping(value = "/userIntegralGroupDate")
	 public Result<?> userIntegralGroupToday(@RequestParam(name = "userId", required = true) String userId,
											@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
											@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		 Page<UserIntegralDate> page = new Page<UserIntegralDate>(pageNo, pageSize);
		 return Result.ok(integralDetailService.queryUserIntegralGroupDate(page, userId));
	 }

	 /**
	  * 时间段查询根据用户分组汇总积分排名
	  * @param integralDetailRank
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @AutoLog(value = "integralRankByDate-日期区间积分排名查询")
	 @ApiOperation(value = "integralRankByDate-日期区间积分排名查询", notes = "integralRankByDate-日期区间积分排名查询")
	 @GetMapping(value = "/integralRankByDate")
	 public Result<?> integralRankByDate(IntegralDetailRank integralDetailRank,
										 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
										 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		 // 判断是否是本周
		 if (BooleanEnum.TRUE.getValue().equals(integralDetailRank.getThisWeek())) {
			 Calendar cal = Calendar.getInstance();
			 // 今天
			 integralDetailRank.setEndDate(DateUtils.formatDate(cal, "yyyyMMdd"));
			 int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
			 cal.add(Calendar.DATE, -day_of_week);
			 // 本周第一天
			 integralDetailRank.setStartDate(DateUtils.formatDate(cal, "yyyyMMdd"));
		 }
		 Page<IntegralDetailRank> page = new Page<IntegralDetailRank>(pageNo, pageSize);
		 return Result.ok(integralDetailService.integralRankByDate(page, integralDetailRank));
	 }

	 /**
	  * 时间段查询根据用户分组汇总积分排名
	  * @param integralDetailRank
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @AutoLog(value = "integralRankDepartByDate-日期区间部门积分排名查询")
	 @ApiOperation(value = "integralRankDepartByDate-日期区间部门积分排名查询", notes = "integralRankDepartByDate-日期区间部门积分排名查询")
	 @GetMapping(value = "/integralRankDepartByDate")
	 public Result<?> integralRankDepartByDate(IntegralDetailRank integralDetailRank,
										 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
										 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		 // 判断是否是本周
		 if (BooleanEnum.TRUE.getValue().equals(integralDetailRank.getThisWeek())) {
			 Calendar cal = Calendar.getInstance();
			 // 今天
			 integralDetailRank.setEndDate(DateUtils.formatDate(cal, "yyyyMMdd"));
			 int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
			 cal.add(Calendar.DATE, -day_of_week);
			 // 本周第一天
			 integralDetailRank.setStartDate(DateUtils.formatDate(cal, "yyyyMMdd"));
		 }
		 Page<IntegralDetailRank> page = new Page<IntegralDetailRank>(pageNo, pageSize);
		 return Result.ok(integralDetailService.integralRankDepartByDate(page, integralDetailRank));
	 }


	 /**
	  * 当年月度积分
	  * @param userId
	  * @return
	  */
	 @AutoLog(value = "yearIntegral-当年月度积分")
	 @ApiOperation(value="yearIntegral-当年月度积分", notes="yearIntegral-当年月度积分")
	 @GetMapping(value = "/yearIntegral")
	 public Result<?> yearIntegral(String userId) {
		 return Result.ok(integralDetailService.yearIntegral(userId));
	 }

	/**
	 * 分页列表查询
	 *
	 * @param integralDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "integral_detail-分页列表查询")
	@ApiOperation(value="integral_detail-分页列表查询", notes="integral_detail-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IntegralDetail integralDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IntegralDetail> queryWrapper = QueryGenerator.initQueryWrapper(integralDetail, req.getParameterMap());
		Page<IntegralDetail> page = new Page<IntegralDetail>(pageNo, pageSize);
		IPage<IntegralDetail> pageList = integralDetailService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param integralDetail
	 * @return
	 */
	@AutoLog(value = "integral_detail-添加")
	@ApiOperation(value="integral_detail-添加", notes="integral_detail-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IntegralDetail integralDetail) {
		integralDetail.setRuleNo(IntegralRuleEnum.R999.getValue());
		autoRecordIntegralAtom.recordIntegralAndSum(integralDetail);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param integralDetail
	 * @return
	 */
	@AutoLog(value = "integral_detail-编辑")
	@ApiOperation(value="integral_detail-编辑", notes="integral_detail-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IntegralDetail integralDetail) {
		integralDetailService.updateById(integralDetail);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "integral_detail-通过id删除")
	@ApiOperation(value="integral_detail-通过id删除", notes="integral_detail-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		integralDetailService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "integral_detail-批量删除")
	@ApiOperation(value="integral_detail-批量删除", notes="integral_detail-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.integralDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "integral_detail-通过id查询")
	@ApiOperation(value="integral_detail-通过id查询", notes="integral_detail-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IntegralDetail integralDetail = integralDetailService.getById(id);
		if(integralDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(integralDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param integralDetail
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IntegralDetail integralDetail) {
        return super.exportXls(request, integralDetail, IntegralDetail.class, "integral_detail");
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
        return super.importExcel(request, response, IntegralDetail.class);
    }

}
