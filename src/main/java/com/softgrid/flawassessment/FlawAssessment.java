package com.softgrid.flawassessment;

import com.softgrid.flawassessment.handler.DatabaseHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlawAssessment extends Application {
	
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

        window.setTitle("管道凹陷安全评定系统");
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
