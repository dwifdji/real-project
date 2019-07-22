package com.winback.arch.web.log;

import com.winback.arch.common.OperatorLogModel;
import com.winback.arch.common.utils.ToolUtil;
import com.winback.arch.web.utils.IpUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/8/31 09:30
 */
@Component
public class LogUtils {

    @Autowired
    private IpUtils ipUtils;

    public void doBefore(OperatorLogModel operatorLogModel, String method, Object[] args) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest       request    = attributes.getRequest();

        //获取传入目标方法的参数
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if (o instanceof ServletRequest || (o instanceof ServletResponse) || o instanceof MultipartFile) {
                args[i] = o.toString();
            }
        }
        String parameterStr = JSONObject.toJSONString(args);

        operatorLogModel.setParameter(parameterStr);
        operatorLogModel.setUri(request.getRequestURI());
        operatorLogModel.setRequestUri(request.getRequestURL().toString());

        Map<String, String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
        operatorLogModel.setBrowser(browserMap.get("os") + "-" + browserMap.get("browser"));

        String ip = ToolUtil.getClientIp(request);
        operatorLogModel.setRemoteAddr(ip);
        JSONObject map = ipUtils.getAddressByIPTencent(ip);
        operatorLogModel.setArea("" + map.get("area"));
        operatorLogModel.setProvince("" + map.get("province"));
        operatorLogModel.setCity("" + map.get("city"));
        operatorLogModel.setIsp("" + map.get("isp"));

        operatorLogModel.setClassMethod(method);
        operatorLogModel.setHttpMethod(request.getMethod());
        operatorLogModel.setType(ToolUtil.isAjax(request) ? "Ajax请求" : "普通请求");
    }
}
