package com.tzCloud.test;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.condition.view.ViewBriefLawfirmLawyerCondition;
import com.tzCloud.core.dao.view.ViewBriefLawfirmLawyerDao;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.BriefResp;
import com.tzCloud.core.model.view.ViewBriefLawfirmLawyer;
import com.tzCloud.core.service.TBriefService;
import com.tzCloud.core.service.ViewTableService;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/7 16:52
 */
public class ServiceTest extends TestBase {

    @Resource
    private TBriefService tBriefService;

    @Test
    public void testTBrief() {
        CaseMatchingReq.BriefSearch req = new CaseMatchingReq.BriefSearch();
        req.setSearchStr("合同");
        PageInfoResp<BriefResp> briefList = tBriefService.getBriefList(req);
        System.out.println(briefList);
    }

    @Resource
    private ViewTableService viewTableService;

    @Test
    public void testCourtList() {
        CaseMatchingReq.CaseMatchingSearch req = new CaseMatchingReq.CaseMatchingSearch();
        req.setBriefStr("合同纠纷");
        System.out.println(viewTableService.getCourtList(req));
    }

    @Test
    public void testLawyerList() {
        CaseMatchingReq.LawyerSearch req = new CaseMatchingReq.LawyerSearch();
        req.setBriefStr("合同纠纷");
        req.setCourtName("上海市嘉定区人民法院");
        req.setLawFirmName("北京盈科(上海)律师事务所律师。");
        System.out.println(viewTableService.getLawyerList(req));
    }


    @Resource
    private ViewBriefLawfirmLawyerDao viewBriefLawfirmLawyerDao;

    /**
     * view_brief_lawfirm_lawyer 数据处理，人名根据 、分离开来
     */
    @Test
    public void processLawyerData() {
        ViewBriefLawfirmLawyerCondition condition = new ViewBriefLawfirmLawyerCondition();
        condition.setLawyerName("、");
        List<ViewBriefLawfirmLawyer> viewBriefLawfirmLawyers = viewBriefLawfirmLawyerDao.selectNeedProcessList(condition);

        for (ViewBriefLawfirmLawyer modle : viewBriefLawfirmLawyers) {
            String[] lawyerNames = modle.getLawyerName().split("、");
            for (String lawyerName:lawyerNames){
                ViewBriefLawfirmLawyerCondition condition2 = new ViewBriefLawfirmLawyerCondition();
                condition2.setLawyerName(lawyerName);
                condition2.setBrief(modle.getBrief());
                condition2.setLawyerFirm(modle.getLawyerFirm());
                List<ViewBriefLawfirmLawyer> list = viewBriefLawfirmLawyerDao.selectList(condition);
                if (CollectionUtils.isEmpty(list)){
                    ViewBriefLawfirmLawyer viewBriefLawfirmLawyer = new ViewBriefLawfirmLawyer();
                    viewBriefLawfirmLawyer.setBrief(modle.getBrief());
                    viewBriefLawfirmLawyer.setLawyerFirm(modle.getLawyerFirm());
                    viewBriefLawfirmLawyer.setLawyerName(lawyerName);
                    viewBriefLawfirmLawyerDao.insert(viewBriefLawfirmLawyer);
                }
            }
        }

    }
}
