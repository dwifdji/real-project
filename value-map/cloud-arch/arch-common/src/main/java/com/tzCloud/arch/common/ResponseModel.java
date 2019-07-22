package com.tzCloud.arch.common;


import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.exception.BaseException;
import com.tzCloud.arch.common.utils.JsonUtil;
import com.alibaba.fastjson.serializer.PropertyFilter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author whisky_vip
 */
public class ResponseModel implements Serializable {

    private static final long   serialVersionUID = -2177650628109118809L;
    /**
     * 返回结果码
     */
    private              String code;
    /**
     * 返回结果描述
     */
    private              String desc;
    /**
     * 返回数据
     */
    private              Object content;
    /**
     * 调用时间戳
     */
    private              long   timestamp;

    public ResponseModel() {
        super();
    }

    public ResponseModel(Object content) {
        super();
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 增加一个differ参数区分 Response(String code, String desc)构造方法
     */
    public ResponseModel(String key, Object value, int differ) {
        super();
        Map<String, Object> content = new HashMap<String, Object>();
        content.put(key, value);
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseModel(String code, String desc) {
        super();
        this.code = code;
        this.desc = desc;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseModel(String code, String desc, Object content) {
        super();
        this.code = code;
        this.desc = desc;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getCode() {
        return code;
    }

    public ResponseModel setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ResponseModel setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Object getContent() {
        return content;
    }

    //属性过滤器
    public void setContent(Object content, PropertyFilter propertyFilter) {
        this.content = JsonUtil.toJSONObject(content, propertyFilter);
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ResponseModel setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ResponseModel warpSuccess() {
        return this.setCode(ApiCallResult.SUCCESS.getCode()).setDesc(
                ApiCallResult.SUCCESS.getDesc());
    }


    public static ResponseModel succ() {
        return succ(null);
    }

    public static ResponseModel succ(Object data) {
        ResponseModel ret = new ResponseModel();
        ret.setCode(ApiCallResult.SUCCESS.getCode());
        ret.setDesc(ApiCallResult.SUCCESS.getDesc());
        ret.setTimestamp(System.currentTimeMillis());
        ret.setContent(data);
        return ret;
    }

    public static ResponseModel succ(Object data, PropertyFilter propertyFilter) {
        ResponseModel ret = new ResponseModel();
        ret.setCode(ApiCallResult.SUCCESS.getCode());
        ret.setDesc(ApiCallResult.SUCCESS.getDesc());
        ret.setTimestamp(System.currentTimeMillis());
        ret.setContent(data, propertyFilter);
        return ret;
    }

    public static ResponseModel fail(ApiCallResult m) {
        ResponseModel ret = new ResponseModel();
        ret.setCode(m.getCode());
        ret.setDesc(m.getDesc());
        ret.setTimestamp(System.currentTimeMillis());
        return ret;
    }

    public static ResponseModel fail(BaseException m) {
        ResponseModel ret = new ResponseModel();
        ret.setCode(m.getExceptionCode());
        ret.setDesc(m.getMessage());
        ret.setTimestamp(System.currentTimeMillis());
        return ret;
    }

    public static ResponseModel fail(ApiCallResult m, Object data) {
        ResponseModel ret = new ResponseModel();
        ret.setCode(m.getCode());
        ret.setDesc(m.getDesc());
        ret.setTimestamp(System.currentTimeMillis());
        ret.setContent(data);
        return ret;
    }


    public static ResponseModel fail() {
        ResponseModel ret = new ResponseModel();
        ret.setCode(ApiCallResult.FAILURE.getCode());
        ret.setDesc(ApiCallResult.FAILURE.getDesc());
        ret.setTimestamp(System.currentTimeMillis());
        return ret;
    }

    public static ResponseModel fail(String message) {
        ResponseModel ret = new ResponseModel();
        ret.setCode(ApiCallResult.FAILURE.getCode());
        ret.setDesc(message);
        ret.setTimestamp(System.currentTimeMillis());
        return ret;
    }

    public static ResponseModel fail(String code, String message) {
        ResponseModel ret = new ResponseModel();
        ret.setCode(code);
        ret.setDesc(message);
        ret.setTimestamp(System.currentTimeMillis());
        return ret;
    }

    public static ResponseModel wrapCount(int count) {
        if (count > 0) {
            return succ();
        } else if (count < 0) {
            return fail(ApiCallResult.REPEAT_OPERATION);
        } else {
            return fail();
        }
    }
}
