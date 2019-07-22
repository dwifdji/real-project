package com.winback.core.service.systemsite.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.dao.systemsite.TCaseSettingDao;
import com.winback.core.facade.systemsite.req.SystemSiteReq;
import com.winback.core.model.systemsite.TCaseSetting;
import com.winback.core.service.systemsite.CaseSettingService;
import com.winback.core.vo.systemsite.CaseSiteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CaseSettingServiceImpl implements CaseSettingService {
    @Autowired
    private TCaseSettingDao tCaseSettingDao;

    @Override
    public Integer saveCaseSetting(SystemSiteReq.SaveCaseSiteReq req) {
        TCaseSetting caseSetting = new TCaseSetting();

        caseSetting.setCode(String.valueOf(System.currentTimeMillis()));
        caseSetting.setName(req.getName());
        caseSetting.setType(req.getType());
        caseSetting.setDeleteFlag(false);
        caseSetting.setStatus("0".equals(req.getStatus())?false: true);
        caseSetting.setCreateTime(new Date());
        caseSetting.setUpdateTime(new Date());

        return tCaseSettingDao.insert(caseSetting);
    }

    @Override
    public Integer updateOrDeleteCaseSetting(SystemSiteReq.UpdateCaseSiteReq req) {
        TCaseSetting caseSetting = tCaseSettingDao.selectById(req.getId());

        if ("0".equals(req.getUpdateFlag())) {
            caseSetting.setName(req.getName());
            caseSetting.setType(req.getType());
            caseSetting.setStatus("0".equals(req.getStatus()) ? false: true);
            caseSetting.setUpdateTime(new Date());
        }else {
            caseSetting.setDeleteFlag(true);
        }

        return tCaseSettingDao.updateById(caseSetting);
    }

    @Override
    public PageInfo getCaseSettingList(SystemSiteReq.CaseSiteListReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        List<CaseSiteVO> caseSiteVOS = tCaseSettingDao.getCaseSettingList(req.getType());
        return new PageInfo(caseSiteVOS == null ? new ArrayList():caseSiteVOS);
    }

    @Override
    public CaseSiteVO getCaseSettingById(SystemSiteReq.CaseSiteListReq req) {
        TCaseSetting caseSetting = tCaseSettingDao.selectById(req.getId());

        CaseSiteVO caseSiteVO = new CaseSiteVO();
        caseSiteVO.setCode(caseSetting.getCode());
        caseSiteVO.setStatus(caseSetting.getStatus() ? 0 : 1);
        caseSiteVO.setType(caseSetting.getType());
        caseSiteVO.setName(caseSetting.getName());
        caseSiteVO.setId(caseSetting.getId().toString());

        return caseSiteVO;
    }
}
