public class Pair<T1,T2> {
    private T1 first;
    private T2 second;

    public T1 get1(){
        return this.first;
    }
    public T2 get2(){
        return this.second;
    }
    Pair( T1 a, T2 b){
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
    public void changeSecond(T2 newVal) {
        this.second = newVal;
    }

    public static void main(String args[]){
        Pair<String,Integer> class1 = new Pair<>( "CS", 2420);
        Pair<String,Integer> class2 = new Pair<>( "Math", 2610);
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
}