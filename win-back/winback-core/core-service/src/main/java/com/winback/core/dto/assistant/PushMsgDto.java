package com.winback.core.dto.assistant;

import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.enums.MessageTemplateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PushMsgDto {


    /**
     *发送的模板 必传
     */
    private MessageTemplateEnum.TYPE type;


    /**
     *发送的参数
     */
    private JSONObject param;


    /**
     *发送目标  accountId   或者 email   不传的话就发送模板配置的
     */
    private List<String> targetList;


}
