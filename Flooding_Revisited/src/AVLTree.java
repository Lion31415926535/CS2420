// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public AVLTree( )
    {
        root = null;
    }

    /**
     * Inserts the given element
     * @param element the item to insert.
     */
    public void insert( AnyType element )
    {
        root = insert( element, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param element the item to remove.
     */
    public void remove( AnyType element )
    {
        root = remove( element, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param element the item to remove.
     * @param currentNode the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove(AnyType element, AvlNode<AnyType> currentNode )
    {
        if( currentNode == null )
            return currentNode;   // Item not found; do nothing

        int compareResult = element.compareTo( currentNode.element );

        if( compareResult < 0 )
            currentNode.left = remove(element, currentNode.left );
        else if( compareResult > 0 )
            currentNode.right = remove(element, currentNode.right );
        else if( currentNode.left != null && currentNode.right != null ) // Two children
        {
            currentNode.element = findMin( currentNode.right ).element;
            currentNode.right = remove( currentNode.element, currentNode.right );
        }
        else
            currentNode = ( currentNode.left != null ) ? currentNode.left : currentNode.right;
        return balance( currentNode );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMin( root ).element;
    }

    /**
     * Calls the private method deleteMin to delete the minimum value in the AVL Tree
     */
    public  void  deleteMin( ){

        root =  deleteMin(root);
     }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param element the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType element)
    {
        return contains(element, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( String label)
    {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume currentNode is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> currentNode )
    {
        if( currentNode == null )
            return currentNode;

        if( height( currentNode.left ) - height( currentNode.right ) > ALLOWED_IMBALANCE )
            if( height( currentNode.left.left ) >= height( currentNode.left.right ) )
                currentNode = rightRotation( currentNode );
            else
                currentNode = doubleRightRotation( currentNode );
        else
        if( height( currentNode.right ) - height( currentNode.left ) > ALLOWED_IMBALANCE )
            if( height( currentNode.right.right ) >= height( currentNode.right.left ) )
                currentNode = leftRotation( currentNode );
            else
                currentNode = doubleLeftRotation( currentNode );

        currentNode.height = Math.max( height( currentNode.left ), height( currentNode.right ) ) + 1;
        return currentNode;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> currentNode )
    {
        if( currentNode == null )
            return -1;

        int leftHeight = checkBalance(currentNode.left);
        int rightHeight = checkBalance( currentNode.right );
        if( Math.abs( height( currentNode.left ) - height( currentNode.right ) ) > 1 ||
                height( currentNode.left ) != leftHeight || height( currentNode.right ) != rightHeight)
            System.out.println( "\n\n***********************OOPS!!" );

        return height( currentNode );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param element the item to insert.
     * @param currentNode the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType element, AvlNode<AnyType> currentNode )
    {
        if( currentNode == null )
            return new AvlNode<>( element, null, null );

        int compareResult = element.compareTo( currentNode.element );

        if( compareResult < 0 )
            currentNode.left = insert( element, currentNode.left );
        else
            currentNode.right = insert( element, currentNode.right );

        return balance( currentNode );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param currentNode the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> currentNode )
    {
        if( currentNode == null )
            return currentNode;

        while( currentNode.left != null )
            currentNode = currentNode.left;
        return currentNode;
    }

    /**
     * Finds and deletes the minimum node (the left-most node)
     * @param currentNode The current node
     * @return The node after deleting
     */
    private AvlNode<AnyType> deleteMin( AvlNode<AnyType> currentNode )
    {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.left == null) {
            return currentNode.right;
        }
        currentNode.left = deleteMin(currentNode.left);
        return balance(currentNode);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param currentNode the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> currentNode )
    {
        if( currentNode == null )
            return currentNode;

        while( currentNode.right != null )
            currentNode = currentNode.right;
        return currentNode;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param element is item to search for.
     * @param currentNode the node that roots the tree.
     * @return true if element is found in subtree.
     */
    private boolean contains( AnyType element, AvlNode<AnyType> currentNode )
    {
        while( currentNode != null )
        {
            int compareResult = element.compareTo( currentNode.element );

            if( compareResult < 0 )
                currentNode = currentNode.left;
            else if( compareResult > 0 )
                currentNode = currentNode.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param currentNode the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> currentNode, String indent )
    {
        if( currentNode != null )
        {
            printTree( currentNode.right, indent+"   " );
            System.out.println( indent+ currentNode.element + "("+ currentNode.height  +")" );
            printTree( currentNode.left, indent+"   " );
        }
    }

    /**
     * Return the height of node currentNode, or -1, if null.
     */
    private int height( AvlNode<AnyType> currentNode )
    {   if (currentNode==null) return -1;
        return currentNode.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> currentNode )
    {
        AvlNode<AnyType> theLeft = currentNode.left;
        currentNode.left = theLeft.right;
        theLeft.right = currentNode;
        currentNode.height = Math.max( height( currentNode.left ), height( currentNode.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), currentNode.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> currentNode )
    {
        AvlNode<AnyType> theRight = currentNode.right;
        currentNode.right = theRight.left;
        theRight.left = currentNode;
        currentNode.height = Math.max( height( currentNode.left ), height( currentNode.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), currentNode.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> currentNode)
    {
        currentNode.left = leftRotation( currentNode.left );
        return rightRotation(currentNode);

    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> currentNode )
    {
        currentNode.right = rightRotation( currentNode.right );
        return leftRotation( currentNode );
    }

    private static class AvlNode<AnyType>
    {
        // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode(AnyType theElement, AvlNode<AnyType> leftChild, AvlNode<AnyType> rightChild )
        {
            element  = theElement;
            left     = leftChild;
            right    = rightChild;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode<AnyType> root;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> t = new AVLTree<>();
        AVLTree<Dwarf> t2 = new AVLTree<>();

        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
        for (int i=0; i < nameList.length; i++)
            t2.insert(new Dwarf(nameList[i]));

        t2.printTree( "The Tree" );

        t2.remove(new Dwarf("Bashful"));

        t2.printTree( "The Tree after delete Bashful" );
        for (int i=0; i < 8; i++) {
            t2.deleteMin();
            t2.printTree( "\n\n The Tree after deleteMin" );
        }
    }

}
