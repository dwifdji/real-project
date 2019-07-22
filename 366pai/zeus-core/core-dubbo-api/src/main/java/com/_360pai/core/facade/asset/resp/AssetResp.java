package com._360pai.core.facade.asset.resp;

import com._360pai.core.facade.asset.vo.AssetVo;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * @author : whisky_vip
 * @date : 2018/8/16 16:14
 */
public class AssetResp implements Serializable {

    AssetVo asset;

    public AssetVo getAsset() {
        return asset;
    }

    public void setAsset(AssetVo asset) {
        this.asset = asset;
    }
}
