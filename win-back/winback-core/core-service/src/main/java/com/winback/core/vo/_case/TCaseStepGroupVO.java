package com.winback.core.vo._case;


import java.io.Serializable;
import java.util.List;

public class TCaseStepGroupVO implements Serializable {

    private String groupId;

    private String groupName;

    private String groupType;

    private String groupOrderDesc;

    private List<TCaseStepVO> caseStepVOS;

    public List<TCaseStepVO> getCaseStepVOS() {
        return caseStepVOS;
    }

    public void setCaseStepVOS(List<TCaseStepVO> caseStepVOS) {
        this.caseStepVOS = caseStepVOS;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupOrderDesc() {
        return groupOrderDesc;
    }

    public void setGroupOrderDesc(String groupOrderDesc) {
        this.groupOrderDesc = groupOrderDesc;
    }
}
