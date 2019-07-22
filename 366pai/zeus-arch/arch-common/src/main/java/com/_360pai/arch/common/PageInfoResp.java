package com._360pai.arch.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ListResp
 * @ProjectName zeus
 * @Description:
 * @date 06/09/2018 19:07
 */
public class PageInfoResp<T> extends BaseResp {

    private long total;

    private boolean hasNextPage;

    private List<T> list;

    public PageInfoResp() {
    }

    public PageInfoResp(PageInfo pageInfo) {
        this.total = pageInfo.getTotal();
        this.hasNextPage = pageInfo.isHasNextPage();
        this.list = pageInfo.getList();
    }

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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
