package org.jeecg.modules.party_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.party_building.entity.DataBrowseHis;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description: data_browse_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface IDataBrowseHisService extends IService<DataBrowseHis> {

    List<DataBrowseHis> listAll(String userId, String browseTime) throws InvocationTargetException, IllegalAccessException;

    IPage<DataBrowseHis> listPageByDate(Page<DataBrowseHis> page, String userid, String time);
}
