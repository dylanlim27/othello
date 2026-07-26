# Reversi (Othello) - Java Swing MVC Implementation

[![Java](https://img.shields.io/badge/Java-8%2B-blue.svg)](https://www.oracle.com/java/)
[![Architecture](https://img.shields.io/badge/Architecture-MVC-orange.svg)](#architecture)

A modern, object-oriented Java implementation of the classic **Reversi (Othello)** strategy board game built using the **Model-View-Controller (MVC)** design pattern and **Java Swing**.

This application features a unique **synchronized dual-window interface** allowing two local players to play head-to-head with 180-degree view rotation for Player 2, an integrated **Greedy AI** bot for single-player practice, dynamic multi-directional capture flipping, and anti-aliased graphical rendering.

---

## 🌟 Key Features

- **Dual-Window Perspective Display**: Renders simultaneous, synchronized viewports for Player 1 (White) and Player 2 (Black). Player 2's board is rotated 180° to reflect an authentic tabletop head-to-head orientation.
- **Greedy AI Player**: Integrated automated decision-making algorithm that evaluates all legal board positions and selects the move maximizing immediate piece captures.
- **Anti-Aliased 2D Graphics**: Custom Swing components with vector-based disc rendering, subtle drop shadows, crisp outlines, and classic green felt board styling.
- **Multi-Directional Capture Engine**: Complete move validation and piece-flipping logic checking all 8 compass directions (Horizontal, Vertical, Diagonal).
- **Turn & End-Game Detection**: Automatic handling of forced turn skips when a player has no valid moves, along with full-board and tie detection score calculations.
- **CLI & GUI Viewports**: Modular presentation layer supporting both rich Graphical UI (`GUIView`) and Terminal/CLI text modes (`TextView`).
- **Diagnostic Test Suite**: Built-in test harness (`SimpleTestModel`) for simulating edge-case board configurations, full-board scenarios, and AI playout simulations.

---

## 🏗️ Architecture

The project strictly follows the **Model-View-Controller (MVC)** design pattern to decouple game logic, state management, and user interfaces:

```
                  +-------------------------+
                  |       ReversiMain       |
                  +------------+------------+
                               | (Wires components)
                               v
         +---------------------+---------------------+
         |                                           |
         v                                           v
+-----------------+    Notifies / Refreshes   +-----------------+
|     IModel      | <------------------------ |      IView      |
|  (SimpleModel)  |                           |    (GUIView)    |
+--------+--------+                           +--------+--------+
         ^                                             |
         |               User Actions                  |
         +---------------------------------------------+
                               |
                               v
                     +-------------------+
                     |    IController    |
                     | (ReversiControl)  |
                     +-------------------+
```

### Class Breakdown

| Component | Class / Interface | Description |
| :--- | :--- | :--- |
| **Main** | `ReversiMain` | Application entry point and dependency injection container. |
| **Model** | `IModel` | Interface defining board representation and state queries. |
| | `SimpleModel` | Grid-based board state storage and turn management. |
| | `SimpleTestModel` | Extension of `SimpleModel` with interactive test controls. |
| **Controller** | `IController` | Interface for game rule execution and automated moves. |
| | `ReversiController` | Implements legal move validation, 8-way directional flips, and Greedy AI. |
| **View** | `IView` | Interface for board visualization and user notifications. |
| | `GUIView` | Dual-window Java Swing graphical user interface. |
| | `TextView` | Terminal/Console text interface with command parser. |
| | `BoardSquareButton` | Custom `JButton` rendering anti-aliased game discs. |
| | `ColorLabel` / `ConsoleInput` | UI indicator labels and keyboard input utilities. |

---

## 🎮 Game Rules & Controls

### Objective
Outnumber your opponent by having the majority of your colored discs on the board at the end of the game.

### Gameplay
1. **Starting Position**: The game starts with 4 pieces placed in the center of an 8x8 grid (2 White, 2 Black).
2. **Making a Move**: Players take turns placing a disc of their color on an empty square.
3. **Flanking & Flipping**: A move must trap one or more opposing discs between the placed disc and another disc of the active player's color along a straight line (horizontal, vertical, or diagonal). All trapped opposing discs are flipped to the active player's color.
4. **Turn Skipping & Game Over**: If a player has no legal moves, their turn is automatically passed. The game ends when neither player can make a valid move.

### GUI Controls
- **Click Board Square**: Attempt to play a disc on the selected grid cell.
- **Greedy AI Button**: Instruct the AI to calculate and play the optimal move for that player.
- **Restart Game**: Clear the board and start a new game session.

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: Version 8 or higher installed on your system.

### Compiling from Command Line

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/reversi.git
   cd reversi
   ```

2. Compile the Java source files into the `bin` output directory:
   ```bash
   javac -d bin src/reversi/*.java
   ```

3. Launch the game:
   ```bash
   java -cp bin reversi.ReversiMain
   ```

### Running in IDE (Eclipse / IntelliJ / VS Code)
1. Open or import the project directory into your favorite IDE.
2. Mark `src` as the Source Root directory.
3. Run `reversi.ReversiMain` as a Java Application.
