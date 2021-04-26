package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.party_building.entity.DataAssessment;
import org.jeecg.modules.party_building.entity.DataAssessmentMember;
import org.jeecg.modules.party_building.mapper.DataAssessmentMapper;
import org.jeecg.modules.party_building.mapper.DataAssessmentMemberMapper;
import org.jeecg.modules.party_building.service.IDataAssessmentMemberService;
import org.jeecg.modules.party_building.vo.DataAssessmentMemberVo;
import org.jeecg.modules.party_building.vo.DataAssessmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: data_assessment_member
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataAssessmentMemberServiceImpl extends ServiceImpl<DataAssessmentMemberMapper, DataAssessmentMember> implements IDataAssessmentMemberService {
    @Autowired
    private DataAssessmentMemberMapper dataAssessmentMemberMapper;
    @Autowired
    private DataAssessmentMapper dataAssessmentMapper;

    @Override
    public IPage<DataAssessmentMemberVo> pageList(Page<DataAssessmentMemberVo> page, DataAssessmentMember dataAssessmentMember) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null){
            dataAssessmentMember.setAssessmentUserId(sysUser.getId());
        }
        return dataAssessmentMemberMapper.pageList(page,dataAssessmentMember);
    }

    @Override
    public IPage<DataAssessmentMemberVo> shenpilist(Page<DataAssessmentMemberVo> page, DataAssessmentMember dataAssessmentMember,String content) {
        return dataAssessmentMemberMapper.shenpilist(page,dataAssessmentMember,content);
    }

    @Override
    public Result<?> shenpiUpdate(String ids, String status,String reason) {
        String[] split = ids.split(",");
        for (String id: split
             ) {
            if(status.equals("3")){
                DataAssessmentMember dataAssessmentMember = dataAssessmentMemberMapper.selectById(id);
                if(!dataAssessmentMember.getStatus().equals("1")){
                        return Result.error("只能驳回待审核的数据");
                }
                dataAssessmentMember.setId(id);
                dataAssessmentMember.setStatus(status);
                dataAssessmentMember.setReason(reason);
                dataAssessmentMemberMapper.updateById(dataAssessmentMember);
            }
            if(status.equals("2")){
                DataAssessmentMember dataAssessmentMember1 = dataAssessmentMemberMapper.selectById(id);
                if(!dataAssessmentMember1.getStatus().equals("1")){
                    return Result.error("只能通过待审核的数据");
                }
                DataAssessment dataAssessment = dataAssessmentMapper.selectById(dataAssessmentMember1.getAssessmentId());
                //已考核人数
                dataAssessment.setAssessCount(dataAssessment.getAssessCount()+1);
                //未考核人数
                if(dataAssessment.getNotAssessCount()>0){
                    dataAssessment.setNotAssessCount(dataAssessment.getNotAssessCount()-1);
                }
                dataAssessmentMember1.setId(id);
                dataAssessmentMember1.setStatus(status);
                dataAssessmentMemberMapper.updateById(dataAssessmentMember1);
                dataAssessmentMapper.updateById(dataAssessment);
            }
        }
        return Result.ok();
    }
}
