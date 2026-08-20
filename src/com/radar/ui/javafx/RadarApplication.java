package com.radar.ui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RadarApplication extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("RADAR TARGET TRACKING SYSTEM");

        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Radar Target Tracking Simulator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}