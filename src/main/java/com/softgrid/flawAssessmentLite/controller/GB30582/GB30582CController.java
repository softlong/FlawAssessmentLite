package com.softgrid.flawAssessmentLite.controller.GB30582;

import com.softgrid.flawAssessmentLite.constant.SteelGrade;
import com.softgrid.flawAssessmentLite.formula.GB30582CFormula;
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
import javafx.scene.control.ComboBox;
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

public class GB30582CController implements Initializable {

    private static Logger logger = Logger.getRootLogger();
    private static final String GB30582C = "GB30582C";
    private ArrayList<String> resultDataList = new ArrayList<>();

    @FXML
    private TextField tube_t;
    @FXML
    private TextField radius_D;
    @FXML
    private TextField power_C;
    @FXML
    private TextField Modulus_E;
    @FXML
    private TextField power_P;
    @FXML
    private TextField deep_H0;
    @FXML
    private TextField deep_d;
    @FXML
    private ComboBox<SteelGrade> comboBox;
    @FXML
    private TextField sigma_Y;
    @FXML
    private TextField sigma_R;
    @FXML
    private TextField tfpipename;

    @FXML
    private TextField tflocation;
    @FXML
    private GridPane resultPane;
    @FXML
    private Label lb_result;
    @FXML
    private Label lb_result1;
    @FXML
    private Label lb_result2;
    @FXML
    private Label lb_datetime;
    @FXML
    private HBox buttonBox;
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().setAll(SteelGrade.values());
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            sigma_Y.setText(Integer.toString(newValue.getSigma_s()));
            sigma_R.setText(Integer.toString(newValue.getSigma_b()));
        });
        resultPane.setVisible(false);
        buttonBox.setVisible(false);
    }

    public void calculate() {
        try {
            double t = NumberUtils.stringToDouble(tube_t.getText());
            double D = NumberUtils.stringToDouble(radius_D.getText());
            double Cv = NumberUtils.stringToDouble(power_C.getText());
            double E = NumberUtils.stringToDouble(Modulus_E.getText());
            double P = NumberUtils.stringToDouble(power_P.getText());
            double H0 = NumberUtils.stringToDouble(deep_H0.getText());
            double d = NumberUtils.stringToDouble(deep_d.getText());
            double sigma = NumberUtils.stringToDouble(sigma_Y.getText());

            double sita1 = GB30582CFormula.GB30582FormulaC(t, D, sigma, Cv, E, P, H0, d);
            double sigma_h =GB30582CFormula.GB30582FormulaC(P,D,t);

            if(sigma_h>sita1){
                String result1 = Double.toString(NumberUtils.formatTwoData(sigma_h));
                String result2 = Double.toString(NumberUtils.formatTwoData(sita1));
                ShowResult("管道失效",result1,result2);

            }else {
                String result1 = Double.toString(NumberUtils.formatTwoData(sigma_h));
                String result2 = Double.toString(NumberUtils.formatTwoData(sita1));
                ShowResult("管道未失效",result1,result2);

            }

        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * “导出PDF” 按钮方法入口
     */
    private void setResultDataList(String createdDate, String result,String result1,String result2) {
        this.resultDataList.clear();
        this.resultDataList.add(createdDate);
        this.resultDataList.addAll(JavaFxUtils.getNodeText(
                tfpipename, tflocation, tube_t, radius_D, comboBox, sigma_Y, sigma_R, power_C, Modulus_E, power_P, deep_H0, deep_d
        ));
        this.resultDataList.add(result1);
        this.resultDataList.add(result2);
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
            alert.show();
        } else {
            logger.info("Directory to save PDF: " + selectedDirectory.getAbsolutePath());
            PdfFactory.exportPDF(GB30582C, selectedDirectory.getAbsolutePath(), this.resultDataList);
        }
    }


    private void ShowResult(String result,String result1,String result2) {
        String createdDate = DateTimeUtil.getDateFormat().format(new Date());
        lb_result.setText(result);
        if ("管道失效".equalsIgnoreCase(result)) {
            lb_result.setStyle("-fx-text-fill: RED; -fx-font-weight: bold");
        } else {
            lb_result.setStyle("-fx-text-fill: GREEN; -fx-font-weight: bold");
        }
        buttonBox.setVisible(true);
        lb_datetime.setText(createdDate);
        resultPane.setVisible(true);
        lb_result1.setText(result1);
        lb_result2.setText(result2);
        setResultDataList(createdDate, result,result1,result2);
    }

    public void saveResult() {
        try {
            String excute = "INSERT INTO GB30582C("
                    + "createdDate, "
                    + "tfpipename, "
                    + "tflocation, "
                    + "tube_t, "
                    + "radius_D, "
                    + "comboBox, "
                    + "sigma_Y, "
                    + "sigma_R, "
                    + "power_C, "
                    + "Modulus_E, "
                    + "power_P, "
                    + "deep_H0, "
                    + "deep_d, "
                    + "result1,"
                    + "result2,"
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