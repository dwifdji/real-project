package com._360pai.core.vo.enrolling;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 15:00
 */
public class EnrollingProgressVo implements java.io.Serializable{


    private String title;//进展标题

    private String context;//进展内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
