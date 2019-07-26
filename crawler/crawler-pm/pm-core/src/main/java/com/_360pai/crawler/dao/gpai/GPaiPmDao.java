package com._360pai.crawler.dao.gpai;

import com._360pai.crawler.model.gpai.GPaiPm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: GPaiDao
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:09
 */
@Component
public interface GPaiPmDao extends JpaRepository<GPaiPm, Serializable> {

    @Query(value = "select * from t_base_gpai_pm b where b.item_id=?1", nativeQuery = true)
    GPaiPm findByItemId(Integer itemId);

    Page<GPaiPm> findByTitleIsNull(Pageable pageable);

    Page<GPaiPm> findByTitle(String title, Pageable pageable);

    Page<GPaiPm> findByBeginAtIsNull(Pageable pageable);

    Page<GPaiPm> findByEndAtIsNull(Pageable pageable);
}
