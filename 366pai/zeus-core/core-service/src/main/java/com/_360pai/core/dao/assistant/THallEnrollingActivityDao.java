
package com._360pai.core.dao.assistant;

import java.util.List;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.assistant.THallEnrollingActivityCondition;
import com._360pai.core.model.assistant.THallEnrollingActivity;
import com._360pai.core.vo.enrolling.web.HomeEnrollingActivityVO;

/**
 * <p>THallEnrollingActivity的基础操作Dao</p>
 * @ClassName: THallEnrollingActivityDao
 * @Description: THallEnrollingActivity基础操作的Dao
 * @author Generator
 * @date 2018年09月18日 16时47分54秒
 */
public interface THallEnrollingActivityDao extends BaseDao<THallEnrollingActivity, THallEnrollingActivityCondition>{

	List<HomeEnrollingActivityVO> getHallEnrollingActivities(Integer hallId);

}
