package com._360pai.admin.utils;

import com._360pai.arch.common.utils.ExcelUtil;
import com._360pai.core.common.constants.EnrollingEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/12/5 15:51
 */
@Slf4j
public class EnrollingExcelUtils {

    public static void buildExcel(String type, Integer thirtyPartyStatus, List<Map<String, Object>> list, HttpServletRequest request, HttpServletResponse response) {
        //获取具体导出模板
        EnrollingEnum.ENROLLING_EXPORT_MODEL enrollingModel =
                EnrollingEnum.ENROLLING_EXPORT_MODEL.getFileNameByTypeStatus(type, thirtyPartyStatus);
        if(enrollingModel == null) {
            return;
        }

        String fileName = enrollingModel.getFileName();
        String[] columnNames = enrollingModel.getExportCloums();
        String[] keys = enrollingModel.getKeys();
        OutputStream outputStream = null;
        try {
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && !userAgent.contains("Edge") &&
                    (userAgent.contains("Firefox") || userAgent.contains("Chrome") || userAgent.contains("Safari"))) {
                fileName = new String((fileName).getBytes(), "ISO8859-1") + ".xls";
            } else {
                //其他浏览器
                fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
            }

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            Workbook work = ExcelUtil.createWorkBook(list, keys, columnNames, "预招商活动数据");
            outputStream = response.getOutputStream();
            work.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("导出数据异常{}", e.getMessage());
        } finally {
            try {
                assert outputStream != null;
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("关闭输出流异常{}", e.getMessage());
            }
        }
    }
}
