package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.MallProduct;
import org.jeecg.modules.party_building.entity.Photographs;

import java.util.List;
import java.util.Map;

public interface PhotographsService extends IService<Photographs> {
    List<Map<String, Object>> pagelistFileList(Photographs photographs);
}
