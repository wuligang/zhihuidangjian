package org.jeecg.modules.party_building.atom;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.entity.*;
import org.jeecg.modules.party_building.enums.IntegralRuleEnum;
import org.jeecg.modules.party_building.enums.ProductStatusEnum;
import org.jeecg.modules.party_building.service.*;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.Objects;

/**
 * 记录原子类，尽可能的使dao或service复用
 */
@Service
@Slf4j
public class AutoRecordIntegralAtom {

    @Autowired
    private IIntegralService integralService;

    @Autowired
    private IIntegralDetailService integralDetailService;

    @Autowired
    private IIntegralRuleService integralRuleService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IMallProductService mallProductService;

    @Autowired
    private IMallExchangeHisService mallExchangeHisService;

    @Autowired
    private IDataManuscriptService dataManuscriptService;

    /**
     * 记录积分变动明细并且汇总积分
     * @param integralDetail
     */
    @Transactional(rollbackFor = Exception.class)
    public void recordIntegralAndSum(IntegralDetail integralDetail){
        integralDetail.setChangeTime(new Date());
        integralDetailService.save(integralDetail);
        // 先查询积分汇总，如果有，则更新，如果没有，则新增
        if(Objects.nonNull(integralService.getIntegralByUserId(integralDetail.getUserId()))){
            // 更新
            integralService.updateByUserId(integralDetail.getUserId(), integralDetail.getChangeValue(), integralDetail.getRuleNo());
        }else{
            // 新增
            Integral integral = new Integral();
            integral.setUserId(integralDetail.getUserId());
            SysUser sysUser = sysUserService.getById(integralDetail.getUserId());
            integral.setUserName(sysUser.getUsername());
            integral.setRealName(sysUser.getRealname());
            int changeValue = integralDetail.getChangeValue()>0?integralDetail.getChangeValue():0;
            integral.setTotalIntegral(changeValue);
            integral.setCurrentIntegral(changeValue);
            integralService.save(integral);
        }

    }

    /**
     * 商品兑换并记录积分
     * @param userId
     * @param mallProduct
     */
    @Transactional(rollbackFor = Exception.class)
    public void exchangeProdAndRecordIntegral(String userId, MallProduct mallProduct) {
        MallProduct modifyMallPro = new MallProduct();
        modifyMallPro.setId(mallProduct.getId());
        int stock = mallProduct.getStock() - 1;
        modifyMallPro.setStock(stock);
        // 兑换后判断商品库存，若为0，自动下架
        if (stock <= 0) {
            modifyMallPro.setStatus(ProductStatusEnum.OFF_SALE.getValue());
        }
        mallProductService.updateById(modifyMallPro);

        // 记录商品兑换历史
        MallExchangeHis mallExchangeHis = new MallExchangeHis();
        mallExchangeHis.setProductId(mallProduct.getId());
        mallExchangeHis.setUserId(userId);
        mallExchangeHis.setFileId(mallProduct.getFileId());
        mallExchangeHis.setProTitle(mallProduct.getProTitle());
        mallExchangeHis.setProIntegral(mallProduct.getProIntegral());
        mallExchangeHis.setProContent(mallProduct.getProContent());
        mallExchangeHis.setExchangeTime(new Date());
        mallExchangeHisService.save(mallExchangeHis);

        // 记录积分变动流水，更新总积分
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setUserId(userId);
        // 商品兑换积分，记录负值
        integralDetail.setChangeValue(-mallProduct.getProIntegral());
        integralDetail.setChangeReason(String.format(IntegralRuleEnum.R998.getDesc(), mallProduct.getProTitle()));
        integralDetail.setRuleNo(IntegralRuleEnum.R998.getValue());
        integralDetail.setChangeTime(new Date());
        integralDetailService.save(integralDetail);
        integralService.updateByUserId(userId, integralDetail.getChangeValue(), integralDetail.getRuleNo());

    }

    /**
     * 稿件审核通过加积分
     * @param dataManuscript
     */
    @Transactional(rollbackFor = Exception.class)
    public void exchangeMenuScriptIntegral(DataManuscript dataManuscript) {
        dataManuscriptService.updateById(dataManuscript);
       DataManuscript dataManuscript1=dataManuscriptService.getById(dataManuscript.getId());
       if (dataManuscript1!=null&&"2".equals(dataManuscript.getManuscriptStatus())){
           String status=dataManuscript1.getManuscriptStatus();
               if ("2".equals(status)){
                   IntegralRule integralRule = integralRuleService.getRuleByRuleNo(IntegralRuleEnum.R003.getValue());
                   // 检查当天的积分是否达上限
                   int todayTotalIntegral = integralDetailService.sumIntegralToday(dataManuscript1.getUserId(),
                           IntegralRuleEnum.R003.getValue());
                   int changeIntegral = integralRule.getIntegralScore();
                   if(Objects.nonNull(integralRule.getDailyLimit())) {
                       if (todayTotalIntegral >= integralRule.getDailyLimit()) {
                           log.info("用户id[{}]今日投稿积分已达上限", dataManuscript1.getUserId());
                           return;
                       } else if (todayTotalIntegral + changeIntegral > integralRule.getDailyLimit()) {
                           changeIntegral = integralRule.getDailyLimit() - todayTotalIntegral;
                       }
                   }

                   IntegralDetail integralDetail=new IntegralDetail();
                   integralDetail.setUserId(dataManuscript1.getUserId());
                   integralDetail.setChangeValue(changeIntegral);
                   integralDetail.setChangeReason(IntegralRuleEnum.R003.getDesc());
                   integralDetail.setRuleNo(IntegralRuleEnum.R003.getValue());
                   recordIntegralAndSum(integralDetail);
               }
       }

    }


    @Transactional(rollbackFor = Exception.class)
    public void manuscriptSaveTransaction(DataManuscript dataManuscript){
        try {
            dataManuscriptService.save(dataManuscript);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

}
