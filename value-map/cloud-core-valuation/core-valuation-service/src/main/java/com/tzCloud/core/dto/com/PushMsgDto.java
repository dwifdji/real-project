package com.tzCloud.core.dto.com;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PushMsgDto {


    /**
     *发送的模板 必传
     */
    private String type;


    /**
     *发送的参数
     */
    private JSONObject param;


    /**
     *发送目标  accountId   或者 email   不传的话就发送模板配置的
     */
    private List<String> targetList;


}
