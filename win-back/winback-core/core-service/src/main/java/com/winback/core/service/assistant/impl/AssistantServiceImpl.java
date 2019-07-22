package com.winback.core.service.assistant.impl;

import com.gexin.fastjson.JSON;
import com.winback.arch.common.Device;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.core.dao.assistant.TAppVersionUpdateDao;
import com.winback.core.dao.connect.TAccountConnectMapDao;
import com.winback.core.facade.assistant.req.AppAssistantReq;
import com.winback.core.facade.assistant.resp.AppAssistantResp;
import com.winback.core.model.assistant.TAppVersionUpdate;
import com.winback.core.model.connect.TAccountConnectMap;
import com.winback.core.service.assistant.AssistantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xdrodger
 * @Title: AssistantServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 10:38
 */
@Slf4j
@Service
public class AssistantServiceImpl implements AssistantService {

    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private TAccountConnectMapDao connectMapDao;
    @Autowired
    private TAppVersionUpdateDao appVersionUpdateDao;

    @Override
    public Device getDevice(Integer accountId) {
        if (accountId == null) {
            return null;
        }
        String cache = (String) redisCachemanager.hGet(RedisKeyConstant.APP_DEVICE, accountId + "");
        if (StringUtils.isEmpty(cache)) {
            /*TAccountConnectMap connectMap = connectMapDao.findLatestBy(accountId);
            if (connectMap == null) {
                return null;
            }
            Device device = new Device();
            device.setNotificationToken(connectMap.getClientId());*/
            return null;
        }
        Device device = JSON.parseObject(cache, Device.class);
        return device;
    }

    @Override
    public void saveDevice(Integer accountId, Device device) {
        if (device == null) {
            return;
        }
        redisCachemanager.hSet(RedisKeyConstant.APP_DEVICE, accountId + "", JSON.toJSONString(device));
        if (StringUtils.isEmpty(device.getNotificationToken())) {
            return;
        }
        TAccountConnectMap connectMap = connectMapDao.findBy(accountId, device.getNotificationToken());
        if (connectMap == null) {
            connectMap = new TAccountConnectMap();
            connectMap.setAccountId(accountId);
            connectMap.setClientId(device.getNotificationToken());
            connectMapDao.insert(connectMap);
        }
    }

    @Override
    public void simpleSaveDevice(Integer accountId, Device device) {
        if (device == null) {
            return;
        }
        redisCachemanager.hSet(RedisKeyConstant.APP_DEVICE, accountId + "", JSON.toJSONString(device));
    }

    @Override
    public AppAssistantResp.CheckUpdateResp checkUpdate(AppAssistantReq.CheckUpdateReq req) {
        AppAssistantResp.CheckUpdateResp resp = new AppAssistantResp.CheckUpdateResp();
        TAppVersionUpdate appVersionUpdate = appVersionUpdateDao.findLatestBy(req.getDevice().getDeviceType());
        if (appVersionUpdate != null) {
            resp.setVersion(appVersionUpdate.getVersion());
            resp.setAlertFlag(appVersionUpdate.getAlertFlag());
            resp.setForceFlag(appVersionUpdate.getForceFlag());
            resp.setDesc(appVersionUpdate.getDescription());
        }
        return resp;
    }
}
