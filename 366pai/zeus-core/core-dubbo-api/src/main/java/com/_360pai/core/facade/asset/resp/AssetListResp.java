package com._360pai.core.facade.asset.resp;

import com._360pai.core.facade.asset.vo.AssetVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author : whisky_vip
 * @date : 2018/8/17 12:35
 */
public class AssetListResp implements Serializable {

    private long total;

    private boolean hasNextPage;

    private List<AssetVo> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<AssetVo> getList() {
        return list;
    }

    public void setList(List<AssetVo> list) {
        this.list = list;
    }
}
