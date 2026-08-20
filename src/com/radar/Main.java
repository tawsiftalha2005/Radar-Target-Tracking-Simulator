package com.radar;
import com.radar.ui.javafx.RadarApplication;
public class Main {
    public static void main(String[] args) {
        // RadarApplication.launch(args);
        javafx.application.Application.launch(com.radar.ui.javafx.RadarApplication.class, args);
    }
}