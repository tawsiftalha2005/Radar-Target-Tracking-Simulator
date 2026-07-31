package com.radar.ui;
import com.radar.model.*;
import com.radar.service.TrackingService;
import com.radar.simulation.SimulationEngine;
import java.util.Scanner;
public class ConsoleMenu {
    private TrackingService trackingService;
    private Scanner scanner;

    public ConsoleMenu(TrackingService trackingService) {
        this.trackingService = trackingService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println(" RADAR TARGET TRACKING SIMULATOR");
            System.out.println("==================================");
            System.out.println("1. Add Target");
            System.out.println("2. View All Targets");
            System.out.println("3. Start Radar Simulation 🔥");
            System.out.println("4. Exit");
            System.out.print("Enter Choice: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    addTargetMenu();
                    break;

                case 2:
                    trackingService.showAllTargets();
                    break;

                case 3:
                    startSimulation();   // 🔥 main feature
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);
    }

    // 🔥 REAL-TIME SIMULATION
    private void startSimulation() {

        SimulationEngine engine = new SimulationEngine();

        while (true) {

            engine.update();
            engine.render();

            System.out.print("\nEnter target ID to destroy (-1 skip): ");
            int id = scanner.nextInt();

            if (id != -1) {
                engine.destroyTarget(id);
            }
        }
    }

    // ✅ ADD TARGET (ID removed — auto generated)
    private void addTargetMenu() {

        System.out.println("\nSelect Target Type:");
        System.out.println("1. Aircraft");
        System.out.println("2. Drone");
        System.out.println("3. Missile");
        System.out.print("Enter Choice: ");

        int type = scanner.nextInt();

        System.out.print("Enter X Coordinate: ");
        double x = scanner.nextDouble();

        System.out.print("Enter Y Coordinate: ");
        double y = scanner.nextDouble();

        System.out.print("Enter Speed: ");
        double speed = scanner.nextDouble();

        System.out.print("Enter Altitude: ");
        double altitude = scanner.nextDouble();

        Coordinate position = new Coordinate(x, y);

        Target target = null;

        switch (type) {
            case 1:
                target = new Aircraft(position, speed, altitude);
                break;
            case 2:
                target = new Drone(position, speed, altitude);
                break;
            case 3:
                target = new Missile(position, speed, altitude);
                break;
            default:
                System.out.println("Invalid type!");
                return;
        }

        trackingService.addTarget(target);
        System.out.println("Target Added Successfully!");
    }
}