package com.winback.core.service.assistant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aspose.words.Document;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.core.file.FilePathUtils;
import com.winback.arch.core.file.QiNiuUtil;
import com.winback.arch.core.file.watermark.WordProcessingUtils;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.core.dao.contract.TContractBigTypeDao;
import com.winback.core.dao.contract.TContractDao;
import com.winback.core.dao.contract.TContractTypeDao;
import com.winback.core.dao.contract.TContractTypeMapDao;
import com.winback.core.model.contract.TContract;
import com.winback.core.model.contract.TContractBigType;
import com.winback.core.model.contract.TContractType;
import com.winback.core.service.assistant.DataMigrationService;
import com.winback.core.service.assistant.TFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * @author xdrodger
 * @Title: DataMigrationServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/2/20 15:05
 */
@Slf4j
@Service
public class DataMigrationServiceImpl implements DataMigrationService {
    @Autowired
    private TContractTypeDao contractTypeDao;
    @Autowired
    private TContractBigTypeDao contractBigTypeDao;
    @Autowired
    private TContractDao contractDao;
    @Autowired
    private TContractTypeMapDao contractTypeMapDao;
    @Autowired
    private TFileService tFileService;
    @Autowired
    private QiNiuUtil qiNiuUtil;
    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public void importContractFileToDb(String filePath) {
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            return;
        }
        File[] files = fileDir.listFiles();
        List<Map<String, Object>> importFiles = new ArrayList<>();
        for (File bigTypeDir : files) {
            if (!bigTypeDir.isDirectory()) {
                continue;
            }
            String bigTypeName = bigTypeDir.getName();
            TContractBigType contractBigType;
            if (!contractBigTypeDao.isExist(bigTypeName)) {
                contractBigType = contractBigTypeDao.save(bigTypeName);
            } else {
                contractBigType = contractBigTypeDao.findBy(bigTypeName);
            }
            log.info("bigTypeName={}/{}", bigTypeName, bigTypeDir.isDirectory());
            for (File typeDir : bigTypeDir.listFiles()) {
                if (!typeDir.isDirectory()) {
                    continue;
                }
                String typeName = typeDir.getName();
                TContractType contractType;
                if (!contractTypeDao.isExist(typeName)) {
                    contractType = contractTypeDao.save(typeName, contractBigType.getId());
                } else {
                    contractType = contractTypeDao.findBy(typeName);
                }
                log.info("-----typeName={}/{}" , typeName, typeDir.isDirectory());
                for (File contractFile : typeDir.listFiles()) {
                    if (contractFile.isDirectory()) {
                        continue;
                    }
                    String contractName = contractFile.getName().split("\\.")[0];
                    log.info("-----contractName={}/{}" , contractName, typeDir.isDirectory());
                    if (contractDao.isExists(contractName)) {
                        continue;
                    }
                    Map<String, Object> importFile = new HashMap<>();
                    importFile.put("contractTypeId", contractType.getId());
                    importFile.put("contractFilePath", contractFile.getPath());
                    importFiles.add(importFile);
                }
            }
            log.info("-------");
        }
        log.info("-----");
        List<Map<String, Object>> itemList = importFiles;
        List<Map<String, Object>> successList = new ArrayList<>();
        List<Map<String, Object>> failList = new ArrayList<>();
        int i = 0;
        for (Map<String, Object> item : itemList) {
            if (i  > 500) {
                break;
            }
            String contractFilePath = (String) item.get("contractFilePath");
            Map<String, Object> result = uploadContract(contractFilePath);
            if (result.isEmpty()) {
                log.error("解析失败，contractFilePath={}", contractFilePath);
                failList.add(item);
                continue;
            }
            i ++;
            item.putAll(result);
            successList.add(item);
        }
        log.info("successList={}", JSON.toJSONString(successList));
        log.info("itemList size={}", itemList.size());
        saveContractToDb(successList);
        System.out.println("--");


        //Map<String, Object> result = resolveContract("/Users/xdrodger/tmp/soyin/contract/企业发展/增资扩股协议/公司增资扩股协议书范本.doc");
        //System.out.println(JSON.toJSONString(result));

        //Map<String, Object> result = uploadContract("/Users/xdrodger/tmp/soyin/contract2/企业发展/增资扩股协议/公司增资扩股协议书范本.doc");
        //result.put("contractTypeId", 675);
        //List<Map<String, Object>> tmpList = new ArrayList<>();
        //tmpList.add(result);
        //saveContractToDb(tmpList);
        //System.out.println(JSON.toJSONString(result));
        System.out.println("--");
    }

    @Override
    public void findWrongContract() {
        String str = readJsonFile("/data/file/input/wrongdoc.json");
        JSONArray wrongDocs = JSON.parseArray(str);
        List<String> wrongDocIds = new ArrayList<>();
        for (Object o : wrongDocs) {
            JSONObject wrongDoc = (JSONObject) o;
            String bigTypeName = wrongDoc.getString("bigTypeName");
            TContractBigType contractBigType = contractBigTypeDao.findBy(bigTypeName);
            String typeName = wrongDoc.getString("typeName");
            TContractType contractType = contractTypeDao.findBy(typeName);
            String contractName = wrongDoc.getString("contractName");
            TContract contract = contractDao.findBy(contractName, contractType.getId());
            if (contract != null) {
                wrongDocIds.add(contract.getId() + "");
            }
        }
        log.info("wrongDocIds={}", JSON.toJSONString(wrongDocIds));
        System.out.println("--");
    }

    @Override
    public void resetContractImage(String filePath) {
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            return;
        }
        File[] files = fileDir.listFiles();
        List<Map<String, Object>> importFiles = new ArrayList<>();
        for (File bigTypeDir : files) {
            if (!bigTypeDir.isDirectory()) {
                continue;
            }
            String bigTypeName = bigTypeDir.getName();
            TContractBigType contractBigType = contractBigTypeDao.findBy(bigTypeName);
            if (contractBigType == null) {
                continue;
            }
            log.info("bigTypeName={}/{}", bigTypeName, bigTypeDir.isDirectory());
            for (File typeDir : bigTypeDir.listFiles()) {
                if (!typeDir.isDirectory()) {
                    continue;
                }
                String typeName = typeDir.getName();
                TContractType contractType = contractTypeDao.findBy(typeName);
                if (contractType == null) {
                    continue;
                }
                log.info("-----typeName={}/{}" , typeName, typeDir.isDirectory());
                for (File contractFile : typeDir.listFiles()) {
                    if (contractFile.isDirectory()) {
                        continue;
                    }
                    String contractName = contractFile.getName().split("\\.")[0];
                    TContract contract = contractDao.findBy(contractName, contractType.getId());
                    if (contract == null || contract.getDeleteFlag()) {
                        continue;
                    }
                    Map<String, Object> result = uploadContractImage(contractFile.getPath());
                    result.put("id", contract.getId());
                    log.info("item={}", JSON.toJSONString(result));
                    importFiles.add(result);
                }
            }
            log.info("-------");
        }
        log.info("-----");
        log.info("itemList={}", JSON.toJSONString(importFiles));
        try {
            File outFile = new File(FilePathUtils.getOutPutPath() + RandomStringUtils.randomAlphabetic(8) + ".json");
            FileUtils.writeStringToFile(outFile, JSON.toJSONString(importFiles), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

    @Override
    public void resetContractName(String filePath) {
        int page = 1;
        List<JSON> list = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        while (true) {
            PageInfo<TContract> pageInfo = contractDao.getWrongList(page, 20, null, "");
            for (TContract contract : pageInfo.getList()) {
                try {
                    TContractType contractType = contractTypeMapDao.getContractType(contract.getId());
                    String typeName = contractType.getName();
                    TContractBigType contractBigType = contractBigTypeDao.selectById(contractType.getBigTypeId());
                    String bigTypeName = contractBigType.getName();
                    String contractName = contract.getName();
                    String fullPath = filePath + File.separator + bigTypeName + File.separator + typeName + File.separator;
                    String fullName = fullPath + contractName + ".doc";
                    File file = new File(fullName);
                    if (!file.exists()) {
                        String realBigTypeName = getRealBigTypeName(filePath, typeName);
                        if (StringUtils.isBlank(realBigTypeName)) {
                            log.error("file not exist ={}", fullName);
                            errorList.add(fullName);
                            continue;
                        }
                        fullPath = filePath + File.separator + realBigTypeName + File.separator + typeName + File.separator;
                        fullName = fullPath + contractName + ".doc";
                        file = new File(fullName);
                        if (!file.exists()) {
                            log.error("file not exist ={}, bigTypeName={}, realBigTypeName={}", fullName, bigTypeName, realBigTypeName);
                            errorList.add(fullName);
                            continue;
                        }
                    }
                    log.info("full name ={}", fullName);
                    Document doc = new Document(file.getPath());
                    System.out.println(doc.getFirstSection().getBody().getFirstParagraph().getText());
                    System.out.println(doc.getFirstSection().getBody().getParagraphs().get(1).getText());
                    String docName = doc.getFirstSection().getBody().getParagraphs().get(1).getText();
                    docName = docName.replace("\r", "");
                    //File newFile = new File(fullPath + docName + ".doc");
                    //if (file.renameTo(newFile)) {
                    //    System.out.println("修改成功!");
                    //} else {
                    //    System.out.println("修改失败");
                    //}
                    JSONObject json = new JSONObject();
                    json.put("id", contract.getId());
                    json.put("fullName", fullName);
                    json.put("originName", contract.getName());
                    json.put("docName", docName);
                    list.add(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            page ++;
            //if (page > 1) {
            //    break;
            //}
        }
        log.info("errorList size={}", errorList.size());
        log.info("errorList={}", JSON.toJSONString(errorList));
        log.info("itemList size={}", list.size());
        log.info("itemList={}", JSON.toJSONString(list));
        try {
            File outFile = new File(FilePathUtils.getOutPutPath() + RandomStringUtils.randomAlphabetic(8) + ".json");
            FileUtils.writeStringToFile(outFile, JSON.toJSONString(list), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

    private String getRealBigTypeName(String filePath, String inputTypeName) {
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            return "";
        }
        File[] files = fileDir.listFiles();
        for (File bigTypeDir : files) {
            if (!bigTypeDir.isDirectory()) {
                continue;
            }
            String bigTypeName = bigTypeDir.getName();
            //log.info("bigTypeName={}/{}", bigTypeName, bigTypeDir.isDirectory());
            for (File typeDir : bigTypeDir.listFiles()) {
                if (!typeDir.isDirectory()) {
                    continue;
                }
                String typeName = typeDir.getName();
                if (inputTypeName.equals(typeName)) {
                    return bigTypeName;
                }
            }
        }
        return "";
    }

    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveContractToDb(List<Map<String, Object>> itemList) {
        for (Map<String, Object> item : itemList) {
            Integer contractId = saveContract(item);
            contractTypeMapDao.saveContractTypeMap(contractId, (Integer) item.get("contractTypeId"));
        }
    }

    private Integer saveContract(Map<String, Object> item) {
        TContract contract = new TContract();
        contract.setName((String) item.get("name"));
        contract.setLength((Integer) item.get("length"));
        contract.setFirstImage((String) item.get("firstImage"));
        contract.setImages((String) item.get("images"));
        contract.setDownloadUrl((String) item.get("downloadUrl"));
        contractDao.insert(contract);
        return contract.getId();
    }

    private Map<String, Object> uploadContractImage(String filePath) {
        Map<String, Object> data = new HashMap<>();
        try {
            Document doc = new Document(filePath);
            int length = WordProcessingUtils.getPageCount(doc);
            if (length == 0) {
                return Collections.EMPTY_MAP;
            }
            File localFile = new File(filePath);
            String fileName = localFile.getName();
            data.put("name", fileName.split("\\.")[0]);
            data.put("length", length);
            String dir = filePath.split("\\.")[0];
            File fileDir = new File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            WordProcessingUtils.splitWithJPEG(doc, dir, 2);
            File pic1 = new File(dir + File.separator + "1.jpeg");
            File pic2 = new File(dir + File.separator  + "2.jpeg");
            String firstImage = tFileService.watermark(pic1);
            data.put("firstImage", firstImage);
            if (pic2.exists()) {
                String secondImage = tFileService.watermark(pic2);
                data.put("images", firstImage + "," + secondImage);
            } else {
                data.put("images", firstImage);
            }
            System.out.println(JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private Map<String, Object> uploadContract(String filePath) {
        Map<String, Object> data = new HashMap<>();
        try {
            File localFile = new File(filePath);
            String fileName = localFile.getName();
            Document doc = new Document(filePath);
            int length = WordProcessingUtils.getPageCount(doc);
            if (length == 0) {
                return Collections.EMPTY_MAP;
            }
            data.put("name", fileName.split("\\.")[0]);
            data.put("length", length);
            String downloadUrl = getReturnUrl(localFile, fileName);
            System.out.println("downloadUrl=" + downloadUrl);
            data.put("downloadUrl", downloadUrl);
            String dir = filePath.split("\\.")[0];
            File pic1 = new File(dir + File.separator + "1.jpeg");
            File pic2 = new File(dir + File.separator  + "2.jpeg");
            String firstImage = tFileService.watermark(pic1);
            data.put("firstImage", firstImage);
            if (pic2.exists()) {
                String secondImage = tFileService.watermark(pic2);
                data.put("images", firstImage + "," + secondImage);
            } else {
                data.put("images", firstImage);
            }
            System.out.println(JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    private Map<String, Object> resolveContract(String filePath) {
        Map<String, Object> data = new HashMap<>();
        try {
            File localFile = new File(filePath);
            String fileName = localFile.getName();
            Document doc = new Document(filePath);
            data.put("name", fileName.split("\\.")[0]);
            data.put("length", WordProcessingUtils.getPageCount(doc));
            String dir = filePath.split("\\.")[0];
            File fileDir = new File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            WordProcessingUtils.splitWithJPEG(doc, dir, 2);
            String downloadUrl = getReturnUrl(localFile, fileName);
            data.put("downloadUrl", downloadUrl);
            System.out.println("downloadUrl=" + downloadUrl);
            String firstImage = tFileService.watermark(new File(fileDir + File.separator + "1.jpeg"));
            String secondImage = tFileService.watermark(new File(fileDir + File.separator + "2.jpeg"));
            data.put("firstImage", firstImage);
            data.put("images", firstImage + "," + secondImage);
            System.out.println(JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
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

}
