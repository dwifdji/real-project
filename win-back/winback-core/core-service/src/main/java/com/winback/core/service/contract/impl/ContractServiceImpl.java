package com.winback.core.service.contract.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.AppletReq;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.file.FilePathUtils;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.commons.constants.ContractEnum;
import com.winback.core.dao.account.TAccountDao;
import com.winback.core.dao.account.TAccountExtBindDao;
import com.winback.core.dao.account.TSysStaffDao;
import com.winback.core.dao.contract.*;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade.contract.req.AdminContractReq;
import com.winback.core.facade.contract.req.AppContractReq;
import com.winback.core.facade.contract.req.AppletContractReq;
import com.winback.core.facade.contract.resp.AppContractResp;
import com.winback.core.facade.contract.resp.AppletContractResp;
import com.winback.core.facade.contract.vo.*;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.core.model.contract.*;
import com.winback.core.service.assistant.EmailService;
import com.winback.core.service.assistant.ExceptionService;
import com.winback.core.service.assistant.PushAppMessageService;
import com.winback.core.service.assistant.PushAppletMessageService;
import com.winback.core.service.contract.ContractService;
import com.winback.core.utils.ReqConvertUtil;
import com.winback.core.utils.RespConvertUtil;
import com.winback.gateway.controller.req.wx.WXACodeUnLimitReq;
import com.winback.gateway.facade.WxFacade;
import com.winback.gateway.resp.wxpay.WXACodeUnLimitResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: ContractServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 09:32
 */
@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private TContractDao contractDao;
    @Autowired
    private TContractBigTypeDao contractBigTypeDao;
    @Autowired
    private TContractTypeDao contractTypeDao;
    @Autowired
    private TContractSearchRecordDao searchRecordDao;
    @Autowired
    private TAccountContractMapDao accountContractMapDao;
    @Autowired
    private TAppletContractSearchRecordDao appletContractSearchRecordDao;
    @Autowired
    private TAccountExtBindDao accountExtBindDao;
    @Autowired
    private TFavoriteContractDao favoriteContractDao;
    @Autowired
    private TAppletFavoriteContractDao appletFavoriteContractDao;
    @Autowired
    private TContractShoppingCartDao shoppingCartDao;
    @Autowired
    private TAppletContractShoppingCartDao appletContractShoppingCartDao;
    @Autowired
    private TContractDownloadRecordDao downloadRecordDao;
    @Autowired
    private TContractOrderDao orderDao;
    @Autowired
    private TContractInvoiceDao invoiceDao;
    @Autowired
    private TContractTypeMapDao contractTypeMapDao;
    @Autowired
    private TContractInvoiceOrderMapDao invoiceOrderMapDao;
    @Autowired
    private TSysStaffDao staffDao;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private PushAppMessageService pushAppMessageService;
    @Autowired
    private EmailService emailService;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private PushAppletMessageService pushAppletMessageService;
    @Reference(version = "1.0.0")
    private WxFacade wxFacade;

    @Override
    public PageInfoResp<Contract> getContractList(AppContractReq.QueryReq req) {
        PageInfoResp<Contract> resp = new PageInfoResp<>();
        List<Contract> resultList = new ArrayList<>();
        if (req.getLoginId() != null && StringUtils.isNotEmpty(req.getQ())) {
            // 保存搜索记录
            searchRecordDao.saveSearchRecord(req.getLoginId(), req.getQ());
        }
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(req);
        PageInfo<TContract> pageInfo = contractDao.getFrontList(req.getPage(), req.getPerPage(), params, "");
        for (TContract item : pageInfo.getList()) {
            Contract vo = RespConvertUtil.convertToContract(item);
            vo.setContractType(contractTypeMapDao.getFullName(item.getId()));
            resultList.add(vo);
        }
        if (req.getLoginId() != null) {
            List<Integer> favoriteContractIdList = favoriteContractDao.getContractIdList(req.getLoginId());
            List<Integer> shoppingCartContractIdList = shoppingCartDao.getContractIdList(req.getLoginId());
            List<Integer> purchasedContractIdList = accountContractMapDao.getContractIdList(req.getLoginId());
            for (Contract vo : resultList) {
                if (favoriteContractIdList.contains(vo.getId())) {
                    vo.setFavoriteFlag(true);
                }
                if (shoppingCartContractIdList.contains(vo.getId())) {
                    vo.setShoppingCartFlag(true);
                }
                if (purchasedContractIdList.contains(vo.getId())) {
                    vo.setPurchasedFlag(true);
                }
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public Contract getContract(AppContractReq.QueryReq req) {
        TContract contract = contractDao.selectById(req.getContractId());
        if (contract == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Contract vo = RespConvertUtil.convertToContract(contract);
        vo.setContractType(contractTypeMapDao.getFullName(contract.getId()));
        if (req.getLoginId() != null) {
            if (accountContractMapDao.hasBuy(req.getLoginId(), req.getContractId())) {
                vo.setPurchasedFlag(true);
            }
            TFavoriteContract favoriteContract = favoriteContractDao.findBy(req.getLoginId(), req.getContractId());
            if (favoriteContract != null) {
                vo.setFavoriteFlag(true);
            }
            if (shoppingCartDao.isInShoppingCart(req.getLoginId(), req.getContractId())) {
                vo.setShoppingCartFlag(true);
            }
        }
        contractDao.addViewCount(req.getContractId());
        return vo;
    }

    @Override
    public PageInfoResp<Contract> getPossessedContractList(AppContractReq.QueryReq req) {
        PageInfoResp<Contract> resp = new PageInfoResp<>();
        List<Contract> resultList = new ArrayList<>();
        if (req.getOrderId() != null) {
            List<TContract> list = orderDao.getContractList(req.getOrderId());
            for (TContract item : list) {
                Contract vo = RespConvertUtil.convertToContract(item);
                resultList.add(vo);
            }
            resp.setList(resultList);
            resp.setHasNextPage(false);
            resp.setTotal(0);
            return resp;
        } else {
            PageInfo<TContract> pageInfo = contractDao.getPossessedContractList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
            for (TContract item : pageInfo.getList()) {
                Contract vo = RespConvertUtil.convertToContract(item);
                resultList.add(vo);
            }
            resp.setList(resultList);
            resp.setHasNextPage(pageInfo.isHasNextPage());
            resp.setTotal(pageInfo.getTotal());
            return resp;
        }
    }

    @Override
    public ListResp<String> getSearchRecordList(AppContractReq.QueryReq req) {
        ListResp<String> resp = new ListResp<>();
        if (req.getLoginId() != null) {
            List<String> list = searchRecordDao.getKeywordList(req.getLoginId());
            resp.setList(list);
        } else {
            resp.setList(Collections.EMPTY_LIST);
        }
        return resp;
    }

    @Override
    public void clearSearchRecord(AppContractReq.QueryReq req) {
        if (req.getLoginId() != null) {
            searchRecordDao.clearSearchRecord(req.getLoginId());
        }
    }

    @Override
    public ListResp<String> download(AppContractReq.DownloadReq req) {
        ListResp<String> resp = new ListResp<>();
        List<Integer> contractIds = req.getContractIds();
        List<String> list = new ArrayList<>();
        for (Integer contractId : contractIds) {
            if (!accountContractMapDao.hasBuy(req.getLoginId(), contractId)) {
                continue;
            }
            TContract contract = contractDao.selectById(contractId);
            if (contract == null) {
                continue;
            }
            list.add(contract.getDownloadUrl());
            contractDao.addDownloadCount(contractId);
            downloadRecordDao.saveDownloadRecord(req.getLoginId(), contractId);
        }
        pushAppMessageService.pushContractDownloadSuccessMsg(req.getLoginId());
        resp.setList(list);
        return resp;
    }

    @Transactional
    @Override
    public Integer favor(AppContractReq.QueryReq req) {
        TFavoriteContract favoriteContract = favoriteContractDao.findBy(req.getLoginId(), req.getContractId());
        if (favoriteContract == null) {
            favoriteContract = new TFavoriteContract();
            favoriteContract.setAccountId(req.getLoginId());
            favoriteContract.setContractId(req.getContractId());
            int result = favoriteContractDao.insert(favoriteContract);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            result = contractDao.addFavoriteCount(req.getContractId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else if (favoriteContract.getDeleteFlag()) {
            favoriteContract.setDeleteFlag(false);
            int result = favoriteContractDao.updateById(favoriteContract);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            result = contractDao.addFavoriteCount(req.getContractId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        TContract contract = contractDao.selectById(req.getContractId());
        return contract.getFavoriteCount();
    }

    @Transactional
    @Override
    public Integer unfavor(AppContractReq.QueryReq req) {
        TFavoriteContract favoriteContract = favoriteContractDao.findBy(req.getLoginId(), req.getContractId());
        if (favoriteContract != null && !favoriteContract.getDeleteFlag()) {
            favoriteContract.setDeleteFlag(true);
            int result = favoriteContractDao.updateById(favoriteContract);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            result = contractDao.subFavoriteCount(req.getContractId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        TContract contract = contractDao.selectById(req.getContractId());
        return contract.getFavoriteCount();
    }

    @Override
    public PageInfoResp<Contract> getContractList(AppletContractReq.QueryReq req) {
        PageInfoResp<Contract> resp = new PageInfoResp<>();
        List<Contract> resultList = new ArrayList<>();
        if (req.getLoginId() != null && StringUtils.isNotBlank(req.getQ())) {
            // 保存搜索记录
            appletContractSearchRecordDao.saveSearchRecord(req.getLoginId(), req.getQ());
        }
        PageInfo<TContract> pageInfo = contractDao.getFrontList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (TContract item : pageInfo.getList()) {
            Contract vo = RespConvertUtil.convertToContract(item);
            resultList.add(vo);
        }
        if (req.getLoginId() != null) {
            TAccountExtBind extBind = accountExtBindDao.selectById(req.getLoginId());
            List<Integer> favoriteContractIdList;
            List<Integer> shoppingCartContractIdList;
            List<Integer> purchasedContractIdList;
            if (extBind.hasBindAccount()) {
                favoriteContractIdList = favoriteContractDao.getContractIdList(extBind.getAccountId());
                shoppingCartContractIdList = shoppingCartDao.getContractIdList(extBind.getAccountId());
                purchasedContractIdList = accountContractMapDao.getContractIdList(extBind.getAccountId());
            } else {
                favoriteContractIdList = appletFavoriteContractDao.getContractIdList(req.getLoginId());
                shoppingCartContractIdList = appletContractShoppingCartDao.getContractIdList(req.getLoginId());
                purchasedContractIdList = Collections.EMPTY_LIST;
            }
            for (Contract vo : resultList) {
                if (favoriteContractIdList.contains(vo.getId())) {
                    vo.setFavoriteFlag(true);
                }
                if (shoppingCartContractIdList.contains(vo.getId())) {
                    vo.setShoppingCartFlag(true);
                }
                if (purchasedContractIdList.contains(vo.getId())) {
                    vo.setPurchasedFlag(true);
                }
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public Contract getContract(AppletContractReq.QueryReq req) {
        TContract contract = contractDao.selectById(req.getContractId());
        if (contract == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Contract vo = RespConvertUtil.convertToContract(contract);
        if (req.getLoginId() != null) {
            TAccountExtBind extBind = accountExtBindDao.selectById(req.getLoginId());
            if (extBind.hasBindAccount()) {
                if (accountContractMapDao.hasBuy(extBind.getAccountId(), req.getContractId())) {
                    vo.setPurchasedFlag(true);
                }
                TFavoriteContract favoriteContract = favoriteContractDao.findBy(extBind.getAccountId(), req.getContractId());
                if (favoriteContract != null) {
                    vo.setFavoriteFlag(true);
                }
                if (shoppingCartDao.isInShoppingCart(extBind.getAccountId(), req.getContractId())) {
                    vo.setShoppingCartFlag(true);
                }
            } else {
                if (accountContractMapDao.hasBuy(req.getLoginId(), req.getContractId())) {
                    vo.setPurchasedFlag(true);
                }
                TAppletFavoriteContract favoriteContract = appletFavoriteContractDao.findBy(req.getLoginId(), req.getContractId());
                if (favoriteContract != null) {
                    vo.setFavoriteFlag(true);
                }
                if (appletContractShoppingCartDao.isInShoppingCart(req.getLoginId(), req.getContractId())) {
                    vo.setShoppingCartFlag(true);
                }
            }
        }
        contractDao.addViewCount(req.getContractId());
        return vo;
    }

    @Override
    public ListResp<String> getSearchRecordList(AppletContractReq.QueryReq req) {
        ListResp<String> resp = new ListResp<>();
        if (req.getLoginId() != null) {
            //List<String> list;
            //TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
            //if (accountExtBind.hasBindAccount()) {
            //    list = searchRecordDao.getKeywordList(accountExtBind.getAccountId());
            //} else {
            //    list = appletContractSearchRecordDao.getKeywordList(accountExtBind.getId());
            //}
            List<String> list = appletContractSearchRecordDao.getKeywordList(req.getLoginId());
            resp.setList(list);
        } else {
            resp.setList(Collections.EMPTY_LIST);
        }
        return resp;
    }

    @Override
    public void clearSearchRecord(AppletContractReq.QueryReq req) {
        if (req.getLoginId() != null) {
            //TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
            //if (accountExtBind.hasBindAccount()) {
            //    searchRecordDao.clearSearchRecord(accountExtBind.getAccountId());
            //} else {
            //    appletContractSearchRecordDao.clearSearchRecord(accountExtBind.getId());
            //}
            appletContractSearchRecordDao.clearSearchRecord(req.getLoginId());
        }
    }

    @Override
    public void deleteSearchRecord(AppletContractReq.QueryReq req) {
        if (StringUtils.isNotBlank(req.getKeyword())) {
            appletContractSearchRecordDao.deleteSearchRecord(req.getLoginId(), req.getKeyword());
        }
    }

    @Override
    public ListResp<String> download(AppletContractReq.DownloadReq req) {
        ListResp<String> resp = new ListResp<>();
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (!accountExtBind.hasBindAccount()) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<Integer> contractIds = req.getContractIds();
        List<Integer> list = accountContractMapDao.getContractIdList(accountExtBind.getAccountId());
        for (Integer contractId : contractIds) {
            if (!list.contains(contractId)) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        List<String> filesUrl = new ArrayList<>();
        for (Integer contractId : contractIds) {
            TContract contract = contractDao.selectById(contractId);
            filesUrl.add(contract.getDownloadUrl());
        }
        emailService.sendContractDownloadEmail(req.getEmail(), filesUrl);
        for (Integer contractId : contractIds) {
            contractDao.addDownloadCount(contractId);
            downloadRecordDao.saveDownloadRecord(accountExtBind.getAccountId(), req.getEmail(), contractId);
        }
        pushAppletMessageService.pushContractDownloadSuccessMsg(req.getLoginId());
        return resp;
    }

    @Transactional
    @Override
    public Integer favor(AppletContractReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.hasBindAccount()) {
            AppContractReq.QueryReq queryReq = new AppContractReq.QueryReq();
            queryReq.setLoginId(accountExtBind.getAccountId());
            queryReq.setContractId(req.getContractId());
            return favor(queryReq);
        } else {
            TAppletFavoriteContract favoriteContract = appletFavoriteContractDao.findBy(req.getLoginId(), req.getContractId());
            if (favoriteContract == null) {
                favoriteContract = new TAppletFavoriteContract();
                favoriteContract.setExtBindId(req.getLoginId());
                favoriteContract.setContractId(req.getContractId());
                int result = appletFavoriteContractDao.insert(favoriteContract);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                result = contractDao.addFavoriteCount(req.getContractId());
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            } else if (favoriteContract.getDeleteFlag()) {
                favoriteContract.setDeleteFlag(false);
                int result = appletFavoriteContractDao.updateById(favoriteContract);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                result = contractDao.addFavoriteCount(req.getContractId());
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
            TContract contract = contractDao.selectById(req.getContractId());
            return contract.getFavoriteCount();
        }
    }

    @Transactional
    @Override
    public Integer unfavor(AppletContractReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.hasBindAccount()) {
            AppContractReq.QueryReq queryReq = new AppContractReq.QueryReq();
            queryReq.setLoginId(accountExtBind.getAccountId());
            queryReq.setContractId(req.getContractId());
            return unfavor(queryReq);
        } else {
            TAppletFavoriteContract favoriteContract = appletFavoriteContractDao.findBy(req.getLoginId(), req.getContractId());
            if (favoriteContract != null && !favoriteContract.getDeleteFlag()) {
                favoriteContract.setDeleteFlag(true);
                int result = appletFavoriteContractDao.updateById(favoriteContract);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                result = contractDao.subFavoriteCount(req.getContractId());
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
            TContract contract = contractDao.selectById(req.getContractId());
            return contract.getFavoriteCount();
        }
    }

    @Override
    public PageInfoResp<Contract> getPossessedContractList(AppletContractReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        AppContractReq.QueryReq queryReq = new AppContractReq.QueryReq();
        queryReq.setLoginId(accountExtBind.getAccountId());
        queryReq.setPeriod(req.getPeriod());
        queryReq.setPage(req.getPage());
        queryReq.setPerPage(req.getPerPage());
        return getPossessedContractList(queryReq);
    }

    @Override
    public PageInfoResp<Contract> getFavoriteContractList(AppReq req) {
        PageInfoResp<Contract> resp = new PageInfoResp<>();
        List<Contract> resultList = new ArrayList<>();
        PageInfo<TContract> pageInfo = favoriteContractDao.getFavoriteContractList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (TContract item : pageInfo.getList()) {
            Contract vo = RespConvertUtil.convertToContract(item);
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<Contract> getFavoriteContractList(AppletReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.hasBindAccount()) {
            AppReq appReq = new AppReq();
            appReq.setLoginId(accountExtBind.getAccountId());
            return getFavoriteContractList(appReq);
        } else {
            PageInfoResp<Contract> resp = new PageInfoResp<>();
            List<Contract> resultList = new ArrayList<>();
            PageInfo<TContract> pageInfo = appletFavoriteContractDao.getFavoriteContractList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
            for (TContract item : pageInfo.getList()) {
                Contract vo = RespConvertUtil.convertToContract(item);
                resultList.add(vo);
            }
            resp.setList(resultList);
            resp.setHasNextPage(pageInfo.isHasNextPage());
            resp.setTotal(pageInfo.getTotal());
            return resp;
        }
    }

    @Override
    public ListResp<ContractBigType> getContractBigTypeList() {
        ListResp<ContractBigType> resp = new ListResp<>();
        List<ContractBigType> list = new ArrayList<>();
        ContractBigType first = new ContractBigType();
        first.setName("全部");
        first.setId("");
        list.add(first);
        List<TContractBigType> itemList = contractBigTypeDao.getFrontList();
        for (TContractBigType item : itemList) {
            ContractBigType vo = new ContractBigType();
            vo.setId(item.getId() + "");
            vo.setName(item.getName());
            vo.setContractTypes(getContractTypeList(item.getId()));
            list.add(vo);
        }
        resp.setList(list);
        return resp;
    }

    @Override
    public ListResp<ContractBigType> getBackContractBigTypeList() {
        ListResp<ContractBigType> resp = new ListResp<>();
        List<ContractBigType> list = new ArrayList<>();
        ContractBigType first = new ContractBigType();
        first.setName("全部");
        first.setId("");
        list.add(first);
        List<TContractBigType> itemList = contractBigTypeDao.getBackList();
        for (TContractBigType item : itemList) {
            ContractBigType vo = new ContractBigType();
            vo.setId(item.getId() + "");
            vo.setName(item.getName());
            vo.setContractTypes(getBackContractTypeList(item.getId()));
            list.add(vo);
        }
        resp.setList(list);
        return resp;
    }

    @Override
    public String getLatestEmail(Integer accountId) {
        return downloadRecordDao.getLatestEmail(accountId);
    }

    @Override
    public AppContractResp.PreInvoiceResp preInvoice(AppContractReq.PreInvoiceReq req) {
        AppContractResp.PreInvoiceResp resp = new AppContractResp.PreInvoiceResp();
        BigDecimal amount = preInvoice(req.getLoginId(), req.getOrderIds());
        resp.setAmount(amount);
        return resp;
    }

    private BigDecimal preInvoice(Integer accountId, List<Integer> orderIds) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Integer orderId : orderIds) {
            TContractOrder order = orderDao.selectById(orderId);
            if (order ==  null || orderDao.hasInvoice(orderId)) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!ContractEnum.PayStatus.SUCCESS.getKey().equals(order.getPayStatus())) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!order.getAccountId().equals(accountId)) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            amount = amount.add(order.getAmount());
        }
        return amount;
    }

    @Transactional
    @Override
    public Integer invoice(AppContractReq.InvoiceReq req) {
        List<Integer> orderIds = req.getOrderIds();
        BigDecimal amount = BigDecimal.ZERO;
        List<TContractOrder> orders = new ArrayList<>();
        for (Integer orderId : orderIds) {
            TContractOrder order = orderDao.selectById(orderId);
            if (order == null || orderDao.hasInvoice(orderId)) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!order.getAccountId().equals(req.getLoginId())) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            amount = amount.add(order.getAmount());
            orders.add(order);
        }
        TContractInvoice invoice = ReqConvertUtil.convertToTContractInvoice(req);
        invoice.setAccountId(req.getLoginId());
        invoice.setAmount(amount);
        int result = invoiceDao.insert(invoice);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        invoiceOrderMapDao.createMap(invoice.getId(), orderIds);
        pushAppMessageService.pushContractInvoiceApplySuccessMsg(req.getLoginId());
        return invoice.getId();
    }

    @Override
    public PageInfoResp<ContractInvoice> getContractInvoiceList(AppContractReq.QueryReq req) {
        PageInfoResp<ContractInvoice> resp = new PageInfoResp<>();
        PageInfo<TContractInvoice> pageInfo = invoiceDao.getFrontList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "i.id desc");
        List<ContractInvoice> resultList = new ArrayList<>();
        for (TContractInvoice item : pageInfo.getList()) {
            ContractInvoice vo = RespConvertUtil.convertToContractInvoice(item);
            if (req.getId() != null) {
                vo.setOrders(getRelatedOrders(vo));
            }
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    private List<ContractOrder> getRelatedOrders(ContractInvoice invoice) {
        List<ContractOrder> resultList = new ArrayList<>();
        List<TContractOrder> orders = invoiceOrderMapDao.getOrderList(invoice.getId());
        for (TContractOrder item : orders) {
            if (orderDao.hasInvoice(item.getId())) {
                continue;
            }
            ContractOrder vo = RespConvertUtil.convertToContractOrder(item);
            resultList.add(vo);
        }
        return resultList;
    }

    @Override
    public AppletContractResp.PreInvoiceResp preInvoice(AppletContractReq.PreInvoiceReq req) {
        AppletContractResp.PreInvoiceResp resp = new AppletContractResp.PreInvoiceResp();
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        BigDecimal amount = preInvoice(accountExtBind.getAccountId(), req.getOrderIds());
        resp.setAmount(amount);
        return resp;
    }

    @Override
    public Integer invoice(AppletContractReq.InvoiceReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        List<Integer> orderIds = req.getOrderIds();
        BigDecimal amount = BigDecimal.ZERO;
        List<TContractOrder> orders = new ArrayList<>();
        for (Integer orderId : orderIds) {
            TContractOrder order = orderDao.selectById(orderId);
            if (order == null || orderDao.hasInvoice(orderId)) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!order.getAccountId().equals(accountExtBind.getAccountId())) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            amount = amount.add(order.getAmount());
            orders.add(order);
        }
        TContractInvoice invoice = ReqConvertUtil.convertToTContractInvoice(req);
        invoice.setAccountId(accountExtBind.getAccountId());
        invoice.setAmount(amount);
        int result = invoiceDao.insert(invoice);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        invoiceOrderMapDao.createMap(invoice.getId(), orderIds);
        pushAppletMessageService.pushContractInvoiceApplySuccessMsg(req.getLoginId());
        return invoice.getId();
    }

    @Override
    public PageInfoResp<ContractInvoice> getContractInvoiceList(AppletContractReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        req.setLoginId(accountExtBind.getAccountId());
        PageInfoResp<ContractInvoice> resp = new PageInfoResp<>();
        PageInfo<TContractInvoice> pageInfo = invoiceDao.getFrontList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "i.id desc");
        List<ContractInvoice> resultList = new ArrayList<>();
        for (TContractInvoice item : pageInfo.getList()) {
            ContractInvoice vo = RespConvertUtil.convertToContractInvoice(item);
            if (req.getId() != null) {
                vo.setOrders(getRelatedOrders(vo));
            }
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<Contract> getContractList(AdminContractReq.QueryReq req) {
        PageInfoResp<Contract> resp = new PageInfoResp<>();
        List<Contract> resultList = new ArrayList<>();
        PageInfo<TContract> pageInfo = contractDao.getBackgroundList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (TContract item : pageInfo.getList()) {
            Contract vo = RespConvertUtil.convertToContract(item);
            vo.setContractType(contractTypeMapDao.getFullName(item.getId()));
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public Contract getContract(AdminContractReq.QueryReq req) {
        TContract contract = contractDao.selectById(req.getContractId());
        if (contract == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Contract vo = RespConvertUtil.convertToContract(contract);
        vo.setContractType(contractTypeMapDao.getFullName(contract.getId()));
        TContractType contractType = contractTypeMapDao.getContractType(contract.getId());
        if (contractType != null) {
            vo.setContractTypeId(contractType.getId());
            vo.setContractBigTypeId(contractType.getBigTypeId());
        }
        return vo;
    }

    @Transactional
    @Override
    public Integer addContract(AdminContractReq.AddReq req) {
        TContract contract = ReqConvertUtil.convertToTContract(req);
        int result = contractDao.insert(contract);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        contractTypeMapDao.saveContractTypeMap(contract.getId(), req.getContractTypeId());
        if (req.getSaleFlag() != null && req.getSaleFlag()) {
            String typeName = contractTypeDao.getName(req.getContractTypeId());
            pushAppMessageService.pushContractNewArrivalMsg(typeName);
            pushAppletMessageService.pushContractNewArrivalMsg(typeName);
        }
        return contract.getId();
    }

    @Transactional
    @Override
    public Integer editContract(AdminContractReq.EditReq req) {
        TContract contract = contractDao.selectById(req.getId());
        if (contract == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        contract = ReqConvertUtil.convertToTContract(req);
        int result = contractDao.updateById(contract);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (req.getContractTypeId() != null) {
            contractTypeMapDao.syncContractTypeMap(contract.getId(), req.getContractTypeId());
        }
        if (req.getSaleFlag() != null && req.getSaleFlag()) {
            String cache = (String) redisCachemanager.hGet(RedisKeyConstant.CONTRACT_NEW_ARRIVAL, req.getId() + "");
            if (StringUtils.isEmpty(cache)) {
                TContractType contractType = contractTypeMapDao.getContractType(req.getId());
                pushAppMessageService.pushContractNewArrivalMsg(contractType.getName());
                pushAppletMessageService.pushContractNewArrivalMsg(contractType.getName());
                redisCachemanager.hSet(RedisKeyConstant.CONTRACT_NEW_ARRIVAL, req.getId() + "", req.getId() + "");
            }
        }
        return contract.getId();
    }

    @Override
    public PageInfoResp<ContractInvoice> getContractInvoiceList(AdminContractReq.QueryReq req) {
        PageInfoResp<ContractInvoice> resp = new PageInfoResp<>();
        PageInfo<TContractInvoice> pageInfo = invoiceDao.getBackgroundList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "i.id desc");
        List<ContractInvoice> resultList = new ArrayList<>();
        for (TContractInvoice item : pageInfo.getList()) {
            try {
                ContractInvoice vo = RespConvertUtil.convertToContractInvoice(item);
                vo.setOperator(staffDao.getNameAndMobile(item.getOperatorId()));
                vo.setMobile(accountDao.getMobile(item.getAccountId()));
                vo.setOrderNos(invoiceDao.getOrderNoList(item.getId()));
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public Integer invoiceApplyApprove(AdminContractReq.InvoiceVerifyReq req) {
        TContractInvoice invoice = invoiceDao.selectById(req.getId());
        if (invoice == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(invoice.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        invoice.setInvoiceNo(req.getInvoiceNo());
        invoice.setInvoiceImgUrl(req.getInvoiceImgUrl());
        invoice.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        invoice.setOperatorId(req.getLoginId());
        int result = invoiceDao.updateById(invoice);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return invoice.getId();
    }

    @Transactional
    @Override
    public Integer invoiceApplyReject(AdminContractReq.InvoiceVerifyReq req) {
        TContractInvoice invoice = invoiceDao.selectById(req.getId());
        if (invoice == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(invoice.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        invoice.setReason(req.getReason());
        invoice.setStatus(AccountEnum.ApplyStatus.REJECT.getKey());
        invoice.setOperatorId(req.getLoginId());
        int result = invoiceDao.updateById(invoice);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return invoice.getId();
    }

    @Override
    public Integer favoriteContractCount(Integer accountId) {
        return favoriteContractDao.favoriteContractCount(accountId);
    }

    @Override
    public Integer appletFavoriteContractCount(Integer extBindId) {
        return appletFavoriteContractDao.favoriteContractCount(extBindId);
    }

    @Override
    public Integer resetContractAmount(List<List<Object>> rowList) {
        if (rowList == null || rowList.size() < 2) {
            return 0;
        }
        log.info("重置合同金额，rowList={}", JSON.toJSONString(rowList));
        int succ = 0;
        for (int i =0; i <  rowList.size(); i ++) {
            if (i == 0) {
                continue;
            }
            List<Object> row =  rowList.get(i);
            log.info("重置合同金额，row={}, rowData={}", i + 1, JSON.toJSONString(row));
            try {
                if (row.size() < 4) {
                    continue;
                }

                String bigTypeName = (String) row.get(0);
                if (StringUtils.isBlank(bigTypeName)) {
                    continue;
                }
                TContractBigType bigType = contractBigTypeDao.findBy(bigTypeName);
                if (bigType == null) {
                    continue;
                }
                String typeName = (String) row.get(1);
                if (StringUtils.isBlank(typeName)) {
                    continue;
                }
                TContractType type = contractTypeDao.findBy(typeName, bigType.getId());
                if (type == null) {
                    continue;
                }
                String contractName = (String) row.get(2);
                if (StringUtils.isBlank(contractName)) {
                    continue;
                }
                String amtStr = (String) row.get(3);
                if (StringUtils.isBlank(amtStr)) {
                    continue;
                }
                BigDecimal amount = new BigDecimal(amtStr);
                TContract contract = contractDao.findBy(contractName, type.getId());
                if (contract == null) {
                    continue;
                }
                if (contract.getAmount().compareTo(amount) == 0) {
                    continue;
                }
                TContract updateContract = new TContract();
                updateContract.setId(contract.getId());
                updateContract.setAmount(amount);
                int result = contractDao.updateById(updateContract);
                succ += result;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("重置合同金额失败，row={}, rowData={}", i + 1, JSON.toJSONString(row));
            }
        }
        return succ;
    }

    @Override
    public AppletContractResp.ShareResp getShareInfo(AppletContractReq.ShareReq req) {
        AppletContractResp.ShareResp resp = new AppletContractResp.ShareResp();
        TAccountExtBind extBind = accountExtBindDao.selectById(req.getLoginId());
        if (extBind != null) {
            resp.setNickName(extBind.getNickName());
            resp.setHeadImgUrl(extBind.getHeadImgUrl());
        }
        if (req.getContractId() != null) {
            TContract contract = contractDao.selectById(req.getContractId());
            if (contract != null) {
                resp.setContractName(contract.getName());
                resp.setImgUrl(contract.getFirstImage());
                resp.setAmount(contract.getAmount());
                resp.setLength(contract.getLength());
            }
        }
        resp.setQrCodeUrl(getAppletQrCode(req.getContractId()));
        return resp;
    }

    private String getAppletQrCode(Integer contractId) {
        String cache = (String) redisCachemanager.hGet(RedisKeyConstant.APPLET_CONTRACT_SHARE_IMAGE, getScene(contractId));
        if (StringUtils.isNotEmpty(cache)) {
            return cache;
        }
        WXACodeUnLimitReq wxaCodeUnLimitReq = new WXACodeUnLimitReq();
        if (contractId != null) {
            wxaCodeUnLimitReq.setPage("pages/details/details");
        } else {
            wxaCodeUnLimitReq.setPage("pages/index/index");
        }
        wxaCodeUnLimitReq.setScene(getScene(contractId));
        log.info("获取合同小程序二维码，入参={}", JSON.toJSONString(wxaCodeUnLimitReq));
        WXACodeUnLimitResp wxaCodeUnLimitResp = wxFacade.getWXACodeUnLimit(wxaCodeUnLimitReq);
        if (wxaCodeUnLimitResp == null || !wxaCodeUnLimitResp.getCode().equals("000")) {
            log.error("获取合同小程序二维码二维码失败，入参={}，出参={}", JSON.toJSONString(wxaCodeUnLimitReq), JSON.toJSONString(wxaCodeUnLimitResp));
            return "";
        }
        redisCachemanager.hSet(RedisKeyConstant.APPLET_CONTRACT_SHARE_IMAGE, getScene(contractId), wxaCodeUnLimitResp.getImgUrl());
        return wxaCodeUnLimitResp.getImgUrl();
    }

    private String getScene(Integer contractId) {
        if (contractId != null) {
            return contractId + "";
        }
        return "0";
    }

    private List<ContractType> getContractTypeList(Integer bigTypeId) {
        List<ContractType> list = new ArrayList<>();
        ContractType first = new ContractType();
        first.setName("全部");
        first.setId("");
        list.add(first);
        List<TContractType> itemList = contractTypeDao.findFrontBy(bigTypeId);
        for (TContractType item : itemList) {
            ContractType vo = new ContractType();
            vo.setId(item.getId() + "");
            vo.setName(item.getName());
            list.add(vo);
        }
        return list;
    }

    private List<ContractType> getBackContractTypeList(Integer bigTypeId) {
        List<ContractType> list = new ArrayList<>();
        ContractType first = new ContractType();
        first.setName("全部");
        first.setId("");
        list.add(first);
        List<TContractType> itemList = contractTypeDao.findBackBy(bigTypeId);
        for (TContractType item : itemList) {
            ContractType vo = new ContractType();
            vo.setId(item.getId() + "");
            vo.setName(item.getName());
            list.add(vo);
        }
        return list;
    }
}
