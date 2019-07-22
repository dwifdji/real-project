package com._360pai.core.service.applet.impl;

import com._360pai.core.dao.applet.TAppletLeadDao;
import com._360pai.core.facade.shop.vo.ShopGuideVO;
import com._360pai.core.model.applet.TAppletLead;
import com._360pai.core.service.applet.TAppletLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TAppletLeadServiceImpl implements TAppletLeadService {

    @Autowired
    private TAppletLeadDao tAppletLeadDao;

    @Override
    public List<ShopGuideVO> getRemainingGuides(String openId) {

        return tAppletLeadDao.getRemainingGuides(openId);
    }

    @Override
    public void saveRemainingGuide(TAppletLead tAppletLead) {
        tAppletLeadDao.insert(tAppletLead);
    }
}
