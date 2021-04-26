package org.jeecg.modules.party_building.service.impl;

import org.jeecg.modules.party_building.entity.DataInformationFile;
import org.jeecg.modules.party_building.mapper.DataInformationFileMapper;
import org.jeecg.modules.party_building.service.IDataInformationFileService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: data_information_file
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
public class DataInformationFileServiceImpl extends ServiceImpl<DataInformationFileMapper, DataInformationFile> implements IDataInformationFileService {

    @Override
    public List<String> listByRelatedId(String relatedId) {
        return baseMapper.listByRelatedId(relatedId);
    }
}
