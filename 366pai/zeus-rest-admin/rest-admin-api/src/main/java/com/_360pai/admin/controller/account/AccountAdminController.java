package com._360pai.admin.controller.account;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.DownloadUtil;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.req.*;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.core.facade.account.vo.AcctDetailVo;
import com._360pai.core.facade.account.vo.AcctVo;
import com._360pai.core.facade.account.vo.SpvVo;
import com._360pai.core.facade.account.vo.WithdrawAcctDetailVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RuQ on 2018/8/28 10:32
 *
 *
 */
@RestController
@RequestMapping(value = "/admin/account", produces = "application/json;charset=UTF-8")
public class AccountAdminController extends AbstractController {


    public static final Logger LOGGER = LoggerFactory.getLogger(AccountAdminController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private StaffFacade staffFacade;


    /**
     * spv认证通过
     */
    @GetMapping(value = "/approvedSpvAuth")
    public ResponseModel approvedSpvAuth(Integer spvApplyId) {
        LOGGER.info("开始调用  approvedSpvAuth ,参数:{}", spvApplyId);
        if(spvApplyId == null || spvApplyId <=0){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if(accountFacade.verifySpv(spvApplyId,SystemConstant.OPERATION_APPROVE,loadCurLoginId())){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * spv认证拒绝
     */
    @GetMapping(value = "/rejectSpvAuth")
    public ResponseModel rejectSpvAuth(Integer spvApplyId) {
        LOGGER.info("开始调用  rejectSpvAuth ,参数:{}", spvApplyId);
        if(spvApplyId == null || spvApplyId <=0){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if(accountFacade.verifySpv(spvApplyId,SystemConstant.OPERATION_REJECT,loadCurLoginId())){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 获取spv列表
     */
    @GetMapping(value = "/getSpvList")
    public ResponseModel getSpvList(Integer companyId,Integer page,Integer perPage) {
        PageInfoResp<SpvVo> spvVoPageInfoResp = accountFacade.getSpvListByPage(companyId,page,perPage,"admin");
        return ResponseModel.succ(spvVoPageInfoResp);

    }


    /**
     * 个人认证通过
     *  applyId,operator_id reason begin end
     */
    @PostMapping(value = "/approveUserAuth")
    public ResponseModel approvedUserAuth(@RequestBody ApplyUserAuthReq req) {
        LOGGER.info("开始调用  approveUserAuth ,参数:{}", JSON.toJSONString(req));
        if(req.getId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        if(accountFacade.verifyUser(req, SystemConstant.OPERATION_APPROVE)){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 个人认证拒绝
     */
    @PostMapping(value = "/rejectUserAuth")
    public ResponseModel rejectUserAuth(@RequestBody ApplyUserAuthReq req) {
        LOGGER.info("开始调用  approvedUserAuth ,参数:{}", JSON.toJSONString(req));
        if(req.getId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        if(accountFacade.verifyUser(req, SystemConstant.OPERATION_REJECT)){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 企业认证通过
     *  applyId,operator_id reason begin end  category
     */
    @PostMapping(value = "/approveCompanyAuth")
    public ResponseModel approveCompanyAuth(@RequestBody ApplyCompanyAuthReq req) {
        LOGGER.info("开始调用  approveCompanyAuth ,参数:{}", JSON.toJSONString(req));
        if(req.getId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        if(accountFacade.verifyCompany(req, SystemConstant.OPERATION_APPROVE)){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /**
     * 企业认证拒绝
     *  applyId,operator_id reason begin end  category
     */
    @PostMapping(value = "/rejectCompanyAuth")
    public ResponseModel rejectCompanyAuth(@RequestBody ApplyCompanyAuthReq req) {
        LOGGER.info("开始调用  rejectCompanyAuth ,参数:{}", JSON.toJSONString(req));
        if(req.getId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        if(accountFacade.verifyCompany(req, SystemConstant.OPERATION_REJECT)){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 机构认证通过
     *  applyId,operator_id short_name code business_begin business_end
     *  qualification_number qualified_begin  qualified_end
     *  serve_buyer_percent serve_seller_percent
     */
    @PostMapping(value = "/approveAgencyAuth")
    public ResponseModel approveAgencyAuth(@RequestBody ApplyAgencyAuthReq req) {
        LOGGER.info("开始调用  approveAgencyAuth ,参数:{}", JSON.toJSONString(req));
        if(req.getId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        if(accountFacade.verifyAgency(req, SystemConstant.OPERATION_APPROVE)){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /**
     * 机构认证拒绝
     *  applyId,operator_id reason begin end  category
     */
    @PostMapping(value = "/rejectAgencyAuth")
    public ResponseModel rejectAgencyAuth(@RequestBody ApplyAgencyAuthReq req) {
        LOGGER.info("开始调用  rejectAgencyAuth ,参数:{}", JSON.toJSONString(req));
        if(req.getId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        if(accountFacade.verifyAgency(req, SystemConstant.OPERATION_REJECT)){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /**
     * 账户列表
     */
    @RequiresPermissions("yhgl_zcyl:list")
    @GetMapping(value = "/list")
    public ResponseModel list(AccountReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getAccountListByPage(req));
    }

    /**
     * 账户详情
     */
    @GetMapping(value = "/detail")
    public ResponseModel detail(AccountReq.BaseReq req) {
        Assert.notNull(req.getAccountId(), "accountId 参数不能为空");
        return ResponseModel.succ(accountFacade.getAccountByAccountId(req));
    }

    /**
     * 个人认证列表
     */
    @RequiresPermissions("yhgl_gryl:apply_list")
    @GetMapping(value = "/user/apply/record/list")
    public ResponseModel userApplyRecordList(AccountReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getUserApplyRecordListByPage(req));
    }

    /**
     * 个人认证详情
     */
    @GetMapping(value = "/user/apply/record/detail")
    public ResponseModel userApplyRecordDetail(AccountReq.BaseReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.getUserApplyRecordById(req));
    }

    /**
     * 企业认证列表
     */
    @RequiresPermissions("yhgl_qyyl:apply_list")
    @GetMapping(value = "/company/apply/record/list")
    public ResponseModel companyApplyRecordList(AccountReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getCompanyApplyRecordListByPage(req));
    }

    /**
     * 企业认证详情
     */
    @GetMapping(value = "/company/apply/record/detail")
    public ResponseModel companyApplyRecordDetail(AccountReq.BaseReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.getCompanyApplyRecordById(req));
    }

    /**
     * 个人用户列表
     */
    @RequiresPermissions(value = {"yhgl_gryl:list", "yhgl_gryl:black_list"}, logical = Logical.OR)
    @GetMapping(value = "/user/list")
    public ResponseModel userList(UserReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getUserListByPage(req));
    }

    /**
     * 个人用户详情
     */
    @GetMapping(value = "/user/detail")
    public ResponseModel userDetail(AccountReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        return ResponseModel.succ(accountFacade.getUserById(req));
    }

    /**
     * 更新个人用户信息
     */
    @PostMapping(value = "/user/update")
    public ResponseModel userUpdate(@RequestBody UserReq.UpdateReq req) {
        return ResponseModel.succ(accountFacade.updateUser(req));
    }

    /**
     * 企业用户列表
     */
    @RequiresPermissions(value = {"yhgl_qyyl:list", "yhgl_qyyl:black_list"}, logical = Logical.OR)
    @GetMapping(value = "/company/list")
    public ResponseModel companyList(CompanyReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getCompanyListByPage(req));
    }

    /**
     * 企业用户详情
     */
    @GetMapping(value = "/company/detail")
    public ResponseModel companyDetail(AccountReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        return ResponseModel.succ(accountFacade.getCompanyById(req));
    }

    /**
     * 更新企业用户信息
     */
    @PostMapping(value = "/company/update")
    public ResponseModel companyUpdate(@Valid @RequestBody CompanyReq.UpdateReq req) {
        return ResponseModel.succ(accountFacade.updateCompany(req));
    }

    /**
     * 变更管理员
     */
    @PostMapping(value = "/company/change/admin")
    public ResponseModel companyChangeAdmin(@Valid @RequestBody CompanyReq.ChangeAdminReq req) {
        return ResponseModel.succ(accountFacade.changeAdmin(req));
    }

    /**
     * 企业用户下用户列表
     */
    @GetMapping(value = "/company/member/list")
    public ResponseModel companyAccountList(CompanyReq.BaseReq req) {
        Assert.notNull(req.getCompanyId(), "companyId 参数不能为空");
        req.setPartyId(req.getCompanyId());
        return ResponseModel.succ(accountFacade.getCompanyMemberList(req));
    }

    /**
     * 新建银行类公司
     */
    @PostMapping(value = "/create/channel/pay/company")
    public ResponseModel createChannelPayCompany(@Valid @RequestBody CompanyReq.CreateChannelPayCompanyReq req) {
        return ResponseModel.succ(accountFacade.createChannelPayCompany(req));
    }

    /**
     * 判断手机号是否已注册
     */
    @PostMapping(value = "/is/register")
    public ResponseModel isRegister(@RequestBody AccountReq.QueryReq req) {
        Assert.notNull(req.getMobile(), "mobile 参数不能为空");
        AccountResp accountResp = accountFacade.findAccountByMobile(req.getMobile());
        Map<String, Object> content = new HashMap<>();
        if(accountResp != null && accountResp.getId() != null){
            content.put("isRegister", true);
        } else {
            content.put("isRegister", false);
        }
        return ResponseModel.succ(content);
    }

    /**
     * 机构列表接口
     */
    @GetMapping(value = "/agency/list")
    public ResponseModel agencyList(AgencyReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getAgencyListByPage(req));
    }

    /**
     * 拉黑
     */
    @PostMapping(value = "/party/lock/in/black/list")
    public ResponseModel partyLockInBlackList(@RequestBody PartyBlackListActionReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        if (StringUtils.isEmpty(req.getReason())) {
            return ResponseModel.fail("原因不能为空");
        }
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.partyLockInBlackList(req));
    }

    /**
     * 取消拉黑
     */
    @PostMapping(value = "/party/release/from/black/list")
    public ResponseModel partyReleaseFromBlackList(@RequestBody PartyBlackListActionReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        if (StringUtils.isEmpty(req.getReason())) {
            return ResponseModel.fail("原因不能为空");
        }
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.partyReleaseFromBlackList(req));
    }

    /**
     * 黑名单记录列表
     */
    @GetMapping(value = "/party/black/list/history")
    public ResponseModel partyBlackListHistory(PartyBlackListActionReq.BaseReq req) {
        return ResponseModel.succ(accountFacade.getPartyBlackListActionListByPage(req));
    }

    /**
     * 设置渠道代理商
     */
    @PostMapping(value = "/party/set/channel/agent")
    public ResponseModel partySetChannelAgent(@RequestBody PartyChannelAgentReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        Assert.notNull(req.getIsChannelAgent(), "isChannelAgent 参数不能为空");
        return ResponseModel.succ(accountFacade.partySetChannelAgent(req));
    }

    /**
     * 个人渠道代理商列表
     */
    @GetMapping(value = "/user/channel/agent/list")
    public ResponseModel userChannelAgentList(UserReq.QueryReq req) {
        req.setIsChannelAgent(true);
        return ResponseModel.succ(accountFacade.getUserListByPage(req));
    }

    /**
     * 企业渠道代理商列表
     */
    @GetMapping(value = "/company/channel/agent/list")
    public ResponseModel companyChannelAgentList(CompanyReq.QueryReq req) {
        req.setIsChannelAgent(true);
        return ResponseModel.succ(accountFacade.getCompanyListByPage(req));
    }

    /**
     * 选择渠道代理商
     */
    @PostMapping(value = "/party/select/channel/agent")
    public ResponseModel partySelectChannelAgent(@RequestBody PartyChannelAgentReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        Assert.notNull(req.getChannelAgentPartyId(), "channelAgentPartyId 参数不能为空");
        Assert.notNull(req.getChannelAgentCommissionPercent(), "channelAgentCommissionPercent 参数不能为空");
        return ResponseModel.succ(accountFacade.partySelectChannelAgent(req));
    }

    /**
     * 取消选择渠道代理商接口
     */
    @PostMapping(value = "/party/cancel/select/channel/agent")
    public ResponseModel partyCancelSelectChannelAgent(@RequestBody PartyChannelAgentReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        return ResponseModel.succ(accountFacade.partyCancelSelectChannelAgent(req));
    }

    /**
     * 设置通道支付类企业
     */
    @PostMapping(value = "/company/set/channel/pay")
    public ResponseModel companySetChannelPay(@RequestBody CompanyReq.BaseReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        return ResponseModel.succ(accountFacade.companySetChannelPay(req));
    }

    /**
     * 处置服务商申请列表
     */
    @RequiresPermissions("yhgl_czfws:apply_list")
    @GetMapping(value = "/dispose/provider/apply/list")
    public ResponseModel disposeProviderApplyList(DisposeProviderApplyReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getDisposeProviderApplyListByPage(req));
    }

    /**
     * 处置服务商申请详情
     */
    @GetMapping(value = "/dispose/provider/apply/detail")
    public ResponseModel disposeProviderApplyDetail(DisposeProviderApplyReq.BaseReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        return ResponseModel.succ(accountFacade.getDisposeProviderApply(req));
    }

    /**
     * 处置服务商申请审核通过
     */
    @PostMapping(value = "/approve/dispose/provider/apply")
    public ResponseModel approveDisposeProviderApply(@RequestBody DisposeProviderApplyReq.BaseReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.approveDisposeProviderApply(req));
    }

    /**
     * 处置服务商申请审核拒绝
     */
    @PostMapping(value = "/reject/dispose/provider/apply")
    public ResponseModel rejectDisposeProviderApply(@RequestBody DisposeProviderApplyReq.BaseReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        Assert.notNull(req.getReason(), "reason 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.rejectDisposeProviderApply(req));
    }

    /**
     * 处置服务商列表
     */
    @RequiresPermissions("yhgl_czfws:list")
    @GetMapping(value = "/dispose/provider/list")
    public ResponseModel disposeProviderList(DisposeProviderReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getDisposeProviderListByPage(req));
    }

    /**
     * 处置服务商详情
     */
    @GetMapping(value = "/dispose/provider/detail")
    public ResponseModel disposeProviderDetail(DisposeProviderReq.BaseReq req) {
        Assert.notNull(req.getProviderId(), "providerId 参数不能为空");
        return ResponseModel.succ(accountFacade.getDisposeProvider(req));
    }

    /**
     * 更新处置服务商信息
     */
    @PostMapping(value = "/update/dispose/provider")
    public ResponseModel updateDisposeProvider(@RequestBody DisposeProviderReq.UpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.updateDisposeProvider(req));
    }

    /**
     * 资金供应商申请列表
     */
    @RequiresPermissions("yhgl_zjgys:apply_list")
    @GetMapping(value = "/fund/provider/apply/list")
    public ResponseModel fundProviderApplyList(FundProviderApplyReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getFundProviderApplyListByPage(req));
    }

    /**
     * 资金供应商申请详情
     */
    @GetMapping(value = "/fund/provider/apply/detail")
    public ResponseModel fundProviderApplyDetail(FundProviderApplyReq.BaseReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        return accountFacade.getFundProviderApply(req.getApplyId());
    }

    /**
     * 资金供应商申请审核通过
     */
    @PostMapping(value = "/approve/fund/provider/apply")
    public ResponseModel approveFundProviderApply(@RequestBody FundProviderApplyReq.UpdateReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.approveFundProviderApply(req));
    }

    /**
     * 资金供应商申请审核拒绝
     */
    @PostMapping(value = "/reject/fund/provider/apply")
    public ResponseModel rejectFundProviderApply(@RequestBody FundProviderApplyReq.BaseReq req) {
        Assert.notNull(req.getApplyId(), "applyId 参数不能为空");
        Assert.notNull(req.getReason(), "reason 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.rejectFundProviderApply(req));
    }

    /**
     * 资金供应商列表
     */
    @RequiresPermissions("yhgl_zjgys:list")
    @GetMapping(value = "/fund/provider/list")
    public ResponseModel fundProviderList(FundProviderReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getFundProviderListByPage(req));
    }

    /**
     * 资金供应商详情
     */
    @GetMapping(value = "/fund/provider/detail")
    public ResponseModel fundProviderDetail(FundProviderReq.BaseReq req) {
        Assert.notNull(req.getProviderId(), "providerId 参数不能为空");
        //return ResponseModel.succ();
        return accountFacade.getFundProvider(req.getProviderId());
    }

    /**
     * 更新资金供应商信息
     */
    @PostMapping(value = "/update/fund/provider")
    public ResponseModel updateFundProvider(@RequestBody FundProviderReq.UpdateReq req) {
        return ResponseModel.succ(accountFacade.updateFundProvider(req));
    }

    /**
     * 设置为线下支付方式
     */
    @RequiresPermissions("yhgl_gg:oper_offfline")
    @PostMapping(value = "/party/operate/offline")
    public ResponseModel partyOperateOffline(@RequestBody PartyReq.OperateOfflineReq req) {
        Assert.notNull(req.getPartyId(), "partyId 参数不能为空");
        Assert.notNull(req.getOperOffline(), "operOffline 参数不能为空");
        return ResponseModel.succ(accountFacade.partyOperateOffline(req));
    }

    /**
     * 用户平台资金账户信息列表接口
     */
    @RequiresPermissions("cwgl_txgl:platform_acct_list")
    @GetMapping(value = "/acct/list")
    public ResponseModel acctList(AcctReq.QueryReq req) {
        return accountFacade.getAcctListByPage(req);
    }

    /**
     * 用户平台资金账户信息列表接口
     */
    @GetMapping(value = "/acct/list/download")
    public ResponseModel acctListDownload(AcctReq.QueryReq req, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", "用户平台资金账户列表");
        params.put("sheetName", "用户平台资金账户数据");
        List<Map<String, Object>> list = new ArrayList<>();
        req.setPage(1);
        req.setPerPage(100);
        while (true) {
            ResponseModel resp = accountFacade.getAcctListByPage(req);
            Map<String, Object> content = (Map<String, Object>) resp.getContent();
            Boolean hasNextPage = (Boolean) content.get("hasNextPage");
            List<AcctVo> itemList = (List<AcctVo>) content.get("list");
            for (AcctVo item : itemList) {
                Map<String, Object> map = ToolUtil.convertBeanToMap(item);
                list.add(map);
            }
            if (!hasNextPage) {
                break;
            }
            req.setPage(req.getPage() + 1);
        }
        params.put("list", list);
        String[] keys = new String[]{
                "name", "typeDesc", "totalAmt", "lockAmt", "availAmt"
        };
        params.put("keys", keys);
        String[] columnNames = new String[]{
                    "用户名称", "用户类型", "账户金额", "锁定金额", "可用余额"
        };
        params.put("columnNames", columnNames);
        DownloadUtil.downloadExcel(request, response, params);
        return ResponseModel.succ();
    }

    /**
     * 用户平台资金账户信息接口
     */
    @GetMapping(value = "/acct")
    public ResponseModel acct(AcctReq.BaseReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.getAcct(req));
    }

    /**
     * 用户平台资金账户信息列表接口
     */
    @GetMapping(value = "/acct/detail/list")
    public ResponseModel acctDetailList(AcctReq.QueryReq req) {
        Assert.notNull(req.getAcctId(), "acctId 参数不能为空");
        return ResponseModel.succ(accountFacade.getAcctDetailListByPage(req));
    }


    /**
     * 用户平台资金账户信息列表接口
     */
    @GetMapping(value = "/acct/detail/list/download")
    public ResponseModel acctDetailList(AcctReq.QueryReq req, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(req.getAcctId(), "acctId 参数不能为空");
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", "用户平台资金账户明细");
        params.put("sheetName", "用户平台资金账户明细数据");
        List<Map<String, Object>> list = new ArrayList<>();
        req.setPage(1);
        req.setPerPage(100);
        while (true) {
            PageInfoResp<AcctDetailVo> pageInfoResp = accountFacade.getAcctDetailListByPage(req);
            List<AcctDetailVo> itemList = pageInfoResp.getList();
            for (AcctDetailVo item : itemList) {
                Map<String, Object> map = ToolUtil.convertBeanToMap(item);
                map.put("createTime", DateUtil.getNormDateStr(item.getCreateTime()));
                list.add(map);
            }
            if (!pageInfoResp.isHasNextPage()) {
                break;
            }
            req.setPage(req.getPage() + 1);
        }
        params.put("list", list);
        String[] keys = new String[]{
                "id", "createTime", "typeDesc", "amount", "totalAmt", "lockAmt", "availAmt"
        };
        params.put("keys", keys);
        String[] columnNames = new String[]{
                "订单编号", "时间", "类型", "变动金额", "账户金额", "锁定金额", "可用余额"
        };
        params.put("columnNames", columnNames);
        DownloadUtil.downloadExcel(request, response, params);
        return ResponseModel.succ();
    }
}
