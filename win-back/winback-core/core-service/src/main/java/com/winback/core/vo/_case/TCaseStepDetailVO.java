package com.winback.core.vo._case;

import java.io.Serializable;
import java.util.List;

public class TCaseStepDetailVO implements Serializable {

    private String id;

    private String name;

    private String type;

    private String nameDesc;

    private Integer branchFlag;

    private String groupId;

    private String orderDesc;

    private String nextId;

    private String nextName;

    private Integer haveBranchFlag;

    public Integer getHaveBranchFlag() {
        return haveBranchFlag;
    }

    public void setHaveBranchFlag(Integer haveBranchFlag) {
        this.haveBranchFlag = haveBranchFlag;
    }

    private List<TCaseStepBranchVO> caseBranchList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameDesc() {
        return nameDesc;
    }

    public void setNameDesc(String nameDesc) {
        this.nameDesc = nameDesc;
    }

    public Integer getBranchFlag() {
        return branchFlag;
    }

    public void setBranchFlag(Integer branchFlag) {
        this.branchFlag = branchFlag;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getNextName() {
        return nextName;
    }

    public void setNextName(String nextName) {
        this.nextName = nextName;
    }

    public List<TCaseStepBranchVO> getCaseBranchList() {
        return caseBranchList;
    }

    public void setCaseBranchList(List<TCaseStepBranchVO> caseBranchList) {
        this.caseBranchList = caseBranchList;
    }
}
