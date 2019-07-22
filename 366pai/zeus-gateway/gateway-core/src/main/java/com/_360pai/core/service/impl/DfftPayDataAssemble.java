package com._360pai.core.service.impl;


import com._360pai.core.model.DfftPay.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：东方付通请求参数组装
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/8 12:23
 */
public class DfftPayDataAssemble {

    //接口类型
    private final static String  PAY_TYPE = "payType";

    //会员号码
    private final static String  MEM_CODE = "memCode";
    //会员名称
    private final static String  MEM_NAME = "memName";

    private final static String  PAY_MEM_TYPE = "payMemType";

    //支付订单为唯一ID
    private final static String  PAY_ID = "payID";

    //原始支付订单ID
    private final static String  ORIGINAL_PAY_ID = "originalPayID";

    //交易的订单号
    private final static String  TRADE_ORDER = "tradeOrder";

    //付款方会员号码
    private final static String  PAY_MEM_CODE = "payMemCode";

    //付款方会员名称
    private final static String  PAY_MEM_NAME = "payMemName";

    //币种
    private final static String  CURRENCY = "currency";
    //金额
    private final static String  PAY_AMT = "payAmt";

    //摘要
    private final static String  SUMMARY = "summary";

    //收款用户code
    private final static String  REC_MEM_CODE = "recMemCode";

    //收款用户名称
    private final static String  REC_MEM_NAME = "recMemName";

    //是否自动标识
    private final static String  AUDIT_FLAG = "auditFlag";

    //自定义字段
    private final static String  CUSTOM_FIELS = "customFiels";

    //锁定字段 1 锁定到收款方
    private final static String  LOCK_TAG = "locktag";

    //银行账号
    private final static String  BANK_ACCOUNT = "bankAccount";
    //银行用途
    private final static String  BANK_USE = "bankUse";

    //银行摘要
    private final static String  BANK_DIGEST = "bankDigest";

    //分润信息
    private final static String  PROFIT_SHARE = "profitShare";

    //银行code
    private final static String  BANK_CODE = "bankCode";
    //银行code
    private final static String  ACC_NAME = "accName";
    //开户银行账号
    private final static String  ACC_NO = "accNO";
    //加急付款  T+0 付款标识，0 表示T+0 付款，空或其他为T+1 付款
    private final static String  INST_CASH = "instCash";
    //翻单标识  1 翻单 0 或不填标识不翻单
    private final static String  REORDER = "reorder";
    // 翻单名称，需要申请开通权限
    private final static String  ORDER_NAME = "orderName";

    private final static String  MALL_ID = "mallID";



    private final static String mallID ="000495";





    //会员绑定接口code
    private final static String  BIND_MEMBER_CODE= "09020";

    //会员绑定关系查询
    private final static String  QUERY_BIND_MEMBER_CODE= "09011";

    //直接支付
    private final static String  DIRECT_PAY_CODE= "01010";

    //关闭支付
    private final static String  CLOSE_PAY_CODE= "01021";

    //保证金锁定
    private final static String  MARGIN_LOCK_CODE= "03031";

    //保证金释放
    private final static String  MARGIN_RELEASE_CODE= "03041";

    //保证金支付
    private final static String  MARGIN_PAY_CODE= "03051";

    //保证金追加
    private final static String  MARGIN_ADD_CODE= "03061";


    /**
     * 绑定用户会员接口参数组装
     *
     * @param  req
     */
    public static Map<String ,String> getBindMemberData(BindMemberReq req){

        Map<String ,String> map = getCommonMemMap(BIND_MEMBER_CODE,req.getMemCode(),req.getMemName());

        map.put(PAY_MEM_TYPE,req.getPayMemType());

        return map;
    }


    /**
     * 查询会员绑定关系参数组装
     *
     * @param  req
     */
    public static Map<String ,String> getQueryBindMemberData(QueryBindMemberReq req){

        Map<String ,String> map = getCommonMemMap(QUERY_BIND_MEMBER_CODE,req.getMemCode(),req.getMemName());

        return map;
    }


    /**
     * 获取账户信息
     *
     * @param  req
     */
    public static Map<String ,String> getQueryBalaceData(QueryBindMemberReq req){


        Map<String ,String> map = new HashMap<>();
        map.put(PAY_TYPE,"06011");
        map.put(MEM_CODE,req.getMemCode());
        return map;
    }

    /**
     * 直接/锁定支付参数组装
     *
     * @param  req
     */
    public static Map<String ,String> getDirectOrLockPayData(DirectOrLockPayReq req){

        Map<String ,String> map = getPayCommonMap(req);

        map.put(REC_MEM_CODE,req.getRecMemCode());
        map.put(REC_MEM_NAME,req.getRecMemName());
        map.put(BANK_ACCOUNT,req.getBankAccount());
        map.put(BANK_DIGEST,req.getBankDigest());
        map.put(BANK_USE,req.getBankUse());
        map.put(CUSTOM_FIELS,req.getCustomFiels());
        map.put(LOCK_TAG,req.getLocktag());
        map.put(PAY_MEM_TYPE,req.getPayMemType());

        return map;
    }


    /**
     * 保证金操作接口参数组装
     *
     * @param  req
     */
    public static Map<String ,String> getMarginOperationData(MarginOperationReq req){


        Map<String ,String> map = getPayCommonMap(req);

        map.put(REC_MEM_CODE,req.getRecMemCode());

        map.put(REC_MEM_NAME,req.getRecMemName());

        map.put(AUDIT_FLAG,req.getAuditFlag());

        map.put(LOCK_TAG,req.getLockTag());

        return map;
    }


    /**
     * 通道支付接口参数组装
     *
     * @param  req
     */
    public static Map<String ,String> getChannelPayData(ChannelPayReq req){

        Map<String ,String> map = getPayCommonMap(req);

        map.put(ACC_NAME,req.getAccName());
        map.put(ACC_NO,req.getAccNO());
        map.put(BANK_ACCOUNT,req.getBankAccount());
        map.put(BANK_CODE,req.getBankCode());
        map.put(BANK_DIGEST,req.getBankDigest());
        map.put(BANK_USE,req.getBankUse());
        map.put(CUSTOM_FIELS,req.getCustomFiels());
        map.put(REORDER,req.getReorder());
        map.put(INST_CASH,req.getInstCash());
        map.put(ORDER_NAME,req.getOrderName());

        return map;
    }


    /**
     * 分润接口参数组装
     *
     * @param  req
     */
    public static Map<String ,String> getFenRunPayData(FenRunPayReq req){

        Map<String ,String> map = getPayCommonMap(req);

        map.put(REC_MEM_CODE,req.getRecMemCode());
        map.put(REC_MEM_NAME,req.getRecMemName());
        //map.put(PROFIT_SHARE,req.getProfitShare());

        return map;
    }




    /**
     * 查询会员公共参数组装
     *
     * @param  payType
     */
    private static Map<String ,String> getCommonMemMap(String payType,String memCode,String memName){

        Map<String ,String> map = new HashMap<>();
        map.put(PAY_TYPE,payType);
        map.put(MEM_CODE,memCode);
        map.put(MEM_NAME,memName);
        return map;
    }

    /**
     * 支付相关公共参数组装
     *
     * @param  payCommonReq
     */
    private static Map<String ,String> getPayCommonMap(PayCommonReq payCommonReq){

        Map<String ,String> map = new HashMap<>();
        map.put(PAY_TYPE,payCommonReq.getPayType());
        map.put(PAY_ID,payCommonReq.getPayID());
        map.put(ORIGINAL_PAY_ID,payCommonReq.getOriginalPayID());
        map.put(PAY_MEM_CODE,payCommonReq.getPayMemCode());
        map.put(PAY_MEM_NAME,payCommonReq.getPayMemName());
        map.put(PAY_AMT,payCommonReq.getPayAmt());
        map.put(CURRENCY,payCommonReq.getCurrency());
        map.put(SUMMARY,payCommonReq.getSummary());
        map.put(TRADE_ORDER,payCommonReq.getTradeOrder());

        map.put("callBackUrl",payCommonReq.getNotifyUrl());


        return map;
    }



    /**
     * 支付相关公共参数组装
     *
     * @param  payId
     */
    public static Map<String ,String> getQueryOrderMap(String payId){

        Map<String ,String> map = new HashMap<>();
        map.put(PAY_TYPE,"05011");
        map.put(PAY_ID,payId);

        return map;
    }
}
