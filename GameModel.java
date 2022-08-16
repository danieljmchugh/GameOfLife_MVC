import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class GameModel
{
    private int gridDim;
    public volatile boolean isStopped;
    public boolean[] gameGrid;         /* Internal representation of board */
    private boolean[] flaggedCells;


    public GameModel(int dimention) {
        gridDim = dimention;
        isStopped = false;
        gameGrid = new boolean[gridDim * gridDim];

        gameGrid[1] = true;
        gameGrid[1 + gridDim + 1] = true;
        gameGrid[1 + 2*gridDim + 1] = true;
        gameGrid[1 + 2*gridDim] = true;
        gameGrid[1 + 2*gridDim - 1] = true;

        flaggedCells = new boolean[gridDim * gridDim];
    }

    private int livingNeighbours(int i) {        
        /* Neightbour offsets */
        int[] neighbourIndexes = {
            i - gridDim - 1,      /* 0: top left */
            i - gridDim,          /* 1: top */
            i - gridDim + 1,      /* 2: top right */
            i - 1,                /* 3: left */
            i + 1,                /* 4: right */
            i + gridDim - 1,      /* 5: bottom left */
            i + gridDim,          /* 6: bottom */
            i + gridDim + 1       /* 7: bottom right */
        };

        ArrayList<Integer> validNeighbours = new ArrayList<Integer>();
        
        /* Index is in leftmost column */
        if (i % gridDim == 0) {
            validNeighbours.addAll(Arrays.asList(1, 2, 4, 6, 7));
        }
        /* Index is in rightmost column */
        else if ((i + 1) % gridDim == 0) {
            validNeighbours.addAll(Arrays.asList(0, 1, 3, 5, 6));
        } 
        else {
            validNeighbours.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        }
        /* Bound withing the range of the grid */
        validNeighbours.removeIf(index -> 
            (neighbourIndexes[index] < 0) || (neighbourIndexes[index] > (gameGrid.length - 1)));

        /* Count number alive neighbours */
        int neighbours = 0;
        for(int n : validNeighbours) {
            if (gameGrid[neighbourIndexes[n]]) {
                neighbours++;
            }
        }
        return neighbours;
    }

    private void flagCellsForUpdating() {

        for(int i = 0; i < gameGrid.length; i++) {
            int neighbours = livingNeighbours(i);
            /* Living Cells */    
            if (gameGrid[i]) {
                if (neighbours < 2 || neighbours > 3) {
                    flaggedCells[i] = true;
                }
            /* Dead Cells */
            } else {
                if (neighbours == 3) {
                    flaggedCells[i] = true;   
                }
            }
        }
    }


    private void updateFlaggedCells() {
        for(int i = 0; i < gameGrid.length; i++) {
            if (flaggedCells[i]) {
                gameGrid[i] = !gameGrid[i];  /* Flip boolean*/
            }
        }
    }



    public void printBoard() {
        for(int i = 0; i < gameGrid.length; i++) {

            if (i % gridDim == 0) { System.out.println(); }

            if (gameGrid[i]) {
                System.out.print("x ");
            } else {
                System.out.print(". ");
            }
        }

        System.out.println();
    }


    public void initGameModel(boolean[] startingCells) {
        gameGrid = startingCells;    
    }

    public void updateGameModel() {
        flagCellsForUpdating();
        updateFlaggedCells();
        /* I believe this is faster then allocating and initialising default values */
        Arrays.fill(flaggedCells, false);        
        // flaggedCells = new boolean[gridDim * gridDim];
    }
}
