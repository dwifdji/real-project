package com.tzCloud.core.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.ViewCourtNumFacade;
import com.tzCloud.core.facade.caseMatching.req.ViewCourtNumReq;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;
import com.tzCloud.core.service.ViewCourtNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zxiao
 * @Title: ViewCourtNumProvider
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/19 10:21
 */
@Component
@Service(version = "1.0.0")
public class ViewCourtNumProvider implements ViewCourtNumFacade {

    @Resource
    private ViewCourtNumService viewCourtNumService;

    @Override
    public PageInfoResp<ViewCourtNumResp> findByCourtName(ViewCourtNumReq req) {
        return viewCourtNumService.findByCourtNum(req);
    }
}
