package com._360pai.arch.core.sysconfig.properties;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope("singleton")
@DisconfFile(filename = "gateway.properties")
public class GatewayProperties {

    //-------东方付通参数----------
    private String payPath;

    private String payPassword;

    private String  payFileName;

    private String payUrl;

    private String payMallID;

    private String notifyUrl;


    private String payCompanyName;

    private String payCustomerId;


    private String isMock;//是否请求mock


    private String mockUrl;//mock请求地址



    //-------法大大参数----------
    private String fddAppId;

    private String fddSecret;

    private String fddVersion;

    private String fddUrl;

    private  String fddNotifyUrl;

    private String fontType;//法大大合同签章的字体类型

    private String fontSize;//签章的字号大小

    private String fddWebFddId;//平台的法大大id

    private  String webName;//签章的平台名称

    private String webAgency;//签章的平台法人代表

    private String webAddress;//平台地址

    private String webPhone;//签章的平台电话

    private String commissionPercent;//抵押物预招商佣金比例


    private String signDay;//签约时间




    //-------阿里短信参数----------
    private String aliAccessKeyId;

    private String aliAccessKeySecret;

    private String aliDefaultConnectTimeout;

    private String aliDefaultReadTimeout;

    private String aliSignName;


    private String aliSmsOpen;



    //-------微信支付参数----------

    //安全证书地址
    private String wxPayCertPath;

    //AppId
    private String wxPayAppID;

    //转账的商户号码
    private String wxPayMchID;

    //秘钥key zhi
    private String wxPayKey;

    //微信支付通知地址
    private String wxPayNotifyUrl;








    //-------邮件参数设置----------
    //发件人账号
    private String senderAccount;
    //发件人账户密码
    private String senderPassword;
    //发件人的SMTP服务器地址
    private String smtpHost;



    //-------七牛参数----------
    private String accessKey;

    private String secretKey;

    private String bucket;

    private String domain;




    //-------预招商不用付钱配置----------
    private String enrollingNotPay;


    private Integer enrollingPayMinute;


    /**
     * 大买办不用付钱配置 partyId
     */
    private String compradorNotPay;
    /**
     * 配资乐不用付钱配置 partyId
     */
    private String withfudigNotPay;
    /**
     *处置服务不用付钱配置 partyId
     */
    private String disposalNotPay;



    //-------东方付通、法大大系统监控查询参数----------
    //查询的code
    private String queryMemCode;
    //查询的name
    private String queryMemName;


    //查询的name
    private String queryContractId;

    //查询的faddid
    private String queryFddId;

    //微信tradeNo
    private String queryOutTradeNo;


    //-------小程序参数----------
    //小程序获取openid 请求url
    private String appletOpenIdUrl;

    //小程序 appId
    private String appletAppId;

    //小程序 appSecret
    private String appletAppSecret;

    //微信小程序token 请求url
    private String appletTokenUrl;


    //获取微信小程序二维码 请求url
    private String appletAcodeUnLimit;


    //--------数字跳动小程序配置


    //计算小程序 appId
    private String calculatorAppId;

    //计算器小程序 appSecret
    private String calculatorAppSecret;

    //详情页地址
    private String appletCompeteUrl;


    //开店不需要支付 partyId
    private String appletOpenShopNotPay;


    //上传预招商能看全部的审核列表
    private String enrollingImportAuditAll;


    //预招商跳转
    private String appletEnrollingCompeteUrl;


    //预招商跳转
    private String appletEnrollingMerchantUrl;


    // ----------360拍服务号参数
    private String mp360PaiAppId;
    private String mp360PaiAppSercret;



    // ----------租赁权参数-------
    private String leaseAuctioneerName;

    private String leaseAuctioneerPhone;

    private String leaseAuctioneerQq;

    @DisconfFileItem(name = "lease.auctioneerName")
    public String getLeaseAuctioneerName() {
        return leaseAuctioneerName;
    }

    public void setLeaseAuctioneerName(String leaseAuctioneerName) {
        this.leaseAuctioneerName = leaseAuctioneerName;
    }

    @DisconfFileItem(name = "lease.auctioneerPhone")
    public String getLeaseAuctioneerPhone() {
        return leaseAuctioneerPhone;
    }

    public void setLeaseAuctioneerPhone(String leaseAuctioneerPhone) {
        this.leaseAuctioneerPhone = leaseAuctioneerPhone;
    }

    @DisconfFileItem(name = "lease.auctioneerQq")
    public String getLeaseAuctioneerQq() {
        return leaseAuctioneerQq;
    }

    public void setLeaseAuctioneerQq(String leaseAuctioneerQq) {
        this.leaseAuctioneerQq = leaseAuctioneerQq;
    }

    @DisconfFileItem(name = "enrolling.merchantUrl")
    public String getAppletEnrollingMerchantUrl() {
        return appletEnrollingMerchantUrl;
    }

    public void setAppletEnrollingMerchantUrl(String appletEnrollingMerchantUrl) {
        this.appletEnrollingMerchantUrl = appletEnrollingMerchantUrl;
    }



    @DisconfFileItem(name = "enrolling.competeUrl")
    public String getAppletEnrollingCompeteUrl() {
        return appletEnrollingCompeteUrl;
    }

    public void setAppletEnrollingCompeteUrl(String appletEnrollingCompeteUrl) {
        this.appletEnrollingCompeteUrl = appletEnrollingCompeteUrl;
    }


    //-------长城资产服务商配置---------

    private String enrollingImportDisposalService;


    private String enrollingImportDisposalPhone;


    private String enrollingImportFundProvider;


    private String enrollingImportFundPhone;



    @DisconfFileItem(name = "calculator.appSecret")
    public String getCalculatorAppSecret() {
        return calculatorAppSecret;
    }

    public void setCalculatorAppSecret(String calculatorAppSecret) {
        this.calculatorAppSecret = calculatorAppSecret;
    }

    @DisconfFileItem(name = "calculator.appId")
    public String getCalculatorAppId() {
        return calculatorAppId;
    }

    public void setCalculatorAppId(String calculatorAppId) {
        this.calculatorAppId = calculatorAppId;
    }

    @DisconfFileItem(name = "enrolling.importDisposalService")
    public String getEnrollingImportDisposalService() {
        return enrollingImportDisposalService;
    }

    public void setEnrollingImportDisposalService(String enrollingImportDisposalService) {
        this.enrollingImportDisposalService = enrollingImportDisposalService;
    }

    @DisconfFileItem(name = "enrolling.importDisposalPhone")
    public String getEnrollingImportDisposalPhone() {
        return enrollingImportDisposalPhone;
    }

    public void setEnrollingImportDisposalPhone(String enrollingImportDisposalPhone) {
        this.enrollingImportDisposalPhone = enrollingImportDisposalPhone;
    }

    @DisconfFileItem(name = "enrolling.importFundProvider")
    public String getEnrollingImportFundProvider() {
        return enrollingImportFundProvider;
    }

    public void setEnrollingImportFundProvider(String enrollingImportFundProvider) {
        this.enrollingImportFundProvider = enrollingImportFundProvider;
    }

    @DisconfFileItem(name = "enrolling.importFundPhone")
    public String getEnrollingImportFundPhone() {
        return enrollingImportFundPhone;
    }

    public void setEnrollingImportFundPhone(String enrollingImportFundPhone) {
        this.enrollingImportFundPhone = enrollingImportFundPhone;
    }

    @DisconfFileItem(name = "enrolling.importAuditAll")
    public String getEnrollingImportAuditAll() {
        return enrollingImportAuditAll;
    }

    public void setEnrollingImportAuditAll(String enrollingImportAuditAll) {
        this.enrollingImportAuditAll = enrollingImportAuditAll;
    }

    @DisconfFileItem(name = "applet.openShopNotPay")
    public String getAppletOpenShopNotPay() {
        return appletOpenShopNotPay;
    }

    public void setAppletOpenShopNotPay(String appletOpenShopNotPay) {
        this.appletOpenShopNotPay = appletOpenShopNotPay;
    }

    @DisconfFileItem(name = "applet.competeUrl")
    public String getAppletCompeteUrl() {
        return appletCompeteUrl;
    }

    public void setAppletCompeteUrl(String appletCompeteUrl) {
        this.appletCompeteUrl = appletCompeteUrl;
    }

    @DisconfFileItem(name = "applet.acodeUnLimitUrl")
    public String getAppletAcodeUnLimit() {
        return appletAcodeUnLimit;
    }

    public void setAppletAcodeUnLimit(String appletAcodeUnLimit) {
        this.appletAcodeUnLimit = appletAcodeUnLimit;
    }

    @DisconfFileItem(name = "applet.tokenUrl")
    public String getAppletTokenUrl() {
        return appletTokenUrl;
    }

    public void setAppletTokenUrl(String appletTokenUrl) {
        this.appletTokenUrl = appletTokenUrl;
    }

    @DisconfFileItem(name = "applet.openIdUrl")
    public String getAppletOpenIdUrl() {
        return appletOpenIdUrl;
    }

    public void setAppletOpenIdUrl(String appletOpenIdUrl) {
        this.appletOpenIdUrl = appletOpenIdUrl;
    }

    @DisconfFileItem(name = "applet.appId")
    public String getAppletAppId() {
        return appletAppId;
    }

    public void setAppletAppId(String appletAppId) {
        this.appletAppId = appletAppId;
    }

    @DisconfFileItem(name = "applet.appSecret")
    public String getAppletAppSecret() {
        return appletAppSecret;
    }

    public void setAppletAppSecret(String appletAppSecret) {
        this.appletAppSecret = appletAppSecret;
    }


    //-------美洽 配置----------
    private String mqAppId;

    @DisconfFileItem(name = "mq.AppId")
    public String getMqAppId() {
        return mqAppId;
    }

    public void setMqAppId(String mqAppId) {
        this.mqAppId = mqAppId;
    }

    @DisconfFileItem(name = "enrolling.payMinute")
    public Integer getEnrollingPayMinute() {
        return enrollingPayMinute;
    }

    public void setEnrollingPayMinute(Integer enrollingPayMinute) {
        this.enrollingPayMinute = enrollingPayMinute;
    }

    @DisconfFileItem(name = "ali.smsOpen")
    public String getAliSmsOpen() {
        return aliSmsOpen;
    }

    public void setAliSmsOpen(String aliSmsOpen) {
        this.aliSmsOpen = aliSmsOpen;
    }

    @DisconfFileItem(name = "pay.mockUrl")
    public String getMockUrl() {
        return mockUrl;
    }

    public void setMockUrl(String mockUrl) {
        this.mockUrl = mockUrl;
    }

    @DisconfFileItem(name = "pay.isMock")
    public String getIsMock() {
        return isMock;
    }

    public void setIsMock(String isMock) {
        this.isMock = isMock;
    }

    @DisconfFileItem(name = "query.outTradeNo")
    public String getQueryOutTradeNo() {
        return queryOutTradeNo;
    }

    public void setQueryOutTradeNo(String queryOutTradeNo) {
        this.queryOutTradeNo = queryOutTradeNo;
    }

    @DisconfFileItem(name = "query.memCode")
    public String getQueryMemCode() {
        return queryMemCode;
    }

    public void setQueryMemCode(String queryMemCode) {
        this.queryMemCode = queryMemCode;
    }

    @DisconfFileItem(name = "query.memName")
    public String getQueryMemName() {
        return queryMemName;
    }

    public void setQueryMemName(String queryMemName) {
        this.queryMemName = queryMemName;
    }

    @DisconfFileItem(name = "query.contractId")
    public String getQueryContractId() {
        return queryContractId;
    }

    public void setQueryContractId(String queryContractId) {
        this.queryContractId = queryContractId;
    }

    @DisconfFileItem(name = "query.fddId")
    public String getQueryFddId() {
        return queryFddId;
    }

    public void setQueryFddId(String queryFddId) {
        this.queryFddId = queryFddId;
    }

    @DisconfFileItem(name = "comprador.notPay")
    public String getCompradorNotPay() {
        return compradorNotPay;
    }

    public void setCompradorNotPay(String compradorNotPay) {
        this.compradorNotPay = compradorNotPay;
    }

    @DisconfFileItem(name = "withfudig.notPay")
    public String getWithfudigNotPay() {
        return withfudigNotPay;
    }

    public void setWithfudigNotPay(String withfudigNotPay) {
        this.withfudigNotPay = withfudigNotPay;
    }
    @DisconfFileItem(name = "disposal.notPay")
    public String getDisposalNotPay() {
        return disposalNotPay;
    }

    public void setDisposalNotPay(String disposalNotPay) {
        this.disposalNotPay = disposalNotPay;
    }

    @DisconfFileItem(name = "enrolling.notPay")
    public String getEnrollingNotPay() {
        return enrollingNotPay;
    }

    public void setEnrollingNotPay(String enrollingNotPay) {
        this.enrollingNotPay = enrollingNotPay;
    }

    @DisconfFileItem(name = "qiniu.accessKey")
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @DisconfFileItem(name = "qiniu.secretKey")
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @DisconfFileItem(name = "qiniu.bucket")
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @DisconfFileItem(name = "qiniu.domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @DisconfFileItem(name = "fdd.commissionPercent")
    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    @DisconfFileItem(name = "fdd.webName")
    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    @DisconfFileItem(name = "fdd.webAgency")
    public String getWebAgency() {
        return webAgency;
    }

    public void setWebAgency(String webAgency) {
        this.webAgency = webAgency;
    }

    @DisconfFileItem(name = "fdd.webAddress")
    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    @DisconfFileItem(name = "fdd.webPhone")
    public String getWebPhone() {
        return webPhone;
    }

    public void setWebPhone(String webPhone) {
        this.webPhone = webPhone;
    }

    @DisconfFileItem(name = "fdd.webFddId")
    public String getFddWebFddId() {
        return fddWebFddId;
    }

    @DisconfFileItem(name = "fdd.signDay")
    public String getSignDay() {
        return signDay;
    }

    public void setSignDay(String signDay) {
        this.signDay = signDay;
    }

    public void setFddWebFddId(String fddWebFddId) {
        this.fddWebFddId = fddWebFddId;
    }

    @DisconfFileItem(name = "email.senderAccount")
    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    @DisconfFileItem(name = "email.senderPassword")
    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    @DisconfFileItem(name = "email.smtpHost")
    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    @DisconfFileItem(name = "pay.notifyUrl")
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @DisconfFileItem(name = "pay.companyName")
    public String getPayCompanyName() {
        return payCompanyName;
    }

    public void setPayCompanyName(String payCompanyName) {
        this.payCompanyName = payCompanyName;
    }

    @DisconfFileItem(name = "pay.customerId")
    public String getPayCustomerId() {
        return payCustomerId;
    }

    public void setPayCustomerId(String payCustomerId) {
        this.payCustomerId = payCustomerId;
    }

    @DisconfFileItem(name = "wxPay.NotifyUrl")
    public String getWxPayNotifyUrl() {
        return wxPayNotifyUrl;
    }

    public void setWxPayNotifyUrl(String wxPayNotifyUrl) {
        this.wxPayNotifyUrl = wxPayNotifyUrl;
    }

    @DisconfFileItem(name = "pay.path")
    public String getPayPath() {
        return payPath;
    }

    public void setPayPath(String payPath) {
        this.payPath = payPath;
    }


    @DisconfFileItem(name = "pay.password")
    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    @DisconfFileItem(name = "pay.filename")
    public String getPayFileName() {
        return payFileName;
    }

    public void setPayFileName(String payFileName) {
        this.payFileName = payFileName;
    }

    @DisconfFileItem(name = "pay.url")
    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
    @DisconfFileItem(name = "pay.mallID")
    public String getPayMallID() {
        return payMallID;
    }

    public void setPayMallID(String payMallID) {
        this.payMallID = payMallID;
    }

    @DisconfFileItem(name = "fdd.appId")
    public String getFddAppId() {
        return fddAppId;
    }

    public void setFddAppId(String fddAppId) {
        this.fddAppId = fddAppId;
    }
    @DisconfFileItem(name = "fdd.secret")
    public String getFddSecret() {
        return fddSecret;
    }

    public void setFddSecret(String fddSecret) {
        this.fddSecret = fddSecret;
    }

    @DisconfFileItem(name = "fdd.version")
    public String getFddVersion() {
        return fddVersion;
    }

    public void setFddVersion(String fddVersion) {
        this.fddVersion = fddVersion;
    }

    @DisconfFileItem(name = "fdd.url")
    public String getFddUrl() {
        return fddUrl;
    }

    public void setFddUrl(String fddUrl) {
        this.fddUrl = fddUrl;
    }

    @DisconfFileItem(name = "fdd.notifyUrl")
    public String getFddNotifyUrl() {
        return fddNotifyUrl;
    }

    public void setFddNotifyUrl(String fddNotifyUrl) {
        this.fddNotifyUrl = fddNotifyUrl;
    }

    @DisconfFileItem(name = "wxPay.certPath")
    public String getWxPayCertPath() {
        return wxPayCertPath;
    }

    public void setWxPayCertPath(String wxPayCertPath) {
        this.wxPayCertPath = wxPayCertPath;
    }

    @DisconfFileItem(name = "wxPay.appID")
    public String getWxPayAppID() {
        return wxPayAppID;
    }

    public void setWxPayAppID(String wxPayAppID) {
        this.wxPayAppID = wxPayAppID;
    }

    @DisconfFileItem(name = "wxPay.MchID")
    public String getWxPayMchID() {
        return wxPayMchID;
    }

    public void setWxPayMchID(String wxPayMchID) {
        this.wxPayMchID = wxPayMchID;
    }

    @DisconfFileItem(name = "wxPay.Key")
    public String getWxPayKey() {
        return wxPayKey;
    }

    public void setWxPayKey(String wxPayKey) {
        this.wxPayKey = wxPayKey;
    }

    @DisconfFileItem(name = "ali.AccessKeyId")
    public String getAliAccessKeyId() {
        return aliAccessKeyId;
    }

    public void setAliAccessKeyId(String aliAccessKeyId) {
        this.aliAccessKeyId = aliAccessKeyId;
    }

    @DisconfFileItem(name = "ali.AccessKeySecret")
    public String getAliAccessKeySecret() {
        return aliAccessKeySecret;
    }

    public void setAliAccessKeySecret(String aliAccessKeySecret) {
        this.aliAccessKeySecret = aliAccessKeySecret;
    }

    @DisconfFileItem(name = "ali.DefaultConnectTimeout")
    public String getAliDefaultConnectTimeout() {
        return aliDefaultConnectTimeout;
    }

    public void setAliDefaultConnectTimeout(String aliDefaultConnectTimeout) {
        this.aliDefaultConnectTimeout = aliDefaultConnectTimeout;
    }

    @DisconfFileItem(name = "ali.DefaultReadTimeout")
    public String getAliDefaultReadTimeout() {
        return aliDefaultReadTimeout;
    }

    public void setAliDefaultReadTimeout(String aliDefaultReadTimeout) {
        this.aliDefaultReadTimeout = aliDefaultReadTimeout;
    }

    @DisconfFileItem(name = "ali.SignName")
    public String getAliSignName() {
        return aliSignName;
    }

    public void setAliSignName(String aliSignName) {
        this.aliSignName = aliSignName;
    }

    @DisconfFileItem(name = "fdd.fontType")
    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    @DisconfFileItem(name = "fdd.fontSize")
    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    @DisconfFileItem(name = "mp.360pai.appId")
    public String getMp360PaiAppId() {
        return mp360PaiAppId;
    }

    public void setMp360PaiAppId(String mp360PaiAppId) {
        this.mp360PaiAppId = mp360PaiAppId;
    }

    @DisconfFileItem(name = "mp.360pai..appSecret")
    public String getMp360PaiAppSercret() {
        return mp360PaiAppSercret;
    }

    public void setMp360PaiAppSercret(String mp360PaiAppSercret) {
        this.mp360PaiAppSercret = mp360PaiAppSercret;
    }
}
