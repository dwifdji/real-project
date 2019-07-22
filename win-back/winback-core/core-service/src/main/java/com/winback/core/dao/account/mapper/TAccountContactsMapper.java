
package com.winback.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TAccountContactsCondition;
import com.winback.core.model.account.TAccountContacts;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TAccountContacts数据层的基础操作</p>
 * @ClassName: TAccountContactsMapper
 * @Description: TAccountContacts数据层的基础操作
 * @author Generator
 * @date 2019年04月03日 14时37分27秒
 */
@Mapper
public interface TAccountContactsMapper extends BaseMapper<TAccountContacts, TAccountContactsCondition>{
    int batchSave(@Param("list") List<TAccountContacts> list);
}
