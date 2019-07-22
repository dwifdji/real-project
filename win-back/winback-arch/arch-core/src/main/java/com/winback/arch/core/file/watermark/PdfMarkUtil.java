package com.winback.arch.core.file.watermark;

import com.winback.arch.core.file.FilePathUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfMarkUtil {

    private static int interval = 3;

    public static void waterMark(String inputFile,
                                 String outputFile, String waterMarkName) {
        try {
            PdfReader  reader  = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);

            Rectangle pageRect = null;
            PdfGState gs       = new PdfGState();
            gs.setFillOpacity(0.3f);
            gs.setStrokeOpacity(0.4f);
            int total = reader.getNumberOfPages() + 1;

            JLabel      label = new JLabel();
            FontMetrics metrics;
            int         textH = 0;
            int         textW = 0;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());

            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                under = stamper.getOverContent(i);
                under.saveState();
                under.setGState(gs);
                under.beginText();
                under.setFontAndSize(base, 20);

                // 水印文字成30度角倾斜
                //你可以随心所欲的改你自己想要的角度
                for (int height = interval + textH; height < pageRect.getHeight();
                     height = height + textH * 8) {
                    for (int width = interval + textW; width < pageRect.getWidth() + textW;
                         width = width + textW * 3) {
                        under.showTextAligned(Element.ALIGN_LEFT
                                , waterMarkName, width - textW,
                                height - textH, 30);
                    }
                }
                // 添加水印文字
                under.endText();
            }
            //说三遍
            //一定不要忘记关闭流
            //一定不要忘记关闭流
            //一定不要忘记关闭流
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addImageWatermark(String inPdfFile, String outPdfFile, String markImagePath){

        try {
            // read existing pdf
            PdfReader  reader  = new PdfReader(inPdfFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outPdfFile));

            // image watermark
            Image img = Image.getInstance(markImagePath);
            float w   = img.getScaledWidth();
            float h   = img.getScaledHeight();

            // properties
            PdfContentByte over;
            Rectangle      pagesize;
            float          x, y;

            // loop over every page
            int n = reader.getNumberOfPages();

            for (int i = 1; i <= n; i++) {
                // get page size and position
                pagesize = reader.getPageSizeWithRotation(i);
                x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                over = stamper.getOverContent(i);
                over.saveState();

                // set transparency
                PdfGState state = new PdfGState();
                state.setFillOpacity(1f);
                over.setGState(state);

                over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                over.restoreState();
            }
            stamper.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        waterMark("D:/1.pdf", "D:/2.pdf", "360pai.com");
        addImageWatermark(FilePathUtils.getInputPath() + "1.pdf",
                FilePathUtils.getOutPutPath() + "1.pdf", FilePathUtils.getInputPath() + "水印.png");
    }

}
