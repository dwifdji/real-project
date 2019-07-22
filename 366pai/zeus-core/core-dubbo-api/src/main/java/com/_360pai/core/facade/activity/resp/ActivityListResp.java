package com._360pai.core.facade.activity.resp;

import com._360pai.core.facade.activity.vo.AuctionActivityVo;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ActivityListResp
 * @ProjectName zeus
 * @Description:
 * @date 05/09/2018 14:55
 */
public class ActivityListResp {
    private long total;

    private boolean hasNextPage;

    private List<AuctionActivityVo> list;

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

    public List<AuctionActivityVo> getList() {
        return list;
    }

    public void setList(List<AuctionActivityVo> list) {
        this.list = list;
    }
}
