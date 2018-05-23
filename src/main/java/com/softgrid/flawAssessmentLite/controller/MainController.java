package com.softgrid.flawAssessmentLite.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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

    private static final Map<String, String> viewResolver;

    static {
        Map<String, String> fxmlMap = new HashMap<>();

        // GB30582 views
        fxmlMap.put("含凹陷管道的剩余强度评估", "/fxml/GB30582/GB30582B.fxml");
        fxmlMap.put("含划伤凹陷管道的剩余强度评价", "/fxml/GB30582/GB30582C.fxml");
        viewResolver = Collections.unmodifiableMap(fxmlMap);
    }

    public void initialize(URL location, ResourceBundle resources) {
        //buildTreeGB19624();
        buildTreeGB30582();
        //buildTreeSYT6996();
        //setOnClickListener(GB19624TreeView, GB19624TabPane);
        setOnClickListener(GB30582TreeView, GB30582TabPane);
        //setOnClickListener(SYT6996TreeView, SYT6996TabPane);
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
}
