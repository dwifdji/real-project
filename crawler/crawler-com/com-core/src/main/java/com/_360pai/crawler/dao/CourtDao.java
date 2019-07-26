package com._360pai.crawler.dao;

import com._360pai.crawler.model.Court;
import com._360pai.crawler.model.Rmfygg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: RmfyggDao
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/8 19:35
 */
@Component
public interface CourtDao extends JpaRepository<Court, Serializable> {

    Page<Court> findByType(String type, Pageable pageable);
}
