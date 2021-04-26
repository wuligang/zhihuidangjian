package org.jeecg.modules.party_building.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.sf.saxon.lib.SaxonOutputKeys;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.party_building.entity.IntegralRule;
import org.jeecg.modules.party_building.service.IIntegralRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: integral_rule
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="积分规则")
@RestController
@RequestMapping("/com/integralRule")
@Slf4j
public class IntegralRuleController extends JeecgController<IntegralRule, IIntegralRuleService> {
	@Autowired
	private IIntegralRuleService integralRuleService;
	
	/**
	 * 分页列表查询
	 *
	 * @param integralRule
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "integral_rule-分页列表查询")
	@ApiOperation(value="integral_rule-分页列表查询", notes="integral_rule-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IntegralRule integralRule,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IntegralRule> queryWrapper = QueryGenerator.initQueryWrapper(integralRule, req.getParameterMap());
		Page<IntegralRule> page = new Page<IntegralRule>(pageNo, pageSize);
		IPage<IntegralRule> pageList = integralRuleService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param integralRule
	 * @return
	 */
	@AutoLog(value = "integral_rule-添加")
	@ApiOperation(value="integral_rule-添加", notes="integral_rule-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IntegralRule integralRule) {
		integralRuleService.save(integralRule);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param integralRule
	 * @return
	 */
	@AutoLog(value = "integral_rule-编辑")
	@ApiOperation(value="integral_rule-编辑", notes="integral_rule-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IntegralRule integralRule) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if (sysUser!=null||!"".equals(sysUser)){
			integralRule.setUpdateBys(sysUser.getRealname());
		}
		System.out.println(integralRule.getUpdateBys());
		integralRuleService.updateById(integralRule);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "integral_rule-通过id删除")
	@ApiOperation(value="integral_rule-通过id删除", notes="integral_rule-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		integralRuleService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "integral_rule-批量删除")
	@ApiOperation(value="integral_rule-批量删除", notes="integral_rule-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.integralRuleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "integral_rule-通过id查询")
	@ApiOperation(value="integral_rule-通过id查询", notes="integral_rule-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IntegralRule integralRule = integralRuleService.getById(id);
		if(integralRule==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(integralRule);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param integralRule
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IntegralRule integralRule) {
        return super.exportXls(request, integralRule, IntegralRule.class, "integral_rule");
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
        return super.importExcel(request, response, IntegralRule.class);
    }

}
