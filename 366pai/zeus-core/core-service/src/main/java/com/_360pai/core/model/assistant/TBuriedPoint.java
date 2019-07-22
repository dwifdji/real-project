
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年10月08日 11时30分23秒
 */
public class TBuriedPoint implements java.io.Serializable {

    /**
     *
     */
    private Integer id;
    /**
     * 埋点key
     */
    private String  pointKey;
    /**
     * 埋点描述
     */
    private String  pointDesc;
    /**
     * 埋点类型
     */
    private String  pointType;
    /**
     * 业务id
     */
    private String  buzId;
    /**
     * 业务参数
     */
    private String  buzParams;
    /**
     * 设备标识
     */
    private String  deviceMark;
    /**
     * 设备类型
     */
    private String  deviceType;
    /**
     * 用户id
     */
    private String  userId;
    /**
     * 用户类型
     */
    private String  userType;
    private String  createTime;
    private String  updateTime;

    private String city;
    private String province;
    private String ip;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 埋点key
     */
    public String getPointKey() {
        return pointKey;
    }

    /**
     * 埋点key
     */
    public void setPointKey(String pointKey) {
        this.pointKey = pointKey;
    }

    /**
     * 埋点描述
     */
    public String getPointDesc() {
        return pointDesc;
    }

    /**
     * 埋点描述
     */
    public void setPointDesc(String pointDesc) {
        this.pointDesc = pointDesc;
    }

    /**
     * 埋点类型
     */
    public String getPointType() {
        return pointType;
    }

    /**
     * 埋点类型
     */
    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    /**
     * 业务id
     */
    public String getBuzId() {
        return buzId;
    }

    /**
     * 业务id
     */
    public void setBuzId(String buzId) {
        this.buzId = buzId;
    }

    /**
     * 业务参数
     */
    public String getBuzParams() {
        return buzParams;
    }

    /**
     * 业务参数
     */
    public void setBuzParams(String buzParams) {
        this.buzParams = buzParams;
    }

    /**
     * 设备标识
     */
    public String getDeviceMark() {
        return deviceMark;
    }

    /**
     * 设备标识
     */
    public void setDeviceMark(String deviceMark) {
        this.deviceMark = deviceMark;
    }

    /**
     * 设备类型
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设备类型
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 用户类型
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 用户类型
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     *
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     *
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     *
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     *
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
