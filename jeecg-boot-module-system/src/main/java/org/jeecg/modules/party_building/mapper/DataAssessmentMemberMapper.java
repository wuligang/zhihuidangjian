package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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
public interface DataAssessmentMemberMapper extends BaseMapper<DataAssessmentMember> {

    IPage<DataAssessmentMemberVo> pageList(Page<DataAssessmentMemberVo> page, DataAssessmentMember dataAssessmentMember);


    IPage<DataAssessmentMemberVo> shenpilist(Page<DataAssessmentMemberVo> page, DataAssessmentMember dataAssessmentMember,@Param("content") String content);
}
