package com._360pai.gateway.common.fddSignature;

import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com.alibaba.fastjson.JSONObject;
import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：法大大请求接口工具类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:31
 */

 public class FddSignatureUtils {


    /**
     * 开户请求
     *
     * @param customer_name 用户名称
     * @param email 邮件
     * @param id_card 证件号码
     * @param ident_type 证件类型
     * @param mobile 手机号码
     */
    public static String invokeSyncPersonAuto(GatewayProperties gatewayProperties,String customer_name,String email,String id_card,String ident_type,String mobile){

        FddClientBase clientBase  = new FddClientBase(gatewayProperties.getFddAppId(),gatewayProperties.getFddSecret(),gatewayProperties.getFddVersion(),gatewayProperties.getFddUrl());

        String response = clientBase.invokeSyncPersonAuto(customer_name,email ,id_card,
            ident_type, mobile);

        return response;
    }


    /**
     * 公司开户请求
     *
     * @param customer_name 公司名称
     * @param email 邮件
     * @param id_card 公司的营业账号
     * @param ident_type 证件类型
     * @param mobile 手机号码
     */
    public static String invokeSyncCompanyAuto(GatewayProperties gatewayProperties,String customer_name,String email,String id_card,String ident_type,String mobile){


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
             String sha1 = FddEncryptTool.sha1(gatewayProperties.getFddAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(gatewayProperties.getFddSecret()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.add(new BasicNameValuePair("customer_name", customer_name));
            params.add(new BasicNameValuePair("email", email));
            String id_mobile = FddEncryptTool.encrypt(id_card + "|" + mobile, gatewayProperties.getFddSecret());
            params.add(new BasicNameValuePair("ident_type", ident_type));
            params.add(new BasicNameValuePair("id_mobile", id_mobile));

            params.add(new BasicNameValuePair("app_id", gatewayProperties.getFddAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", gatewayProperties.getFddVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doCaPost(gatewayProperties.getFddUrl()+"/syncCompany_auto.api", params);











     }



    /**
     * 上传合同模板请求
     *
     * @param customer_id 模板id
     * @param mobile 手机号码
     * @param email 邮件
     */
    public static String invokeInfoChange(GatewayProperties gatewayProperties,String customer_id,String mobile,String  email){

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            String sha1 = FddEncryptTool.sha1(gatewayProperties.getFddAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(gatewayProperties.getFddSecret()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("mobile", mobile));

            params.add(new BasicNameValuePair("app_id", gatewayProperties.getFddAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", gatewayProperties.getFddVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doCaPost(gatewayProperties.getFddUrl()+"/infochange.api", params);



    }







    /**
     * 上传合同模板请求
     *
     * @param template_id 模板id
     * @param file 模板文件流
     * @param doc_url 模板文件地址
     */
    public  String invokeUploadTemplate(GatewayProperties gatewayProperties,String template_id,File file,String  doc_url){


        //生产环境
        //FddClientBase clientBase  = new FddClientBase("000749","lCFaCGsAZRdsfcym7lHU2Ndc","2.0","https://extapi.fadada.com/api2/");


        FddClientBase clientBase  = new FddClientBase(gatewayProperties.getFddAppId(),gatewayProperties.getFddSecret(),gatewayProperties.getFddVersion(),gatewayProperties.getFddUrl());

        String response = clientBase.invokeUploadTemplate(template_id,  file,  doc_url);

        return response;
    }


    /**
     * 合同生成接口请求参数
     *
     * @param template_id 模板id
     * @param contract_id 合同id
     * @param doc_title 合同标题
     * @param parameter_map 填充内容
     * @param dynamic_tables 动态表格
     */
    public static String invokeGenerateContract(GatewayProperties gatewayProperties, String template_id, String contract_id, String doc_title,
                                                 String parameter_map, String dynamic_tables){
        FddClientBase clientBase  = new FddClientBase(gatewayProperties.getFddAppId(),gatewayProperties.getFddSecret(),gatewayProperties.getFddVersion(),gatewayProperties.getFddUrl());

        String response = clientBase.invokeGenerateContract(template_id,  contract_id,  doc_title,gatewayProperties.getFontSize(),gatewayProperties.getFontType(),
                parameter_map, dynamic_tables);

        return response;
    }

    /**
     * 文档字段签署接口
     *
     * @param transaction_id   交易号
     * @param customer_id      客户编号
     * @param client_role      客户角色
     * @param contract_id      合同编号
     * @param doc_title        文档标题
     * @param sign_keyword     定位关键字
     * @param keyword_strategy 签章策略
     */
    public static String invokeExtSignAuto(GatewayProperties gatewayProperties,String transaction_id, String customer_id, String client_role, String contract_id, String doc_title, String sign_keyword, String keyword_strategy
            ){

        FddClientBase clientBase  = new FddClientBase(gatewayProperties.getFddAppId(),gatewayProperties.getFddSecret(),gatewayProperties.getFddVersion(),gatewayProperties.getFddUrl());


        String response = clientBase.invokeExtSignAuto(transaction_id,  customer_id,  client_role,contract_id,doc_title,
                sign_keyword, keyword_strategy,gatewayProperties.getFddNotifyUrl());

        return response;
    }


    /**
     * 文档字段签署接口
     *
     * @param transaction_id   交易号
     * @param customer_id      客户编号
     * @param client_role      客户角色
     * @param contract_id      合同编号
     * @param doc_title        文档标题
     * @param sign_keyword     定位关键字
     * @param keyword_strategy 签章策略
     */
    public static String extSign(GatewayProperties gatewayProperties,String transaction_id, String customer_id, String client_role, String contract_id, String doc_title, String sign_keyword, String keyword_strategy,String notify_url){

        JSONObject params = new JSONObject();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(gatewayProperties.getFddAppId() + FddEncryptTool.md5Digest(transaction_id + timeStamp) + FddEncryptTool.sha1(gatewayProperties.getFddSecret() + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.put("transaction_id", transaction_id);
            params.put("customer_id", customer_id);
            params.put("client_role", client_role);
            params.put("contract_id", contract_id);
            params.put("doc_title", doc_title);
            params.put("position_type", "0");
            params.put("sign_keyword", sign_keyword);
            params.put("keyword_strategy", keyword_strategy);
            params.put("notify_url", notify_url);

            params.put("app_id", gatewayProperties.getFddAppId());
            params.put("timestamp", timeStamp);
            params.put("v",gatewayProperties.getFddVersion());
            params.put("msg_digest", msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return params.toJSONString();
    }



    /**
     * 文档字段签署接口
     *
     * @param contract_id     签章合同号
     * @param customer_id      客户编号
     */
    public static String invokeQuerySignStatus(GatewayProperties gatewayProperties ,String contract_id, String customer_id){
        FddClientBase clientBase  = new FddClientBase(gatewayProperties.getFddAppId(),gatewayProperties.getFddSecret(),gatewayProperties.getFddVersion(),gatewayProperties.getFddUrl());


        String response = clientBase.invokeQuerySignStatus(contract_id,  customer_id);

        return response;
    }




    /**
     * 验签
     * @param msg_digest      验签字符串
     */
    private static boolean verify(GatewayProperties gatewayProperties ,String msg_digest) {

        //String sha1 = FddEncryptTool.sha1(gatewayProperties.getFddAppId() + FddEncryptTool.md5Digest(transaction_id + timeStamp) + FddEncryptTool.sha1(gatewayProperties.getFddSecret() + customer_id));
        //String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

        return true;
    }


}
