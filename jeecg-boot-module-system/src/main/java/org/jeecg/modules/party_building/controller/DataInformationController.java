package org.jeecg.modules.party_building.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.oss.entity.OSSFile;
import org.jeecg.modules.oss.service.IOSSFileService;
import org.jeecg.modules.party_building.entity.*;
import org.jeecg.modules.party_building.service.IDataInformationService;
import org.jeecg.modules.party_building.service.*;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.party_building.service.IDataCategoryService;

import org.jeecg.modules.party_building.util.FTPUtil;
import org.jeecg.modules.party_building.vo.DataInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_information
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="党建资讯")
@RestController
@RequestMapping("/com/dataInformation")
@Slf4j
public class DataInformationController extends JeecgController<DataInformation, IDataInformationService> {
	@Autowired
	private IDataInformationService dataInformationService;

	@Autowired
	private IDataCategoryService dataCategoryService;

    @Autowired
    private IDataCollectSupportService dataCollectSupportService;


	 @Autowired
	 private IDataBrowseHisService iDataBrowseHisService;

	 @Autowired
	 IOSSFileService iossFileService;
	 @Value(value = "${jeecg.path.upload}")
	 private String uploadpath;
	 @Value(value = "${jeecg.path.upload1}")
	 private String uploadpath1;

    /**
     * 分页列表查询
     *
     * @param dataInformation
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "data_information-分页列表查询")
    @ApiOperation(value = "data_information-分页列表查询", notes = "data_information-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DataInformation dataInformation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DataInformation> queryWrapper = QueryGenerator.initQueryWrapper(dataInformation, req.getParameterMap());
        Page<DataInformation> page = new Page<DataInformation>(pageNo, pageSize);
        IPage<DataInformationVo> pageList = dataInformationService.pageList(page, queryWrapper);
        return Result.ok(pageList);
    }

	 /**
	  * 门户首页查询所有资讯管理（按类型分组每组取前五条）
	  * @return
	  */
	 @AutoLog(value = "data_information-门户首页查询所有资讯管理")
	 @ApiOperation(value="data_information-门户首页查询所有资讯管理", notes="data_information-门户首页查询所有资讯管理")
	 @GetMapping(value = "/listAll")
	 public Result<?> queryList() {
		 List listPid=new ArrayList<>();
		 listPid.add("1");
		 Map<String, Object> map = new HashMap<>();
		 map.put("pid", listPid);
		 List<Map<String, Object>> listCategory = dataCategoryService.listByPid(map);

		 List<Map<String,Object>> resultList=new ArrayList<>();

		 for (Map<String,Object> categoryMap:listCategory){
		 	String categoryId=(String) categoryMap.get("id");
		 	String name=(String) categoryMap.get("name");
		 	Map<String,Object> resultMap=new HashMap<>();
		 	resultMap.put("categoryId",categoryId);
		 	resultMap.put("name",name);

		 	Integer start=0,size=5;
		 	String articleStates="2";
		 	List<Map<String,Object>> dataInformationList=dataInformationService.listByCategoryId(categoryId,articleStates,start,
					size);
		 	resultMap.put("dataList",dataInformationList);
		 	resultList.add(resultMap);
		 }
		 return Result.ok(resultList);
	 }

    /**
     * 党建资讯或微课堂全部列表查询（含图片视频）
     *
     * @param dataInformation module 1 党建资讯 2 微课堂  status 2 审核通过
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "data_information-分页列表查询")
    @ApiOperation(value = "data_information-分页列表查询", notes = "data_information-分页列表查询")
    @GetMapping(value = "/informationAndImagelist")
    public Result<?> queryinformationAndImagelist(DataInformation dataInformation,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest req,HttpServletResponse response) {
        String userId = req.getParameter("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("dataInformation", dataInformation);
        if(pageNo-1<=0){
        	map.put("start",0);
		}else{
			map.put("start", (pageNo - 1) * pageSize);
		}
        map.put("size", pageSize);
        map.put("queryCondition",(String)req.getParameter("queryCondition"));

        int count = dataInformationService.countinformationAndImagelist(map);
        List<Map<String, Object>> informationAndImagelist = dataInformationService.selectInformationAndImagelist(map);
        if (map.get("dataInformation") != null) {
            //查询点赞状态和收藏状态
            DataInformation dataInformation1 = (DataInformation) map.get("dataInformation");
            if (dataInformation1.getId() != null && !"".equals(dataInformation1) && userId != null&& !"".equals(userId)) {
                String supportType="2",collectionType="1";
                String supportStatus =dataCollectSupportService.selectTypeByUserIdAndInfoId(dataInformation1.getId(),
                        userId,supportType);
                String collectionStatus =dataCollectSupportService.selectTypeByUserIdAndInfoId(dataInformation1.getId(),
                        userId,collectionType);
                for (Map<String, Object> map1:informationAndImagelist){
                    if (supportStatus!=null){
                        map1.put("supportStatus","1");
                    }else{
                        map1.put("supportStatus","0");
                    }
                   if (collectionStatus!=null){
                       map1.put("collectionStatus","1");
                   }else {
                       map1.put("collectionStatus","0");
                   }
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", count);
        resultMap.put("current", pageNo);
        resultMap.put("size", pageSize);
        if (count % pageSize == 0) {
            resultMap.put("pages", count / pageSize);
        } else {
            resultMap.put("pages", (count / pageSize + 1));
        }
        resultMap.put("searchCount", true);
        resultMap.put("records", informationAndImagelist);
        return Result.ok(resultMap);
    }

    /**
     * 通过categoryBelong查询轮播图
     *
     * @param
     * @return
     */
    @AutoLog(value = "data_information_file-通过categoryBelong查询查询轮播图")
    @ApiOperation(value = "data_information_file-通过categoryBelong查询查询轮播图", notes = "data_information_file-通过categoryBelong查询查询轮播图")
    @GetMapping(value = "/queryRotationChart")
    public Result<?> queryRotationChartByMap(DataInformation dataInformation,
                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("dataInformation", dataInformation);
        map.put("start", (pageNo - 1) * pageSize);
        map.put("size", pageSize);
        List<Map<String, Object>> dataInformationList = dataInformationService.listRotationChartByMap(map);
        if (dataInformationList == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(dataInformationList);
    }

    /**
     * 分页列表查询微课堂id title create_time readNo
     *
     * @param dataInformation
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "data_information-分页列表查询id title create_time")
    @ApiOperation(value = "data_information-分页列表查询id title create_time", notes = "data_information-分页列表查询id title create_time")
    @GetMapping(value = "/listIdAndTitle")
    public Result<?> querylistIdAndTitle(DataInformation dataInformation,
                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                         HttpServletRequest req) {
        QueryWrapper<DataInformation> queryWrapper = QueryGenerator.initQueryWrapper(dataInformation, req.getParameterMap());
        Page<DataInformation> page = new Page<DataInformation>(pageNo, pageSize);
        IPage<DataInformation> pageList = dataInformationService.page(page, queryWrapper);
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (pageList.getRecords().size() > 0) {
            for (DataInformation dif : pageList.getRecords()) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", dif.getId());
                dataMap.put("title", dif.getTitle());
                dataMap.put("createTime", dif.getCreateTime());
                dataMap.put("readNo", dif.getReadNo());
                dataList.add(dataMap);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", "党建微课堂");
        map.put("dataList", dataList);
        map.put("total", pageList.getTotal());
        map.put("size", pageList.getSize());
        map.put("current", pageList.getCurrent());
        map.put("pages", pageList.getPages());
        map.put("searchCount", true);
        return Result.ok(map);
    }

	/**
	 *   添加
	 *
	 * @param dataInformation
	 * @return
	 */
	@AutoLog(value = "data_information-添加")
	@ApiOperation(value="data_information-添加", notes="data_information-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataInformation dataInformation) {
		List<DataInformation> list = dataInformationService.list(new QueryWrapper<DataInformation>().eq("title", dataInformation.getTitle()).eq("module", dataInformation.getModule()));
		if(!list.isEmpty()){
			return Result.error("标题名称重复！");
		}
		dataInformation.setCreateTime(new Date());
		dataInformationService.save(dataInformation);

		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param dataInformation
	 * @return
	 */
	@AutoLog(value = "data_information-编辑")
	@ApiOperation(value="data_information-编辑", notes="data_information-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataInformation dataInformation) {
		if (dataInformation.getSupportNo()!=null){
			Integer number=dataInformation.getSupportNo();
			Integer supportNo=dataInformationService.getById(dataInformation.getId()).getSupportNo();
			if ((number+supportNo)<=0){
				number=0;
				dataInformation.setSupportNo(number);
			}else {
				dataInformation.setSupportNo(supportNo+number);
			}
		}
		dataInformation.setUpdateTime(new Date());
		dataInformationService.updateById(dataInformation);

		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_information-通过id删除")
	@ApiOperation(value="data_information-通过id删除", notes="data_information-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataInformationService.removeById(id);
//		dataCollectSupportService.remove(new QueryWrapper<DataCollectSupport>().eq("info_id",id));
//		iDataBrowseHisService.remove(new QueryWrapper<DataBrowseHis>().eq("info_id",id));
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_information-批量删除")
	@ApiOperation(value="data_information-批量删除", notes="data_information-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dataInformationService.removeByIds(Arrays.asList(ids.split(",")));
//		dataCollectSupportService.remove(new QueryWrapper<DataCollectSupport>().in("info_id",Arrays.asList(ids.split(","))));
//		iDataBrowseHisService.remove(new QueryWrapper<DataBrowseHis>().eq("info_id",Arrays.asList(ids.split(","))));
		return Result.ok("批量删除成功!");
	}
	/**
	 *  批量修改审核状态
	 *
	 * @param json
	 * @return
	 */
	@AutoLog(value = "data_information-批量修改审核状态")
	@ApiOperation(value="data_information-批量修改审核状态", notes="data_information-批量修改审核状态")
	@PostMapping(value = "/updateStatusByIds")
	public Result<?> updateStatusByIds(@RequestBody JSONObject json) {
		String ids=json.getString("ids");
		String status=json.getString("status");
		String reason=json.getString("reason");

		if(StringUtils.isBlank(ids)||StringUtils.isBlank(status)){
			return Result.error("参数不全");
		}
		return 		this.dataInformationService.updateStatusByIds(Arrays.asList(ids.split(",")),status,reason);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_information-通过id查询")
	@ApiOperation(value="data_information-通过id查询", notes="data_information-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataInformation dataInformation = dataInformationService.getById(id);
		if(dataInformation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataInformation);
	}

	/**
	 * 通过categroyId分页查询资讯列表
	 * @param dataInformation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_information-党建资讯（两学一做、三会一课、党建动态、党风廉政）分页列表查询")
	@ApiOperation(value="data_information-党建资讯（两学一做、三会一课、党建动态、党风廉政）分页列表查询", notes="data_information-党建资讯（两学一做、三会一课、党建动态、党风廉政）分页列表查询")
	@GetMapping(value = "/queryByCategoryId")
	public Result<?> queryListByCategoryId(DataInformation dataInformation,
										   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										   HttpServletRequest req) {
		Map<String,Object> map=new HashMap<>();
		DataCategory dataCategory = dataCategoryService.getById(dataInformation.getCategoryId());
		if(dataCategory!=null){
			map.put("name",dataCategory.getName());
		}
		QueryWrapper<DataInformation> queryWrapper = QueryGenerator.initQueryWrapper(dataInformation, req.getParameterMap());
		Page<DataInformation> page = new Page<DataInformation>(pageNo, pageSize);
		IPage<DataInformation> pageList = dataInformationService.page(page, queryWrapper);


//		map.put("dataList",pageList);
		map.put("dataList",pageList.getRecords());
		map.put("total",pageList.getTotal());
		map.put("size",pageList.getSize());
		map.put("current",pageList.getCurrent());
		map.put("pages",pageList.getPages());
		map.put("searchCount",true);
		return Result.ok(map);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataInformation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataInformation dataInformation) {
        return super.exportXls(request, dataInformation, DataInformation.class, "data_information");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DataInformation.class);
    }

}
