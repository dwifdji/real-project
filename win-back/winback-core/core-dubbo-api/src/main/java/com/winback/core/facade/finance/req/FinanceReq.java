package com.winback.core.facade.finance.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

public class FinanceReq {

    @Setter
    @Getter
    public static class expendListReq extends RequestModel {

        private String caseNo;
        private String customer;
        private String expendBeginAt;
        private String expendEndAt;
        private String mode;
        private String status;


    }




    @Setter
    @Getter
    public static class receivableListReq extends RequestModel {

        private String caseNo;
        private String customer;
        private String receivableBeginAt;
        private String receivableEndAt;
        private String mode;
        private String type;
        private String status;

    }



    @Setter
    @Getter
    public static class invoiceListReq extends RequestModel {

        private String caseNo;
        private String customer;
        private String expendBeginAt;
        private String expendEndAt;
        private String mode;
        private String status;

    }



    @Setter
    @Getter
    public static class auditReq extends RequestModel {

        private String[] ids;
        private String type;
        private String status;
    }



    @Setter
    @Getter
    public static class saveExpendReq extends RequestModel {

        private String caseId;
        private String amount;
        private String acctName;
        private String acctNo;
        private String acctBankName;
        private String msg;
        private String operId;

    }


    @Setter
    @Getter
    public static class saveReceivableReq extends RequestModel {

        private String caseId;
        private String type;
        private String amount;
        private String caseAmount;
        private String cost;
        private String msg;
        private String certificateUrl;
    }


    @Setter
    @Getter
    public static class saveInvoiceReq extends RequestModel {

        private String caseId;
        private String type;
        private String amount;
        private String comName;
        private String dutyNo;
        private String phone;


    }



    @Setter
    @Getter
    public static class commonReq extends RequestModel {

        private String caseId;

    }


    @Setter
    @Getter
    public static class uploadCertificateReq extends RequestModel {

        private String id;
        private String type;
        private String certificateUrl;

    }



}
