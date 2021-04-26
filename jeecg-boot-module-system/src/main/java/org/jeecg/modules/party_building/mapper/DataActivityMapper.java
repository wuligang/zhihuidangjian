package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataActivity;
import org.jeecg.modules.party_building.vo.DataActivityVo;

import java.util.List;
import java.util.Map;

/**
 * @Description: data_activity
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataActivityMapper extends BaseMapper<DataActivity> {

    List<Map<String, Object>> getTop(@Param("start") int start, @Param("end") int end);

    IPage<DataActivityVo> pageList(Page<DataActivityVo> page,@Param("dataActivity") DataActivity dataActivity, @Param("time") String time, @Param("userid") String userid, @Param("shenpihuodong") String shenpihuodong,@Param("bohui") String bohui);

    void updateIds(@Param("id") String id,@Param("activeStatus") String activeStatus);

    IPage<DataActivityVo> xiangqing(Page<DataActivityVo> page,@Param("dataActivity") DataActivity dataActivity,@Param("userid") String userid);
}
