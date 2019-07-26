package com.mybatisPlus.demo.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.HeaderFooter;
import com.aspose.words.HeaderFooterType;
import com.aspose.words.HorizontalAlignment;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.Paragraph;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import com.aspose.words.Shape;
import com.aspose.words.ShapeType;
import com.aspose.words.VerticalAlignment;
import com.aspose.words.WrapType;

/**
 * @author ShengYoufu
 * 
 */
public class RemoveWordWatermark {
	
	static {
		loadLicense();
	}

	public static void main(String[] args) throws Exception {
		// Sample infrastructure.
//		URI exeDir = RemoveWordWatermark.class.getResource("").toURI();
//		String dataDir = new File(exeDir.resolve("../../Data")) + File.separator;
//		Document doc = new Document(dataDir + "TestFile.doc");
//		doc.save(dataDir + "TestFile Out.doc");
		try {
			Document doc = new Document("D:/lawyer365test/2019公司餐厅承包合同范本.doc");
			// 去除水印
			removeWatermark(doc);
			// 去除页脚
			removePageFoot(doc);
			// 去除第一段
			removeFistParagraph(doc);
			// 加上我们的自定义水印
			insertWatermarkText(doc, "好好学习，天天向上");
//			doc.getRange().replace("律师365合同栏目", "可以开始进行测试一下啦", true, false);
			doc.save("D:/lawyer365test/2019公司餐厅承包合同范本copy3.doc");
			
			// 将word转换为pdf
			changeWordToPdf("D:/lawyer365test/2019公司餐厅承包合同范本copy3.doc");
			// 将word转换为image
			changeWordToJpeg("D:/lawyer365test/2019公司餐厅承包合同范本copy3.doc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void changeWordToJpeg(String wordUrl) throws Exception {
		// 初始化一个文件作为图片
//		File file = new File("D:/lawyer365test/2019公司餐厅承包合同范本.jpeg");
//		FileOutputStream out = new FileOutputStream(file);
        ImageSaveOptions options = new ImageSaveOptions(SaveFormat.JPEG);

		Document doc = new Document(wordUrl);
		
        for (int i = 0; i < doc.getPageCount(); i++)
        {
    	   options.setPageIndex(i);
           options.setPageCount(i + 1);
           doc.save("D:/lawyer365test/2019公司餐厅承包合同范本" + (i + 1) + ".jpg", options);
        }
        
		// 获取document对象从而进行文件转换
		
//		ImageSaveOptions imageSaveOptions = new ImageSaveOptions(SaveFormat.JPEG);
////		
//		imageSaveOptions.setSaveFormat(200);
//		doc.save(out, SaveFormat.JPEG);
//		doc.save(out, SaveFormat.JPEG);
	}


	private static void changeWordToPdf(String word) throws Exception {
		
		long old = System.currentTimeMillis();
		
		File file = new File("D:/lawyer365test/2019公司餐厅承包合同范本.pdf");
		FileOutputStream os = new FileOutputStream(file);
		
		
		Document doc = new Document(word);
		doc.save(os, SaveFormat.PDF);
	
		long now = System.currentTimeMillis();
	    System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
	}

	
	public static void removeFistParagraph(Document doc) throws Exception {
        if (doc.getFirstSection().getBody().getFirstParagraph().getCount() > 0) {
            //word中的所有段落
            doc.getFirstSection().getBody().getParagraphs().get(0).remove();
        }
    }

	/**
	 * 
	 * Inserts a watermark into a document.
	 * 
	 * @param doc The input document.
	 * @param watermarkText Text of the watermark.
	 * 
	 */
	private static void insertWatermarkText(Document doc, String watermarkText) throws Exception {
		// Create a watermark shape. This will be a WordArt shape.
		// You are free to try other shape types as watermarks.
		Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
		// Set up the text of the watermark.
		watermark.getTextPath().setText(watermarkText);
		watermark.getTextPath().setFontFamily("Arial");
		watermark.setWidth(500);
		watermark.setHeight(100);
		// Text will be directed from the bottom-left to the top-right corner.
		watermark.setRotation(-40);
		// Remove the following two lines if you need a solid black text.
		watermark.getFill().setColor(Color.GREEN); // Try LightGray to get more Word-style watermark
		watermark.setStrokeColor(Color.GRAY); // Try LightGray to get more Word-style watermark
		// Place the watermark in the page center.
		watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
		watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
		watermark.setWrapType(WrapType.NONE);
		watermark.setVerticalAlignment(VerticalAlignment.CENTER);
		watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
		// Create a new paragraph and append the watermark to this paragraph.
		Paragraph watermarkPara = new Paragraph(doc);
		watermarkPara.appendChild(watermark);
		// Insert the watermark into all headers of each document section.
		for (Section sect : doc.getSections()) {
			// There could be up to three different headers in each section, since we want
			// the watermark to appear on all pages, insert into all headers.
			insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
			insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
			insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
		}
	}

	/**
	 * 插入水印
	 * @param watermarkPara
	 * @param sect
	 * @param headerType
	 * @throws Exception
	 */
	private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) throws Exception {
		HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
		if (header == null) {
			// There is no header of the specified type in the current section, create it.
			header = new HeaderFooter(sect.getDocument(), headerType);
			sect.getHeadersFooters().add(header);
		}

		// Insert a clone of the watermark into the header.
		header.appendChild(watermarkPara.deepClone(true));
	}

	/**
	 * 移除全部水印
	 * @param doc
	 * @throws Exception
	 */
	private static void removeWatermark(Document doc) throws Exception {
		for (Section sect : doc.getSections()) {
			// There could be up to three different headers in each section, since we want
			// the watermark to appear on all pages, insert into all headers.
			removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_PRIMARY);
			removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_FIRST);
			removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_EVEN);
		}
	}
	
	
	/**
	 * 移除全部水印
	 * @param doc
	 * @throws Exception
	 */
	private static void removePageFoot(Document doc) throws Exception {
		for (Section sect : doc.getSections()) {
			// There could be up to three different headers in each section, since we want
			// the watermark to appear on all pages, insert into all headers.
			removePageFootBySect(sect, HeaderFooterType.FOOTER_PRIMARY);
			removePageFootBySect(sect, HeaderFooterType.FOOTER_FIRST);
			removePageFootBySect(sect, HeaderFooterType.FOOTER_EVEN);
			 
		}
	}
	
	/**
	 * 移除指定Section的水印
	 * @param sect
	 * @param headerType
	 * @throws Exception
	 */
	private static void removeWatermarkFromHeader(Section sect, int headerType) throws Exception {
		HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
		if (header != null) {
			header.removeAllChildren();
		}
	}
	
	
	/**
	 * 移除每一节页脚
	 * @param sect
	 * @param headerType
	 * @throws Exception
	 */
	private static void removePageFootBySect(Section sect, int headerType) throws Exception {
		HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
		if (header != null) {
			header.remove();
		}
	}
	
	
	/**
	 * 从Classpath（jar文件中）中读取License
	 */
	private static void loadLicense() {
	  //返回读取指定资源的输入流
	  License license = new License();
	  InputStream is = null;
	  try {
	    is = RemoveWordWatermark.class.getResourceAsStream("/Aspose.Words.lic");
	    if(is==null) 
	      throw new RuntimeException("Cannot find licenses file. Please contact wdmsyf@yahoo.com or visit http://sheng.javaeye.com for get more information.");
	    license.setLicense(is);
	  } catch (Exception ex) {
	    ex.printStackTrace();
	  }finally{
	    if(is!=null){
	      try{ is.close(); }catch(IOException ex){ };
	      is = null;
	    }
	  }
	}
}