package com.winback.core.service.contract;

import com.winback.core.facade.contract.req.AppShoppingCartReq;
import com.winback.core.facade.contract.req.AppletShoppingCartReq;
import com.winback.core.facade.contract.vo.ContractShoppingCart;

/**
 * @author xdrodger
 * @Title: ShoppingCartService
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 10:52
 */
public interface ShoppingCartService {
    ContractShoppingCart getShoppingCart(AppShoppingCartReq.QueryReq req);

    Integer addShoppingCartItem(AppShoppingCartReq.AddItemReq req);

    Integer deleteShoppingCartItem(AppShoppingCartReq.DeleteItemReq req);

    Integer clearShoppingCart(AppShoppingCartReq.QueryReq req);

    ContractShoppingCart getShoppingCart(AppletShoppingCartReq.QueryReq req);

    Integer addShoppingCartItem(AppletShoppingCartReq.AddItemReq req);

    Integer deleteShoppingCartItem(AppletShoppingCartReq.DeleteItemReq req);

    Integer clearShoppingCart(AppletShoppingCartReq.QueryReq req);
}
