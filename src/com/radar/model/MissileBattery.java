package com.radar.model;

public class MissileBattery {

    private String batteryId;
    private String name;
    private boolean available;

    public MissileBattery(String batteryId, String name) {
        this.batteryId = batteryId;
        this.name = name;
        this.available = true;
    }

    public String getBatteryId() {
        return batteryId;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public Interceptor launchInterceptor(Target target) {

        if (!available) {
            System.out.println(
                    "Missile Battery is currently unavailable."
            );
            return null;
        }

        if (target == null) {
            System.out.println(
                    "Cannot launch interceptor: target not found."
            );
            return null;
        }

        // Interceptor starts from missile battery
        Coordinate interceptorPosition =
                new Coordinate(20, 18);

        // Create ground interceptor
        Interceptor interceptor =
                new Interceptor(
                        "G01",
                        "GROUND_INTERCEPTOR",
                        interceptorPosition
                );

        // Assign target
        interceptor.assignTarget(target);

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "          MISSILE BATTERY"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Battery       : " + name
        );

        System.out.println(
                "Target ID     : " + target.getId()
        );

        System.out.println(
                "Target Type   : " + target.getType()
        );

        System.out.println(
                "Interceptor   : " + interceptor.getInterceptorId()
        );

        System.out.println(
                "Type          : " + interceptor.getType()
        );

        System.out.println(
                "Status        : INTERCEPTOR DEPLOYED"
        );

        System.out.println(
                "========================================"
        );

        return interceptor;
    }
}