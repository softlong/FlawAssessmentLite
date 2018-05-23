package com.softgrid.flawassessment.template.pdf;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * @author Vincent Geng
 *
 * Created on 16 May 2018
 */
public class PdfFactory {
	
	private final static Logger logger = Logger.getLogger(PdfFactory.class);
	private static final String PACKAGE_NAME = "com.softgrid.flawassessment.template.pdf.";
	
	/**
	 * @param templateName
	 * @param path
	 */
	public static void exportPDF(String templateName, String path) {
		try {
			PdfTemplate pdfTemplate = (PdfTemplate) Class.forName(convertTemplateNameToClassName(templateName)).newInstance();
			pdfTemplate.generatePDF(templateName, path);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**
	 * @param templateName
	 * @return
	 */
	private static String convertTemplateNameToClassName(String templateName) {
		String className = PACKAGE_NAME+templateName.replaceAll("\\s","")+"PDF";
		logger.info("convertTemplateNameToClassName||Class Name: "+className);
		return className;
	}
	
	
	
}
