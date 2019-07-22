package com._360pai.core.job;

import com._360pai.arch.common.HttpUtils;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.ExcelUtil;
import com._360pai.arch.common.utils.ListUtils;
import com._360pai.arch.core.file.FilePathUtils;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.assistant.TJobActivityTotalViewCountCondition;
import com._360pai.core.condition.enrolling.EnrollingActivityCondition;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.assistant.TJobActivityTotalViewCountDao;
import com._360pai.core.dao.assistant.TJobActivityViewCountRecodeDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.TJobActivityTotalViewCount;
import com._360pai.core.model.assistant.TJobActivityViewCountRecode;
import com._360pai.core.model.assistant.TSmsEmailConfig;
import com._360pai.core.model.enrolling.AgencyPortalEnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.activity.AuctionActivityShareStatsService;
import com._360pai.core.service.assistant.TSmsEmailConfigService;
import com._360pai.core.service.enrolling.AgencyPortalEnrollingActivityService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.utils.EmailUtils;
import com._360pai.core.vo.enrolling.web.MyEnrollingActivityVO;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/12/3 17:56
 */
@Service
@Slf4j
public class JobUtils {

    @Autowired
    private SystemProperties                     systemProperties;
    @Autowired
    private AuctionActivityShareStatsService     auctionActivityShareStatsService;
    @Autowired
    private AgencyService                        agencyService;
    @Autowired
    private AuctionActivityDao                   auctionActivityDao;
    @Autowired
    private EnrollingActivityDao                 enrollingActivityDao;
    @Autowired
    private TJobActivityTotalViewCountDao        tJobActivityTotalViewCountDao;
    @Autowired
    private TJobActivityViewCountRecodeDao       tJobActivityViewCountRecodeDao;
    @Autowired
    private EnrollingActivityService             enrollingActivityService;
    @Autowired
    private AgencyPortalEnrollingActivityService agencyPortalEnrollingActivityService;
    @Autowired
    private EmailUtils                           emailUtils;
    @Autowired
    private TSmsEmailConfigService               tSmsEmailConfigService;

    public void activityViewCountAutoIncrement() {

        List<TAgency> tAgencies = agencyService.searchAgency(null, null);

        // 1,判断disconf是否需要开启执行job
        if (StringUtils.isNotBlank(systemProperties.getViewCountIncrementFlag()) && Boolean.valueOf(systemProperties.getViewCountIncrementFlag())) {
            // 执行拍卖
            doActivity(tAgencies);
            // 执行预招商
            doEnrollingActivity(tAgencies);
        }
    }

    /**
     * 判断第三方系统中 预招商是否结束 ，结束了，则修改状态为结束
     */
    public void thirdEnrollingActivityStatusProcess() {

        EnrollingActivityCondition condition = new EnrollingActivityCondition();
        condition.setThirdPartyStatus(1);
        condition.setStatus(EnrollingEnum.STATUS.ONLINE.getType());

        List<EnrollingActivity> list = enrollingActivityDao.selectList(condition);

        List<Map<String, Object>> listMap = new ArrayList<>();

        Map<String, Object> map = Maps.newHashMap();
        for (EnrollingActivity enrollingActivity : list) {
            if (StringUtils.isNotBlank(enrollingActivity.getThirdPartyUrl())) {
                // 判断是否需要改变状态
                String status = HttpUtils.getStatusThirdEnrolling(enrollingActivity.getThirdPartyTitle(),enrollingActivity.getThirdPartyUrl());
                if (!EnrollingStatus.ROADSHOW.key.equals(status)) {
                    enrollingActivity.setStatus(EnrollingEnum.STATUS.CLOSED.getType());
                    enrollingActivityDao.updateById(enrollingActivity);
                    map.put("名称", enrollingActivity.getName());
                    map.put("状态", EnrollingStatus.getDesc(status));
                    listMap.add(map);
                }
            }
        }

        sendEmail(listMap);

    }


    public void sendEmail(List<Map<String, Object>> mapList) {
        if (CollectionUtils.isEmpty(mapList)) {
            return;
        }
        try {
            File         file         = new File(FilePathUtils.getTempPath() + "thirdEnrolling" + System.currentTimeMillis() + ".xls");
            OutputStream outputStream = new FileOutputStream(file);
            ExcelUtil.createWorkBook(mapList, new String[]{"名称", "状态"}, new String[]{"名称", "状态"}).write(outputStream);

            EmailSendReq emailSendReq = new EmailSendReq();
            List<String> emailList    = new ArrayList<>();

            TSmsEmailConfig mobileByType = tSmsEmailConfigService.getMobileByType("22");
            String          emailStr     = mobileByType.getAuditorEmail();
            if (org.apache.commons.lang.StringUtils.isNotBlank(emailStr)) {
                emailList.addAll(Arrays.asList(emailStr.split(",")));
            }

            emailSendReq.setTitle("第三方预招商活动状态改变");
            emailSendReq.setContent("见附件");
            emailSendReq.setEmail(emailList);
            emailSendReq.setSendType(EmailSendReq.CONTENT_TYPE);
            List<File> fileList = new ArrayList<>();
            fileList.add(file);
            emailSendReq.setFiles(fileList);
            emailUtils.sendEmail(emailSendReq);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    enum EnrollingStatus {
        //        roadshow 招商中 tobid 已转竞价 offline 线下已处理
        ROADSHOW("roadshow", "招商中"),
        TOBID("tobid", "已转竞价 "),
        OFFLINE("offline", "线下已处理");
        private String key;
        private String value;

        EnrollingStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getDesc(String key) {
            for (Status e : Status.values()) {
                if (e.getKey().equals(key)) {
                    return e.getValue();
                }
            }
            return key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    enum Status {
        ZERO("0", "即将开拍"),
        ONE("1", "正在进行 "),
        TWO("2", "结束状态"),
        SEVEN("7", "撤回"),
        EIGHT("8", "中止");
        private String key;
        private String value;

        Status(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getDesc(String key) {
            for (Status e : Status.values()) {
                if (e.getKey().equals(key)) {
                    return e.getValue();
                }
            }
            return key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private Integer getStatus(String url) {
        Integer status = 0;
        String  text   = HttpUtils.sendGet(url);

        if (StringUtils.isNotBlank(text)) {
            Document document = Jsoup.parse(text);
            Element  element  = document.getElementById("J_Status");
            String   ss       = element.attr("value");
            // 0即将开拍 ，1 正在进行 2为结束状态  7撤回 8中止
            if (Integer.valueOf(ss) > 1) {
                status = Integer.valueOf(ss);
            }
        }

        //test
//        if (url.equals("hehe")) {
//            return 2;
//        }
        return status;
    }

    private void doActivity(List<TAgency> tAgencies) {
        List<AuctionActivity> aheadAndBeginAuction = auctionActivityDao.getAheadAndBeginAuction();
        if (CollectionUtils.isNotEmpty(aheadAndBeginAuction)) {
            for (AuctionActivity auctionActivity : aheadAndBeginAuction) {

                // key为agencyCode, value为需要增加的浏览量
                Map<String, Integer> map      = makeMap(tAgencies);
                Boolean              aBoolean = checkIfDo(auctionActivity.getId(), 1);
                if (!aBoolean) {
                    continue;
                }

                Set<String> agencyCodeKeySet = map.keySet();
                for (String agencyCode : agencyCodeKeySet) {

                    if (map.get(agencyCode) < 0) {
                        continue;
                    }
                    auctionActivityShareStatsService.activityView(auctionActivity.getId(), agencyCode, map.get(agencyCode));

                    TJobActivityViewCountRecode model = new TJobActivityViewCountRecode();
                    model.setActivityId(auctionActivity.getId());
                    model.setActivityType(1);
                    model.setCreateAt(new Date());
                    model.setViewCountNum(map.get(agencyCode));
                    model.setCreateDate(DateUtil.format(new Date(), DateUtil.NORM_DATE_PATTERN));
                    Optional<TAgency> cartOptional = tAgencies.parallelStream().filter(tAgency -> tAgency.getCode().equals(agencyCode)).findFirst();
                    cartOptional.ifPresent(tAgency -> model.setAgencyId(tAgency.getId()));
                    tJobActivityViewCountRecodeDao.insert(model);
                }
            }
        }
    }

    private void doEnrollingActivity(List<TAgency> tAgencies) {

        EnrollingListReqDto params = new EnrollingListReqDto();
        params.setStatus("ONLINE");
        List<MyEnrollingActivityVO> enrollingActivityVOS = enrollingActivityDao.searchHomeActivity(params);
        if (CollectionUtils.isNotEmpty(enrollingActivityVOS)) {
            for (MyEnrollingActivityVO myEnrollingActivityVO : enrollingActivityVOS) {
                Map<String, Integer> map      = makeMap(tAgencies);
                Boolean              aBoolean = checkIfDo(Integer.valueOf(myEnrollingActivityVO.getId()), 2);
                if (!aBoolean) {
                    continue;
                }
                Set<String> agencyCodeKeySet = map.keySet();
                for (String agencyCode : agencyCodeKeySet) {
                    if (map.get(agencyCode) < 0) {
                        continue;
                    }

                    Integer           agencyId;
                    Optional<TAgency> cartOptional = tAgencies.parallelStream().filter(tAgency -> tAgency.getCode().equals(agencyCode)).findFirst();
                    if (cartOptional.isPresent()) {
                        agencyId = cartOptional.get().getId();

                        EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(myEnrollingActivityVO.getId());
                        enrollingActivity.setId(Integer.valueOf(myEnrollingActivityVO.getId()));
                        enrollingActivity.setBrowseNumber(enrollingActivity.getBrowseNumber() + map.get(agencyCode));
                        enrollingActivityService.updateEnrollingActivityById(enrollingActivity);

                        AgencyPortalEnrollingActivity agencyPortalEnrollingActivity = new AgencyPortalEnrollingActivity();
                        agencyPortalEnrollingActivity.setAgencyId(agencyId);
                        agencyPortalEnrollingActivity.setEnrollingActivityId(Integer.valueOf(myEnrollingActivityVO.getId()));
                        agencyPortalEnrollingActivity.setCreatedAt(DateUtil.getSysTime());
                        agencyPortalEnrollingActivity.setViewCount(map.get(agencyCode));
                        agencyPortalEnrollingActivityService.saveOrUpdate(agencyPortalEnrollingActivity, map.get(agencyCode));

                        TJobActivityViewCountRecode model = new TJobActivityViewCountRecode();
                        model.setActivityId(enrollingActivity.getId());
                        model.setActivityType(2);
                        model.setCreateAt(new Date());
                        model.setViewCountNum(map.get(agencyCode));
                        model.setAgencyId(agencyId);
                        model.setCreateDate(DateUtil.format(new Date(), DateUtil.NORM_DATE_PATTERN));

                        tJobActivityViewCountRecodeDao.insert(model);
                    }
                }

            }
        }

    }


    private Map<String, Integer> makeMap(List<TAgency> tAgencies) {
        List<String> agencyCodes = new ArrayList<>();
        tAgencies.forEach(agency -> agencyCodes.add(agency.getCode()));
        List                 randomList    = ListUtils.getRandomList(agencyCodes, Integer.valueOf(systemProperties.getViewCountIncrementAgencyNum()));
        Map<String, Integer> result        = new HashMap<>(Integer.valueOf(systemProperties.getViewCountIncrementAgencyNum()));
        List<Integer>        randomNumList = ListUtils.getRandomNumList(Integer.valueOf(systemProperties.getViewCountIncrementAgencyNum()), systemProperties.getViewCountIncrementSingle());
        int                  size          = randomList.size();
        for (int i = 0; i < size; i++) {
            result.put((String) randomList.get(i), randomNumList.get(i));
        }
        return result;
    }

    private Boolean checkIfDo(Integer activityId, Integer type) {
        int totalActivityViewCount = tJobActivityViewCountRecodeDao.getTotalActivityViewCount(type, activityId);

        int                                 totalNum;
        TJobActivityTotalViewCountCondition tJobActivityTotalViewCountCondition = new TJobActivityTotalViewCountCondition();
        tJobActivityTotalViewCountCondition.setActivityType(type);
        tJobActivityTotalViewCountCondition.setActivityId(activityId);
        TJobActivityTotalViewCount tJobActivityTotalViewCount = tJobActivityTotalViewCountDao.selectFirst(tJobActivityTotalViewCountCondition);

        if (tJobActivityTotalViewCount == null) {
            totalNum = getRandomIncrementNum(systemProperties.getViewCountIncrementTotal());
            TJobActivityTotalViewCount totalViewCount = new TJobActivityTotalViewCount();
            totalViewCount.setActivityId(activityId);
            totalViewCount.setActivityType(type);
            totalViewCount.setCreateAt(new Date());
            totalViewCount.setTotalViewCount(totalNum);
            tJobActivityTotalViewCountDao.insert(totalViewCount);
        } else {
            totalNum = tJobActivityTotalViewCount.getTotalViewCount();
        }
        return totalNum > totalActivityViewCount;
    }

    private Integer getRandomIncrementNum(String viewCount) {
        if (StringUtils.isBlank(viewCount)) {
            return 0;
        }
        try {
            String[] split = viewCount.split("-");
            if (split.length == 1) {
                return Integer.valueOf(split[0]);
            } else if (split.length == 2) {
                return RandomUtils.nextInt(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
