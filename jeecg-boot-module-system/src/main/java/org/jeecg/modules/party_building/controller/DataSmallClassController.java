package org.jeecg.modules.party_building.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.party_building.entity.DataSmallClass;
import org.jeecg.modules.party_building.service.IDataSmallClassService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: data_small_class
 * @Author: jeecg-boot
 * @Date: 2020-07-02
 * @Version: V1.0
 */
@Api(tags = "微课堂")
@RestController
@RequestMapping("/com/dataSmallClass")
@Slf4j
public class DataSmallClassController extends JeecgController<DataSmallClass, IDataSmallClassService> {
    @Autowired
    private IDataSmallClassService dataSmallClassService;

    /**
     * 分页列表查询
     *
     * @param dataSmallClass
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "data_small_class-分页列表查询")
    @ApiOperation(value = "data_small_class-分页列表查询", notes = "data_small_class-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DataSmallClass dataSmallClass,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DataSmallClass> queryWrapper = QueryGenerator.initQueryWrapper(dataSmallClass, req.getParameterMap());
        Page<DataSmallClass> page = new Page<DataSmallClass>(pageNo, pageSize);
        IPage<DataSmallClass> pageList = dataSmallClassService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 分页列表查询id title create_time
     *
     * @param dataSmallClass
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "data_small_class-分页列表查询id title create_time")
    @ApiOperation(value = "data_small_class-分页列表查询id title create_time", notes = "data_small_class-分页列表查询id title create_time")
    @GetMapping(value = "/listIdAndTitle")
    public Result<?> queryPageList2(DataSmallClass dataSmallClass,
                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                    HttpServletRequest req) {
        dataSmallClass.setClassStates("2");
        QueryWrapper<DataSmallClass> queryWrapper = QueryGenerator.initQueryWrapper(dataSmallClass, req.getParameterMap());
        Page<DataSmallClass> page = new Page<DataSmallClass>(pageNo, pageSize);
        IPage<DataSmallClass> pageList = dataSmallClassService.page(page, queryWrapper);
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (pageList.getRecords().size() > 0) {
            for (DataSmallClass dsc : pageList.getRecords()) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", dsc.getId());
                dataMap.put("title", dsc.getTitle());
                dataMap.put("createTime", dsc.getCreateTime());
                dataList.add(dataMap);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", "党建微课堂");
        map.put("dataList", dataList);
        map.put("total", pageList.getTotal());
        map.put("size", pageList.getSize());
        map.put("current", pageList.getCurrent());
        map.put("pages", pageList.getPages());
        map.put("searchCount", true);
        return Result.ok(map);
    }

    /**
     * app端分页列表展示（含视频）
     * @param dataSmallClass
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "data_small_class-app端分页列表展示（含视频）")
    @ApiOperation(value = "data_small_class-app端分页列表展示（含视频）分页列表查询", notes = "data_small_class-app端分页列表展示（含视频）分页列表查询")
    @GetMapping(value = "/appList")
    public Result<?> queryAppPageList(DataSmallClass dataSmallClass,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        map.put("dataSmallClass", dataSmallClass);
        map.put("categoryBelong", "6");
        map.put("start", (pageNo - 1) * pageSize);
        map.put("size", pageSize);

        int count = dataSmallClassService.countSmallClassAppPageByCondition(map);
        List<Map<String, Object>> dataManuscriptList = dataSmallClassService.selectSmallClassAppPageByCondition(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", count);
        resultMap.put("current", pageNo);
        resultMap.put("size", pageSize);
        if (count % pageSize == 0) {
            resultMap.put("pages", count / pageSize);
        } else {
            resultMap.put("pages", (count / pageSize + 1));
        }
        resultMap.put("searchCount", true);
        resultMap.put("records", dataManuscriptList);
        return Result.ok(resultMap);
    }

    /**
     * 添加
     *
     * @param dataSmallClass
     * @return
     */
    @AutoLog(value = "data_small_class-添加")
    @ApiOperation(value = "data_small_class-添加", notes = "data_small_class-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DataSmallClass dataSmallClass) {
        dataSmallClassService.save(dataSmallClass);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dataSmallClass
     * @return
     */
    @AutoLog(value = "data_small_class-编辑")
    @ApiOperation(value = "data_small_class-编辑", notes = "data_small_class-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DataSmallClass dataSmallClass) {
        dataSmallClassService.updateById(dataSmallClass);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "data_small_class-通过id删除")
    @ApiOperation(value = "data_small_class-通过id删除", notes = "data_small_class-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        dataSmallClassService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "data_small_class-批量删除")
    @ApiOperation(value = "data_small_class-批量删除", notes = "data_small_class-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dataSmallClassService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "data_small_class-通过id查询")
    @ApiOperation(value = "data_small_class-通过id查询", notes = "data_small_class-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DataSmallClass dataSmallClass = dataSmallClassService.getById(id);
        if (dataSmallClass == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(dataSmallClass);
    }

    /**
     * 通过id查询党建微课堂（含视频）
     *
     * @param id
     * @return
     */
    @AutoLog(value = "data_small_class-通过id查询")
    @ApiOperation(value = "data_small_class-通过id查询党建微课堂（含视频）", notes = "data_small_class-通过id查询通过id查询党建微课堂（含视频）")
    @GetMapping(value = "/queryByMap")
    public Result<?> queryByMap(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("categoryBelong", "党建微课堂");
        List<Map<String, Object>> smallClassList = dataSmallClassService.getSmallClassByMap(map);
        if (smallClassList == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(smallClassList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param dataSmallClass
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataSmallClass dataSmallClass) {
        return super.exportXls(request, dataSmallClass, DataSmallClass.class, "data_small_class");
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
        return super.importExcel(request, response, DataSmallClass.class);
    }

}
