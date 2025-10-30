# Connect4 🎯

A Java-based implementation of the classic Connect 4 game, developed for CS 342. The project features a clean GUI using JavaFX, flexible game logic supporting two players, and test coverage to validate key game states.

---

## 🤖 Overview

This Connect4 project allows two players to place discs in a 7-column, 6-row grid until one player aligns four discs vertically, horizontally, or diagonally. It demonstrates GUI design in JavaFX, event-driven programming, and modular game-logic architecture.

---

## 🛠 Technology Stack

- **Language:** Java  
- **UI Framework:** JavaFX  
- **Architecture:** Separation of UI (views/controllers) and game logic (model)  
- **Testing:** JUnit (or equivalent) in `MyTest.java` to validate game logic  
- **Version Control:** GitHub  

---

## ✨ Key Features

- 🎮 Interactive GUI with clickable columns (via `GameButton.java`) and dynamic disc placement  
- 🧠 Robust game-logic that checks win conditions in all directions (horizontal, vertical, diagonal)  
- 🔄 Clean restart and new-game support  
- ✅ Unit tests to ensure game logic correctness and expected outcomes  
- 🧩 Modular code structure to facilitate enhancements (e.g., AI opponent, networked play)

---

## 🚀 Getting Started

### Prerequisites  
- Java JDK (version 8 or higher recommended)  
- JavaFX SDK (if not bundled)  
- Git (optional, for cloning)

### Installation & Run  
```bash
# Clone this repository
git clone https://github.com/pranavsrathod/Connect4.git

# Navigate into the directory
cd Connect4

# Compile all Java files (example)
javac *.java

# Run the main application (replace MainClass with actual main class name)
java JavaFXTemplate
