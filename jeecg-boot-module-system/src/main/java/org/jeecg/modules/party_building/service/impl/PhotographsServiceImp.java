package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.party_building.entity.Photographs;
import org.jeecg.modules.party_building.mapper.PhotographsMapper;
import org.jeecg.modules.party_building.service.PhotographsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PhotographsServiceImp extends ServiceImpl<PhotographsMapper, Photographs> implements PhotographsService {
    @Autowired
    private PhotographsMapper photographsMapper;

    @Override
    public List<Map<String, Object>> pagelistFileList(Photographs photographs) {
        return photographsMapper.pagelistFileList(photographs);
    }
}
