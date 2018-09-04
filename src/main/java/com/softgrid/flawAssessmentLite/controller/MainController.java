package com.softgrid.flawAssessmentLite.controller;

import com.softgrid.flawAssessmentLite.entity.History;
import com.softgrid.flawAssessmentLite.handler.DatabaseHandler;
import com.softgrid.flawAssessmentLite.pdf.PdfFactory;
import com.softgrid.flawAssessmentLite.util.DateTimeUtil;
import com.softgrid.flawAssessmentLite.util.StringUtils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class MainController implements Initializable {

    private static Logger logger = Logger.getRootLogger();

    @FXML
    private TreeView<String> GB19624TreeView;

    @FXML
    private TreeView<String> GB30582TreeView;

    @FXML
    private TreeView<String> SYT6996TreeView;

    @FXML
    private TabPane GB19624TabPane;

    @FXML
    private TabPane GB30582TabPane;

    @FXML
    private TabPane SYT6996TabPane;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private DatePicker dp_from1;

    @FXML
    private DatePicker dp_to1;

    @FXML
    private DatePicker dp_from2;

    @FXML
    private DatePicker dp_to2;

    private static final LinkedHashMap<String, String> viewResolver = new LinkedHashMap<>();

    static {

        viewResolver.put("含凹陷管道的剩余强度评估", "/fxml/GB30582/GB30582B.fxml");
        viewResolver.put("含划伤凹陷管道的剩余强度评价", "/fxml/GB30582/GB30582C.fxml");

    }

    public void initialize(URL location, ResourceBundle resources) {

        buildTreeGB30582();
        //buildTreeSYT6996();

        setOnClickListener(GB30582TreeView, GB30582TabPane);
        //setOnClickListener(SYT6996TreeView, SYT6996TabPane);
        comboBox1.getItems().setAll(new ArrayList<>(viewResolver.keySet()).subList(0, 2));
        //comboBox2.getItems().setAll(new ArrayList<>(viewResolver.keySet()).subList(9, 16));
    }



    private void buildTreeGB30582() {
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> treeItemB = new TreeItem<>("含凹陷管道的剩余强度评估"); // -> B
        TreeItem<String> treeItemC = new TreeItem<>("含划伤凹陷管道的剩余强度评价"); // -> C
        root.getChildren().add(treeItemB);
        root.getChildren().add(treeItemC);


        GB30582TreeView.setRoot(root);
        GB30582TreeView.setShowRoot(false);
    }

    private void buildTreeSYT6996() {
        // build TreeView for SYT6996
    }

    private void setOnClickListener(TreeView<String> treeView, TabPane tabPane) {
        treeView.setOnMouseClicked(event -> {
            TreeItem<String> currentItem = treeView.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && currentItem.isLeaf()) {
                createNewTab(tabPane, currentItem.getValue(), viewResolver.get(currentItem.getValue()));
            }
        });
    }

    private void createNewTab(TabPane tabPane, String value, String fxml) {
        try {
            SplitPane splitPane = FXMLLoader.load(getClass().getResource(fxml));
            Tab tab = new Tab(value, splitPane);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void search(ActionEvent event) {
        String assmentType = "";
        String from = "1700-01-01 00:00:00";
        String to = DateTimeUtil.getSimpleDateFormat().format(new Date()) + " 23:59:59";

        try {
            // getUserData from fxml file
            Node node = (Node) event.getSource();
            String GB = (String) node.getUserData();

            if ("GB30582".equalsIgnoreCase(GB)) {
                if (StringUtils.isEmpty(comboBox1.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("提示");
                    alert.setHeaderText("警告");
                    alert.setContentText("请选择评价方式");
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                    alert.show();
                    return;
                }
                assmentType = comboBox1.getValue();
                if (dp_from1.getValue() != null) {
                    from = DateTimeUtil.getDateFormat().format(DateTimeUtil.LocalDateToDate(dp_from1.getValue()));
                }
                if (dp_to1.getValue() != null) {
                    to = DateTimeUtil.getSimpleDateFormat().format(DateTimeUtil.LocalDateToDate(dp_to1.getValue())) + " 23:59:59";
                }
            } else if ("GB19624".equalsIgnoreCase(GB)) {
                if (StringUtils.isEmpty(comboBox2.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("提示");
                    alert.setHeaderText("警告");
                    alert.setContentText("请选择评价方式");
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                    alert.show();
                    return;
                }
                assmentType = comboBox2.getValue();
                if (dp_from2.getValue() != null) {
                    from = DateTimeUtil.getDateFormat().format(DateTimeUtil.LocalDateToDate(dp_from2.getValue()));
                }
                if (dp_to2.getValue() != null) {
                    to = DateTimeUtil.getSimpleDateFormat().format(DateTimeUtil.LocalDateToDate(dp_to2.getValue())) + " 23:59:59";
                }
            }

            // Init tableView, and history list
            TableView<History> tableView = new TableView<>();
            ObservableList<History> histories = FXCollections.observableArrayList();

            tableView.setPadding(new Insets(8, 10, 8, 10));

            // Construct table columns
            TableColumn<History, String> idColumn = new TableColumn<>("ID");
            idColumn.setMinWidth(50);
            idColumn.setPrefWidth(50);
            idColumn.setStyle("-fx-alignment: CENTER;");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<History, String> createdDateColumn = new TableColumn<>("评价时间");
            createdDateColumn.setMinWidth(100);
            createdDateColumn.setPrefWidth(200);
            createdDateColumn.setStyle("-fx-alignment: CENTER-LEFT;");
            createdDateColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

            TableColumn<History, String> tfpipenameColumn = new TableColumn<>("管道名称");
            tfpipenameColumn.setMinWidth(100);
            tfpipenameColumn.setPrefWidth(150);
            tfpipenameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
            tfpipenameColumn.setCellValueFactory(new PropertyValueFactory<>("tfpipename"));

            TableColumn<History, String> tflocationColumn = new TableColumn<>("测量地点");
            tflocationColumn.setMinWidth(100);
            tflocationColumn.setPrefWidth(150);
            tflocationColumn.setStyle("-fx-alignment: CENTER-LEFT;");
            tflocationColumn.setCellValueFactory(new PropertyValueFactory<>("tflocation"));

            TableColumn<History, String> resultColumn = new TableColumn<>("评价结果");
            resultColumn.setMinWidth(100);
            resultColumn.setPrefWidth(200);
            resultColumn.setStyle("-fx-alignment: CENTER-LEFT;");
            resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

            TableColumn<History, Boolean> actionColumn = new TableColumn<>("操作");
            actionColumn.setMinWidth(100);
            actionColumn.setPrefWidth(100);
            actionColumn.setStyle("-fx-alignment: CENTER;");
            actionColumn.setSortable(false);
            // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
            actionColumn.setCellValueFactory(features -> new SimpleBooleanProperty(features.getValue() != null));
            // create a cell factory with a button for each row in the table.
            actionColumn.setCellFactory(column -> new ButtonCell());

            // utilize the viewResolver map to extract dbTableName
            String dbTableName = viewResolver.get(assmentType).split("/")[3].replace(".fxml", "");

            // Query data from db
            String query = "SELECT * FROM " + dbTableName;
            if (!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)) {
                query = "SELECT * FROM " + dbTableName + " WHERE CREATEDDATE BETWEEN '" + from + "' AND '" + to + "'";
            }
            logger.info(query);
            ResultSet resultSet = DatabaseHandler.execQuery(query);

            // Populate history list
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String createdDate = resultSet.getString("createdDate").replace(".0", "");
                String tfpipename = resultSet.getString("tfpipename");
                String tflocation = resultSet.getString("tflocation");
                String result = resultSet.getString("result");
                histories.add(new History(id, createdDate, tfpipename, tflocation, result, dbTableName));
            }

            tableView.setItems(histories);
            tableView.getColumns().addAll(idColumn, createdDateColumn, tfpipenameColumn, tflocationColumn, resultColumn, actionColumn);
            showHistory(tableView, GB);
            resultSet.close();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    private void showHistory(TableView tableView, String GB) {
        try {
            Tab tab;
            if ("GB19624".equalsIgnoreCase(GB)) {
                if (GB19624TabPane.getTabs().size() > 0 && "HISTORY19624".equalsIgnoreCase(GB19624TabPane.getTabs().get(0).getId())) {
                    tab = GB19624TabPane.getTabs().get(0);
                } else {
                    ScrollPane scrollPane = new ScrollPane();
                    tab = new Tab("查询结果", scrollPane);
                    tab.setId("HISTORY19624");
                    GB19624TabPane.getTabs().add(0, tab);
                }

                tab.setContent(tableView);
                GB19624TabPane.getSelectionModel().select(tab);
            } else if ("GB30582".equalsIgnoreCase(GB)) {
                if (GB30582TabPane.getTabs().size() > 0 && "HISTORY30582".equalsIgnoreCase(GB30582TabPane.getTabs().get(0).getId())) {
                    tab = GB30582TabPane.getTabs().get(0);
                } else {
                    ScrollPane scrollPane = new ScrollPane();
                    tab = new Tab("查询结果", scrollPane);
                    tab.setId("HISTORY30582");
                    GB30582TabPane.getTabs().add(0, tab);
                }

                tab.setContent(tableView);
                GB30582TabPane.getSelectionModel().select(tab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This inner class is to define the button cell
     */
    private class ButtonCell extends TableCell<History, Boolean> {
        final Button cellButton = new Button("导出PDF");

        ButtonCell() {
            cellButton.setOnAction(event -> {
                try {
                    // Since previously we defined the tableCell is a History tableCell when setCellFactory,
                    // so that we can cast the table cell item into a History object to get the History Id for generating the PDF
                    History history = (History) getTableRow().getItem();
                    logger.info("History Id: " + history.getId());

                    // Query history data from db
                    String query = "SELECT * FROM " + history.getDbTableName() + " WHERE id = " + history.getId();
                    ResultSet resultSet = DatabaseHandler.execQuery(query);

                    // Stores properties of a ResultSet object, including column count
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    ArrayList<String> resultList = new ArrayList<>(columnCount);

                    // Store ResultSet into ArrayList
                    while (resultSet.next()) {
                        // Starts from 2 because first one is ID which is not needed in PDF
                        int i = 2;
                        while (i <= columnCount) {
                            resultList.add(resultSet.getString(i++));
                        }
                    }

                    // Export PDF
                    Stage primaryStage = new Stage();
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF 文件 (*.pdf)", "*.pdf");
                    fileChooser.getExtensionFilters().add(extFilter);
                    String defaultFileName = "凹陷管道安全评价系统评价报告";
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
                        PdfFactory.exportPDF(history.getDbTableName(), selectedDirectory.getAbsolutePath(), resultList);
                    }
                } catch (SQLException e) {
                    logger.error(ExceptionUtils.getStackTrace(e));
                }
            });
        }

        // Display button if the row is not empty
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }
}
