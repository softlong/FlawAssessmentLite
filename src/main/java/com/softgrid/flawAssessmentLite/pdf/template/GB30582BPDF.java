package com.softgrid.flawAssessmentLite.pdf.template;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
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
 * Date:Created in 16:53 2018/6/14
 */
public class GB30582BPDF extends PdfTemplate {
    private static final Logger logger = Logger.getLogger(GB30582BPDF.class);
    private final String[] fieldNameList = {
            "评价时间",
            "管道壁厚t(mm)",
            "管道内半径R0(mm)",
            "管道横截面曲率半径(mm)",
            "管道轴向面曲率半径(mm)",
            "凹陷长度L(mm)",
            "凹陷深度d(mm)",
            "评价结果"};

    @Override
    protected void setupPdfBody(ArrayList<String> resultDataList) {
        try {
            Style labelStyle = new Style();
            PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
            labelStyle.setFontSize(10);
            labelStyle.setPaddingTop(5);
            labelStyle.setPaddingBottom(5);
            labelStyle.setFont(font);
            labelStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            labelStyle.setBorder(Border.NO_BORDER);

            Table reportTable = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}));
            reportTable.setMarginTop(20);
            reportTable.setWidth(UnitValue.createPercentValue(100));
            reportTable.setBorder(Border.NO_BORDER);
            reportTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            reportTable.addCell(new Cell(1, 1).add(new Paragraph("评价方式")).addStyle(labelStyle));
            reportTable.addCell(new Cell(1, 3).add(new Paragraph("含凹陷管道的剩余强度评价")).addStyle(labelStyle).setBold());


            int i = 0;
            for (String fieldName : fieldNameList) {
                if (i == 1) {
                    reportTable.addCell(new Cell(1, 4).add(new Paragraph("用户参数")).addStyle(labelStyle).setBold().setBorderBottom(new SolidBorder(0.5f)));
                }

                Cell cell;
                if (i == 0 ) {
                    cell = new Cell(1, 4);
                } else {
                    cell = new Cell(1, 1);
                }
                reportTable.addCell(new Cell(1, 1).add(new Paragraph(fieldName)).addStyle(labelStyle));
                reportTable.addCell(cell.add(new Paragraph(resultDataList.get(i))).addStyle(labelStyle).setBold());
                i++;
            }

            document.add(reportTable);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
