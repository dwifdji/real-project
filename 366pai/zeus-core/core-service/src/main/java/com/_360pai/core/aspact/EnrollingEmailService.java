package com._360pai.core.aspact;

import com._360pai.core.common.constants.EnrollingEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述：预招商 发送邮件服务类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/10/12 12:53
 */
@Component
public class EnrollingEmailService extends EmailService {



    /**
     *
     *发送预招商审核邮件
     */
    public  void sendEnrollingAudit(String activityCode,String type) {

        if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(type)){

            enrollingAudit(activityCode);
        }else{
            claimOrRealAudit(activityCode);

        }

    }



    /**
     *
     *预招商报名邮件
     */
    public  void sendEnrollingApply(String activityCode,String type) {

        if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(type)){

            enrollingApply(activityCode);
        }else{
            claimOrRealApply(activityCode);

        }

    }


        /**
         *
         *债权与物权上拍审核发送邮件 to 平台审核人
         */
        private  void claimOrRealAudit(String activityCode){

        //获取发送邮件的
        List<String>  emailList =  getEmailList("1","1");

        if(emailList.size()==0){
            return;
        }

        String title = "招商标的"+activityCode+"需要及时审核";

        String content = "招商标的"+activityCode+"需要及时审核，请尽快到360PAI系统后台操作~";

        sendEmail(emailList,title,content);

    }



    /**
     *
     *抵债权物权 有人报名 to 平台审核人
     */
    private  void enrollingAudit(String activityCode){

        //获取发送邮件的
        List<String>  emailList =  getEmailList("1","1");

        if(emailList.size()==0){
            return;
        }


        String title = "招商标的"+activityCode+"已通过送拍机构审核";

        String content = "招商标的"+activityCode+"已通过送拍机构审核，请尽快在360PAI后台操作审核并上线";

        sendEmail(emailList,title,content);


    }




    /**
     *
     *债权 或者物权报名 to 平台客服
     */
    private void claimOrRealApply(String activityCode){

        List<String>  emailList =  getEmailList("1","2");

        if(emailList.size()==0){
            return;
        }


        String title = "招商标的"+activityCode+"已有人报名";

        String content = "招商标的"+activityCode+"已有人报名，请尽快联系委托人，告知其可以准备走正式拍卖流程。";

        sendEmail(emailList,title,content);


    }



    /**
     *
     *抵押物预招商有人报名to 平台客服
     */
    private  void enrollingApply(String activityCode){

        List<String>  emailList =  getEmailList("1","2");

        if(emailList.size()==0){
            return;
        }

        String title = "招商标的"+activityCode+"已有人提交保证金";

        String content = "招商标的"+activityCode+"已有人提交保证金，请尽快联系委托人获知后续拍卖事项，并通知竞买人。";

        sendEmail(emailList,title,content);

    }

}
