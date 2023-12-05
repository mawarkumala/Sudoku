import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MySudoku extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();

    public MySudoku() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);
            }
        }

        ActionListener commonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cell clickedCell = (Cell) e.getSource();
                // Handle the common action for all cells
                // For example, you can get the clicked cell and perform some action
                // Add your logic here
            }
        };

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(commonListener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void newGame() {
        puzzle.newPuzzle(2);

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
        applySudokuRules();
    }

    private void applySudokuRules() {
        // Mengecek dan memberi warna merah pada kolom yang memiliki angka yang sama
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            for (int row1 = 0; row1 < SudokuConstants.GRID_SIZE - 1; row1++) {
                for (int row2 = row1 + 1; row2 < SudokuConstants.GRID_SIZE; row2++) {
                    if (cells[row1][col].getNumber() == cells[row2][col].getNumber()) {
                        cells[row1][col].setStatus(CellStatus.WRONG_GUESS);
                        cells[row2][col].setStatus(CellStatus.WRONG_GUESS);
                    }
                }
            }
    }
        // Mengecek dan memberi warna merah pada baris yang memiliki angka yang sama
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col1 = 0; col1 < SudokuConstants.GRID_SIZE - 1; col1++) {
                for (int col2 = col1 + 1; col2 < SudokuConstants.GRID_SIZE; col2++) {
                    if (cells[row][col1].getNumber() == cells[row][col2].getNumber()) {
                        cells[row][col1].setStatus(CellStatus.WRONG_GUESS);
                        cells[row][col2].setStatus(CellStatus.WRONG_GUESS);
                    }
                }
            }
        }
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }
}

