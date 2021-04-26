package org.jeecg.modules.party_building.controller;

import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.party_building.atom.AutoRecordIntegralAtom;
import org.jeecg.modules.party_building.entity.*;
import org.jeecg.modules.party_building.enums.BooleanEnum;
import org.jeecg.modules.party_building.enums.ProductStatusEnum;
import org.jeecg.modules.party_building.service.IIntegralService;
import org.jeecg.modules.party_building.service.IMallProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: mall_product
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="商品")
@RestController
@RequestMapping("/com/mallProduct")
@Slf4j
public class MallProductController extends JeecgController<MallProduct, IMallProductService> {

	 @Autowired
	 private IMallProductService mallProductService;

	 @Autowired
	 private IIntegralService integralService;

	 @Autowired
	 private AutoRecordIntegralAtom autoRecordIntegralAtom;


	 @AutoLog(value = "exchange_product-商品兑换")
	 @ApiOperation(value = "exchange_product-商品兑换", notes = "exchange_product-商品兑换")
	 @PostMapping(value = "/exchangeProd")
	 public Result<?> exchangeProd(@RequestParam(name = "proId", required = true) String proId, @RequestParam(name = "userId", required = true) String userId) {
		 MallProduct mallProduct = mallProductService.getValidProdById(proId);
		 if (Objects.isNull(mallProduct)) {
			 return Result.error("商品不存在或状态无效!");
		 }
		 if (mallProduct.getStock() <= 0) {
			 return Result.error("商品库存不足，请联系管理员!");
		 }
		 // 获取用户当前积分
		 Integral userIntegral = integralService.getIntegralByUserId(userId);
		 if (Objects.isNull(userIntegral)) {
			 return Result.error("不存在积分!");
		 }
		 // 当前积分与商品积分比较
		 if (userIntegral.getCurrentIntegral().compareTo(mallProduct.getProIntegral()) == -1) {
			 return Result.error("积分不足,当前可用积分" + userIntegral.getCurrentIntegral());
		 }
		 // 兑换后判断商品库存，若为0，自动下架，更新商品，记录商品兑换历史，记录积分变动流水，更新总积分
		 autoRecordIntegralAtom.exchangeProdAndRecordIntegral(userId, mallProduct);
		 return Result.ok();
	 }


	 @AutoLog(value = "can_exchange_product-可兑换商品")
	 @ApiOperation(value = "can_exchange_product-可兑换商品", notes = "can_exchange_product-可兑换商品")
	 @GetMapping(value = "/canExchangeProd")
	 public Result<?> canExchangeProd(@RequestParam(name = "userId", required = true) String userId,
									  MallProduct mallProduct,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		 Integral integral = integralService.getIntegralByUserId(userId);
		 if (Objects.isNull(integral)) {
			 return Result.error("不存在积分!");
		 }
		 mallProduct.setStatus(ProductStatusEnum.ON_SALE.getValue());
		 mallProduct.setIsDelete(BooleanEnum.FALSE.getValue());
		 req.setAttribute("proIntegral_end", integral.getCurrentIntegral());
		 return queryPageList(mallProduct, pageNo, pageSize, req);
	 }


	 /**
	 * 分页列表查询
	 *
	 * @param mallProduct
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "mall_product-分页列表查询")
	@ApiOperation(value="mall_product-分页列表查询", notes="mall_product-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MallProduct mallProduct,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MallProduct> queryWrapper = QueryGenerator.initQueryWrapper(mallProduct, req.getParameterMap());
		Page<MallProduct> page = new Page<MallProduct>(pageNo, pageSize);
		IPage<MallProduct> pageList = mallProductService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param mallProduct
	 * @return
	 */
	@AutoLog(value = "mall_product-添加")
	@ApiOperation(value="mall_product-添加", notes="mall_product-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MallProduct mallProduct) {
		mallProduct.setStatus(ProductStatusEnum.NEW.getValue());
		mallProduct.setIsDelete(BooleanEnum.FALSE.getValue());
		mallProductService.save(mallProduct);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param mallProduct
	 * @return
	 */
	@AutoLog(value = "mall_product-编辑")
	@ApiOperation(value="mall_product-编辑", notes="mall_product-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MallProduct mallProduct) {
		mallProductService.updateById(mallProduct);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "mall_product-通过id删除")
	@ApiOperation(value="mall_product-通过id删除", notes="mall_product-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mallProductService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "mall_product-批量删除")
	@ApiOperation(value="mall_product-批量删除", notes="mall_product-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mallProductService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "mall_product-通过id查询")
	@ApiOperation(value="mall_product-通过id查询", notes="mall_product-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MallProduct mallProduct = mallProductService.getById(id);
		if(mallProduct==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mallProduct);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mallProduct
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MallProduct mallProduct) {
        return super.exportXls(request, mallProduct, MallProduct.class, "mall_product");
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
        return super.importExcel(request, response, MallProduct.class);
    }

}
