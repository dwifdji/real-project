package com._360pai.core.service.applet;

import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.ShopMessageTypeVO;
import com._360pai.core.model.applet.TAppletMessage;
import com._360pai.core.model.applet.TAppletReadMessageMap;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TAppletMessageService {

    PageInfo getShopMessageList(ShopReq.ShopMessageReq shopMessageReq);

    TAppletMessage getShopMessageById(String messageId);

    List<ShopMessageTypeVO> getMyShopMessage(Map<String, Object> params);

    void sendAccountRegisterMessage(Integer accountId);

    void sendSubordinateAccountRegisterSecondMessageIfNeed(Integer accountId);

    void sendAuthApplyApproveMessage(Integer accountId);

    void sendSubordinateAuthFinishSecondMessageIfNeed(Integer accountId);

    void saveTAppletReadMessage(TAppletReadMessageMap tAppletReadMessageMap);

    void sendOpenShopSuccessMessage(Integer accountId);

    void sendSubordinateOpenShopSuccessMessage(Integer accountId);

    void sendWithdrawVerifySuccessMessage(Integer partyId);

    void sendArrivalReminderMessage(Integer shopId);

    void sendCommissionReminderMessage(Integer partyId);

    PageInfo  getAnnoucementList(ShopReq.ShopMessageReq shopMessageReq);

    TAppletReadMessageMap getShopReadMessageBy(ShopReq.ShopMessageDetailReq shopMessageDetailReq);

    void sendEnrollingActivityApplyMessage(Integer accountId, String activityName);

    void sendShopUpdateApplyApproveMessage(Integer accountId);
}
