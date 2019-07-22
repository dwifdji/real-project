package com._360pai.core.facade.fastway;

import com._360pai.core.facade.fastway.req.DisposeApplyReq;
import com._360pai.core.facade.fastway.resp.DisposeCompanyVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-26 11:04
 */
public interface DisposeApplyFacade {

    /**
     * 律师申请处置服务商
     * @param req
     * @param accountId
     * @return
     */
    int lawyerApplyDispose(DisposeApplyReq.LawyerApplyReq req, Integer accountId);

    /**
     * 律师事务所申请处置服务商
     * @param req
     * @param accountId
     * @return
     */
    int lawOfficeApplyDispose(DisposeApplyReq.LawOfficeApplyReq req, Integer accountId);

    /**
     * 发送文档给客户
     * @param emailAddress
     * @return
     */
    int sendDocByEmail(String[] emailAddress);

    /**
     * 根据手机号查律师认证信息
     * @param mobile
     * @return
     */
    Map<String, Object> getLawyerAuthInfoByMobile(String mobile);

    /**
     * 根据手机号查律所认证信息
     * @param mobile
     * @return
     */
    List<DisposeCompanyVO> getLawOfficeAuthInfoByMobile(String mobile);
}
