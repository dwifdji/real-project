package com.tzCloud.core.provider.legalEngine;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.facade.legalEngine.CollectFacade;
import com.tzCloud.core.facade.legalEngine.req.CollectReq;
import com.tzCloud.core.service.CollectService;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiaolei
 * @create 2019-04-24 15:14
 */
@Service(version = "1.0.0")
@Component
public class CollectProvider implements CollectFacade
{

    @Resource
    private CollectService collectService;

    @Override
    public Integer collect(CollectReq.Collect req)
    {
        Assert.notNull(req.getAccountId(), "accountId 不能为空");
        Assert.notNull(req.getCollectKey(), "collectKey 不能为空");
        Assert.notNull(req.getCollectType(), "collectType 不能为空");
        if (!CaseEnum.CollectTypeEum.containsKey(req.getCollectType()))
        {
            throw new RuntimeException("collectType 错误");
        }
        return collectService.collect(req);
    }

    @Override
    public void cancelCollect(CollectReq.Collect req)
    {
        collectService.cancelCollect(req);
    }

    @Override
    public PageInfoResp getCollectCase(CollectReq.Collect req) {
        return collectService.getCollectCase(req);
    }

    @Override
    public PageInfoResp getCollectLawyer(CollectReq.Collect req) {
        return collectService.getLawyerByES(req);
    }
}
