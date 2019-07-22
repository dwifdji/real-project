
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAccountExtBindCondition;
import com._360pai.core.facade.applet.vo.InviteRecord;
import com._360pai.core.model.account.TAccountExtBind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAccountExtBind数据层的基础操作</p>
 * @ClassName: TAccountExtBindMapper
 * @Description: TAccountExtBind数据层的基础操作
 * @author Generator
 * @date 2018年11月23日 09时16分44秒
 */
@Mapper
public interface TAccountExtBindMapper extends BaseMapper<TAccountExtBind, TAccountExtBindCondition>{

    List<InviteRecord> getInviteRecordList(Map<String, Object> params);

    int inviteCount(@Param("inviteType") String inviteType, @Param("inviteCode") Integer inviteCode);
}
