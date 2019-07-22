package com._360pai.core.provider.fastway;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.DisposeApplyAdminFacade;
import com._360pai.core.facade.fastway.req.DisposeApplyReq;
import com._360pai.core.facade.fastway.resp.DisposeApplyVO;
import com._360pai.core.facade.fastway.resp.DisposeLawOfficeVO;
import com._360pai.core.facade.fastway.resp.DisposeLawyerVO;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.assistant.StaffService;
import com._360pai.core.service.fastway.DisposeApplyAdminService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-26 14:23
 */
@Component
@Service(version = "1.0.0")
public class DisposeApplyAdminProvider implements DisposeApplyAdminFacade {

    @Autowired
    private DisposeApplyAdminService disposeApplyAdminService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CompanyService companyService;

    @Override
    public PageInfoResp findDisposeApplyByParam(DisposeApplyReq.DisposeFindReq req) {
        Map<String, Object> query = new HashMap<>(2);
        if (StringUtils.isNotBlank(req.getApplyStatus())) {
            query.put("applyStatus", req.getApplyStatus());
        }
        if (StringUtils.isNotBlank(req.getQuery())) {
            query.put("query", req.getQuery());
        }
        PageInfo pageInfo = disposeApplyAdminService.findByParam(query, req.getPage(), req.getPerPage());
        pageInfo.setList(convertModel(pageInfo.getList()));
        return new PageInfoResp(pageInfo);
    }

    @Override
    public DisposeLawyerVO findLawyerDetailById(Integer applyId) {
        TFastwayDisposeApply byId = disposeApplyAdminService.findById(applyId);
        JSONObject jsonObject = byId.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAWYER);
        DisposeLawyerVO disposeLawyerVO = JSONObject.parseObject(jsonObject.toJSONString(), DisposeLawyerVO.class);
        disposeLawyerVO.setApplyStatus(byId.getApplyStatus());
        disposeLawyerVO.setCreateTime(byId.getCreateTime());
        disposeLawyerVO.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(disposeLawyerVO.getApplyStatus()));
        disposeLawyerVO.setResidentCity1(JSONObject.parseObject(jsonObject.getString("residentCity"), City.class));
        if (byId.getOpenAccountOperatorId() != null) {
            disposeLawyerVO.setOpenAccountOperator(staffDao.getName(byId.getOpenAccountOperatorId()));
        }
        if (byId.getBusinessOperatorId() != null) {
            disposeLawyerVO.setBusinessOperator(staffDao.getName(byId.getBusinessOperatorId()));
        }
        return disposeLawyerVO;
    }

    @Override
    public DisposeLawOfficeVO findLawOfficeDetailById(Integer applyId) {
        TFastwayDisposeApply byId = disposeApplyAdminService.findById(applyId);
        TAccount tAccount = accountService.selectByPrimaryKey(byId.getAccountId());
        JSONObject jsonObject = byId.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAW_OFFICE);
        DisposeLawOfficeVO disposeLawOfficeVO = JSONObject.parseObject(jsonObject.toJSONString(), DisposeLawOfficeVO.class);
        disposeLawOfficeVO.setApplyStatus(byId.getApplyStatus());
        disposeLawOfficeVO.setCreateTime(byId.getCreateTime());
        disposeLawOfficeVO.setWorkCity1(JSONObject.parseObject(jsonObject.getString("workCity"), City.class));
        disposeLawOfficeVO.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(disposeLawOfficeVO.getApplyStatus()));
        disposeLawOfficeVO.setRemark(byId.getRemark());
        disposeLawOfficeVO.setBeginDate(byId.getBeginDate());
        disposeLawOfficeVO.setEndDate(byId.getEndDate());
        disposeLawOfficeVO.setMobile(tAccount.getMobile());
        if (byId.getPartyId() != null) {
            TCompany companyById = companyService.findCompanyById(byId.getPartyId());
            disposeLawOfficeVO.setSocialCreditCode(companyById.getLicense());
        }
        if (byId.getOpenAccountOperatorId() != null) {
            disposeLawOfficeVO.setOpenAccountOperator(staffDao.getName(byId.getOpenAccountOperatorId()));
        }
        if (byId.getBusinessOperatorId() != null) {
            disposeLawOfficeVO.setBusinessOperator(staffDao.getName(byId.getBusinessOperatorId()));
        }
        return disposeLawOfficeVO;
    }

    @Override
    public int lawyerVerify(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId) {
        lawyerUpdate(req, operatorId);
        return disposeApplyAdminService.lawyerVerify(req.getApplyStatus(), req.getApplyId(), operatorId,req.getRefuseReason(), req.getOpenAccountOperatorId(), req.getBusinessOperatorId());
    }

    @Override
    public int lawOfficeVerify(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId) {
        // 保存当前修改
        lawOfficeUpdate(req, operatorId);
        return disposeApplyAdminService.lawOfficeVerify(req.getApplyStatus(), req.getApplyId(), operatorId,req.getRefuseReason(), req.getOpenAccountOperatorId(), req.getBusinessOperatorId());
    }

    @Override
    public int lawyerUpdate(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId) {
        if (req.getLawyerVO() == null) {
            return 0;
        }
        req.getLawyerVO().setOpenAccountOperatorId(req.getOpenAccountOperatorId());
        req.getLawyerVO().setBusinessOperatorId(req.getBusinessOperatorId());
//        req.setBusinessOperatorId(req.getLawyerVO().getBusinessOperatorId());
//        req.setOpenAccountOperatorId(req.getLawyerVO().getOpenAccountOperatorId());
        JSONObject json = new JSONObject();
        json.put(FastwayEnum.DisposeType.LAWYER, req.getLawyerVO());
        return disposeApplyAdminService.lawyerUpdate(req.getApplyId(), json, operatorId);
    }

    @Override
    public int lawOfficeUpdate(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId) {
        if (req.getLawOfficeVO() == null) {
            return 0;
        }
//        req.getLawOfficeVO().setOpenAccountOperatorId(req.getOpenAccountOperatorId());
//        req.getLawOfficeVO().setBusinessOperatorId(req.getBusinessOperatorId());
        DisposeLawOfficeVO detailVO = req.getLawOfficeVO();
        if (detailVO.getWorkCity1() != null) {
            detailVO.setWorkCity(JSON.toJSONString(detailVO.getWorkCity1()));
        }
        req.setOpenAccountOperatorId( detailVO.getOpenAccountOperatorId());
        req.setBusinessOperatorId( detailVO.getBusinessOperatorId());
        JSONObject json = new JSONObject();
        json.put(FastwayEnum.DisposeType.LAW_OFFICE, detailVO);
        return disposeApplyAdminService.lawOfficeUpdate(req.getApplyId(), json, operatorId);
    }

    private List<DisposeApplyVO> convertModel(List<TFastwayDisposeApply> source) {
        List<DisposeApplyVO> target = new LinkedList<>();
        for (int i = 0, size = source.size(); i < size; i++) {
            TFastwayDisposeApply tmp = source.get(i);
            DisposeApplyVO       vo  = new DisposeApplyVO();
            if (FastwayEnum.DisposeType.LAWYER.equals(tmp.getApplyType())) {
                vo.setApplyTypeDesc("律师");
                if (null != tmp.getPartyId()) {
                    vo.setAuthorInfo("律师");
                }
                JSONObject jsonObject = tmp.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAWYER);
                vo.setApplyName(jsonObject.getString("cardName"));
                vo.setCity(JSONObject.parseObject(jsonObject.getString("residentCity"), City.class));
//                vo.setMobile(jsonObject.getString("contactMobile"));
            }
            if (FastwayEnum.DisposeType.LAW_OFFICE.equals(tmp.getApplyType())) {
                vo.setApplyTypeDesc("律师事务所");
                if (null != tmp.getPartyId()) {
                    vo.setAuthorInfo("律师事务所");
                }
                JSONObject jsonObject = tmp.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAW_OFFICE);
                vo.setApplyName(jsonObject.getString("lawOffice"));
                vo.setCity(JSONObject.parseObject(jsonObject.getString("workCity"), City.class));
//                vo.setMobile(jsonObject.getString("contactMobile"));
            }
            vo.setMobile(tmp.getMobile());
            vo.setApplyId(tmp.getId());
            vo.setApplyStatus(tmp.getApplyStatus());
            vo.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(tmp.getApplyStatus()));
            vo.setApplyType(tmp.getApplyType());
            vo.setCreateTime(tmp.getCreateTime());
            vo.setOperatorTime(tmp.getOperatorTime());
            if (tmp.getOperatorId() != null) {
                Staff staff = staffService.getById(tmp.getOperatorId());
                if (staff != null) {
                    vo.setOperatorName(staff.getName());
                }
            }
            target.add(vo);
        }
        return target;
    }

}
