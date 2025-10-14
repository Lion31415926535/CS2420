public class Pair implements Comparable{
    private String first;
    private int second;

    public String get1(){
        return this.first;
    }
    public int get2(){
        return this.second;
    }
    Pair( String a, int b){
        this.first = a;
        this.second = b;
      }

    public String toString(){
      return  "(" + first +"," +second +")";
    }

    /**
     * Changes the second variable to a new value
     * @param newVal New value for the second variable
     */
    public void changeSecond(int newVal) {
        this.second = newVal;
    }

    public static void main(String args[]){
        Pair class1 = new Pair( "CS", 2420);
        Pair class2 = new Pair( "Math", 2610);
        System.out.println("I am taking " + class1 + class2);
    }

    /**
     * Creates a hash code based only on the first item (the key)
     * @return A unique hash code
     */
    @Override
    public int hashCode() {
        return this.first.hashCode();
    }

    /**
     * Compares this pair with an object.
     * If the object isn't a Pair, then it just compares the objects
     * If the object is a Pair, then it compares their first values
     * @param otherObject The other Pair to compare to
     * @return negative if less than, positive if greater than, and 0 if equal
     */
    @Override
    public int compareTo(Object otherObject) {
        if (!(otherObject instanceof Pair)) {
            return this.compareTo(otherObject);
        }
        Pair otherPair = (Pair) otherObject;
        return this.first.compareTo(otherPair.first);
    }


    /**
     * Checks if this pair is equal to another object
     * If the object isn't a Pair type it returns false
     * If the object is a Pair type, then it compares the first value
     * @param otherObject The object to be compared
     * @return true if they are equal, otherwise false
     */
    @Override
    public boolean equals(Object otherObject) {
        // Make sure you are comparing to a Pair object
        if (!(otherObject instanceof Pair)) {
            return false;
        }
        Pair otherPair = (Pair) otherObject;
        return this.first.compareTo(otherPair.first) == 0;
    }
}