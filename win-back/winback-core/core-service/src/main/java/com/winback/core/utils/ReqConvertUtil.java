package com.winback.core.utils;

import com.winback.core.facade._case.req.AdminCaseReq;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AdminSysReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.resp.AdminAccountResp;
import com.winback.core.facade.contract.req.AdminContractReq;
import com.winback.core.facade.contract.req.AppContractReq;
import com.winback.core.facade.contract.req.AppletContractReq;
import com.winback.core.model._case.TCaseBigBrief;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model.account.*;
import com.winback.core.model.contract.TContract;
import com.winback.core.model.contract.TContractInvoice;
import org.springframework.beans.BeanUtils;

/**
 * @author xdrodger
 * @Title: ReqConvertUtil
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 14:45
 */
public class ReqConvertUtil {

    public static TLawyerApplyRecord convertToTLawyerApplyRecord(AppAccountReq.LawyerApplyReq req) {
        TLawyerApplyRecord model = new TLawyerApplyRecord();
        BeanUtils.copyProperties(req, model);
        model.setAccountId(req.getLoginId());
        return model;
    }

    public static TLawyerApplyRecord convertToTLawyerApplyRecord(AdminAccountReq.LawyerVerifyReq req) {
        TLawyerApplyRecord model = new TLawyerApplyRecord();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TFranchiseeApplyRecord convertToTFranchiseeApplyRecord(AppAccountReq.FranchiseeApplyReq req) {
        TFranchiseeApplyRecord model = new TFranchiseeApplyRecord();
        BeanUtils.copyProperties(req, model);
        model.setAccountId(req.getLoginId());
        return model;
    }

    public static TFranchiseeApplyRecord convertToTFranchiseeApplyRecord(AdminAccountReq.FranchiseeVerifyReq req) {
        TFranchiseeApplyRecord model = new TFranchiseeApplyRecord();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TLawyer convertToTLawyer(AdminAccountReq.LawyerUpdateReq req) {
        TLawyer model = new TLawyer();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TLawFirm convertToTLawFirm(AdminAccountReq.LawFirmAddReq req) {
        TLawFirm model = new TLawFirm();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TLawFirm convertToTLawFirm(AdminAccountReq.LawFirmUpdateReq req) {
        TLawFirm model = new TLawFirm();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TFranchisee convertToTFranchisee(AdminAccountReq.FranchiseeUpdateReq req) {
        TFranchisee model = new TFranchisee();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TSysStaff convertToTSysStaff(AdminSysReq.StaffAddReq req) {
        TSysStaff model = new TSysStaff();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TSysStaff convertToTSysStaff(AdminSysReq.StaffUpdateReq req) {
        TSysStaff model = new TSysStaff();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TSysRole convertToTSysRole(AdminSysReq.RoleAddReq req) {
        TSysRole model = new TSysRole();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TSysRole convertToTSysRole(AdminSysReq.RoleUpdateReq req) {
        TSysRole model = new TSysRole();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TContractInvoice convertToTContractInvoice(AppContractReq.InvoiceReq req) {
        TContractInvoice model = new TContractInvoice();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TContractInvoice convertToTContractInvoice(AppletContractReq.InvoiceReq req) {
        TContractInvoice model = new TContractInvoice();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TContract convertToTContract(AdminContractReq.AddReq req) {
        TContract model = new TContract();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TContract convertToTContract(AdminContractReq.EditReq req) {
        TContract model = new TContract();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TCaseBrief convertToTCaseBrief(AdminCaseReq.AddCaseBriefReq req) {
        TCaseBrief model = new TCaseBrief();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TCaseBigBrief convertToTCaseBigBrief(AdminCaseReq.AddCaseBriefReq req) {
        TCaseBigBrief model = new TCaseBigBrief();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TCaseBrief convertToTCaseBrief(AdminCaseReq.EditCaseBriefReq req) {
        TCaseBrief model = new TCaseBrief();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TCaseBigBrief convertToTCaseBigBrief(AdminCaseReq.EditCaseBriefReq req) {
        TCaseBigBrief model = new TCaseBigBrief();
        BeanUtils.copyProperties(req, model);
        return model;
    }
}
