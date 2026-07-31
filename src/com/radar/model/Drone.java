package com.radar.model;

public class Drone extends Target {

    public Drone(String id,
                 Coordinate coordinate,
                 double speed,
                 double altitude) {

        super(id, coordinate, speed, altitude);
    }

    @Override
    public String getType() {
        return "Drone";
    }

    @Override
    public void displayInfo() {

        System.out.println("\n========== Drone ==========");
        System.out.println("ID : " + getId());
        System.out.println("Type : " + getType());
        System.out.println("Position : " + getCoordinate());
        System.out.println("Speed : " + getSpeed() + " km/h");
        System.out.println("Altitude : " + getAltitude() + " m");
    }
}