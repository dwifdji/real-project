package com.winback.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aspose.words.Document;

import com.winback.arch.core.file.FilePathUtils;
import com.winback.arch.core.file.watermark.WordProcessingUtils;
import com.winback.core.commons.constants.PushEnum;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: SimpleTest
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 16:26
 */
public class SimpleTest {


    @Test
    public void tmp() {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(1);
        set.add(2);
        set.add(3);
        Integer i = 3;
        System.out.println(set.contains(1));
        System.out.println(set.contains(i));

        String str = ".~业劳动合同范本.doc";
        System.out.println(str.startsWith("."));
    }

    /**
     * 合同文件第一步预处理
     */
    @Test
    public void test1stProcessContractFile() {
        //File fileDir = new File("/Users/xdrodger/tmp/soyin/contract");
        File fileDir = new File("/Users/xdrodger/tmp/365/contract");
        if (!fileDir.exists()) {
            return;
        }
        String outputPath = FilePathUtils.getOutPutPath() + File.separator + "contract";
        File saveFileDir = new File(outputPath);
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
        }
        File[] files = fileDir.listFiles();
        for (File bigTypeDir : files) {
            if (!bigTypeDir.isDirectory()) {
                continue;
            }
            System.out.println("bigTypeName=" + bigTypeDir.getName() + "/" + bigTypeDir.isDirectory());
            String bigTypeName = bigTypeDir.getName();
            String outBigTypeDirPath = outputPath + File.separator + bigTypeName;
            File outBigTypeDir = new File(outBigTypeDirPath);
            if (!outBigTypeDir.exists()) {
                outBigTypeDir.mkdir();
            }
            for (File typeDir : bigTypeDir.listFiles()) {
                if (!typeDir.isDirectory()) {
                    continue;
                }
                System.out.println("-----typeName=" + typeDir.getName() + "/" + typeDir.isDirectory());
                String typeName = typeDir.getName();
                String outTypeDirPath = outBigTypeDirPath + File.separator + typeName;
                File outTypeDir = new File(outTypeDirPath);
                if (!outTypeDir.exists()) {
                    outTypeDir.mkdir();
                }
                for (File contractFile : typeDir.listFiles()) {
                    if (contractFile.getName().startsWith(".")) {
                        continue;
                    }
                    String contractName = contractFile.getName().split("\\.")[0];
                    System.out.println("-----contractName=" + contractFile.getName() + "/" + typeDir.isDirectory());
                    System.out.println("-----contractFilePath=" + contractFile.getPath() + "/" + typeDir.isDirectory());
                    System.out.println("-----contractName=" + contractFile.getName() + "/" + typeDir.isDirectory());
                    pre1stProcess(contractFile, outTypeDirPath);
                }
            }
            System.out.println("-------");
        }
        System.out.println("-----");
    }

    /**
     * 合同文件第二部预处理
     */
    @Test
    public void test2stProcessContractFile() {
        //File fileDir = new File("/data/file/output/contract");
        //File fileDir = new File("/Users/xdrodger/tmp/soyin/contract");
        File fileDir = new File("/Users/xdrodger/tmp/365/contract");

        if (!fileDir.exists()) {
            return;
        }
        String outputPath = FilePathUtils.getOutPutPath() + File.separator + "contract";
        File saveFileDir = new File(outputPath);
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
        }
        File[] files = fileDir.listFiles();
        for (File bigTypeDir : files) {
            if (!bigTypeDir.isDirectory()) {
                continue;
            }
            System.out.println("bigTypeName=" + bigTypeDir.getName() + "/" + bigTypeDir.isDirectory());
            String bigTypeName = bigTypeDir.getName();
            String outBigTypeDirPath = outputPath + File.separator + bigTypeName;
            File outBigTypeDir = new File(outBigTypeDirPath);
            if (!outBigTypeDir.exists()) {
                outBigTypeDir.mkdir();
            }
            for (File typeDir : bigTypeDir.listFiles()) {
                if (!typeDir.isDirectory()) {
                    continue;
                }
                System.out.println("-----typeName=" + typeDir.getName() + "/" + typeDir.isDirectory());
                String typeName = typeDir.getName();
                String outTypeDirPath = outBigTypeDirPath + File.separator + typeName;
                File outTypeDir = new File(outTypeDirPath);
                if (!outTypeDir.exists()) {
                    outTypeDir.mkdir();
                }
                for (File contractFile : typeDir.listFiles()) {
                    if (contractFile.isDirectory()) {
                        continue;
                    }
                    if (contractFile.getName().startsWith(".")) {
                        continue;
                    }
                    String contractName = contractFile.getName().split("\\.")[0];
                    System.out.println("-----contractName=" + contractFile.getName() + "/" + typeDir.isDirectory());
                    System.out.println("-----contractFilePath=" + contractFile.getPath() + "/" + typeDir.isDirectory());
                    System.out.println("-----contractName=" + contractFile.getName() + "/" + typeDir.isDirectory());
                    pre2stProcess(contractFile, outTypeDirPath);
                }
            }
            System.out.println("-------");
        }
        System.out.println("-----");
    }

    private void pre1stProcess(File srcFile, String dir) {
        try {
            String fileName = srcFile.getName();
            Document doc = new Document(srcFile.getPath());
            WordProcessingUtils.removeFistParagraph(doc);
            WordProcessingUtils.removeWatermark(doc);
            WordProcessingUtils.removeComments(doc);
            File fileDir = new File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            doc.save(dir + File.separator + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pre2stProcess(File srcFile, String dir) {
        try {
            String fileName = srcFile.getName();
            Document doc = new Document(srcFile.getPath());
            WordProcessingUtils.removeWatermark(doc);
            WordProcessingUtils.removeComments(doc);
            File fileDir = new File(dir + File.separator + fileName.split("\\.")[0]);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            doc.save(dir + File.separator + fileName);
            WordProcessingUtils.splitWithJPEG(doc, fileDir.getPath(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTmp() {
        File file = new File("/Users/xdrodger/tmp/soyin/contract/企业发展/增资扩股协议/公司增资扩股协议书范本/1.jpeg");
        String fileFormat = new MimetypesFileTypeMap().getContentType(file);
        System.out.println(fileFormat);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
        System.out.println(file.getParent());
        System.out.println(file.getName());

        FilePathUtils.watermarkFile(file);
    }

    @Test
    public void testSingleDoc() {




        printDocInfo("/Users/xdrodger/tmp/委托代理合同2019.doc");
        printDocInfo("/Users/xdrodger/tmp/2019最新个人借款合同范本_671984.doc");
        System.out.println("--");

    }

    private void printDocInfo(String filePath) {
        try {
            File file = new File(filePath);
            Document doc = new Document(file.getPath());
            System.out.println(doc.getPageCount());
            System.out.println(doc.getBuiltInDocumentProperties().getWords());
            System.out.println(doc.getBuiltInDocumentProperties().getParagraphs());
            System.out.println(doc.getBuiltInDocumentProperties().getBytes());
            System.out.println("--");
            System.out.println(isWrongDoc(file));

            System.out.println(doc.getBuiltInDocumentProperties().getTitle());
            System.out.println(JSON.toJSONString(doc.getBuiltInDocumentProperties()));

            System.out.println(doc.getFirstSection().getBody().getFirstParagraph().getText());
            System.out.println(doc.getFirstSection().getBody().getParagraphs().get(1).getText());
            System.out.println(doc.getFirstSection().getBody().getParagraphs().get(2).getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isWrongDoc(File file) {
        try {
            Document doc = new Document(file.getPath());
            if (doc.getPageCount() > 1) {
                return false;
            }
            if (doc.getBuiltInDocumentProperties().getWords() == 0 && doc.getBuiltInDocumentProperties().getCharacters() == 0 && doc.getBuiltInDocumentProperties().getParagraphs() == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void testFilterDoc() {
        //File fileDir = new File("/Users/xdrodger/tmp/soyin/contract");
        File fileDir = new File("/Users/xdrodger/tmp/365/contract");
        if (!fileDir.exists()) {
            return;
        }
        String outputPath = FilePathUtils.getOutPutPath() + File.separator + "contract";
        File saveFileDir = new File(outputPath);
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
        }
        File[] files = fileDir.listFiles();
        JSONArray wrongDocs = new JSONArray();
        for (File bigTypeDir : files) {
            if (!bigTypeDir.isDirectory()) {
                continue;
            }
            System.out.println("bigTypeName=" + bigTypeDir.getName() + "/" + bigTypeDir.isDirectory());
            String bigTypeName = bigTypeDir.getName();
            String outBigTypeDirPath = outputPath + File.separator + bigTypeName;
            File outBigTypeDir = new File(outBigTypeDirPath);
            if (!outBigTypeDir.exists()) {
                outBigTypeDir.mkdir();
            }
            for (File typeDir : bigTypeDir.listFiles()) {
                if (!typeDir.isDirectory()) {
                    continue;
                }
                System.out.println("-----typeName=" + typeDir.getName() + "/" + typeDir.isDirectory());
                String typeName = typeDir.getName();
                String outTypeDirPath = outBigTypeDirPath + File.separator + typeName;
                File outTypeDir = new File(outTypeDirPath);
                if (!outTypeDir.exists()) {
                    outTypeDir.mkdir();
                }
                for (File contractFile : typeDir.listFiles()) {
                    if (contractFile.getName().startsWith(".")) {
                        continue;
                    }
                    String contractName = contractFile.getName().split("\\.")[0];

                    boolean isWrongDoc = isWrongDoc(contractFile);
                    if (isWrongDoc) {
                        System.out.println(contractName);
                        JSONObject json = new JSONObject();
                        json.put("bigTypeName", bigTypeDir.getName());
                        json.put("typeName", typeDir.getName());
                        json.put("contractName", contractName);
                        wrongDocs.add(json);
                    }
                }
            }
            System.out.println("-------");
        }
        System.out.println("-----");
        System.out.println("wrong doc legth=" + wrongDocs.size());
        System.out.println(wrongDocs.toJSONString());
        System.out.println("--");
    }

    @Test
    public void testFilterContractFile() {
        //File fileDir = new File("/data/file/output/contract");
        //File fileDir = new File("/Users/xdrodger/tmp/soyin/contract");
        File fileDir = new File("/Users/xdrodger/tmp/365/contract");

        if (!fileDir.exists()) {
            return;
        }
        String outputPath = "/Users/xdrodger/tmp/365/" + "contract0";
        File saveFileDir = new File(outputPath);
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
        }
        File[] files = fileDir.listFiles();
        for (File bigTypeDir : files) {
            if (!bigTypeDir.isDirectory()) {
                continue;
            }
            System.out.println("bigTypeName=" + bigTypeDir.getName() + "/" + bigTypeDir.isDirectory());
            String bigTypeName = bigTypeDir.getName();
            String outBigTypeDirPath = outputPath + File.separator + bigTypeName;
            File outBigTypeDir = new File(outBigTypeDirPath);
            if (!outBigTypeDir.exists()) {
                outBigTypeDir.mkdir();
            }
            for (File typeDir : bigTypeDir.listFiles()) {
                if (!typeDir.isDirectory()) {
                    continue;
                }
                System.out.println("-----typeName=" + typeDir.getName() + "/" + typeDir.isDirectory());
                String typeName = typeDir.getName();
                String outTypeDirPath = outBigTypeDirPath + File.separator + typeName;
                File outTypeDir = new File(outTypeDirPath);
                if (!outTypeDir.exists()) {
                    outTypeDir.mkdir();
                }
                for (File contractFile : typeDir.listFiles()) {
                    if (contractFile.isDirectory()) {
                        continue;
                    }
                    if (contractFile.getName().startsWith(".")) {
                        continue;
                    }
                    String contractName = contractFile.getName().split("\\.")[0];
                    System.out.println("-----contractName=" + contractFile.getName() + "/" + typeDir.isDirectory());
                    System.out.println("-----contractFilePath=" + contractFile.getPath() + "/" + typeDir.isDirectory());
                    System.out.println("-----contractName=" + contractFile.getName() + "/" + typeDir.isDirectory());
                }
            }
            System.out.println("-------");
        }
        System.out.println("-----");
    }

    @Test
    public void createResetContractImageSql() {
        try {
            String filePath = "/data/file/output/files";
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                return;
            }
            String sql = "update win.t_contract set first_image='%s',images='%s' where id='%s';";
            File[] files = fileDir.listFiles();
            List<String> sqls = new ArrayList<>();
            List<JSONObject> errorList = new ArrayList<>();
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                try {
                    String jsonStr = readJsonFile(file.getAbsolutePath());
                    JSONArray jsonArray = JSON.parseArray(jsonStr);
                    for (Object o : jsonArray) {
                        JSONObject json = (JSONObject) o;
                        String id = json.getString("id");
                        String images = json.getString("images");
                        String firstImage = json.getString("firstImage");
                        if (StringUtils.isBlank(firstImage)) {
                            System.out.println("error item--" + json.toJSONString());
                            errorList.add(json);
                        }
                        String sqlStr = String.format(sql, firstImage, images, id);
                        System.out.println("-sql---" + sqlStr);
                        sqls.add(sqlStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(JSON.toJSONString(sqls));
            writeFile("/data/file/output/db.sql", sqls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

    @Test
    public void createResetContractNameSql() {
        try {
            String filePath = "/data/file/output/files";
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                return;
            }
            String sql = "update win.t_contract set name='%s' where id='%s';";
            File[] files = fileDir.listFiles();
            List<String> sqls = new ArrayList<>();
            List<JSONObject> errorList = new ArrayList<>();
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                try {
                    String jsonStr = readJsonFile(file.getAbsolutePath());
                    JSONArray jsonArray = JSON.parseArray(jsonStr);
                    for (Object o : jsonArray) {
                        JSONObject json = (JSONObject) o;
                        String id = json.getString("id");
                        String name = json.getString("docName");
                        if (StringUtils.isBlank(name)) {
                            System.out.println("error item--" + json.toJSONString());
                            errorList.add(json);
                        }
                        name = name.replace("\r", "");
                        String sqlStr = String.format(sql, name, id);
                        System.out.println("-sql---" + sqlStr);
                        sqls.add(sqlStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(JSON.toJSONString(sqls));
            writeFile("/data/file/output/db.sql", sqls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
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

    public void writeFile(String path, List<String> contents) {
        File file = null;
        FileOutputStream fos = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            // append if true, then bytes will be written to the end of the file rather than the beginning
            fos = new FileOutputStream(file, true);
            for (String content : contents) {
                fos.write(content.getBytes());
                fos.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testResetContractName() {

    }
}
