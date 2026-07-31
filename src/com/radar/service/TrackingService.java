package com.radar.service;
import com.radar.model.Radar;
import com.radar.model.Target;
public class TrackingService {
    private Radar radar;
    public TrackingService(Radar radar) {
        this.radar = radar;
    }
    // Add target
    public void addTarget(Target target) {
        radar.addTarget(target);
    }
    // Show all targets
    public void showAllTargets() {
        if (radar.getTargets().isEmpty()) {
            System.out.println("No targets found!");
            return;
        }
        for (Target t : radar.getTargets()) {
            t.displayInfo();
        }
    }
}