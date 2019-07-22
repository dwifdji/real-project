package com.winback.arch.core.file.watermark;

import com.aspose.words.Document;
import com.itextpdf.text.pdf.PdfReader;

import java.io.FileInputStream;

public class Doc2PDF
{
    public static void main(String[] args) throws Exception {
        // The path to the documents directory.
        AsposeWorldLisence.loadLicense();
        String dataDir = "d://";

        //ExStart:Doc2Pdf
		// Load the document from disk.
        Document doc = new Document(dataDir + "page.doc");

        // Save the document in PDF format.
        dataDir = dataDir + "1_1.pdf";
        doc.save(dataDir);
		//ExEnd:Doc2Pdf


        PdfReader pdfReader = new PdfReader(new FileInputStream(dataDir));

        int pages = pdfReader.getNumberOfPages();
        System.out.println(pages);
        System.out.println("\nDocument converted to PDF successfully.\nFile saved at " + dataDir);
    }
}
