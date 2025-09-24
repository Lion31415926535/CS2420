// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.ArrayList;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     *
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree {
    private BinaryNode root;  // Root of tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create tree from list
     * @param arr   List of elements
     * @param label Name of tree
     * @ordered true if we want an ordered tree
     */
    public Tree(Integer[] arr, String label, boolean ordered) {
        treeName = label;
        if (ordered) {
            root = null;
            for (int i = 0; i < arr.length; i++) {
                bstInsert(arr[i]);
            }
        } else root = buildUnordered(arr, 0, arr.length - 1);
    }


    /**
     * Build a NON BST tree by inorder
     * @param arr nodes to be added
     * @return new tree
     */
    private BinaryNode buildUnordered(Integer[] arr, int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        BinaryNode curr = new BinaryNode(arr[mid], null, null);
        curr.left = buildUnordered(arr, low, mid - 1);
        curr.right = buildUnordered(arr, mid + 1, high);
        return curr;
    }


    /**
     * Change name of tree
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + "\n" + toString(root, 1);
    }

    // Problem 1
    /**
     * Returns a string of the tree displayed vertically with spacing determined by level
     * Complexity is O(n)
     *
     * @param n current node
     * @param level current level
     */
    private String toString(BinaryNode n, int level) {
        if (n == null) {
            return "";
        }
        int space = 2; // Space between each level of the tree
        StringBuilder sb = new StringBuilder();

        //Iterates through tree using disorder or right, me, left
        sb.append(toString(n.right, level + 1));
        sb.append(String.format("%" + level * space + "s\n", n.element.toString()));
        sb.append(toString(n.left, level + 1));
        return sb.toString();
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(n)
     *
     * @param t the node that roots the subtree.
     */
    public String toString2(BinaryNode t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    //Problem 2
    /**
     * The complexity of finding the flip is O(n)
     * reverse left and right children recursively
     */
    public void flip() {
        flip(root);
    }

    /**
     * Recursively flips children of each node in the tree
     * @param n current node
     */
    private void flip(BinaryNode n){
        if (n == null) {
            return;
        }

        // Saves left and right child then swaps them
        BinaryNode rTemp = n.right;
        BinaryNode lTemp = n.left;
        n.right = lTemp;
        n.left = rTemp;

        // Recursively swaps the children of each child
        flip(n.left);
        flip(n.right);
    }


    //Problem 3
    /**
     * Find the deepest node and returns its value
     * The complexity of finding the deepest node is O(n)
     * @return The value (Integer) of the deepest node
     */
    public Integer deepestNode() {
        if (root == null) {
            return null;
        }
        return deepestNode(root, 0).element;
    }

    /**
     * Recursively finds the deepest node in a BST
     * @param n Current node
     * @param level Current level
     * @return An object with the deepest node's element and level
     */
    private DeepNode deepestNode(BinaryNode n, int level) {
        if (n == null) {
            return new DeepNode(0,0);
        }
        if (n.left == null && n.right == null) {
            return new DeepNode(n.element, level);
        }

        // Recursively find the deepest node of each child
        DeepNode deepLeft = deepestNode(n.left, level + 1);
        DeepNode deepRight = deepestNode(n.right, level + 1);

        // Returns the DeepNode with the highest level
        if (deepRight.level > deepLeft.level) {
            return deepRight;
        }
        return deepLeft;
    }

    /**
     * Class to keep track of the deepest node's level and element
     */
    private class DeepNode {
        Integer element;
        int level;

        public DeepNode(Integer element, int level) {
            this.element = element;
            this.level = level;
        }
    }


    //Problem 4
    /**
     * Counts number of nodes in specified level
     * The complexity of nodesInLevel is O(n)
     * @param targetLevel Level to count nodes at
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int targetLevel) {
        return nodesInLevel(root, targetLevel, 0);
    }

    /**
     * Recursively counts the nodes at the specified level
     *
     * @param n current node
     * @param targetLevel level to count nodes at
     * @param currentLevel
     * @return sum of nodes at the level given
     */
    private int nodesInLevel(BinaryNode n, int targetLevel, int currentLevel) {
        if (n == null) {
            return 0;
        }

        // Return 1 if at the correct level
        if (currentLevel == targetLevel) {
            return 1;
        }

        // Recursively finds sum of nodes at the chosen level
        int sum = nodesInLevel(n.left, targetLevel, currentLevel + 1);
        sum += nodesInLevel(n.right, targetLevel, currentLevel + 1);
        return sum;
    }

    //Problem 5
    /**
     * Print all paths from root to leaves
     * The complexity of printAllPaths is O(n)
     */
    public void printAllPaths() {
        printAllPaths(root, "");
    }

    /**
     * Recursively prints the paths to each leaf node
     * @param n Current Node
     * @param path Accumulated path to current node
     */
    private void printAllPaths(BinaryNode n, String path) {
        if (n == null) {
            return;
        }
        // Add current element to the path string
        path += n.element + " ";

        // If node is a leaf node, then print the path
        if (n.left == null && n.right == null) {
            System.out.println(path);
            return;
        }

        // Recursively print paths of children
        printAllPaths(n.left, path);
        printAllPaths(n.right, path);

    }


    //Problem 6
    /**
     * Remove all paths from tree that sum to less than given value
     * If the maximum sum is less than k, then set the root to null
     * Complexity for pruneK is O(n)
     * @param k: minimum path sum allowed in final tree
     */
    public void pruneK(Integer k) {
        int maxSum = pruneK(root, k, 0);
        if (maxSum < k) {
            root = null;
        }
    }

    /**
     * Recursively sums paths to each leaf node, removing paths that don't sum up to k
     * @param n Current node
     * @param k Minimum value to not prune a branch
     * @param sum Sum from root to current node
     * @return The max sum of paths the node is in
     */
    private int pruneK(BinaryNode n, Integer k, int sum) {
        if (n == null) {
            return 0;
        }

        sum += n.element;

        if (n.left == null && n.right == null) {
            return sum;
        }

        // Find the sum of paths through left and right children
        int sumLeft = pruneK(n.left, k, sum);
        int sumRight = pruneK(n.right, k, sum);

        // If the sum of the path is less than k, then remove that path
        if (sumLeft < k) {
            n.left = null;
        }
        if (sumRight < k) {
            n.right = null;
        }
        return Math.max(sumLeft, sumRight);
    }



    //Problem 7
    /**
     * Find the least common ancestor of two nodes
     * Complexity is O(log n)
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public Integer lca(Integer a, Integer b) {
        Integer min = Math.min(a,b);
        Integer max = Math.max(a,b);
        // If the tree contains both nodes, then find and return the lca
        if (contains(min) && contains(max)) {
            BinaryNode  l  = lca(root,min,max);
            return l.element;
        }
        return null;
    }

    /**
     * Recursively finds the node that is ancestor to the given nodes
     * @param n Current node
     * @param low Lower number node to find
     * @param high Higher number node to find
     * @return The node that is the least common ancestor
     */
    private BinaryNode lca(BinaryNode  n,Integer low, Integer  high) {
        // Determines if we need to go right or left to find the lca
        if (n.element < low) {
            return lca(n.right, low, high);
        }
        if (n.element > high) {
            return lca(n.left, low, high);
        }
        return n;
    }


    //Problem 8
    /**
     * Balances the tree
     * Complexity O(n)
     * Creates an array list then array of the in order traversal of the tree
     * Then rebuilds the tree from that order
     */
    public void balanceTree() {

        // Creates an in order array list then converts it into an array
        ArrayList<Integer> inOrderArrayList = new ArrayList<>();
        getInOrderArray(inOrderArrayList, root);
        Integer[] inOrderArray = new Integer[inOrderArrayList.size()];
        for (int i = 0; i < inOrderArrayList.size(); i++) {
            inOrderArray[i] = inOrderArrayList.get(i);
        }

        // Rebuilds the tree in a balanced order
        root = buildUnordered(inOrderArray, 0, inOrderArray.length - 1);
    }

    /**
     * Recursively creates array list with the elements of a tree in order
     * @param arr Original array
     * @param n Current Node
     * @return An array list from an inorder traversal of the tree
     */
    private ArrayList<Integer> getInOrderArray(ArrayList<Integer> arr, BinaryNode n) {
        if (n == null) {
            return null;
        }
        getInOrderArray(arr, n.left);
        arr.add(n.element);
        getInOrderArray(arr, n.right);
        return arr;

    }


    //Problem 9
    /**
     * In a BST, keep only nodes between range
     * Complexity is O(n)
     *
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(Integer a, Integer b) {
        root = keepRange(root, a, b);
    }

    /**
     * Recursively finds the nodes within the range, keeping those and deleting nodes outside the range
     * @param n Current Node
     * @param low Lower bound of range to keep
     * @param high Upper bound of range
     * @return A node after necessary branches have been removed
     */
    private BinaryNode keepRange(BinaryNode n, Integer low, Integer high) {
        if (n == null) {
            return null;
        }

        // Determines if current node is in range, and call keep range on left, right, or both children accordingly
        if (n.element >= low && n.element <= high) {
            n.left = keepRange(n.left, low, high);
            n.right = keepRange(n.right, low, high);
            return n;
        }
        if (n.element < low) {
            return keepRange(n.right, low, high);
        }
        if (n.element > high) {
            return  keepRange(n.left, low, high);
        }
        return n;
    }


    //Problem 10
    /**
     * Counts all non-null binary search trees embedded in tree
     *  The complexity of countBST is O(n)
     * @return Count of embedded binary search trees
     */
    public Integer countBST() {
        if (root == null) {
            return 0;
        }
        return countBST(root).sum;
    }

    /**
     * Recursively counts all sub-BST's within a BST
     * @param n Current node
     * @return An object with the count of subtrees and if the current tree is a BST
     */
    private BSTCounter countBST(BinaryNode n) {
        if (n == null) {
            return new BSTCounter(0, true);
        }

        // Recursively finds sum of BSTs of both children
        BSTCounter leftBST = countBST(n.left);
        BSTCounter rightBST = countBST(n.right);

        // If both children are BSTs
        if (leftBST.isBST && rightBST.isBST) {
            // If current node is a BST
            if ((n.left == null || n.left.element < n.element) && (n.right == null || n.right.element > n.element)) {
                return new BSTCounter(1 + leftBST.sum + rightBST.sum, true);
            }
        }

        return new BSTCounter(leftBST.sum + rightBST.sum, false);
    }

    /**
     * Class that counts sum of sub-BSTs and if the current tree is also a BST
     */
    private class BSTCounter {
        int sum;
        boolean isBST;

        public BSTCounter (int sum, boolean isBST) {
            this.sum = sum;
            this.isBST = isBST;
        }
    }


    //Problem 11
    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     * Complexity is O(n^2)
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeFromTraversals(Integer[] inOrder, Integer[] preOrder) {
        root = buildTreeFromTraversals(inOrder, preOrder, 0, inOrder.length - 1);
    }

    /**
     * Recursively creates the left and right branches of the tree and all subtrees
     * Finds the current preorder value in the inOrder list then uses all values left of that for the left subtree and values right of it for the right subtree
     * @param inOrder List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     * @param start The starting index for the inOrder list
     * @param end The ending index for the inOrder list
     * @return The newest node in the tree
     */
    public BinaryNode buildTreeFromTraversals(Integer[] inOrder, Integer[] preOrder, int start, int end) {
        if (start > end) {
            return null;
        }

        int preIndex = findPreIndex(preOrder);
        BinaryNode n = new BinaryNode(preOrder[preIndex]);

        // Sets used preOrder node to null so we can move to the next preOrder node
        preOrder[preIndex] = null;

        // Finds the index of the current node in the inOrder list
        int inIndex = findInIndex(inOrder, n.element);

        // Build the left child from the inOrder list before the current node
        n.left = buildTreeFromTraversals(inOrder, preOrder, start, inIndex - 1);
        // Build the right child from the inOrder list after the current node
        n.right = buildTreeFromTraversals(inOrder, preOrder, inIndex + 1, end);

        return n;
    }

    /**
     * Finds the index of the next preorder node
     * @param preOrder List of preorder nodes
     * @return The index of the next preorder node needed
     */
    private int findPreIndex(Integer[] preOrder) {
        for (int i = 0; i < preOrder.length; i++) {
            if (preOrder[i] != null) {
                return i;
            }
        }
        return -1; // Never will reach this with valid input
    }

    /**
     *
     * @param inOrder List of inorder nodes
     * @param target The value of the preorder node that we want to find in the inorder list
     * @return The index of the node with value matching the target value
     */
    private int findInIndex(Integer[] inOrder, Integer target) {
        for (int i = 0; i < inOrder.length; i++) {
            if (inOrder[i].equals(target)) {
                return i;
            }
        }
        return -1; // Never will reach this with valid input
    }

    /**
     * Insert into a bst tree; duplicates are allowed
     * The complexity of bstInsert depends on the tree.  If it is balanced the complexity is O(log n)
     * @param x the item to insert.
     */
    public void bstInsert(Integer x) {

        root = bstInsert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode bstInsert(Integer x, BinaryNode t) {
        if (t == null)
            return new BinaryNode (x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = bstInsert(x, t.left);
        } else {
            t.right = bstInsert(x, t.right);
        }
        return t;
    }

    /**
     * Determines if item is in tree
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(Integer item) {
        return contains(item, root);
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(Integer x, BinaryNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    public Integer sumAll(){
        BinaryNode  r =   root;
        return sumAll(r);
    }
    public Integer sumAll(BinaryNode  t){
        if (t==null) return 0;
        return t.element + sumAll(t.left) + sumAll(t.right);
    }



    // Basic node stored in unbalanced binary  trees
    public static class BinaryNode  {
        Integer element;            // The data in the node
        BinaryNode left;   // Left child
        BinaryNode  right;  // Right child

        // Constructors
        BinaryNode(Integer theElement) {
            this(theElement, null, null);
        }

        BinaryNode(Integer theElement, BinaryNode lt, BinaryNode rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        // toString for BinaryNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            return sb.toString();
        }

    }


}
