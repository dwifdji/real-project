package com._360pai.crawler.dao;

import com._360pai.crawler.model.Task;
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
public interface TaskDao extends JpaRepository<Task, Serializable> {
    Task findFirstByTypeAndStatusOrderByIdDesc(String type, String status);
}
