
package com.winback.core.dao.account.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.account.TAccountCondition;
import com.winback.core.facade.account.vo.Party;
import com.winback.core.model.account.TAccount;
import com.winback.core.vo.finance.WorkBenchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAccount数据层的基础操作</p>
 * @ClassName: TAccountMapper
 * @Description: TAccount数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分01秒
 */
@Mapper
public interface TAccountMapper extends BaseMapper<TAccount, TAccountCondition>{

    List<TAccount> getList(Map<String, Object> params);

    List<Party> getPartyList(Map<String, Object> params);

    List<WorkBenchVO> getTodayRole(@Param("searchDay")String searchDay);
}
