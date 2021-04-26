package org.jeecg.modules.party_building.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.party_building.entity.DataActivitySign;
import org.jeecg.modules.party_building.service.IDataActivitySignService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_activity_sign
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="活动签到")
@RestController
@RequestMapping("/com/dataActivitySign")
@Slf4j
public class DataActivitySignController extends JeecgController<DataActivitySign, IDataActivitySignService> {
	@Autowired
	private IDataActivitySignService dataActivitySignService;
	 @Autowired
	 private ISysUserService sysUserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dataActivitySign
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_activity_sign-分页列表查询")
	@ApiOperation(value="data_activity_sign-分页列表查询", notes="data_activity_sign-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataActivitySign dataActivitySign,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DataActivitySign> queryWrapper = QueryGenerator.initQueryWrapper(dataActivitySign, req.getParameterMap());
		Page<DataActivitySign> page = new Page<DataActivitySign>(pageNo, pageSize);
		IPage<DataActivitySign> pageList = dataActivitySignService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   活动签到
	 *
	 * @param dataActivitySign
	 * @return
	 */
	@AutoLog(value = "data_activity_sign-添加")
	@ApiOperation(value="data_activity_sign-添加", notes="data_activity_sign-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataActivitySign dataActivitySign) {
		if(dataActivitySign!=null){
			List<DataActivitySign> list = dataActivitySignService.list(new QueryWrapper<DataActivitySign>().eq("actity_Sign_Up_Id", dataActivitySign.getActitySignUpId()).eq("user_Id", dataActivitySign.getUserId()));
			if(list.size()<1){
				SysUser byId = sysUserService.getById(dataActivitySign.getUserId());
				dataActivitySign.setActitySignUpId(dataActivitySign.getActitySignUpId());
				dataActivitySign.setUserName(byId.getUsername());
				dataActivitySign.getUserId();
				dataActivitySign.setSignTime(new Date());
				dataActivitySignService.save(dataActivitySign);
			}
			if(list.size()==1){
				if(list.get(0).getSignTime()==null){
					dataActivitySign.setSignTime(new Date());
					dataActivitySignService.updateById(dataActivitySign);
				}else {
					return  Result.error("活动已签到");
				}
			}
		}

		return Result.ok("签到成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dataActivitySign
	 * @return
	 */
	@AutoLog(value = "data_activity_sign-编辑")
	@ApiOperation(value="data_activity_sign-编辑", notes="data_activity_sign-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataActivitySign dataActivitySign) {
		dataActivitySignService.updateById(dataActivitySign);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_activity_sign-通过id删除")
	@ApiOperation(value="data_activity_sign-通过id删除", notes="data_activity_sign-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataActivitySignService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_activity_sign-批量删除")
	@ApiOperation(value="data_activity_sign-批量删除", notes="data_activity_sign-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dataActivitySignService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_activity_sign-通过id查询")
	@ApiOperation(value="data_activity_sign-通过id查询", notes="data_activity_sign-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataActivitySign dataActivitySign = dataActivitySignService.getById(id);
		if(dataActivitySign==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataActivitySign);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataActivitySign
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataActivitySign dataActivitySign) {
        return super.exportXls(request, dataActivitySign, DataActivitySign.class, "data_activity_sign");
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
        return super.importExcel(request, response, DataActivitySign.class);
    }

}
