package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.DataAssessment;
import org.jeecg.modules.party_building.vo.DataAssessmentVo;
import org.springframework.data.repository.query.Param;

/**
 * @Description: data_assessment
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataAssessmentMapper extends BaseMapper<DataAssessment> {

    IPage<DataAssessment> assessMentlist(Page<DataAssessment> page, DataAssessment dataAssessment);

    IPage<DataAssessmentVo> mykaohelist(Page<DataAssessmentVo> page,@Param("id") String id,@Param("status") String status,@Param("assessmentId") String assessmentId);


    void updateIds(@Param("id") String id,@Param("issue") String issue);
}
