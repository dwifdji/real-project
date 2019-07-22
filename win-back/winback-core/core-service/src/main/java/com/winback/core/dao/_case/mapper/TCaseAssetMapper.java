
package com.winback.core.dao._case.mapper;

import com.winback.core.facade._case.req.CaseAssetReq;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseAssetCondition;
import com.winback.core.model._case.TCaseAsset;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TCaseAsset数据层的基础操作</p>
 * @ClassName: TCaseAssetMapper
 * @Description: TCaseAsset数据层的基础操作
 * @author Generator
 * @date 2019年02月28日 16时03分22秒
 */
@Mapper
public interface TCaseAssetMapper extends BaseMapper<TCaseAsset, TCaseAssetCondition>{
    List<TCaseAsset> searchCaseAsset(CaseAssetReq req);
}
