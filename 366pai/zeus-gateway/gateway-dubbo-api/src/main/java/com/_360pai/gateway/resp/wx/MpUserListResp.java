package com._360pai.gateway.resp.wx;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: MpUserListResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/5/10 14:42
 */
@Data
public class MpUserListResp implements Serializable {
    private String code;

    private String desc;
    private List<String> list;
    private String nextOpenId;
}
