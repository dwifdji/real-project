
package com.tzCloud.core.dao.view.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.view.ViewCaseLawFirmCondition;
import com.tzCloud.core.dao.view.ViewCaseLawFirmDao;
import com.tzCloud.core.dao.view.mapper.ViewCaseLawFirmMapper;
import com.tzCloud.core.model.view.ViewCaseLawFirm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ViewCaseLawFirmDaoImpl extends AbstractDaoImpl<ViewCaseLawFirm, ViewCaseLawFirmCondition, BaseMapper<ViewCaseLawFirm, ViewCaseLawFirmCondition>> implements ViewCaseLawFirmDao {

    @Resource
    private ViewCaseLawFirmMapper viewCaseLawFirmMapper;

    @Override
    protected BaseMapper<ViewCaseLawFirm, ViewCaseLawFirmCondition> daoSupport() {
        return viewCaseLawFirmMapper;
    }

    @Override
    protected ViewCaseLawFirmCondition blankCondition() {
        return new ViewCaseLawFirmCondition();
    }

    @Override
    public List<Map<String, Object>> findByCourtName(String name) {
        return viewCaseLawFirmMapper.findByCourtName(name);
    }
}
