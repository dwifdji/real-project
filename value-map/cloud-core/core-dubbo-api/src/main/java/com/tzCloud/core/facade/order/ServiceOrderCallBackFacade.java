package com.tzCloud.core.facade.order;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/15 15:03
 */
public interface ServiceOrderCallBackFacade {
    void doCallBackProcess(Integer serviceOrderId, String code, String msg);
}
