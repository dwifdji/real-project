package com._360pai.test;

import com._360pai.crawler.dao.CourtDao;
import com._360pai.crawler.model.Court;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author xdrodger
 * @Title: RmfyggTest
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 13:06
 */
public class RmfyggTest extends TestBase {

    @Autowired
    private CourtDao courtDao;

    @Test
    public void testFindByType() {
        Page<Court> pageInfo = courtDao.findByType("高级法院", new PageRequest(1, 20));
        System.out.println(pageInfo.getTotalElements());

        System.out.println(pageInfo.getContent().size());

        System.out.println("--");
    }

}
