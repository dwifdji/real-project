package com._360pai.core.dto;

import lombok.Data;
import org.apache.commons.collections4.list.TreeList;

import java.io.Serializable;
import java.util.List;

/**
 * @author zxiao
 * @Title: AssetDto
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/17 15:44
 */
@Data
public class AssetRes implements Serializable {

    private String type;
    private String name;
    private String key;
    private List<Object> val = new TreeList<>();
    private String title;
    private String titleSubset;
    private String grading;
    private String gradingSecond;
    private String errorMsg;
    private String titleName;
    private String titleSubsetName;
    private List<Object> valId = new TreeList<>();
    private List<Object> valNum = new TreeList<>();

}
