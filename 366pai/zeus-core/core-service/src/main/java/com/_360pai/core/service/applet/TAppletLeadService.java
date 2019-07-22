package com._360pai.core.service.applet;

import com._360pai.core.facade.shop.vo.ShopGuideVO;
import com._360pai.core.model.applet.TAppletLead;

import java.util.List;

public interface TAppletLeadService {

    List<ShopGuideVO> getRemainingGuides(String openId);

    void saveRemainingGuide(TAppletLead tAppletLead);
}
