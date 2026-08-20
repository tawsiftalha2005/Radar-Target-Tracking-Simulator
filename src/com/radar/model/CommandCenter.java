package com.radar.model;

public class CommandCenter {

    private String commandCenterId;
    private String name;

    private Airbase airbase;
    private MissileBattery missileBattery;

    public CommandCenter(
            String commandCenterId,
            String name,
            Airbase airbase,
            MissileBattery missileBattery
    ) {
        this.commandCenterId = commandCenterId;
        this.name = name;
        this.airbase = airbase;
        this.missileBattery = missileBattery;
    }

    public String getCommandCenterId() {
        return commandCenterId;
    }

    public String getName() {
        return name;
    }

    public ThreatLevel analyzeThreat(Target target) {

        if (target == null) {
            throw new IllegalArgumentException(
                    "Target cannot be null."
            );
        }

        if (target.getStatus() != Target.Status.UNAUTHORIZED) {
            return ThreatLevel.LOW;
        }

        if (target.getType().equalsIgnoreCase("Missile")) {

            return ThreatLevel.CRITICAL;
        }

        if (target.getType().equalsIgnoreCase("Aircraft")) {

            if (target.getSpeed() >= 700) {
                return ThreatLevel.HIGH;
            }

            return ThreatLevel.MEDIUM;
        }

        if (target.getType().equalsIgnoreCase("Drone")) {

            if (target.getSpeed() >= 200) {
                return ThreatLevel.HIGH;
            }

            return ThreatLevel.MEDIUM;
        }

        return ThreatLevel.MEDIUM;
    }

    public String makeInterceptionDecision(Target target) {

        ThreatLevel threatLevel =
                analyzeThreat(target);

        switch (threatLevel) {

            case CRITICAL:

                return "GROUND_INTERCEPTOR";

            case HIGH:

                if (target.getType()
                        .equalsIgnoreCase("Aircraft")) {

                    return "FIGHTER_INTERCEPTOR";
                }

                return "GROUND_INTERCEPTOR";

            case MEDIUM:

                return "GROUND_INTERCEPTOR";

            case LOW:

            default:

                return "MONITOR";
        }
    }

    public void processTarget(Target target) {

        if (target == null) {
            throw new IllegalArgumentException(
                    "Target cannot be null."
            );
        }

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "         COMMAND CENTRE"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Command Centre : " + name
        );

        System.out.println(
                "Target ID      : " + target.getId()
        );

        System.out.println(
                "Target Type    : " + target.getType()
        );

        System.out.println(
                "Target Status  : " + target.getStatus()
        );

        if (target.getStatus()
                != Target.Status.UNAUTHORIZED) {

            System.out.println(
                    "Decision       : MONITOR"
            );

            System.out.println(
                    "========================================"
            );

            return;
        }

        ThreatLevel threatLevel =
                analyzeThreat(target);

        String decision =
                makeInterceptionDecision(target);

        System.out.println(
                "Threat Level   : " + threatLevel
        );

        System.out.println(
                "Decision       : " + decision
        );

        /*
         * Command Centre only gives the command.
         *
         * Actual interceptor deployment will be
         * handled by SimulationEngine through
         * Airbase or MissileBattery.
         */

        if (decision.equals("FIGHTER_INTERCEPTOR")) {

            System.out.println(
                    "Command        : Deploy fighter from Airbase"
            );

        } else if (decision.equals("GROUND_INTERCEPTOR")) {

            System.out.println(
                    "Command        : Deploy interceptor from Missile Battery"
            );

        } else {

            System.out.println(
                    "Command        : Continue monitoring"
            );
        }

        System.out.println(
                "========================================"
        );
    }
}