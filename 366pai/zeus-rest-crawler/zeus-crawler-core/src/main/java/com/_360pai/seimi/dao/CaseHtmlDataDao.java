package com._360pai.seimi.dao;

import com._360pai.seimi.model.CaseHtmlData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaolei
 * @create 2019-01-29 13:36
 */
@Repository
public interface CaseHtmlDataDao extends JpaRepository<CaseHtmlData, Serializable> {
    @Query(value = "select t1.* from crawler.case_html_data t1 left join crawler.case_relate_info t2 on t1.doc_id = t2.doc_id where t2.case_type = '民事案件' limit 0, ?", nativeQuery = true)
    List<CaseHtmlData> findCaseHtmlDataByCaseType(int limit);

    @Query(value = "select t1.* from crawler.case_html_data t1 left join crawler.case_relate_info t2 on t1.doc_id = t2.doc_id " +
//            "where t2.trial_round = ?1 limit 0, ?2", nativeQuery = true)
            "where if (?1 = 'NULL', t2.trial_round is null, t2.trial_round = ?1) limit 0, ?2", nativeQuery = true)
    List<CaseHtmlData> findCaseHtmlDataByTrailRound(String trailRound, int limit);
}
