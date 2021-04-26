package org.jeecg.modules.party_building.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.party_building.entity.DataCategory;
import org.jeecg.modules.party_building.service.IDataCategoryService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 分类表（资讯，微课堂，活动）
 * @Author: jeecg-boot
 * @Date: 2020-07-01
 * @Version: V1.0
 */
@Api(tags = "分类表（资讯，微课堂，活动）")
@RestController
@RequestMapping("/party_building/dataCategory")
@Slf4j
public class DataCategoryController extends JeecgController<DataCategory, IDataCategoryService> {
    @Autowired
    private IDataCategoryService dataCategoryService;

    /**
     * 分页列表查询
     *
     * @param dataCategory
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "分类表（资讯，微课堂，活动）-分页列表查询")
    @ApiOperation(value = "分类表（资讯，微课堂，活动）-分页列表查询", notes = "分类表（资讯，微课堂，活动）-分页列表查询")
    @GetMapping(value = "/rootList")
    public Result<?> queryPageList(DataCategory dataCategory,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        String parentId = dataCategory.getPid();
        if (oConvertUtils.isEmpty(parentId)) {
            parentId = "0";
        }
        dataCategory.setPid(null);
        QueryWrapper<DataCategory> queryWrapper = QueryGenerator.initQueryWrapper(dataCategory, req.getParameterMap());
        // 使用 eq 防止模糊查询
        queryWrapper.eq("pid", parentId);
        Page<DataCategory> page = new Page<DataCategory>(pageNo, pageSize);
        IPage<DataCategory> pageList = dataCategoryService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 获取子数据
     *
     * @param testTree
     * @param req
     * @return
     */
    @AutoLog(value = "分类表（资讯，微课堂，活动）-获取子数据")
    @ApiOperation(value = "分类表（资讯，微课堂，活动）-获取子数据", notes = "分类表（资讯，微课堂，活动）-获取子数据")
    @GetMapping(value = "/childList")
    public Result<?> queryPageList(DataCategory dataCategory, HttpServletRequest req) {
        QueryWrapper<DataCategory> queryWrapper = QueryGenerator.initQueryWrapper(dataCategory, req.getParameterMap());
        List<DataCategory> list = dataCategoryService.list(queryWrapper);
        return Result.ok(list);
    }


    /**
     * 添加
     *
     * @param dataCategory
     * @return
     */
    @AutoLog(value = "分类表（资讯，微课堂，活动）-添加")
    @ApiOperation(value = "分类表（资讯，微课堂，活动）-添加", notes = "分类表（资讯，微课堂，活动）-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DataCategory dataCategory) {
        dataCategoryService.addDataCategory(dataCategory);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dataCategory
     * @return
     */
    @AutoLog(value = "分类表（资讯，微课堂，活动）-编辑")
    @ApiOperation(value = "分类表（资讯，微课堂，活动）-编辑", notes = "分类表（资讯，微课堂，活动）-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DataCategory dataCategory) {
        dataCategoryService.updateDataCategory(dataCategory);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "分类表（资讯，微课堂，活动）-通过id删除")
    @ApiOperation(value = "分类表（资讯，微课堂，活动）-通过id删除", notes = "分类表（资讯，微课堂，活动）-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {

        return dataCategoryService.deleteDataCategory(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "分类表（资讯，微课堂，活动）-批量删除")
    @ApiOperation(value = "分类表（资讯，微课堂，活动）-批量删除", notes = "分类表（资讯，微课堂，活动）-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dataCategoryService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "分类表（资讯，微课堂，活动）-通过id查询")
    @ApiOperation(value = "分类表（资讯，微课堂，活动）-通过id查询", notes = "分类表（资讯，微课堂，活动）-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DataCategory dataCategory = dataCategoryService.getById(id);
        if (dataCategory == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(dataCategory);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param dataCategory
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataCategory dataCategory) {
        return super.exportXls(request, dataCategory, DataCategory.class, "分类表（资讯，微课堂，活动）");
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
        return super.importExcel(request, response, DataCategory.class);
    }

    /**
     * 通过pid查询门户首页导航栏
     * @return
     */
    @RequestMapping(value = "/selectByPid")
    public Result<?> selectByPid() {
        List listPid=new ArrayList<>();
        listPid.add("1");
        listPid.add("2");
        Map<String, Object> map = new HashMap<>();
        map.put("pid", listPid);
        List<Map<String, Object>> list = dataCategoryService.listByPid(map);
        if (list == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(list);
    }

    /**
     * 查询资讯分类 id、name
     * @return
     */
    @RequestMapping(value = "/selectInformationByPid")
    public Result<?> selectInformationByPid(String pid) {
        List listPid=new ArrayList<>();
        if (pid==null||"".equals(pid)){
            listPid.add("1");
        }else {
            listPid.add(pid);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pid", listPid);
        List<Map<String, Object>> list = dataCategoryService.listByPid(map);
        if (list == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(list);
    }
}
