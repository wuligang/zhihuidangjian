package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.DataAssessment;
import org.jeecg.modules.party_building.vo.DataAssessmentVo;

/**
 * @Description: data_assessment
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IDataAssessmentService extends IService<DataAssessment> {

    IPage<DataAssessment> assessMentlist(Page<DataAssessment> page, DataAssessment dataAssessment);

    IPage<DataAssessmentVo> mykaohelist(Page<DataAssessmentVo> page, String id,String status,String assessmentId);

    Result<?> add(DataAssessment dataAssessment);

    Result updateIds(String ids, String issue);

    Result<?> delete(String id);

    Result<?> deleteBatchIds(String ids);

    Result recallIds(String id);
}
