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
    private Label lb_result;
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

                ShowResult("管线需要修复");

            } else {
                ShowResult("管线继续运行");

            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

    }
    private void setResultDataList(String createdDate,String result) {
        this.resultDataList.add(createdDate);
        this.resultDataList.addAll(JavaFxUtils.getNodeText(
                tube_t, radius_R0, radius_R1, radius_R2, length_L, deep_d
        ));
        this.resultDataList.add(result);
    }
    public void exportPDF() {
        Stage primaryStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("操作错误");
            alert.setContentText("请选择一个目录");
            alert.show();
        } else {
            logger.info("Directory to save PDF: " + selectedDirectory.getAbsolutePath());
            PdfFactory.exportPDF(GB30582B, selectedDirectory.getAbsolutePath(), this.resultDataList);
        }
    }

    public void saveResult(){

    }

        private void ShowResult(String result){
            String createdDate = DateTimeUtil.getDateFormat().format(new Date());
            lb_result.setText(result);
            if("管线需要修复".equalsIgnoreCase(result)){
                lb_result.setStyle("-fx-text-fill: RED; -fx-font-weight: bold");
            }else {
                lb_result.setStyle("-fx-text-fill: GREEN; -fx-font-weight: bold");
            }
            lb_datetime.setText(createdDate);
            buttonBox.setVisible(true);
            resultPane.setVisible(true);
            saveHistory(result,createdDate);
            setResultDataList(createdDate,result);
        }

        private void saveHistory(String result,String createdDate){
            String excute = "INSERT INTO GB30582B("
                    + "tube_t, "
                    + "radius_R0, "
                    + "radius_R1, "
                    + "radius_R2, "
                    + "length_L, "
                    + "deep_d, "
                    + "createdDate, "
                    + "result) VALUES("+
                    "'" + tube_t.getText() + "'," +
                    "'" + radius_R0.getText() + "'," +
                    "'" + radius_R1.getText() + "'," +
                    "'" + radius_R2.getText() + "'," +
                    "'" + length_L.getText() + "'," +
                    "'" + deep_d.getText() + "'," +
                    "'" + createdDate + "'," +
                    "'" + result + "')";
            DatabaseHandler.execAction(excute);
        }
    public void resetData(){
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node: children
                ) {
            if ( node instanceof TextField){
                ((TextField)node).setText(null);
            }
        }
        resultPane.setVisible(false);
    }
}