package com._360pai.test;

import com._360pai.crawler.commons.TaskConstants;
import com._360pai.crawler.model.TaskItem;
import com._360pai.crawler.service.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xdrodger
 * @Title: TaskTest
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 15:02
 */
public class TaskTest extends TestBase {
    @Autowired
    private TaskService taskService;

    @Test
    public void testGetLatestDoingTask() {

        Integer taskId = taskService.getLatestDoingTask(TaskConstants.Type.Type_1);
        System.out.println(taskId);


        TaskItem taskItem = taskService.getTaskItem(taskId, "中华人民共和国最高人民法院");
        System.out.println(taskItem.getId());
        System.out.println("--");
    }
}
