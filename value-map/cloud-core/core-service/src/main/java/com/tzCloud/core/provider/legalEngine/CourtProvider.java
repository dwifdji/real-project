package com.tzCloud.core.provider.legalEngine;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.core.facade.caseMatching.req.CourtReq;
import com.tzCloud.core.facade.caseMatching.req.ViewCourtNumReq;
import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;
import com.tzCloud.core.facade.legalEngine.CourtFacade;
import com.tzCloud.core.service.CourtMigrationService;
import com.tzCloud.core.service.CourtService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zxiao
 * @Title: CourtProvider
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/22 10:26
 */
@Component
@Service(version = "1.0.0")
public class CourtProvider implements CourtFacade {

    @Resource
    private CourtService courtService;
    @Resource
    private CourtMigrationService courtMigrationService;

    @Override
    public CourtResp courtDetail(CourtReq req) {
        return courtService.findByCourtName(req);
    }

    @Override
    public Object getAggs(CourtReq req) {
        return courtMigrationService.getAggs();

    }

    @Override
    public void migrationCourtToElasticSearch() {
        courtMigrationService.migrationCourtToElasticSearch();
    }
}
