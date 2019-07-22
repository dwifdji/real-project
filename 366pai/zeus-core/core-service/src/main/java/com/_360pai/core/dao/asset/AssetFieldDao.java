
package com._360pai.core.dao.asset;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.asset.AssetFieldCondition;
import com._360pai.core.dto.AssetFieldDto;
import com._360pai.core.model.asset.AssetField;
import com._360pai.core.vo.asset.AssetFieldVo;

import java.util.List;

/**
 * <p>AssetField的基础操作Dao</p>
 * @ClassName: AssetFieldDao
 * @Description: AssetField基础操作的Dao
 * @author Generator
 * @date 2018年08月08日 13时53分17秒
 */
public interface AssetFieldDao extends BaseDao<AssetField, AssetFieldCondition>{

    List<AssetFieldVo> selectAssetFieldAndGroup (AssetFieldDto params);

}
