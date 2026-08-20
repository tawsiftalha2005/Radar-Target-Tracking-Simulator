import pypandoc

markdown_text = """# Radar Target Tracking and Interception Simulator

## Project Overview

The **Radar Target Tracking and Interception Simulator** is a software-based simulation system designed to demonstrate the process of detecting, tracking, classifying, and responding to aerial targets in a controlled virtual environment. The system simulates different types of airborne targets, such as aircraft, drones, and missiles, and continuously monitors their movement through a radar-based tracking mechanism.

When a target is detected, the system determines whether it is authorized or unauthorized. Information about an unauthorized target is transmitted to a simulated **Command Centre**, where the threat level is analyzed based on factors such as target type, speed, and status. Based on the threat assessment, the Command Centre makes an interception decision and sends the appropriate command to a simulated **Airbase** or **Missile Battery**.

For example, an unauthorized high-threat aircraft may result in the deployment of a fighter interceptor, while an unauthorized drone or missile may result in the deployment of a ground-based interceptor. The deployed interceptor then tracks and moves toward its assigned target. When the interceptor reaches the target within a defined interception distance, an interception event is generated and the target is removed from the simulation.

The system is intended as a **simulation and visualization platform**, allowing users to observe the complete target detection-to-interception workflow through a graphical user interface (GUI).

## Project Scope

The scope of the project includes the simulation and visualization of the following major activities:

1. **Target Generation**
   - Simulate different aerial target types, including aircraft, drones, and missiles.
   - Assign properties such as target ID, position, speed, and status.

2. **Radar Detection**
   - Continuously monitor the simulated environment.
   - Detect newly appearing targets.
   - Track the position and movement of detected targets.

3. **Target Identification and Authorization**
   - Determine whether a detected target is authorized or unauthorized.
   - Generate appropriate radar events for detected threats.

4. **Command Centre Processing**
   - Receive information about unauthorized targets.
   - Analyze the threat level of each target.
   - Classify threats as low, medium, high, or critical.
   - Select an appropriate interception strategy.

5. **Defence Unit Deployment**
   - Communicate interception commands to the appropriate defence unit.
   - Deploy fighter interceptors through the simulated Airbase.
   - Deploy ground interceptors through the simulated Missile Battery.

6. **Interceptor Tracking**
   - Assign an interceptor to the selected target.
   - Simulate interceptor movement toward the target.
   - Continuously calculate the distance between the interceptor and target.

7. **Interception Simulation**
   - Detect when an interceptor reaches its target.
   - Generate an interception-success event.
   - Update the target status and remove the intercepted target from active tracking.

8. **Graphical Visualization**
   - Display the radar environment through a JavaFX-based GUI.
   - Visualize targets, radar, interceptors, and their movements.
   - Display important system events and interception information.

The project does **not** represent a real-world military command or weapon-control system. It is a controlled software simulation intended for educational, demonstration, and software-engineering purposes.

## Project Objectives

The main objectives of the project are:

1. To develop a software-based simulation of a radar target tracking environment.
2. To simulate the detection and continuous tracking of different aerial targets.
3. To distinguish between authorized and unauthorized targets within the simulated environment.
4. To transmit information about unauthorized targets to a centralized Command Centre.
5. To implement an automated threat assessment mechanism based on target characteristics.
6. To simulate decision-making by the Command Centre for selecting an appropriate interception method.
7. To model communication between the Command Centre, Airbase, and Missile Battery.
8. To simulate the deployment and movement of fighter and ground interceptors.
9. To demonstrate target-interceptor tracking and successful interception events.
10. To provide a graphical interface through which the complete detection, decision-making, tracking, and interception process can be observed in real time.
11. To demonstrate the application of object-oriented programming, simulation logic, event processing, and GUI development in a single integrated software system.
"""

output_path = "/mnt/data/Radar_Target_Tracking_Simulator_Overview_Scope_Objectives.md"
pypandoc.convert_text(
    markdown_text,
    "md",
    format="md",
    outputfile=output_path,
    extra_args=["--standalone"]
)

print(f"Created: {output_path}")
