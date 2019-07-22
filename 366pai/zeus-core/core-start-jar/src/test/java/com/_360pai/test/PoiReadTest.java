package com._360pai.test;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/2/14 10:49
 */
public class PoiReadTest {


    public static void main(String[] args) {


        File file = new File("D:\\商品类型调整.xlsx");
        try {

            XSSFWorkbook workbook=new XSSFWorkbook(file);


            //获取第一个工作表
            XSSFSheet hs=workbook.getSheetAt(0);

            //获取Sheet的第一个行号和最后一个行号
            int last=hs.getLastRowNum()+1;
            int first=2; //从第五行开始读取数据

            //遍历获取单元格里的信息
            for (int i = first; i <last; i++) {

                StringBuffer data = new StringBuffer();

                StringBuffer activity = new StringBuffer();

                XSSFRow row=hs.getRow(i);


                data.append("update  zeus.enrolling_activity_import_data  set specific_assure_means = '");
                data.append(row.getCell(1));
                data.append("' where activity_id = ( select id from   zeus.enrolling_activity  where code = '");
                data.append(row.getCell(0));
                data.append("');");


                activity.append("update zeus.enrolling_activity  set guarantee = '1' ,bus_type_name = '");
                activity.append(row.getCell(1).toString().replace("、",","));
                activity.append("' where code = '");
                activity.append(row.getCell(0));
                activity.append("';");



                System.out.println(data.toString());
                System.out.println(activity.toString());


            }
        } catch (Exception e) {

            throw new RuntimeException(e);
        }









    }

}



