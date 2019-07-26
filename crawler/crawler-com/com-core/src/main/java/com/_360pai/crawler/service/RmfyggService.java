package com._360pai.crawler.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xdrodger
 * @Title: RmfyggService
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/8 19:36
 */
public interface RmfyggService {

    JSONObject getList(int page, String noticeTypeVal);

    JSONObject getList(int page, String court, String noticeTypeVal);

    JSONObject getDetail(String noticeId);

    void startRequest(String noticeTypeVal);

    void startRequest(String court, String noticeTypeVal);

    void startRequestByCourt(String court);

    void startRequestByCourt();
}
