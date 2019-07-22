package com._360pai.seimi.service;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.GPaiPm;
import com._360pai.seimi.model.RmfysszcPm;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xdrodger
 * @Title: GPaiPmService
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:10
 */
public interface RmfysszcPmService {
    RmfysszcPm saveItem(RmfysszcPm rmfysszcPm);

    RmfysszcPm findByItemId(String itemId);
}
