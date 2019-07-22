package com._360pai.core.facade.applet.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/5/28 17:27
 */
@Data
public class CalculatorQueryConditionVo implements Serializable {

    Set<String>      projectManagerList = new HashSet<>();
//    List<JSONObject> areaList;

}
