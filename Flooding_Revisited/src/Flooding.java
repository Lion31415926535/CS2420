import java.util.Arrays;

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
        /*
        Creates water sources and begins recursive flooding
         */
        System.out.println("Flooded in Regions Recursive");
        flooded = new boolean[rows][cols];
        for (int i = 0; i < rows; i++)
            Arrays.fill(flooded[i], false);

        // Creates and floods water sources
        for (GridLocation source : sources) {
            if (terrain[source.row][source.col] <= height) {
                flooded[source.row][source.col] = true;
                markFloodedR(source);
            }


        }
        return flooded;
    }

    void markFloodedR(GridLocation currentLocation) {
        /*
        Recursively traverses the terrain and floods appropriate tiles using depth-first search
         */
        try {
                // Create the four neighbors' grid locations
            GridLocation[] neighbors = {
                    new GridLocation(currentLocation.row + 1, currentLocation.col),
                    new GridLocation(currentLocation.row - 1, currentLocation.col),
                    new GridLocation(currentLocation.row, currentLocation.col + 1),
                    new GridLocation(currentLocation.row, currentLocation.col - 1)};

            // If neighbor is valid and isn't flooded, then flood it and call markFloodedR
            for (GridLocation neighbor : neighbors) {
                if (validNeighbor(neighbor) && !flooded[neighbor.row][neighbor.col] && terrain[neighbor.row][neighbor.col] <= height) {
                    flooded[neighbor.row][neighbor.col] = true;
                    markFloodedR(neighbor);
                }
            }



        } catch (StackOverflowError e) {
            System.err.println("Stack Overflow");
            System.exit(0);
        }


    }

    public boolean[][] markFlooded() {
        /*
        Traverses the terrain iteratively and floods appropriate tiles using breath-first search
         */
        flooded = new boolean[rows][cols];
        for (int i = 0; i < rows; i++)
            Arrays.fill(flooded[i], false);
        //Create Queue
        Queue<GridLocation> locations = new Queue<>();

        // Add water sources to queue
        for (GridLocation source : sources) {
            if (terrain[source.row][source.col] <= height) {
                flooded[source.row][source.col] = true;
                locations.add(source);
            }
        }

        while (!locations.isEmpty()) {
            // Retrieve a location from the queue
            GridLocation currentLocation = locations.remove();

            // Create neighbors of current location
            GridLocation[] neighbors = {
                    new GridLocation(currentLocation.row + 1, currentLocation.col),
                    new GridLocation(currentLocation.row - 1, currentLocation.col),
                    new GridLocation(currentLocation.row, currentLocation.col + 1),
                    new GridLocation(currentLocation.row, currentLocation.col - 1)};

            // For each neighbor, check if it is valid, not flooded, and below water height
            // If so, mark the neighbor as flooded then add it to the queue
            for (GridLocation neighbor : neighbors) {
                if (validNeighbor(neighbor) && !flooded[neighbor.row][neighbor.col] && terrain[neighbor.row][neighbor.col] <= height) {
                    flooded[neighbor.row][neighbor.col] = true;
                    locations.add(neighbor);
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


    public int[][] whenFloodExhaustive() {
        return null;
    }

    public int[][] whenFlood() {
        return null;
    }


}
