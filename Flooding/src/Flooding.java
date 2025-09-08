import java.util.*;

public class Flooding {
    int[][] terrain;
    GridLocation[] sources;
    boolean[][] flooded;
    int height;
    int rows;
    int cols;

    Flooding(int[][] terrain, GridLocation[] sources, int height) {
        this.terrain = terrain;
        this.sources = sources;
        this.height = height;
        rows = terrain.length;
        cols = terrain[0].length;
    }


    public boolean[][] markFloodedR() {
        System.out.println("Flooded in Regions Recursive");
        flooded = new boolean[rows][cols];
        for (int i = 0; i < rows; i++)
            Arrays.fill(flooded[i], false);
        for (GridLocation g : sources) {
            markFloodedR(g);

        }
        return flooded;
    }

    void markFloodedR(GridLocation g) {
        try {
            // First, check if current tile is below water
            // If so, mark as flooded
            if (terrain[g.row][g.col] <= height) {
                flooded[g.row][g.col] = true;
            }

            GridLocation[] neighbors = {
                    new GridLocation(g.row + 1, g.col),
                    new GridLocation(g.row - 1, g.col),
                    new GridLocation(g.row, g.col + 1),
                    new GridLocation(g.row, g.col - 1)};
            // Next, for each neighbor, check if a neighbor is valid
            // Then call markFloodedR for the neighbor
            for (GridLocation n : neighbors) {
                if (validNeighbor(n) && !flooded[n.row][n.col]) {
                    if (terrain[n.row][n.col] <= height) {
                        markFloodedR(n);
                    }
                }
            }

        } catch (StackOverflowError e) {
            System.err.println("Stack Overflow");
            System.exit(0);
        }


    }

    public boolean[][] markFlooded() {
        flooded = new boolean[rows][cols];
        for (int i = 0; i < rows; i++)
            Arrays.fill(flooded[i], false);
        //Write code to mark flooding
        return flooded;
    }

    boolean validNeighbor(GridLocation g) {
        int row = g.row;
        int col = g.col;
        return (row >= 0 && col >= 0 && row < rows && col < cols);
    }


}
