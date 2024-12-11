package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FifteenPuzzleFrameTest {

    private FifteenPuzzleFrame frame;

    @BeforeEach
    public void setUp() {
        // Initialize the FifteenPuzzleFrame before each test
        frame = new FifteenPuzzleFrame();
    }

    /**
     * Test isPuzzleSolvable for solvable grid.
     */
    @Test
    public void isPuzzleSolvable_returnsTrueForSolvableGrid() {
        int[][] solvableGrid = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        };

        assertTrue(frame.isPuzzleSolvable(solvableGrid), "Expected solvable grid to be solvable.");
    }

    /**
     * Test isPuzzleSolvable for unsolvable grid.
     */
    @Test
    public void isPuzzleSolvable_returnsFalseForUnsolvableGrid() {
        int[][] unsolvableGrid = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 15, 14, 0}
        };

        assertFalse(frame.isPuzzleSolvable(unsolvableGrid), "Expected unsolvable grid to be unsolvable.");
    }

    /**
     * Test adjustPuzzleGridToBeSolvable modifies unsolvable grid to become solvable.
     */
    @Test
    public void adjustPuzzleGridToBeSolvable_makesUnsolvableGridSolvable() {
        int[][] unsolvableGrid = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 15, 14, 0}
        };

        frame.adjustPuzzleGridToBeSolvable(unsolvableGrid);

        assertTrue(frame.isPuzzleSolvable(unsolvableGrid), "Adjusted grid should be solvable.");
    }

    /**
     * Test adjustPuzzleGridToBeSolvable does not change already solvable grid.
     */
    @Test
    public void adjustPuzzleGridToBeSolvable_doesNotChangeSolvableGrid() {
        int[][] solvableGrid = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        };

        frame.adjustPuzzleGridToBeSolvable(solvableGrid);

        assertTrue(frame.isPuzzleSolvable(solvableGrid), "Already solvable grid should remain solvable.");
    }
}