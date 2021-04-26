package org.jeecg.modules.party_building.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.party_building.entity.MallExchangeHis;
import org.jeecg.modules.party_building.model.MallExchHisRequestModel;
import org.jeecg.modules.party_building.model.MallExchHisResponseModel;
import org.jeecg.modules.party_building.service.IMallExchangeHisService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: mall_exchange_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="商品兑换历史")
@RestController
@RequestMapping("/com/mallExchangeHis")
@Slf4j
public class MallExchangeHisController extends JeecgController<MallExchangeHis, IMallExchangeHisService> {
	@Autowired
	private IMallExchangeHisService mallExchangeHisService;

	 /**
	  * 兑换历史查询
	  *
	  * @param mallExchHisRequestModel
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @AutoLog(value = "mall_exchange_his_details-兑换历史查询")
	 @ApiOperation(value="mall_exchange_his_details-兑换历史查询", notes="mall_exchange_his_details-兑换历史查询")
	 @GetMapping(value = "/exchangeHisList")
	public Result<?> queryExchangeHisList(MallExchHisRequestModel mallExchHisRequestModel,
										  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize){
		Page<MallExchHisResponseModel> page = new Page<MallExchHisResponseModel>(pageNo, pageSize);
		IPage<MallExchHisResponseModel> pageList = mallExchangeHisService.queryExchangeHisList(page, mallExchHisRequestModel);
		return Result.ok(pageList);
	}

	/**
	 * 分页列表查询
	 *
	 * @param mallExchangeHis
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "mall_exchange_his-分页列表查询")
	@ApiOperation(value="mall_exchange_his-分页列表查询", notes="mall_exchange_his-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MallExchangeHis mallExchangeHis,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MallExchangeHis> queryWrapper = QueryGenerator.initQueryWrapper(mallExchangeHis, req.getParameterMap());
		Page<MallExchangeHis> page = new Page<MallExchangeHis>(pageNo, pageSize);
		IPage<MallExchangeHis> pageList = mallExchangeHisService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param mallExchangeHis
	 * @return
	 */
	@AutoLog(value = "mall_exchange_his-添加")
	@ApiOperation(value="mall_exchange_his-添加", notes="mall_exchange_his-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MallExchangeHis mallExchangeHis) {
		mallExchangeHisService.save(mallExchangeHis);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param mallExchangeHis
	 * @return
	 */
	@AutoLog(value = "mall_exchange_his-编辑")
	@ApiOperation(value="mall_exchange_his-编辑", notes="mall_exchange_his-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MallExchangeHis mallExchangeHis) {
		mallExchangeHisService.updateById(mallExchangeHis);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "mall_exchange_his-通过id删除")
	@ApiOperation(value="mall_exchange_his-通过id删除", notes="mall_exchange_his-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mallExchangeHisService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "mall_exchange_his-批量删除")
	@ApiOperation(value="mall_exchange_his-批量删除", notes="mall_exchange_his-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mallExchangeHisService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "mall_exchange_his-通过id查询")
	@ApiOperation(value="mall_exchange_his-通过id查询", notes="mall_exchange_his-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MallExchangeHis mallExchangeHis = mallExchangeHisService.getById(id);
		if(mallExchangeHis==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mallExchangeHis);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mallExchangeHis
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MallExchangeHis mallExchangeHis) {
        return super.exportXls(request, mallExchangeHis, MallExchangeHis.class, "mall_exchange_his");
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
        return super.importExcel(request, response, MallExchangeHis.class);
    }

}
