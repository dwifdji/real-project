package com._360pai.core.provider.fastway;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.AgencyApplyAdminFacade;
import com._360pai.core.facade.fastway.req.AgencyApplyReq;
import com._360pai.core.facade.fastway.resp.AgencyApplyListVO;
import com._360pai.core.facade.fastway.resp.AgencyAuctionDetailVO;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.service.assistant.StaffService;
import com._360pai.core.service.fastway.AgencyApplyAdminService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-29 16:08
 */
@Service(version = "1.0.0")
public class AgencyApplyAdminProvider implements AgencyApplyAdminFacade {

    @Autowired
    private AgencyApplyAdminService agencyApplyAdminService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffDao staffDao;

    @Override
    public PageInfoResp findAgencyApplyByParam(AgencyApplyReq.AgencyFindReq req) {
        Map<String, Object> query = new HashMap<>(2);
        if (StringUtils.isNotBlank(req.getApplyStatus())) {
            query.put("applyStatus", req.getApplyStatus());
        }
        if (StringUtils.isNotBlank(req.getQuery())) {
            query.put("query", req.getQuery());
        }
        PageInfo pageInfo = agencyApplyAdminService.findByParam(query, req.getPage(), req.getPerPage());
        pageInfo.setList(convertModel(pageInfo.getList()));
        return new PageInfoResp(pageInfo);
    }

    @Override
    public AgencyAuctionDetailVO findAuctionDetailById(Integer applyId) {
        TFastwayAgencyApply apply = agencyApplyAdminService.findById(applyId);
        if (apply == null) {
            throw new BusinessException("该记录不存在");
        }
        return convertToAgencyAuctionDetailVO(apply);
    }

    @Override
    public int auctionUpdate(AgencyApplyReq.AuctionUpdateReq req, Integer operatorId) {
        return agencyApplyAdminService.auctionUpdate(req.getDetailVO(), req.getApplyId(), operatorId);
    }

    @Override
    public int auctionVerifyAccess(AgencyApplyReq.AuctionUpdateReq req, Integer operatorId) {
        return agencyApplyAdminService.auctionVerifyAccess(req.getDetailVO(), req.getApplyId(), operatorId);
    }

    @Override
    public int auctionVerifyDeny(AgencyApplyReq.AuctionUpdateReq req, Integer operatorId) {
        return agencyApplyAdminService.auctionVerifyDeny(req.getRefuseReason(), req.getApplyId(), operatorId);
    }

    private AgencyAuctionDetailVO convertToAgencyAuctionDetailVO(TFastwayAgencyApply apply) {
        AgencyAuctionDetailVO vo = apply.getApplyFiled()
                .getJSONObject(FastwayEnum.AgencyType.AUCTION)
                .toJavaObject(AgencyAuctionDetailVO.class);
        BeanUtils.copyProperties(apply, vo);
        vo.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(vo.getApplyStatus()));
        if (StringUtils.isNoneBlank(vo.getCityId())) {
            vo.setWorkCity(JSONObject.parseObject(vo.getCityId(), City.class));
        }
        if (apply.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(apply.getOpenAccountOperatorId()));
        }
        if (apply.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(apply.getBusinessOperatorId()));
        }
        return vo;
    }

    private List<AgencyApplyListVO> convertModel(List<TFastwayAgencyApply> source) {
        List<AgencyApplyListVO> target = new LinkedList<>();
        AgencyApplyListVO vo ;
        JSONObject jsonObject;
        for (TFastwayAgencyApply tmp : source) {
            vo         = new AgencyApplyListVO();
            jsonObject = getAuctionJSONObject(tmp.getApplyFiled());
            vo.setName(jsonObject.getString("name"));
            vo.setMobile(tmp.getMobile());
            vo.setCreateTime(tmp.getCreateTime());
            vo.setApplyStatus(tmp.getApplyStatus());
            vo.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(tmp.getApplyStatus()));
            vo.setOperatorName(getOperatorName(tmp.getOperatorId()));
            vo.setOperatorTime(tmp.getOperatorTime());
            vo.setApplyId(tmp.getId());
            vo.setRefuseReason(tmp.getRefuseReason());
            vo.setWorkCity(JSONObject.parseObject(jsonObject.getString("cityId"), City.class));
            target.add(vo);
        }
        return target;
    }

    private JSONObject getAuctionJSONObject(JSONObject applyFiled) {
        return applyFiled.getJSONObject(FastwayEnum.AgencyType.AUCTION);
    }

    private String getOperatorName(Integer operatorId) {
        if (operatorId != null) {
            Staff staff = staffService.getById(operatorId);
            if (staff != null) {
                return staff.getName();
            }
        }
        return null;
    }
}
