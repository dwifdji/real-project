package com._360pai.core.provider.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.NumberToCN;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.AcctFacade;
import com._360pai.core.facade.account.req.SearchBatchReq;
import com._360pai.core.facade.account.req.WithdrawReq;
import com._360pai.core.facade.account.resp.*;
import com._360pai.core.facade.account.vo.AcctDetailVo;
import com._360pai.core.facade.activity.resp.SignContractResp;
import com._360pai.core.model.account.*;
import com._360pai.core.model.agreement.WithdrawAgreement;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AcctService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.applet.TAppletMessageService;
import com._360pai.core.service.assistant.StaffService;
import com._360pai.core.service.assistant.TBankService;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author RuQ
 * @Title: AcctProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/12/5 13:06
 */
@Component
@Service(version = "1.0.0")
public class AcctProvider implements AcctFacade {

    public static final Logger LOGGER = LoggerFactory.getLogger(AcctProvider.class);

    @Autowired
    private AcctService acctService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private TBankService tBankService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private TAppletMessageService appletMessageService;

    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;

    @Override
    public WithdrawResp withdraw(WithdrawReq req) {
        LOGGER.info("开始调用 withdraw ,参数:{}", JSON.toJSONString(req));
        return acctService.withdrawAcctAmount(req.getType(),req.getPartyId(),req.getAmount(),req.getBankAccountId());
    }

    @Override
    public boolean firstVerify(WithdrawReq req) {
        LOGGER.info("开始调用 firstVerify ,参数:{}", JSON.toJSONString(req));
        TAcctDetail updateParam = new TAcctDetail();
        updateParam.setId(req.getAcctDetailId());
        updateParam.setStatus(req.getStatus());
        updateParam.setFirstVerifyOperatorId(req.getOperatorId());
        updateParam.setFirstVerifyRefuseReason(req.getVerifyRemark());
        updateParam.setFirstVerifyTime(new Date());

        if(AccountEnum.AcctDetailStatus.FIRST_VERIFY_FAIL.getKey().equals(req.getStatus())){
            acctService.withdrawFail(req.getAcctDetailId());
        }else{
            TAcctDetail detail = acctService.findDetailById(req.getAcctDetailId());
            TAcct acct = acctService.findAcctById(detail.getAcctId());
            AccountBaseDto dto = accountService.getAcctBaseByPartyIdAndType(acct.getPartyId(),acct.getType());
            createContract(dto.getName(),dto.getMobile(),detail.getAmount(),detail.getId(),acct.getPartyId());
        }

        return acctService.updateAcctDetail(updateParam);
    }

    private void createContract(String userName,String mobile,BigDecimal amount,Long detailId,Integer partyId){
        GenerateContractComReq comReq = new GenerateContractComReq();
        comReq.setActivityId(detailId+"");
        comReq.setType(FddEnums.SING_TYPE.SERVICE_ADVISORY.getType());
        comReq.setPartyId(partyId+"");

        GenerateServiceAdvisoryReq req = new GenerateServiceAdvisoryReq();
        req.setAmount(amount.setScale(2,BigDecimal.ROUND_HALF_UP)+"");
        req.setAmountUpper(NumberToCN.number2CNMontrayUnit(amount));
        req.setUser(userName);
        req.setUserPhone(mobile);

        LOGGER.info("开始调用 fddSignatureFacade generateContract，参数:{},{}",JSON.toJSONString(comReq),JSON.toJSONString(req));
        GenerateContractResp resp = fddSignatureFacade.generateContract(comReq,req);
        LOGGER.info("结束调用 fddSignatureFacade generateContract，参数:{},{},结果:{}",JSON.toJSONString(comReq),JSON.toJSONString(req),JSON.toJSONString(resp));

        if (resp != null && resp.getCode().equals(ApiCallResult.SUCCESS.getCode())) {
            //落表
            acctService.saveContract(detailId,resp.getContractId(),resp.getViewPdfUrl(),resp.getDownloadUrl());
        }

    }

    @Override
    public SignContractResp signContract(WithdrawReq req) {

        TAcctDetail detail = acctService.findDetailById(req.getAcctDetailId());
        TAcct acct = acctService.findAcctById(detail.getAcctId());

        WithdrawAgreement agreement = acctService.getWithdrawAgreement(req.getAcctDetailId());
        if(agreement == null){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if(agreement.getSigned()){
            throw new BusinessException(ExceptionEnumImpl.HAS_SIGN_ERROR);
        }

        SignContractResp signContractResp = new SignContractResp();

        if(String.valueOf(req.getPartyId()).equals(String.valueOf(acct.getPartyId())) && req.getType().equals(acct.getType())){
            WithdrawAgreement withdrawAgreement = acctService.getWithdrawAgreement(req.getAcctDetailId());
            ExtSignContractReq extSignContractReq = new ExtSignContractReq();
            extSignContractReq.setContract_id(withdrawAgreement.getContractId());
            extSignContractReq.setActivity_id(String.valueOf(req.getAcctDetailId()));
            extSignContractReq.setType(FddEnums.SING_TYPE.SERVICE_ADVISORY.getType());

            List<FddSignInfo> list = new ArrayList<FddSignInfo>();
            FddSignInfo fddSignInfo = new FddSignInfo();
            fddSignInfo.setSign_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
            fddSignInfo.setIs_auto(FddEnums.SING_AUTO.NOT_AUTO.getType());


            if(AccountEnum.AcctType.USER.getKey().equals(acct.getType())){
                fddSignInfo.setMem_role("1");
            }else{
                fddSignInfo.setMem_role("2");
            }

            AccountBaseDto dto = accountService.getAcctBaseByPartyIdAndType(acct.getPartyId(),acct.getType());
            fddSignInfo.setFdd_id(dto.getFadadaId());
            list.add(fddSignInfo);

            extSignContractReq.setSign_list(list);
            LOGGER.info("开始调用 fddSignatureFacade extSignContract,参数:{}",JSON.toJSONString(extSignContractReq));
            ExtSignContractResp resp = fddSignatureFacade.extSignContract(extSignContractReq);
            LOGGER.info("结束调用 fddSignatureFacade extSignContract,参数:{},结果:{}",JSON.toJSONString(extSignContractReq),JSON.toJSONString(resp));
            if (ApiCallResult.SUCCESS.getCode().equals(resp.getCode())) {
                signContractResp.setUrl(resp.getUrl());
                signContractResp.setParam(resp.getParam());
            }
        }else{
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        return signContractResp;
    }

    @Override
    @Transactional
    public boolean signCallBack(String detailId, String contractId, boolean hasSuccess) {
        TAcctDetail detail = acctService.findDetailById(Long.parseLong(detailId));
        if(detail == null || detail.getStatus().equals(AccountEnum.AcctDetailStatus.HAS_SIGN_CONTRACT.getKey())){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        WithdrawAgreement agreement = acctService.getWithdrawAgreement(Long.parseLong(detailId));
        if(agreement == null){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if(agreement.getSigned()){
            throw new BusinessException(ExceptionEnumImpl.HAS_SIGN_ERROR);
        }

        if(hasSuccess){
            WithdrawAgreement updateParam = new WithdrawAgreement();
            updateParam.setAllSigned(true);
            updateParam.setSigned(true);
            updateParam.setId(agreement.getId());
            acctService.updateWithdrawAgreement(updateParam);

            TAcctDetail detailParam = new TAcctDetail();
            detailParam.setId(Long.parseLong(detailId));
            detailParam.setStatus(AccountEnum.AcctDetailStatus.HAS_SIGN_CONTRACT.getKey());
            acctService.updateAcctDetail(detailParam);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean submitInvoice(WithdrawReq req) {
        LOGGER.info("开始调用 submitInvoice ,参数:{}", JSON.toJSONString(req));
        TAcct acct = acctService.findAcctByPartyIdAndType(req.getPartyId(),req.getType());
        if (acct == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAcctDetail detail = acctService.findDetailById(req.getAcctDetailId());
        if (detail == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!detail.getAcctId().equals(acct.getId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!(AccountEnum.AcctDetailStatus.HAS_SIGN_CONTRACT.getKey().equals(detail.getStatus())
                || AccountEnum.AcctDetailStatus.INVOICE_VERIFY_FAIL.getKey().equals(detail.getStatus()) )) {
            throw new BusinessException("请先签署合同");
        }
        TInvoice saveParam = new TInvoice();
        saveParam.setAcctId(acct.getId());
        saveParam.setType(req.getInvoiceType());
        if(AccountEnum.InvoiceType.PAPER.getKey().equals(req.getInvoiceType())){
            saveParam.setLogisticsCompany(req.getLogisticsCompany());
            saveParam.setLogisticsNo(req.getLogisticsNo());
        }else{
            saveParam.setImgurl(req.getImgUrl());
        }
        acctService.saveInvoice(saveParam);

        TAcctDetail updateDetailParam = new TAcctDetail();
        updateDetailParam.setId(req.getAcctDetailId());
        updateDetailParam.setInvoiceId(saveParam.getId());
        updateDetailParam.setStatus(AccountEnum.AcctDetailStatus.HAS_PROVIDE_INVOICE.getKey());
        return acctService.updateAcctDetail(updateDetailParam);

    }

    @Override
    @Transactional
    public boolean verifyInvoice(WithdrawReq req) {
        LOGGER.info("开始调用 verifyInvoice ,参数:{}", JSON.toJSONString(req));
        TAcctDetail updateParam = new TAcctDetail();
        updateParam.setId(req.getAcctDetailId());
        updateParam.setStatus(req.getStatus());
        updateParam.setInvoiceVerifyOperatorId(req.getOperatorId());
        updateParam.setInvoiceVerifyRefuseReason(req.getVerifyRemark());
        updateParam.setInvoiceVerifyTime(new Date());
        if(AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey().equals(req.getStatus())){
            TAcctDetail detail = acctService.findDetailById(req.getAcctDetailId());
            TAcct acct = acctService.findAcctById(detail.getAcctId());
            if(! AccountEnum.AcctType.AGENCY.getKey().equals(acct.getType())){
                appletMessageService.sendWithdrawVerifySuccessMessage(acct.getPartyId());
            }

            TInvoice invoiceUpdateParam = new TInvoice();
            invoiceUpdateParam.setId(detail.getInvoiceId());
            invoiceUpdateParam.setCode(req.getInvoiceCode());
            invoiceUpdateParam.setNum(req.getInvoiceNo());
            acctService.updateInvoice(invoiceUpdateParam);


        }
        return acctService.updateAcctDetail(updateParam);
    }

    @Override
    public BatchOrderInfoResp preBatchOrder(WithdrawReq req) {
        LOGGER.info("开始调用 preBatchOrder ,参数:{}", JSON.toJSONString(req));
        BatchOrderInfoResp resp = new BatchOrderInfoResp();
        resp.setBatchOrderNo(getBatchOrderId()+"");
        resp.setBatchOrderCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<WithdrawDetailVo> voList = new ArrayList<WithdrawDetailVo>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(String id : req.getDetailIdList()){
            WithdrawDetailVo vo = new WithdrawDetailVo();
            TAcctDetail detail = acctService.findDetailById(Long.parseLong(id));
            totalAmount = totalAmount.add(detail.getAmount());
            if(detail == null){
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
            if(!detail.getStatus().equals(AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey())){
                throw new BusinessException(ExceptionEnumImpl.DETAIL_STATUS_ERROR);
            }
            if(!detail.getType().equals(AccountEnum.AcctOperateType.WITHDRAW.getKey())){
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
            if(acctService.checkDetailRefExist(Long.parseLong(id))){
                throw new BusinessException(ExceptionEnumImpl.HAS_IN_BATCH_ERROR);
            }
            transDetaiInfo(vo,detail);
            voList.add(vo);
        }
        sortList(voList);
        resp.setTotalNum(req.getDetailIdList().size()+"");
        resp.setTotalAmount(totalAmount.setScale(2,BigDecimal.ROUND_HALF_UP)+"");
        resp.setDetailList(voList);
        return resp;
    }

    private Long getBatchOrderId(){
        TBatchOrder maxOrder = acctService.findMaxOrder();
        if(maxOrder == null){
            return Long.parseLong(RandomNumberGenerator.generatorBatchOrderNum());

        }
        String maxOrderId = maxOrder.getId()+"";
        String maxPreDate = maxOrderId.substring(0,8);
        if(!maxPreDate.equals(new SimpleDateFormat("yyyyMMdd").format(new Date()))){
            return Long.parseLong(RandomNumberGenerator.generatorBatchOrderNum());
        }else {
            return maxOrder.getId()+1;
        }


    }



    @Override
    @Transactional
    public boolean saveOrUpdateBatchOrder(Long batchOrderId, List<String> detailIdList,Integer operatorId) {
        LOGGER.info("开始调用 saveBatchOrder ,参数:{}", JSON.toJSONString(detailIdList));
        checkDetailRef(detailIdList);
        if(detailIdList != null && detailIdList.size()>0){

            if(batchOrderId == null){
                Long id = getBatchOrderId();
                acctService.saveBatchOrder(id,operatorId);
                for(String detailId : detailIdList){
                    acctService.saveBatchDetailRef(id,Long.parseLong(detailId));
                }
            }else{
                for(String detailId : detailIdList){
                    acctService.saveBatchDetailRef(batchOrderId,Long.parseLong(detailId));
                }
            }

        }
        return true;
    }

    private void checkDetailRef(List<String> detailIdList){
        for(String id:detailIdList){
            TAcctDetail detail = acctService.findDetailById(Long.parseLong(id));
            if(detail == null){
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
            if(!detail.getStatus().equals(AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey())){
                throw new BusinessException(ExceptionEnumImpl.DETAIL_STATUS_ERROR);
            }
            if(!detail.getType().equals(AccountEnum.AcctOperateType.WITHDRAW.getKey())){
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
            if(acctService.checkDetailRefExist(Long.parseLong(id))){
                throw new BusinessException(ExceptionEnumImpl.HAS_IN_BATCH_ERROR);
            }
        }
    }

    @Override
    public boolean lastVerify(WithdrawReq req) {
        LOGGER.info("开始调用 lastVerify ,参数:{}", JSON.toJSONString(req));
        TBatchOrder updateParam = new TBatchOrder();
        updateParam.setId(Long.parseLong(req.getBatchOrderNo()));
        updateParam.setStatus(req.getStatus());
        updateParam.setVerifyOperatorId(req.getOperatorId());
        updateParam.setRemark(req.getVerifyRemark());

        if(AccountEnum.AcctDetailStatus.LAST_VERIFY_FAIL.getKey().equals(req.getStatus())){
            acctService.delteBatchDetailRef(Long.parseLong(req.getBatchOrderNo()));
        }

        return acctService.updateBatchOrder(updateParam);
    }

    @Override
    @Transactional
    public boolean hasPay(WithdrawReq req) {
        LOGGER.info("开始调用 hasPay ,参数:{}", JSON.toJSONString(req));
        TAcctDetail detail = acctService.findDetailById(req.getAcctDetailId());
        if(!AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey().equals(detail.getStatus())){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        if (req.getPayBankAccountId() == null || StringUtils.isEmpty(req.getBankDetailNo()) || StringUtils.isEmpty(req.getPayTime())) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        TAcctDetail updateParam = new TAcctDetail();
        updateParam.setId(req.getAcctDetailId());
        updateParam.setStatus(AccountEnum.AcctDetailStatus.HAS_PAY.getKey());
        updateParam.setPayOperatorId(req.getOperatorId());
        updateParam.setPayBankAccountId(req.getPayBankAccountId());
        updateParam.setPayDetailNo(req.getBankDetailNo());
        updateParam.setPayTime(req.getPayTime());
        acctService.updateAcctDetail(updateParam);

        return acctService.releaseAcctLockedAmount(req.getAcctDetailId());


    }

    @Override
    @Transactional
    public boolean cancelPay(WithdrawReq req) {
        LOGGER.info("开始调用 cancelPay ,参数:{}", JSON.toJSONString(req));
        return acctService.cancelPay(req.getAcctDetailId(),req.getOperatorId(),req.getVerifyRemark());
    }

    @Override
    public boolean hc(WithdrawReq req) {
        LOGGER.info("开始调用 hc ,参数:{}", JSON.toJSONString(req));
        return acctService.hc(req.getAcctDetailId(),req.getOperatorId(),req.getVerifyRemark());
    }

    @Override
    public AcctResp getAcctByPartyIdAndType(Integer partyId, String type) {
        return acctService.getAcctByPartyIdAndType(partyId,type);
    }

    @Override
    public ResponseModel getDetailByBatchNo(WithdrawReq req) {
        List<TBatchDetailRef> refList = acctService.findDetailRefListByBatchId(Long.parseLong(req.getBatchOrderNo()));
        List<WithdrawDetailVo> voList = new ArrayList<WithdrawDetailVo>();
        BigDecimal totalAmt = BigDecimal.ZERO;
        if(refList != null){
            for(TBatchDetailRef ref : refList){
                WithdrawDetailVo vo = new WithdrawDetailVo();
                TAcctDetail detail = acctService.findDetailById(ref.getDetailId());
                transDetaiInfo(vo,detail);
                vo.setRefId(ref.getId());
                voList.add(vo);
                totalAmt = totalAmt.add(detail.getAmount());
            }

            sortList(voList);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("totalNum", voList.size());
        data.put("list", voList);
        data.put("totalAmt",totalAmt);
        return ResponseModel.succ(data);
    }

    @Override
    public List<WithdrawDetailVo> getNoBatchDetail() {
        List<TAcctDetail> detailList = acctService.getNoBatchDetail();
        List<WithdrawDetailVo> voList = new ArrayList<WithdrawDetailVo>();
        if(detailList != null){
            for (TAcctDetail detail : detailList){
                WithdrawDetailVo vo = new WithdrawDetailVo();
                transDetaiInfo(vo,detail);
                voList.add(vo);
            }
        }
        return voList;
    }

    @Override
    public ResponseModel searchBatchOrder(SearchBatchReq req) {
        PageInfoResp<BatchOrderInfoResp> resp = new PageInfoResp<BatchOrderInfoResp>();
        List<BatchOrderInfoResp> respList = new ArrayList<BatchOrderInfoResp>();
        PageInfoResp<TBatchOrder> orderPageInfoResp = acctService.searchBatchOrder(req);
        BigDecimal allAmount = BigDecimal.ZERO;
        if(orderPageInfoResp != null && orderPageInfoResp.getList() != null){
            resp.setTotal(orderPageInfoResp.getTotal());
            resp.setHasNextPage(orderPageInfoResp.isHasNextPage());
            for(TBatchOrder order : orderPageInfoResp.getList()){
                BatchOrderInfoResp batchOrderInfoResp = new BatchOrderInfoResp();
                batchOrderInfoResp.setBatchOrderNo(order.getId()+"");
                batchOrderInfoResp.setBatchOrderCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()));
                batchOrderInfoResp.setStatus(order.getStatus().equals(AccountEnum.AcctDetailStatus.LAST_VERIFY_SUCCESS.getKey())?"是":"否");

                List<TBatchDetailRef> refList = acctService.findDetailRefListByBatchId(order.getId());
                List<WithdrawDetailVo> voList = new ArrayList<WithdrawDetailVo>();
                if(refList != null){
                    BigDecimal totalAmt = BigDecimal.ZERO;
                    for(TBatchDetailRef ref : refList){
                        WithdrawDetailVo vo = new WithdrawDetailVo();
                        TAcctDetail detail = acctService.findDetailById(ref.getDetailId());
                        totalAmt = totalAmt.add(detail.getAmount());
                        transDetaiInfo(vo,detail);
                        vo.setRefId(ref.getId());
                        voList.add(vo);
                    }
                    batchOrderInfoResp.setTotalNum(refList.size()+"");
                    batchOrderInfoResp.setTotalAmount(totalAmt.setScale(2,BigDecimal.ROUND_HALF_UP)+"");
                    sortList(voList);
                    batchOrderInfoResp.setDetailList(voList);
                    allAmount = allAmount.add(totalAmt);
                }
                respList.add(batchOrderInfoResp);

            }
            resp.setList(respList);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("total", resp.getTotal());
        data.put("hasNextPage", resp.isHasNextPage());
        data.put("list", resp.getList());
        data.put("allAmount",allAmount);
        return ResponseModel.succ(data);
    }

    @Override
    public boolean deleteDetailRef(WithdrawReq req) {
        return acctService.delteBatchDetailRefById(req.getRefId());
    }

    @Override
    public PageInfoResp<WithdrawDetailVo> getWithdrawRecord(SearchBatchReq req) {
        PageInfoResp<WithdrawDetailVo> pageInfoResp = new PageInfoResp<WithdrawDetailVo>();
        PageInfoResp<TAcctDetail> detailPageInfoResp = acctService.getWithdrawRecord(req);
        pageInfoResp.setTotal(detailPageInfoResp.getTotal());
        pageInfoResp.setHasNextPage(detailPageInfoResp.isHasNextPage());

        List<WithdrawDetailVo> voList = new ArrayList<WithdrawDetailVo>();

        for(TAcctDetail detail : detailPageInfoResp.getList()){
            WithdrawDetailVo vo = new WithdrawDetailVo();
            transDetaiInfo(vo,detail);
            voList.add(vo);
        }
        pageInfoResp.setList(voList);
        return pageInfoResp;
    }

    private void sortList(List<WithdrawDetailVo> voList){
        if (voList.size() > 1) {
            Collections.sort(voList, new Comparator<WithdrawDetailVo>() {
                @Override
                public int compare(WithdrawDetailVo o1, WithdrawDetailVo o2) {
                    if(Long.parseLong(o1.getDetailOrderNo())-(Long.parseLong(o2.getDetailOrderNo()))>0){
                        return 1;
                    }else if(Long.parseLong(o1.getDetailOrderNo())-(Long.parseLong(o2.getDetailOrderNo()))<0){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        }
        for(int i = 0;i<voList.size();i++ ){
            voList.get(i).setIndexNo("00" + (i + 1));
        }
    }

    private void transDetaiInfo(WithdrawDetailVo vo,TAcctDetail detail){
        TAcct acct = acctService.findAcctById(detail.getAcctId());
        vo.setDetailOrderNo(detail.getId()+"");
        vo.setUserType(SystemConstant.ACCOUNT_USER_TYPE.equals(acct.getType())?"个人":"企业");
        if(SystemConstant.ACCOUNT_AGENY_TYPE.equals(acct.getType())){
            TAgency agency = agencyService.findByAgencyId(acct.getPartyId());
            vo.setUserName(agency.getName());
            vo.setRegistMobile(agency.getMobile());
        }else {
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(acct.getPartyId());
            vo.setUserName(accountBaseDto.getName());
            vo.setRegistMobile(accountBaseDto.getMobile());
        }
        vo.setAmount(detail.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)+"");
        if (detail.getBankAccountId() != null) {
            TBankAccount bankAccount = tBankService.getBankAccountById(detail.getBankAccountId());
            if(StringUtils.isEmpty(bankAccount.getBankCode())){
                vo.setBankName(bankAccount.getBankName());
            }else{
                TBank bank = tBankService.getBankByCode(bankAccount.getBankCode());
                vo.setBankName(bank.getName());
            }
            vo.setBankAccountNo(bankAccount.getBankAccountNo());
            vo.setSubBankName(bankAccount.getSubBankName());
        }
        if(detail.getInvoiceId() != null){
            TInvoice invoice = acctService.findInvoiceById(detail.getInvoiceId());
            vo.setInvoiceCode(invoice.getCode());
            vo.setInvoiceNum(invoice.getNum());
            vo.setInvoiceType(invoice.getType());
            vo.setInvoiceImgUrl(invoice.getImgurl());
            vo.setInvoiceLogisticsCompany(invoice.getLogisticsCompany());
            vo.setInvoiceLogisticsNo(invoice.getLogisticsNo());
        }
        vo.setStatus(detail.getStatus());
        setFrontAcctDetailStatusDesc(vo);
        vo.setPayTime(detail.getPayTime());

        if(detail.getPayBankAccountId() != null){
            TBankAccount payBankAccount = tBankService.getBankAccountById(detail.getPayBankAccountId());
            vo.setPayBankName(payBankAccount.getBankName());
            vo.setPayBankAccountNo(payBankAccount.getBankAccountNo());
            vo.setPayBankDetailNo(detail.getPayDetailNo());
        }

        if(detail.getPayOperatorId() != null){
            Staff payStaff = staffService.getById(detail.getPayOperatorId());
            vo.setPayAdminUserName(payStaff.getName());
        }

        vo.setHcReason(detail.getHcReason());
        if(detail.getHcTime() != null){
            vo.setHcTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getHcTime()));
        }

        if(detail.getHcOperatorId() != null){
            Staff hcStaff = staffService.getById(detail.getHcOperatorId());
                vo.setHcAdmiUserName(hcStaff.getName());
        }


        vo.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getCreateTime()));
        if (AccountEnum.AcctDetailStatus.FIRST_VERIFY_FAIL.getKey().equals(detail.getStatus())) {
            vo.setRefuseReason(detail.getFirstVerifyRefuseReason());
        }
        if (AccountEnum.AcctDetailStatus.HAS_MARK_HC.getKey().equals(detail.getStatus())) {
            vo.setRefuseReason(detail.getHcReason());
        }
    }

    private void setFrontAcctDetailStatusDesc(WithdrawDetailVo vo) {
        // 待审核
        if (AccountEnum.AcctDetailStatus.INIT.getKey().equals(vo.getStatus())
                || AccountEnum.AcctDetailStatus.FIRST_VERIFY_SUCCESS.getKey().equals(vo.getStatus())
                || AccountEnum.AcctDetailStatus.HAS_SIGN_CONTRACT.getKey().equals(vo.getStatus())
                || AccountEnum.AcctDetailStatus.HAS_PROVIDE_INVOICE.getKey().equals(vo.getStatus())
                || AccountEnum.AcctDetailStatus.INVOICE_VERIFY_FAIL.getKey().equals(vo.getStatus())) {
            vo.setPayStatus(AccountEnum.FrontAcctDetailStatus.NEED_VERIFY.getValue());
        }
        // 已完成
        if (AccountEnum.AcctDetailStatus.HAS_PAY.getKey().equals(vo.getStatus())) {
            vo.setPayStatus(AccountEnum.FrontAcctDetailStatus.HAS_PAY.getValue());
        }
        // 待出款
        if (AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey().equals(vo.getStatus())
                || AccountEnum.AcctDetailStatus.LAST_VERIFY_SUCCESS.getKey().equals(vo.getStatus())
                || AccountEnum.AcctDetailStatus.LAST_VERIFY_FAIL.getKey().equals(vo.getStatus())) {
            vo.setPayStatus(AccountEnum.FrontAcctDetailStatus.NEED_PAY.getValue());
        }
        // 已失败
        if (AccountEnum.AcctDetailStatus.FIRST_VERIFY_FAIL.getKey().equals(vo.getStatus())
                || AccountEnum.AcctDetailStatus.HAS_MARK_HC.getKey().equals(vo.getStatus())) {
            vo.setPayStatus(AccountEnum.FrontAcctDetailStatus.VERIFY_FAIL.getValue());
        }
    }
}
