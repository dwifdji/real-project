package com.winback.admin.controller.assistant;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.AdminReq;
import com.winback.arch.common.AppletReq;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.ExcelUtil;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.core.commons.SystemDict;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade.assistant.AssistantFacade;
import com.winback.core.facade.assistant.req.AdminAssistantReq;
import com.winback.core.facade.contract.ContractFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: AssistantController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:37
 */
@Slf4j
@RestController
@RequestMapping(value = "/open/assistant", produces = "application/json;charset=UTF-8")
public class AssistantController {
    @Reference(version = "1.0.0")
    private AssistantFacade assistantFacade;
    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;
    @Resource
    private RedisCachemanager redisCacheManager;
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

    /**
     * 数据字典接口
     */
    @GetMapping(value = "/systemDict")
    public ResponseModel systemDict() {
        Map<Object, Map<Object, Object>> map = SystemDict.instance.getSystemDict();
        return ResponseModel.succ(map);
    }

    /**
     * 获取七牛token信息
     */
    @GetMapping(value = "/getQiniuToken")
    public ResponseModel getQiniuToken(@RequestParam(value = "fileType", required = false, defaultValue = "") String fileType) {
        return assistantFacade.getQiNiuToken(fileType);
    }

    /**
     * 获取所有城市接口
     */
    @GetMapping(value = "/getAllCities")
    public ResponseModel getAllCities(@RequestParam(value = "type", required = false, defaultValue = "0") String type) {
        return assistantFacade.getAllCities(type);
    }
    /**
     * 获取所有省份接口
     */
    @GetMapping(value = "/getAllProvinces")
    public ResponseModel getAllProvinces() {
        return ResponseModel.succ(assistantFacade.getAllProvinces());
    }

    /**
     * 获取省份下城市接口
     */
    @GetMapping(value = "/getCitiesByProvince")
    public ResponseModel getCitiesByProvince(String provinceCode) {
        return ResponseModel.succ(assistantFacade.getCitiesByProvinceCode(provinceCode));
    }

    /**
     * 获取城市下区县接口
     */
    @GetMapping(value = "/getAreasByCity")
    public ResponseModel getAreasByCity(String cityCode) {
        return ResponseModel.succ(assistantFacade.getAreasByCityCode(cityCode));
    }

    /**
     * 获取所有合同大类类型列表接口
     */
    @GetMapping(value = "/getContractBigTypeList")
    public ResponseModel getContractBigTypeList(AdminReq req) {
        return ResponseModel.succ(contractFacade.getBackContractBigTypeList());
    }

    /**
     * 合同文件导入接口
     */
    @PostMapping(value = "/importContractFileToDb")
    public ResponseModel importContractFileToDb(@RequestBody AdminAssistantReq.ImportContractFileToDbReq req) {
        Assert.notNull(req.getFilePath(), "filePath 参数不能为空");
        assistantFacade.importContractFileToDb(req.getFilePath());
        return ResponseModel.succ();
    }

    /**
     * 重置合同图片接口
     */
    @PostMapping(value = "/resetContractImage")
    public ResponseModel resetContractImage(@RequestBody AdminAssistantReq.ImportContractFileToDbReq req) {
        Assert.notNull(req.getFilePath(), "filePath 参数不能为空");
        assistantFacade.resetContractImage(req.getFilePath());
        return ResponseModel.succ();
    }

    /**
     * 重置合同名称接口
     */
    @PostMapping(value = "/resetContractName")
    public ResponseModel resetContractName(@RequestBody AdminAssistantReq.ImportContractFileToDbReq req) {
        Assert.notNull(req.getFilePath(), "filePath 参数不能为空");
        assistantFacade.resetContractName(req.getFilePath());
        return ResponseModel.succ();
    }


    /**
     * 重置合同金额
     */
    @PostMapping(value = "/resetContractAmount")
    public ResponseModel resetContractAmount(@RequestParam("file") MultipartFile multfile) {
        if(multfile==null){
            return ResponseModel.fail("请选择导入文件！");
        }
        byte[] buffer;
        try {
            buffer = multfile.getBytes();
        } catch (IOException e) {
            return ResponseModel.fail("获取文件错误！");
        }
        InputStream inputStream = new ByteArrayInputStream(buffer);
        try {
            //获取工作簿
            OPCPackage opcPackage = OPCPackage.open(inputStream);
            XSSFWorkbook workbook=new XSSFWorkbook(opcPackage);
            if(workbook.getNumberOfSheets()<1){
                return ResponseModel.fail("获取文件错误！");
            }
            List<List<Object>> rowList = ExcelUtil.readExcelValue(workbook);
            return ResponseModel.succ(contractFacade.resetContractAmount(rowList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail("解析文件错误！");
        }
    }

    /**
     * 获取案由大类列表接口
     */
    @GetMapping(value = "/getCaseBigBriefList")
    public ResponseModel getCaseBigBriefList(AdminReq req) {
        return ResponseModel.succ(caseFacade.getBackgroundCaseBigBriefList(false));
    }

    /**
     * 获取案由大类列表接口
     */
    @GetMapping(value = "/getAllCaseBigBriefList")
    public ResponseModel getAllCaseBigBriefList(AdminReq req) {
        return ResponseModel.succ(caseFacade.getBackgroundCaseBigBriefList(true));
    }
}
