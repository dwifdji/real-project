package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AppletEnum;
import com._360pai.core.condition.applet.TAppletMessageCondition;
import com._360pai.core.condition.applet.TAppletReadMessageMapCondition;
import com._360pai.core.dao.account.TAccountExtBindDao;
import com._360pai.core.dao.applet.TAppletMessageDao;
import com._360pai.core.dao.applet.TAppletReadMessageMapDao;
import com._360pai.core.dao.applet.TAppletShopDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.AppletMessageVO;
import com._360pai.core.facade.shop.vo.ShopMessageTypeVO;
import com._360pai.core.model.account.TAccountExtBind;
import com._360pai.core.model.applet.TAppletMessage;
import com._360pai.core.model.applet.TAppletReadMessageMap;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.service.applet.TAppletMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TAppletMessageServiceImpl implements TAppletMessageService {

    @Autowired
    private TAppletMessageDao tAppletMessageDao;
    @Autowired
    private TAccountExtBindDao accountExtBindDao;

    @Autowired
    private TAppletReadMessageMapDao tAppletReadMessageMapDao;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private TAppletShopDao shopDao;
    @Autowired
    private GatewayMqSender mqSender;

    @Override
    public PageInfo getShopMessageList(ShopReq.ShopMessageReq shopMessageReq) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shopMessageReq.getShopId());
        params.put("openId", shopMessageReq.getOpenId());

        PageHelper.startPage(shopMessageReq.getPage(), shopMessageReq.getPerPage());
        List<AppletMessageVO> appletMessageVOS = tAppletMessageDao.getShopMessageList(params);

        return new PageInfo<>(appletMessageVOS);
    }

    @Override
    public TAppletMessage getShopMessageById(String messageId) {
        TAppletMessageCondition tAppletMessageCondition = new TAppletMessageCondition();
        tAppletMessageCondition.setId(Integer.parseInt(messageId));
        TAppletMessage tAppletMessage = tAppletMessageDao.selectFirst(tAppletMessageCondition);
        return tAppletMessage;
    }

    @Override
    public List<ShopMessageTypeVO> getMyShopMessage(Map<String, Object> params) {
        List<ShopMessageTypeVO> myShopMessage = tAppletMessageDao.getMyShopMessage(params);
        String publishAt = "";
        for (ShopMessageTypeVO shopMessageTypeVO: myShopMessage) {
            if("1".equals(shopMessageTypeVO.getMessageType()) && StringUtils.isBlank(shopMessageTypeVO.getPublicAt())) {
                publishAt  = tAppletMessageDao.getRecentMessageAt(params);
            }else if("2".equals(shopMessageTypeVO.getMessageType()) && StringUtils.isBlank(shopMessageTypeVO.getPublicAt())){
                publishAt = tAppletMessageDao.getRecentAnnouncementAt(params);
            }else{
                publishAt = shopMessageTypeVO.getPublicAt();
            }

            publishAt = checkFormatPublicAt(publishAt);
            shopMessageTypeVO.setPublicAt(publishAt);
        }
        return myShopMessage;
    }

    private static String checkFormatPublicAt(String publishAt) {
        Date date = null;
        if(StringUtils.isNotBlank(publishAt)) {
            date = DateUtil.parse(publishAt, "yyyy/mm/dd");
            if(DateUtil.getYear(date).equals(DateUtil.getYear(new Date()))) {
                return publishAt.substring(5, publishAt.length());
            }else {
                return  publishAt;
            }
        }

        return "";
    }

    @Override
    public void sendAccountRegisterMessage(Integer accountId) {
        TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
        if (accountExtBind == null) {
            return;
        }
        // 注册完成
        TAppletMessage message = new TAppletMessage();
        message.setType(AppletEnum.MessageType.ACCOUNT_REGISTER.getKey());
        message.setName(AppletEnum.MessageType.ACCOUNT_REGISTER.getValue());
        message.setContext(String.format("【360PAI】您已成功注册，同时为您开通360PAI平台账户，默认密码为%s。请登录360pai.com查看更多资产信息。", systemProperties.getAppletDefaultRegisterPassword()));
        message.setOpenId(accountExtBind.getExtUserId());
        int result = tAppletMessageDao.insert(message);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        // 下线注册完成 （第一次）
        if (AccountEnum.InviteType.DP.getKey().equals(accountExtBind.getInviteType()) && accountExtBind.getInviteCode() != null) {
            message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.SUBORDINATE_ACCOUNT_REGISTER.getKey());
            message.setName(AppletEnum.MessageType.SUBORDINATE_ACCOUNT_REGISTER.getValue());
            message.setContext(String.format("恭喜！您的好友%s已完成注册，快叫他来一起开店吧！", accountExtBind.getNickName()));
            message.setShopId(accountExtBind.getInviteCode());
            result = tAppletMessageDao.insert(message);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            // 下线注册完成 （第二次，3天内并未完成认证）
            mqSender.subordinateAccountRegisterSecondEnqueue(accountId + "", 3 * 24 * 3600);
        }
        return;
    }

    @Override
    public void sendSubordinateAccountRegisterSecondMessageIfNeed(Integer accountId) {
        TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
        if (accountExtBind.getCurrentPartyId() != null) {
            if (AccountEnum.InviteType.DP.getKey().equals(accountExtBind.getInviteType()) && accountExtBind.getInviteCode() != null) {
                TAppletMessage message = new TAppletMessage();
                message.setType(AppletEnum.MessageType.SUBORDINATE_ACCOUNT_REGISTER_SECOND.getKey());
                message.setName(AppletEnum.MessageType.SUBORDINATE_ACCOUNT_REGISTER_SECOND.getValue());
                message.setContext(String.format("您的好友%s已完成注册，但还未认证账户哦！", accountExtBind.getNickName()));
                message.setShopId(accountExtBind.getInviteCode());
                int result = tAppletMessageDao.insert(message);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
        }
    }

    @Override
    public void sendAuthApplyApproveMessage(Integer accountId) {
        TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
        if (accountExtBind == null) {
            return;
        }
        // 认证完成（第一次）
        if (AccountEnum.InviteType.DP.getKey().equals(accountExtBind.getInviteType()) && accountExtBind.getInviteCode() != null) {
            TAppletMessage message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.SUBORDINATE_AUTH_FINISH.getKey());
            message.setName(AppletEnum.MessageType.SUBORDINATE_AUTH_FINISH.getValue());
            message.setContext(String.format("恭喜！您的好友%s已完成账户认证，快叫他来一起开店吧！", accountExtBind.getNickName()));
            message.setShopId(accountExtBind.getInviteCode());
            int result = tAppletMessageDao.insert(message);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            // 认证完成（第二次，认证完成后5天内并未完成开店）
            mqSender.subordinateAuthFinishSecondEnqueue(accountId + "", 5 * 24 * 3600);
        }
        return;
    }

    @Override
    public void sendSubordinateAuthFinishSecondMessageIfNeed(Integer accountId) {
        TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
        if (accountExtBind == null || accountExtBind.getCurrentPartyId() == null) {
            return;
        }
        TAppletShop shop = shopDao.getByPartyId(accountExtBind.getCurrentPartyId());
        if (shop == null) {
            return;
        }
        if (AccountEnum.InviteType.DP.getKey().equals(accountExtBind.getInviteType()) && accountExtBind.getInviteCode() != null) {
            TAppletMessage message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.SUBORDINATE_AUTH_FINISH_SECOND.getKey());
            message.setName(AppletEnum.MessageType.SUBORDINATE_AUTH_FINISH_SECOND.getValue());
            message.setContext(String.format("恭喜！您的好友%s还没完成开店哦~快叫他来一起开店吧！", accountExtBind.getNickName()));
            message.setShopId(accountExtBind.getInviteCode());
            int result = tAppletMessageDao.insert(message);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return;
    }


    @Override
    public void saveTAppletReadMessage(TAppletReadMessageMap tAppletReadMessageMap) {
        tAppletReadMessageMapDao.insert(tAppletReadMessageMap);
    }

    @Override
    public void sendOpenShopSuccessMessage(Integer accountId) {
        try {
            TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
            if (accountExtBind == null) {
                return;
            }
            TAppletMessage message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.OPEN_SHOP.getKey());
            message.setName(AppletEnum.MessageType.OPEN_SHOP.getValue());
            message.setContext("恭喜！您已成功开通店铺，您可进行添加拍品和热推拍品。");
            message.setOpenId(accountExtBind.getExtUserId());
            TAppletShop shop = shopDao.getByPartyId(accountExtBind.getCurrentPartyId());
            message.setShopId(shop.getId());
            int result = tAppletMessageDao.insert(message);
            if (result == 0) {
                log.error("发送开店成功店铺消息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSubordinateOpenShopSuccessMessage(Integer accountId) {
        try {
            TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
            if (accountExtBind == null ) {
                return;
            }
            TAppletShop shop = shopDao.getByPartyId(accountExtBind.getCurrentPartyId());
            if (shop == null) {
                return;
            }
            if (AccountEnum.InviteType.DP.getKey().equals(accountExtBind.getInviteType()) && accountExtBind.getInviteCode() != null) {
                TAppletMessage message = new TAppletMessage();
                message.setType(AppletEnum.MessageType.SUBORDINATE_OPEN_SHOP.getKey());
                message.setName(AppletEnum.MessageType.SUBORDINATE_OPEN_SHOP.getValue());
                String inviteCommission = shop.getServiceCharge().divide(new BigDecimal(2)).toPlainString();
                message.setContext(String.format("恭喜！您的好友已成功开通店铺，您将获得推荐金￥%s。 推荐金到账将在3个工作日内完成。", inviteCommission));
                message.setShopId(accountExtBind.getInviteCode());
                int result = tAppletMessageDao.insert(message);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendWithdrawVerifySuccessMessage(Integer partyId) {
        try {
            TAccountExtBind accountExtBind = accountExtBindDao.findAppletByCurrentPartyId(partyId);
            if (accountExtBind == null ) {
                return;
            }
            TAppletMessage message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.WITHDRAW.getKey());
            message.setName(AppletEnum.MessageType.WITHDRAW.getValue());
            message.setContext("恭喜！您的提现申请已通过。 提现金将在3~5个工作日内到账。如有问题，请致电400-015-00005。");
            message.setOpenId(accountExtBind.getExtUserId());
            int result = tAppletMessageDao.insert(message);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendArrivalReminderMessage(Integer shopId) {
        try {
            TAppletShop shop = shopDao.getByPartyId(shopId);
            if (shop == null) {
                return;
            }
            TAppletMessage message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.ARRIVAL_REMINDER.getKey());
            message.setName(AppletEnum.MessageType.ARRIVAL_REMINDER.getValue());
            message.setContext("【360PAI】您的推荐金已到账，请到佣金记录查询。如有问题，请致电400-015-00005。");
            message.setShopId(shopId);
            int result = tAppletMessageDao.insert(message);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCommissionReminderMessage(Integer partyId) {
        try {
            TAccountExtBind accountExtBind = accountExtBindDao.findAppletByCurrentPartyId(partyId);
            if (accountExtBind == null ) {
                return;
            }
            TAppletMessage message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.COMMISSION_REMINDER.getKey());
            message.setName(AppletEnum.MessageType.COMMISSION_REMINDER.getValue());
            message.setContext("【360PAI】您的项目分佣已到账，请到佣金记录查询。如有问题，请致电400-015-00005。");
            message.setOpenId(accountExtBind.getExtUserId());
            int result = tAppletMessageDao.insert(message);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageInfo getAnnoucementList(ShopReq.ShopMessageReq shopMessageReq) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shopMessageReq.getShopId());
        params.put("openId", shopMessageReq.getOpenId());

        PageHelper.startPage(shopMessageReq.getPage(), shopMessageReq.getPerPage());
        List<AppletMessageVO> appletMessageVOS = tAppletMessageDao.getAnnoucementList(params);

        return new PageInfo<>(appletMessageVOS);
    }

    @Override
    public TAppletReadMessageMap getShopReadMessageBy(ShopReq.ShopMessageDetailReq shopMessageDetailReq) {
        TAppletReadMessageMapCondition tAppletReadMessageMapCondition = new TAppletReadMessageMapCondition();
        tAppletReadMessageMapCondition.setOpenId(shopMessageDetailReq.getOpenId());
        tAppletReadMessageMapCondition.setShopId(Integer.parseInt(shopMessageDetailReq.getShopId()));
        tAppletReadMessageMapCondition.setMessageId(Integer.parseInt(shopMessageDetailReq.getMessageId()));

        TAppletReadMessageMap tAppletReadMessageMap = tAppletReadMessageMapDao.selectFirst(tAppletReadMessageMapCondition);
        return tAppletReadMessageMap;
    }

    @Override
    public void sendEnrollingActivityApplyMessage(Integer accountId, String activityName) {
        try {
            TAccountExtBind extBind = accountExtBindDao.findAppletByAccountId(accountId);
            if (extBind == null) {
                return;
            }
            if (AccountEnum.InviteType.DP.getKey().equals(extBind.getInviteType())) {
                TAppletMessage message = new TAppletMessage();
                message.setType(AppletEnum.MessageType.ARRIVAL_REMINDER.getKey());
                message.setName(AppletEnum.MessageType.ARRIVAL_REMINDER.getValue());
                message.setContext(String.format("【360PAI】您的好友%s已成功报名%s招商信息请及时跟进", StringEscapeUtils.unescapeJava(extBind.getNickName()), activityName));
                message.setShopId(extBind.getInviteCode());
                int result = tAppletMessageDao.insert(message);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendShopUpdateApplyApproveMessage(Integer accountId) {
        try {
            TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
            if (accountExtBind == null) {
                return;
            }
            TAppletMessage message = new TAppletMessage();
            message.setType(AppletEnum.MessageType.SHOP_UPDATE_APPLY_APPROVE_REMINDER.getKey());
            message.setName(AppletEnum.MessageType.SHOP_UPDATE_APPLY_APPROVE_REMINDER.getValue());
            message.setContext("【360PAI】您的昵称或头像修改成功。");
            message.setOpenId(accountExtBind.getExtUserId());
            int result = tAppletMessageDao.insert(message);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
