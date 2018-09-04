package com.softgrid.flawAssessmentLite.pdf;

import com.softgrid.flawAssessmentLite.pdf.template.PdfTemplate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * @author Vincent Geng
 * <p>
 * Created on 16 May 2018
 */
public class PdfFactory {

    private final static Logger logger = Logger.getLogger(PdfFactory.class);
    private static final String PACKAGE_NAME = "com.softgrid.flawAssessmentLite.pdf.template.";

    /**
     * @param templateName
     * @param path
     */
    public static void exportPDF(String templateName, String path, ArrayList<String> resultDataList) {
        try {
            PdfTemplate pdfTemplate = (PdfTemplate) Class.forName(convertTemplateNameToClassName(templateName)).newInstance();
            pdfTemplate.generatePDF(path, resultDataList);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * @param templateName
     * @return
     */
    private static String convertTemplateNameToClassName(String templateName) {
        String className = PACKAGE_NAME + templateName.replaceAll("\\s", "") + "PDF";
        logger.info("convertTemplateNameToClassName||Class Name: " + className);
        return className;
    }

}
