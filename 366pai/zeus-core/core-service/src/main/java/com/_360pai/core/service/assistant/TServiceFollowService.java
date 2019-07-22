package com._360pai.core.service.assistant;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;

import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/17 10:19
 */
public interface TServiceFollowService {
    int remove(ServiceFollowReq.Remove req);

    int add(ServiceFollowReq.Add req);

    PageUtils.Page list(ServiceFollowReq.List req);

    /**
     * 描述 判断是否关注
     *
     * @author : whisky_vip
     * @date : 2018/9/17 15:09
     */
    boolean followFlag(Integer relativeId, String relativeType, Integer partyId, Integer accountId);

    int removeList(ServiceFollowReq.Remove req);

    /**
     * 描述
     * disposal服务处置
     * withfudig 配资乐
     * comprador 大买办
     *
     * @author : whisky_vip
     * @date : 2018/10/9 10:03
     * @return like {diposal=3, withfudig=1, comprador=0}
     */
    Map<String,Integer> getFollowNumByAccountId(Integer accountId, Integer partyId);

    /**
     * 如果有partyId 则进行绑定
     * @param accountId
     * @param partyId
     * @return
     */
    int partyIdBind(Integer accountId, Integer partyId);
}
