package com.winback.arch.core.sysconfig.properties;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope("singleton")
@DisconfFile(filename = "gateway.properties")
public class GatewayProperties {


    private String smsRoute;//短信路由


    //-------创蓝短信配置----------
    private String cLanUrl;

    private String cLanSignName;

    private String cLanAccount;

    private String cLanPassword;



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


    //-------支付宝配置----------

    //支付网关
    private String aliPayUrl;


    //商户appid
    private String aliPayAppId;


    //商户RSA 私钥
    private String aliPayAppPrivateKey;

    //请求格式
    private String aliPayFormat;

    //编码格式
    private String aliPayCharset;

    //支付宝公钥
    private String aliPayPublicKey;

    //签名方式
    private String aliPaySignType;


    //返回地址URL
    private String aliPayReturnUrl;

    //支付结果通知地址
    private String aliPayNotifyUrl;



    //------企查查配置-------

    //企查查key值
    private String checkKey;

    //秘钥
    private String checkSecretKey;


    //获取企业详情信息 url
    private String checkGetDetailsByNameUrl;


    //查询裁判文书 url
    private String checkSearchJudgmentDocUrl;


    //查询异常信息 url
    private String checkGetOpExceptionUrl;


    //失信信息 url
    private String checkSearchShiXinUrl;


    //执行信息 url
    private String checkSearchZhiXingUrl;


    //股权穿透十层接口 url
    private String checkGetStockAnalysisDataUrl;


    //查询对外投资 url
    private String checkSearchInvestmentUrl;



    //------个推配置-------

    //个推 appKey url
    private String  pushAppKey;

    //个推 masterSecret
    private String pushMasterSecret;


    //个推 AppID
    private String pushAppId;



    //个推 AppSecret
    private String pushAppSecret;

    @DisconfFileItem(name = "sms.route")
    public String getSmsRoute() {
        return smsRoute;
    }

    public void setSmsRoute(String smsRoute) {
        this.smsRoute = smsRoute;
    }
    @DisconfFileItem(name = "cLan.url")
    public String getcLanUrl() {
        return cLanUrl;
    }

    public void setcLanUrl(String cLanUrl) {
        this.cLanUrl = cLanUrl;
    }

    @DisconfFileItem(name = "cLan.signName")
    public String getcLanSignName() {
        return cLanSignName;
    }

    public void setcLanSignName(String cLanSignName) {
        this.cLanSignName = cLanSignName;
    }

    @DisconfFileItem(name = "cLan.account")
    public String getcLanAccount() {
        return cLanAccount;
    }

    public void setcLanAccount(String cLanAccount) {
        this.cLanAccount = cLanAccount;
    }

    @DisconfFileItem(name = "cLan.password")
    public String getcLanPassword() {
        return cLanPassword;
    }

    public void setcLanPassword(String cLanPassword) {
        this.cLanPassword = cLanPassword;
    }

    @DisconfFileItem(name = "push.appId")
    public String getPushAppId() {
        return pushAppId;
    }

    public void setPushAppId(String pushAppId) {
        this.pushAppId = pushAppId;
    }

    @DisconfFileItem(name = "push.appSecret")
    public String getPushAppSecret() {
        return pushAppSecret;
    }

    public void setPushAppSecret(String pushAppSecret) {
        this.pushAppSecret = pushAppSecret;
    }

    @DisconfFileItem(name = "push.masterSecret")
    public String getPushMasterSecret() {
        return pushMasterSecret;
    }

    public void setPushMasterSecret(String pushMasterSecret) {
        this.pushMasterSecret = pushMasterSecret;
    }

    @DisconfFileItem(name = "push.appKey")
    public String getPushAppKey() {
        return pushAppKey;
    }

    public void setPushAppKey(String pushAppKey) {
        this.pushAppKey = pushAppKey;
    }


    @DisconfFileItem(name = "check.searchInvestmentUrl")
    public String getCheckSearchInvestmentUrl() {
        return checkSearchInvestmentUrl;
    }

    public void setCheckSearchInvestmentUrl(String checkSearchInvestmentUrl) {
        this.checkSearchInvestmentUrl = checkSearchInvestmentUrl;
    }

    @DisconfFileItem(name = "check.key")
    public String getCheckKey() {
        return checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }

    @DisconfFileItem(name = "check.secretKey")
    public String getCheckSecretKey() {
        return checkSecretKey;
    }

    public void setCheckSecretKey(String checkSecretKey) {
        this.checkSecretKey = checkSecretKey;
    }

    @DisconfFileItem(name = "check.detailsByNameUrl")
    public String getCheckGetDetailsByNameUrl() {
        return checkGetDetailsByNameUrl;
    }

    public void setCheckGetDetailsByNameUrl(String checkGetDetailsByNameUrl) {
        this.checkGetDetailsByNameUrl = checkGetDetailsByNameUrl;
    }

    @DisconfFileItem(name = "check.searchJudgmentDocUrl")
    public String getCheckSearchJudgmentDocUrl() {
        return checkSearchJudgmentDocUrl;
    }

    public void setCheckSearchJudgmentDocUrl(String checkSearchJudgmentDocUrl) {
        this.checkSearchJudgmentDocUrl = checkSearchJudgmentDocUrl;
    }

    @DisconfFileItem(name = "check.opExceptionUrl")
    public String getCheckGetOpExceptionUrl() {
        return checkGetOpExceptionUrl;
    }

    public void setCheckGetOpExceptionUrl(String checkGetOpExceptionUrl) {
        this.checkGetOpExceptionUrl = checkGetOpExceptionUrl;
    }

    @DisconfFileItem(name = "check.searchShiXinUrl")
    public String getCheckSearchShiXinUrl() {
        return checkSearchShiXinUrl;
    }

    public void setCheckSearchShiXinUrl(String checkSearchShiXinUrl) {
        this.checkSearchShiXinUrl = checkSearchShiXinUrl;
    }

    @DisconfFileItem(name = "check.searchZhiXingUrl")
    public String getCheckSearchZhiXingUrl() {
        return checkSearchZhiXingUrl;
    }

    public void setCheckSearchZhiXingUrl(String checkSearchZhiXingUrl) {
        this.checkSearchZhiXingUrl = checkSearchZhiXingUrl;
    }

    @DisconfFileItem(name = "check.stockAnalysisDataUrl")
    public String getCheckGetStockAnalysisDataUrl() {
        return checkGetStockAnalysisDataUrl;
    }

    public void setCheckGetStockAnalysisDataUrl(String checkGetStockAnalysisDataUrl) {
        this.checkGetStockAnalysisDataUrl = checkGetStockAnalysisDataUrl;
    }

    public String getAliPayReturnUrl() {
        return aliPayReturnUrl;
    }

    public void setAliPayReturnUrl(String aliPayReturnUrl) {
        this.aliPayReturnUrl = aliPayReturnUrl;
    }

    public String getAliPayNotifyUrl() {
        return aliPayNotifyUrl;
    }

    public void setAliPayNotifyUrl(String aliPayNotifyUrl) {
        this.aliPayNotifyUrl = aliPayNotifyUrl;
    }

    @DisconfFileItem(name = "aliPay.url")
    public String getAliPayUrl() {
        return aliPayUrl;
    }

    public void setAliPayUrl(String aliPayUrl) {
        this.aliPayUrl = aliPayUrl;
    }

    @DisconfFileItem(name = "aliPay.appId")
    public String getAliPayAppId() {
        return aliPayAppId;
    }

    public void setAliPayAppId(String aliPayAppId) {
        this.aliPayAppId = aliPayAppId;
    }

    @DisconfFileItem(name = "aliPay.appPrivateKey")
    public String getAliPayAppPrivateKey() {
        return aliPayAppPrivateKey;
    }

    public void setAliPayAppPrivateKey(String aliPayAppPrivateKey) {
        this.aliPayAppPrivateKey = aliPayAppPrivateKey;
    }

    @DisconfFileItem(name = "aliPay.format")
    public String getAliPayFormat() {
        return aliPayFormat;
    }

    public void setAliPayFormat(String aliPayFormat) {
        this.aliPayFormat = aliPayFormat;
    }

    @DisconfFileItem(name = "aliPay.charset")
    public String getAliPayCharset() {
        return aliPayCharset;
    }

    public void setAliPayCharset(String aliPayCharset) {
        this.aliPayCharset = aliPayCharset;
    }

    @DisconfFileItem(name = "aliPay.publicKey")
    public String getAliPayPublicKey() {
        return aliPayPublicKey;
    }

    public void setAliPayPublicKey(String aliPayPublicKey) {
        this.aliPayPublicKey = aliPayPublicKey;
    }

    @DisconfFileItem(name = "aliPay.signType")
    public String getAliPaySignType() {
        return aliPaySignType;
    }

    public void setAliPaySignType(String aliPaySignType) {
        this.aliPaySignType = aliPaySignType;
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

    @DisconfFileItem(name ="wxPay.NotifyUrl")
    public String getWxPayNotifyUrl() {
        return wxPayNotifyUrl;
    }

    public void setWxPayNotifyUrl(String wxPayNotifyUrl) {
        this.wxPayNotifyUrl = wxPayNotifyUrl;
    }

    @DisconfFileItem(name = "ali.smsOpen")
    public String getAliSmsOpen() {
        return aliSmsOpen;
    }

    public void setAliSmsOpen(String aliSmsOpen) {
        this.aliSmsOpen = aliSmsOpen;
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


}
