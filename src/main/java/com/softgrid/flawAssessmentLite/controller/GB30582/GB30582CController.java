package com.softgrid.flawAssessmentLite.controller.GB30582;

import com.softgrid.flawAssessmentLite.constant.SteelGrade;
import com.softgrid.flawAssessmentLite.formula.GB30582CFormula;
import com.softgrid.flawAssessmentLite.handler.DatabaseHandler;
import com.softgrid.flawAssessmentLite.template.pdf.PdfFactory;
import com.softgrid.flawAssessmentLite.util.DateTimeUtil;
import com.softgrid.flawAssessmentLite.util.NumberUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class GB30582CController implements Initializable {

    private static Logger logger = Logger.getRootLogger();
    private static final String GB30582C = "GB30582C";

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
    private GridPane resultPane;
    @FXML
    private Label lb_result;
    @FXML
    private Label lb_datetime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().setAll(SteelGrade.values());
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            sigma_Y.setText(Integer.toString(newValue.getSigma_s()));
            sigma_R.setText(Integer.toString(newValue.getSigma_b()));
        });
        resultPane.setVisible(false);
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

            double power = GB30582CFormula.GB30582FormulaC(t, D, sigma, Cv, E, P, H0, d);


            if (power == 0) {
                ShowResult("管道失效");

            } else if (power == 1) {
                ShowResult("管道未失效");

            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * “导出PDF” 按钮方法入口
     */
    public void exportPDF() {
        Stage primaryStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory
                = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("操作错误");
            alert.setContentText("请选择一个目录！");
            alert.show();
        } else {
            logger.info("Directory to save PDF: "+selectedDirectory.getAbsolutePath());
            PdfFactory.exportPDF(GB30582C, selectedDirectory.getAbsolutePath());
        }
    }

    private void ShowResult(String result){
        String createdDate = DateTimeUtil.getDateFormat().format(new Date());
        lb_result.setText(result);
        if("管道失效".equalsIgnoreCase(result)){
            lb_result.setStyle("-fx-text-fill: RED; -fx-font-weight: bold");
        }else {
            lb_result.setStyle("-fx-text-fill: GREEN; -fx-font-weight: bold");
        }
        lb_datetime.setText(createdDate);
        resultPane.setVisible(true);
        saveHistory(result,createdDate);
    }

    private void saveHistory(String result,String createdDate){
        String excute = "INSERT INTO GB30582C("
                + "tube_t, "
                + "radius_D, "
                + "power_C, "
                + "Modulus_E, "
                + "power_P, "
                + "deep_H0, "
                + "deep_d, "
                + "sigma_Y, "
                + "sigma_R, "
                + "comboBox, "
                + "createdDate, "
                + "result) VALUES("+
                "'" + tube_t.getText() + "'," +
                "'" + radius_D.getText() + "'," +
                "'" + power_C.getText() + "'," +
                "'" + Modulus_E.getText() + "'," +
                "'" + power_P.getText() + "'," +
                "'" + deep_H0.getText() + "'," +
                "'" + deep_d.getText() + "'," +
                "'" + sigma_Y.getText() + "'," +
                "'" + sigma_R.getText() + "'," +
                "'" + comboBox.getValue() + "'," +
                "'" + createdDate + "'," +
                "'" + result + "')";
        DatabaseHandler.execAction(excute);
    }
}