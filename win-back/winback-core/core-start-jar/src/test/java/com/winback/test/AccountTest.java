package com.winback.test;

import com.winback.core.service.account.FranchiseeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xdrodger
 * @Title: AccountTest
 * @ProjectName winback
 * @Description:
 * @date 2019/3/14 18:31
 */
public class AccountTest extends TestBase {

    @Autowired
    private FranchiseeService franchiseeService;

    @Test
    public void testGetApplyStatus() {
        try {
            String applyStatus = franchiseeService.getApplyStatus(22);
            System.out.println(applyStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
