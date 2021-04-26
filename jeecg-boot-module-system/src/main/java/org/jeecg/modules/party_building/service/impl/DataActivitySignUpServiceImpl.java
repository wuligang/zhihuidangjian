package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.saxon.expr.Component;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.party_building.entity.DataActivity;
import org.jeecg.modules.party_building.entity.DataActivitysignUp;
import org.jeecg.modules.party_building.mapper.DataActivityMapper;
import org.jeecg.modules.party_building.mapper.DataActivitySignUpMapper;
import org.jeecg.modules.party_building.service.DataActivitySignUpService;
import org.jeecg.modules.party_building.vo.DataActivitysignUpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataActivitySignUpServiceImpl extends ServiceImpl<DataActivitySignUpMapper, DataActivitysignUp> implements DataActivitySignUpService {
    @Autowired
    private DataActivitySignUpMapper dataActivitySignUpMapper;
    @Autowired
    private DataActivityMapper dataActivityMapper;

    @Override
    public IPage<DataActivitysignUpVo> pageList(Page<DataActivitysignUpVo> page, DataActivitysignUp dataActivitysignUp,String categoryId,String astime) {
        if(dataActivitysignUp.getUserId()==null){
            //获取当前登陆的用户
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            dataActivitysignUp.setUserId(sysUser.getId());
        }
        return dataActivitySignUpMapper.pageList(page,dataActivitysignUp,categoryId,astime);
    }

    @Override
    public IPage<DataActivitysignUpVo> historyhuodong(Page<DataActivitysignUpVo> page, DataActivitysignUp dataActivitysignUp) {
        if(dataActivitysignUp.getUserId()==null){
            //获取当前登陆的用户
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            dataActivitysignUp.setUserId(sysUser.getId());
        }
        return dataActivitySignUpMapper.historyhuodong(page,dataActivitysignUp);
    }
/*批量添加活动报名*/
    @Override
    public Result updateIds(String ids) {
        if(ids!=null || ids!=""){
            String[] split = ids.split(",");
            for (String id: split
                 ) {
                //获取当前登陆的用户
                LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                List<DataActivitysignUp> dataActivitysignUps = dataActivitySignUpMapper.selectList(new QueryWrapper<DataActivitysignUp>().eq("activity_Id", id).eq("user_Id", sysUser.getId()));
                if(dataActivitysignUps.get(0).getActivitysignUpStatus().equals("2")){
                    return Result.error("不可重复报名");
                }
                DataActivity byId = dataActivityMapper.selectById(dataActivitysignUps.get(0).getActivityId());
                if(byId.getSignUpStartTime().getTime()>new  Date().getTime()){
                    return  Result.error("还不到报名时间");
                }
                if(byId.getSignUpEndTime().getTime()<new Date().getTime()){
                    return  Result.error("报名时间已结束");
                }
                DataActivitysignUp dataActivitysignUp=new DataActivitysignUp();
                dataActivitysignUp.setActivityId(id);
                dataActivitysignUp.setActivitysignUpStatus("2");
                dataActivitysignUp.setUserId(sysUser.getId());
                dataActivitysignUp.setSignUpTime(new Date());
                dataActivitySignUpMapper.updateIds(dataActivitysignUp.getActivitysignUpStatus(),dataActivitysignUp.getUserId(),dataActivitysignUp.getActivityId(),dataActivitysignUp.getSignUpTime());
            }
        }
        return Result.ok();
    }

    @Override
    public List<Map<String, Object>> myhuodong() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<DataActivity> organizer_user_id = dataActivityMapper.selectList(new QueryWrapper<DataActivity>().eq("organizer_User_Id", sysUser.getRealname()).eq("active_Status",2));
        Integer count1=0;
        Integer count2=0;
        List<Map<String, Object>> maps=new ArrayList<>();
        Map map=new HashMap();
        if(organizer_user_id.size()>0){
            Integer d= dataActivitySignUpMapper.myFaQiHuoDong(sysUser.getRealname());
            for (DataActivity dataActivity: organizer_user_id) {
              List<DataActivitysignUp>  dataActivitysignUps=dataActivitySignUpMapper.getCanYuHuoDong(dataActivity.getId());
              if(dataActivitysignUps.size()>0){
                  //活动参与的成功数  1未报名
                  count2=count2+1;
              }else {
                  count1=count1+1;
              }
            }
            map.put("count",d);
            map.put("count1",count1);
            map.put("count2",count2);
            maps.add(map);
            return maps;
        }
        return dataActivitySignUpMapper.myhuodong(sysUser.getId());
    }
}
