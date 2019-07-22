package com.winback.arch.common;

import lombok.Data;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ListResp
 * @ProjectName winback
 * @Description:
 * @date 21/09/2018 10:56
 */
@Data
public class ListResp<T> extends RespModel {
    private List<T> list;
}
