package com.tzCloud.core.vo.house;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class HouseTransactionListVO implements Serializable {

    private String id;

    private String title;

    private String currentPrice;

    private String unitPrice;

    private String listingPrice;

    private String transactionCycle;

    private String renovationCondition;

    private String houseOrientation;

    private String elevatorFlag;

    private String lat;

    private String lng;

    private String endTime;

    private String useType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(String listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getTransactionCycle() {
        return transactionCycle;
    }

    public void setTransactionCycle(String transactionCycle) {
        this.transactionCycle = transactionCycle;
    }

    public String getRenovationCondition() {
        return renovationCondition;
    }

    public void setRenovationCondition(String renovationCondition) {
        this.renovationCondition = renovationCondition;
    }

    public String getHouseOrientation() {
        return houseOrientation;
    }

    public void setHouseOrientation(String houseOrientation) {
        this.houseOrientation = houseOrientation;
    }

    public String getElevatorFlag() {
        return elevatorFlag;
    }

    public void setElevatorFlag(String elevatorFlag) {
        this.elevatorFlag = elevatorFlag;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null:endTime.replace(".","-");
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }
}
