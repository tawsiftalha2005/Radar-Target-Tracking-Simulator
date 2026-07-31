package com.radar.simulation;

import com.radar.model.*;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class SimulationEngine {
    private boolean running = true;
    private final int FPS = 5;
    private final int WIDTH = 40;
    private final int HEIGHT = 20;
    private List<Target> targets = new ArrayList<>();
    private Random random = new Random();
    private List<String> eventLog = new ArrayList<>();


    private char[][] grid;

    public SimulationEngine() {
        grid = new char[HEIGHT][WIDTH];
        spawnInitialTargets();

    }

    private void logEvent(String message) {
        eventLog.add(message);
        // limit log size (last 10)
        if (eventLog.size() > 10) {
            eventLog.remove(0);
        }
    }

    private void detectTargets() {
        for (Target t : targets) {

            if (t.getStatus() == Target.Status.NEW) {

                t.setStatus(Target.Status.DETECTED);

                logEvent(t.getClass().getSimpleName() + " detected");

                if (random.nextBoolean()) {
                    t.setStatus(Target.Status.AUTHORIZED);
                    logEvent(t.getClass().getSimpleName() + " authorized");
                } else {
                    t.setStatus(Target.Status.UNAUTHORIZED);
                    logEvent("⚠ Unauthorized " + t.getClass().getSimpleName());
                }
            }
        }
    }

    private void spawnInitialTargets() {
        for (int i = 0; i < 5; i++) {

            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT / 2); // top area

            Coordinate pos = new Coordinate(x, y);

            int type = random.nextInt(3);

            Target t;

            if (type == 0) {
                t = new Aircraft(pos, 800, 10000);
            } else if (type == 1) {
                t = new Drone( pos, 120, 500);
            } else {
                t = new Missile( pos, 1500, 2000);
            }

            targets.add(t);
        }
    }

    public void start() {

        long frameDelay = 1000 / FPS;

        while (running) {

            long startTime = System.currentTimeMillis();

            update();
            render();

            long endTime = System.currentTimeMillis();
            long elapsed = endTime - startTime;

            long sleepTime = frameDelay - elapsed;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update() {
        // Clear grid
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = ' ';
            }
        }
        // Place Radar (bottom-center)
        int radarX = WIDTH / 2;
        int radarY = HEIGHT - 3;
        grid[radarY][radarX] = 'R'; // later emoji দিবো

        moveTargets();
        detectTargets();


        // Place targets
        for (Target t : targets) {
            int x = (int) t.getCoordinate().getX();
            int y = (int) t.getCoordinate().getY();
            if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                // 🔥 show ID (last digit)
                grid[y][x] = Character.forDigit(t.getId() % 10, 10);
            }
        }
    }

    private void moveTargets() {
        for (Target t : targets) {
            double x = t.getCoordinate().getX();
            double y = t.getCoordinate().getY();
            if (t instanceof Aircraft) {
                // move diagonally
                x += 0.5;
                y += 0.2;
            } else if (t instanceof Drone) {
                // random movement
                x += random.nextInt(3) - 1; // -1,0,1
                y += random.nextInt(2);     // 0 বা 1
            } else if (t instanceof Missile) {
                // fast downward
                y += 1.5;
            }
            // set new position
            t.getCoordinate().setX(x);
            t.getCoordinate().setY(y);
        }
        // remove targets out of map
        targets.removeIf(t ->
                t.getCoordinate().getY() >= HEIGHT ||
                        t.getCoordinate().getX() >= WIDTH ||
                        t.getCoordinate().getX() < 0
        );
    }

    private char getSymbol(Target t) {
        if (t instanceof Aircraft) {
            return t.getStatus() == Target.Status.UNAUTHORIZED ? 'a' : 'A';
        }

        if (t instanceof Drone) {
            return t.getStatus() == Target.Status.UNAUTHORIZED ? 'd' : 'D';
        }

        if (t instanceof Missile) {
            return t.getStatus() == Target.Status.UNAUTHORIZED ? 'm' : 'M';
        }

        return '?';
    }

    public void render() {
        // Clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
// Top border
        System.out.print("+");
        for (int i = 0; i < WIDTH; i++) System.out.print("-");
        System.out.println("+");

// Grid print
        for (int i = 0; i < HEIGHT; i++) {
            System.out.print("|");
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("|");
        }

// Bottom border
        System.out.print("+");
        for (int i = 0; i < WIDTH; i++) System.out.print("-");
        System.out.println("+");

        System.out.println("RADAR ACTIVE | FPS: " + FPS);

// Event log
        System.out.println("\n=== EVENT LOG ===");
        for (String log : eventLog) {
            System.out.println(log);
        }
    }

    public void destroyTarget(int id) {
        Iterator<Target> iterator = targets.iterator();

        while (iterator.hasNext()) {
            Target t = iterator.next();

            if (t.getId() == id) {

                iterator.remove();

                logEvent("💥 Target " + id + " destroyed");
                return;
            }
        }

        logEvent("❌ Target " + id + " not found");
    }

}