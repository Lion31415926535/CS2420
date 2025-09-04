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
        // Write recursive code to markFlooded areas
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
