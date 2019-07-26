package com._360pai.test;

import org.junit.Test;

/**
 * @author xdrodger
 * @Title: SimpleTest
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/16 14:02
 */
public class SimpleTest {

    @Test
    public void test() {
        String str = "//d03.lawtimeimg.com/micphoto/fayuan/size/high_fayuan.jpg";
        System.out.println(str.replaceFirst("//", ""));
        System.out.println("--");
    }
}
