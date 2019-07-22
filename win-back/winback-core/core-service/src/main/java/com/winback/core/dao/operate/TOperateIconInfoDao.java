
package com.winback.core.dao.operate;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.operate.TOperateIconInfoCondition;
import com.winback.core.dto.operate.OperateIconDto;
import com.winback.core.model.operate.TOperateIconInfo;
import com.winback.core.vo.operate.*;

import java.util.List;

/**
 * <p>TOperateIconInfo的基础操作Dao</p>
 * @ClassName: TOperateIconInfoDao
 * @Description: TOperateIconInfo基础操作的Dao
 * @author Generator
 * @date 2019年01月22日 10时27分34秒
 */
public interface TOperateIconInfoDao extends BaseDao<TOperateIconInfo,TOperateIconInfoCondition> {

    List<OperateIconListVO> getReleaseAreaList(OperateIconDto params);

    List<HomeIconCategoryVO> getQualityCaseIcons(Integer limitSize);

    List<HomeContractCategoryVO> getContractModelIcons(Integer limitSize);

    List<QuickReleaseVO> getCaseTypeList(Integer limitSize);
}
