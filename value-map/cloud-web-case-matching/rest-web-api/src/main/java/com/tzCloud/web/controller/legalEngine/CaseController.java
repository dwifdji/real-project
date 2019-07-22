package com.tzCloud.web.controller.legalEngine;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.utils.WordProcessingUtils;
import com.tzCloud.core.facade.account.AccountFacade;
import com.tzCloud.core.facade.legalEngine.CaseFacade;
import com.tzCloud.core.facade.legalEngine.req.CaseSearchReq;
import com.tzCloud.core.facade.legalEngine.vo.CaseHtmlDataVo;
import com.tzCloud.web.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author xdrodger
 * @Title: CaseController
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-19 13:44
 */
@Slf4j
@RestController
public class CaseController  extends AbstractController {
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

    /**
     * 案例搜索接口(分页)
     */
    @PostMapping(value = "/open/legalEngine/searchCase")
    public ResponseModel searchCase(@RequestBody CaseSearchReq.SearchListReq req, HttpServletResponse response) throws Exception {
        req.setLoginId(loadCurLoginId());
        if ((req.getLoginId() == null || req.getLoginId().intValue() == -1) && req.getPage() > 1) {
            WebUtils.toHttp(response).sendError(401);
        }
        return caseFacade.searchCase(req);
    }

    /**
     * 案例搜索接口(侧边栏)
     */
    @PostMapping(value = "/open/legalEngine/searchCaseSidebar")
    public ResponseModel searchCaseSidebar(@RequestBody CaseSearchReq.SearchListReq req) {
        req.setLoginId(loadCurLoginId());
        return caseFacade.searchCaseSidebar(req);
    }

    /**
     * 案例详情接口
     */
    @GetMapping(value = "/confined/legalEngine/getCaseDetail")
    public ResponseModel getCaseDetail(CaseSearchReq.BaseReq req) {
        Assert.notNull(req.getDocId(), "docId不能为空");
        req.setLoginId(loadCurLoginId());
        return caseFacade.getCaseDetail(req);
    }

    /**
     * 案例下载接口
     */
    @GetMapping(value = "/confined/legalEngine/case/download")
    public String downloadCase(CaseSearchReq.BaseReq req, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("cookie id =", loadCurLoginId());
        Assert.notNull(req.getDocId(), "docId不能为空");
        req.setLoginId(loadCurLoginId());
        CaseHtmlDataVo dataVo = caseFacade.getByDocId(req.getDocId());
        String fileName = dataVo.getTitle() + ".doc";//被下载文件的名称
        String filePath = "/data/file/output/" + fileName;//被下载的文件在服务器中的路径,
        String style = "<style>";
        style += "</style>";
        String content = "<html>" +
                "<head>" + style + "</head>" +
                "<body>";
        content += dataVo.getHtml();
        content += "</body>" + "</html>";
        WordProcessingUtils.html2word(filePath, content);
        File file = new File(filePath);
        if (file.exists()) {
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0
                    || userAgent.indexOf("Safari") >= 0) {
                fileName = new String((fileName).getBytes(), "ISO8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF8"); //其他浏览器
            }
            response.setContentType("application/msword;charset=UTF-8");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }

                return "下载成功";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                //file.deleteOnExit();
                file.delete();
            }
        }
        return "下载失败";
    }
}
