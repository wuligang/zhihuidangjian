package org.jeecg.modules.party_building.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ServiceException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.util.CommonUtils;
import org.jeecg.modules.party_building.atom.AutoRecordIntegralAtom;
import org.jeecg.modules.party_building.entity.Integral;
import org.jeecg.modules.party_building.entity.IntegralDetail;
import org.jeecg.modules.party_building.entity.IntegralExt;
import org.jeecg.modules.party_building.entity.IntegralRule;
import org.jeecg.modules.party_building.enums.IntegralRuleEnum;
import org.jeecg.modules.party_building.model.IntegralRecordModel;
import org.jeecg.modules.party_building.service.IIntegralDetailService;
import org.jeecg.modules.party_building.service.IIntegralRuleService;
import org.jeecg.modules.party_building.service.IIntegralService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.party_building.util.ExcelUtil;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import sun.misc.MessageUtils;

/**
 * @Description: integral
 * @Author: jeecg-boot
 * @Date: 2020-07-02
 * @Version: V1.0
 */
@Api(tags = "积分")
@RestController
@RequestMapping("/com/integral")
@Slf4j
public class IntegralController extends JeecgController<Integral, IIntegralService> {
    @Autowired
    private IIntegralService integralService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IIntegralDetailService integralDetailService;

    @Autowired
    private IIntegralRuleService integralRuleService;

    @Autowired
    private AutoRecordIntegralAtom autoRecordIntegralAtom;

    @Autowired
    private ISysDepartService departService;


    /**
     * 按照integral_rule表的规则进行积分明细记录，积分汇总调整
     *
     * @param integralRecordModel
     * @return
     */
    @AutoLog(value = "autoRecordIntegral-积分自动记录")
    @ApiOperation(value = "autoRecordIntegral-积分自动记录", notes = "autoRecordIntegral-积分自动记录")
    @PostMapping(value = "/autoRecord")
    public Result<?> autoRecordIntegral(IntegralRecordModel integralRecordModel) {
        log.info("积分记录请求参数: {}", integralRecordModel);
        // 拿规则编号查询出规则
        IntegralRule integralRule = integralRuleService.getRuleByRuleNo(integralRecordModel.getRuleNo());
        if (Objects.isNull(integralRule)) {
            log.info("integral_rule表未查询到规则编号{}的记录", integralRecordModel.getRuleNo());
            Result.error(String.format("不存在编号[%s]的积分规则", integralRecordModel.getRuleNo()));
        }
        String ruleNo = integralRule.getRuleNo();
        IntegralRuleEnum integralRuleEnum = IntegralRuleEnum.getIntegralRuleEnum(ruleNo);

        int dbTimeLength = 0;
        int timeLength = 0;
        if(CommonUtils.oneEquals(integralRuleEnum, IntegralRuleEnum.R001, IntegralRuleEnum.R002)) {
            dbTimeLength = integralRule.getTimeLength();
            // 配置规则时间单位统一转成秒
            if ("m".equals(integralRule.getTimeUnit())) {
                dbTimeLength = dbTimeLength * 60;
            } else if ("h".equals(integralRule.getTimeUnit())) {
                dbTimeLength = dbTimeLength * 60 * 60;
            }

            timeLength = integralRecordModel.getTimeLength();
        }
        // 积分变动值
        int changeIntegral;
        // 当天总积分
        int todayTotalIntegral;
        switch (integralRuleEnum) {
            case R001:
                // 微课堂视频观看
                // 检查用户当天此视频是否已经看过，若已看过，直接返回成功，并且不计积分
                if (integralDetailService.haveRecordToday(integralRecordModel.getUserId(), ruleNo, integralRecordModel.getVideoName())) {
                    log.info("用户id[{}]重复观看不计分", integralRecordModel.getUserId());
                    return Result.ok("重复观看不计分");
                }
                // 若视频总时长小于配置单位的时长，则记录最小单位积分
                if (timeLength <= dbTimeLength) {
                    changeIntegral = integralRule.getIntegralScore();
                }
                // 若视频总时长大于配置单位时长，则根据规则计算具体积分，取整
                else {
                    changeIntegral = timeLength / dbTimeLength * integralRule.getIntegralScore();
                }

                // 检查当天的积分是否达上限
                todayTotalIntegral = integralDetailService.sumIntegralToday(integralRecordModel.getUserId(), ruleNo);
                if (todayTotalIntegral >= integralRule.getDailyLimit()) {
                    log.info("用户id[{}]今日看视频积分已达上限", integralRecordModel.getUserId());
                    return Result.ok("今日看视频积分已达上限!");
                } else if (todayTotalIntegral + changeIntegral > integralRule.getDailyLimit()) {
                    changeIntegral = integralRule.getDailyLimit() - todayTotalIntegral;
                }

                // 记录明细并汇总
                autoRecordIntegralAtom.recordIntegralAndSum(getIntegralDetail(integralRecordModel, integralRuleEnum, changeIntegral));
                break;
            case R002:
                // 微课堂文本
                // 若停留时长小于配置单位时长，则不计分，并直接返回
                if (timeLength < dbTimeLength) {
                    log.info("用户id[{}]达最小单位不计分", integralRecordModel.getUserId());
                    return Result.ok("未达最小单位不计分!");
                }
                // 根据配置规则计算具体时长，取整
                else {
                    changeIntegral = timeLength / dbTimeLength * integralRule.getIntegralScore();
                }
                // 检查当天的积分是否达上限
                todayTotalIntegral = integralDetailService.sumIntegralToday(integralRecordModel.getUserId(), ruleNo);
                if (todayTotalIntegral >= integralRule.getDailyLimit()) {
                    log.info("用户id[{}]今日阅读积分已达上限", integralRecordModel.getUserId());
                    return Result.ok("今日阅读积分已达上限!");
                } else if (todayTotalIntegral + changeIntegral > integralRule.getDailyLimit()) {
                    changeIntegral = integralRule.getDailyLimit() - todayTotalIntegral;
                }

                // 记录明细并汇总
                autoRecordIntegralAtom.recordIntegralAndSum(getIntegralDetail(integralRecordModel, integralRuleEnum, changeIntegral));
                break;
            case R003:
                // 投稿
                // 取配置
                changeIntegral = integralRule.getIntegralScore();
                // 检查当天的积分是否达上限
                todayTotalIntegral = integralDetailService.sumIntegralToday(integralRecordModel.getUserId(), ruleNo);
                if(Objects.nonNull(integralRule.getDailyLimit())) {
                    if (todayTotalIntegral >= integralRule.getDailyLimit()) {
                        log.info("用户id[{}]今日投稿积分已达上限", integralRecordModel.getUserId());
                        return Result.ok("今日投稿积分已达上限!");
                    } else if (todayTotalIntegral + changeIntegral > integralRule.getDailyLimit()) {
                        changeIntegral = integralRule.getDailyLimit() - todayTotalIntegral;
                    }
                }
                // 记录明细并汇总
                autoRecordIntegralAtom.recordIntegralAndSum(getIntegralDetail(integralRecordModel, integralRuleEnum, changeIntegral));
                break;
            case R004:
                // 思政网
                // 检查当天是否登录过思政网，若已登录过，直接返回成功，并且不记积分
                if (integralDetailService.haveRecordToday(integralRecordModel.getUserId(), ruleNo, null)) {
                    return Result.ok("仅首次访问思政网计分");
                }
                // 根据规则取出积分
                changeIntegral = integralRule.getIntegralScore();

                // 记录明细并汇总
                autoRecordIntegralAtom.recordIntegralAndSum(getIntegralDetail(integralRecordModel, integralRuleEnum, changeIntegral));
                break;
            default:
                return Result.error(String.format("不存在编号[%s]的积分规则", integralRecordModel.getRuleNo()));
        }

        return Result.ok("积分自动记录成功!");
    }

    /**
     * 组织IntegralDetail入参
     * @param integralRecordModel
     * @param integralRuleEnum
     * @param changeIntegral
     * @return
     */
    private IntegralDetail getIntegralDetail(IntegralRecordModel integralRecordModel, IntegralRuleEnum integralRuleEnum, int changeIntegral) {
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setUserId(integralRecordModel.getUserId());
        integralDetail.setChangeValue(changeIntegral);
        integralDetail.setChangeReason(integralRuleEnum.getDesc());
        integralDetail.setVideoName(integralRecordModel.getVideoName());
        integralDetail.setRuleNo(integralRecordModel.getRuleNo());
        return integralDetail;
    }

    /**
     * 查询所有积分，并按照累计积分，当前积分排序
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "integral-积分排名查询")
    @ApiOperation(value = "integral-积分排名查询", notes = "integral-积分排名查询")
    @GetMapping(value = "/listSort")
    public Result<?> queryPageListByIntegralSort(Integral integral,Integer test,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 HttpServletRequest req){
        Result<JSONObject> result=new Result<>();
        JSONObject obj = new JSONObject();
        if(test==null){
            Result<Page<Integral>> resultl = new Result<Page<Integral>>();
            Page<Integral> pageList = new Page<Integral>(pageNo, pageSize);
            pageList = integralService.listSort(pageList, integral);

            //批量查询用户的所属部门
            //step.1 先拿到全部的 useids
            //step.2 通过 useids，一次性查询用户的所属部门名字
            List<String> userIds = pageList.getRecords().stream().map(Integral::getUserId).collect(Collectors.toList());
            if(userIds!=null && userIds.size()>0){
                Map<String,String> useDepNames = sysUserService.getDepNamesByUserIds(userIds);
                pageList.getRecords().forEach(item->{
                    //TODO 临时借用这个字段用于页面展示
                    item.setOrgCode(useDepNames.get(item.getUserId()));
                });
            }
            resultl.setSuccess(true);
            resultl.setResult(pageList);
            return resultl;
        }else {
            if(integral.getUserId()!=null){
                Page<Integral> pageLists = new Page<Integral>(1, 1);
                pageLists = integralService.listSort(pageLists, integral);
                obj.put("pageList",pageLists);
                integral.setUserId(null);
                Page<Integral> pageListll = new Page<Integral>(pageNo, pageSize);
                pageListll = integralService.listSort(pageListll, integral);
                obj.put("pageListss",pageListll);
            }
            result.setResult(obj);
            return result;
        }

    }

    /**
     *  导出党员积分排名
     * */
    @RequestMapping("exportExcel")
    public Result<?> exportExcel(HttpServletResponse response,HttpServletRequest request,Integral integral) throws UnsupportedEncodingException {
        Result<?> result=new Result<>();

        Page<Integral> pageList = new Page<Integral>(1, 5000);
        pageList = integralService.listSortNew(pageList, integral);

        //批量查询用户的所属部门
        //step.1 先拿到全部的 useids
        //step.2 通过 useids，一次性查询用户的所属部门名字
        List<String> userIds = pageList.getRecords().stream().map(Integral::getUserId).collect(Collectors.toList());
        if(userIds!=null && userIds.size()>0){
            Map<String,String> useDepNames = sysUserService.getDepNamesByUserIds(userIds);
            pageList.getRecords().forEach(item->{
                //TODO 临时借用这个字段用于页面展示
                item.setOrgCode(useDepNames.get(item.getUserId()));
            });
        }
        String[] headList={"排名","部门","姓名","学习积分","可用积分",};
        String[] attrbuteList={"rankNew","orgCode","realName","totalIntegral","currentIntegral"};
        String fileName="党员积分排名";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes(),"iso-8859-1") + ".xls");

        ExcelUtil.buildDocument(fileName,pageList.getRecords(),headList,attrbuteList,response);
        result.success("导出成功");
        return result;
    }




    /**
     * 分页列表查询
     *
     * @param integral
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "integral-分页列表查询")
    @ApiOperation(value = "integral-分页列表查询", notes = "integral-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(Integral integral,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<Integral> queryWrapper = QueryGenerator.initQueryWrapper(integral, req.getParameterMap());
        Page<Integral> page = new Page<Integral>(pageNo, pageSize);
        IPage<Integral> pageList = integralService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param integral
     * @return
     */
    @AutoLog(value = "integral-添加")
    @ApiOperation(value = "integral-添加", notes = "integral-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Integral integral) {
        integralService.save(integral);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param integral
     * @return
     */
    @AutoLog(value = "integral-编辑")
    @ApiOperation(value = "integral-编辑", notes = "integral-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody Integral integral) {
        integralService.updateById(integral);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "integral-通过id删除")
    @ApiOperation(value = "integral-通过id删除", notes = "integral-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        integralService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "integral-批量删除")
    @ApiOperation(value = "integral-批量删除", notes = "integral-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.integralService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "integral-通过id查询")
    @ApiOperation(value = "integral-通过id查询", notes = "integral-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        Integral integral = integralService.getById(id);
        if (integral == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(integral);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param integral
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Integral integral) {
        return super.exportXls(request, integral, Integral.class, "integral");
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
        return super.importExcel(request, response, Integral.class);
    }

}
