package org.jeecg.modules.party_building.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.party_building.entity.Photographs;
import org.jeecg.modules.party_building.service.PhotographsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api("轮播图配置")
@RestController
@RequestMapping("photographs")
@Slf4j
public class PhotographsController {
    @Autowired
    private PhotographsService photographsService;

    @AutoLog(value = "轮播图页面- 展示")
    @ApiOperation(value="轮播图页面-展示", notes="轮播图页面-展示")
    @GetMapping("/list")
    public Result pageList(Photographs photographs, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                           @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                           HttpServletRequest req){
        QueryWrapper<Photographs> queryWrapper = QueryGenerator.initQueryWrapper(photographs, req.getParameterMap());
        Page<Photographs> page = new Page<Photographs>(pageNo, pageSize);
        IPage<Photographs> pageList = photographsService.page(page,queryWrapper);
        return Result.ok(pageList);
    }
    @AutoLog(value = "轮播图配置-添加")
    @ApiOperation(value="轮播图配置-添加", notes="轮播图配置-添加")
    @RequestMapping("add")
    public Result add(@RequestBody Photographs photographs){
        photographs.setCreateTime(new Date());
        photographsService.save(photographs);
        return Result.ok();
    }

    /**
     *  编辑
     * */
    @RequestMapping("update")
    @AutoLog(value = "轮播图_编辑")
    @ApiOperation(value ="轮播图_编辑", notes="轮播图_编辑")
    public Result update(@RequestBody Photographs photographs){
        photographsService.updateById(photographs);
        return Result.ok();
    }

    /**
     *  删除
     * */
    @RequestMapping("delete")
    @AutoLog(value = "轮播图_删除")
    @ApiOperation(value ="轮播图_删除", notes="轮播图_删除")
    public Result delete(@RequestParam String  id){
        photographsService.removeById(id);
        return Result.ok();
    }
    /**
     *  轮播图页面展示
     * */
    @AutoLog(value = "轮播图页面展示- 展示")
    @ApiOperation(value="轮播图页面展示-展示", notes="轮播图页面展示-展示")
    @RequestMapping("pagelistFileList")
    public Result pagelistFileList(Photographs photographs){
        List<Map<String,Object>> pageList=photographsService.pagelistFileList(photographs);
        return Result.ok(pageList);
    }


    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "轮播图-通过id查询")
    @ApiOperation(value="轮播图-通过id查询", notes="轮播图-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        Photographs photographs =photographsService.getById(id);
        if(photographs==null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(photographs);
    }

}
