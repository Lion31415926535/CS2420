
// QuadraticProbing Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// bool insert( x )       --> Insert x
// bool remove( x )       --> Remove x
// bool contains( x )     --> Return true if x is present
// void makeEmpty( )      --> Remove all items


import java.util.ArrayList;

/**
 * Probing table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author Mark Allen Weiss
 */
public class HashTable<E>
{
    /**
     * Construct the hash table.
     */
    public HashTable( )
    {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    public HashTable( int size )
    {
        allocateArray( size );
        doClear( );
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * Implementation issue: This routine doesn't allow you to use a lazily deleted location.  Do you see why?
     * @param x the item to insert.
     */
    public boolean insert( E x )
    {
        // Insert x as active
        int currentPos = findPos( x );
        if( isActive( currentPos ) )
            return false;

        array[ currentPos ] = new HashEntry<>( x, true );
        currentActiveEntries++;

        // Rehash; see Section 5.5
        if( ++occupiedCt > array.length / 2 )
            rehash( );

        return true;
    }

    public String toString (int limit){
        StringBuilder sb = new StringBuilder();
        int ct=0;
        for (int i=0; i < array.length && ct < limit; i++){
            if (array[i]!=null && array[i].isActive) {
                sb.append( i + ": " + array[i].element + "\n" );
                ct++;
            }
        }
        return sb.toString();
    }

    /**
     * Expand the hash table.
     */
    private void rehash( )
    {
        HashEntry<E> [ ] oldArray = array;

        // Create a new double-sized, empty table
        allocateArray( 2 * oldArray.length );
        occupiedCt = 0;
        currentActiveEntries = 0;

        // Resets the average probe count
        this.totalUses = 0;
        this.totalProbeCount = 0;

        // Copy table over
        for( HashEntry<E> entry : oldArray )
            if( entry != null && entry.isActive )
                insert( entry.element );
    }

    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     * Never returns an inactive location.
     */
    private int findPos( E x )
    {
        int probeCount = 1;
        int offset = 1;
        int currentPos = myhash( x );

        while( array[ currentPos ] != null &&
                !array[ currentPos ].element.equals( x ) )
        {
            probeCount++;
            currentPos += offset;  // Compute ith probe
            offset += 2;
            if( currentPos >= array.length )
                currentPos -= array.length;
        }
        this.totalProbeCount += probeCount;
        this.totalUses++;

        return currentPos;
    }

    /**
     * Returns the average probe count needed for adding, deleting, finding, etc.
     * The total probe count and total uses are kept track of and updated each time findPos is called
     * The average probe count restarts when rehashing occurs since that would affect, and should lower, the average
     * @return The average probe count per operation
     */
    public float getAverageProbeCount() {
        return (float) this.totalProbeCount / this.totalUses;
    }

    /**
     * Returns an ArrayList of all elements in the table
     * Iterates through the hash table and adds all active, non-null elements to an array list
     * @return ArrayList of all active elements in the table
     */
    public ArrayList<E> getAll() {
        ArrayList<E> allData = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].isActive) {
                allData.add(array[i].element);
            }
        }
        return allData;
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     * @return true if item removed
     */
    public boolean remove( E x )
    {
        int currentPos = findPos( x );
        if( isActive( currentPos ) )
        {
            array[ currentPos ].isActive = false;
            currentActiveEntries--;
            return true;
        }
        else
            return false;
    }

    /**
     * Get current size.
     * @return the size.
     */
    public int size( )
    {
        return currentActiveEntries;
    }

    /**
     * Get length of internal table.
     * @return the size.
     */
    public int capacity( )
    {
        return array.length;
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return true if item is found
     */
    public boolean contains( E x )
    {
        int currentPos = findPos( x );
        return isActive( currentPos );
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public E find( E x )
    {
        int currentPos = findPos( x );
        if (!isActive( currentPos )) {
            return null;
        }
        else {
            return array[currentPos].element;
        }
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive( int currentPos )
    {
        return array[ currentPos ] != null && array[ currentPos ].isActive;
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        doClear( );
    }

    private void doClear( )
    {
        occupiedCt = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }

    private int myhash( E x )
    {
        int hashVal = x.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }

    private static class HashEntry<E>
    {
        public E  element;   // the element
        public boolean isActive;  // false if marked deleted

        public HashEntry( E e )
        {
            this( e, true );
        }

        public HashEntry( E e, boolean i )
        {
            element  = e;
            isActive = i;
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private HashEntry<E> [ ] array; // The array of elements
    private int occupiedCt;         // The number of occupied cells: active or deleted
    private int currentActiveEntries;                  // Current size

    private int totalProbeCount;
    private int totalUses;

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray( int arraySize )
    {
        array = new HashEntry[ nextPrime( arraySize ) ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     *
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }


    // Simple main
    public static void main( String [ ] args ) {
        HashTable<Pair> H = new HashTable<>();

        // Test Insertion
        Pair pair1 = new Pair("James", 42);
        if (H.insert(pair1)) {
            System.out.printf("%s was inserted\n", pair1.get1());
        } else {
            System.out.printf("%s wasn't inserted\n", pair1.get1());
        }

        Pair pair2 = new Pair("Harry", 11);
        if (H.insert(pair2)) {
            System.out.printf("%s was inserted\n", pair2.get1());
        } else {
            System.out.printf("%s wasn't inserted\n", pair2.get1());
        }

        // Insert a duplicate key, should print that it wasn't inserted
        Pair pair1Copy = new Pair("James", 25);
        if (H.insert(pair1Copy)) {
            System.out.printf("%s was inserted\n", pair1.get1());
        } else {
            System.out.printf("%s wasn't inserted\n", pair1.get1());
        }
        System.out.println();

        // Test Find
        // Should print out each pair
        System.out.println(H.find(pair1));
        System.out.println(H.find(pair2));
        // Should print out pair 1, not the copy since it will only find using the key
        System.out.println(H.find(pair1Copy));
        System.out.println();

        // Test Delete
        H.remove(pair2);
        // Should print null because pair2 was deleted
        System.out.println(H.find(pair2));

        Pair pair3 = new Pair("Lily", 45);

        // Should print that pair3 wasn't deleted because pair 3 wasn't in the hash table
        if (H.remove(pair3)) {
            System.out.printf("%s was deleted", pair3.get1());
        } else {
            System.out.printf("%s wasn't deleted", pair3.get1());
        }
        System.out.println();

        // Test Changing Values
        System.out.println(H.find(pair1));
        // Change the value of the object found at pair1 key
        H.find(pair1).changeSecond(85);
        System.out.println(H.find(pair1));
        System.out.println();

        // Test Initial Size
        // Should create a hash table with size 7, since 7 is next prime after 6.
        HashTable<Pair> H2 = new HashTable<>(6);


        // Test Rehash
        // Creates an array of pairs greater than the size of the table
        Pair[] pairs = new Pair[100];
        for (int i = 0; i < pairs.length; i++) {
            pairs[i] = new Pair(String.format("pair%d", i),i);
        }

        // Adds more pairs than the size of the table
        // Shouldn't cause an error due to rehashing
        // We also see the average probe count generally increasing until a rehash when it goes back to down to 1
        for (Pair pair : pairs) {
            if (H2.insert(pair)) {
                System.out.printf("%s was added\n", pair.get1());
                System.out.println(H2.getAverageProbeCount());
            }
        }
        System.out.println();


        // Test getAll()
        ArrayList<Pair> getAllList = H2.getAll();

        // Should print all elements but not in sorted order
        System.out.println(getAllList);
        // Sorts the elements
        getAllList.sort(null);
        // Should print all elements in ascending order by key
        System.out.println(getAllList);
    }
}

