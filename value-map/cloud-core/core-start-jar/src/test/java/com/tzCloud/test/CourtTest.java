package com.tzCloud.test;

import com.alibaba.fastjson.JSONObject;
import com.tzCloud.arch.common.HttpUtilNew;
import com.tzCloud.arch.common.HttpUtilNewModel;
import com.tzCloud.core.condition.caseMatching.TTreeContentCondition;
import com.tzCloud.core.dao.caseMatching.TTreeContentDao;
import com.tzCloud.core.dao.legalEngine.TCourtDao;
import com.tzCloud.core.dao.legalEngine.TJudgePersonDao;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.core.model.legalEngine.TCourt;
import com.tzCloud.core.model.legalEngine.TJudgePerson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zxiao
 * @Title: CourtTest
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/19 16:06
 */
@Slf4j
public class CourtTest extends TestBase {

    @Resource
    private TCourtDao tCourtDao;
    @Resource
    private TTreeContentDao treeContentDao;
    @Resource
    private TJudgePersonDao judgePersonDao;

    @Test
    public void name() {
        List<TCourt> tCourts = tCourtDao.selectAll();

        for (TCourt court : tCourts) {
//            if (StringUtils.isNotEmpty(court.getProvince())) {
//                continue;
//            }
//            if (StringUtils.isNotBlank(court.getProvince())) {
//                continue;
//            }
            String name = court.getName();

            TTreeContentCondition contentCondition = new TTreeContentCondition();
            contentCondition.setKeyWord(name);
            TTreeContent tTreeContent = treeContentDao.selectFirst(contentCondition);

            if (tTreeContent.getField().equals("基层法院")) {
                TTreeContentCondition baseCondition = new TTreeContentCondition();
                baseCondition.setId(tTreeContent.getParentId());
                TTreeContent tTreeContent1 = treeContentDao.selectFirst(baseCondition);

                TTreeContentCondition Condition2 = new TTreeContentCondition();
                Condition2.setId(tTreeContent1.getParentId());
                TTreeContent tTreeContent2 = treeContentDao.selectFirst(Condition2);

                if (tTreeContent2.getField().equals("法院地域")) {
                    court.setProvince(tTreeContent2.getKeyWord());
                    tCourtDao.updateById(court);
                }
            }

            if (tTreeContent.getField().equals("中级法院")) {
                TTreeContentCondition Condition1 = new TTreeContentCondition();
                Condition1.setId(tTreeContent.getParentId());
                TTreeContent tTreeContent2 = treeContentDao.selectFirst(Condition1);

                if (tTreeContent2.getField().equals("法院地域")) {
                    court.setProvince(tTreeContent2.getKeyWord());
                    tCourtDao.updateById(court);
                }
            }


        }
    }

    @Test
    public void name3() {
        List<Map<String, String>> maps = tCourtDao.findDiffCourt();
        for (Map<String, String> map : maps) {
            String name = map.get("name");
            List<TTreeContent> contents;
            contents = treeContentDao.LikeByKeyWord(name);
            if (name.contains("县")) {
                String namestr = name.replace("县", "市");
                contents = treeContentDao.LikeByKeyWord(namestr);
                if (contents.isEmpty()) {
                    if (name.contains("县")) {
                         namestr = name.replace("县", "区");
                    }
                    contents = treeContentDao.LikeByKeyWord(namestr);
                }

            }
            if (name.contains("市")) {
                String namestr = name.replace("市", "区");
                contents = treeContentDao.LikeByKeyWord(namestr);
            }

            TCourt court = tCourtDao.findBy(name);
            if (!contents.isEmpty()) {
                TTreeContent tTreeContent = contents.get(0);

                log.info("法院原名称为：{}，查询后需改变的名称为：{}", name, tTreeContent.getKeyWord());
                court.setName(tTreeContent.getKeyWord());

                tCourtDao.updateById(court);
            }
        }
        System.out.println("maps = " + maps);
    }

    @Test
    public void name2() {
        List<TCourt> tCourts = tCourtDao.QueryList();

        for (TCourt court : tCourts) {
            HttpUtilNewModel json = HttpUtilNew.get("http://api.map.baidu.com/geocoder/v2/?address=" + court.getName() + "&ak=tI4ZKcyR4WiPNY7Uabu1qNoyzikMdtaw&output=json");
            if (json.getHtml().contains("服务器内部错误")) {
                continue;
            }
            JSONObject jsonObject = JSONObject.parseObject(json.getHtml());

            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject location = result.getJSONObject("location");

            String lat = location.getString("lat");
            String lng = location.getString("lng");

            log.info("地址；{}，获取到经纬度为：{}-{}", court.getName(), lng, lat);

            HttpUtilNewModel areaPage = HttpUtilNew.get("http://api.map.baidu.com/reverse_geocoding/v3/?ak=tI4ZKcyR4WiPNY7Uabu1qNoyzikMdtaw&output=json&coordtype=wgs84ll&location=" + lat + "," + lng + "");

            JSONObject area = JSONObject.parseObject(areaPage.getHtml());
            JSONObject result1 = area.getJSONObject("result");
            JSONObject addressComponent = result1.getJSONObject("addressComponent");
            String city = addressComponent.getString("city");

            log.info("获取到的城市信息为：{}", city);
            court.setCity(city);

            tCourtDao.updateById(court);
        }

    }

    @Test
    public void name1() {

        List<TJudgePerson> tJudgePeople = judgePersonDao.selectAll();

        for (TJudgePerson judgePerson : tJudgePeople) {
            if (judgePerson.getId() < 253847) {
                continue;
            }
            String presidingJudge = judgePerson.getPresidingJudge();
            String judicialOfficer = judgePerson.getJudicialOfficer();

            String[] split = presidingJudge.split(";");
            TreeSet<String> presidingSet = new TreeSet<>(Arrays.asList(split));
            String[] judiciaNames = judicialOfficer.split(";");
            TreeSet<String> judicialSet = new TreeSet<>(Arrays.asList(judiciaNames));

            StringBuilder presidingJudgeStr = new StringBuilder();
            StringBuilder actingJudgeStr = new StringBuilder();
            for (String name : presidingSet) {
                if (name.contains("审判长")) {
                    presidingJudgeStr.append(name).append(";");
                }

                if (name.contains("代理审判员")) {
                    actingJudgeStr.append(name).append(";");
                }
            }

            judgePerson.setPresidingJudge(presidingJudgeStr.toString());
            judgePerson.setActingJudge(actingJudgeStr.toString());

            StringBuilder judicialOfficerStr = new StringBuilder();
            StringBuilder executorStr = new StringBuilder();

            for (String name : judicialSet) {
                if (name.contains("审判员")) {
                    judicialOfficerStr.append(name).append(";");
                }

                if (name.contains("执行员")) {
                    executorStr.append(name).append(";");
                }
            }

            judgePerson.setJudicialOfficer(judicialOfficerStr.toString());
            judgePerson.setExecutor(executorStr.toString());

            judgePersonDao.updateById(judgePerson);
        }

    }
}
