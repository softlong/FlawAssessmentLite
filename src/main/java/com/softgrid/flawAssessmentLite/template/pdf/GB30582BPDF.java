package com.softgrid.flawAssessmentLite.template.pdf;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;

/**
 * @author Vincent Geng
 *
 * Created on 17 May 2018
 */
public class GB30582BPDF extends PdfTemplate {
	
	private static final Logger logger = Logger.getLogger(GB30582BPDF.class);
	
	/* (non-Javadoc)
	 * @see com.softgrid.flawAssessmentLite.template.pdf.PdfTemplate#setupPdfHeader()
	 */
	@Override
	protected void setupPdfHeader() {
		// TODO Setup PDF Header

	}

	/* (non-Javadoc)
	 * @see com.softgrid.flawAssessmentLite.template.pdf.PdfTemplate#setupPdfBody()
	 */
	@Override
	protected void setupPdfBody() {
		try {
			Style reportLabel = new Style();
			reportLabel.setFontSize(7);
			reportLabel.setHorizontalAlignment(HorizontalAlignment.LEFT);
			reportLabel.setBorder(Border.NO_BORDER);
			Table reportTable = new Table(UnitValue.createPercentArray(new float[] { 5, 5, 5, 5 }));
			reportTable.setMarginTop(10);
			reportTable.setWidth(UnitValue.createPercentValue(100));
			reportTable.setBorder(Border.NO_BORDER);
			//        reportTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
			reportTable.addCell(new Cell(1, 4).add(new Paragraph("GB30582B")).addStyle(reportLabel));
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("tfpipename: ")).addStyle(reportLabel));
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("Test Pipe")).addStyle(reportLabel));
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("tflocation: ")).addStyle(reportLabel));
			PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("新加坡")).addStyle(reportLabel).setFont(font));
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("tft: ")).addStyle(reportLabel));
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("50")).addStyle(reportLabel));
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("tfD: ")).addStyle(reportLabel));
			reportTable.addCell(new Cell(1, 1).add(new Paragraph("10")).addStyle(reportLabel));
			document.add(reportTable);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

}
