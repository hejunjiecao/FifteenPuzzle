package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Represents the main frame for the Fifteen-Puzzle game.
 * Handles game logic, UI components, and user interactions.
 */
public class FifteenPuzzleFrame extends JFrame {
    // Constants for dimensions
    private static final int TILE_SIZE = 76;
    private static final int GRID_ROWS = 4;
    private static final int GRID_COLUMNS = 4;
    private static final int BACKGROUND_WIDTH = 6 * TILE_SIZE;
    private static final int BACKGROUND_HEIGHT = 8 * TILE_SIZE;
    private static final int WINDOW_PADDING = 50;
    private static final int WINDOW_WIDTH = BACKGROUND_WIDTH;
    private static final int WINDOW_HEIGHT = BACKGROUND_HEIGHT + WINDOW_PADDING;
    private static final int TILE_MARGIN = 78;

    private int stepCounter = 0; // Tracks the number of moves made
    private final int[][] puzzleGrid = { // The puzzle grid
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0} // 0 represents the empty tile
    };

    /**
     * Constructor for the FifteenPuzzleFrame.
     * Initializes the game window, grid, and event listeners.
     */
    public FifteenPuzzleFrame() {
        initializeWindow();
        initializeStepCounterLabel();
        shufflePuzzleGrid();
        refreshPuzzleGrid();
        initializeKeyListener();
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
    }

    // Public Methods

    /**
     * Finds the position of the empty tile in the grid.
     *
     * @param grid The current puzzle grid.
     * @return The row and column indices of the empty tile.
     */
    public static int[] findEmptyTile(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Private Methods

    /**
     * Initializes the main game window with basic properties.
     */
    private void initializeWindow() {
        setTitle("Fifteen-Puzzle");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(null); // Use absolute positioning
    }

    /**
     * Initializes the label to display the number of steps taken.
     */
    private void initializeStepCounterLabel() {
        // Displays the step count
        JLabel stepCounterLabel = new JLabel("Steps: " + stepCounter);
        stepCounterLabel.setBounds(10, 10, 120, 30);
        stepCounterLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(stepCounterLabel);
    }

    /**
     * Adds a button to shuffle the puzzle grid and reset the game.
     */
    private void initializeShuffleButton() {
        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.setBounds(120, WINDOW_HEIGHT - 150, 200, 60);
        shuffleButton.setForeground(Color.decode("#be2c16"));
        shuffleButton.setFont(new Font("Arial", Font.BOLD, 20));
        shuffleButton.addActionListener(e -> resetPuzzleGrid());
        add(shuffleButton);
    }

    /**
     * Adds a key listener to handle tile movement based on arrow key inputs.
     */
    private void initializeKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP -> moveTile(Direction.UP);
                    case KeyEvent.VK_DOWN -> moveTile(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> moveTile(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> moveTile(Direction.RIGHT);
                }
            }
        });
    }

    /**
     * Shuffles the puzzle grid to create a new game state.
     */
    private void shufflePuzzleGrid() {
        shuffleArray(puzzleGrid);
        while (!isPuzzleSolvable(puzzleGrid)) {
            adjustPuzzleGridToBeSolvable(puzzleGrid);
        }
        stepCounter = 0;
    }

    /**
     * Moves a tile in the specified direction if possible.
     *
     * @param direction The direction to move the tile.
     */
    private void moveTile(Direction direction) {
        int[] emptyTilePosition = findEmptyTile(puzzleGrid);
        assert emptyTilePosition != null;

        int emptyRow = emptyTilePosition[0];
        int emptyCol = emptyTilePosition[1];
        int targetRow = emptyRow + direction.getRowOffset();
        int targetCol = emptyCol + direction.getColOffset();

        if (targetRow >= 0 && targetRow < GRID_ROWS && targetCol >= 0 && targetCol < GRID_COLUMNS) {
            puzzleGrid[emptyRow][emptyCol] = puzzleGrid[targetRow][targetCol];
            puzzleGrid[targetRow][targetCol] = 0;
            stepCounter++;
        }

        refreshPuzzleGrid();
    }

    /**
     * Refreshes the puzzle grid UI and checks for a win condition.
     */
    private void refreshPuzzleGrid() {
        getContentPane().removeAll();
        initializeStepCounterLabel();
        addPuzzleTilesToGrid();
        addBackgroundImage();
        initializeShuffleButton();
        if (checkIfPuzzleSolved()) {
            displayVictoryMessage();
        }
        getContentPane().repaint();
    }

    /**
     * Checks if the puzzle is solved by comparing it to the target state.
     *
     * @return True if the puzzle is solved, false otherwise.
     */
    private boolean checkIfPuzzleSolved() {
        int[][] solvedGrid = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };

        for (int i = 0; i < GRID_ROWS; i++) {
            for (int j = 0; j < GRID_COLUMNS; j++) {
                if (puzzleGrid[i][j] != solvedGrid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Displays a victory message when the puzzle is solved.
     */
    private void displayVictoryMessage() {
        ImageIcon victoryIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/Victory.png")));
        JLabel victoryLabel = new JLabel(victoryIcon);
        int imageWidth = 300;
        int imageHeight = 80;
        int xPosition = (getWidth() - imageWidth) / 2;
        int yPosition = 40;
        victoryLabel.setBounds(xPosition, yPosition, imageWidth, imageHeight);
        add(victoryLabel);
    }

    /**
     * Adds the puzzle tiles to the grid UI.
     */
    private void addPuzzleTilesToGrid() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLUMNS; col++) {
                JLabel tileLabel = new JLabel();
                String resourcePath = String.format("/images/%d.png", puzzleGrid[row][col]);
                tileLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(resourcePath))));
                tileLabel.setBounds(col * TILE_SIZE + TILE_MARGIN, row * TILE_SIZE + TILE_MARGIN * 2, TILE_SIZE, TILE_SIZE);
                add(tileLabel);
            }
        }
    }

    /**
     * Adds the background image to the UI.
     */
    private void addBackgroundImage() {
        JLabel backgroundLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/background.png"))));
        backgroundLabel.setBounds(0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        add(backgroundLabel);
    }

    /**
     * Resets the puzzle grid by shuffling and refreshing the UI.
     */
    private void resetPuzzleGrid() {
        shufflePuzzleGrid();
        refreshPuzzleGrid();
    }

    /**
     * Shuffles the puzzle grid using the Fisher-Yates algorithm.
     *
     * @param grid The grid to shuffle.
     */
    public void shuffleArray(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[] flatArray = new int[rows * cols];
        int index = 0;

        for (int[] row : grid) {
            for (int val : row) {
                flatArray[index++] = val;
            }
        }

        Random random = new Random();
        for (int i = flatArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = flatArray[i];
            flatArray[i] = flatArray[j];
            flatArray[j] = temp;
        }

        index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = flatArray[index++];
            }
        }
    }

    /**
     * Checks if the puzzle grid is solvable.
     *
     * @param grid The grid to check.
     * @return True if solvable, false otherwise.
     */
    public boolean isPuzzleSolvable(int[][] grid) {
        int[] flatGrid = flattenGrid(grid);
        int inversions = countInversions(flatGrid);
        int blankRowFromBottom = findBlankRowFromBottom(grid);

        // Determine resolvability: based on inversion count and blank row position
        int gridWidth = grid.length;
        if (gridWidth % 2 == 0) {
            // Even-width grid: blank row position and inversion count must satisfy conditions
            return (blankRowFromBottom % 2 == 0) == (inversions % 2 != 0);
        } else {
            // Odd-width grid: inversion count must be even
            return inversions % 2 == 0;
        }
    }

    /**
     * Flattens a 2D grid into a 1D array.
     *
     * @param grid The grid to flatten.
     * @return A 1D array representation of the grid.
     */
    private int[] flattenGrid(int[][] grid) {
        return Arrays.stream(grid).flatMapToInt(Arrays::stream).toArray();
    }

    /**
     * Counts the number of inversions in a flattened grid.
     * An inversion occurs when a larger number precedes a smaller number in the array.
     *
     * @param flatGrid The flattened grid array.
     * @return The number of inversions.
     */
    private int countInversions(int[] flatGrid) {
        int inversions = 0;
        for (int i = 0; i < flatGrid.length; i++) {
            if (flatGrid[i] == 0) {
                continue; // Skip the empty tile
            }
            for (int j = i + 1; j < flatGrid.length; j++) {
                if (flatGrid[j] != 0 && flatGrid[i] > flatGrid[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    /**
     * Finds the row index of the empty tile (0), counting from the bottom.
     *
     * @param grid The grid to search.
     * @return The row index of the empty tile, counting from the bottom.
     */
    private int findBlankRowFromBottom(int[][] grid) {
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return grid.length - i;
                }
            }
        }
        return -1; // Should not reach here
    }

    /**
     * Adjusts the puzzle grid to make it solvable.
     * If the grid is already solvable, no adjustments are made.
     *
     * @param grid The grid to adjust.
     */
    public void adjustPuzzleGridToBeSolvable(int[][] grid) {
        if (isPuzzleSolvable(grid)) {
            return; // Grid is already solvable, no need to adjust
        }

        // Find the last two non-zero numbers and swap them
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[i].length - 1; j >= 0; j--) {
                if (grid[i][j] == 0) {
                    continue;
                }
                for (int k = j - 1; k >= 0; k--) {
                    if (grid[i][k] == 0) {
                        continue;
                    }
                    // Swap the last two non-zero numbers
                    int temp = grid[i][j];
                    grid[i][j] = grid[i][k];
                    grid[i][k] = temp;
                    return;
                }
            }
        }
    }
}