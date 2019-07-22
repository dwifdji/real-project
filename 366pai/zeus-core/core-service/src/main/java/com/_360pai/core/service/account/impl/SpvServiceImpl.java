package com._360pai.core.service.account.impl;

import com._360pai.core.condition.account.TSpvApplyCondition;
import com._360pai.core.condition.account.TSpvCondition;
import com._360pai.core.dao.account.TSpvApplyDao;
import com._360pai.core.dao.account.TSpvDao;
import com._360pai.core.model.account.TSpv;
import com._360pai.core.model.account.TSpvApply;
import com._360pai.core.service.account.SpvService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RuQ
 * @Title: SpvServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/21 20:09
 */
@Service
public class SpvServiceImpl implements SpvService {

    @Resource
    private TSpvDao spvDao;

    @Resource
    private TSpvApplyDao tSpvApplyDao;

    @Override
    public boolean saveSpv(TSpv spv) {
        return spvDao.insert(spv) == 1;
    }

    @Override
    public boolean updateSpv(TSpv spv) {
        return spvDao.updateById(spv) == 1;
    }

    @Override
    public List<TSpv> getSpvByCompanyId(Integer companyId) {
        TSpvCondition condition = new TSpvCondition();
        condition.setCompanyId(companyId);
        return spvDao.selectList(condition);
    }


    @Override
    public List<TSpv> getSpvByParam(TSpvCondition spvCondition) {
        return spvDao.selectList(spvCondition);
    }

    @Override
    public TSpv getSpvById(Integer spvId) {
        return spvDao.selectById(spvId);
    }

    @Override
    public TSpv getSpvByMobile(String mobile) {
        TSpvCondition condition = new TSpvCondition();
        condition.setMobile(mobile);
        return spvDao.selectFirst(condition);
    }

    @Override
    public boolean saveSpvApply(TSpvApply spvApply) {
        return tSpvApplyDao.insert(spvApply) == 1;
    }

    @Override
    public boolean updateSpvApply(Integer spvApplyId, String status, Integer operatorId) {
        TSpvApply spvApply = new TSpvApply();
        spvApply.setId(spvApplyId);
        spvApply.setStatus(status);
        spvApply.setOperatorId(operatorId);
        return tSpvApplyDao.updateById(spvApply) == 1;
    }

    @Override
    public List<TSpvApply> getSpvApplyByMobileAndStatus(String mobile, String status) {
        TSpvApplyCondition spvApply = new TSpvApplyCondition();
        spvApply.setMobile(mobile);
        spvApply.setStatus(status);
        return tSpvApplyDao.selectList(spvApply);
    }

    @Override
    public List<TSpvApply> getSpvApplyByLicenseAndStatus(String license, String status) {
        TSpvApplyCondition spvApply = new TSpvApplyCondition();
        spvApply.setLicense(license);
        spvApply.setStatus(status);
        return tSpvApplyDao.selectList(spvApply);
    }

    @Override
    public TSpvApply getSpvApplyById(Integer applyId) {
        return tSpvApplyDao.selectById(applyId);
    }

    @Override
    public PageInfo<TSpvApply> getSpvListByPage(Integer companyId, Integer page, Integer perPage) {
        PageHelper.startPage(page, perPage);
        TSpvApplyCondition spvApply = new TSpvApplyCondition();
        spvApply.setCompanyId(companyId);
        List<TSpvApply> list = tSpvApplyDao.selectList(spvApply);
        return new PageInfo<>(list);
    }
}
