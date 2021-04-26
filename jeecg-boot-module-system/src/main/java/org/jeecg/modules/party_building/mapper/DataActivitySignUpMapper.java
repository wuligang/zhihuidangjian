package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataActivitysignUp;
import org.jeecg.modules.party_building.vo.DataActivitysignUpVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DataActivitySignUpMapper extends BaseMapper<DataActivitysignUp> {
    IPage<DataActivitysignUpVo> pageList(Page<DataActivitysignUpVo> page, DataActivitysignUp dataActivitysignUp,@Param("categoryId") String categoryId,@Param("astime") String astime);

    IPage<DataActivitysignUpVo> historyhuodong(Page<DataActivitysignUpVo> page, DataActivitysignUp dataActivitysignUp);

    void updateIds(@Param("activitysignUpStatus") String activitysignUpStatus,@Param("userId") String userId,@Param("activityId") String activityId,@Param("signUpTime") Date signUpTime);

    List<Map<String, Object>> myhuodong(@Param("id") String id);

    Integer myFaQiHuoDong(@Param("realName") String realName);

    List<DataActivitysignUp> getCanYuHuoDong(@Param("id") String id);
}
