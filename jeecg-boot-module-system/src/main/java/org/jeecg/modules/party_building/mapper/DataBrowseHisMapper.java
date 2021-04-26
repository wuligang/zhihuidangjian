package org.jeecg.modules.party_building.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.party_building.entity.DataBrowseHis;
import org.jeecg.modules.party_building.vo.DataBrowseHisGroupByTimeVo;

import java.util.List;

/**
 * @Description: data_browse_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
public interface DataBrowseHisMapper extends BaseMapper<DataBrowseHis> {

    List<DataBrowseHis> selectListBrowseTime(@Param("userId")String userId,@Param("browseTime")String browseTime);

    IPage<DataBrowseHis> listPageByDate(IPage<DataBrowseHis> page,@Param("userId")String userId,@Param("browseTime") String browseTime );
}
