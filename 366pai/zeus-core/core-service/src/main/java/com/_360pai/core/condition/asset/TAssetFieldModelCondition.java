
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年09月06日 19时24分48秒
 */
@Data
public class TAssetFieldModelCondition implements DaoCondition {

    /**
     * 主键自增
     */
    private Integer id;
    /**
     * 是否可扩展
     */
    private Integer extensible;
    /**
     * 模块标题
     */
    private String modelTitle;
    /**
     * 模板ID
     */
    private Integer templateId;
    /**
     * 分组ID
     */
    private Integer fieldGroupId;
    /**
     * 分组Key
     */
    private String modelKey;
    /**
     * 是否隐藏标题
     */
    private Integer showTitle;

}