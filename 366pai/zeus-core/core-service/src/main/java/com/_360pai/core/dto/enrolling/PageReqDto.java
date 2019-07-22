package com._360pai.core.dto.enrolling;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 14:02
 */
public class PageReqDto {

    /**
     * 每页数量.
     */
    private int perPage;

    /**
     * 第几页
     */
    private int page;

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
