package com._360pai.core.provider.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataVo;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;
import com._360pai.core.service.enrolling.EnrollingActivityImportService;
import com._360pai.core.service.enrolling.EnrollingActivityRealImportService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：预招商Facade接口实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/2/14 14:16
 */
@Component
@Service(version = "1.0.0")
public class EnrollingImportProvider implements EnrollingImportFacade {

    private final Logger logger = LoggerFactory.getLogger(EnrollingImportProvider.class);


    @Autowired
    private EnrollingActivityImportService enrollingActivityImportService;

    @Autowired
    private EnrollingActivityRealImportService enrollingActivityRealImportService;


    @Override
    public ResponseModel getUserList(EnrollingImportReq.getUserListReq req) {


        return ResponseModel.succ(enrollingActivityImportService.getUserList(req));
    }

    @Override
    public ResponseModel getUploadActivityList(EnrollingImportReq.getUploadActivityListReq req) {

        if(StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        PageSerializable page = new PageSerializable();

        PageInfo info = enrollingActivityImportService.getUploadActivityList(req);

        List<EnrollingActivityImportDataListVo> listVoList =getFromList(info.getList());

        page.setTotal(info.getTotal());
        page.setList(listVoList);

        return ResponseModel.succ(page);
    }

    private List<EnrollingActivityImportDataListVo> getFromList(List<EnrollingActivityImportDataListVo> list) {

        for(EnrollingActivityImportDataListVo vo :list){
            vo.setImages(getImagesList(vo.getImage()));
            vo.setRejectFlag(StringUtils.isNotEmpty(vo.getRejectReason()));
            vo.setRefPrice(formatPriceInfo(vo.getRefPrice()));
            vo.setLocation(getLocationInfo(vo));


        }

        return list;
    }

    private String getLocationInfo(EnrollingActivityImportDataListVo vo) {
        StringBuffer sb = new StringBuffer();

        if(StringUtils.isNotEmpty(vo.getDebtorPro())){
            sb.append(vo.getDebtorPro());
        }
        if(StringUtils.isNotEmpty(vo.getDebtorCity())){
            sb.append(vo.getDebtorCity());
        }
        if(StringUtils.isNotEmpty(vo.getDebtorArea())){
            sb.append(vo.getDebtorArea());
        }
        return sb.toString();
    }

    private String formatPriceInfo(String refPrice) {

        try {
            BigDecimal num = new BigDecimal(refPrice);

            BigDecimal bigDecimalDivide = num.divide(new BigDecimal("10000"), 4, BigDecimal.ROUND_HALF_UP);
            double divide = bigDecimalDivide.doubleValue();

            NumberFormat number = NumberFormat.getNumberInstance();

            return number.format(divide)+"万元";
        }catch (Exception e){
            return refPrice;
        }

     }

    private List<String> getImagesList(String image) {

        List<String> imgList = new ArrayList<>();

        if(StringUtils.isEmpty(image)){
            return imgList;
        }
        JSONObject object = JSON.parseObject(image);

        JSONArray array = JSON.parseArray(object.getString("images"));

        for(Object s :array){
            imgList.add(s.toString());
        }


        return imgList;
    }

    @Override
    public ResponseModel uploadActivity(EnrollingImportReq.uploadActivityReq req) {

        //添加
        List<EnrollingActivityImportDataVo> activityList = getActivityList(req);


        enrollingActivityImportService.uploadActivity(activityList);

        return ResponseModel.succ();
    }


    private List<EnrollingActivityImportDataVo> getActivityList(EnrollingImportReq.uploadActivityReq req) {

        List<EnrollingActivityImportDataVo> activityList = new ArrayList<>();

        InputStream inputStream = new ByteArrayInputStream(req.getBytes());
        try {
            //获取工作簿
            OPCPackage opcPackage = OPCPackage.open(inputStream);

            XSSFWorkbook workbook=new XSSFWorkbook(opcPackage);

            if(workbook.getNumberOfSheets()<1){
                throw new BusinessException(ApiCallResult.TEMPLATE_ERROR);

            }
            //获取第一个工作表
            XSSFSheet hs=workbook.getSheetAt(1);

            //获取Sheet的第一个行号和最后一个行号
            int last=hs.getLastRowNum()+1;
            int first=5; //从第五行开始读取数据
            if(last>5000){
                throw new BusinessException(ApiCallResult.DATA_BIG);
            }
            //校验是否是对应的模板
            XSSFRow row1=hs.getRow(0);
            XSSFCell cell1=row1.getCell(0);

            if(!"长城资产特殊资产推介信息表".equals(cell1.getStringCellValue())){
                throw new BusinessException(ApiCallResult.TEMPLATE_ERROR);
            }

            //遍历获取单元格里的信息
            for (int i = first; i <last; i++) {
                XSSFRow row=hs.getRow(i);
                if(row==null){
                    continue;
                }
                int firstCellNum=row.getFirstCellNum();//获取所在行的第一个行号
                int lastCellNum=row.getLastCellNum();//获取所在行的最后一个行号

                EnrollingActivityImportDataVo vo = new EnrollingActivityImportDataVo();
                for (int j = firstCellNum; j <lastCellNum; j++) {
                    XSSFCell cell=row.getCell(j);
                    String value;
                    if(cell ==null){
                        continue;
                    }
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        if(j==12||j==21||j==22){     //基准日  评估基准日 评估报告出具日
                            value = getDateStr(cell);
                        }else {
                            cell.setCellType(CellType.STRING);
                            value=cell.getStringCellValue();
                            if(StringUtils.isNotEmpty(value)&&value.contains("E")){
                                DecimalFormat df = new DecimalFormat("0");
                                value = df.format(cell.getNumericCellValue());
                            }

                        }//数值类型的

                    }else {
                        cell.setCellType(CellType.STRING);
                        value=cell.getStringCellValue();
                    }

                    //参数赋值
                    vo.setPartyId(req.getUserId());
                    vo.setCreateTime(com._360pai.arch.common.utils.DateUtil.format(new Date(),com._360pai.arch.common.utils.DateUtil.NORM_DATETIME_PATTERN));
                    vo.setOperatorId(req.getOperatorId());

                    vo = setImportDataVo(vo,j,replaceBlank(value));
                 }
                if(StringUtils.isNotEmpty(vo.getSerialNumber())){

                    checkEmpty(vo);
                    activityList.add(vo);
                }
            }
        } catch (Exception e) {

            throw new RuntimeException(e);
         }



        return activityList;
    }

    private void checkEmpty(EnrollingActivityImportDataVo vo) {

        if(StringUtils.isEmpty(vo.getName())||StringUtils.isEmpty(vo.getDebtorPro())||StringUtils.isEmpty(vo.getDebtorCity())||StringUtils.isEmpty(vo.getOutstandingPrincipal())){
            throw new BusinessException(ApiCallResult.TEMPLATE_DATA_ERROR);
        }


        //校验金额是否为数字
        if(StringUtils.isNotEmpty(vo.getOutstandingPrincipal())){

            if(chekIsNotNum(vo.getOutstandingPrincipal())){
                throw new BusinessException(ApiCallResult.NOT_NUM);
            }

        }


        //校验金额是否为数字
        if(StringUtils.isNotEmpty(vo.getOutstandingInterest())){

            if(chekIsNotNum(vo.getOutstandingInterest())){
                throw new BusinessException(ApiCallResult.NOT_NUM_TWO);
            }
        }


    }

    private boolean chekIsNotNum(String num) {
        try {
            Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");

            return  !pattern.matcher(num).matches();

        }catch (Exception e){
            return false;
        }

    }

    public static class XSSFDateUtil extends DateUtil {
        protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
            return DateUtil.absoluteDay(cal, use1904windowing);
        }
    }

    private static String getDateStr(XSSFCell cell) {


        short format = cell.getCellStyle().getDataFormat();

        SimpleDateFormat sdf = new SimpleDateFormat(com._360pai.arch.common.utils.DateUtil.YYYY_DATE_PATTERN);
        if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
            sdf = new SimpleDateFormat("HH:mm");
        }


        String result;

        try {

            double value = cell.getNumericCellValue();

            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            if(date==null || "".equals(date)){
                return cell.getStringCellValue();
            }

            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return cell.getStringCellValue();
        }
        return result;
    }


    private  String replaceBlank(String str) {
        String dest = "";
        if (StringUtils.isNotEmpty(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    private EnrollingActivityImportDataVo setImportDataVo(EnrollingActivityImportDataVo vo,int j,String value) {

        switch(j){
            case 0:
                vo.setSerialNumber(value);
                break;
            case 1:
                vo.setBranchComName(value);
                break;
            case 2:
                vo.setName(value);
                break;
            case 3:
                vo.setDebtor(value);
                break;
            case 4:
                vo.setDebtorBus(value);
                break;
            case 5:
                vo.setBusStates(value);
                break;
            case 6:
                vo.setDebtorPro(value);
                break;
            case 7:
                vo.setDebtorCity(value);
                break;
            case 8:
                vo.setDebtorArea(value);
                break;
            case 9:
                vo.setPawnPro(value);
                break;
            case 10:
                vo.setPawnCity(value);
                break;
            case 11:
                vo.setPawnArea(value);
                break;
            case 12:
                vo.setBaseDate(value);
                break;
            case 13:
                vo.setOutstandingPrincipal(formatAmount(value));
                break;
            case 14:
                vo.setOutstandingInterest(formatAmount(value));
                break;
            case 15:
                vo.setDedit(formatAmount(value));
                break;
            case 16:
                vo.setOtherInfo(formatAmount(value));
                break;
            case 17:
                vo.setTotalDebt(formatAmount(value));
                break;
            case 18:
                vo.setAssetNum(value);
                break;
            case 19:
                vo.setAssetSource(value);
                break;
            case 20:
                vo.setAssetValue(value);
                break;
            case 21:
                vo.setAssetBaseDate(value);
                break;
            case 22:
                vo.setReportDate(value);
                break;
            case 23:
                vo.setAssureMeans(value);
                break;
            case 24:
                vo.setAssureName(value);
                break;
            case 25:
                vo.setSpecificAssureMeans(value);
                break;
            case 26:
                vo.setRealtyCharacter(value);
                break;
            case 27:
                vo.setLandArea(formatArea(value));
                break;
            case 28:
                vo.setBuildingArea(formatArea(value));
                break;
            case 29:
                vo.setPledgeSequence(value);
                break;
            case 30:
                vo.setGuaranteeThat(value);
                break;
            case 31:
                vo.setLitigationLink(value);
                break;
            case 32:
                vo.setHasGuarantee(value);
                break;
            case 33:
                vo.setIfGuarantee(value);
                break;
            case 34:
                vo.setProjectWindow(value);
                break;
            case 35:
                vo.setRemarks(value);
                break;
            case 36:
                vo.setAssetDesc(value);
                break;

            case 37:
                vo.setDisposalService(value);
                break;
            case 38:
                vo.setFundProvider(value);
                break;

            case 39:
                vo.setContactPerson(value);
                break;
            case 40:
                vo.setContactPhone(value);
                break;

        }
        return vo;
    }

    private String formatArea(String value) {

        try {
            if(value.substring(value.lastIndexOf(".")+1).length()>2){
                Double dd = Double.valueOf(value);

                DecimalFormat dec = new DecimalFormat("0.00");
                String s= dec.format(dd);
                return s;

            }

            return value;

        }catch (Exception e){
            return value;
        }

    }



    private String formatAmount(String value) {

        try {
            if(value.substring(value.lastIndexOf(".")+1).length()>4){
                Double dd = Double.valueOf(value);

                DecimalFormat dec = new DecimalFormat("0.0000");
                String s= dec.format(dd);
                return s;

            }

            return value;

        }catch (Exception e){
            return value;
        }

    }





    @Override
    public ResponseModel getUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {




        return enrollingActivityImportService.getUploadActivityDetails(req);
    }

    @Override
    public ResponseModel updateImages(EnrollingImportReq.updateImagesReq req) {

        EnrollingActivity enrollingActivity = new EnrollingActivity();

        enrollingActivity.setId(Integer.valueOf(req.getActivityId()));
        enrollingActivity.setExtra(getImages(req.getImages()));


        //更新物权导入的图片信息
        updateRealInfo(enrollingActivity);

        return enrollingActivityImportService.updateActivity(enrollingActivity);
    }

    private void updateRealInfo(EnrollingActivity enrollingActivity) {


        EnrollingActivityImportRealData realData = new EnrollingActivityImportRealData();
        realData.setExtra(enrollingActivity.getExtra().toJSONString());
        realData.setActivityId(enrollingActivity.getId());

        enrollingActivityRealImportService.updateImportActivityByActivityId(realData);

    }

    private JSONObject getImages(String[] images) {

        JSONArray jsonArray = new JSONArray();
        for (String img : images) {
            jsonArray.add(img);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("images", jsonArray);

        return jsonObject1;
    }



    @Override
    public ResponseModel submitActivity(EnrollingImportReq.activityIdReq req) {


        if("1".equals(req.getDeleteFlag())){
            return deleteActivity(req);
        }

        EnrollingActivity enrollingActivity = new EnrollingActivity();

        enrollingActivity.setId(Integer.valueOf(req.getActivityId()));
        enrollingActivity.setStatus(EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
        enrollingActivity.setExtra(getImages(req.getImages()));

        return enrollingActivityImportService.updateActivity(enrollingActivity);

     }

    private ResponseModel deleteActivity(EnrollingImportReq.activityIdReq req) {


        return enrollingActivityImportService.deleteImportActivity(req);
    }

    @Override
    public ResponseModel auditActivity(EnrollingImportReq.auditActivityReq req) {
         enrollingActivityImportService.batchAuditActivity(req);

        return ResponseModel.succ();
    }


    @Override
    public ResponseModel updateImportActivity(EnrollingImportReq.updateActivityReq req) {
        return enrollingActivityImportService.updateImportActivity(req);
    }


    @Override
    public ResponseModel saveFundServiceInfo(EnrollingImportReq.getFundServiceReq req) {
        return enrollingActivityImportService.saveFundServiceInfo(req);
    }
}
