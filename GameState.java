import java.util.ArrayList;
import java.util.List;

public class GameState
{
    private final int gridROWS = 20;
    private final int gridCOLS = 20;
    
    private boolean gameGrid[][] = new boolean[gridROWS][gridCOLS];
    private List<Cell> flaggedCells = new ArrayList<Cell>();
    public List<Cell> liveCells = new ArrayList<Cell>();

    private void updateLiveCells() {
        liveCells.clear();

        for(int row = 0; row < gridROWS; row++) {
            for(int col = 0; col < gridCOLS; col++) {
                if (gameGrid[row][col]) {
                    liveCells.add(new Cell(row, col));
                }
            }
        }
    }

    private int numNeighbours(int row, int col) {
        int neighbours = 0;
        /* max()/min() is to prevent out of bounds error */
        for (int rowNum = Math.max(0, row - 1); rowNum <= Math.min(row + 1, gridROWS - 1); rowNum++) {
            for (int colNum = Math.max(0, col - 1); colNum <= Math.min(col + 1, gridCOLS - 1); colNum++) {
                if (rowNum == row && colNum == col) {
                    ;
                }
                else if (gameGrid[rowNum][colNum]) {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }

    private void flagCellsForUpdating() {
        flaggedCells.clear();

        for(int row = 0; row < gridROWS; row++) {
            for(int col = 0; col < gridCOLS; col++) {
                int neighbours = numNeighbours(row, col);
                /* Living cells */
                if (gameGrid[row][col]) {
                    if ((neighbours < 2) || (neighbours > 3)) {
                        flaggedCells.add(new Cell(row, col));
                    }
                }
                /* Dead cells */
                else if (!gameGrid[row][col]) {
                    if (neighbours == 3) {
                        flaggedCells.add(new Cell(row, col));
                    }
                }
            }
        }
    }


    private void updateFlaggedCells() {
        for (Cell c : flaggedCells) {
            gameGrid[c.getRow()][c.getCol()] = !gameGrid[c.getRow()][c.getCol()]; /* Flips boolean */
        }
    }

    public void initGameState(List<Cell> cells) {
        liveCells = cells;
        for (Cell c : cells) {
            gameGrid[c.getRow()][c.getCol()] = !gameGrid[c.getRow()][c.getCol()];
        }
    }

    public void updateGameState() {
        flagCellsForUpdating();
        updateFlaggedCells(); 
        updateLiveCells();
    }
}
