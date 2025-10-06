public  class GridLocation implements Comparable<GridLocation>{
    public final int row;
    public final int col;
    public int whenFlood;

    public GridLocation(int row, int col) {
        this.row = row;
        this.col = col;

    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof GridLocation)) return false;
        
        var other = (GridLocation) o;
        return row == other.row && col == other.col;
    }

    @Override public String toString() {
        String sb = "{ " + row + ", " + col + " } ";
        return sb;
    }

    @Override
    public int compareTo(GridLocation g2) {
        if (this.whenFlood < g2.whenFlood) {
            return -1;
        }
        if (this.whenFlood > g2.whenFlood) {
            return 1;
        }
        return 0;
    }

}
