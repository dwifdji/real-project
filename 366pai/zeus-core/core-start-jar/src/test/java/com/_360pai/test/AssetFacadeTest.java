package com._360pai.test;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.ExcelUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.condition.asset.AssetDataCopyCondition;
import com._360pai.core.dao.asset.AssetCopyDao;
import com._360pai.core.dao.asset.AssetDataCopyDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.TAssetCateGoryFacade;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.model.asset.AssetCopy;
import com._360pai.core.model.asset.AssetDataCopy;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.NotifyPartyActivityService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssetFacadeTest extends TestBase {
    private static Logger logger = LoggerFactory.getLogger(AssetFacadeTest.class);
    @Resource
    private AssetFacade assetFacade;

    @Resource
    private RedisCachemanager redisCachemanager;

    @Autowired
    private ActivityFacade activityFacade;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetCopyDao assetCopyDao;

    @Autowired
    private AssetDataCopyDao assetDataCopyDao;

    @Autowired
    private TAssetCateGoryFacade tAssetCateGoryFacade;

    private static SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);

    @Autowired
    private NotifyPartyActivityService notifyPartyActivityService;

    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private ProvinceDao provinceDao;

    @Test
    public void testAssetFacade() {
        PageInfo pageInfo = assetFacade.getAllAssetByPage(1, 20);
        System.out.println(JSONObject.toJSONString(pageInfo));
    }

    @Test
    public void testRedis() {
        redisCachemanager.set("ruqing333", "28", 30);
        System.out.println("ruqing333====" + redisCachemanager.get("ruqing333"));
    }

    @Test
    public void testSignDelegationAgreementCallBack() {
        try {
            ActivityReq.SignDelegationAgreementCallBackReq req = new ActivityReq.SignDelegationAgreementCallBackReq();
            req.setActivityId(1241);
            req.setContractId("num201904111114027358");
            req.setHasSuccess(true);
            ActivityResp resp = activityFacade.signDelegationAgreementCallBack(req);
            System.out.println(JSONObject.toJSONString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSignAdditionalAgreementCallBack() {
        try {
            ActivityReq.SignAdditionalAgreementCallBackReq req = new ActivityReq.SignAdditionalAgreementCallBackReq();
            req.setActivityId(438);
            req.setContractId("num201810191525286635");
            req.setHasSuccess(true);
            ActivityResp resp = activityFacade.signAdditionalAgreementCallBack(req);
            System.out.println(JSONObject.toJSONString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCategoryOptionNameByCategoryId() {
        String name = tAssetCateGoryFacade.getCategoryOptionNameByCategoryId(2);
        System.out.printf("|||" + name);
    }

    @Test
    public void assetDataCopy() {

        AssetCopy asset = assetCopyDao.selectById(681);


        AssetReq.AddReq req = new AssetReq.AddReq();
        req.setAssetId(asset.getId());
        JSONObject object = (JSONObject) assetFacade.makeOldData(req);
        JSONObject files = object.getJSONObject("files");
        AssetDataCopyCondition copyCondition = new AssetDataCopyCondition();
        copyCondition.setAssetId(asset.getId());
        AssetDataCopy assetDataCopy = assetDataCopyDao.selectOneResult(copyCondition);
        assetDataCopy.setContent(files);

        int i = assetDataCopyDao.updateById(assetDataCopy);
        if (i <= 0) {
            logger.info("标的数据为：", JSON.toJSONString(asset));
            logger.info("标的data数据为：", JSON.toJSONString(assetDataCopy));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "更改失败");
        }

    }

    @Test
    public void testAuctionBeAboutToBeginNotify() {
        try {
            notifyPartyActivityService.auctionBeAboutToBeginNotify(566);
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuctionBeAboutToEndNotify() {
        try {
            notifyPartyActivityService.auctionBeAboutToEndNotify(566);
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMqSender() {
        try {
            long timeout = 35;
            mqSender.auctionOrderPayTimeoutEnqueue("599", timeout);
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void uno() {

        ActivityReq.ActivityId req = new ActivityReq.ActivityId();

        req.setActivityId(475);


         activityFacade.unionData(req);
    }


    @Test
    public void readExcel() {
        String filePath = "/data/file/input/拍卖活动信息汇总.xls";
        Set<Integer> provinceIds = new HashSet<>();
        provinceIds.add(9);
        provinceIds.add(10);
        provinceIds.add(11);
        provinceIds.add(12);
        try {
            File file = new File(filePath);
            String fileName = file.getName();//获取文件名
            InputStream fis = new FileInputStream(file);
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (ExcelUtil.isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(fis);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(fis);
            }
            // 得到第一个shell
            Sheet sheet = wb.getSheetAt(0);
            // 得到Excel的行数
            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCells = 0;
            // 得到Excel的列数(前提是有行数)
            if (totalRows > 1 && sheet.getRow(0) != null) {
                totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            }
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
            // 循环Excel行数
            List<Row> needToRemoveList = new ArrayList<>();
            for (int r = 0; r < totalRows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Cell cell = row.getCell(6);
                if (cell == null) {
                    continue;
                }
                String cityName = ExcelUtil.getStringType(cell, evaluator);
                City city = cityDao.findByName(cityName);
                if (city != null) {
                    System.out.println(city.getName() + "" + city.getProvinceId());
                    if (!provinceIds.contains(city.getProvinceId())) {
                        System.out.println("remove" + city.getName() + "" + city.getProvinceId());
                        //ExcelUtil.removeRow((HSSFSheet) sheet, r);
                        needToRemoveList.add(row);
                    } else {
                        cell.setCellValue("" + provinceDao.getName(city.getProvinceId()));
                    }
                }

            }
            for (Row row : needToRemoveList) {
                sheet.removeRow(row);
            }
            FileOutputStream os = new FileOutputStream("/data/file/input/拍卖活动信息汇总-3.xls");
            wb.write(os);
            os.close();
            fis.close();
            System.out.println("--");



        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    public void readExcel2() {
        String filePath = "/data/file/input/债权招商.xls";
        Set<Integer> provinceIds = new HashSet<>();
        provinceIds.add(9);
        provinceIds.add(10);
        provinceIds.add(11);
        provinceIds.add(12);
        try {
            File file = new File(filePath);
            String fileName = file.getName();//获取文件名
            InputStream fis = new FileInputStream(file);
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (ExcelUtil.isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(fis);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(fis);
            }
            // 得到第一个shell
            Sheet sheet = wb.getSheetAt(0);
            // 得到Excel的行数
            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCells = 0;
            // 得到Excel的列数(前提是有行数)
            if (totalRows > 1 && sheet.getRow(0) != null) {
                totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            }
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
            // 循环Excel行数
            List<Row> needToRemoveList = new ArrayList<>();
            for (int r = 0; r < totalRows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Cell cell = row.getCell(3);
                if (cell == null) {
                    continue;
                }
                String cityName = ExcelUtil.getStringType(cell, evaluator);
                City city = cityDao.findByName(cityName);
                if (city != null) {
                    System.out.println(city.getName() + "" + city.getProvinceId());
                    if (!provinceIds.contains(city.getProvinceId())) {
                        System.out.println("remove" + city.getName() + "" + city.getProvinceId());
                        //ExcelUtil.removeRow((HSSFSheet) sheet, r);
                        needToRemoveList.add(row);
                    } else {
                        cell.setCellValue("" + provinceDao.getName(city.getProvinceId()));
                    }
                }

            }
            for (Row row : needToRemoveList) {
                sheet.removeRow(row);
            }
            FileOutputStream os = new FileOutputStream("//data/file/input/债权招商-3.xls");
            wb.write(os);
            os.close();
            fis.close();
            System.out.println("--");



        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
