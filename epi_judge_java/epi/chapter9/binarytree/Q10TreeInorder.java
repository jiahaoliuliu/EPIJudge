package epi.chapter9.binarytree;
import epi.BinaryTreeNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;

/**
 * The direct implementation of an inorder traversal using recursion has O(h) space complexity,
 * where h is the height of the tree. Recursion can be removed with an explicit stack, but the space
 * complexity remains O(h)
 *
 * Write a nonrecursive program for computing the inorder traversal sequence for a binary tree.
 * Assume nodes have parent fields.
 *
 * Hint: How can you tell whether a node is a left child or right child of its parent?
 */
public class Q10TreeInorder {

  private static class NodeAndState {
    public BinaryTreeNode<Integer> node;
    public Boolean leftSubtreeTraversed;

    public NodeAndState(BinaryTreeNode<Integer> node,
                        Boolean leftSubtreeTraversed) {
      this.node = node;
      this.leftSubtreeTraversed = leftSubtreeTraversed;
    }
  }

  /**
   * Initial analysis: In order means: Left, root, right.
   *
   * We can use a data structure to store the data temporally.
   *
   * @param tree
   * @return
   */
  @EpiTest(testDataFile = "tree_inorder.tsv")
  public static List<Integer> inorderTraversal(BinaryTreeNode<Integer> tree) {
    // TODO: Implement this
    return null;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
