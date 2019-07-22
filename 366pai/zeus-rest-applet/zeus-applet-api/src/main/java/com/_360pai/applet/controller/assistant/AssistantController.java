package com._360pai.applet.controller.assistant;

import com._360pai.applet.controller.AbstractController;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.file.FilePathUtils;
import com._360pai.arch.core.file.QiNiuUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.InstructionsContentFacade;
import com._360pai.core.facade.applet.AppletFacade;
import com._360pai.core.facade.applet.req.AssistantReq;
import com._360pai.core.facade.assistant.BannerFacade;
import com._360pai.core.facade.assistant.CommonFacade;
import com._360pai.core.facade.assistant.req.BannerReq;
import com._360pai.core.facade.assistant.req.FileReq;
import com._360pai.core.facade.assistant.resp.TokenResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：小程序辅助模块 controller
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/26 11:08
 */
@RestController
public class AssistantController extends AbstractController {

    @Reference(version = "1.0.0")
    private AppletFacade appletFacade;
    @Reference(version = "1.0.0")
    private CommonFacade commonFacade;
    @Autowired
    private QiNiuUtil qiNiuUtil;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Reference(version = "1.0.0")
    private ActivityFacade activityFacade;
    @Autowired
    private SystemProperties systemProperties;
    @Reference(version = "1.0.0")
    private InstructionsContentFacade instructionsContentFacade;
    @Reference(version = "1.0.0")
    private BannerFacade bannerFacade;

    /**
     * 获取最近访客列表
     */
    @GetMapping(value = "/confined/getVisitList")
    public ResponseModel getVisitList(AssistantReq.comReq req) {
        if (StringUtils.isEmpty(req.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo.getShopId()==null){
            return ResponseModel.fail("未开店");
        }
        req.setShopId(String.valueOf(accountBaseInfo.getShopId()));

        //当请求的店不为自己的店时 直接返回
        if(StringUtils.isNotEmpty(req.getShopId())){
            if(!req.getShopId().equals(String.valueOf(accountBaseInfo.getShopId()))){
                req.setShopId("0");
            }
        }

        return appletFacade.getVisitList(req);
    }



    /**
     * 添加最近访客
     */
    @PostMapping(value = "/confined/saveVisitRecord")
    public ResponseModel saveVisitRecord(@RequestBody AssistantReq.comReq req) {

        if(StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setReqShopId(StringUtils.isEmpty(req.getShopId())?null:Integer.valueOf(req.getShopId()));
        req.setOpenId(accountBaseInfo.getOpenId());
        req.setShopId(accountBaseInfo.getShopId()==null?null:String.valueOf(accountBaseInfo.getShopId()));
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        return appletFacade.saveVisitRecord(req);
    }

    /**
     * 获取配置信息接口
     */
    @GetMapping(value = "/open/config/info")
    public ResponseModel getConfigInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("servicePhone", systemProperties.getAppletServicePhone());
        data.put("frequentlyAskedQuestionsUrls", new String[] {
                "https://cdn-images.360pai.com/FpTi2GyBdI59Y0-bbyeevso80qws", "https://cdn-images.360pai.com/Fg5cVMz9B5xATiyfRdPvc1-v3ZTy", "https://cdn-images.360pai.com/FiDmWUoG9tvb-Q0VlHwbxALfKie-"
        });
        return ResponseModel.succ(data);
    }


    /**
     * 开店支付
     */
    @PostMapping(value = "/confined/openShopPay")
    public ResponseModel openShopPay() {

        AssistantReq.comReq req = new AssistantReq.comReq();

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        //未认证账户返回
        if(accountBaseInfo.getAccountId()==null){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        if(accountBaseInfo.getOpenId()==null){
            return ResponseModel.fail("openId为空！");

        }

        req.setOpenId(accountBaseInfo.getOpenId());
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        return appletFacade.openShopPay(req);
    }



    /**
     * 开店支付
     */
    @PostMapping(value = "/confined/pay/callBack")
    public ResponseModel payCallBack(@RequestBody AssistantReq.payCallBackReq req) {

        if(StringUtils.isEmpty(req.getOrderId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        //未认证账户返回
        if(accountBaseInfo.getAccountId()==null){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        req.setOpenId(accountBaseInfo.getOpenId());
        return appletFacade.openShopPayCallBack(req);
    }

    /**
     * 获取七牛token信息
     */
    @GetMapping(value = "/open/get_qiniu_token")
    public ResponseModel getQiniuToken() {

        TokenResp resp = commonFacade.getQiNiuToken();
        return ResponseModel.succ(resp);

    }

    @PostMapping(value = "/open/save/external/img/url")
    @ResponseBody
    public ResponseModel saveExternalImgUrl(@RequestBody Map<String, Object> params) {
        String imgUrl = (String) params.get("imgUrl");
        if (StringUtils.isEmpty(imgUrl)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("newImgUrl", commonFacade.saveExternalImgUrl(imgUrl));
        return ResponseModel.succ(data);
    }

    @PostMapping(value = "/open/file/imageUpload")
    @ResponseBody
    public ResponseModel imageUpload(@RequestParam("file") MultipartFile file) {
        //保存文件
        Boolean flag = saveFile(file);
        if (!flag) {
            return ResponseModel.fail();
        }
        File uploadFile = new File(FilePathUtils.getInputPath() + file.getOriginalFilename());
        if (!uploadFile.exists()) {
            return ResponseModel.fail();
        }
        String returnUrl = null;
        try {
            returnUrl = qiNiuUtil.uploadToPublic(uploadFile, DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT) + "/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(returnUrl)) {
            return ResponseModel.fail();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("imgUrl", "https://" + gatewayProperties.getDomain() + "/" + returnUrl);
        return ResponseModel.succ(data);
    }

    private boolean saveFile(MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String savePath = FilePathUtils.getInputPath() + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(savePath));

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取所有银行
     */
    @GetMapping(value = "/open/getAllBanks")
    public ResponseModel getAllBanks(@RequestParam(name="type", defaultValue = "0") String type) {
        return ResponseModel.succ(commonFacade.getAllBanks(type));
    }

    /**
     * 获取小程序协议信息接口
     */
    @GetMapping(value = "/open/applet/agreement")
    public ResponseModel getAgreement(@RequestParam(name="type", defaultValue = "1") String type) {
        return instructionsContentFacade.getAppletAgreement(type);
    }

    /**
     * 生产二维码图片
     */
    @GetMapping(value = "/open/create/qr/code")
    public void createQrCode(FileReq.Watermark req, HttpServletResponse response) {
        String content = req.getUrl();
        int width = 300;
        int height = 300;
        String format = "png";
        //定义二维码的参数
        HashMap map = new HashMap();
        //设置编码
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置纠错等级
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 2);
        String path = FilePathUtils.getOutPutPath() + RandomNumberGenerator.wordGenerator(9) + ".png";
        try {
            //生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            Path file = new File(path).toPath();
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, os);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: banner列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:20
     */
    @GetMapping(value = "/open/banners/list")
    public ResponseModel selectBannerList(BannerReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setIsOnline(true);
        PageInfo pageInfo = bannerFacade.selectBanner(req);
        model.setContent(pageInfo);
        return model;
    }
}
