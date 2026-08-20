package com.radar.model;
public abstract class Target {
    private static int counter = 1;   // 🔥 auto ID generator
    private int id;                  // 🔥 int ID
    private Coordinate coordinate;
    private double speed;
    private double altitude;
    private Status status = Status.NEW;
    public Target(Coordinate coordinate,
                  double speed,
                  double altitude) {
        this.id = counter++;         // 🔥 auto assign ID
        this.coordinate = coordinate;
        this.speed = speed;
        this.altitude = altitude;
    }
    public enum Status {
        NEW,
        DETECTED,
        AUTHORIZED,
        UNAUTHORIZED,
        DESTROYED
    }
    public int getId() {             // 🔥 int getter
        return id;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    public abstract String getType();
    public abstract void displayInfo();
}