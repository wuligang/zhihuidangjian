package org.jeecg.modules.party_building.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.party_building.entity.DataInformation;
import org.jeecg.modules.party_building.entity.DataInformationFile;
import org.jeecg.modules.party_building.service.IDataInformationFileService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_information_file
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="data_information_file")
@RestController
@RequestMapping("/com/dataInformationFile")
@Slf4j
public class DataInformationFileController extends JeecgController<DataInformationFile, IDataInformationFileService> {
	@Autowired
	private IDataInformationFileService dataInformationFileService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dataInformationFile
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_information_file-分页列表查询")
	@ApiOperation(value="data_information_file-分页列表查询", notes="data_information_file-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataInformationFile dataInformationFile,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DataInformationFile> queryWrapper = QueryGenerator.initQueryWrapper(dataInformationFile, req.getParameterMap());
		Page<DataInformationFile> page = new Page<DataInformationFile>(pageNo, pageSize);
		IPage<DataInformationFile> pageList = dataInformationFileService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  * 分页列表查询微课堂
	  * @param dataInformationFile
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */

//	 @AutoLog(value = "data_information_file-分页列表查询微课堂")
//	 @ApiOperation(value="data_information_file-分页列表查询微课堂", notes="data_information_file-分页列表查询微课堂")
//	 @GetMapping(value = "/SmallClasslist")
//	 public Result<?> querySmallClassPageList(DataInformationFile dataInformationFile,
//									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									HttpServletRequest req) {
//	 	dataInformationFile.setCategoryBelong("党建微课堂");
//		 QueryWrapper<DataInformationFile> queryWrapper = QueryGenerator.initQueryWrapper(dataInformationFile, req.getParameterMap());
//		 Page<DataInformationFile> page = new Page<DataInformationFile>(pageNo, pageSize);
//		 IPage<DataInformationFile> pageList = dataInformationFileService.page(page, queryWrapper);
//		 Map<String,Object> map=new HashMap<>();
//		 map.put("dataList",pageList.getRecords());
//		 map.put("total",pageList.getTotal());
//		 map.put("size",pageList.getSize());
//		 map.put("current",pageList.getCurrent());
//		 map.put("pages",pageList.getPages());
//		 map.put("searchCount",true);
//		 map.put("name",dataInformationFile.getCategoryBelong());
//		 return Result.ok(map);
//	 }
	
	/**
	 *   添加
	 *
	 * @param dataInformationFile
	 * @return
	 */
	@AutoLog(value = "data_information_file-添加")
	@ApiOperation(value="data_information_file-添加", notes="data_information_file-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataInformationFile dataInformationFile) {
		dataInformationFile.setCreateTime(new Date());
		dataInformationFileService.save(dataInformationFile);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dataInformationFile
	 * @return
	 */
	@AutoLog(value = "data_information_file-编辑")
	@ApiOperation(value="data_information_file-编辑", notes="data_information_file-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataInformationFile dataInformationFile) {
		dataInformationFile.setUpdateTime(new Date());
		dataInformationFileService.updateById(dataInformationFile);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_information_file-通过id删除")
	@ApiOperation(value="data_information_file-通过id删除", notes="data_information_file-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataInformationFileService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_information_file-批量删除")
	@ApiOperation(value="data_information_file-批量删除", notes="data_information_file-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dataInformationFileService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_information_file-通过id查询")
	@ApiOperation(value="data_information_file-通过id查询", notes="data_information_file-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataInformationFile dataInformationFile = dataInformationFileService.getById(id);
		if(dataInformationFile==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataInformationFile);
	}

	 /**
	  * 通过categoryBelong查询轮播图
	  *
	  * @param
	  * @return
	  */
	 @AutoLog(value = "data_information_file-通过categoryBelong查询")
	 @ApiOperation(value="data_information_file-通过categoryBelong查询", notes="data_information_file-通过categoryBelong查询")
	 @GetMapping(value = "/queryByCategoryBelong")
	 public Result<?> queryByCategoryBelong(String categoryBelong) {
		 Map<String,Object> map=new HashMap<>();
		 map.put("category_belong",categoryBelong);
		 List<DataInformationFile> dataInformationFileList = (List<DataInformationFile>) dataInformationFileService.listByMap(map);
		 if(dataInformationFileList==null) {
			 return Result.error("未找到对应数据");
		 }
		 List<DataInformationFile> dataInformationFileList1=new ArrayList<>();
		 for (DataInformationFile dataInformationFile:dataInformationFileList){
		 	String url=dataInformationFile.getFileUrl();
		 	if (url.contains(".")){
		 		String formatString=url.substring(url.lastIndexOf(".")+1);
		 		if (formatString.equals("jpg")||formatString.equals("png")||formatString.equals("jpeg")||formatString.equals(
		 				"giff")||formatString.equals("tiff")||formatString.equals("bmp")){
		 			dataInformationFileList1.add(dataInformationFile);
				}
			}
		 }
		 if(dataInformationFileList1.size()==0) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.ok(dataInformationFileList1);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param dataInformationFile
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataInformationFile dataInformationFile) {
        return super.exportXls(request, dataInformationFile, DataInformationFile.class, "data_information_file");
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
        return super.importExcel(request, response, DataInformationFile.class);
    }

}
