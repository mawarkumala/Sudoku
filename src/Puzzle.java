import java.util.Random;

public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {
        clearPuzzle();
        fillRandom(cellsToGuess);
        makeBoldBlocks();
    }

    private void clearPuzzle() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;
                isGiven[row][col] = false;
            }
        }
    }

    private void fillRandom(int cellsToGuess) {
        Random random = new Random();

        // Fill the main diagonal of the puzzle with random numbers
        for (int i = 0; i < SudokuConstants.GRID_SIZE; ++i) {
            int randomNumber = random.nextInt(9) + 1;
            numbers[i][i] = randomNumber;
            isGiven[i][i] = true;
        }

        // Shuffle each row to create a valid Sudoku puzzle
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            shuffleRow(row);
        }

        // Shuffle each column to create a valid Sudoku puzzle
        for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            shuffleColumn(col);
        }

        // Set the remaining cells to guess
        setCellsToGuess(cellsToGuess);
    }

    private void shuffleRow(int row) {
        Random random = new Random();
        for (int i = 0; i < SudokuConstants.GRID_SIZE; ++i) {
            int j = random.nextInt(SudokuConstants.GRID_SIZE);
            swapValues(row, i, row, j);
        }
    }

    private void shuffleColumn(int col) {
        Random random = new Random();
        for (int i = 0; i < SudokuConstants.GRID_SIZE; ++i) {
            int j = random.nextInt(SudokuConstants.GRID_SIZE);
            swapValues(i, col, j, col);
        }
    }

    private void swapValues(int row1, int col1, int row2, int col2) {
        int temp = numbers[row1][col1];
        numbers[row1][col1] = numbers[row2][col2];
        numbers[row2][col2] = temp;
    }

    private void setCellsToGuess(int cellsToGuess) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cellsToGuess > 0) {
                    if (isGiven[row][col]) {
                        cellsToGuess--;
                        continue;
                    }
                    isGiven[row][col] = true;
                    cellsToGuess--;
                } else {
                    break;
                }
            }
            if (cellsToGuess <= 0) {
                break;
            }
        }
    }

    private void makeBoldBlocks() {
        int boldBlockRow = new Random().nextInt(SudokuConstants.SUBGRID_SIZE);
        int boldBlockCol = new Random().nextInt(SudokuConstants.SUBGRID_SIZE);

        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (row % SudokuConstants.SUBGRID_SIZE == boldBlockRow && col % SudokuConstants.SUBGRID_SIZE == boldBlockCol) {
                    isGiven[row][col] = true;
                } else {
                    isGiven[row][col] = false;
                }
            }
        }
    }
}
