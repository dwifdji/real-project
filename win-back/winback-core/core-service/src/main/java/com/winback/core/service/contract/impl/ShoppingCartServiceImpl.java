package com.winback.core.service.contract.impl;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.dao.account.TAccountExtBindDao;
import com.winback.core.dao.contract.*;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade.contract.req.AppShoppingCartReq;
import com.winback.core.facade.contract.req.AppletShoppingCartReq;
import com.winback.core.facade.contract.vo.ContractShoppingCart;
import com.winback.core.facade.contract.vo.ContractShoppingCartItem;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.core.model.contract.*;
import com.winback.core.service.contract.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ShoppingCartServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 10:52
 */
@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private TContractShoppingCartDao shoppingCartDao;
    @Autowired
    private TContractShoppingCartItemDao shoppingCartItemDao;
    @Autowired
    private TContractDao contractDao;
    @Autowired
    private TAccountExtBindDao accountExtBindDao;
    @Autowired
    private TAppletContractShoppingCartDao appletContractShoppingCartDao;
    @Autowired
    private TAppletContractShoppingCartItemDao appletContractShoppingCartItemDao;
    @Autowired
    private TAccountContractMapDao contractMapDao;

    @Override
    public ContractShoppingCart getShoppingCart(AppShoppingCartReq.QueryReq req) {
        ContractShoppingCart vo = new ContractShoppingCart();
        TContractShoppingCart shoppingCart = shoppingCartDao.findBy(req.getLoginId());
        if (shoppingCart == null) {
            shoppingCart = shoppingCartDao.create(req.getLoginId());
        }
        List<ContractShoppingCartItem> cartItemList = new ArrayList<>();
        List<TContractShoppingCartItem> list = shoppingCartItemDao.findBy(shoppingCart.getId());
        BigDecimal totalAmt = BigDecimal.ZERO;
        for (TContractShoppingCartItem item : list) {
            if (contractMapDao.hasBuy(req.getLoginId(), item.getContractId())) {
                item.setDeleteFlag(true);
                int result = shoppingCartItemDao.updateById(item);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                continue;
            }
            TContract contract = contractDao.selectById(item.getContractId());
            ContractShoppingCartItem cartItem = new ContractShoppingCartItem();
            cartItem.setId(item.getId());
            cartItem.setAmount(contract.getAmount());
            cartItem.setName(contract.getName());
            cartItem.setContractId(item.getContractId());
            cartItemList.add(cartItem);
            totalAmt = totalAmt.add(contract.getAmount());
        }
        vo.setTotal(cartItemList.size());
        vo.setTotalAmt(totalAmt);
        vo.setItemList(cartItemList);
        return vo;
    }

    @Transactional
    @Override
    public Integer addShoppingCartItem(AppShoppingCartReq.AddItemReq req) {
        TContractShoppingCart shoppingCart = shoppingCartDao.findBy(req.getLoginId());
        if (shoppingCart == null) {
            shoppingCart = new TContractShoppingCart();
            shoppingCart.setAccountId(req.getLoginId());
            int result = shoppingCartDao.insert(shoppingCart);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        if (contractMapDao.hasBuy(req.getLoginId(), req.getContractId())) {
            throw new BusinessException("合同已购买");
        }
        TContractShoppingCartItem shoppingCartItem = shoppingCartItemDao.findBy(shoppingCart.getId(), req.getContractId());
        if (shoppingCartItem != null) {
            throw new BusinessException("合同已在购物车内，请勿重复添加");
        }
        shoppingCartItem = new TContractShoppingCartItem();
        shoppingCartItem.setShoppingCartId(shoppingCart.getId());
        shoppingCartItem.setContractId(req.getContractId());
        shoppingCartItem.setNum(req.getNum());
        int result = shoppingCartItemDao.insert(shoppingCartItem);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }

        return shoppingCartItem.getId();
    }

    @Override
    public Integer deleteShoppingCartItem(AppShoppingCartReq.DeleteItemReq req) {
        TContractShoppingCartItem shoppingCartItem = shoppingCartItemDao.selectById(req.getItemId());
        if (shoppingCartItem == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TContractShoppingCart shoppingCart = shoppingCartDao.findBy(req.getLoginId());
        if (shoppingCart == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!shoppingCart.getId().equals(shoppingCartItem.getShoppingCartId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        shoppingCartItem.setDeleteFlag(true);
        int result = shoppingCartItemDao.updateById(shoppingCartItem);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return shoppingCartItem.getId();
    }

    @Override
    public Integer clearShoppingCart(AppShoppingCartReq.QueryReq req) {
        TContractShoppingCart shoppingCart = shoppingCartDao.findBy(req.getLoginId());
        if (shoppingCart == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        shoppingCartItemDao.clearShoppingCart(shoppingCart.getId());
        return shoppingCart.getId();
    }

    @Override
    public ContractShoppingCart getShoppingCart(AppletShoppingCartReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.hasBindAccount()) {
            AppShoppingCartReq.QueryReq queryReq = new AppShoppingCartReq.QueryReq();
            queryReq.setLoginId(accountExtBind.getAccountId());
            return getShoppingCart(queryReq);
        } else {
            ContractShoppingCart vo = new ContractShoppingCart();
            TAppletContractShoppingCart shoppingCart = appletContractShoppingCartDao.findBy(req.getLoginId());
            if (shoppingCart == null) {
                shoppingCart = appletContractShoppingCartDao.create(accountExtBind.getId());
            }
            List<ContractShoppingCartItem> cartItemList = new ArrayList<>();
            List<TAppletContractShoppingCartItem> list = appletContractShoppingCartItemDao.findBy(shoppingCart.getId());
            BigDecimal totalAmt = BigDecimal.ZERO;
            for (TAppletContractShoppingCartItem item : list) {
                TContract contract = contractDao.selectById(item.getContractId());
                ContractShoppingCartItem cartItem = new ContractShoppingCartItem();
                cartItem.setId(item.getId());
                cartItem.setAmount(contract.getAmount());
                cartItem.setName(contract.getName());
                cartItem.setContractId(item.getContractId());
                cartItemList.add(cartItem);
                totalAmt = totalAmt.add(contract.getAmount());
            }
            vo.setTotal(cartItemList.size());
            vo.setTotalAmt(totalAmt);
            vo.setItemList(cartItemList);
            return vo;
        }
    }

    @Transactional
    @Override
    public Integer addShoppingCartItem(AppletShoppingCartReq.AddItemReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.hasBindAccount()) {
            AppShoppingCartReq.AddItemReq addItemReq = new AppShoppingCartReq.AddItemReq();
            addItemReq.setContractId(req.getContractId());
            addItemReq.setLoginId(accountExtBind.getAccountId());
            return addShoppingCartItem(addItemReq);
        } else {
            TAppletContractShoppingCart shoppingCart = appletContractShoppingCartDao.findBy(req.getLoginId());
            if (shoppingCart == null) {
                shoppingCart = new TAppletContractShoppingCart();
                shoppingCart.setExtBindId(req.getLoginId());
                int result = appletContractShoppingCartDao.insert(shoppingCart);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
            TAppletContractShoppingCartItem shoppingCartItem = appletContractShoppingCartItemDao.findBy(shoppingCart.getId(), req.getContractId());
            if (shoppingCartItem != null) {
                throw new BusinessException("合同已在购物车内，请勿重复添加");
            }
            shoppingCartItem = new TAppletContractShoppingCartItem();
            shoppingCartItem.setShoppingCartId(shoppingCart.getId());
            shoppingCartItem.setContractId(req.getContractId());
            shoppingCartItem.setNum(req.getNum());
            int result = appletContractShoppingCartItemDao.insert(shoppingCartItem);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }

            return shoppingCartItem.getId();
        }
    }

    @Override
    public Integer deleteShoppingCartItem(AppletShoppingCartReq.DeleteItemReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.hasBindAccount()) {
            AppShoppingCartReq.DeleteItemReq deleteItemReq = new AppShoppingCartReq.DeleteItemReq();
            deleteItemReq.setItemId(req.getItemId());
            deleteItemReq.setLoginId(accountExtBind.getAccountId());
            return deleteShoppingCartItem(deleteItemReq);
        } else {
            TAppletContractShoppingCartItem shoppingCartItem = appletContractShoppingCartItemDao.selectById(req.getItemId());
            if (shoppingCartItem == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            TAppletContractShoppingCart shoppingCart = appletContractShoppingCartDao.findBy(req.getLoginId());
            if (shoppingCart == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!shoppingCart.getId().equals(shoppingCartItem.getShoppingCartId())) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            shoppingCartItem.setDeleteFlag(true);
            int result = appletContractShoppingCartItemDao.updateById(shoppingCartItem);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            return shoppingCartItem.getId();
        }
    }

    @Override
    public Integer clearShoppingCart(AppletShoppingCartReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.hasBindAccount()) {
            AppShoppingCartReq.QueryReq queryReq = new AppShoppingCartReq.QueryReq();
            queryReq.setLoginId(accountExtBind.getAccountId());
            return clearShoppingCart(queryReq);
        } else {
            TAppletContractShoppingCart shoppingCart = appletContractShoppingCartDao.findBy(req.getLoginId());
            if (shoppingCart == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            appletContractShoppingCartItemDao.clearShoppingCart(shoppingCart.getId());
            return shoppingCart.getId();
        }
    }
}
