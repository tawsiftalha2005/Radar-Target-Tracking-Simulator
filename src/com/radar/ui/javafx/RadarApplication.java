package com.radar.ui.javafx;

import com.radar.simulation.SimulationEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RadarApplication extends javafx.application.Application {

    private SimulationEngine engine;
    private RadarPane radarPane;
    private VBox eventLogBox;
    private Label statusLabel;
    private Timeline timeline;
    private boolean running = true;

    @Override
    public void start(Stage stage) {

        engine = new SimulationEngine();
        radarPane = new RadarPane();
        radarPane.setEngine(engine);

        BorderPane root = new BorderPane();
        root.setTop(createControlPanel());
        root.setCenter(radarPane);
        root.setRight(createEventLogPanel());
        root.setBottom(createStatusBar());

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("Radar Target Tracking Simulator");
        stage.setScene(scene);
        stage.show();

        startSimulationLoop();
    }

    private HBox createControlPanel() {

        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setStyle("-fx-background-color: #1a1a1a;");

        Label title = new Label("RADAR TARGET TRACKING SIMULATOR");
        title.setStyle("-fx-text-fill: #00ff00; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label autoSpawnLabel = new Label("AUTO-SPAWN ACTIVE");
        autoSpawnLabel.setStyle("-fx-text-fill: #00ff00; -fx-font-size: 12px;");

        Button toggleBtn = createButton("Pause");
        toggleBtn.setOnAction(e -> toggleSimulation(toggleBtn));

        controls.getChildren().addAll(title, autoSpawnLabel, toggleBtn);
        return controls;
    }

    private VBox createEventLogPanel() {

        VBox panel = new VBox(5);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(280);
        panel.setStyle("-fx-background-color: #0d0d0d;");

        Label logTitle = new Label("EVENT LOG");
        logTitle.setStyle("-fx-text-fill: #00ff00; -fx-font-weight: bold;");

        eventLogBox = new VBox(3);
        eventLogBox.setStyle("-fx-background-color: #001100; -fx-padding: 5;");

        panel.getChildren().addAll(logTitle, eventLogBox);
        return panel;
    }

    private Label createStatusBar() {

        statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: #00ff00; -fx-background-color: #1a1a1a; -fx-padding: 5;");
        updateStatus();
        return statusLabel;
    }

    private Button createButton(String text) {

        Button btn = new Button(text);
        btn.setStyle("-fx-text-fill: #00ff00; -fx-background-color: #003300; -fx-border-color: #00ff00;");
        return btn;
    }

    private void toggleSimulation(Button btn) {

        if (running) {
            timeline.stop();
            btn.setText("Resume");
        } else {
            timeline.play();
            btn.setText("Pause");
        }
        running = !running;
    }

    private void startSimulationLoop() {

        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            engine.step();
            radarPane.update();
            updateEventLog();
            updateStatus();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateEventLog() {

        eventLogBox.getChildren().clear();

        for (String log : engine.getEventLog()) {
            Label label = new Label(log);
            label.setStyle("-fx-text-fill: #00ff00; -fx-font-size: 11px;");
            eventLogBox.getChildren().add(label);
        }
    }

    private void updateStatus() {

        int targets = engine.getTargets().size();
        int interceptors = engine.getInterceptors().size();
        statusLabel.setText("Targets: " + targets + "  |  Interceptors: " + interceptors + "  |  Status: " + (running ? "RUNNING" : "PAUSED"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
