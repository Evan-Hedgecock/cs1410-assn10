public class BinarySearchTree<T extends Comparable<T>> {
    private static class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        @Override
        public int compareTo(TreeNode<T> node) {
           return node.data.compareTo(this.data);
        }
    }
    private TreeNode<T> root;
    public boolean insert(T data) {
        TreeNode<T> newNode = new TreeNode<>();
        newNode.data = data;
//        If this is first node inserted, assign to root node
        if (root == null) {
            root = newNode;
            return true;
        } else {
            TreeNode<T> parent = null;
            TreeNode<T> node = root;
// find the node to attach the new node to
            while(node != null) {
                parent = node;
                if (newNode.data.compareTo(parent.data) == 0) return false;
                if (parent.compareTo(newNode) < 0) { // go to left
                    node = node.left;
                } else { // go to the right
                    node = node.right;
                }
            }
// attach the node
            if (parent.compareTo(newNode) < 0) { // attach to the left
                parent.left = newNode;
            } else { // attach to the right
                parent.right = newNode;
            }
            return true;
        }
    }
    public void display(String message) {
        System.out.println(message);
    }
    private void displayInOrder(TreeNode<T> node) {
        if (node == null) return;
        displayInOrder(node.left);
        System.out.println(node.data);
        displayInOrder(node.right);
    }
    public void displayTreeStructure() {
        displayTreeStructure(root, "");
    }
    private void displayTreeStructure(TreeNode<T> node, String prefix) {
        if (node == null) return;
        displayTreeStructure(node.right, prefix + " ");
        System.out.println(prefix + node.data);
        displayTreeStructure(node.left, prefix + " ");
    }
    public boolean search(T data) {
        return search(root, data);
    }
    private boolean search(TreeNode<T> node, T data) {
        if (node == null) return false;
        if (node.data == data) return true;
        if (data.compareTo(node.data) < 0) { // value must be on the left
            return search(node.left, data);
        } else {
            return search(node.right, data);
        }
    }
    public boolean remove(T data) {
        TreeNode<T> parent = null;
        TreeNode<T> node = root;
// find the node you are deleting
        boolean done = false;
        while(!done && node != null) {
            if(data.compareTo(node.data) < 0) {// go to left
                parent = node;
                node = node.left;
            } else if (data.compareTo(node.data) > 0) { // go to the right
                parent = node;
                node = node.right;
            } else {
                done = true;
            }
        }
        if (node == null) return false;
        if (node.left == null) {
// where there is no left child we can do something
// similar to what we do with a linked list - link the parent
// node to the right child. Remember to handle the root!
            if (node == root) {
                root = node.right;
            } else if (node == parent.left) {
                parent.left = node.right;
            } else {
                parent.right = node.right;
            }
            return true;
        } else {
/* If there is a left child, we have to so something more complex.
1. Find the right-most node of the left subtree
2. Keep track of the parent of the right most node
3. When you find the right most node replace the value in the node
replace the value in the node you are deleting with the value in
the right-most node.
4. Attach the left node of the right-most node to either
the left or the right of the right-most's parent, depending on
which side the right most is found on.
*/
            TreeNode<T> parentOfRightMost = node;
            TreeNode<T> rightMost = node.left;
            while(rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            node.data = rightMost.data;
            if (parentOfRightMost.left == rightMost) {
                parentOfRightMost.left = rightMost.left;
            } else {
                parentOfRightMost.right = rightMost.left;
            }
            return true;
        }
    }
    public int numberNodes() {
        if (root == null) return 0;
        // Call recursive method with the args of which node to look at, and the current count
        return 1 + recurseNodeCount(root.left) + recurseNodeCount(root.right);
    }
    private int recurseNodeCount(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + recurseNodeCount(node.left) + recurseNodeCount(node.right);
    }
    public int numberLeafNodes() {
        if (root == null) return 0;
        return recurseLeafNodeCount(root.left) + recurseLeafNodeCount(root.right);
    }
    private int recurseLeafNodeCount(TreeNode<T> node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return recurseLeafNodeCount(node.left) + recurseLeafNodeCount(node.right);
    }
    public int height() {
        if (root == null) return 0;
        // Call recursive function for each layer of tree
        return Math.max(recurseHeight(root.left), recurseHeight(root.right));
    }
    // Claude AI helped me think through the logic for this problem, no code was provided. The chat.txt file includes
    // our conversation.
    private int recurseHeight(TreeNode<T> node) {
        // Base case is node is null, then return 0
        if (node == null) return 0;
        // Get height of left subtree
        // Get height of right subtree
        // Return whichever height is greater
        // Intellij suggested Math.max
        return Math.max(1 + recurseHeight(node.left), 1 + recurseHeight(node.right));
    }
}