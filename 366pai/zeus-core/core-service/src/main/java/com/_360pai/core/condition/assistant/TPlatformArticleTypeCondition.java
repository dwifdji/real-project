
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年09月27日 15时13分45秒
 */
@Data
public class TPlatformArticleTypeCondition implements DaoCondition {

    public static final Integer online = 1;
    public static final Integer down = 0;

    public static final Integer jrtt = 1;
    public static final Integer zcdmb = 2;

    /**
     * 自增
     */
    private Integer id;
    /**
     * 标题
     */
    private String articleTitle;
    /**
     * 图片
     */
    private String image;
    /**
     * 状态  1:上线  0:下线
     */
    private Integer status;
    /**
     * 类别  1:首页今日头条 2:资产大买办
     */
    private Integer type;
    /**
     * 0不删除 1删除
     */
    private Integer delFlag;
    /**
     * 0 在新闻中心展示 1不在新闻中心
     */
    private Integer showNews;
    /**
     * 排序字段
     */
    private Integer orderNum;


}