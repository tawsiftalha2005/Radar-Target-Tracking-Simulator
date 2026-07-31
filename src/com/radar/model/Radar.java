package com.radar.model;

import java.util.ArrayList;
import java.util.List;

public class Radar {

    private String radarId;
    private String radarName;
    private double detectionRange;

    private List<Target> targets;

    public Radar(String radarId,
                 String radarName,
                 double detectionRange) {

        this.radarId = radarId;
        this.radarName = radarName;
        this.detectionRange = detectionRange;

        targets = new ArrayList<>();
    }

    public String getRadarId() {
        return radarId;
    }

    public String getRadarName() {
        return radarName;
    }

    public double getDetectionRange() {
        return detectionRange;
    }

    public void setDetectionRange(double detectionRange) {
        this.detectionRange = detectionRange;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void addTarget(Target target) {
        targets.add(target);
    }

    public void removeTarget(Target target) {
        targets.remove(target);
    }

    public void displayRadarInfo() {

        System.out.println("\n==============================");
        System.out.println("Radar ID : " + radarId);
        System.out.println("Radar Name : " + radarName);
        System.out.println("Detection Range : " + detectionRange + " km");
        System.out.println("Total Targets : " + targets.size());
        System.out.println("==============================");
    }
}