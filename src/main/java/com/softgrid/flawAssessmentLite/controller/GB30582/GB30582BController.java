package com.softgrid.flawAssessmentLite.controller.GB30582;

import com.softgrid.flawAssessmentLite.formula.GB30582BFormula;
import com.softgrid.flawAssessmentLite.handler.DatabaseHandler;
import com.softgrid.flawAssessmentLite.pdf.PdfFactory;
import com.softgrid.flawAssessmentLite.util.DateTimeUtil;
import com.softgrid.flawAssessmentLite.util.JavaFxUtils;
import com.softgrid.flawAssessmentLite.util.NumberUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class GB30582BController implements Initializable {

    private static Logger logger = Logger.getRootLogger();
    private static final String GB30582B = "GB30582B";
    private ArrayList<String> resultDataList = new ArrayList<>();

    /*管道壁厚t*/
    @FXML
    private TextField tube_t;
    /*管道内半径R0*/
    @FXML
    private TextField radius_R0;
    /*管道横截面曲率半径R1*/
    @FXML
    private TextField radius_R1;
    /*管道轴向面曲率半径R2*/
    @FXML
    private TextField radius_R2;
    /*凹陷轴线长度L*/
    @FXML
    private TextField length_L;
    /*凹陷深度d*/
    @FXML
    private TextField deep_d;
    @FXML
    private TextField tfpipename;

    @FXML
    private TextField tflocation;

    @FXML
    private Label lb_result;
    @FXML
    private Label lb_result1;
    @FXML
    private Label lb_datetime;
    @FXML
    private GridPane resultPane;
    @FXML
    private HBox buttonBox;
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultPane.setVisible(false);
        buttonBox.setVisible(false);
    }

    public void calculate() {
        try {
            double t = NumberUtils.stringToDouble(tube_t.getText());
            double R0 = NumberUtils.stringToDouble(radius_R0.getText());
            double R1 = NumberUtils.stringToDouble(radius_R1.getText());
            double R2 = NumberUtils.stringToDouble(radius_R2.getText());
            double L = NumberUtils.stringToDouble(length_L.getText());
            double d = NumberUtils.stringToDouble(deep_d.getText());

            double epsilonMax = GB30582BFormula.FormulaB(t, R0, R1, R2, L, d);


            if (epsilonMax > 0.06) {

                String result1 = Double.toString(NumberUtils.formatTwoData(epsilonMax));
                ShowResult("管线需要修复",result1);

            } else {
                String result1 = Double.toString(NumberUtils.formatTwoData(epsilonMax));
                ShowResult("管线继续运行",result1);

            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * “导出PDF” 按钮方法入口
     */
    private void setResultDataList(String createdDate, String result,String result1) {
        this.resultDataList.clear();
        this.resultDataList.add(createdDate);
        this.resultDataList.addAll(JavaFxUtils.getNodeText(
                tfpipename, tflocation, tube_t, radius_R0, radius_R1, radius_R2, length_L, deep_d
        ));
        this.resultDataList.add(result1);
        this.resultDataList.add(result);
    }

    public void exportPDF() {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF 文件 (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        String defaultFileName = "油气管道凹陷安全评价系统评价报告";
        fileChooser.setInitialFileName(defaultFileName + ".pdf");
        File selectedDirectory = fileChooser.showSaveDialog(primaryStage);
        if (selectedDirectory == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("操作错误");
            alert.setContentText("请选择一个目录");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            alert.show();
        } else {
            logger.info("Directory to save PDF: " + selectedDirectory.getAbsolutePath());
            PdfFactory.exportPDF(GB30582B, selectedDirectory.getAbsolutePath(), this.resultDataList);
        }
    }

    private void ShowResult(String result,String result1) {
        String createdDate = DateTimeUtil.getDateFormat().format(new Date());
        lb_result.setText(result);
        if ("管线需要修复".equalsIgnoreCase(result)) {
            lb_result.setStyle("-fx-text-fill: RED; -fx-font-weight: bold");
        } else {
            lb_result.setStyle("-fx-text-fill: GREEN; -fx-font-weight: bold");
        }
        buttonBox.setVisible(true);
        lb_datetime.setText(createdDate);
        resultPane.setVisible(true);
        lb_result1.setText(result1);
        setResultDataList(createdDate, result, result1);
    }

    public void saveResult() {
        try {
            String excute = "INSERT INTO GB30582B("
                    + "createdDate, "
                    + "tfpipename, "
                    + "tflocation, "
                    + "tube_t, "
                    + "radius_R0, "
                    + "radius_R1, "
                    + "radius_R2, "
                    + "length_L, "
                    + "deep_d, "
                    + "result1, "
                    + "result) VALUES(";
            StringBuilder sb = new StringBuilder(excute);
            int len = this.resultDataList.size();
            for (int i = 0; i < len - 1; i++) {
                sb.append("'" + this.resultDataList.get(i) + "',");
            }
            sb.append("'" + this.resultDataList.get(len - 1) + "')");

            DatabaseHandler.execAction(sb.toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("信息");
            alert.setContentText("保存成功");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("提示");
            alert.setHeaderText("错误");
            alert.setContentText("对不起，保存失败");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            alert.show();
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public void resetData() {
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children
                ) {
            if (node instanceof TextField) {
                ((TextField) node).setText(null);
            }
        }
        resultPane.setVisible(false);
    }
}