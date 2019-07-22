package com._360pai.core.aspact;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.common.constants.ServiceMessageEnum;
import com._360pai.core.condition.disposal.TDisposeRefuseRecordCondition;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.comprador.TCompradorRecommendDao;
import com._360pai.core.dao.comprador.TCompradorRequirementDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.dao.disposal.TDisposeLevelDao;
import com._360pai.core.dao.disposal.TDisposeRefuseRecordDao;
import com._360pai.core.dao.fastway.TFastwayAgencyApplyDao;
import com._360pai.core.dao.fastway.TFastwayDisposeApplyDao;
import com._360pai.core.dao.fastway.TFastwayFundApplyDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementDao;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.comprador.TCompradorRecommend;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.model.disposal.TDisposeRefuseRecord;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.core.service.activity.AuctionActivityService;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述：服务类 发送邮件服务类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/10/12 12:53
 */
@Component
@Slf4j
public class ServiceEmailService extends EmailService {

    private final static String TITLE   = "title";
    private final static String CONTENT = "content";

    @Autowired
    private TWithfudigRequirementDao withfudigRequirementDao;
    @Autowired
    private TCompradorRequirementDao compradorRequirementDao;
    @Autowired
    private TCompradorRecommendDao   compradorRecommendDao;
    @Autowired
    private TDisposalRequirementDao  disposalRequirementDao;
    @Autowired
    private CityDao                  cityDao;
    @Autowired
    private ProvinceDao              provinceDao;
    @Autowired
    private AuctionActivityService   auctionActivityService;
    @Autowired
    private TDisposeRefuseRecordDao  disposeRefuseRecordDao;
    @Autowired
    private TDisposeLevelDao         disposeLevelDao;
    @Autowired
    private TDisposeProviderDao      disposeProviderDao;
    @Autowired
    private TFastwayDisposeApplyDao fastwayDisposeApplyDao;
    @Autowired
    private TFastwayAgencyApplyDao fastwayAgencyApplyDao;
    @Autowired
    private TFastwayFundApplyDao fastwayFundApplyDao;
    @Resource
    private SystemProperties systemProperties;

    /**
     * 发送服务类审核邮件
     * <p>
     * serviceId 需求号
     * <p>
     * type 类型  2 处置服务  3 配置乐 4 资产大买办
     * <p>
     * emailType 发送人类型  1 发送给平台审核人  2 发送给 平台客服
     */
    public void sendServiceEmail(String serviceId, ServiceMessageEnum serviceMessageEnum) {

        log.info("开始发送服务类邮件，参数为：serviceId:{} ,serviceMessageEnum:{},sendToType:{}", serviceId, serviceMessageEnum);

        try {
            // 获取发送邮件的标题和内容
            Map<String, String> tileAndContent = makeTileAndContent(serviceId, serviceMessageEnum);
            // 获取邮件接收人
            List<String> emailList = getEmailList(serviceMessageEnum);

            if (CollectionUtils.isEmpty(emailList) || StringUtils.isBlank(tileAndContent.get(CONTENT))) {
                log.error("发送邮件失败，有参数为空 emailList:{},title:{},content:{}", emailList, tileAndContent.get(TITLE), tileAndContent.get(CONTENT));
            } else {
                // 发送邮件
                sendEmail(emailList, tileAndContent.get(TITLE), tileAndContent.get(CONTENT));
                log.info("发送邮件成功 emailList:{},title:{},content:{}", String.join(",", emailList), tileAndContent.get(TITLE), tileAndContent.get(CONTENT));
            }

        } catch (Exception e) {

            log.error("发送服务类邮件异常，异常信息为：{}", e);

        }


    }

    /**
     * 描述
     * emailType 发送人类型  1 发送给平台审核人  2 发送给 平台客服
     * type 类型  2 处置服务  3 配置乐 4 资产大买办
     *
     * @author : whisky_vip
     * @date : 2018/10/15 10:09
     */
    private List<String> getEmailList(ServiceMessageEnum serviceMessageEnum) {
        switch (serviceMessageEnum) {
            case COMPRADOR_ADD:
                return getEmailList("4", "1");
            case COMPRADOR_RECOMMENDER_ADD:
                return getEmailList("4", "2");
            case COMPRADOR_RECOMMEND_ADD:
                return getEmailList("4", "1");
            case WITHFUDIG_ADD:
                return getEmailList("3", "1");
            case WITHFUDIG_NOTPLATFORM_INVEST:
                return getEmailList("3", "2");
            case WITHFUDIG_SUPPLEMENTAL_INFORMATION:
                return getEmailList("3", "1");
            case DISPOSAL_REQUIREMENT_ADD:
                return getEmailList("2", "1");
            case BREAK_CONTRACT_3:
                return getEmailList("6", "1");
            case THIRD_CONFIRM_REPORT:
                return getEmailList("6", "1");
            case DISPOSAL_BIDDING_SUCCESS_TO_OPERATOR:
                return getEmailList("2", "2");
            case FASTWAY_DISPOSE_APPLY:
                return getEmailList("7", "1");
            case FASTWAY_AGENCY_APPLY:
                return getEmailList("7", "1");
            case FASTWAY_FUND_APPLY:
                return getEmailList("7", "1");
            default:
                return null;
        }

    }

    private Map<String, String> makeTileAndContent(String serviceId, ServiceMessageEnum serviceMessageEnum) {
        String title = "";

        String content = "";

        switch (serviceMessageEnum) {
            case COMPRADOR_ADD:
                //邮件主题：资产大买办需求**（需求号）需要及时审核
                //内容：资产大买办需求****（需求号）需要及时审核，请尽快到360PAI系统后台操作~
                TCompradorRequirement compradorAdd = compradorRequirementDao.selectById(serviceId);
                title = "资产大买办需求" + compradorAdd.getRequirementNo() + "需要及时审核";
                content = "资产大买办需求" + compradorAdd.getRequirementNo() + "需要及时审核，请尽快到360PAI系统后台操作~";
                break;
            case COMPRADOR_RECOMMENDER_ADD:
                //邮件主题：资产大买办需求**（需求号）有资产推介方确认投标
                //内容：资产大买办需求**（需求号）有资产推介方确认投标，请尽快到360PAI系统后台操作并跟进，评估是否告知资产需求方相关信息~
                TCompradorRequirement compradorRecommenderAdd = compradorRequirementDao.selectById(serviceId);
                title = "资产大买办需求" + compradorRecommenderAdd.getRequirementNo() + "有资产推介方确认投标";
                content = "资产大买办需求" + compradorRecommenderAdd.getRequirementNo() + "有资产推介方确认投标，请尽快到360PAI系统后台操作并跟进，评估是否告知资产需求方相关信息~";
                break;
            case COMPRADOR_RECOMMEND_ADD:
                //邮件主题：资产大买办需求**（需求号）需要审核
                //内容：资产大买办需求**（需求号）需要审核，请尽快到360PAI系统后台操作并跟进~
                TCompradorRecommend compradorRecommend = compradorRecommendDao.selectById(serviceId);
                title = "资产大买办需求" + compradorRecommend.getRecommendNo() + "需要审核";
                content = "资产大买办需求" + compradorRecommend.getRecommendNo() + "需要审核，请尽快到360PAI系统后台操作并跟进~";
                break;
            case WITHFUDIG_ADD:
                //邮件主题：配资需求**（需求号）需要及时审核
                //内容：配资需求****（需求号）需要及时审核，请尽快到360PAI系统后台操作~
                TWithfudigRequirement withfudigAdd = withfudigRequirementDao.selectById(serviceId);
                title = "配资需求" + withfudigAdd.getRequirementNo() + "需要及时审核";
                content = "配资需求" + withfudigAdd.getRequirementNo() + "需要及时审核，请尽快到360PAI系统后台操作~";
                break;
            case WITHFUDIG_NOTPLATFORM_INVEST:
                //邮件主题：配资需求**（需求号）有出资方意向配资
                //内容：配资需求****（需求号)已有资金服务商意向配资，请尽快到360PAI系统后台操作，并撮合服务~
                TWithfudigRequirement withfudigNotplatformInvest = withfudigRequirementDao.selectById(serviceId);
                title = "配资需求" + withfudigNotplatformInvest.getRequirementNo() + "有出资方意向配资";
                content = "配资需求" + withfudigNotplatformInvest.getRequirementNo() + "已有资金服务商意向配资，请尽快到360PAI系统后台操作，并撮合服务~";
                break;
            case WITHFUDIG_SUPPLEMENTAL_INFORMATION:
                //邮件主题：配资需求**（需求号）已补充配资所需资料
                //内容：配资需求****（需求号)已补充配资所需资料,请需要及时审核，尽快到360PAI系统后台操作~
                //有资金服务商有意向，请及时跟进
                TWithfudigRequirement withfudigSupplementalInformation = withfudigRequirementDao.selectById(serviceId);
                title = "配资需求" + withfudigSupplementalInformation.getRequirementNo() + "已补充配资所需资料";
                content = "配资需求" + withfudigSupplementalInformation.getRequirementNo() + "已补充配资所需资料,请需要及时审核，尽快到360PAI系统后台操作~ 有资金服务商有意向，请及时跟进";
                break;
            case DISPOSAL_REQUIREMENT_ADD:
                //邮件主题：处置需求**（需求号）需要及时审核
                //内容：处置需求****需要及时审核，请尽快到360I系统后台操作~
                TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(serviceId);
                title = "处置需求" + tDisposalRequirement.getDisposalNo() + "需要及时审核";
                content = "处置需求" + tDisposalRequirement.getDisposalNo() + "需要及时审核，请尽快到360PAI系统后台操作~";
                break;
            case THIRD_CONFIRM_REPORT:
                //第三方尽调  发送邮件
                //【尽调需求】+XX省XX城市
                Asset asset = auctionActivityService.getAssetByActivityId(Integer.valueOf(serviceId));
                AuctionActivity activity = auctionActivityService.getById(Integer.valueOf(serviceId));
                String[] split = asset.getCityId().split(",");
                City city = cityDao.selectById(Integer.valueOf(split[0]));
                Province province = provinceDao.selectById(Integer.valueOf(city.getProvinceId()));
                title = "【尽调需求】".concat(province.getName()).concat(city.getName());
                content = "拍品名称：" + activity.getAssetName() + "</br>"
                        + " 活动Id：" + serviceId + "</br>"
                        + " 拍卖方式：" + ActivityEnum.ActivityMode.getKeyByValue(activity.getMode()) + "</br>"
                        + " 保证金：" + activity.getDeposit() + "元</br>"
                        + " 预展开始时间：" + DateUtil.formatDate2Str(activity.getPreviewAt(), DateUtil.NORM_DATETIME_PATTERN) + "</br>"
                        + " 拍卖开始时间：" + DateUtil.formatDate2Str(activity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN);
                break;
            case BREAK_CONTRACT_3:
                //标题：【违约3次】XX城市+XX律所
                //正文：XX城市+XX律所+已违约3次+每次违约的时间
                TDisposeLevel level = disposeLevelDao.selectById(serviceId);
                TDisposeProvider provider = disposeProviderDao.selectById(level.getProviderId());
                City city1 = cityDao.selectById(level.getCityId());
                title = "【违约" + level.getSurveyRefuseNum() + "次】" + city1.getName() + provider.getCompanyName();
                content = city1.getName() + provider.getCompanyName() + "已违约" + level.getSurveyRefuseNum() + "次，"
                        + "每次违约时间: "
                        + getRefuseTime(Integer.valueOf(serviceId))
                        + "。";
                break;
            case DISPOSAL_BIDDING_SUCCESS_TO_OPERATOR:
                //邮件主题：处置需求**（需求号）有处置服务商投标
                //内容：处置需求****（需求号)已有处置服务商投标，请尽快到360PAI系统后台操作，并撮合服务~
                TDisposalRequirement requirement = disposalRequirementDao.selectById(serviceId);
                title = "处置需求" + requirement.getDisposalNo() + "有处置服务商投标";
                content = "处置需求" + requirement.getDisposalNo() + "已有处置服务商投标，请尽快到360PAI系统后台操作，并撮合服务~";
                break;
            case FASTWAY_DISPOSE_APPLY:
                // 快速通道处置服务商申请
                // XXX申请提交了处置服务商认证，请尽快处理+链接
                TFastwayDisposeApply disposeApply = fastwayDisposeApplyDao.selectById(serviceId);
                String name = "";
                String disposelink = "";
                if (disposeApply.getApplyType().equals(FastwayEnum.DisposeType.LAWYER)) {
                    name = disposeApply.getApplyFiled()
                            .getJSONObject(FastwayEnum.DisposeType.LAWYER)
                            .getString("cardName");
                    disposelink = getAdminDisposeLink(FastwayEnum.DisposeType.LAWYER, Integer.valueOf(serviceId));
                }
                if (disposeApply.getApplyType().equals(FastwayEnum.DisposeType.LAW_OFFICE)) {
                    name = disposeApply.getApplyFiled()
                            .getJSONObject(FastwayEnum.DisposeType.LAW_OFFICE)
                            .getString("lawOffice");
                    disposelink = getAdminDisposeLink(FastwayEnum.DisposeType.LAW_OFFICE, Integer.valueOf(serviceId));
                }
                title = "快速通道处置服务商申请";
                content = name + "申请提交了处置服务商认证，请尽快处理。</br>" + disposelink;
                break;
            case FASTWAY_AGENCY_APPLY:
                // 快速通道拍卖行申请
                // XXX申请提交了联拍机构认证，请尽快处理+链接
                TFastwayAgencyApply agencyApply = fastwayAgencyApplyDao.selectById(serviceId);
                String auctionName = "";
                String agencyLink = "";
                if (agencyApply.getApplyType().equals(FastwayEnum.AgencyType.AUCTION)) {
                    auctionName = agencyApply.getApplyFiled()
                            .getJSONObject(FastwayEnum.AgencyType.AUCTION)
                            .getString("name");
                    agencyLink = getAdminAuctionLink(Integer.valueOf(serviceId));
                }
                title = "快速通道联拍机构申请";
                content = auctionName + "申请提交了联拍机构认证，请尽快处理。</br>" + agencyLink;
                break;
            case FASTWAY_FUND_APPLY:
                // 快速通道资金服务商申请
                // XXX申请提交了资金服务商认证，请尽快处理+链接
                TFastwayFundApply fundApply = fastwayFundApplyDao.selectById(serviceId);
                String fundName = "";
                String fundLink = "";
                if (fundApply.getApplyType().equals(FastwayEnum.FundType.User)) {
                    fundName = fundApply.getApplyFiled()
                            .getJSONObject(FastwayEnum.FundType.User)
                            .getString("name");
                    fundLink = getAdminFundLink(Integer.valueOf(serviceId));

                }
                if (fundApply.getApplyType().equals(FastwayEnum.FundType.Company)) {
                    fundName = fundApply.getApplyFiled()
                            .getJSONObject(FastwayEnum.FundType.Company)
                            .getString("name");
                    fundLink = getAdminFundLink(Integer.valueOf(serviceId));
                }
                title = "快速通道资金服务商申请";
                content = fundName + "申请提交了资金服务商认证，请尽快处理。</br>" + fundLink;
                break;
            default:
                log.error("服务类发送邮件类型错误，参数为：serviceId:{} ,serviceMessageEnum:{}", serviceId, serviceMessageEnum);
        }

        Map<String, String> titleAndContent = Maps.newHashMap();
        titleAndContent.put(TITLE, title);
        titleAndContent.put(CONTENT, content);
        return titleAndContent;
    }

    private String getAdminDisposeLink(String applyType, Integer applyId) {
        if (systemProperties.getEnvironment().equals(FastwayEnum.Enviroment.ONLINE)) {
            return FastwayEnum.AdminLink.ONLINE_DOMAIN + FastwayEnum.AdminLink.DISPOSE_DETAIL + "applyId="+ applyId +"&applyType=" + applyType;
        }

        if (systemProperties.getEnvironment().equals(FastwayEnum.Enviroment.PRE)) {
            return FastwayEnum.AdminLink.PRE_DOMAIN + FastwayEnum.AdminLink.DISPOSE_DETAIL + "applyId="+ applyId +"&applyType=" + applyType;
        }
        return "";
    }

    private String getAdminAuctionLink(Integer applyId) {
        if (systemProperties.getEnvironment().equals(FastwayEnum.Enviroment.ONLINE)) {
            return FastwayEnum.AdminLink.ONLINE_DOMAIN + FastwayEnum.AdminLink.AUCTION_DETAIL + "applyId="+ applyId ;
        }

        if (systemProperties.getEnvironment().equals(FastwayEnum.Enviroment.PRE)) {
            return FastwayEnum.AdminLink.PRE_DOMAIN + FastwayEnum.AdminLink.AUCTION_DETAIL + "applyId="+ applyId ;
        }
        return "";
    }

    private String getAdminFundLink(Integer applyId) {
        if (systemProperties.getEnvironment().equals(FastwayEnum.Enviroment.ONLINE)) {
            return FastwayEnum.AdminLink.ONLINE_DOMAIN + FastwayEnum.AdminLink.FUND_DETAIL + "applyId="+ applyId ;
        }

        if (systemProperties.getEnvironment().equals(FastwayEnum.Enviroment.PRE)) {
            return FastwayEnum.AdminLink.PRE_DOMAIN + FastwayEnum.AdminLink.FUND_DETAIL + "applyId="+ applyId ;
        }
        return "";
    }

    private String getRefuseTime(Integer levelId) {
        TDisposeRefuseRecordCondition condition = new TDisposeRefuseRecordCondition();
        condition.setLevelId(levelId);
        List<TDisposeRefuseRecord> records = disposeRefuseRecordDao.selectList(condition);
        List<String>               list    = new ArrayList<>(records.size());
        records.forEach(u -> list.add(DateUtil.formatDate2Str(u.getRefuseTime(), DateUtil.NORM_DATETIME_PATTERN)));
        return list.toString();
    }


}
