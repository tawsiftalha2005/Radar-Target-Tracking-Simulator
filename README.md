# 📡 Radar Target Tracking Simulator

![Java](https://img.shields.io/badge/Java-26-orange?style=for-the-badge&logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-26.0.2-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build-c71a36?style=for-the-badge&logo=apachemaven)
![Platform](https://img.shields.io/badge/Platform-Windows-lightgrey?style=for-the-badge)

A **JavaFX-based desktop simulation** that demonstrates a simplified radar target tracking and interception workflow.

The simulator visualizes how aerial targets enter a radar detection area, are detected and classified as authorized or unauthorized, assessed by a Command Centre, and—when necessary—assigned an appropriate interceptor.

> ⚠️ **Educational Project:**  
> This software is a fictional educational simulation. It does not model, control, or interact with any real-world radar, military, defense, or weapon system.

---

## 📌 Project Overview

The **Radar Target Tracking Simulator** is an Object-Oriented Programming project developed using Java, JavaFX, and Maven.

The system simulates three major types of aerial targets:

- ✈️ Aircraft
- 🛸 Drones
- 🚀 Missiles

When a target enters the radar area, the system detects the target and determines whether it is **AUTHORIZED** or **UNAUTHORIZED**.

Unauthorized targets are forwarded to the **Command Centre**, where their threat level is analyzed and an appropriate interception response is selected.

---

## ✨ Features

- 📡 Real-time radar simulation
- 🎯 Aircraft, drone, and missile simulation
- 🔐 Automatic target authorization
- 🧠 Command Centre threat assessment
- ⚠️ LOW, MEDIUM, HIGH, and CRITICAL threat levels
- ✈️ Fighter interceptor deployment
- 🚀 Ground interceptor deployment
- 🎯 Real-time target tracking
- 📏 Distance calculation between interceptor and target
- 💥 Interception success events
- 📊 Live event log
- 🖥️ JavaFX graphical interface
- ⏯️ Pause and Resume controls
- 🔄 Real-time simulation updates

---

## ⚙️ System Workflow

```text
Target Enters Radar Area
          ↓
Radar Detection
          ↓
Authorization Check
          ↓
     ┌────┴────┐
     ↓         ↓
AUTHORIZED  UNAUTHORIZED
     ↓         ↓
 Continue   Command Centre
 Tracking       ↓
          Threat Assessment
                 ↓
          Interception Decision
                 ↓
        ┌────────┴────────┐
        ↓                 ↓
 Fighter Interceptor  Ground Interceptor
        ↓                 ↓
     Airbase         Missile Battery
        ↓                 ↓
        └────────┬────────┘
                 ↓
          Target Tracking
                 ↓
          Distance Check
                 ↓
         Interception Event
                 ↓
          Target Removed
```

---

## 🎯 Target Types

The simulator supports three types of targets:

| Target      | Description                                  |
| ----------- | -------------------------------------------- |
| ✈️ Aircraft | Aerial target with diagonal movement         |
| 🛸 Drone    | Aerial target with variable movement         |
| 🚀 Missile  | Target moving downward toward the radar area |

---

## 🔐 Authorization System

After radar detection, every target is classified as:

```text
AUTHORIZED
```

or

```text
UNAUTHORIZED
```

Only unauthorized targets are sent to the Command Centre for threat analysis and possible interception.

---

## 🧠 Threat Assessment

The Command Centre evaluates unauthorized targets and assigns a threat level.

| Target Condition                       | Threat Level |
| --------------------------------------- | ------------ |
| Authorized Target                      | LOW          |
| Unauthorized Missile                   | CRITICAL     |
| Unauthorized Aircraft with speed ≥ 700 | HIGH         |
| Unauthorized Aircraft below 700        | MEDIUM       |
| Unauthorized Drone with speed ≥ 200    | HIGH         |
| Unauthorized Drone below 200           | MEDIUM       |

---

## 🛡️ Interception System

### ✈️ Fighter Interceptor

Fast unauthorized aircraft are assigned to a fighter interceptor.

```text
Unauthorized Aircraft
        ↓
Command Centre
        ↓
HIGH Threat
        ↓
FIGHTER_INTERCEPTOR
        ↓
Airbase
        ↓
Fighter Deployed
        ↓
Target Tracking
        ↓
Interception
```

### 🚀 Ground Interceptor

Unauthorized drones, missiles, and other applicable threats are assigned to a ground interceptor.

```text
Unauthorized Target
        ↓
Command Centre
        ↓
Threat Assessment
        ↓
GROUND_INTERCEPTOR
        ↓
Missile Battery
        ↓
Interceptor Deployed
        ↓
Target Tracking
        ↓
Interception
```

---

## 🎯 Target Tracking

After deployment, an interceptor is assigned to a specific target.

The interceptor continuously moves toward its target while the simulation calculates the distance between them.

Example:

```text
G01 moving toward Target #5 | Distance: 18.43
G01 moving toward Target #5 | Distance: 13.56
G01 moving toward Target #5 | Distance: 7.46
G01 moving toward Target #5 | Distance: 1.20
```

When the interceptor reaches the target within the required interception range, the simulation generates:

```text
💥 INTERCEPTION SUCCESSFUL!
Target #5 intercepted by G01
```

The intercepted target is then removed from the active simulation.

---

## 🖥️ Graphical Interface

The JavaFX interface provides a visual radar environment with target and interceptor markers.

### Radar Legend

```text
R = Radar
A = Aircraft
D = Drone
M = Missile
F = Fighter
I = Interceptor
```

The interface also displays:

* Radar status
* Active targets
* Active interceptors
* Target positions
* Interceptor positions
* Event log
* Interception events
* Simulation controls

---

## 📋 Example Event Log

```text
Missile #5 detected by radar
⚠ Unauthorized Missile #5

→ Target #5 data sent to Command Centre

Command Centre:
Target #5 | Threat: CRITICAL

Command Centre Decision:
GROUND_INTERCEPTOR

🚀 Ground Interceptor G01 launched for Target #5

G01 moving toward Target #5 | Distance: 18.43
G01 moving toward Target #5 | Distance: 13.56
G01 moving toward Target #5 | Distance: 7.46

💥 INTERCEPTION SUCCESSFUL!
Target #5 intercepted by G01
```

---

## 🏗️ Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── radar/
                ├── Main.java
                │
                ├── model/
                │   ├── Airbase.java
                │   ├── Aircraft.java
                │   ├── CommandCenter.java
                │   ├── Coordinate.java
                │   ├── Drone.java
                │   ├── Interceptor.java
                │   ├── Missile.java
                │   ├── MissileBattery.java
                │   ├── Radar.java
                │   ├── Target.java
                │   └── ThreatLevel.java
                │
                ├── simulation/
                │   └── SimulationEngine.java
                │
                ├── service/
                │   └── TrackingService.java
                │
                ├── ui/
                │   ├── ConsoleMenu.java
                │   └── javafx/
                │       └── RadarApplication.java
                │
                └── util/
                    └── InputHelper.java
```

---

## 🧩 Main Components

### Target

Base class representing an aerial target.

```text
Target
 ├── Aircraft
 ├── Drone
 └── Missile
```

### Radar

Responsible for detecting and tracking targets within the simulated radar area.

### CommandCenter

Responsible for:

* Receiving unauthorized target data
* Threat analysis
* Threat classification
* Interception decisions
* Sending commands to defense units

### Airbase

Responsible for deploying fighter interceptors.

### MissileBattery

Responsible for deploying ground interceptors.

### Interceptor

Represents an interceptor assigned to a target.

It moves toward its assigned target and continuously updates its position.

### SimulationEngine

Controls the main simulation process, including:

* Target spawning
* Target movement
* Radar detection
* Authorization
* Command Centre communication
* Threat assessment
* Interceptor deployment
* Interceptor movement
* Distance calculation
* Interception detection
* Event logging

### RadarApplication

JavaFX application responsible for displaying the graphical radar simulation.

---

## 🎓 OOP Concepts Demonstrated

This project demonstrates several Object-Oriented Programming concepts:

* **Encapsulation**
* **Inheritance**
* **Polymorphism**
* **Abstraction**
* **Composition**
* **Classes and Objects**
* **Method Overriding**
* **Java Collections**

### Inheritance

```text
Target
   │
   ├── Aircraft
   ├── Drone
   └── Missile
```

### Composition

```text
CommandCenter
      │
      ├── Airbase
      │
      └── MissileBattery
```

---

## 🛠️ Technologies Used

* **Java 26**
* **JavaFX 26.0.2**
* **Maven**
* Object-Oriented Programming
* Java Collections
* Real-Time Simulation
* JavaFX GUI

---

## 📋 Requirements

Before running the project, make sure you have:

* JDK 26 or later
* Maven 3.9 or later
* IntelliJ IDEA or another Java-compatible IDE
* JavaFX 26.0.2

---

## 🚀 Getting Started

### Clone the Repository

```bash
git clone https://github.com/tawsiftalha2005/Radar-Target-Tracking-Simulator.git
```

### Navigate to the Project

```bash
cd Radar-Target-Tracking-Simulator
```

### Build the Project

```bash
mvn clean package
```

### Run the Application

```bash
mvn javafx:run
```

Maven automatically downloads the required JavaFX dependencies defined in `pom.xml` and launches the simulator.

---

## 📦 Build JAR

To create a packaged build:

```bash
mvn clean package
```

The generated files will be available inside:

```text
target/
```

Because JavaFX uses platform-specific native libraries, running a standalone JAR may require the appropriate JavaFX runtime for the operating system.

For development, the recommended command is:

```bash
mvn javafx:run
```

---

## 📈 Complete Simulation Flow

```text
1. Target enters radar area
             ↓
2. Radar detects target
             ↓
3. Authorization is determined
             ↓
4. Target marked UNAUTHORIZED
             ↓
5. Target data sent to Command Centre
             ↓
6. Threat level calculated
             ↓
7. Interception decision made
             ↓
8. Airbase / Missile Battery receives command
             ↓
9. Interceptor deployed
             ↓
10. Interceptor assigned to target
             ↓
11. Interceptor moves toward target
             ↓
12. Distance continuously calculated
             ↓
13. Interception condition reached
             ↓
14. INTERCEPTION SUCCESSFUL
             ↓
15. Target removed from simulation
```

---

## 🔮 Future Improvements

Possible future improvements include:

* More advanced radar visualization
* Improved target path prediction
* Multiple radar stations
* Multiple airbases and missile batteries
* Improved interceptor animations
* More detailed interception visualization
* Manual Command Centre controls
* Target filtering and search
* Simulation statistics
* Performance monitoring
* Additional JavaFX UI components

---

## 📊 Project Status

| Component            | Status            |
| --------------------- | ----------------- |
| Radar Detection      | 🟢 Completed      |
| Target Simulation    | 🟢 Completed      |
| Authorization System | 🟢 Completed      |
| Threat Assessment    | 🟢 Completed      |
| Command Centre       | 🟢 Completed      |
| Interceptor System   | 🟢 Completed      |
| Target Tracking      | 🟢 Completed      |
| Interception Events  | 🟢 Completed      |
| JavaFX GUI           | 🟢 Completed      ||

---

## 🎯 Educational Purpose

This project was developed as an **Object-Oriented Programming semester project** to demonstrate the practical application of Java and OOP concepts in a real-time simulation.

The project combines:

```text
Object-Oriented Programming
          +
Java
          +
JavaFX
          +
Maven
          +
Real-Time Simulation
```

---

## ⚠️ Disclaimer

This project is created **strictly for educational and software engineering purposes**.

It is a fictional simulation and does not provide real-world military, radar, targeting, interception, or weapon-control functionality.

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

**Md. Wahid Tawsif Talha**

GitHub:
[https://github.com/tawsiftalha2005](https://github.com/tawsiftalha2005)

---

⭐ If you find this project useful, consider giving it a star!
