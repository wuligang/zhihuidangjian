package org.jeecg.modules.party_building.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.party_building.atom.AutoRecordIntegralAtom;
import org.jeecg.modules.party_building.entity.DataManuscript;
import org.jeecg.modules.party_building.qo.DataManuscriptQo;
import org.jeecg.modules.party_building.service.IDataCollectSupportService;
import org.jeecg.modules.party_building.service.IDataManuscriptService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.party_building.util.EmojiUtil;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: data_manuscript
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Api(tags="稿件")
@RestController
@RequestMapping("/com/dataManuscript")
@Slf4j
public class DataManuscriptController extends JeecgController<DataManuscript, IDataManuscriptService> {
	@Autowired
	private IDataManuscriptService dataManuscriptService;

	 @Autowired
	 private ISysUserService sysUserService;

	 @Autowired
	 private IDataCollectSupportService dataCollectSupportService;


	 @Autowired
	 private AutoRecordIntegralAtom autoRecordIntegralAtom;

	 @Autowired
	 private ISysDictItemService sysDictItemService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dataManuscript
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "data_manuscript-分页列表查询")
	@ApiOperation(value="data_manuscript-分页列表查询", notes="data_manuscript-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataManuscript dataManuscript,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DataManuscript> queryWrapper = QueryGenerator.initQueryWrapper(dataManuscript, req.getParameterMap());
		Page<DataManuscript> page = new Page<DataManuscript>(pageNo, pageSize);
		IPage<DataManuscript> pageList = dataManuscriptService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  * 稿件列表根据查询条件模糊匹配分页查询
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "data_manuscript-稿件列表根据查询条件模糊匹配分页查询")
	 @ApiOperation(value="data_manuscript-稿件列表根据查询条件模糊匹配分页查询", notes="data_manuscript-稿件列表根据查询条件模糊匹配分页查询参数queryCondition" +
			 "（查询条件）pageSize，pageNo")
	 @GetMapping(value = "/listLike")
	 public Result<?> queryPageLikeList(DataManuscript dataManuscript,
										@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 List<String> manuscriptStatusList = new ArrayList<>();//稿件状态集合
		 if (dataManuscript.getManuscriptStatus() != null) {
			 String[] manuscriptStatusArray = dataManuscript.getManuscriptStatus().split(",");
			 if (manuscriptStatusArray != null && manuscriptStatusArray.length > 0) {
				 for (String status : manuscriptStatusArray) {
					 manuscriptStatusList.add(status);
				 }
			 }
			 dataManuscript.setManuscriptStatus("");
		 }
		 String queryCondition = req.getParameter("queryCondition");
		 if (queryCondition != null) {//模糊查询条件内容
			 queryCondition = queryCondition.trim();
			 /*按照稿件类型查询*/
			 String typeDictCode = "script_categoryId";
			 List<SysDictItem> typeDictList = sysDictItemService.selectItemsByDictCode(typeDictCode);
			 if (typeDictList != null && typeDictList.size() > 0) {
				 for (SysDictItem item : typeDictList) {
					 if (queryCondition.equals(item.getItemText())) {
						 dataManuscript.setCategoryId(Integer.parseInt(item.getItemValue()));
						 queryCondition = "";
						 break;
					 }
				 }
			 }
			 /*按照投稿状态查询*/
			 String statusDictCode = "manuscript_status";
			 List<SysDictItem> statusDictList = sysDictItemService.selectItemsByDictCode(statusDictCode);
			 if (statusDictList != null && statusDictList.size() > 0) {
				 for (SysDictItem statusItem : statusDictList) {
					 if (queryCondition.equals(statusItem.getItemText())) {
						 dataManuscript.setManuscriptStatus(statusItem.getItemValue());
						 queryCondition = "";
						 break;
					 }
				 }
			 }
		 }
		 Map<String, Object> map = new HashMap<>();
		 map.put("dataManuscript", dataManuscript);
		 map.put("queryCondition", queryCondition);
		 map.put("manuscriptStatus", manuscriptStatusList);
		 Page<DataManuscript> page = new Page<DataManuscript>(pageNo, pageSize);
		 IPage<DataManuscript> pageList = dataManuscriptService.selectPageByCondition(page, map);
		 return Result.ok(pageList);
	 }

	 @AutoLog(value = "data_information-分页列表查询")
	 @ApiOperation(value = "data_information-分页列表查询", notes = "data_information-分页列表查询")
	 @GetMapping(value = "/dataManuscriptAndImagelist")
	 public Result<?> queryManuscriptAndImagelist(DataManuscript dataManuscript,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		 String user = req.getParameter("user");
		 Map<String, Object> map = new HashMap<>();

		 List<String> manuscriptStatusList = new ArrayList<>();//稿件状态集合
		 if (dataManuscript.getManuscriptStatus() != null&&!"".equals(dataManuscript.getManuscriptStatus())) {
			 String[] manuscriptStatusArray = dataManuscript.getManuscriptStatus().split(",");
			 if (manuscriptStatusArray != null && manuscriptStatusArray.length > 0) {
				 for (String status : manuscriptStatusArray) {
					 manuscriptStatusList.add(status);
				 }
			 }
			 dataManuscript.setManuscriptStatus("");
		 }
		 map.put("dataManuscript", dataManuscript);
		 map.put("manuscriptStatus",manuscriptStatusList);
//		 map.put("dataManuscript", dataManuscript);
		 if(pageNo-1<=0){
			 map.put("start",0);
		 }else{
			 map.put("start", (pageNo - 1) * pageSize);
		 }
		 map.put("size", pageSize);
		 map.put("queryCondition",(String)req.getParameter("queryCondition"));

		 int count = dataManuscriptService.countDataManuscriptAndImagelist(map);
		 List<Map<String, Object>> manuscriptAndImagelist = dataManuscriptService.selectDataManuscriptAndImagelist(map);
		 if (map.get("dataManuscript") != null) {
			 //查询点赞状态和收藏状态
			 DataManuscript dataManuscript1 = (DataManuscript) map.get("dataManuscript");
			 if (dataManuscript1.getId() != null && !"".equals(dataManuscript1) && user != null&&!"".equals(user)) {
				 String supportType="2",collectionType="1";
				 String supportStatus =dataCollectSupportService.selectTypeByUserIdAndInfoId(dataManuscript1.getId(),
						 user,supportType);
				 String collectionStatus =dataCollectSupportService.selectTypeByUserIdAndInfoId(dataManuscript1.getId(),
						 user,collectionType);
				 for (Map<String, Object> map1:manuscriptAndImagelist){
					 if (supportStatus!=null){
						 map1.put("supportStatus",1);
					 }else{
						 map1.put("supportStatus",0);
					 }
					 if (collectionStatus!=null){
						 map1.put("collectionStatus",1);
					 }else {
						 map1.put("collectionStatus",0);
					 }
				 }
			 }
		 }
		 Map<String, Object> resultMap = new HashMap<>();
		 resultMap.put("total", count);
		 resultMap.put("current", pageNo);
		 resultMap.put("size", pageSize);
		 resultMap.put("pages", (count+pageSize-1) / pageSize );
		 resultMap.put("searchCount", true);
		 resultMap.put("records", manuscriptAndImagelist);
		 resultMap.put("module","3");
		 return Result.ok(resultMap);
	 }

	 @AutoLog(value = "data_information-首页统计投稿数量")
	 @ApiOperation(value = "data_information-首页统计投稿数量", notes = "data_information-首页统计投稿数量")
	 @GetMapping(value = "/manuscriptCountByUser")
	 public Result<?> manuscriptCountByUser(String userId) {
		 QueryWrapper<DataManuscript> queryWrapper =new QueryWrapper<>();
		 queryWrapper.eq("user_id",userId);
		 queryWrapper.eq("manuscript_status",'2');
		 int count = dataManuscriptService.count(queryWrapper);
		 return Result.ok(count);
	 }

	/**
	 *   添加
	 *
	 * @param dataManuscript
	 * @return
	 */
	@AutoLog(value = "data_manuscript-添加")
	@ApiOperation(value="data_manuscript-添加", notes="data_manuscript-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataManuscript dataManuscript) {
		String name = "", content = "";
		if (dataManuscript.getName() != null) {
			name = dataManuscript.getName().trim();
		}
		if (dataManuscript.getContent()!=null){
			content = dataManuscript.getContent().trim();
		}
		QueryWrapper<DataManuscript> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("manuscript_status",'2');
		List<DataManuscript> dataManuscriptList = dataManuscriptService.list(queryWrapper);

		for (DataManuscript dms : dataManuscriptList) {
			if (name.equals(dms.getName().trim())) {
				return Result.error("标题已存在，不允许重复");
			}
			if(content.equals(dms.getContent().trim())){
				return Result.error("内容已存在，不允许重复");
			}

		}

		if (content != null) {
			boolean flag = EmojiUtil.deWXName(content);
			if (flag) {
				return Result.error("文本内容不允许包含表情！");
			} else {
				if (dataManuscript.getUserId() != null) {
					SysUser sysUser = sysUserService.getById(dataManuscript.getUserId());
					if (sysUser != null) {
						dataManuscript.setOrgCode(sysUser.getOrgCode());
						dataManuscript.setAuthor(sysUser.getRealname());
					}
				}
				Date date = new DateTime();
				dataManuscript.setCreateTime(date);
				dataManuscript.setUpdateTime(date);
//				dataManuscriptService.save(dataManuscript);
				autoRecordIntegralAtom.manuscriptSaveTransaction(dataManuscript);
			}
		}
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dataManuscript
	 * @return
	 */
	@AutoLog(value = "data_manuscript-编辑")
	@ApiOperation(value="data_manuscript-编辑", notes="data_manuscript-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataManuscriptQo dataManuscript) {
		if (dataManuscript.getFlag()!=null){
			if(dataManuscript.getManuscriptStatus()!=null&&dataManuscript.getManuscriptStatus()!=""&&dataManuscript.getId()!=null){
				DataManuscript dataManuscript1=dataManuscriptService.getById(dataManuscript.getId());
				String status=dataManuscript1.getManuscriptStatus();//更改前稿件状态
				String newStatus=dataManuscript.getManuscriptStatus();//更改后稿件状态
				if (("2".equals(newStatus)&&!"1".equals(status))||("3".equals(newStatus)&&!"1".equals(status))||("4".equals(newStatus)&&!"2".equals(status))){
					return Result.error("稿件状态已改变，请刷新页面再操作");
				}else {
					DataManuscript dataManuscript2=new DataManuscript();
					dataManuscript2.setManuscriptStatus(newStatus);
					dataManuscript2.setId(dataManuscript.getId());
					if (dataManuscript.getRejectReason()!=null){
						dataManuscript2.setRejectReason(dataManuscript.getRejectReason());
					}
					Date date=new DateTime();
					dataManuscript.setUpdateTime(date);
					autoRecordIntegralAtom.exchangeMenuScriptIntegral(dataManuscript);
					return Result.ok("编辑成功!");
				}
			}
		}

		String name =null, content =null;
		if (dataManuscript.getName() != null) {
			name = dataManuscript.getName().trim();
		}
		if (dataManuscript.getContent()!=null){
			content = dataManuscript.getContent().trim();
		}
		QueryWrapper<DataManuscript> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("manuscript_status",'2');
		queryWrapper.ne("id",dataManuscript.getId());
		List<DataManuscript> dataManuscriptList = dataManuscriptService.list(queryWrapper);

		for (DataManuscript dms : dataManuscriptList) {
			if (name!=null){
				if (name.equals(dms.getName().trim())){
					return Result.error("标题已存在，不允许重复");
				}
			}
			if (content!=null){
				if ( content.equals(dms.getContent().trim())) {
					return Result.error("内容已存在" +
							"，不允许重复");
				}
			}
		}

		if (dataManuscript.getSupportNo()!=null){
			Integer number=dataManuscript.getSupportNo();
			Integer supportNo=dataManuscriptService.getById(dataManuscript.getId()).getSupportNo();
			if ((number+supportNo)<=0){
				number=0;
				dataManuscript.setSupportNo(number);
			}else {
				dataManuscript.setSupportNo(supportNo+number);
			}
		}
		Date date=new DateTime();
//		dataManuscript.setCreateTime(date);
        dataManuscript.setUpdateTime(date);
		autoRecordIntegralAtom.exchangeMenuScriptIntegral(dataManuscript);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_manuscript-通过id删除")
	@ApiOperation(value="data_manuscript-通过id删除", notes="data_manuscript-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataManuscriptService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_manuscript-批量删除")
	@ApiOperation(value="data_manuscript-批量删除", notes="data_manuscript-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dataManuscriptService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_manuscript-通过id查询")
	@ApiOperation(value="data_manuscript-通过id查询", notes="data_manuscript-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataManuscript dataManuscript = dataManuscriptService.getById(id);
		if(dataManuscript==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataManuscript);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataManuscript
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataManuscript dataManuscript) {
        return super.exportXls(request, dataManuscript, DataManuscript.class, "data_manuscript");
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
        return super.importExcel(request, response, DataManuscript.class);
    }

}
