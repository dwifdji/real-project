package com.winback.gateway.service.email.impl;



import com.alibaba.fastjson.JSON;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.core.file.FilePathUtils;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.condition.email.TGatewayEmailConfigCondition;
import com.winback.gateway.dao.email.TGatewayEmailConfigDao;
import com.winback.gateway.dao.email.TGatewayEmailRecordDao;
import com.winback.gateway.model.email.TGatewayEmailConfig;
import com.winback.gateway.model.email.TGatewayEmailRecord;
import com.winback.gateway.service.email.EmailService;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.dao.email.TGatewayEmailRecordDao;
import com.winback.gateway.model.email.TGatewayEmailRecord;
import com.winback.gateway.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:00
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private TGatewayEmailRecordDao gatewayEmailRecordDao;

    @Autowired
    private TGatewayEmailConfigDao gatewayEmailConfigDao;

    /**
     * redis缓存管理器
     */
    @Resource
    private RedisCachemanager redisCacheManager;


    @Override
    public void sendEmail(EmailSendReq emailSendReq) {

        try{

            addFile(emailSendReq);

            //发送频率过滤
            if(notSend(emailSendReq)){
                log.error("触发邮件发送频率限制，不发送邮件，请求参数为：{}",JSON.toJSONString(emailSendReq));
                return;
            }
            log.info("收到发送邮箱请求，参数为:{}", JSON.toJSONString(emailSendReq));
            if(emailSendReq.getId()==null){
                emailSendReq.setId(saveRecord(emailSendReq));
            }
            Properties prop = new Properties();
            prop.setProperty("mail.host", gatewayProperties.getSmtpHost());
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.port", "465");
            prop.setProperty("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            //session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、连上邮件服务器，需要发件人提供邮箱的用户名和密码进行验证
            ts.connect(gatewayProperties.getSmtpHost(), gatewayProperties.getSenderAccount(), gatewayProperties.getSenderPassword());
            //4、创建邮件
            Message message = createSimpleMail(session,emailSendReq);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();

            new Thread(()->updateRecord(emailSendReq,ApiCallResult.SUCCESS.getCode(),ApiCallResult.SUCCESS.getDesc())).start();

        }catch (Exception e){

            updateRecord(emailSendReq,"222",e.getMessage());

            //ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.EMAIL, JSON.toJSONString(emailSendReq),"发送邮件异常", null,e);


            log.error("发送邮件异常，异常信息为：{}",e);

        }

    }


    /**
     *
     *附件url不为空 添加
     */
    private void addFile(EmailSendReq emailSendReq) {
        if(emailSendReq.getFilesUrl()!=null&&emailSendReq.getFilesUrl().size()>0){
            List<File> files = new ArrayList<>();

            for(String fileUrl:  emailSendReq.getFilesUrl()){
                String fileName = FilePathUtils.getFileName(fileUrl);
                File file = FilePathUtils.downLoadFromUrl(fileUrl, fileName);
                files.add(file);
            }
            emailSendReq.setFiles(files);

        }
     }

    private boolean notSend(EmailSendReq emailSendReq) {

        String content = emailSendReq.getContent();

            try {
                if(content.contains("No provider available from registry")||content.contains("Failed to invoke the method")){
                    String  noProvider = "noProvider";

                    Object value= redisCacheManager.get(noProvider);

                    if(value==null){
                        Long timeOut = 5*60L;
                        redisCacheManager.set(noProvider,"No provider available from registry" ,timeOut);
                    }else{
                        return true;
                    }

                }

                return false;
            }catch (Exception e){

                return false;
        }

    }

    private Integer saveRecord(EmailSendReq req) {

        try {
            TGatewayEmailRecord recordReq = new TGatewayEmailRecord();

            recordReq.setContent(req.getContent());
            recordReq.setCopyEmail(JSON.toJSONString(req.getCopyEmail()));
            recordReq.setSendType(req.getSendType());
            recordReq.setEmail(JSON.toJSONString(req.getEmail()));
            recordReq.setTemplateCode(req.getTemplateCode());
            recordReq.setTemplateParam(req.getTemplateParam());
            recordReq.setCreateTime(DateUtil.getSysTime());
            recordReq.setTitle(req.getTitle());

            gatewayEmailRecordDao.insert(recordReq);

            return recordReq.getId();
        }catch (Exception e ){
            log.error("保存邮件异常，异常信息为：{}",e);
        }
        return 0;

    }
    private void updateRecord(EmailSendReq emailSendReq,String status,String msg) {
        try {
            TGatewayEmailRecord record = new TGatewayEmailRecord();
            record.setId(emailSendReq.getId());
            record.setStatus(status);
            record.setMsg(msg);
            record.setUpdateTime(DateUtil.getSysTime());
            gatewayEmailRecordDao.updateById(record);

        }catch (Exception e){

            log.error("更新邮件表异常，异常信息为：{}",e);
        }
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     * @Anthor:孤傲苍狼
     *
     * @param session
     * @return
     * @throws Exception
     */
    public MimeMessage createSimpleMail(Session session, EmailSendReq emailSendReq)
            throws Exception {

        //获取发送的内容
        String content = getContent(emailSendReq);

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(gatewayProperties.getSenderAccount()));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发

        String users ="";

         for(String email :emailSendReq.getEmail()){

            users =users + email+",";
          }

        // 设置邮件接收方
        Address[] internetAddressTo = new InternetAddress().parse(users);


        message.setRecipients(Message.RecipientType.TO, internetAddressTo);

        //抄送地址不为空
        if(emailSendReq.getCopyEmail()!=null){
            //抄送地址
            for(String email :emailSendReq.getCopyEmail()){
                message.setRecipient(Message.RecipientType.CC, new InternetAddress(email));
            }
        }

        //邮件的标题
        message.setSubject(emailSendReq.getTitle());
        //创建消息部分
        final BodyPart messageBodyPart = new MimeBodyPart();
        //消息
        messageBodyPart.setContent(content,"text/html;charset=UTF-8");
        //创建多重消息
        final Multipart multipart = new MimeMultipart();
        //设置文本消息部分
        multipart.addBodyPart(messageBodyPart);

        if(emailSendReq.getFiles()!=null){
            for(File file :emailSendReq.getFiles()){
                BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                //MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
                multipart.addBodyPart(attachmentBodyPart);
            }

        }
        // 发送完整消息
        message.setContent(multipart);

        //返回创建好的邮件对象
        return message;
    }


    /**
     *
     *根据发送的方式获取发送的内容
     */
    private String getContent(EmailSendReq emailSendReq) {


        return emailSendReq.getContent();
    }


    @Override
    public TGatewayEmailConfig getEmailConfig(TGatewayEmailConfigCondition condition) {
        return gatewayEmailConfigDao.selectFirst(condition);
    }
}
