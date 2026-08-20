package com.radar.model;

public class Interceptor {

    private String interceptorId;
    private String type;
    private Coordinate coordinate;
    private Target target;
    private boolean active;

    public Interceptor(
            String interceptorId,
            String type,
            Coordinate coordinate
    ) {
        this.interceptorId = interceptorId;
        this.type = type;
        this.coordinate = coordinate;
        this.active = false;
    }

    public String getInterceptorId() {
        return interceptorId;
    }

    public String getType() {
        return type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Target getTarget() {
        return target;
    }

    public boolean isActive() {
        return active;
    }

    public void assignTarget(Target target) {

        if (target == null) {
            throw new IllegalArgumentException(
                    "Target cannot be null."
            );
        }

        this.target = target;
        this.active = true;
    }

    public void moveTowardsTarget() {

        if (!active || target == null) {
            return;
        }

        double currentX = coordinate.getX();
        double currentY = coordinate.getY();

        double targetX =
                target.getCoordinate().getX();

        double targetY =
                target.getCoordinate().getY();

        double dx = targetX - currentX;
        double dy = targetY - currentY;

        double distance =
                Math.sqrt(dx * dx + dy * dy);

        if (distance <= 1.0) {
            return;
        }

        double speed = 2.0;

        double newX =
                currentX + (dx / distance) * speed;

        double newY =
                currentY + (dy / distance) * speed;

        coordinate.setX(newX);
        coordinate.setY(newY);
    }
}