package com.tzCloud.core.service.report.impl;

import com.alibaba.fastjson.JSONObject;
import com.tzCloud.core.condition.message.TMapMessageTemplateCondition;
import com.tzCloud.core.dao.applicant.TMapApplicantDao;
import com.tzCloud.core.dao.message.TMapMessageTemplateDao;
import com.tzCloud.core.dto.com.PushMsgDto;
import com.tzCloud.core.model.applicant.TMapApplicant;
import com.tzCloud.core.model.message.TMapMessageTemplate;
import com.tzCloud.core.service.message.MessageService;
import com.tzCloud.core.service.report.ReportService;
import com.tzCloud.facade.report.req.ReportReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private TMapApplicantDao tMapApplicantDao;
    @Autowired
    private TMapMessageTemplateDao tMapMessageTemplateDao;
    @Autowired
    private MessageService messageService;


    @Override
    @Transactional
    public Integer saveReportApplicant(ReportReq.SaveReporttReq req) {
        TMapApplicant tMapApplicant = new TMapApplicant();
        tMapApplicant.setApplicantName(req.getApplicantName());
        tMapApplicant.setApplicantPhone(req.getApplicantPhone());
        tMapApplicant.setRemark(req.getRemark());
        tMapApplicant.setCreateTime(new Date());
        tMapApplicant.setUpdateTime(new Date());
        tMapApplicant.setDeleteFlag(0);

        int insert = tMapApplicantDao.insert(tMapApplicant);
        // 发送邮箱
        sendEmailMessage(req.getApplicantName(), req.getApplicantPhone(), req.getRemark());
        return insert;
    }

    private void sendEmailMessage(String applicantName, String applicantPhone, String remark) {
        TMapMessageTemplateCondition condition = new TMapMessageTemplateCondition();
        condition.setType("test");
        condition.setDeleteFlag(false);
        TMapMessageTemplate tMapMessageTemplate = tMapMessageTemplateDao.selectFirst(condition);

        PushMsgDto msgDto = new PushMsgDto();
        JSONObject param = new JSONObject();
        param.put("applicantName", applicantName);
        param.put("applicantPhone", applicantPhone);
        param.put("remark", remark);
        msgDto.setParam(param);

        messageService.sendEmail(tMapMessageTemplate, msgDto);
    }
}
