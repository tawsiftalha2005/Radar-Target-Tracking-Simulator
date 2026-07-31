package com.radar.model;
public class Aircraft extends Target {
    // 🔥 Updated constructor (NO id)
    public Aircraft(Coordinate position,
                    double speed,
                    double altitude) {

        super(position, speed, altitude);
    }

    @Override
    public String getType() {
        return "Aircraft";
    }

    @Override
    public void displayInfo() {
        System.out.println("\n================= Aircraft ================");
        System.out.println("ID : " + getId());
        System.out.println("Type : " + getType());
        System.out.println("Position : " + getCoordinate());
        System.out.println("Speed : " + getSpeed() + " km/h");
        System.out.println("Altitude : " + getAltitude() + " m");
    }
}