package com._360pai.core.provider.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.EnrollingRealImportFacade;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportRealDataVo;
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
 * 描述：物权导入facade实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/2/14 14:16
 */
@Component
@Service(version = "1.0.0")
public class EnrollingRealImportProvider implements EnrollingRealImportFacade {

    private final Logger logger = LoggerFactory.getLogger(EnrollingRealImportProvider.class);


    @Autowired
    private EnrollingActivityRealImportService enrollingActivityRealImportService;





    @Override
    public ResponseModel getUploadRealActivityList(EnrollingImportReq.getUploadActivityListReq req) {

        if(StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        PageSerializable page = new PageSerializable();

        PageInfo info = enrollingActivityRealImportService.getUploadRealActivityList(req);

        List<EnrollingActivityImportDataListVo> listVoList =getFromList(info.getList());

        page.setTotal(info.getTotal());
        page.setList(listVoList);

        return ResponseModel.succ(page);
    }

    private List<EnrollingActivityImportDataListVo> getFromList(List<EnrollingActivityImportDataListVo> list) {

        for(EnrollingActivityImportDataListVo vo :list){
            vo.setImages(getImagesList(vo.getImage()));
            vo.setRejectFlag(StringUtils.isNotEmpty(vo.getRejectReason()));
            vo.setRefPrice(vo.getRefPrice()==null?"暂无":vo.getRefPrice()+"元");
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

        //解析Excel参数
        List<EnrollingActivityImportRealDataVo> activityList = getActivityList(req);

        if(activityList.size()<1){
            return ResponseModel.fail("导入内容为空！");
        }


        enrollingActivityRealImportService.uploadActivity(activityList);

        return ResponseModel.succ();
    }


    private List<EnrollingActivityImportRealDataVo> getActivityList(EnrollingImportReq.uploadActivityReq req) {

        List<EnrollingActivityImportRealDataVo> activityList = new ArrayList<>();

        InputStream inputStream = new ByteArrayInputStream(req.getBytes());
        try {
            //获取工作簿
            OPCPackage opcPackage = OPCPackage.open(inputStream);
            XSSFWorkbook workbook=new XSSFWorkbook(opcPackage);

            if(workbook.getNumberOfSheets()<0){
                throw new BusinessException(ApiCallResult.TEMPLATE_ERROR);

            }
            //获取第一个工作表
            XSSFSheet hs=workbook.getSheetAt(0);

            //获取Sheet的第一个行号和最后一个行号
            int last=hs.getLastRowNum()+1;
            int first=3; //从第五行开始读取数据
            if(last>2000){
                throw new BusinessException(ApiCallResult.DATA_BIG);
            }

            //遍历获取单元格里的信息
            for (int i = first; i <last; i++) {
                XSSFRow row=hs.getRow(i);
                if(row==null){
                    continue;
                }
                int firstCellNum=row.getFirstCellNum();//获取所在行的第一个行号
                int lastCellNum=row.getLastCellNum();//获取所在行的最后一个行号
                EnrollingActivityImportRealDataVo vo = new EnrollingActivityImportRealDataVo();
                for (int j = firstCellNum; j <lastCellNum; j++) {
                    XSSFCell cell=row.getCell(j);
                    String value;
                    if(cell ==null){
                        continue;
                    }

                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        if(j==12||j==17||j==31||j==45){     //基准日  评估基准日 评估报告出具日
                            value = getDateStr(cell);
                        }else {
                            cell.setCellType(CellType.STRING);
                            value=cell.getStringCellValue();
                            if(StringUtils.isNotEmpty(value)&&value.contains("E")){
                                DecimalFormat df = new DecimalFormat("0");
                                value = df.format(cell.getNumericCellValue());
                            }
                        }
                    }else {
                        cell.setCellType(CellType.STRING);
                        value=cell.getStringCellValue();
                    }




                    //参数赋值
                    vo.setPartyId(req.getUserId());
                    vo.setCreateTime(new Date());
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

    private void checkEmpty(EnrollingActivityImportRealDataVo vo) {

        if(StringUtils.isEmpty(vo.getName())||StringUtils.isEmpty(vo.getPawnPro())||StringUtils.isEmpty(vo.getPawnCity())||StringUtils.isEmpty(vo.getBusTypeName())){
            throw new BusinessException(ApiCallResult.TEMPLATE_DATA_ERROR);
        }

        //市场参考价数字校验
        if(StringUtils.isNotEmpty(vo.getRefPrice())){
            if(checkIsNotNum(vo.getRefPrice())){

                throw new BusinessException("市场参考价必须为数字！");

            }
        }

    }

    private boolean checkIsNotNum(String num) {
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

            Date date = DateUtil.getJavaDate(value);
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

    private EnrollingActivityImportRealDataVo setImportDataVo(EnrollingActivityImportRealDataVo vo, int j, String value) {

        switch(j){
            case 0:
                vo.setSerialNumber(value);
                break;
            case 1:
                vo.setName(value);
                break;
            case 2:
                vo.setPawnPro(value);
                break;
            case 3:
                vo.setPawnCity(value);
                break;
            case 4:
                vo.setRefPrice(value.replace("元",""));
                break;
            case 5:
                vo.setBusTypeName(value);
                break;
            case 6:
                vo.setPowerType(value);
                break;
            case 7:
                vo.setOwnedCondition(value);
                break;
            case 8:
                vo.setOwnedNum(value);
                break;
            case 9:
                vo.setOwnedSource(value);
                break;
            case 10:
                vo.setRealLicense(value);
                break;
            case 11:
                vo.setLandLicense(value);
                break;
            case 12:
                vo.setRecordDate(value);
                break;
            case 13:
                vo.setBuildArea(value);
                break;
            case 14:
                vo.setInsideSpace(value);
                break;
            case 15:
                vo.setHousingType(value);
                break;
            case 16:
                vo.setDecorate(value);
                break;
            case 17:
                vo.setCompletionDate(value);
                break;
            case 18:
                vo.setTotalNum(value);
                break;
            case 19:
                vo.setStoryNum(value);
                break;
            case 20:
                vo.setBearWay(value);
                break;
            case 21:
                vo.setPublicOwe(value);
                break;
            case 22:
                vo.setTenementOwe(value);
                break;
            case 23:
                vo.setVatAddition(value);
                break;
            case 24:
                vo.setLandVat(value);
                break;
            case 25:
                vo.setPersonalVat(value);
                break;
            case 26:
                vo.setRemark(value);
                break;
            case 27:
                vo.setLandArea(value);
                break;
            case 28:
                vo.setSharingArea(value);
                break;
            case 29:
                vo.setLandUse(value);
                break;
            case 30:
                vo.setUseSource(value);
                break;
            case 31:
                vo.setUsePeriod(value);
                break;
            case 32:
                vo.setIfMortgage(value);
                break;
            case 33:
                vo.setHolder(value);
                break;
            case 34:
                vo.setRightType(value);
                break;
            case 35:
                vo.setOtherEquity(value);
                break;
            case 36:
                vo.setRightValue(value);
                break;

            case 37:
                vo.setUsageType(value);
                break;
            case 38:
                vo.setRegistration(value);
                break;

            case 39:
                vo.setRegistrationNum(value);
                break;
            case 40:
                vo.setLeaseCondition(value);
                break;
            case 41:
                vo.setLeaseDeadline(value);
                break;
            case 42:
                vo.setLeaseUser(value);
                break;
            case 43:
                vo.setLeaseDeposit(value);
                break;
            case 44:
                vo.setLeaseRent(value);
                break;
            case 45:
                vo.setLeasePayTo(value);
                break;
            case 46:
                vo.setRelationship(value);
                break;
            case 47:
                vo.setIndoorGoods(value);
                break;
            case 48:
                vo.setLeasePhone(value);
                break;
            case 49:
                vo.setLeaseRemark(value);
                break;
            case 50:
                vo.setUnlimited(value);
                break;
            case 51:
                vo.setLimitOne(value);
                break;



            case 52:
                vo.setLimitType(value);
                break;

            case 53:
                vo.setLimitOther(value);
                break;
            case 54:
                vo.setLimitRemark(value);
                break;
            case 55:
                vo.setFundProvider(value);
                break;
            case 56:
                vo.setContactName(value);
                break;
            case 57:
                vo.setContactPhone(value);
                break;


        }
        return vo;
    }











    @Override
    public ResponseModel getRealUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {




        return enrollingActivityRealImportService.getRealUploadActivityDetails(req);
    }

    @Override
    public ResponseModel updateImages(EnrollingImportReq.updateImagesReq req) {

        EnrollingActivity enrollingActivity = new EnrollingActivity();

        enrollingActivity.setId(Integer.valueOf(req.getActivityId()));
        enrollingActivity.setExtra(getImages(req.getImages()));


        return enrollingActivityRealImportService.updateRealActivity(enrollingActivity);
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


        //删除导入信息
        if("1".equals(req.getDeleteFlag())){
            return deleteRealActivity(req);
        }

        EnrollingActivity enrollingActivity = new EnrollingActivity();

        enrollingActivity.setId(Integer.valueOf(req.getActivityId()));
        enrollingActivity.setStatus(EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
        enrollingActivity.setExtra(getImages(req.getImages()));

        updateRealInfo(enrollingActivity);

        return enrollingActivityRealImportService.updateRealActivity(enrollingActivity);

     }


    private void updateRealInfo(EnrollingActivity enrollingActivity) {


        EnrollingActivityImportRealData realData = new EnrollingActivityImportRealData();
        realData.setExtra(enrollingActivity.getExtra()!=null?enrollingActivity.getExtra().toJSONString():null);
        realData.setActivityId(enrollingActivity.getId());

        enrollingActivityRealImportService.updateImportActivityByActivityId(realData);

    }



    private ResponseModel deleteRealActivity(EnrollingImportReq.activityIdReq req) {

        EnrollingActivityImportRealData data = new EnrollingActivityImportRealData();
        data.setActivityId(Integer.valueOf(req.getActivityId()));
        data.setDeleteFlag(true);
        enrollingActivityRealImportService.updateImportActivityByActivityId(data);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel auditRealActivity(EnrollingImportReq.auditActivityReq req) {
        enrollingActivityRealImportService.batchAuditActivity(req);

        return ResponseModel.succ();
    }


    @Override
    public ResponseModel updateImportActivity(EnrollingImportReq.updateRealActivityReq req) {

        //校验市场参考价是否为数字
        if(StringUtils.isNotEmpty(req.getRefPrice())){
            if(checkIsNotNum(req.getRefPrice())){
                return ResponseModel.fail("市场参考价必须为数字！");
            }
        }


        return enrollingActivityRealImportService.updateRealImportActivity(req);
    }



}
