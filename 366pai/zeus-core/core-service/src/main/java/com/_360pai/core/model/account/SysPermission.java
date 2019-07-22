
package com._360pai.core.model.account;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月07日 20时50分28秒
 */
@Getter
@Setter
public class SysPermission implements java.io.Serializable{

	/**
	 * 自定id,主要供前端展示权限列表分类排序使用.
	 */
	private Integer id;
	/**
	 * 模块code
	 */
	private String moduleCode;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 模块排序
	 */
	private Integer moduleOrder;
	/**
	 * 归属菜单,前端判断并展示菜单使用,
	 */
	private String menuCode;
	/**
	 * 菜单的中文释义
	 */
	private String menuName;
	/**
	 * 菜单排序
	 */
	private Integer menuOrder;
	/**
	 * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
	 */
	private String permissionCode;
	/**
	 * 本权限的中文释义
	 */
	private String permissionName;
	/**
	 * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
	 */
	private Boolean requiredPermission;
	/**
	 * 按钮类型：0普通，1特殊
	 */
	private String permissionType;
	/**
	 * 是否删除
	 */
	private java.lang.Boolean isDelete;
}
