package com.winback.core.utils;

import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.commons.constants.ContractEnum;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.CaseBrief;
import com.winback.core.facade.account.vo.*;
import com.winback.core.facade.assistant.vo.HelpItem;
import com.winback.core.facade.contract.vo.Contract;
import com.winback.core.facade.contract.vo.ContractInvoice;
import com.winback.core.facade.contract.vo.ContractOrder;
import com.winback.core.model._case.TCase;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model.account.*;
import com.winback.core.model.assistant.THelpItem;
import com.winback.core.model.contract.TContract;
import com.winback.core.model.contract.TContractInvoice;
import com.winback.core.model.contract.TContractOrder;
import org.springframework.beans.BeanUtils;

/**
 * @author xdrodger
 * @Title: RespConvertUtil
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 17:04
 */
public class RespConvertUtil {

    /**
     * 转换 属性
     */
    public static <T> T convert(Object source, T target) {
        if (source == null) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static HelpItem convertToHelpItem(THelpItem model) {
        if (model == null) {
            return null;
        }
        HelpItem vo = new HelpItem();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static Customer convertToCustomer(TAccount model) {
        if (model == null) {
            return null;
        }
        Customer vo = new Customer();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static Account convertToAccount(TAccount model) {
        if (model == null) {
            return null;
        }
        Account vo = new Account();
        BeanUtils.copyProperties(model, vo);
        vo.setRegisterSourceDesc(AccountEnum.RegisterSource.getValueByKey(model.getRegisterSource()));
        return vo;
    }

    public static ProjectManager convertToProjectManager(TAccount model) {
        if (model == null) {
            return null;
        }
        ProjectManager vo = new ProjectManager();
        BeanUtils.copyProperties(model, vo);
        vo.setRegisterSourceDesc(AccountEnum.RegisterSource.getValueByKey(model.getRegisterSource()));
        return vo;
    }

    public static Party convertToParty(TAccount model) {
        if (model == null) {
            return null;
        }
        Party vo = new Party();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static LawyerApplyRecord convertToLawyerApplyRecord(TLawyerApplyRecord model) {
        if (model == null) {
            return null;
        }
        LawyerApplyRecord vo = new LawyerApplyRecord();
        BeanUtils.copyProperties(model, vo);
        vo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(model.getStatus()));
        return vo;
    }

    public static Lawyer convertToLawyer(TLawyer model) {
        if (model == null) {
            return null;
        }
        Lawyer vo = new Lawyer();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static LawFirm convertToLawFirm(TLawFirm model) {
        if (model == null) {
            return null;
        }
        LawFirm vo = new LawFirm();
        BeanUtils.copyProperties(model, vo);
        vo.setTypeDesc(AccountEnum.LawFirmType.getValueByKey(model.getType()));
        return vo;
    }

    public static CaseBrief convertToCaseBrief(TCaseBrief model) {
        if (model == null) {
            return null;
        }
        CaseBrief vo = new CaseBrief();
        BeanUtils.copyProperties(model, vo);
        vo.setId(model.getId() + "");
        return vo;
    }

    public static FranchiseeApplyRecord convertToFranchiseeApplyRecord(TFranchiseeApplyRecord model) {
        if (model == null) {
            return null;
        }
        FranchiseeApplyRecord vo = new FranchiseeApplyRecord();
        BeanUtils.copyProperties(model, vo);
        vo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(model.getStatus()));
        return vo;
    }

    public static Franchisee convertToFranchisee(TFranchisee model) {
        if (model == null) {
            return null;
        }
        Franchisee vo = new Franchisee();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static Case convertToCase(TCase model) {
        if (model == null) {
            return null;
        }
        Case vo = new Case();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static Staff convertToStaff(TSysStaff model) {
        if (model == null) {
            return null;
        }
        Staff vo = new Staff();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static Role convertToRole(TSysRole model) {
        if (model == null) {
            return null;
        }
        Role vo = new Role();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static Permission convertToPermission(TSysPermission model) {
        if (model == null) {
            return null;
        }
        Permission vo = new Permission();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static Contract convertToContract(TContract model) {
        if (model == null) {
            return null;
        }
        Contract vo = new Contract();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static ContractOrder convertToContractOrder(TContractOrder model) {
        if (model == null) {
            return null;
        }
        ContractOrder vo = new ContractOrder();
        BeanUtils.copyProperties(model, vo);
        vo.setPayStatusDesc(ContractEnum.PayStatus.getValueByKey(model.getPayStatus()));
        vo.setOrderSourceDesc(ContractEnum.OrderSource.getValueByKey(model.getOrderSource()));
        vo.setPayTypeDesc(ContractEnum.PayType.getValueByKey(model.getPayType()));
        return vo;
    }

    public static ContractInvoice convertToContractInvoice(TContractInvoice model) {
        if (model == null) {
            return null;
        }
        ContractInvoice vo = new ContractInvoice();
        BeanUtils.copyProperties(model, vo);
        vo.setTypeDesc(ContractEnum.InvoiceType.getValueByKey(model.getType()));
        vo.setTitleTypeDesc(ContractEnum.TitleType.getValueByKey(model.getTitleType()));
        vo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(model.getStatus()));
        return vo;
    }
}
