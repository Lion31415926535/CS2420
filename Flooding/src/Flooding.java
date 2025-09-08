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
            // If current tile is below water height, mark it as flooded
            if (terrain[g.row][g.col] <= height) {
                flooded[g.row][g.col] = true;

                // Create the four neighbors' grid locations
                GridLocation[] neighbors = {
                        new GridLocation(g.row + 1, g.col),
                        new GridLocation(g.row - 1, g.col),
                        new GridLocation(g.row, g.col + 1),
                        new GridLocation(g.row, g.col - 1)};

                // If neighbor is valid and isn't flooded, then call markFlooded
                for (GridLocation n : neighbors) {
                    if (validNeighbor(n) && !flooded[n.row][n.col]) {
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
        //Create Queue
        Queue<GridLocation> locations = new Queue<>();

        // Add water sources to queue
        for (GridLocation g : sources) {
            locations.add(g);
            if (terrain[g.row][g.col] <= height) {
                flooded[g.row][g.col] = true;
            }
        }

        while (!locations.isEmpty()) {
            // Retrieve a location from the queue
            GridLocation g = locations.remove();

            // Create neighbors of current location
            GridLocation[] neighbors = {
                    new GridLocation(g.row + 1, g.col),
                    new GridLocation(g.row - 1, g.col),
                    new GridLocation(g.row, g.col + 1),
                    new GridLocation(g.row, g.col - 1)};

            // For each neighbor, check if it is valid, not flooded, and below water height, then mark as flooded
            for (GridLocation n : neighbors) {
                if (validNeighbor(n) && !flooded[n.row][n.col] && terrain[n.row][n.col] <= height) {
                    flooded[n.row][n.col] = true;
                    locations.add(n);
                }
            }
        }

        return flooded;
    }

    boolean validNeighbor(GridLocation g) {
        int row = g.row;
        int col = g.col;
        return (row >= 0 && col >= 0 && row < rows && col < cols);
    }


}
