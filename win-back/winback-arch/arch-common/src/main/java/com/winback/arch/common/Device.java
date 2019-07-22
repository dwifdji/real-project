package com.winback.arch.common;

import com.winback.arch.common.enums.DeviceType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Device
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 16:09
 */
@Data
public class Device implements Serializable {
    /**
     * 设备类型 Android 0/iOS 1
     */
    private String deviceType;
    /**
     * 设备token，唯一，但同一台设备会变
     */
    private String deviceToken;
    /**
     * 推送token，唯一，但同一台设备会变
     */
    private String notificationToken;
    /**
     * 设备系统版本号
     */
    private String deviceVersion;
    /**
     * 应用版本号
     */
    private String clientVersion;
    /**
     * 设备型号（手机）
     */
    private String phoneModel;
    /**
     * 网络环境
     */
    private String netType;
}
