Fifteen Puzzle Game

This project implements the classic Fifteen Puzzle game with a graphical user interface built using Java Swing. The game challenges players to arrange numbered tiles in ascending order by sliding tiles into an empty space.

Game Rules
	1.	The puzzle consists of a 4x4 grid containing 15 numbered tiles and one empty space.
	2.	Players use arrow keys to move tiles adjacent to the empty space into it.
	3.	The objective is to arrange the tiles in ascending order from left to right, top to bottom, with the empty space in the bottom-right corner.

Features
	•	Shuffling Algorithm: The puzzle uses the Fisher-Yates algorithm to randomize the tiles, ensuring a well-distributed initial state.
	•	Guaranteed Solvability: After shuffling, the program checks the solvability of the grid using inversion count and blank tile row position logic. If unsolvable, the grid is adjusted to guarantee a solvable configuration.

Planned Features
	•	Steps to Solve: Display a step-by-step guide for solving the current puzzle configuration.
	•	Optimal Solution: Integrate an algorithm (e.g., A* search) to compute the shortest sequence of moves to solve the puzzle.

Resources

This project includes modified image assets originally sourced from OpenGameArt.org’s 2048 Board and Tiles.
	•	Author: Vircon32 (Carra)
	•	License: CC-BY 4.0
	•	Images have been adapted to meet the visual requirements of this project.
