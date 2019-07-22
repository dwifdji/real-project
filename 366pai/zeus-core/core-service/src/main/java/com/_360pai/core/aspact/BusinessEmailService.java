package com._360pai.core.aspact;

import com._360pai.core.common.constants.EnrollingEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xdrodger
 * @Title: BusinessEmailService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/3/5 19:36
 */
@Component
public class BusinessEmailService extends EmailService {
    /**
     *
     * 发送店铺信息修改申请邮件
     */
    public  void sendShopUpdateApply(String name) {
        List<String> emailList =  getEmailList("24","2");
        if(emailList.size()==0){
            return;
        }
        String title = "店铺信息变更申请";
        String content = String.format("【360PAI】%s用户申请进行头像/昵称的变更，请立即处理。", name);
        sendEmail(emailList,title,content);
    }
}
