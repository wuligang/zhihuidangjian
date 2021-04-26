package org.jeecg.modules.party_building.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.party_building.entity.DataBrowseHis;
import org.jeecg.modules.party_building.mapper.DataBrowseHisMapper;
import org.jeecg.modules.party_building.service.IDataBrowseHisService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description: data_browse_his
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Service
@Slf4j
public class DataBrowseHisServiceImpl extends ServiceImpl<DataBrowseHisMapper, DataBrowseHis> implements IDataBrowseHisService {

    @Override
    public List<DataBrowseHis> listAll(String userId, String browseTime) throws InvocationTargetException, IllegalAccessException {
        /*List<DataBrowseHisGroupByTimeVo> list=baseMapper.selectListBrowseTime(userId);
        for (DataBrowseHisGroupByTimeVo dataBrowseHisGroupByTimeVo:list
             ) {
            QueryWrapper<DataBrowseHis> dataBrowseHisQueryWrapper=new QueryWrapper<>();
            BeanUtils.copyProperties(dataBrowseHisQueryWrapper,queryWrapper);
            dataBrowseHisQueryWrapper.eq("Date(browse_time)",dataBrowseHisGroupByTimeVo.getBrowseTime());
            List<DataBrowseHis> dataBrowseHisVos=baseMapper.selectList(dataBrowseHisQueryWrapper);
            dataBrowseHisGroupByTimeVo.setDataBrowseHiss(dataBrowseHisVos);
            DateFormat df= new SimpleDateFormat("yyyy-MM-dd");//对日期进行格式化
            if(dataBrowseHisGroupByTimeVo.getBrowseTime().equals(df.format(new Date()))){
                dataBrowseHisGroupByTimeVo.setBrowseTime("今天");
            }
           *//* log.info("浏览历史时间为+++"+dataBrowseHisGroupByTimeVo.getBrowseTime());
            log.info("当前时间为+++"+df.format(new Date()));
            log.info("对比结果++++++"+dataBrowseHisGroupByTimeVo.getBrowseTime().equals(df.format(new Date())));*//*
        }*/

        List<DataBrowseHis> dataBrowseHisVos=baseMapper.selectListBrowseTime(userId,browseTime);
        return dataBrowseHisVos;
    }

    @Override
    public IPage<DataBrowseHis> listPageByDate(Page<DataBrowseHis> page, String userId, String time ) {
        return baseMapper.listPageByDate(page,userId,time);
    }
}
