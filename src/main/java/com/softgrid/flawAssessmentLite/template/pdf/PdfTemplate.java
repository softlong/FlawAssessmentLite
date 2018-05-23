package com.softgrid.flawAssessmentLite.template.pdf;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

/**
 * @author Vincent Geng
 *
 * Created on 16 May 2018
 */
public abstract class PdfTemplate {
	
	private final static Logger logger = Logger.getLogger(PdfTemplate.class);
	
	protected PdfDocument pdf;
	protected Document document;
	
	/**
	 * @param templateName
	 * @param path
	 */
	protected void initPdfDocument(String templateName, String path) {
		
		try {
			Date currentDate = new Date();
			Long unixTimestamp = currentDate.getTime() / 1000;
			String filePath = path + "\\" + templateName + "_" + unixTimestamp.toString() + ".pdf";
			logger.info("PDF file path: " + filePath);
			this.pdf = new PdfDocument(new PdfWriter(filePath));
			PageSize ps = PageSize.A4;
			this.document = new Document(pdf, ps);
			document.setTopMargin(22);
//	        logger.info("Left Margin: "+ document.getLeftMargin());
//	        logger.info("Right Margin: "+ document.getRightMargin());
//	        logger.info("Top Margin: "+ document.getTopMargin());
//	        logger.info("Bottom Margin: "+ document.getBottomMargin());
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		
	}
	
	/**
	 * @param 
	 * @return 
	 */
	//Can be a concrete function or an abstract function
	protected abstract void setupPdfHeader();
	
	/**
	 * @param 
	 * @return 
	 */
	protected abstract void setupPdfBody();
	
	/**
	 * @param 
	 * @return 
	 */
	protected void setupPdfFooter(){
		pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandler(document));
	};
	
	/**
	 * 
	 */
	protected void closeDocument() {
		this.document.close();
	}

	/**
	 * @param templateName
	 * @param path
	 */
	public void generatePDF(String templateName, String path) {
		initPdfDocument(templateName, path);
		setupPdfHeader();
		setupPdfBody();
		setupPdfFooter();
		closeDocument();
	}

	protected class TextFooterEventHandler implements IEventHandler {
        protected Document doc;
 
        public TextFooterEventHandler(Document doc) {
            this.doc = doc;
        }
 
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();
            
            canvas.beginText();
            try {
                canvas.setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            canvas.moveText(doc.getLeftMargin() - 10, pageSize.getBottom() + doc.getBottomMargin() - 10)
                   
            .showText("Footer                                                                                                             Flaw Assessment                                                                                                                      Page "+docEvent.getDocument().getPageNumber(docEvent.getPage())+" / "+docEvent.getDocument().getNumberOfPages())
                    .endText()
                    .release();
        }
    }
	
}
