package com.softgrid.flawAssessmentLite.pdf.template;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Author: Cheryl
 * Description:
 * Date:Created in 16:54 2018/6/14
 */
public class GB30582CPDF extends PdfTemplate {
    private static final Logger logger = Logger.getLogger(GB30582CPDF.class);
    private final String[] fieldNameList = {
            "壁厚t（mm）",
            "管线外径D（mm）",
            "管线钢钢级",
            "屈服强度（MPa）",
            "抗拉强度（MPa）",
            "夏比冲击功Cv0（J）",
            "弹性模量E（MPa）",
            "实际运行压力P（MPa）",
            "无内压时凹陷深度H0（mm）",
            "有内压时划痕深度d（mm）"};

    @Override
    protected void setupPdfBody(ArrayList<String> resultDataList) {
        try {
            Style labelStyle = new Style();
            PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
            labelStyle.setFontSize(10);
            labelStyle.setFont(font);
            labelStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            labelStyle.setBorder(Border.NO_BORDER);

            Table reportTable = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}));
            reportTable.setMarginTop(20);
            reportTable.setWidth(UnitValue.createPercentValue(100));
            reportTable.setBorder(Border.NO_BORDER);
            reportTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            reportTable.addCell(new Cell(1, 4).add(new Paragraph("含划伤凹陷管道剩余强度评价")).addStyle(labelStyle));

            int i = 0;
            for (String fieldName : fieldNameList) {
                reportTable.addCell(new Cell(1, 1).add(new Paragraph(fieldName)).addStyle(labelStyle));
                reportTable.addCell(new Cell(1, 1).add(new Paragraph(resultDataList.get(i))).addStyle(labelStyle));
                i++;
            }

            document.add(reportTable);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

}
