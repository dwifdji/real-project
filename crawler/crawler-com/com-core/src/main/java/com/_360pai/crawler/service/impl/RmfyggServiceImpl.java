package com._360pai.crawler.service.impl;

import com._360pai.crawler.common.util.OkHttpUtil;
import com._360pai.crawler.commons.CourtConstants;
import com._360pai.crawler.commons.RmfyggConstants;
import com._360pai.crawler.commons.TaskConstants;
import com._360pai.crawler.dao.CourtDao;
import com._360pai.crawler.dao.RmfyggDao;
import com._360pai.crawler.model.Court;
import com._360pai.crawler.model.Rmfygg;
import com._360pai.crawler.model.TaskItem;
import com._360pai.crawler.service.RmfyggService;
import com._360pai.crawler.service.TaskService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xdrodger
 * @Title: RmfyggServiceImpl
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/8 19:37
 */
@Slf4j
@Service
public class RmfyggServiceImpl implements RmfyggService {

    private static int perPage = 15;

    @Autowired
    private RmfyggDao rmfyggDao;
    @Autowired
    private CourtDao courtDao;
    @Autowired
    private TaskService taskService;


    private JSONArray getAoData(int page) {
        JSONArray aoData = new JSONArray();
        JSONObject sEcho = new JSONObject();
        sEcho.put("name", "sEcho");
        sEcho.put("value", 1);
        aoData.add(sEcho);

        JSONObject iColumns = new JSONObject();
        iColumns.put("name", "iColumns");
        iColumns.put("value", 6);
        aoData.add(iColumns);

        JSONObject sColumns = new JSONObject();
        sColumns.put("name", "sColumns");
        sColumns.put("value", ",,,,,");
        aoData.add(sColumns);

        JSONObject iDisplayStart = new JSONObject();
        iDisplayStart.put("name", "iDisplayStart");
        iDisplayStart.put("value", (page-1) * perPage);
        aoData.add(iDisplayStart);

        JSONObject iDisplayLength = new JSONObject();
        iDisplayLength.put("name", "iDisplayLength");
        iDisplayLength.put("value", perPage);
        aoData.add(iDisplayLength);
        return aoData;
    }

    @Override
    public JSONObject getList(int page, String noticeTypeVal) {
        return getList(page, null, noticeTypeVal);
    }

    @Override
    public JSONObject getList(int page, String court, String noticeTypeVal) {
        String url = RmfyggConstants.LIST_URL + "?p_p_id=noticelist_WAR_rmfynoticeListportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=initNoticeList&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1";
        Map<String, String> params = new HashMap<>();
        params.put("_noticelist_WAR_rmfynoticeListportlet_content", "");
        params.put("_noticelist_WAR_rmfynoticeListportlet_searchContent", "");
        params.put("_noticelist_WAR_rmfynoticeListportlet_courtParam", "");
        params.put("_noticelist_WAR_rmfynoticeListportlet_IEVersion", "ie");
        params.put("_noticelist_WAR_rmfynoticeListportlet_flag", "click");
        //params.put("_noticelist_WAR_rmfynoticeListportlet_noticeType", "");
        if (StringUtils.isNotBlank(court)) {
            params.put("_noticelist_WAR_rmfynoticeListportlet_courtParam", court);
        }
        if (StringUtils.isBlank(noticeTypeVal)) {
            params.put("_noticelist_WAR_rmfynoticeListportlet_noticeTypeVal", "全部");
        } else {
            params.put("_noticelist_WAR_rmfynoticeListportlet_noticeTypeVal", noticeTypeVal);
        }
        params.put("_noticelist_WAR_rmfynoticeListportlet_aoData", getAoData(page).toJSONString());
        String result = OkHttpUtil.postForm(url, params);
        log.info("page={}, noticeTypeVal={}, response={}", page, noticeTypeVal, result);
        if (StringUtils.isNotBlank(result)) {
            return JSON.parseObject(result);
        }
        return null;
    }

    @Override
    public JSONObject getDetail(String noticeId) {
        String url = RmfyggConstants.DETAIL_URL + "?p_p_id=noticedetail_WAR_rmfynoticeDetailportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=noticeDetail&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1";
        Map<String, String> params = new HashMap<>();
        params.put("_noticedetail_WAR_rmfynoticeDetailportlet_uuid", noticeId);
        String result = OkHttpUtil.postForm(url, params);
        log.info("uuid={}, response={}", noticeId, result);
        if (StringUtils.isNotBlank(result)) {
            return JSON.parseObject(result);
        }
        return null;
    }

    @Override
    public void startRequest(String noticeTypeVal) {
        startRequest(null, noticeTypeVal);
    }

    @Override
    public void startRequest(String court, String noticeTypeVal) {
        int page = 1;
        while (true) {
            log.info("rmfygg start list request page={}, noticeTypeVal={}", page, noticeTypeVal);
            JSONObject result = getList(page, court, noticeTypeVal);
            if (!result.containsKey("iTotalRecords") || result.getIntValue("iTotalRecords") < page * perPage) {
                break;
            }
            JSONArray data = result.getJSONArray("data");
            if (data == null || data.size() == 0) {
                break;
            }
            processItemList(data);
            page ++;
        }
    }

    @Override
    public void startRequestByCourt(String court) {
        Set<String> noticeTypeVals = new LinkedHashSet<>();
        noticeTypeVals.add(RmfyggConstants.DEFAULT_NOTICE_TYPE_VAL);
        noticeTypeVals.addAll(RmfyggConstants.noticeTypeVal);
        for (String noticeTypeVal : noticeTypeVals) {
            int page = 1;
            while (true) {
                log.info("rmfygg start list request page={}, court={}, noticeTypeVal={}", page, court, noticeTypeVal);
                JSONObject result = getList(page, court, noticeTypeVal);
                if (!result.containsKey("iTotalRecords") || result.getIntValue("iTotalRecords") < page * perPage) {
                    if (RmfyggConstants.DEFAULT_NOTICE_TYPE_VAL.equals(noticeTypeVal)) {
                        return;
                    }
                    break;
                }
                JSONArray data = result.getJSONArray("data");
                if (data == null || data.size() == 0) {
                    break;
                }
                processItemList(data);
                page ++;
            }
        }
    }

    @Override
    public void startRequestByCourt() {
        Integer taskId = taskService.getLatestDoingTask(TaskConstants.Type.Type_1);
        int page = 0;
        int perPage = 10;
        while (true) {
            log.info("rmfygg by court start request taskId={}, page={}, ", taskId, page);
            Page<Court> pageInfo = courtDao.findByType(CourtConstants.Type.Type_1.getKey(), new PageRequest(page, perPage));
            if (!pageInfo.hasContent()) {
                break;
            }
            for (Court court : pageInfo.getContent()) {
                TaskItem taskItem = taskService.getTaskItem(taskId, court.getName());
                log.info("rmfygg by court start request taskId={}, taskItem={}, page={}, court={}", taskId, taskItem.getId(), page, court.getName());
                try {
                    if (TaskConstants.Status.DOING.getKey().equals(taskItem.getStatus())) {
                        startRequestByCourt(court.getName());
                        taskService.updateTaskItem(taskItem.getId(), TaskConstants.Status.FINISH);
                    } else {
                        log.info("rmfygg by court start request taskId={}, taskItem={}, page={}, court={}, status={}", taskId, taskItem.getId(), page, court.getName(), taskItem.getStatus());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    taskService.updateTaskItem(taskItem.getId(), TaskConstants.Status.FAILURE);
                }
            }
            page ++;
        }
        taskService.updateTask(taskId, TaskConstants.Status.FINISH);
    }

    private void processItemList(JSONArray itemList) {
        int count= 0;
        for (Object o : itemList) {
            count ++;
            try {
                JSONObject item = (JSONObject) o;
                String uuid = item.getString("uuid");
                if (StringUtils.isBlank(uuid)) {
                    continue;
                }
                Rmfygg rmfygg = rmfyggDao.findByNoticeId(uuid);
                if (rmfygg != null) {
                    continue;
                }
                count ++;
                log.info("rmfygg start detail request uui={}", uuid);
                JSONObject result = getDetail(uuid);
                processItemDetail(result);
                //Thread.sleep(RandomUtils.nextInt(500,1000));
                //Thread.sleep(RandomUtils.nextInt(300, 600));
                //Thread.sleep(RandomUtils.nextInt(300, 600));
                //Thread.sleep(RandomUtils.nextInt(150, 600));
                Thread.sleep(RandomUtils.nextInt(100, 300));
                //Thread.sleep(RandomUtils.nextInt(70, 300));
                //Thread.sleep(RandomUtils.nextInt(70, 150));
                //Thread.sleep(RandomUtils.nextInt(50, 120));
                //Thread.sleep(RandomUtils.nextInt(30, 120));
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(30000);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void processItemDetail(JSONObject result) {
        if (result == null || !result.containsKey("uuid")) {
            return;
        }
        Rmfygg rmfygg = new Rmfygg();
        rmfygg.setNoticeId(result.getString("uuid"));
        rmfygg.setNoticeType(result.getString("noticeType"));
        rmfygg.setNoticeTitle(result.getString("noniceTitle"));
        rmfygg.setNoticeContent(result.getString("noticeContent"));
        rmfygg.setNoticeCode(result.getString("noticeCode"));
        rmfygg.setNoticeCodeEnc(result.getString("noticeCodeEnc"));
        rmfygg.setDefendant(result.getString("tosendPeople"));
        rmfygg.setCourt(result.getString("court"));
        rmfygg.setPublishDate(result.getString("publishDate"));
        rmfygg.setPublishPage(result.getString("publishPage"));
        rmfygg.setUploadDate(result.getString("uploadDate"));
        rmfygg.setProvince(result.getString("province"));
        rmfygg.setDeleteFlag(false);
        Date now =new Date();
        rmfygg.setCreateTime(now);
        rmfygg.setUpdateTime(now);
        rmfyggDao.save(rmfygg);
    }
}
