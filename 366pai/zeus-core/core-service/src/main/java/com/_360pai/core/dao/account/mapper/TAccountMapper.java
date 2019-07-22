
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAccountCondition;
import com._360pai.core.facade.account.vo.ApplyRecordVo;
import com._360pai.core.model.account.TAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAccount数据层的基础操作</p>
 * @ClassName: TAccountMapper
 * @Description: TAccount数据层的基础操作
 * @author Generator
 * @date 2018年08月15日 19时32分10秒
 */
@Mapper
public interface TAccountMapper extends BaseMapper<TAccount, TAccountCondition>{

    List<TAccount> getAccountList(Map<String, Object> params);

    List<TAccount> getCompanyAccountList(Map<String, Object> params);

    int unBindAgency(@Param("id") Integer id);

    List<ApplyRecordVo> getApplyRecordList(Map<String, Object> params);

    int updateCurrentPartyId(@Param("id") Integer id, @Param("currentPartyId") Integer currentPartyId);

    List<TAccount> getAppletAccountListNeedRepair(Map<String, Object> params);
}
