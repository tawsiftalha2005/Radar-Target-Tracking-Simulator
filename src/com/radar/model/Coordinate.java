package com.radar.model;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate(){
    }
    public Coordinate(double x,double y)
    {
        this.x = x;
        this.y =y;

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double distanceTo(Coordinate other){
        double dx = this.x - other.x;
        double dy = this.y -other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    @Override
    public String toString(){
        return"(" + x + ", " + y + ")";

    }
}
