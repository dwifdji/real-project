package com._360pai.crawler.service.impl;

import com._360pai.crawler.commons.TaskConstants;
import com._360pai.crawler.dao.TaskDao;
import com._360pai.crawler.dao.TaskItemDao;
import com._360pai.crawler.model.Task;
import com._360pai.crawler.model.TaskItem;
import com._360pai.crawler.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xdrodger
 * @Title: TaskServiceImpl
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 14:23
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskItemDao taskItemDao;


    @Override
    public Integer getLatestDoingTask(TaskConstants.Type type) {
        Task task = taskDao.findFirstByTypeAndStatusOrderByIdDesc(type.getKey(), TaskConstants.Status.DOING.getKey());
        if (task == null) {
            task = createTask(type);
        }
        return task.getId();
    }

    private Task createTask(TaskConstants.Type type) {
        Task task = new Task();
        task.setType(type.getKey());
        task.setStatus(TaskConstants.Status.DOING.getKey());
        task.setDeleteFlag(false);
        Date now = new Date();
        task.setCreateTime(now);
        task.setUpdateTime(now);
        taskDao.save(task);
        return task;
    }

    @Override
    public Integer updateTask(Integer taskId, TaskConstants.Status status) {
        Task task = taskDao.findOne(taskId);
        task.setStatus(status.getKey());
        task.setUpdateTime(new Date());
        return task.getId();
    }

    @Override
    public TaskItem getTaskItem(Integer taskId, String keyword) {
        TaskItem taskItem = taskItemDao.findFirstByTaskIdAndKeywordOrderByIdDesc(taskId, keyword);
        if (taskItem == null) {
            taskItem = createTaskItem(taskId, keyword);
        }
        return taskItem;
    }

    private TaskItem createTaskItem(Integer taskId, String keyword) {
        TaskItem taskItem = new TaskItem();
        taskItem.setTaskId(taskId);
        taskItem.setKeyword(keyword);
        taskItem.setStatus(TaskConstants.Status.DOING.getKey());
        taskItem.setDeleteFlag(false);
        Date now = new Date();
        taskItem.setCreateTime(now);
        taskItem.setUpdateTime(now);
        taskItemDao.save(taskItem);
        return taskItem;
    }

    @Override
    public Integer updateTaskItem(Integer taskItemId, TaskConstants.Status status) {
        TaskItem taskItem = taskItemDao.findOne(taskItemId);
        taskItem.setStatus(status.getKey());
        taskItem.setUpdateTime(new Date());
        taskItemDao.save(taskItem);
        return taskItem.getId();
    }
}
