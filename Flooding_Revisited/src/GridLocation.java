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

    /**
     * Compares the whenFlood of two grid locations.
     * If this grid location is less than the other one, it returns -1.
     * Returns 0 if they are equal
     * Returns 1 if this one is greater than the other
     * @param otherLocation the object to be compared.
     * @return -1,0,1 depending on if it is less than, equal to, or greater than
     */
    @Override
    public int compareTo(GridLocation otherLocation) {
        if (this.whenFlood < otherLocation.whenFlood) {
            return -1;
        }
        if (this.whenFlood > otherLocation.whenFlood) {
            return 1;
        }
        return 0;
    }

}
