package com._360pai.crawler.service;

import com._360pai.crawler.commons.TaskConstants;
import com._360pai.crawler.model.Task;
import com._360pai.crawler.model.TaskItem;

/**
 * @author xdrodger
 * @Title: TaskService
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 14:23
 */
public interface TaskService {
    Integer getLatestDoingTask(TaskConstants.Type type);

    Integer updateTask(Integer taskId, TaskConstants.Status status);

    TaskItem getTaskItem(Integer taskId, String keyword);

    Integer updateTaskItem(Integer taskItemId, TaskConstants.Status status);
}
