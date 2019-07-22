
package com.tzCloud.core.dao.legalEngine;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.legalEngine.TCourtCondition;
import com.tzCloud.core.model.legalEngine.TCourt;
import org.springframework.amqp.rabbit.annotation.Queue;

import java.util.List;
import java.util.Map;

/**
 * <p>TCourt的基础操作Dao</p>
 * @ClassName: TCourtDao
 * @Description: TCourt基础操作的Dao
 * @author Generator
 * @date 2019年04月19日 15时41分11秒
 */
public interface TCourtDao extends BaseDao<TCourt,TCourtCondition>{


    TCourt findBy(String name);

    List<TCourt> QueryList();

    List<Map<String, String>> findDiffCourt();
}
