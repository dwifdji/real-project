package com.tzCloud.gateway.resp.risk;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/17 10:44
 */
@Data
public class RiskInvestmentResp implements Serializable {

    private String code;

    private String desc;

    private String jsonIno;



    public static RiskInvestmentResp failure(String code , String desc){

        RiskInvestmentResp resp = new RiskInvestmentResp();

        resp.setCode(code);
        resp.setDesc(desc);
        return resp;
    }
}
