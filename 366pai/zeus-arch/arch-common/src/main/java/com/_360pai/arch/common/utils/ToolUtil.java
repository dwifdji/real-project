/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com._360pai.arch.common.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ToolUtil {

    private static final String LOCALHOST  = "127.0.0.1";
    private static final String LOCALHOST1 = "localhost";
    private static final String LOCALHOST2 = "0.0.0.0";
    private static final String LOCALHOST3 = "0:0:0:0:0:0:0:1";

    private static final  String provinceStrings = "北京市,天津市,河北省,山西省,内蒙古自治区,辽宁省,吉林省,黑龙江省,上海市,江苏省,浙江省,安徽省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省,广西壮族自治区,海南省,重庆市,四川省,贵州省,云南省,西藏自治区,陕西省,甘肃省,青海省,宁夏回族自治区,新疆维吾尔自治区,香港特别行政区,澳门特别行政区,台湾省,北京,天津,河北,山西,内蒙古自治区,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西壮族自治区,海南,重庆,四川,贵州,云南,西藏自治区,陕西,甘肃,青海,宁夏回族自治区,新疆维吾尔自治区,香港特别行政区,澳门特别行政区,台湾,北京,天津,河北,山西,内蒙古自治区,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西,海南,重庆,四川,贵州,云南,西藏,陕西,甘肃,青海,宁夏,新疆,香港,澳门,台湾";

    private static final Set<String> provinceSet = new HashSet<>();

    static {
        //provinceSet = new HashSet<>(Arrays.asList(provinceStrs.split(",")));
        provinceSet.addAll(Arrays.asList(provinceStrings.split(",")));
    }

    /**
     * 获取客户端的ip信息
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        log.debug("ipadd : " + ip);
        // 蓝讯cdn的真实ip
        if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (LOCALHOST1.equals(ip) || LOCALHOST2.equals(ip) || LOCALHOST3.equals(ip) || LOCALHOST.equals(ip)) {
            ip = "127.0.0.1";
        }
        log.debug(" ip --> " + ip);
        return ip;
    }

    /**
     * 将bean转换成map
     *
     * @param condition
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertBeanToMap(Object condition) {
        if (condition == null) {
            return null;
        }
        if (condition instanceof Map) {
            return (Map<String, Object>) condition;
        }
        Map<String, Object> objectAsMap = new HashMap<String, Object>();
        BeanInfo            info        = null;
        try {
            info = Introspector.getBeanInfo(condition.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !"class".equals(pd.getName())) {
                try {
                    objectAsMap.put(pd.getName(), reader.invoke(condition));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return objectAsMap;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        int d = fileName.lastIndexOf(".");
        if (d == -1) {
            return "text/html";
        }
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".png".equalsIgnoreCase(fileExtension)) {
            return "image/png";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || ".docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        return "text/html";
    }

    /**
     * 判断请求是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        return accept != null && accept.contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }

    /**
     * 获取操作系统,浏览器及浏览器版本信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getOsAndBrowserInfo(HttpServletRequest request) {
        Map<String, String> map            = Maps.newHashMap();
        String              userAgent      = request.getHeader("User-Agent");
        String              user           = userAgent.toLowerCase();

        String os      = "";
        String browser = "";

        try {
            //=================OS Info=======================
            if (userAgent.toLowerCase().contains("windows")) {
                os = "Windows";
            } else if (userAgent.toLowerCase().contains("mac")) {
                os = "Mac";
            } else if (userAgent.toLowerCase().contains("x11")) {
                os = "Unix";
            } else if (userAgent.toLowerCase().contains("android")) {
                os = "Android";
            } else if (userAgent.toLowerCase().contains("iphone")) {
                os = "IPhone";
            } else {
                os = "UnKnown, More-Info: " + userAgent;
            }
            //===============Browser===========================
            if (user.contains("edge")) {
                browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
            } else if (user.contains("msie")) {
                String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
                browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
            } else if (user.contains("safari") && user.contains("version")) {
                browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                        + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr") || user.contains("opera")) {
                if (user.contains("opera")) {
                    browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                            + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
                } else if (user.contains("opr")) {
                    browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                            .replace("OPR", "Opera");
                }

            } else if (user.contains("chrome")) {
                browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
            } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6")) ||
                    (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) ||
                    (user.contains("mozilla/4.08")) || (user.contains("mozilla/3"))) {
                browser = "Netscape-?";

            } else if (user.contains("firefox")) {
                browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
            } else if (user.contains("rv")) {
                String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
                browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
            } else {
                browser = "UnKnown, More-Info: " + userAgent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("os", os);
        map.put("browser", browser);
        return map;
    }

    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    private  static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    public static boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 隐藏手机号中间四位
     */
    public static String maskMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    /**
     * 联系方式脱敏
     */
    public static String maskContact(String contact) {
        if (StringUtils.isEmpty(contact)) {
            return contact;
        }
        if (contact.length() == 11) {
            return contact.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        }
        if (contact.length() >= 6) {
            if (contact.contains("@")) {
                return contact.substring(0,2) + "****" + contact.substring(contact.lastIndexOf("@"));
            }
            return contact.substring(0,2) + "****" + contact.substring(contact.length()-2);
        }
        return contact;
    }


    /**
     * 隐藏身份证号号中间12位
     */
    public static String maskCertificateNumber(String certificateNumber) {
        if (StringUtils.isEmpty(certificateNumber)) {
            return certificateNumber;
        }
        int length = certificateNumber.length();
        if (length == 18) {
            return certificateNumber.replaceAll("(\\d{2})\\d{12}(\\d{4})","$1************$2");
        } else if (length == 15) {
            return certificateNumber.replaceAll("(\\d{2})\\d{9}(\\d{4})","$1*********$2");
        } else if (length > 6) {
            return certificateNumber.replaceAll("(\\d{2})\\d{" + (length - 6) + "}(\\d{4})", "$1" + StringUtils.repeat("*", (length - 6)) + "$2");
        } else {
            return certificateNumber;
        }
    }

    /**
     * 隐藏律师姓名
     */
    public static String maskLawyerName(String name) {
        if (StringUtils.isBlank(name)) {
            return "--";
        }
        return name.substring(0,1) + "律师";
    }

    /**
     * 隐藏律所名称
     */
    public static String maskLawOfficeName(String name) {
        if (StringUtils.isBlank(name)) {
            return "--";
        }
        String key = "";
        for (String province : provinceSet) {
            if (name.contains(province)) {
                key = province;
                break;
            }
        }
        if (StringUtils.isNotBlank(key)) {
            return key + "****律师事务所";
        }
        if (name.length() > 3) {
            return name.substring(0,3) + "****律师事务所";
        }
        return "****律师事务所";
    }

    /**
     * 处置服务商名称脱敏
     */
    public static String maskDisposeProviderName(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        if (name.contains("律所") || name.contains("律师事务所") || name.contains("事务所")) {
            return maskLawOfficeName(name);
        }
        return maskLawyerName(name);
    }

    public static void main(String args[]) throws Exception {
        //long t1 = System.currentTimeMillis();
        //Map<String,String> map = getAddressByIP("0.0.0.0");
        //LOGGER.info("地区："+map.get("country"));
        //LOGGER.info("省："+map.get("province"));
        //LOGGER.info("市："+map.get("city"));
        //LOGGER.info("互联网服务提供商："+map.get("isp"));
        //long t2 = System.currentTimeMillis();
        //System.out.println("执行时间为"+(t2-t1));

        //StringBuilder sb = new StringBuilder("https://apis.map.qq.com/ws/location/v1/ip?ip=117.82.187.111&key=N7XBZ-NX764-OFOUH-D5LJY-KZ3QK-6WFNX");
        //String result= HttpUtil.get(sb.toString(), "UTF-8");
        //Map<String,String> map = Maps.newHashMap();
        //Map resultMap = JSON.parseObject(result,Map.class);
        //Map m = (Map) resultMap.get("result");
        //Map r = (Map) m.get("ad_info");
        //Integer code = (Integer) resultMap.get("code");
        //if(code == 0){
        //	Map<String,String> detail = (Map<String,String>)resultMap.get("data");
        //	String country = detail.get("country");
        System.out.println(isContainChinese("活动不存在asasda"));
        System.out.println("--");
        //}

        System.out.println(maskCertificateNumber("123456789012345"));
        System.out.println(maskCertificateNumber("123456789012345678"));
        System.out.println(maskCertificateNumber("1234567890"));
        System.out.println(maskCertificateNumber("12345"));
    }
}
