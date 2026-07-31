package com.radar.model;

public class Missile extends Target {

    public Missile(
                   Coordinate position,
                   double speed,
                   double altitude) {

        super(position, speed, altitude);
    }

    @Override
    public String getType() {
        return "Missile";
    }

    @Override
    public void displayInfo() {

        System.out.println("\n========== Missile ==========");
        System.out.println("ID : " + getId());
        System.out.println("Type : " + getType());
        System.out.println("Position : " + getCoordinate());
        System.out.println("Speed : " + getSpeed() + " km/h");
        System.out.println("Altitude : " + getAltitude() + " m");
    }
}