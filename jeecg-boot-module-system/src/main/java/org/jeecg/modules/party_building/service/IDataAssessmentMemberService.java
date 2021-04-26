package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.DataAssessment;
import org.jeecg.modules.party_building.entity.DataAssessmentMember;
import org.jeecg.modules.party_building.vo.DataAssessmentMemberVo;
import org.jeecg.modules.party_building.vo.DataAssessmentVo;

/**
 * @Description: data_assessment_member
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IDataAssessmentMemberService extends IService<DataAssessmentMember> {
    IPage<DataAssessmentMemberVo> pageList(Page<DataAssessmentMemberVo> page, DataAssessmentMember dataAssessmentMember);

    IPage<DataAssessmentMemberVo> shenpilist(Page<DataAssessmentMemberVo> page, DataAssessmentMember dataAssessmentMember,String content);

    Result<?> shenpiUpdate(String ids, String status,String reason);
}
