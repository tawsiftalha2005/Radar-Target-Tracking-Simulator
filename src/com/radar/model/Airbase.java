package com.radar.model;

public class Airbase {

    private String airbaseId;
    private String name;
    private boolean available;

    public Airbase(String airbaseId, String name) {
        this.airbaseId = airbaseId;
        this.name = name;
        this.available = true;
    }

    public String getAirbaseId() {
        return airbaseId;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public Interceptor launchFighter(Target target) {

        if (!available) {
            System.out.println(
                    "Airbase is currently unavailable."
            );
            return null;
        }

        if (target == null) {
            System.out.println(
                    "Cannot launch fighter: target not found."
            );
            return null;
        }

        // Fighter starts from the airbase position
        Coordinate fighterPosition =
                new Coordinate(20, 18);

        // Create fighter interceptor
        Interceptor fighter =
                new Interceptor(
                        "F01",
                        "FIGHTER",
                        fighterPosition
                );

        // Assign target
        fighter.assignTarget(target);

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "             AIRBASE"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Airbase       : " + name
        );

        System.out.println(
                "Target ID     : " + target.getId()
        );

        System.out.println(
                "Target Type   : " + target.getType()
        );

        System.out.println(
                "Interceptor   : " + fighter.getInterceptorId()
        );

        System.out.println(
                "Type          : " + fighter.getType()
        );

        System.out.println(
                "Status        : FIGHTER DEPLOYED"
        );

        System.out.println(
                "========================================"
        );

        return fighter;
    }
}