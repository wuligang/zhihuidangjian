package org.jeecg.modules.party_building.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.party_building.entity.DataActivity;
import org.jeecg.modules.party_building.entity.DataActivitysignUp;
import org.jeecg.modules.party_building.mapper.DataActivityMapper;
import org.jeecg.modules.party_building.mapper.DataActivitySignUpMapper;
import org.jeecg.modules.party_building.service.IDataActivityService;
import org.jeecg.modules.party_building.vo.DataActivityVo;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.mapper.SysDepartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Description: data_activity
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataActivityServiceImpl extends ServiceImpl<DataActivityMapper, DataActivity> implements IDataActivityService {
    @Autowired
    private  DataActivityMapper dataActivityMapper;
    @Autowired
    private DataActivitySignUpMapper dataActivitySignUpMapper;
    @Autowired
    private SysDepartMapper sysDepartMapper;
    /**
     * 获取党建活动前几条数据
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Map<String, Object>> getTop(int start, int end) {
        List<Map<String,Object>> activeList=baseMapper.getTop(start,end);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map<String,Object> map:activeList){
            Date date = new Date();
            try {
                date = dateformat.parse(String.valueOf(map.get("create_time")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String date1=dateformat.format(date);
            map.put("create_time",date1);
        }
        return activeList;
    }



    @Override
    public IPage<DataActivityVo> pageList(Page<DataActivityVo> page, DataActivity dataActivity, String time, String userid, String shenpihuodong,String bohui) {
            if(dataActivity.getOrgCode()!=null){
             return    dataActivityMapper.xiangqing(page,dataActivity,userid);
            }
        return dataActivityMapper.pageList(page,dataActivity,time,userid,shenpihuodong,bohui);
    }

    @Override
    public Result<?> updateByIds(String ids, String activeStatus,String reason) {
        String[] split = ids.split(",");
        for (String id:
             split) {
            if(activeStatus.equals("0")){
                dataActivityMapper.updateIds(id,activeStatus);
            }
            if(activeStatus.equals("1")){
                DataActivity dataActivity = dataActivityMapper.selectById(id);
                if(dataActivity.getActiveStatus().equals("1")){
                    return Result.error("待审核的数据不可重复提交");
                }
                if(dataActivity.getActiveStatus().equals("2")){
                    return Result.error("审核通过的的数据不可重复提交");
                }
                if(dataActivity.getActiveStatus().equals("3")){
                    return Result.error("驳回的数据不可提交");
                }
                dataActivityMapper.updateIds(id,activeStatus);
            }
            if(activeStatus.equals("3")){
                DataActivity dataActivity = dataActivityMapper.selectById(id);
                if(!dataActivity.getActiveStatus().equals("1")){
                    return Result.error("审核过得数据不可驳回");
                }
                dataActivity.setReason(reason);
                dataActivity.setActiveStatus(activeStatus);
                dataActivity.setId(id);
                dataActivityMapper.updateById(dataActivity);
            }
            if(activeStatus.equals("2")){
                DataActivity dataActivity = dataActivityMapper.selectById(id);
                if(dataActivity.getActiveStatus().equals("2")){
                    return Result.error("审核通过的数据不可再次审核");
                }
                if(dataActivity.getActiveStatus().equals("3")){
                    return Result.error("被驳回的数据不可再次通过");
                }
                dataActivityMapper.updateIds(id,activeStatus);
                String partyMember = dataActivity.getPartyMember();
                String[] pary = partyMember.split(",");
                for (String p: pary
                     ) {
                    List<DataActivitysignUp> dataActivitysignUps = dataActivitySignUpMapper.selectList(new QueryWrapper<DataActivitysignUp>().eq("activity_Id", dataActivity.getId()).eq("user_Id", p));
                    if(dataActivitysignUps.size()<1){
                        DataActivitysignUp dataActivitysignUp=new DataActivitysignUp();
                        dataActivitysignUp.setActivityId(dataActivity.getId());
                        dataActivitysignUp.setActivitysignUpStatus("1");
                        dataActivitysignUp.setName(dataActivity.getName());
                        dataActivitysignUp.setUserId(p);
                        dataActivitySignUpMapper.insert(dataActivitysignUp);
                    }
                }
            }
            if(activeStatus.equals("4")){
                DataActivity dataActivity = dataActivityMapper.selectById(id);
                if(!dataActivity.getActiveStatus().equals("2")){
                    return Result.error("只能对审核通过的数据进行下架");
                }
                String partyMember = dataActivity.getPartyMember();
                String[] split1 = partyMember.split(",");
                for (String s: split1
                     ) {
                    dataActivitySignUpMapper.delete(new QueryWrapper<DataActivitysignUp>().eq("user_Id",s).eq("activity_Id",dataActivity.getId()));
                }
                dataActivityMapper.updateIds(id,activeStatus);
            }
        }
        return Result.ok();
    }

    @Override
    public Result<?> add(DataActivity dataActivity) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<DataActivity> name = dataActivityMapper.selectList(new QueryWrapper<DataActivity>().eq("name", dataActivity.getName()));
        if(name.size()>0){
            return Result.error("已有此活动名称的数据");
        }
        dataActivity.setActiveStatus("0");
        dataActivity.setOrganizerUserId(sysUser.getRealname());
        SysDepart sysDepart=sysDepartMapper.getOrgCode(sysUser.getOrgCode());
        if(sysDepart!=null){
            dataActivity.setHostDepartId(sysDepart.getId());
        }
        dataActivityMapper.insert(dataActivity);
        return Result.ok();
    }

    @Override
    public Result<?> deleteIds(String ids) {
        String[] split = ids.split(",");
        for (String id: split
             ) {
            DataActivity dataActivity = dataActivityMapper.selectById(id);
            if(dataActivity.getActiveStatus().equals("2")){
                return Result.error("审核通过的数据不可删除");
            }
            dataActivityMapper.deleteById(id);
        }
        return Result.ok("删除成功");
    }


}
