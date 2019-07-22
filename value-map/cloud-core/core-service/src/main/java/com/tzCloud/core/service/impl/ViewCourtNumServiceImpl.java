package com.tzCloud.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.dao.view.ViewCourtNumDao;
import com.tzCloud.core.facade.caseMatching.req.ViewCourtNumReq;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;
import com.tzCloud.core.model.view.ViewCourtNum;
import com.tzCloud.core.service.ViewCourtNumService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zxiao
 * @Title: ViewCourtNumService
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/19 9:55
 */
@Service
public class ViewCourtNumServiceImpl implements ViewCourtNumService {

    @Resource
    private ViewCourtNumDao viewCourtNumDao;


    @Override
    public PageInfoResp<ViewCourtNumResp> findByCourtNum(ViewCourtNumReq req) {
        int page = req.getPage();
        int perPage = req.getPerPage();
        PageHelper.startPage(page, perPage);

        ViewCourtNum court = new ViewCourtNum();
        court.setCourtName(req.getCourtName());
        court.setProvince(req.getProvince());
        court.setCityName(req.getCityName());
        List<ViewCourtNumResp> courtNumList = viewCourtNumDao.findByCourtName(court);
        PageInfo<ViewCourtNumResp> pageInfo = new PageInfo<>(courtNumList);
        return new PageInfoResp<>(pageInfo);
    }
}
