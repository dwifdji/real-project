package com.winback.core.facade.assistant.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: ShareInfo
 * @ProjectName winback
 * @Description:
 * @date 2019/3/6 17:16
 */
@Data
public class ShareInfo implements Serializable {
    private String title = "";
    private String content = "";
    private String imgUrl = "";
    private String url = "";
}
