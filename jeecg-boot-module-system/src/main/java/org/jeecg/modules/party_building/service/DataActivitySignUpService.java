package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.DataActivitysignUp;
import org.jeecg.modules.party_building.qo.DataActivitysignUpQo;
import org.jeecg.modules.party_building.vo.DataActivitysignUpVo;

import java.util.List;
import java.util.Map;

public interface DataActivitySignUpService  extends IService<DataActivitysignUp> {
    IPage<DataActivitysignUpVo> pageList(Page<DataActivitysignUpVo> page, DataActivitysignUp dataActivitysignUp,String categoryId,String astime);

    IPage<DataActivitysignUpVo> historyhuodong(Page<DataActivitysignUpVo> page, DataActivitysignUp dataActivitysignUp);

    Result updateIds(String ids);

    List<Map<String, Object>> myhuodong();
}
