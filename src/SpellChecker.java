import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {
    public static void main(String[] args) {

        // Step 1: Demonstrate tree capabilities
        testTree();

        // Step 2: Read the dictionary and report the tree statistics
        BinarySearchTree<String> dictionary = readDictionary();
        reportTreeStats(dictionary);

        // Step 3: Perform spell checking
        System.out.println("--- Misspelled Words ---");
        File file = new File("Letter.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String word = scanner.next().trim().toLowerCase().replaceAll("\\p{Punct}", " ");
                if (!dictionary.search(word)) {
                    System.out.println(word);
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Error in reading letter: " + e.getMessage());
        }
    }

    /**
     * This method is used to help develop the BST, also for the grader to
     * evaluate if the BST is performing correctly.
     */
    public static void testTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();

        // Add a bunch of values to the tree
        tree.insert("Olga");
        tree.insert("Tomeka");
        tree.insert("Benjamin");
        tree.insert("Ulysses");
        tree.insert("Tanesha");
        tree.insert("Judie");
        tree.insert("Tisa");
        tree.insert("Santiago");
        tree.insert("Chia");
        tree.insert("Arden");

        // Make sure it displays in sorted order
        tree.display("--- Initial Tree State ---");
        reportTreeStats(tree);

        //
        // Try to add a duplicate
        if (tree.insert("Tomeka")) {
            System.out.println("oops, shouldn't have returned true from the insert");
        }
        tree.display("--- After Adding Duplicate ---");
        reportTreeStats(tree);

        //
        // Remove some existing values from the tree
        tree.remove("Olga");    // Root node
        tree.remove("Arden");   // a leaf node
        tree.display("--- Removing Existing Values ---");
        reportTreeStats(tree);

        //
        // Remove a value that was never in the tree, hope it doesn't crash!
        tree.remove("Karl");
        tree.display("--- Removing A Non-Existent Value ---");
        reportTreeStats(tree);
    }

    /**
     * This method is used to report on some stats about the BST
     */
     public static void reportTreeStats(BinarySearchTree<String> tree) {
         System.out.println("-- Tree Stats --");
         System.out.printf("Total Nodes : %d\n", tree.numberNodes());
         System.out.printf("Leaf Nodes  : %d\n", tree.numberLeafNodes());
         System.out.printf("Tree Height : %d\n", tree.height());
     }

    /**
     * This method reads the dictionary and constructs the BST to be
     * used for the spell checking.
     */
    public static BinarySearchTree<String> readDictionary() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        ArrayList<String> words = new ArrayList<>();
        // Read the file dictionary.txt, each word is on a new line
        File file = new File("Dictionary.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                // trim all punctuation and white space from each word
                // replace all punctuation with an empty string literal
                // remove all capitalization from the words and then add into an array
                // Claude AI helped with the regex
                words.add(word.toLowerCase().trim().replaceAll("\\p{Punct}", ""));
                // keep track of how many words are added to avoid iterating over array again
            }
        }
        catch (IOException e) {
            System.out.println("Error reading dictionary: " + e.getMessage());
        }
        // perform a binary traversal over the sorted array of words
        words.sort(String::compareTo);
        binaryTraversal(words, tree);
        return tree;
    }
    private static void binaryTraversal(ArrayList<String> words, BinarySearchTree<String> tree) {
        // if the array length is 0 then return
        if (words.isEmpty()) return;
        // if the array length is 1 then add and return
        if (words.size() == 1) {
           tree.insert(words.getFirst());
           return;
        }
        // if the array is length 2, add the left to array and then right and return
        if (words.size() == 2) {
            tree.insert(words.getFirst());
            tree.insert(words.getLast());
            return;
        }
        // recursively add the middle of array to tree and then call the recursive function with left of middle
        // and also call the recursive function with right of middle
        int middle = words.size() / 2;
        tree.insert(words.get(middle));
        // Asked Claude AI what java array list method was like splice
        ArrayList<String> left = new ArrayList<>(words.subList(0, middle));
        ArrayList<String> right = new ArrayList<>(words.subList(middle, words.size()));
        binaryTraversal(left, tree);
        binaryTraversal(right, tree);
    }
}
