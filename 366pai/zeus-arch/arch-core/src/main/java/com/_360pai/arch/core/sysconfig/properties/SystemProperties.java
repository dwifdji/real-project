package com._360pai.arch.core.sysconfig.properties;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import java.math.BigDecimal;

@Service
@Scope("singleton")
@DisconfFile(filename = "system.properties")
public class SystemProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String validationQuery;

    private String initialSize;
    private String maxActive;
    private String minIdle;
    private String maxWait;
    private String testOnBorrow;
    private String testOnReturn;
    private String testWhileIdle;
    private String timeBetweenEvictionRunsMillis;
    private String minEvictableIdleTimeMillis;
    private String removeAbandoned;
    private String removeAbandonedTimeout;
    private String logAbandoned;
    private String filters;

    private String platformCustomerServicePhone;

    private String agencyWhiteList;
    private String adminWhiteList;

    private String offlineBankAccountNumber;
    private String offlineBankAccountName;
    private String offlineBankName;
    private String basisReport;
    private String completeReport;

    private String redisHost;
    private int    redisPort;

    private int checkPreviewAt;
    private int differenceDay;


    private String appletDefaultRegisterPassword;
    private String websiteAppletHomePage;
    private String shopAppletHomePage;
    private String shopInviteHomePage;
    private String appletServicePhone;


    /**
     * 库
     */
    private int        redisDbIndex = 0;
    /**
     * 密码
     */
    private String     redisPassword;
    private String     accessKeyId;
    private String     accessKeySecret;
    private String     registerTemCode;
    /**
     * 成交分润比例
     */
    private String firstDealCommissionPercent;
    private String secondDealCommissionPercent;

    /**
     * 单点登录（false 否 true 是）
     */
    private Boolean sso;

    private String viewCountIncrementTotal;
    private String viewCountIncrementSingle;
    private String viewCountIncrementAgencyNum;
    private String viewCountIncrementFlag;
    private String focusReminderCountIncrementFlag;

    /**
     * 环境
     */
    private String environment;

    /**
     * 服务类支付过期时间
     */
    private int servicePayExpired;
    /**
     * 子站点展示小程序二维码(false 否 true 是)
     */
    private Boolean showAppletQrCode;
    /**
     * 传递小程序二维码页面参数(false 否 true 是)
     */
    private Boolean putAppletHomePage;
    /**
     * 租赁权的拍卖服务机构名称
     */
    private String agencyName;
    /**
     * 租赁权的拍卖服务机构id
     */
    private String agencyId;

    @DisconfFileItem(name = "agencyName")
    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    @DisconfFileItem(name = "agencyId")
    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    @DisconfFileItem(name = "viewCount.increment.agencyNum")
    public String getViewCountIncrementAgencyNum() {
        return viewCountIncrementAgencyNum;
    }

    public void setViewCountIncrementAgencyNum(String viewCountIncrementAgencyNum) {
        this.viewCountIncrementAgencyNum = viewCountIncrementAgencyNum;
    }

    @DisconfFileItem(name = "viewCount.increment.total")
    public String getViewCountIncrementTotal() {
        return viewCountIncrementTotal;
    }

    public void setViewCountIncrementTotal(String viewCountIncrementTotal) {
        this.viewCountIncrementTotal = viewCountIncrementTotal;
    }

    @DisconfFileItem(name = "viewCount.increment.single")
    public String getViewCountIncrementSingle() {
        return viewCountIncrementSingle;
    }

    public void setViewCountIncrementSingle(String viewCountIncrementSingle) {
        this.viewCountIncrementSingle = viewCountIncrementSingle;
    }

    @DisconfFileItem(name = "viewCount.increment.flag")
    public String getViewCountIncrementFlag() {
        return viewCountIncrementFlag;
    }

    public void setViewCountIncrementFlag(String viewCountIncrementFlag) {
        this.viewCountIncrementFlag = viewCountIncrementFlag;
    }

    @DisconfFileItem(name = "focusReminderCount.increment.flag")
    public String getFocusReminderCountIncrementFlag() {
        return focusReminderCountIncrementFlag;
    }

    public void setFocusReminderCountIncrementFlag(String focusReminderCountIncrementFlag) {
        this.focusReminderCountIncrementFlag = focusReminderCountIncrementFlag;
    }

    @DisconfFileItem(name = "jdbc.driverClassName")
    public String getDriverClassName() {
        return driverClassName;
    }

    @DisconfFileItem(name = "jdbc.url")
    public String getUrl() {
        return url;
    }

    @DisconfFileItem(name = "jdbc.username")
    public String getUsername() {
        return username;
    }

    @DisconfFileItem(name = "jdbc.password")
    public String getPassword() {
        return password;
    }

    @DisconfFileItem(name = "jdbc.validationQuery")
    public String getValidationQuery() {
        return validationQuery;
    }

    @DisconfFileItem(name = "druid.initialSize")
    public String getInitialSize() {
        return initialSize;
    }

    @DisconfFileItem(name = "druid.maxActive")
    public String getMaxActive() {
        return maxActive;
    }

    @DisconfFileItem(name = "druid.minIdle")
    public String getMinIdle() {
        return minIdle;
    }

    @DisconfFileItem(name = "druid.maxWait")
    public String getMaxWait() {
        return maxWait;
    }

    @DisconfFileItem(name = "druid.testOnBorrow")
    public String getTestOnBorrow() {
        return testOnBorrow;
    }

    @DisconfFileItem(name = "druid.testOnReturn")
    public String getTestOnReturn() {
        return testOnReturn;
    }

    @DisconfFileItem(name = "druid.testWhileIdle")
    public String getTestWhileIdle() {
        return testWhileIdle;
    }

    @DisconfFileItem(name = "druid.timeBetweenEvictionRunsMillis")
    public String getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    @DisconfFileItem(name = "druid.minEvictableIdleTimeMillis")
    public String getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    @DisconfFileItem(name = "druid.removeAbandoned")
    public String getRemoveAbandoned() {
        return removeAbandoned;
    }

    @DisconfFileItem(name = "druid.removeAbandonedTimeout")
    public String getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    @DisconfFileItem(name = "druid.logAbandoned")
    public String getLogAbandoned() {
        return logAbandoned;
    }

    @DisconfFileItem(name = "druid.filters")
    public String getFilters() {
        return filters;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(String testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void setTestWhileIdle(String testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public void setRemoveAbandoned(String removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public void setRemoveAbandonedTimeout(String removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    public void setLogAbandoned(String logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }


    @DisconfFileItem(name = "cache.redis.host")
    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    @DisconfFileItem(name = "cache.redis.port")
    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    @DisconfFileItem(name = "cache.redis.dbIndex")
    public int getRedisDbIndex() {
        return redisDbIndex;
    }

    public void setRedisDbIndex(int redisDbIndex) {
        this.redisDbIndex = redisDbIndex;
    }

    @DisconfFileItem(name = "cache.redis.password")
    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }


    @DisconfFileItem(name = "aliyun.sms.accessKeyId")
    public String getAccessKeyId() {
        return accessKeyId;
    }

    @DisconfFileItem(name = "aliyun.sms.accessKeySecret")
    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    @DisconfFileItem(name = "register.templeteCode")
    public String getRegisterTemCode() {
        return registerTemCode;
    }

    public void setRegisterTemCode(String registerTemCode) {
        this.registerTemCode = registerTemCode;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }


    @DisconfFileItem(name = "platform.customer.service.phone")
    public String getPlatformCustomerServicePhone() {
        return platformCustomerServicePhone;
    }

    public void setPlatformCustomerServicePhone(String platformCustomerServicePhone) {
        this.platformCustomerServicePhone = platformCustomerServicePhone;
    }

    @DisconfFileItem(name = "agency.white.list")
    public String getAgencyWhiteList() {
        return agencyWhiteList;
    }

    public void setAgencyWhiteList(String agencyWhiteList) {
        this.agencyWhiteList = agencyWhiteList;
    }

    @DisconfFileItem(name = "admin.white.list")
    public String getAdminWhiteList() {
        return adminWhiteList;
    }

    public void setAdminWhiteList(String adminWhiteList) {
        this.adminWhiteList = adminWhiteList;
    }

    @DisconfFileItem(name = "sso")
    public Boolean getSso() {
        return sso;
    }

    public void setSso(Boolean sso) {
        this.sso = sso;
    }

    @DisconfFileItem(name = "offline.bank.account.number")
    public String getOfflineBankAccountNumber() {
        return offlineBankAccountNumber;
    }

    public void setOfflineBankAccountNumber(String offlineBankAccountNumber) {
        this.offlineBankAccountNumber = offlineBankAccountNumber;
    }

    @DisconfFileItem(name = "offline.bank.account.name")
    public String getOfflineBankAccountName() {
        return offlineBankAccountName;
    }

    public void setOfflineBankAccountName(String offlineBankAccountName) {
        this.offlineBankAccountName = offlineBankAccountName;
    }

    @DisconfFileItem(name = "offline.bank.name")
    public String getOfflineBankName() {
        return offlineBankName;
    }

    public void setOfflineBankName(String offlineBankName) {
        this.offlineBankName = offlineBankName;
    }

    @DisconfFileItem(name = "basis.report")
    public String getBasisReport() {
        return basisReport;
    }

    public void setBasisReport(String basisReport) {
        this.basisReport = basisReport;
    }

    @DisconfFileItem(name = "complete.report")
    public String getCompleteReport() {
        return completeReport;
    }

    public void setCompleteReport(String completeReport) {
        this.completeReport = completeReport;
    }

    @DisconfFileItem(name = "applet.default.register.password")
    public String getAppletDefaultRegisterPassword() {
        return appletDefaultRegisterPassword;
    }

    public void setAppletDefaultRegisterPassword(String appletDefaultRegisterPassword) {
        this.appletDefaultRegisterPassword = appletDefaultRegisterPassword;
    }

    @DisconfFileItem(name = "website.applet.home.page")
    public String getWebsiteAppletHomePage() {
        return websiteAppletHomePage;
    }

    public void setWebsiteAppletHomePage(String websiteAppletHomePage) {
        this.websiteAppletHomePage = websiteAppletHomePage;
    }

    @DisconfFileItem(name = "shop.applet.home.page")
    public String getShopAppletHomePage() {
        return shopAppletHomePage;
    }

    public void setShopAppletHomePage(String shopAppletHomePage) {
        this.shopAppletHomePage = shopAppletHomePage;
    }

    @DisconfFileItem(name = "shop.invite.home.page")
    public String getShopInviteHomePage() {
        return shopInviteHomePage;
    }

    public void setShopInviteHomePage(String shopInviteHomePage) {
        this.shopInviteHomePage = shopInviteHomePage;
    }

    @DisconfFileItem(name = "applet.service.phone")
    public String getAppletServicePhone() {
        return appletServicePhone;
    }

    public void setAppletServicePhone(String appletServicePhone) {
        this.appletServicePhone = appletServicePhone;
    }

    @DisconfFileItem(name = "checkPreviewAt")
    public int getCheckPreviewAt() {
        return checkPreviewAt;
    }

    public void setCheckPreviewAt(int checkPreviewAt) {
        this.checkPreviewAt = checkPreviewAt;
    }

    @DisconfFileItem(name = "differenceDay")
    public int getDifferenceDay() {
        return differenceDay;
    }

    public void setDifferenceDay(int differenceDay) {
        this.differenceDay = differenceDay;
    }

    @DisconfFileItem(name = "first.deal.commission.percent")
    public String getFirstDealCommissionPercent() {
        return firstDealCommissionPercent;
    }

    public void setFirstDealCommissionPercent(String firstDealCommissionPercent) {
        this.firstDealCommissionPercent = firstDealCommissionPercent;
    }

    @DisconfFileItem(name = "second.deal.commission.percent")
    public String getSecondDealCommissionPercent() {
        return secondDealCommissionPercent;
    }

    public void setSecondDealCommissionPercent(String secondDealCommissionPercent) {
        this.secondDealCommissionPercent = secondDealCommissionPercent;
    }


    @DisconfFileItem(name = "environment")
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @DisconfFileItem(name = "servicePayExpired")
    public int getServicePayExpired() {
        return servicePayExpired;
    }

    public void setServicePayExpired(int servicePayExpired) {
        this.servicePayExpired = servicePayExpired;
    }

    @DisconfFileItem(name = "showAppletQrCode")
    public Boolean getShowAppletQrCode() {
        return showAppletQrCode;
    }

    public void setShowAppletQrCode(Boolean showAppletQrCode) {
        this.showAppletQrCode = showAppletQrCode;
    }

    @DisconfFileItem(name = "putAppletHomePage")
    public Boolean getPutAppletHomePage() {
        return putAppletHomePage;
    }

    public void setPutAppletHomePage(Boolean putAppletHomePage) {
        this.putAppletHomePage = putAppletHomePage;
    }
}
