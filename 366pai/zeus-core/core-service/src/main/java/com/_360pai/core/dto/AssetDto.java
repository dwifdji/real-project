package com._360pai.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
public class AssetDto implements Serializable {

    private String type;
    private String name;
    private String key;
    private String choiced;
    private List<String> val = new TreeList<>();
    private String title;
    private String titleSubset;
    private String grading;
    private String gradingSecond;
    private String errorMsg;
    private String titleName;
    private String titleSubsetName;
    private String fmNum;
    private List<String> valId = new TreeList<>();
    private List<Integer> valNum = new TreeList<>();

}
