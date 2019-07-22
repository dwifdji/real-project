
package com.tzCloud.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.account.TAccountMembershipCardCondition;
import com.tzCloud.core.model.account.TAccountMembershipCard;
import com.tzCloud.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TAccountMembershipCard数据层的基础操作</p>
 * @ClassName: TAccountMembershipCardMapper
 * @Description: TAccountMembershipCard数据层的基础操作
 * @author Generator
 * @date 2019年04月19日 13时33分42秒
 */
@Mapper
public interface TAccountMembershipCardMapper extends BaseMapper<TAccountMembershipCard, TAccountMembershipCardCondition>{
    List<TAccountMembershipCard> findAvailableCard(Integer id);
}
