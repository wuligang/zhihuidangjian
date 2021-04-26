package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.party_building.entity.Photographs;

import java.util.List;
import java.util.Map;

public interface PhotographsMapper extends BaseMapper<Photographs> {
    List<Map<String, Object>> pagelistFileList(Photographs photographs);
}
