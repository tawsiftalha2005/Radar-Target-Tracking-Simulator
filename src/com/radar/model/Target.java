package com.radar.model;

public abstract class Target {
    private String id;
    private Coordinate coordinate;
    private double speed;
    private double altitude;

    public Target(String id,
                  Coordinate coordinate,
                  double speed,
                  double altitude){
        this.id = id;
        this.coordinate = coordinate;
        this.speed = speed;
        this.altitude = altitude;
    }
    public String getId(){
        return id;

    }
    public Coordinate getCoordinate(){
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }
    public double getSpeed(){
        return speed;
    }
    public void setSpeed(double speed){
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
