package com.winback.web.controller.contract;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.core.facade.contract.req.AppShoppingCartReq;
import com.winback.web.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdrodger
 * @Title: ContractController
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 10:06
 */
@Slf4j
@RestController
public class ContractShoppingCartController extends AbstractController {

    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;

    /**
     * 获取购物车信息接口
     */
    @GetMapping(value = "/confined/contract/shopping/cart")
    public ResponseModel getShoppingCart(AppShoppingCartReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getShoppingCart(req));
    }

    /**
     * 添加购物车条目接口
     */
    @PostMapping(value = "/confined/contract/shopping/cart/item/add")
    public ResponseModel addShoppingCartItem(@RequestBody AppShoppingCartReq.AddItemReq req) {
        Assert.notNull(req.getContractId(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.addShoppingCartItem(req));
    }

    /**
     * 删除购物车条目接口
     */
    @PostMapping(value = "/confined/contract/shopping/cart/item/delete")
    public ResponseModel deleteShoppingCartItem(@RequestBody AppShoppingCartReq.DeleteItemReq req) {
        Assert.notNull(req.getItemId(), "itemId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.deleteShoppingCartItem(req));
    }

    /**
     * 清空购物车接口
     */
    @PostMapping(value = "/confined/contract/clear/shopping/cart")
    public ResponseModel clearShoppingCart(@RequestBody AppShoppingCartReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.clearShoppingCart(req));
    }

}
