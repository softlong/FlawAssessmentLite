package com.softgrid.flawAssessmentLite;

import com.softgrid.flawAssessmentLite.handler.DatabaseHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlawAssessmentLite extends Application {
	
	private DatabaseHandler databaseHandler;

    @Override
    public void init() throws Exception {
        super.init();
        this.databaseHandler =  DatabaseHandler.getInstance();
        // Perform initializations
    }

    @Override
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(root);

        window.setTitle("凹陷管道安全评定系统");
        window.setScene(scene);
        window.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        databaseHandler.closeConnection();
        // Perform Cleanup
    }

    public static void main(String[] args) {
        launch(args);
    }

}
