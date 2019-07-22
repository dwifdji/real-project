package com._360pai;

import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.service.EmailService;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class EmailTest extends BaseJunit {



    @Autowired
    private EmailService emailService;

    @Autowired
    private GatewayExceptionEmailService gatewayExceptionEmailService;

    @Resource
    private RedisCachemanager redisCacheManager;



    /**
     *
     * 发送邮件test
     * @param
     * @param
     */
    @Test
    public void testEmail() {

        EmailSendReq emailSendReq = new EmailSendReq();

        List<String> emailList = new ArrayList<>();

       emailList.add("766248878@qq.com");


       emailSendReq.setTitle("附件测试");
        emailSendReq.setContent("异常报警邮件122");
        emailSendReq.setEmail(emailList);
        emailSendReq.setSendType(EmailSendReq.CONTENT_TYPE);

        List<File> fileList = new ArrayList<>();

        File source = new File("C:\\Users\\15651\\Desktop\\项目资料\\第三方文档\\上线须知.docx");

        File source1 = new File("C:\\Users\\15651\\Desktop\\项目资料\\第三方文档\\上线检查单1225.docx");

        fileList.add(source);
        fileList.add(source1);

        emailSendReq.setFiles(fileList);
        
        emailService.sendEmail(emailSendReq);

        System.out.println();
    }




    /**
     *
     * 发送邮件test
     * @param
     * @param
     */
    @Test
    public void testExceptionEmailService() {

        try {
            String content = "No provider available from registry 121212";

        if(content.contains("No provider available from registry")){
            String  noProvider = "noProvider";

            Object value= redisCacheManager.get(noProvider);

            if(value==null){
                Long timeOut = 5*60L;
                redisCacheManager.set(noProvider,"No provider available from registry" ,timeOut);
            }else{

                System.out.println("111111111111");

             }

        }


        System.out.println("2222222222");
    }catch (Exception e){

        System.out.println("3333333333333");


     }



        //gatewayExceptionEmailService.sendRemindEmail(ExceptionEmailEnum.MODULE_TYPE.ENROLLING,"测试","测试1","测试222");

    }








}
