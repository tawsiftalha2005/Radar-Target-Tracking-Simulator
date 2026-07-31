package com.radar;

import com.radar.model.*;

public class Main {

    public static void main(String[] args) {

        Radar radar = new Radar(
                "R001",
                "Bangladesh Air Defence Radar",
                500
        );

        Target aircraft = new Aircraft(
                "A101",
                new Coordinate(120, 80),
                850,
                10000
        );

        Target drone = new Drone(
                "D201",
                new Coordinate(50, 30),
                120,
                500
        );

        Target missile = new Missile(
                "M301",
                new Coordinate(300, 100),
                1500,
                2000
        );

        radar.addTarget(aircraft);
        radar.addTarget(drone);
        radar.addTarget(missile);

        radar.displayRadarInfo();

        for (Target target : radar.getTargets()) {
            target.displayInfo();
        }
    }
}