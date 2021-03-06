package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.DataAssessment;
import org.jeecg.modules.party_building.entity.DataAssessmentMember;
import org.jeecg.modules.party_building.mapper.DataAssessmentMapper;
import org.jeecg.modules.party_building.mapper.DataAssessmentMemberMapper;
import org.jeecg.modules.party_building.service.IDataAssessmentService;
import org.jeecg.modules.party_building.vo.DataAssessmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: data_assessment
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataAssessmentServiceImpl extends ServiceImpl<DataAssessmentMapper, DataAssessment> implements IDataAssessmentService {
@Autowired
private DataAssessmentMapper dataAssessmentMapper;
@Autowired
private DataAssessmentMemberMapper dataAssessmentMemberMapper;

    @Override
    public IPage<DataAssessment> assessMentlist(Page<DataAssessment> page, DataAssessment dataAssessment) {
        return dataAssessmentMapper.assessMentlist(page,dataAssessment);
    }

    @Override
    public IPage<DataAssessmentVo> mykaohelist(Page<DataAssessmentVo> page, String id,String status,String assessmentId) {
        return dataAssessmentMapper.mykaohelist(page,id,status,assessmentId);
    }

    @Override
    public Result<?> add(DataAssessment dataAssessment) {
        List<DataAssessment> assess_content = dataAssessmentMapper.selectList(new QueryWrapper<DataAssessment>().eq("assess_Content", dataAssessment.getAssessContent()));
        if(assess_content.size()>0){
            return Result.error("???????????????????????????");
        }
        dataAssessment.setIssue("2");
        dataAssessmentMapper.insert(dataAssessment);
        //????????????????????????notAssessMembers
       /* String notAssessMembers = dataAssessment.getNotAssessMembers();
        String[] split = notAssessMembers.split(",");
        for (String s: split
             ) {
            DataAssessmentMember  dataAssessmentMember=new DataAssessmentMember();
            dataAssessmentMember.setAssessmentId(dataAssessment.getId());
            dataAssessmentMember.setAssessmentUserId(s);
            dataAssessmentMember.setCreateTime(new Date());
            dataAssessmentMember.setStatus("0");
            dataAssessmentMemberMapper.insert(dataAssessmentMember);
}*/
        return Result.ok();
    }

    @Override
    public Result updateIds(String ids, String issue) {
        String[] split = ids.split(",");
        for (String id: split
             ) {
            //??????id????????????????????????
            DataAssessment dataAssessment = dataAssessmentMapper.selectById(id);
             if(issue.equals("1")){
                if(dataAssessment.getIssue().equals(issue)){
                    return Result.error("?????????????????????????????????????????????");
                }
                 dataAssessmentMapper.updateIds(id,issue);
             }
            if(issue.equals("1")){
                String[] splitl = dataAssessment.getNotAssessMembers().split(",");
                for (String s: splitl
                ) {
                    DataAssessmentMember  dataAssessmentMember=new DataAssessmentMember();
                    dataAssessmentMember.setAssessmentId(dataAssessment.getId());
                    dataAssessmentMember.setAssessmentUserId(s);
                    dataAssessmentMember.setCreateTime(new Date());
                    dataAssessmentMember.setStatus("0");
                    dataAssessmentMemberMapper.insert(dataAssessmentMember);
                }
            }
            if(issue.equals("2")){
                dataAssessmentMapper.updateIds(id,issue);
            }

        }
        return Result.ok();
    }

    @Override
    public Result<?> delete(String id) {
        DataAssessment dataAssessment = dataAssessmentMapper.selectById(id);
        if(dataAssessment.getIssue().equals("1")){
            return Result.error("???????????????????????????");
        }
        dataAssessmentMapper.deleteById(id);
        return Result.ok();
    }

    @Override
    public Result<?> deleteBatchIds(String ids) {
        String[] split = ids.split(",");
        for (String id: split
             ) {
            DataAssessment dataAssessment = dataAssessmentMapper.selectById(id);
            if(dataAssessment.getIssue().equals("1")){
                return Result.error("???????????????????????????");
            }
            dataAssessmentMapper.deleteById(id);
        }
        return  Result.ok();
    }

    @Override
    public Result recallIds(String id) {
        DataAssessment dataAssessment = dataAssessmentMapper.selectById(id);
        if(dataAssessment==null){
            return Result.error("??????????????????");
        }
        if(dataAssessment.getIssue().equals("2")){
            return Result.error("??????????????????????????????");
        }
        String notAssessMembers = dataAssessment.getNotAssessMembers();
        String[] split = notAssessMembers.split(",");
        for (String ids: split
             ) {
            dataAssessmentMemberMapper.delete(new QueryWrapper<DataAssessmentMember>().eq("assessment_Id",id).eq("assessment_User_Id",ids));
        }
        dataAssessment.setNotAssessCount(dataAssessment.getNotAssessCount()+dataAssessment.getAssessCount());
        dataAssessment.setAssessCount(0);
        dataAssessment.setIssue("2");
        dataAssessmentMapper.updateById(dataAssessment);
        return Result.ok();
    }
}
