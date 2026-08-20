package com.radar.simulation;

import com.radar.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SimulationEngine {

    private boolean running = true;

    private final int FPS = 5;
    private final int WIDTH = 40;
    private final int HEIGHT = 20;
    private static final int MAX_TARGETS = 8;
    private static final double SPAWN_CHANCE_PER_TICK = 0.08;

    private List<Target> targets = new ArrayList<>();
    private List<Interceptor> interceptors = new ArrayList<>();
    private List<CollisionEffect> collisions = new ArrayList<>();

    private Random random = new Random();
    private List<String> eventLog = new ArrayList<>();

    private char[][] grid;

    // Defence system
    private CommandCenter commandCenter;
    private Airbase airbase;
    private MissileBattery missileBattery;

    public SimulationEngine() {

        grid = new char[HEIGHT][WIDTH];

        // Create Airbase
        airbase = new Airbase(
                "AB001",
                "Main Airbase"
        );

        // Create Missile Battery
        missileBattery = new MissileBattery(
                "MB001",
                "Main Missile Battery"
        );

        // Create Command Centre
        commandCenter = new CommandCenter(
                "CC001",
                "National Air Defence Command",
                airbase,
                missileBattery
        );

        // Create initial targets
        spawnInitialTargets();
    }

    private void logEvent(String message) {

        eventLog.add(message);

        if (eventLog.size() > 15) {
            eventLog.remove(0);
        }
    }

    /**
     * Detect new targets using radar.
     */
    private void detectTargets() {

        for (Target t : targets) {

            if (t.getStatus() == Target.Status.NEW) {

                t.setStatus(Target.Status.DETECTED);

                logEvent(
                        t.getType()
                                + " #"
                                + t.getId()
                                + " detected by radar"
                );

                // Random authorization for simulation
                if (random.nextBoolean()) {

                    t.setStatus(Target.Status.AUTHORIZED);

                    logEvent(
                            t.getType()
                                    + " #"
                                    + t.getId()
                                    + " authorized"
                    );

                } else {

                    t.setStatus(Target.Status.UNAUTHORIZED);

                    logEvent(
                            "⚠ Unauthorized "
                                    + t.getType()
                                    + " #"
                                    + t.getId()
                    );

                    processUnauthorizedTarget(t);
                }
            }
        }
    }

    /**
     * Sends unauthorized target to Command Centre.
     */
    private void processUnauthorizedTarget(Target target) {

        logEvent(
                "→ Target #"
                        + target.getId()
                        + " data sent to Command Centre"
        );

        ThreatLevel threatLevel =
                commandCenter.analyzeThreat(target);

        String decision =
                commandCenter.makeInterceptionDecision(target);

        logEvent(
                "Command Centre: Target #"
                        + target.getId()
                        + " | Threat: "
                        + threatLevel
        );

        logEvent(
                "Command Centre Decision: "
                        + decision
        );

        /*
         * Ask Command Centre to process
         * and send the command to the
         * appropriate defence unit.
         */
        commandCenter.processTarget(target);

        /*
         * Create an interceptor according
         * to the Command Centre decision.
         */
        if (decision.equals("FIGHTER_INTERCEPTOR")) {

            Interceptor fighter =
                    airbase.launchFighter(target);

            if (fighter != null) {

                interceptors.add(fighter);

                logEvent(
                        "✈ Fighter "
                                + fighter.getInterceptorId()
                                + " launched for Target #"
                                + target.getId()
                );
            }

        } else if (decision.equals("GROUND_INTERCEPTOR")) {

            Interceptor interceptor =
                    missileBattery.launchInterceptor(target);

            if (interceptor != null) {

                interceptors.add(interceptor);

                logEvent(
                        "🚀 Ground Interceptor "
                                + interceptor.getInterceptorId()
                                + " launched for Target #"
                                + target.getId()
                );
            }
        }
    }

    /**
     * Creates initial targets.
     */
    private void spawnInitialTargets() {

        for (int i = 0; i < 3; i++) {
            spawnRandomTarget();
        }
    }

    /** Adds one randomly selected aircraft, drone, or missile to the radar. */
    private void spawnRandomTarget() {

            int x = 2 + random.nextInt(WIDTH - 4);
            int y = 1 + random.nextInt(4);

            Coordinate position =
                    new Coordinate(x, y);

            int type =
                    random.nextInt(3);

            Target target;

            if (type == 0) {

                target = new Aircraft(
                        position,
                        800,
                        10000
                );

            } else if (type == 1) {

                target = new Drone(
                        position,
                        120,
                        500
                );

            } else {

                target = new Missile(
                        position,
                        1500,
                        2000
                );
            }

            targets.add(target);
            logEvent(target.getType() + " #" + target.getId() + " entered radar area");
    }

    /**
     * Advances the simulation by one tick.
     */
    public void step() {
        update();
    }

    /**
     * Starts the simulation.
     */
    public void start() {

        long frameDelay =
                1000 / FPS;

        while (running) {

            long startTime =
                    System.currentTimeMillis();

            update();
            render();

            long endTime =
                    System.currentTimeMillis();

            long elapsed =
                    endTime - startTime;

            long sleepTime =
                    frameDelay - elapsed;

            if (sleepTime > 0) {

                try {

                    Thread.sleep(sleepTime);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    running = false;

                    logEvent(
                            "Simulation interrupted"
                    );
                }
            }
        }
    }

    /**
     * Updates the complete simulation.
     */
    public void update() {

        spawnTargetsRandomly();
        updateCollisionEffects();

        // Clear radar grid
        for (int i = 0; i < HEIGHT; i++) {

            for (int j = 0; j < WIDTH; j++) {

                grid[i][j] = ' ';
            }
        }

        // Radar position
        int radarX = WIDTH / 2;
        int radarY = HEIGHT - 3;

        grid[radarY][radarX] = 'R';

        // Move targets
        moveTargets();

        // Detect targets
        detectTargets();

        // Move interceptors
        updateInterceptors();

        // Draw targets
        drawTargets();

        // Draw interceptors
        drawInterceptors();
    }

    private void spawnTargetsRandomly() {
        if (targets.size() < MAX_TARGETS && random.nextDouble() < SPAWN_CHANCE_PER_TICK) {
            spawnRandomTarget();
        }
    }

    private void updateCollisionEffects() {
        Iterator<CollisionEffect> iterator = collisions.iterator();
        while (iterator.hasNext()) {
            CollisionEffect collision = iterator.next();
            collision.remainingTicks--;
            if (collision.remainingTicks <= 0) {
                iterator.remove();
            }
        }
    }

    /**
     * Moves all targets.
     */
    private void moveTargets() {

        for (Target t : targets) {

            double x =
                    t.getCoordinate().getX();

            double y =
                    t.getCoordinate().getY();

            if (t instanceof Aircraft) {

                x += 0.5;
                y += 0.2;

            } else if (t instanceof Drone) {

                x += random.nextInt(3) - 1;
                y += random.nextInt(2);

            } else if (t instanceof Missile) {

                y += 1.5;
            }

            t.getCoordinate().setX(x);
            t.getCoordinate().setY(y);
        }

        // Remove targets outside radar area
        targets.removeIf(t ->
                t.getCoordinate().getY() >= HEIGHT
                        || t.getCoordinate().getX() >= WIDTH
                        || t.getCoordinate().getX() < 0
        );
    }

    /**
     * Moves interceptors toward their assigned targets.
     */
    private void updateInterceptors() {

        Iterator<Interceptor> iterator =
                interceptors.iterator();

        while (iterator.hasNext()) {

            Interceptor interceptor =
                    iterator.next();

            Target target =
                    interceptor.getTarget();

            // Target no longer exists
            if (target == null
                    || !targets.contains(target)) {

                iterator.remove();

                continue;
            }

            // Move interceptor toward target
            interceptor.moveTowardsTarget();

            double distance =
                    calculateDistance(
                            interceptor.getCoordinate(),
                            target.getCoordinate()
                    );

            logEvent(
                    interceptor.getInterceptorId()
                            + " moving toward Target #"
                            + target.getId()
                            + " | Distance: "
                            + String.format("%.2f", distance)
            );

            if (distance <= 1.0) {

                logEvent(
                        "💥 INTERCEPTION SUCCESSFUL!"
                );

                logEvent(
                        "Target #"
                                + target.getId()
                                + " intercepted by "
                                + interceptor.getInterceptorId()
                );

                target.setStatus(Target.Status.DESTROYED);

                collisions.add(new CollisionEffect(
                        new Coordinate(target.getCoordinate().getX(), target.getCoordinate().getY()), 5));

                targets.remove(target);

                iterator.remove();
            }
        }
    }

    /**
     * Calculates distance between two coordinates.
     */
    private double calculateDistance(
            Coordinate first,
            Coordinate second
    ) {

        double dx =
                first.getX() - second.getX();

        double dy =
                first.getY() - second.getY();

        return Math.sqrt(
                dx * dx + dy * dy
        );
    }

    /**
     * Draws targets on radar.
     */
    private void drawTargets() {

        for (Target t : targets) {

            int x =
                    (int) t.getCoordinate().getX();

            int y =
                    (int) t.getCoordinate().getY();

            if (x >= 0
                    && x < WIDTH
                    && y >= 0
                    && y < HEIGHT) {

                grid[y][x] =
                        getTargetSymbol(t);
            }
        }
    }

    /**
     * Draws interceptors on radar.
     */
    private void drawInterceptors() {

        for (Interceptor interceptor : interceptors) {

            int x =
                    (int) interceptor.getCoordinate().getX();

            int y =
                    (int) interceptor.getCoordinate().getY();

            if (x >= 0
                    && x < WIDTH
                    && y >= 0
                    && y < HEIGHT) {

                if (interceptor.getType()
                        .equals("FIGHTER")) {

                    grid[y][x] = 'F';

                } else {

                    grid[y][x] = 'I';
                }
            }
        }
    }

    /**
     * Returns target symbol.
     */
    private char getTargetSymbol(Target target) {

        if (target instanceof Aircraft) {

            return target.getStatus()
                    == Target.Status.UNAUTHORIZED
                    ? 'a'
                    : 'A';
        }

        if (target instanceof Drone) {

            return target.getStatus()
                    == Target.Status.UNAUTHORIZED
                    ? 'd'
                    : 'D';
        }

        if (target instanceof Missile) {

            return target.getStatus()
                    == Target.Status.UNAUTHORIZED
                    ? 'm'
                    : 'M';
        }

        return '?';
    }

    /**
     * Renders the radar screen.
     */
    public void render() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Top border
        System.out.print("+");

        for (int i = 0; i < WIDTH; i++) {
            System.out.print("-");
        }

        System.out.println("+");

        // Radar grid
        for (int i = 0; i < HEIGHT; i++) {

            System.out.print("|");

            for (int j = 0; j < WIDTH; j++) {

                System.out.print(grid[i][j]);
            }

            System.out.println("|");
        }

        // Bottom border
        System.out.print("+");

        for (int i = 0; i < WIDTH; i++) {
            System.out.print("-");
        }

        System.out.println("+");

        System.out.println(
                "RADAR ACTIVE | FPS: " + FPS
        );

        System.out.println(
                "Legend: R=Radar | A=Aircraft | D=Drone | M=Missile | F=Fighter | I=Interceptor"
        );

        System.out.println("\n=== EVENT LOG ===");

        for (String log : eventLog) {

            System.out.println(log);
        }
    }

    /**
     * Manually destroys a target.
     */
    public void destroyTarget(int id) {

        Iterator<Target> iterator =
                targets.iterator();

        while (iterator.hasNext()) {

            Target t = iterator.next();

            if (t.getId() == id) {

                iterator.remove();

                logEvent(
                        "💥 Target "
                                + id
                                + " destroyed"
                );

                return;
            }
        }

        logEvent(
                "❌ Target "
                        + id
                        + " not found"
        );
    }

    public List<Target> getTargets() {
        return Collections.unmodifiableList(targets);
    }

    public void addTarget(Target target) {
        if (target != null) {
            targets.add(target);
        }
    }

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(interceptors);
    }

    public List<CollisionEffect> getCollisions() {
        return Collections.unmodifiableList(collisions);
    }

    public List<String> getEventLog() {
        return Collections.unmodifiableList(eventLog);
    }

    public CommandCenter getCommandCenter() {
        return commandCenter;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static final class CollisionEffect {
        private final Coordinate coordinate;
        private int remainingTicks;

        private CollisionEffect(Coordinate coordinate, int remainingTicks) {
            this.coordinate = coordinate;
            this.remainingTicks = remainingTicks;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }
    }
}
