package com.winback.core.service.assistant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.enums.MessageTemplateEnum;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.dao.assistant.TAppMessageDao;
import com.winback.core.dao.assistant.TAppReadMessageMapDao;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.vo.AppMessage;
import com.winback.core.service.assistant.AppMessageService;
import com.winback.core.utils.BusinessUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xdrodger
 * @Title: AppMessageServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:17
 */
@Slf4j
@Service
public class AppMessageServiceImpl implements AppMessageService {

    @Autowired
    private TAppMessageDao messageDao;
    @Autowired
    private TAppReadMessageMapDao messageMapDao;

    @Override
    public int getUnreadMessageCount(Integer accountId) {
        return messageDao.unreadMessageCount(accountId);
    }


    @Override
    public int getUnreadConnectCount(Integer accountId, Boolean lawyerFlag) {

        try {
            String type = null;

            return messageDao.unreadConnectCount(accountId,type);

        }catch (Exception e){

        }

        return 0;
    }

    @Override
    public PageInfoResp<AppMessage> getAppMessageListByPage(AppAccountReq.MessageReq req) {
        PageInfo<AppMessage> pageInfo = messageDao.getAppMessageList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (AppMessage item : pageInfo.getList()) {
            item.setCreateTimeDesc(BusinessUtil.formatAppMessageTime(item.getCreateTime()));
        }
        return new PageInfoResp<AppMessage>(pageInfo);
    }

    @Override
    public AppMessage getAppMessage(AppAccountReq.MessageReq req) {
        PageInfo<AppMessage> pageInfo = messageDao.getAppMessageList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        if (pageInfo.getList().size() > 0) {
            AppMessage vo = pageInfo.getList().get(0);
            vo.setCreateTimeDesc(BusinessUtil.formatAppMessageTime(vo.getCreateTime()));
            vo.setPrimaryKey(getPrimaryKeyInfo(vo));


            if (!vo.getReadFlag()) {
                messageMapDao.readMessage(req.getLoginId(), req.getId());
            }
            return vo;
        }
        return null;
    }

    private String getPrimaryKeyInfo(AppMessage vo) {
        //获取caseId 的类型 12  16  20  24 30
        if(MessageTemplateEnum.TYPE.TYPE_12.getType().equals(vo.getType())||MessageTemplateEnum.TYPE.TYPE_16.getType().equals(vo.getType())||MessageTemplateEnum.TYPE.TYPE_20.getType().equals(vo.getType())||MessageTemplateEnum.TYPE.TYPE_24.getType().equals(vo.getType())||MessageTemplateEnum.TYPE.TYPE_30.getType().equals(vo.getType())||MessageTemplateEnum.TYPE.TYPE_41.getType().equals(vo.getType())){

            if(StringUtils.isNotEmpty(vo.getParam())){
                JSONObject jsonObject = JSON.parseObject(vo.getParam());

                return jsonObject.getString("caseId");
            }

        }

        return null;
    }

}
