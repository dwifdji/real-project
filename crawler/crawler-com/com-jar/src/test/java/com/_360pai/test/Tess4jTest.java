package com._360pai.test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.junit.Test;

import java.io.File;

/**
 * @author xdrodger
 * @Title: Tess4jTest
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 16:33
 */
public class Tess4jTest {

    @Test
    public void testOcr() {
        try {
            String filePath = "/Users/xdrodger/Downloads/captcha.png";
            File imageFile = new File(filePath);//图片位置
            ITesseract instance = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            //instance.setDatapath("/Library/workspace/third/tessdata-master");
            instance.setDatapath(tessDataFolder.getAbsolutePath());
            instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）
            String result = instance.doOCR(imageFile);//开始识别
            System.out.println(result);//打印图片内容
        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

}
