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

    /**
     * Finds at what water height each grid location will flood using an exhaustive approach
     * @return A grid of ints with when that grid location will flood
     */
    public int[][] whenFloodExhaustive() {
        int insertCt=0;
        int[][] whenFlood = new int[rows][cols];
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                whenFlood[i][j] = Integer.MAX_VALUE;
            }
        }

        Queue<GridLocation> toDo = new Queue<>();

        for (GridLocation source : sources) {
            // Updates when flood at the source
            whenFlood[source.row][source.col] = terrain[source.row][source.col];
            toDo.add(source);
            while (!toDo.isEmpty()) {
                // Removes location from queue
                GridLocation prev = toDo.remove();

                // Creates an array of all neighbors
                GridLocation[] neighbors = {
                        new GridLocation(prev.row + 1, prev.col),
                        new GridLocation(prev.row - 1, prev.col),
                        new GridLocation(prev.row, prev.col + 1),
                        new GridLocation(prev.row, prev.col - 1)};

                for (GridLocation neighbor : neighbors) {
                    // if neighbor is valid and whenFlood at the neighbor is greater than previous
//                    if (validNeighbor(neighbor) && whenFlood[neighbor.row][neighbor.col] > whenFlood[prev.row][prev.col]) {
//                        // Add neighbor to the queue and update when flood at its location
//                        insertCt++;
//                        toDo.add(neighbor);
//                        whenFlood[neighbor.row][neighbor.col] = Math.max(terrain[neighbor.row][neighbor.col],
//                                Math.min( whenFlood[neighbor.row][neighbor.col], whenFlood[prev.row][prev.col]));
//                    }
                    if (validNeighbor(neighbor)) {
                        int newFlood = Math.max(terrain[neighbor.row][neighbor.col], Math.min(whenFlood[neighbor.row][neighbor.col], whenFlood[prev.row][prev.col]));

                        if (newFlood < whenFlood[neighbor.row][neighbor.col]) {
                            whenFlood[neighbor.row][neighbor.col] = newFlood;
                            neighbor.whenFlood = newFlood;
                            toDo.add(neighbor);
                            insertCt++;
                        }
                    }
                }
            }
        }

        System.out.println("Exhaustive Nodes " + String.format("%,5d",insertCt));
        return whenFlood;
    }


    /**
     * Finds at what water height each grid location will flood using a priority approach
     * @return A grid of ints with when that grid location will flood
     */
    public int[][] whenFlood() {
        int insertCt=0;
        int[][] whenFlood = new int[rows][cols];
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                whenFlood[i][j] = Integer.MAX_VALUE;
            }
        }

        AVLTree<GridLocation> toDo = new AVLTree<>();

        for (GridLocation source : sources) {
            // Updates when flood at the source
            whenFlood[source.row][source.col] = terrain[source.row][source.col];
            source.whenFlood = terrain[source.row][source.col];
            toDo.insert(source);
        }

        while (!toDo.isEmpty()) {
            // Removes location from the AVL Tree
            GridLocation prev = toDo.findMin();
            toDo.deleteMin();

            // Creates an array of all neighbors
            GridLocation[] neighbors = {
                    new GridLocation(prev.row + 1, prev.col),
                    new GridLocation(prev.row - 1, prev.col),
                    new GridLocation(prev.row, prev.col + 1),
                    new GridLocation(prev.row, prev.col - 1)};

            for (GridLocation neighbor : neighbors) {
                // if neighbor is valid and whenFlood at the neighbor is greater than previous
//                if (validNeighbor(neighbor) && whenFlood[neighbor.row][neighbor.col] > whenFlood[prev.row][prev.col]) {
//                    // Add neighbor to the AVL Tree and update whenFlood at its location
//                    insertCt++;
//
//                    int whenFloodValue = Math.max(terrain[neighbor.row][neighbor.col],
//                            Math.min(whenFlood[neighbor.row][neighbor.col], whenFlood[prev.row][prev.col]));
//                    whenFlood[neighbor.row][neighbor.col] = whenFloodValue;
//                    neighbor.whenFlood = whenFloodValue;
//                    toDo.insert(neighbor);
//                }
                // If valid neighbor then calculate what the new flood level is
                if (validNeighbor(neighbor)) {
                    int newFlood = Math.max(terrain[neighbor.row][neighbor.col], Math.min(whenFlood[neighbor.row][neighbor.col], whenFlood[prev.row][prev.col]));

                    // If the new flood level is less than the current flood level, then update it and add to the AVL tree
                    if (newFlood < whenFlood[neighbor.row][neighbor.col]) {
                        whenFlood[neighbor.row][neighbor.col] = newFlood;
                        neighbor.whenFlood = newFlood;
                        toDo.insert(neighbor);
                        insertCt++;
                    }
                }
            }
        }

        System.out.println("PQ Nodes " +String.format("%,5d",insertCt));
        return whenFlood;
    }


}
