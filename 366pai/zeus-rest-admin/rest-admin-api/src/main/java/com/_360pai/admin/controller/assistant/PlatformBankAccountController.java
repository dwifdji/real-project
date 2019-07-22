package com._360pai.admin.controller.assistant;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.TBankAccountReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xdrodger
 * @Title: PlatformBankAccountController
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/6 19:04
 */
@RestController
@RequestMapping(value = "/admin/platform/bank/account", produces = "application/json;charset=UTF-8")
public class PlatformBankAccountController  extends AbstractController {
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    /**
     * 平台银行账户列表接口
     */
    @RequiresPermissions("cwgl_txgl:platform_bank_account_list")
    @GetMapping(value = "/list")
    public ResponseModel bankAccountList(TBankAccountReq.BaseReq req) {
        req.setAcctId(-1);
        return ResponseModel.succ(accountFacade.getPlatformTBankAccounts(req));
    }

    /**
     * 平台银行账户添加接口
     */
    @PostMapping(value = "/add")
    public ResponseModel addBankAccount(@Valid @RequestBody TBankAccountReq.PlatformCreateReq req) {
        return ResponseModel.succ(accountFacade.addPlatformTBankAccount(req));
    }

    /**
     * 平台银行账户修改接口
     */
    @PostMapping(value = "/update")
    public ResponseModel updateBankAccount(@Valid @RequestBody TBankAccountReq.PlatformUpdateReq req) {
        return ResponseModel.succ(accountFacade.updatePlatformTBankAccount(req));
    }

    /**
     * 平台银行账户切换状态接口
     */
    @PostMapping(value = "/toggle/status")
    public ResponseModel toggleStatus(@Valid @RequestBody TBankAccountReq.BaseReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        return ResponseModel.succ(accountFacade.togglePlatformTBankAccountStatus(req));
    }
}
