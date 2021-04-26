package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
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
public interface IDataActivityService extends IService<DataActivity> {
    /**
     * 查询党建活动前几条
     * @return
     */
    List<Map<String, Object>> getTop(int start,int end);

    IPage<DataActivityVo> pageList(Page<DataActivityVo> page, DataActivity dataActivity, String time, String userid, String shenpihuodong,String bohui);

    Result<?> updateByIds(String ids, String activeStatus,String reason);

    Result<?> add(DataActivity dataActivity);

    Result<?> deleteIds(String ids);
}
