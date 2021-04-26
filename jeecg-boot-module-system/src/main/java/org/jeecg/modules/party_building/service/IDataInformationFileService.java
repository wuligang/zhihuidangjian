package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.DataInformationFile;

import java.util.List;

/**
 * @Description: data_information_file
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IDataInformationFileService extends IService<DataInformationFile> {


    List<String> listByRelatedId(String id);
}
