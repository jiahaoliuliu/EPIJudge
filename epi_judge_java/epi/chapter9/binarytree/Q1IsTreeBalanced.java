package epi.chapter9.binarytree;
import epi.BinaryTreeNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * A binary tree is said to be height-balanced if for each node in the tree, the difference in the
 * height of its left and right subtrees is at most one. A perfect binary tree is height-balanced,
 * as is a complete binary tree. A height-balanced binary tree does not have to be perfect or complete.
 *
 * Write a program that takes as input the root of a binary tree and checks whether the tree is
 * height-balanced.
 *
 * Hint: Think of a classic binary tree algorithm
 *
 * Variant: Write a program that returns the size of the largest subtree that is complete
 *
 * Variant: Define a node in a binary tree to be k-balanced if the difference in the number of nodes in
 * its left and right subtrees is not more than k.
 * Design an algorithm that takes as input a binary tree and positive integer k, and returns a node in the binary tree
 * such that the node is not k-balanced, but all of its descendants are k-balanced. For example, when applied to the binary
 * tree  in Figure 9.1 on page 123, if k = 3, your algorithm should return Node j
 */
public class Q1IsTreeBalanced {

  /**
   * Initial analysis: A tree is balanced if the height of left node and right
   * node does not differ more than 1.
   *
   * If node is a leaf, the height is 0
   *
   * We can check both left and right subtree and runs recursively, check the difference
   * at the same time. If they are not balanced, throw an exception
   *
   * Time complexity: O(n) where n is the number of nodes
   * Space complexity: O(n) where n is the number of nodes
   *
   * @param tree
   * @return
   */
  @EpiTest(testDataFile = "is_tree_balanced.tsv")
  public static boolean isBalanced(BinaryTreeNode<Integer> tree) {
    try {
      checkHeight(tree);
    } catch (IllegalStateException illegalStateException) {
      return false;
    }
    return true;
  }

  private static int checkHeight(BinaryTreeNode<Integer> tree) {
    if (tree == null) return 0; // This should return -1 because it is below to the leaf

    // Post order
    int leftSubtreeHeight = checkHeight(tree.getLeft());
    int rightSubtreeHeight = checkHeight(tree.getRight());

    int higher = Math.max(leftSubtreeHeight, rightSubtreeHeight);
    int lower = Math.min(leftSubtreeHeight, rightSubtreeHeight);
    if (higher - lower > 1) {
      throw new IllegalStateException("The tree is not balanced");
    }

    return 1 + higher;
  }

  /**
   * Book solution
   * @param args
   */
  private static class BalanceStatusWithHeight {
    public boolean balanced;
    public int height;

    public BalanceStatusWithHeight(boolean balanced, int height) {
      this.balanced = balanced;
      this.height = height;
    }
  }

  public static boolean bookSol1IsBlanaced(BinaryTreeNode<Integer> tree) {
    return checkBalanced(tree).balanced;
  }

  private static BalanceStatusWithHeight checkBalanced(BinaryTreeNode<Integer> tree) {
    if (tree == null) {
      return new BalanceStatusWithHeight(true, -1);
    }

    BalanceStatusWithHeight leftResult = checkBalanced(tree.left);
    // If the left subtree is not balanced, propagate this information up
    if (!leftResult.balanced) {
      return leftResult;
    }

    BalanceStatusWithHeight rightResult = checkBalanced(tree.right);
    // If the right subtree is not balanced, propagate this information up
    if (!rightResult.balanced) {
      return rightResult;
    }

    boolean isBalanced = Math.abs(leftResult.height - rightResult.height) <= 1;
    int height = Math.max(leftResult.height, rightResult.height) + 1;
    return new BalanceStatusWithHeight(isBalanced, height);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeBalanced.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
