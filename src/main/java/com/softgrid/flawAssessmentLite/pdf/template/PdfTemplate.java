package com.softgrid.flawAssessmentLite.pdf.template;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import javafx.scene.control.Alert;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author: Cheryl
 * Description:
 * Date:Created in 16:52 2018/6/14
 */
public abstract class PdfTemplate {
    private final static Logger logger = Logger.getLogger(PdfTemplate.class);

    protected PdfDocument pdf;
    protected Document document;

    /**
     * @param
     * @param path
     */
    protected void initPdfDocument(String path) {
        try {
            logger.info("PDF file path: " + path);
            this.pdf = new PdfDocument(new PdfWriter(path));
            PageSize ps = PageSize.A4;
            this.document = new Document(pdf, ps);
            document.setTopMargin(22);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("操作错误");
            alert.setContentText("无法访问该文件，因为该文件正被另一个进程使用");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            alert.show();

            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    protected void setupPdfHeader() {
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, new TextHeaderEventHandler(document, "油气管道安全评价系统 - 评价报告"));
    }

    protected abstract void setupPdfBody(ArrayList<String> resultDataList);

    protected void setupPdfFooter() {
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandler(document));
    }

    protected void closeDocument() {
        this.document.close();
    }

    public void generatePDF(String path, ArrayList<String> resultDataList) {
        initPdfDocument(path);
        setupPdfHeader();
        setupPdfBody(resultDataList);
        setupPdfFooter();
        closeDocument();
    }

    protected class TextHeaderEventHandler implements IEventHandler {
        protected String header;
        protected Document doc;

        public TextHeaderEventHandler(Document doc, String header) {
            this.header = header;
            this.doc = doc;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();

            canvas.beginText();

            try {
                PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
                canvas.setFontAndSize(font, 12);
            } catch (IOException e) {
                e.printStackTrace();
            }

            canvas.moveText(pageSize.getWidth() / 3, pageSize.getHeight() - 20)
                    .showText(header)
                    .endText()
                    .release();
        }
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
                PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
                canvas.setFontAndSize(font, 7);
            } catch (IOException e) {
                e.printStackTrace();
            }

            canvas.moveText(doc.getLeftMargin() - 10, pageSize.getBottom() + doc.getBottomMargin() - 10)
                    .showText("页脚" + String.format("%135s", "") + "油气管道安全评价系统" + String.format("%135s", "") + "页码 " + docEvent.getDocument().getPageNumber(docEvent.getPage()) + " / " + docEvent.getDocument().getNumberOfPages())
                    .endText()
                    .release();
        }
    }

}
