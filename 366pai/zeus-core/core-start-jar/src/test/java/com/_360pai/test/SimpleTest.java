package com._360pai.test;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.ExcelUtil;
import com._360pai.arch.common.utils.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.Collection;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.output.SAXOutputter;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xdrodger
 * @Title: SimpleTest
 * @ProjectName zeus
 * @Description:
 * @date 15/09/2018 11:55
 */
public class SimpleTest {
    @Test
    public void testS() {
        String id = "1";
        SkySea skySea = new SkySea();

        //setValue(skySea, "id", id);
        setValue(skySea, "id", "1");
        setValue(skySea, "id", 2);
        setValue(skySea, "id", 3L);
        setValue(skySea, "id2", "1");
        setValue(skySea, "id2", 2);
        setValue(skySea, "id2", 3L);
        setValue(skySea, "id3", "1");
        setValue(skySea, "id3", 2);
        setValue(skySea, "id3", 3L);

        System.out.println(skySea.getId());
        System.out.println(skySea.getId2());
        System.out.println(skySea.getId3());

        String str = "rwerqer";
        System.out.println(str.substring(0,2) + "****" + str.substring(str.length()-2));
        String str1 = "932581088@qq.com";

        System.out.println(ToolUtil.maskContact(str));
        System.out.println(ToolUtil.maskContact(str1));
        System.out.println(ToolUtil.maskContact("123@360pai.com"));
        System.out.println("--");
    }

    private void setValue(Object dto, String name, Object value) {
        Method[] m = dto.getClass().getMethods();
        try {
            for (int i = 0; i < m.length; i++) {
                if (("set" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                    Parameter[] parameters = m[i].getParameters();
                    Class c = parameters[0].getType();
                    System.out.println(parameters[0].getParameterizedType().getTypeName());
                    System.out.println(parameters[0].getType().getTypeName());
                    if (c.equals(value.getClass())) {
                        m[i].invoke(dto,  value);
                    } else {
                        if (value.getClass().equals(String.class)) {
                            if (c.equals(Integer.class)) {
                                m[i].invoke(dto, Integer.parseInt((String) value));
                            } else if (c.equals(Long.class)) {
                                m[i].invoke(dto, Long.parseLong((String) value));
                            }
                        } else if (value.getClass().equals(Integer.class)) {
                            if (c.equals(String.class)) {
                                m[i].invoke(dto, String.valueOf(value));
                            } else if (c.equals(Long.class)) {
                                m[i].invoke(dto, ((Integer) value).longValue());
                            }
                        } else if (value.getClass().equals(Long.class)) {
                            if (c.equals(String.class)) {
                                m[i].invoke(dto, String.valueOf(value));
                            } else if (c.equals(Integer.class)) {
                                m[i].invoke(dto, ((Long) value).intValue());
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class SkySea {
        private Integer id;

        private String id2;

        private Long id3;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getId2() {
            return id2;
        }

        public void setId2(String id2) {
            this.id2 = id2;
        }

        public Long getId3() {
            return id3;
        }

        public void setId3(Long id3) {
            this.id3 = id3;
        }
    }


    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").substring(0,24));
    }


    @Test
    public void testStr() {
        System.out.println("121".equals(null));

        Set<String> set = Collections.EMPTY_SET;
        System.out.println(JSON.toJSONString(set));

        String str = "auction_be_about_to_begin_2322";
        String[] arr = str.split("_");
        System.out.println(arr[arr.length-1]);

        System.out.println(isContainChinese("活动不存在asasda"));
        System.out.println("--");




        String keys = "北京市,天津市,河北省,山西省,内蒙古自治区,辽宁省,吉林省,黑龙江省,上海市,江苏省,浙江省,安徽省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省,广西壮族自治区,海南省,重庆市,四川省,贵州省,云南省,西藏自治区,陕西省,甘肃省,青海省,宁夏回族自治区,新疆维吾尔自治区,香港特别行政区,澳门特别行政区,台湾省,北京,天津,河北,山西,内蒙古自治区,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西壮族自治区,海南,重庆,四川,贵州,云南,西藏自治区,陕西,甘肃,青海,宁夏回族自治区,新疆维吾尔自治区,香港特别行政区,澳门特别行政区,台湾,北京,天津,河北,山西,内蒙古自治区,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西,海南,重庆,四川,贵州,云南,西藏,陕西,甘肃,青海,宁夏,新疆,香港,澳门,台湾";

        Set<String> sets = new HashSet<>(Arrays.asList(keys.split(",")));

        String str1 = "江苏立科律师事务所";
        for (String key : sets) {
            if (str1.contains(key)) {
                System.out.println(key);
            }
        }
        System.out.println("--");
    }

    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public  boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Test
    public void  testYear() {
        try {
            Date date = DateUtil.parse("2019-03-07 12:00:00", DateUtil.NORM_DATETIME_PATTERN);
            int y = DateUtil.getYearMargin(date);
            System.out.println(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void readExcel() {
        String filePath = "/data/file/input/拍卖活动信息汇总.xls";
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
            List<Integer> rowIndexs = new ArrayList<>();
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
                //System.out.println(cityName);
                if ("上海市".equals(cityName)) {
                    rowIndexs.add(r);
                    //System.out.println("remove" + cityName);
                    needToRemoveList.add(row);
                    //sheet.shiftRows(r + 1, sheet.getLastRowNum(),-1);
                    //ExcelUtil.removeRow((HSSFSheet) sheet, r);

                } else {
                    cell.setCellValue("北京市");
                    System.out.println(cityName);
                }
            }
            for (Row row : needToRemoveList) {
                sheet.removeRow(row);
            }

            //for (Integer rowIndex : rowIndexs) {
            //    ExcelUtil.removeRow((HSSFSheet) sheet, rowIndex);
            //}
            FileOutputStream os = new FileOutputStream("/data/file/input/拍卖活动信息汇总-3.xls");
            wb.write(os);
            os.close();
            fis.close();
            System.out.println("--");



        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
