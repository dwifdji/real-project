
package com.tzCloud.core.dao.caseMatching.mapper;

import com.tzCloud.core.facade.legalEngine.vo.Brief;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.caseMatching.TTreeContentCondition;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TTreeContent数据层的基础操作</p>
 * @ClassName: TTreeContentMapper
 * @Description: TTreeContent数据层的基础操作
 * @author Generator
 * @date 2019年03月07日 16时22分18秒
 */
@Mapper
public interface TTreeContentMapper extends BaseMapper<TTreeContent, TTreeContentCondition>{
    List<TTreeContent> findAllBrief();


    Brief findBriefLevelById(Integer id);

    List<TTreeContent> likeByKeyWord(String name);
}
