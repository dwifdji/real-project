package com._360pai.core.facade.assistant;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.resp.TokenResp;
import com._360pai.core.facade.assistant.vo.BankVo;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/24 15:16
 */
public interface CommonFacade {

    TokenResp getQiNiuToken();


    /**
     *
     * 根据type获取属性信息
     * @param  type 1 热门拍品 2 拍卖大厅 3 预招商
     */
    ResponseModel getPropertyByType(String type);



    /**
     *
     *获取所以的城市信息
     */
    ResponseModel getAllCities();

    /**
     * 获取所有银行
     */
    ListResp<BankVo> getAllBanks(String type);


    /**
     *
     *获取所以的城市信息
     */
    ResponseModel getAgencyInfo(String code);


    /**
     *
     *获取发送异常的邮件信息
     */
    String getEmailConfig(String code);

    void migrateAgencyData();

    void migrateAccountData();

    void syncCompanyFadadaEmial();

    void syncAgencyFadadaEmial();


    void testDelayQueue(long delay);

    void testQueue(String message);

    String saveExternalImgUrl(String imgUrl);

    List<String> getAppletAccountListNeedRepair(Map<String, Object> params);

    void syncProvinceId();

    void syncAuctionActivityBusTypeName();

    void syncAssetData();

    void syncEnrollingActivityData();

    void syncOldSubscribeMp360PaiUserToDb();

    void syncProvincePinyin();

    void syncAgencyPinyin();

    void removeActivityExpireKeyInRedisEndAtOver2Days();
}
