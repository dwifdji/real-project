package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：自动签署合同
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:14
 */
public class ExtSignContractReq implements Serializable {

    private String type; //签署类型 1 委托协议 2 委托补充协议 3 成交协议 4 预招商合同  其他待定

    private String contract_id;//合同id

    private String activity_id;//平台活动id

    private List<FddSignInfo>  sign_list;//签章的参数


    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public List<FddSignInfo> getSign_list() {
        return sign_list;
    }

    public void setSign_list(List<FddSignInfo> sign_list) {
        this.sign_list = sign_list;
    }
}
