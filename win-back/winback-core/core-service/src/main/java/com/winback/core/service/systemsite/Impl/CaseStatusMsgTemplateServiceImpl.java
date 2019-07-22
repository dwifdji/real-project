package com.winback.core.service.systemsite.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.dao._case.TCaseStatusDescDao;
import com.winback.core.dao.systemsite.TCaseStatusMsgTemplateDao;
import com.winback.core.facade.systemsite.req.SystemSiteReq;
import com.winback.core.model._case.TCaseStatusDesc;
import com.winback.core.model.systemsite.TCaseStatusMsgTemplate;
import com.winback.core.service.systemsite.CaseStatusMsgTemplateService;
import com.winback.core.vo.systemsite.CaseStatusMsgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CaseStatusMsgTemplateServiceImpl implements CaseStatusMsgTemplateService {

    @Autowired
    private TCaseStatusMsgTemplateDao tCaseStatusMsgTemplateDao;
    @Autowired
    private TCaseStatusDescDao tCaseStatusDescDao;

    @Override
    public Integer saveCaseStatusMsg(SystemSiteReq.CaseStatusMsgSaveReq req) {
        TCaseStatusMsgTemplate tCaseStatusMsgTemplate = new TCaseStatusMsgTemplate();

        tCaseStatusMsgTemplate.setCaseStatus(req.getCaseStatus());
        tCaseStatusMsgTemplate.setCode(String.valueOf(System.currentTimeMillis()));
        tCaseStatusMsgTemplate.setMsgBody(req.getMsgBody());
        tCaseStatusMsgTemplate.setStatus("0".equals(req.getStatus()) ? false: true);
        tCaseStatusMsgTemplate.setType(0);
        tCaseStatusMsgTemplate.setDeleteFlag(false);
        tCaseStatusMsgTemplate.setCreateTime(new Date());
        tCaseStatusMsgTemplate.setUpdateTime(new Date());

        return tCaseStatusMsgTemplateDao.insert(tCaseStatusMsgTemplate);
    }

    @Override
    public Integer updateOrDeleteCaseStatusMsg(SystemSiteReq.CaseStatusMsgUpdateeReq req) {

        TCaseStatusDesc tCaseStatusMsgTemplate = tCaseStatusDescDao.selectById(req.getId());

        if("0".equals(req.getUpdateFlag())) {
            tCaseStatusMsgTemplate.setStatusName(req.getCaseStatusDesc());
            tCaseStatusMsgTemplate.setStatusDesc(req.getMsgBody());
            tCaseStatusMsgTemplate.setUpdateTime(new Date());
        }else {
            tCaseStatusMsgTemplate.setUpdateTime(new Date());
        }

        return tCaseStatusDescDao.updateById(tCaseStatusMsgTemplate);
    }

    @Override
    public PageInfo getCaseStatusMsgList(SystemSiteReq.CaseStatusListReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        List<CaseStatusMsgVO> caseStatusMsgVOS = tCaseStatusMsgTemplateDao.getCaseStatusMsgList(req.getType());
        return new PageInfo(caseStatusMsgVOS == null?new ArrayList(): caseStatusMsgVOS);
    }

    @Override
    public CaseStatusMsgVO getCaseStatusMsgById(SystemSiteReq.CaseStatusListReq req) {
        TCaseStatusMsgTemplate tCaseStatusMsgTemplate = tCaseStatusMsgTemplateDao.selectById(req.getId());

        CaseStatusMsgVO caseStatusMsgVO = new CaseStatusMsgVO();
        caseStatusMsgVO.setCaseStatus(tCaseStatusMsgTemplate.getCaseStatus());
        caseStatusMsgVO.setMsgBody(tCaseStatusMsgTemplate.getMsgBody());
//        caseStatusMsgVO.setType(tCaseStatusMsgTemplate.getType());
        caseStatusMsgVO.setId(tCaseStatusMsgTemplate.getId());
        caseStatusMsgVO.setMsgBody(tCaseStatusMsgTemplate.getMsgBody());
        caseStatusMsgVO.setCode(tCaseStatusMsgTemplate.getCode());
//        caseStatusMsgVO.setStatus(tCaseStatusMsgTemplate.getStatus()?0:1);

        return caseStatusMsgVO;
    }
}
