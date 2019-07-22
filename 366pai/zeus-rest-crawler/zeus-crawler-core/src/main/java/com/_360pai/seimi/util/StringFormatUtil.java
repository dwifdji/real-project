package com._360pai.seimi.util;

import org.seimicrawler.xpath.JXNode;

public class StringFormatUtil {


    public static String changeObjectToStr(Object ob){

        return ob == null? "": ob.toString();
    }


    public static String changeObjectToStr(JXNode jxNode){

        return jxNode == null? "": jxNode.toString();
    }

}
