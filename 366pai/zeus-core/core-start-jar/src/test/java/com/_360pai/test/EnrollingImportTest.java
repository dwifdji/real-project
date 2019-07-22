package com._360pai.test;

import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.EnrollingRealImportFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.*;

/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class EnrollingImportTest extends TestBase{






    @Resource
    private EnrollingImportFacade enrollingImportFacade;



    @Resource
    private EnrollingRealImportFacade enrollingRealImportFacade;




    @Test
    public void uploadActivity() {

        EnrollingImportReq.uploadActivityReq req = new EnrollingImportReq.uploadActivityReq();
        req.setUserId("1");
        enrollingImportFacade.uploadActivity(req);
    }



    @Test
    public void uploadRealActivity() {

        EnrollingImportReq.uploadActivityReq req = new EnrollingImportReq.uploadActivityReq();
        req.setUserId("1");
        req.setBytes(fileToBytes("D:\\物权招商模板.xlsx"));


        enrollingRealImportFacade.uploadActivity(req);
    }




    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
         } catch (IOException ex) {
         } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {
             } finally{
                try {
                    if(null!=fis){
                        fis.close();
                    }
                } catch (IOException ex) {
                 }
            }
        }

        return buffer;
    }


    @Test
    public void getUserList() {

        EnrollingImportReq.getUserListReq req = new EnrollingImportReq.getUserListReq();
        //req.setName("1");
        System.out.println("返回参数。。。。。"+JSON.toJSONString(enrollingImportFacade.getUserList(req)));
    }



    @Test
    public void getUploadActivityDetails() {

        EnrollingImportReq.getUploadActivityDetailsReq req = new EnrollingImportReq.getUploadActivityDetailsReq();
        req.setActivityId("641");
        System.out.println("返回参数。。。。。"+JSON.toJSONString(enrollingImportFacade.getUploadActivityDetails(req)));
    }



    @Test
    public void getUploadActivityList() {

        EnrollingImportReq.getUploadActivityListReq  req = new EnrollingImportReq.getUploadActivityListReq();
        req.setType("1");
        System.out.println("返回参数。。。。。"+JSON.toJSONString(enrollingRealImportFacade.getUploadRealActivityList(req)));
    }
}




