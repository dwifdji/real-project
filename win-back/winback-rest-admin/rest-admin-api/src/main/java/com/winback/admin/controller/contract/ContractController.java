package com.winback.admin.controller.contract;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.aspose.words.Document;
import com.winback.admin.controller.AbstractController;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.core.file.FilePathUtils;
import com.winback.arch.core.file.QiNiuUtil;
import com.winback.arch.core.file.watermark.WordProcessingUtils;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.core.facade.assistant.FileFacade;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.core.facade.contract.req.AdminContractOrderReq;
import com.winback.core.facade.contract.req.AdminContractReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: ContractController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/31 14:38
 */
@Slf4j
@RestController
public class ContractController extends AbstractController {

    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;
    @Autowired
    private QiNiuUtil qiNiuUtil;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Reference(version = "1.0.0")
    private FileFacade fileFacade;

    /**
     * 获取合同列表接口(分页)
     */
    @RequiresPermissions("operation_mgt_7_1_1")
    @GetMapping(value = "/confined/contract/list")
    public ResponseModel getContractList(AdminContractReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getContractList(req));
    }

    /**
     * 获取合同详情接口
     */
    @GetMapping(value = "/confined/contract/detail")
    public ResponseModel getContract(AdminContractReq.QueryReq req) {
        Assert.notNull(req.getContractId(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getContract(req));
    }

    /**
     * 添加合同接口
     */
    @PostMapping(value = "/confined/contract/add")
    public ResponseModel addContract(@RequestBody AdminContractReq.AddReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.addContract(req));
    }

    /**
     * 修改合同接口
     */
    @PostMapping(value = "/confined/contract/edit")
    public ResponseModel editContract(@RequestBody AdminContractReq.EditReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.editContract(req));
    }

    /**
     * 解析合同文件接口
     */
    @PostMapping(value = "/confined/contract/resolve")
    @ResponseBody
    public ResponseModel resolveContract(@RequestParam("file") MultipartFile file) {
        //保存文件
        Boolean flag = saveFile(file);
        if (!flag) {
            return ResponseModel.fail();
        }
        File uploadFile = new File(FilePathUtils.getInputPath() + file.getOriginalFilename());
        if (!uploadFile.exists()) {
            return ResponseModel.fail();
        }
        Map<String, Object> data = new HashMap<>();
        try {
            String filePath = FilePathUtils.getInputPath() + file.getOriginalFilename();
            File     localFile     = new File(filePath);
            String   fileName = localFile.getName();
            Document doc      = new Document(filePath);
            data.put("name", file.getOriginalFilename().split("\\.")[0]);
            int length = WordProcessingUtils.getPageCount(doc);
            if (length == 0) {
                return ResponseModel.fail("内容为空");
            }
            data.put("length", length);
            WordProcessingUtils.removeWatermark(doc);
            //WordProcessingUtils.removeFistParagraph(doc);
            String dir     = filePath.split("\\.")[0];
            File   fileDir = new File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            doc.save(fileDir + File.separator + fileName);
            Document doc1 = new Document(fileDir + File.separator + fileName);
            WordProcessingUtils.splitWithJPEG(doc1, dir, 2);
            String downloadUrl = getReturnUrl(new File(fileDir + File.separator + fileName), file.getOriginalFilename());
            downloadUrl = fileFacade.watermark(downloadUrl);
            data.put("downloadUrl", downloadUrl);
            System.out.println("downloadUrl=" + downloadUrl);
            String firstImage = getReturnUrl(new File(fileDir + File.separator + "1.jpeg"), "1.jpeg");
            if (StringUtils.isNotEmpty(firstImage)) {
                firstImage = fileFacade.watermark(firstImage);
                data.put("firstImage", firstImage);
            }
            if (length > 1) {
                String secondImage = getReturnUrl(new File(fileDir + File.separator + "2.jpeg"), "2.jpeg");
                if (StringUtils.isNotEmpty(secondImage)) {
                    secondImage = fileFacade.watermark(secondImage);
                    data.put("images", firstImage + "," + secondImage);
                }
            } else {
                data.put("images", firstImage);
            }

            System.out.println(JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseModel.succ(data);
    }

    private String getReturnUrl(File uploadFile, String originalFilename) {
        String returnUrl = null;
        try {
            returnUrl = qiNiuUtil.uploadToPublic(uploadFile, "contract/" + DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT2) + "/" + originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(returnUrl)) {
            return null;
        }
        return "https://" + gatewayProperties.getDomain() + "/" + returnUrl;
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
     * 获取发票列表接口(分页)
     */
    @RequiresPermissions(value = {"operation_mgt_1_1_1", "operation_mgt_1_2_1"}, logical = Logical.OR)
    @GetMapping(value = "/confined/contract/invoice/list")
    public ResponseModel getInvoiceList(AdminContractReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getContractInvoiceList(req));
    }

    /**
     * 合同发票审核通过接口
     */
    @PostMapping(value = "/confined/contract/invoice/apply/approve")
    public ResponseModel invoiceApplyApprove(@RequestBody AdminContractReq.InvoiceVerifyReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        Assert.notNull(req.getInvoiceNo(), "invoiceNo 参数不能为空");
        Assert.notNull(req.getInvoiceImgUrl(), "invoiceImgUrl 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.invoiceApplyApprove(req));
    }

    /**
     * 合同发票审核拒绝接口
     */
    @PostMapping(value = "/confined/contract/invoice/apply/reject")
    public ResponseModel invoiceApplyReject(@RequestBody AdminContractReq.InvoiceVerifyReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        Assert.notNull(req.getReason(), "reason 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.invoiceApplyReject(req));
    }

    /**
     * 合同订单列表接口(分页)
     */
    @RequiresPermissions("operation_mgt_6_1_1")
    @GetMapping(value = "/confined/contract/order/list")
    public ResponseModel getContractOrderList(AdminContractOrderReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return contractFacade.getOrderList(req);
    }
}
