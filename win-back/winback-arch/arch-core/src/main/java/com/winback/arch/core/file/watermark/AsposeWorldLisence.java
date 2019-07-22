package com.winback.arch.core.file.watermark;

import com.aspose.words.License;
import com.winback.arch.core.file.FilePathUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/15 10:09
 */
public class AsposeWorldLisence {
    /**
     * 从Classpath（jar文件中）中读取License
     */
    public static void loadLicense() {
        //返回读取指定资源的输入流
        InputStream is = null;
        try {
            is = new FileInputStream(FilePathUtils.getInputPath() + "Aspose.Words.lic");
            License aposeLic = new License();
            aposeLic.setLicense(is);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                }

            }
        }
    }
}
