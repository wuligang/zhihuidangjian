package org.jeecg.modules.party_building.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.NamedElement;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.party_building.entity.DataActivity;
import org.jeecg.modules.party_building.entity.DataActivitySign;
import org.jeecg.modules.party_building.entity.DataActivitysignUp;
import org.jeecg.modules.party_building.qo.DataActivitysignUpQo;
import org.jeecg.modules.party_building.service.DataActivitySignUpService;
import org.jeecg.modules.party_building.service.IDataActivityService;
import org.jeecg.modules.party_building.vo.DataActivitysignUpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api("活动报名管理")
@RestController
@RequestMapping("DataActivitySignUp")
@Slf4j
public class DataActivitySignUpController {
    @Resource
    private DataActivitySignUpService dataActivitySignUpService;
    @Autowired
    private IDataActivityService dataActivityService;
    @ApiOperation(value = "活动报名管理-分页列表查询")
    @RequestMapping("list")//astime 根据参数字段判断是否显示已经结束的活动
    public Result list(DataActivitysignUp dataActivitysignUp,String categoryId,String astime, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       HttpServletRequest req){
  //  QueryWrapper<DataActivitysignUp> queryWrapper = QueryGenerator.initQueryWrapper(dataActivitysignUp, req.getParameterMap());
    Page<DataActivitysignUpVo> page = new Page<DataActivitysignUpVo>(pageNo, pageSize);
    IPage<DataActivitysignUpVo> pageList = dataActivitySignUpService.pageList(page, dataActivitysignUp,categoryId,astime);
    return Result.ok(pageList);
}

    @RequestMapping("update")
    @ApiOperation(value = "活动报名")
    public Result update(@RequestBody DataActivitysignUp dataActivitysignUp){
        if(dataActivitysignUp.getUserId()==null){
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            DataActivitysignUp one = dataActivitySignUpService.getOne(new QueryWrapper<DataActivitysignUp>().eq("activity_Id", dataActivitysignUp.getActivityId()).eq("user_Id", sysUser.getId()));
            DataActivity byId = dataActivityService.getById(dataActivitysignUp.getActivityId());
            if(byId.getSignUpStartTime().getTime()>new  Date().getTime()){
                return  Result.error("还不到报名时间");
            }
            if(byId.getSignUpEndTime().getTime()<new Date().getTime()){
                return  Result.error("报名时间已结束");
            }
            if(one.getActivitysignUpStatus().equals("1")){
                Date date=new Date();
                dataActivitysignUp.setSignUpTime(date);
                dataActivitySignUpService.updateById(dataActivitysignUp);
            }
        }else {
            DataActivitysignUp one = dataActivitySignUpService.getOne(new QueryWrapper<DataActivitysignUp>().eq("activity_Id", dataActivitysignUp.getActivityId()).eq("user_Id", dataActivitysignUp.getUserId()));
            if(one.getActivitysignUpStatus().equals("1")){
                Date date=new Date();
                dataActivitysignUp.setSignUpTime(date);
                dataActivitySignUpService.updateById(dataActivitysignUp);
            }
        }

        return Result.ok("报名成功");
    }
    /**
     * 批量添加活动报名
     * */
    @RequestMapping("updateIds")
    @ApiOperation(value = "批量添加活动报名")
    public Result updateIds(@RequestBody DataActivitysignUpQo dataActivitysignUpQo){
        return dataActivitySignUpService.updateIds(dataActivitysignUpQo.getIds());
    }
    /**
     *  查看我的历史活动
     * */
    @ApiOperation(value = "查看我的历史活动")
    @RequestMapping("historyhuodong")
    public Result historyhuodong(DataActivitysignUp dataActivitysignUp, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       HttpServletRequest req){
        //  QueryWrapper<DataActivitysignUp> queryWrapper = QueryGenerator.initQueryWrapper(dataActivitysignUp, req.getParameterMap());
        Page<DataActivitysignUpVo> page = new Page<DataActivitysignUpVo>(pageNo, pageSize);
        IPage<DataActivitysignUpVo> pageList = dataActivitySignUpService.historyhuodong(page, dataActivitysignUp);
        return Result.ok(pageList);
    }

    /**
     *  查看活动报名首页展示百分比
     * */
    @ApiOperation(value = "查看活动报名首页展示百分比")
    @GetMapping("myhuodong")
    public Result myhuodong(){
        List<Map<String,Object>>  pageList = dataActivitySignUpService.myhuodong();
        return Result.ok(pageList);
    }
}
