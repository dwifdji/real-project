package com._360pai.core.service.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.AgencyApplyReq;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.account.resp.AgencyApplyResp;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.account.vo.AgencyApplyRecordVo;
import com._360pai.core.facade.account.vo.AgencyVo;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface AgencyService {

    TAgency findByAgencyId (Integer id);

    TAgency findByLicense(String license);

    TAgency findByMobile(String mobile);

    TAgency findByAgencyCode(String code);

    List<TAgency> searchAgency(String cityId,String name);

    boolean saveAgency(TAgency tAgency);

    PageInfoResp<AgencyVo> getAgencyListByPage(AgencyReq.QueryReq req);

    AgencyResp.DetailResp getAgencyById(AgencyReq.BaseReq req);

    AgencyApplyResp agencyApply(AgencyApplyReq.CreateReq req);

    PageInfoResp<AgencyApplyRecordVo> getAgencyApplyListByPage(AgencyApplyReq.QueryReq req);

    AgencyApplyResp.DetailResp getAgencyApplyRecordById(AgencyApplyReq.BaseReq req);

    TAgencyApplyRecord getAgencyApplyRecordById(Integer id);

    AgencyApplyResp agencyApplyUpdate(AgencyApplyReq.UpdateReq req);

    AgencyApplyResp agencyApplyApprove(AgencyApplyReq.BaseReq req, boolean needSms);

    AgencyApplyResp agencyApplyReject(AgencyApplyReq.BaseReq req);

    AgencyResp updateAgency(AgencyReq.UpdateReq req);

    AgencyResp updateAgency(AgencyReq.AgencyUpdateReq req);

    AgencyResp updateAgency(AgencyReq.UpdateDfftOrFadadaReq req);

    AgencyResp setChannelAgent(AgencyReq.BaseReq req);

    AgencyResp selectChannelAgent(AgencyReq.BaseReq req);

    AgencyResp cancelSelectChannelAgent(AgencyReq.BaseReq req);

    AgencyResp agencyPortalOffline(AgencyReq.BaseReq req);

    AgencyResp agencyPortalOnline(AgencyReq.BaseReq req);

    AgencyResp.DfftResp paymentAccountBalance(AgencyReq.BaseReq req);

    PageInfo getPartnerAgencyList(int page, int perPage);

    AgencyResp.ProfileResp profile(AgencyReq.BaseReq req);

    AgencyResp setCanCheckReservePrice(AgencyReq.BaseReq req);


    void updateAgency(Map<String, Object> map);

    Map<String, Object> getDefaultAgency();


    ResponseModel getAgencyList(AgencyReq.BaseReq req);

    ResponseModel searchAgencyList(AgencyReq.QueryReq req);
}
