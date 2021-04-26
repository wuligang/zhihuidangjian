package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.party_building.entity.DataInformationFile;

import java.util.List;

/**
 * @Description: data_information_file
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataInformationFileMapper extends BaseMapper<DataInformationFile> {

    List<String> listByRelatedId(String relatedId);
}
