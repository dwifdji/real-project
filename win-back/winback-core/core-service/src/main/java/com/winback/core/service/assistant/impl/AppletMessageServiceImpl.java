package com.winback.core.service.assistant.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.AppletReq;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.utils.AnnouncementUtil;
import com.winback.core.dao.applet.TAppletMessageDao;
import com.winback.core.dao.applet.TAppletReadMessageMapDao;
import com.winback.core.dao.assistant.TAppReadMessageMapDao;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.account.vo.AppMessage;
import com.winback.core.facade.account.vo.AppletMessage;
import com.winback.core.model.applet.TAppletMessage;
import com.winback.core.service.assistant.AppletMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xdrodger
 * @Title: AppletMessageServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/2/12 15:33
 */
@Slf4j
@Service
public class AppletMessageServiceImpl implements AppletMessageService {
    @Autowired
    private TAppletMessageDao messageDao;
    @Autowired
    private TAppletReadMessageMapDao messageMapDao;

    @Override
    public int getUnreadMessageCount(Integer extBindId) {
        return messageDao.unreadMessageCount(extBindId);
    }

    @Override
    public PageInfoResp<AppletMessage> getAppletMessageList(AppletAccountReq.MessageReq req) {
        PageInfo<AppletMessage> pageInfo = messageDao.getAppletMessageList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (AppletMessage item : pageInfo.getList()) {
            String formatString = AnnouncementUtil.parse("{{", "}}", item.getContent(), JSON.parseObject(item.getParam()));
            item.setContent(formatString);
        }
        return new PageInfoResp<AppletMessage>(pageInfo);
    }

    @Override
    public AppletMessage getAppletMessage(AppletAccountReq.MessageReq req) {
        PageInfo<AppletMessage> pageInfo = messageDao.getAppletMessageList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        if (pageInfo.getList().size() > 0) {
            AppletMessage vo = pageInfo.getList().get(0);
            if (!vo.getReadFlag()) {
                messageMapDao.readMessage(req.getLoginId(), req.getId());
            }
            return vo;
        }
        return null;
    }

    @Override
    public void save(TAppletMessage message) {
        messageDao.insert(message);
    }
}
