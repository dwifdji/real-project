package com.winback.arch.core.file.watermark;

import com.aspose.words.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/15 10:08
 */
public class WordProcessingUtils {

    static {
        AsposeWorldLisence.loadLicense();
    }

    public static void main(String[] args) throws Exception {
        //String filePath = "D:/混合担保合同范本.doc";
        long startTime = System.currentTimeMillis();
        String filePath = "/Users/xdrodger/tmp/2019城市房屋拆迁补偿安置协议.doc";

        File     file     = new File(filePath);
        String   fileName = file.getName();
        Document doc      = new Document(filePath);
        System.out.println(getPageCount(doc));
        removeWatermark(doc);
        //removeFistParagraph(doc);
        String dir     = filePath.split("\\.")[0];
        File   fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        doc.save(fileDir + File.separator + fileName);

        Document doc1 = new Document(fileDir + File.separator + fileName);
        splitWithJPEG(doc1, dir, 2);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime-startTime)/1000);
    }

    public static Integer getPageCount(Document doc) throws Exception {
        return doc.getPageCount();
    }

    /**
     * 移除全部水印
     *
     * @param doc
     * @throws Exception
     */
    public static void removeWatermark(Document doc) throws Exception {
        for (Section sect : doc.getSections()) {
            sect.clearHeadersFooters();
            // There could be up to three different headers in each section, since we want
            // the watermark to appear on all pages, insert into all headers.
            removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_PRIMARY);
            removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_FIRST);
            removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_EVEN);
        }
        NodeCollection<Shape> shapes = (NodeCollection<Shape>) doc.getChildNodes(NodeType.SHAPE, true);
        for (Shape shape : shapes) {
            if (shape.hasImage()) {
                shape.remove();
            }
        }
    }

    /**
     * 移除指定Section的水印
     *
     * @param sect
     * @param headerType
     * @throws Exception
     */
    private static void removeWatermarkFromHeader(Section sect, int headerType) throws Exception {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header != null) {
            header.remove();
            header.removeAllChildren();
        }
    }

    public static void removeFistParagraph(Document doc) throws Exception {
        if (doc.getFirstSection().getBody().getFirstParagraph().getCount() > 0) {
            //word中的所有段落
            doc.getFirstSection().getBody().getParagraphs().get(0).remove();
        }
    }

    public static void splitWithJPEG(Document doc, String fileDir, int num) throws Exception {
        doc.save(fileDir + File.separator + "1.jpeg");
        if (doc.getPageCount() < num) {
            return;
        }
        ImageSaveOptions options = new ImageSaveOptions(SaveFormat.JPEG);
        for (int i = 1; i <= num - 1; i++) {
            options.setPageIndex(i);
            options.setPageCount(i + 1);
//            options.setJpegQuality(5);
//            options.setResolution(160);
            doc.save(fileDir + File.separator + (i + 1) + ".jpeg", options);
        }
    }

    private static void removeImage(Document doc)throws Exception{
        NodeCollection<Shape> shapes = (NodeCollection<Shape>) doc.getChildNodes(NodeType.SHAPE, true);
        for (Shape shape : shapes) {
            if (shape.hasImage()) {
                shape.remove();
            }
        }
        doc.save(doc.getOriginalFileName());
    }

    public static void removeComments(Document doc)throws Exception{
        NodeCollection comments = doc.getChildNodes(NodeType.COMMENT, true);
        comments.clear();
        doc.save(doc.getOriginalFileName());
    }
}
