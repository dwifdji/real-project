package com.winback.arch.common.constant;

/**
 * @author xdrodger
 * @Title: RedisKeyConstant
 * @ProjectName winback
 * @Description:
 * @date 22/09/2018 14:23
 */
public class RedisKeyConstant {

    /**
     * 表缓存相关
     */
    public static final String TABLE = "_table_";

    /**
     * 角色权限相关
     */
    public static final String RBAC = "_rbac_";
    public static final String RBAC_ALL_MODULE = "_rbac_all_module";
    public static final String RBAC_ALL_NORMAL_PERMISSION_CODE = "_rbac_all_normal_permission_code";
    public static final String RBAC_ALL_SPECIAL_PERMISSION_CODE = "_rbac_all_special_permission_code";
    public static final String RBAC_ROLE_MODULE = "_rbac_role_module";
    public static final String RBAC_ROLE_PERMISSION_CODE = "_rbac_role_permission_code";
    public static final String RBAC_STAFF_ROLE = "_rbac_staff_role";
    public static final String RBAC_STAFF_SPECIAL_PERMISSION = "_rbac_staff_special_permission_code";

    /**
     * 设备信息key
     */
    public static final String APP_DEVICE = "_app_device";

    /**
     * 所有省份城市的缓存信息
     */
    public static final String ALL_CITY_KEY = "_all_city_token_key_";

    /**
     * 合同上架缓存
     */
    public static final String CONTRACT_NEW_ARRIVAL = "contract_new_arrival_";

    /**
     * 小程序合同分享图片
     */
    public static final String APPLET_CONTRACT_SHARE_IMAGE = "applet_contract_share_image_";
}
