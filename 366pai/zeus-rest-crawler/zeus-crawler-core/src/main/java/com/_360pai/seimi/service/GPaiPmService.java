package com._360pai.seimi.service;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.GPaiAreaModel;
import com._360pai.seimi.model.GPaiPm;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xdrodger
 * @Title: GPaiPmService
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:10
 */
public interface GPaiPmService {
    GPaiPm saveItem(GPaiPm gPaiPm);

    GPaiPm findByItemId(Integer itemId);

    boolean updateContent(Response response);

    Page<GPaiPm> findNeedToUpdate(int page, int size);

    List<GPaiAreaModel>  getAllCities();

    void setContent(String item_id, GPaiPm gPaiPm, CloseableHttpClient httpClient);

    List<GPaiAreaModel> selectAllAreas();
}
