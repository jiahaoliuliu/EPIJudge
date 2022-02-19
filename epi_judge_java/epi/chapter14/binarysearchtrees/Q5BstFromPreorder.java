package epi.chapter14.binarysearchtrees;
import epi.BstNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

/**
 * As discussed in Problem 9.11 on Page 138 there are many different binary tres that yield the same
 * sequence of visited nodes in an inorder traversal. This is also true for preorder and postorder
 * traversals. Given the sequence of nodes that an inorder traversal sequence visits and either of the
 * other two traversal sequences, there exists a unique binary tree that yields those sequences. Here
 * we study if it is possible to reconstruct the tree with less traversal information when the tree is
 * known to be a BST.
 *
 * It is critical that the elements stored in the tree be unique. If the root contains key v and the
 * tree contains more occurrences of v, we cannot always identify from the sequence whether the
 * subsequent vs are in the left subtree or the right subtree. For example, for the tree rooted at G in
 * Figure 14.2 on Page 226 the preorder traversal sequence is 285, 243, 285, 401. the same preorder
 * traversal sequence is seen if 285 appears in the left subtree as the right child of the node with key
 * 243 and 401 is at the root's right child.
 *
 * Suppose you are given the sequence in which keys are visited in an inorder traversal of a BST, and
 * all keys are distinct. Can you reconstruct the BST from the sequence? If so, write a program to do
 * so. Solve the same problem for preorder and postorder traversal sequences.
 *
 * Hint: Draw the five BSTs on the key 1, 2, 3 and the corresponding traversal orders.
 */
public class Q5BstFromPreorder {

  /**
   * Initial analysis.
   * - In order: Left, root, right <---
   * - Pre order: Root, left, right
   * - Post order: Left, right, root
   * Given a list of nodes for in Order with unique keys, build a BST
   * On the BST, the left node need to be always bigger than the right node
   * BstNode class does not have a pointer to its parent node
   * Here the key point are:
   * - How to know if the next node is a left subtree, a right subtree, or it should return to the parent?
   * - How to return to the father? -> Using recursive
   * ---------
   *  [1, 2, 3]              2
   *                       1   3
   *  -------------------------------
   *  Binary search tree
   *                         5
   *                     3      8
   *                  2   4    7  9
   *
   * Preorder array  [5, 3, 2, 4, 8, 7, 9] -> Root, left, right -> The root is always the first element
   * Inorder array   [2, 3, 4, 5, 7, 8, 9] -> Left, root, right <- question:
   *   - Starting with the leftiest one, then root, then right
   * PostOrder array [2, 4, 3, 7, 9, 8, 5] -> left, right, root
   *
   *
   * If recursive, how to update the list?
   *      -> A pointer, but the father need to get the elements as well
   * Better solution: Going through the list, then use stack or queue to replace recursive
   * -> Stack -> Pull, push , peek, isEmpty()
   * -> Java stack = Dequeue ->
   * @param preorderSequence
   * @return
   */
  @EpiTest(testDataFile = "bst_from_preorder.tsv")
  public static BstNode<Integer> rebuildBSTFromPreorder(List<Integer> preorderSequence) {
    // 1. Going through the list
    Deque<BstNode<Integer>> stack = new ArrayDeque<>();

    // TODO


    return null;
  }

  /**
   * Book solution 1
   * In preorder, the first element of the list must be the root
   * Worse case scenario
   * Left-skewed tree
   * - W(n) = W(n - 1) + O(n)
   * ---> O(n^2)
   * Best case scenario
   * Right-skewed tree
   * ---> O(n)
   * BST -> B(n) = 2*B(n/2) + O(n) = O(n log n)
   */
  public static BstNode<Integer> sol1RebuildBSTFromPreorderHelper(List<Integer> preorderSequence) {
    // List. Start and the end
    return rebuildBSTFromPreorderHelper(preorderSequence, 0, preorderSequence.size());
  }

  public static BstNode<Integer> rebuildBSTFromPreorderHelper(List<Integer> preorderSequence, int start, int end) {
    // If we reached to the end, stop
    if (start >= end) {
      return null;
    }

    // the next point is the starting point
    // Find the transition point from which the element is bigger than the start point
    // the list must be sorted -> For preorder on binary search tree, it is
    // From this point, the left side is the left subtree, the right side is the right subtree
    int transitionPoint = start + 1;
    while (transitionPoint < end && preorderSequence.get(transitionPoint) < preorderSequence.get(start)) {
      ++ transitionPoint;
    }

    return new BstNode<> (
      preorderSequence.get(start),
      rebuildBSTFromPreorderHelper(preorderSequence, start + 1, transitionPoint),
      rebuildBSTFromPreorderHelper(preorderSequence, transitionPoint, end));
  }

  /**
   * Second solution from the book
   * We do not want to iterate from first entry after the root to the last entry smaller than the root,
   * only to go back and partially repeat this process for the root's left subtree.
   *
   * The worst-case time complexity is O(n), since it performs a constant amount of work per node.
   * @param preorderSequence
   * @return
   */
  // Global variable, tracks current subtree
  private static Integer rootIdx;
  public static BstNode<Integer> bookSol2rebuildBSTFromPreorder(List<Integer> preorderSequence) {
    rootIdx = 0;
    return rebuildBSFFromPreorderOnValueRange(preorderSequence, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  // Builds a BST on the subtree rooted at rootIdx from preorderSequence on keys
  // in (lowerBound, upperBound)
  private static BstNode<Integer> rebuildBSFFromPreorderOnValueRange(
          List<Integer> preorderSequence, Integer lowerBound, Integer upperBound) {
    if (rootIdx == preorderSequence.size()) {
      return null;
    }

    Integer root = preorderSequence.get(rootIdx);
    if (root < lowerBound || root > upperBound) {
      return null;
    }
    ++rootIdx;
    // Note that rebuildBSFromPreorderOnValueRange updates rootIdx. So the order
    // of following two calls are critical.
    BstNode<Integer> leftSubtree =
            rebuildBSFFromPreorderOnValueRange(preorderSequence, lowerBound, root);
    BstNode<Integer> rightSubtree =
            rebuildBSFFromPreorderOnValueRange(preorderSequence, root, upperBound);
    return new BstNode<>(root, leftSubtree, rightSubtree);
  }



  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BstFromPreorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
