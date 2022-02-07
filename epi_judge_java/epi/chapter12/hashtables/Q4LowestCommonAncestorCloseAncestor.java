package epi.chapter12.hashtables;
import epi.BinaryTree;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * The problem 9.4 on Page 130 is concerned with computing the LCA in a binary tree with parent pointers
 * in time proportional to the height of the tree. The algorithm presented in Solution 9.4 on page 130
 * entails traversing all the way to the root even if the nodes whose LCA is being computed are very close
 * to their LCA.
 *
 * Design an algorithm for computing the LCA of two nodes in a binary tree. The algorithm's time complexity
 * should depend only on the distance from the nodes to the LCA
 *
 * Hint: Focus on the extreme case described in the problem introduction.
 *
 * Extra: Problem 9.3
 * Any two nodes in a binary tree have a common ancestor, namely the root. The lowest common ancestor (LCA)
 * of any two nodes in a binary tree is the node furthest from the root that is an ancestor of both nodes.
 * For example, the LCA of M and N in Figure 9.1 on Page 123 is K.
 *
 * Computing the LCA has important applications. For example, it is an essential calculation when rendering web
 * pages, specifically when computing the Cascading Style Sheet (CSS) that is applicable to a particular
 * Document Object Model (DOM) element.
 *
 * Design an algorithm for computing the LCA of two nodes in a binary tree in which does not have a parent field
 *
 * Hint: When is the root the LCA?
 *
 * Extra: Problem 9.4
 * Given two nodes in a binary tree, design an algorithm that computes their LCA. Assume that each node has a
 * parent pointer.
 *
 * Hint: The problem is easy if both nodes are the same distance from the root
 */
public class Q4LowestCommonAncestorCloseAncestor {

  /**
   * Initial analysis. We just have to scale up, add the nodes into two hash tables (does it need to be hash tables)
   * and check it periodically, it should be ok.
   *
   * Complexity: O(n), where n is the deepest element between node0 and node1, + 1
   * 
   * @param node0
   * @param node1
   * @return
   */
  public static BinaryTree<Integer> lca(BinaryTree<Integer> node0,
                                        BinaryTree<Integer> node1) {
    // Corner case
    if (node0 == node1) return node0;
    if (node0.left == node1 || node0.right == node1) return node0;
    if (node1.left == node0 || node1.right == node0) return node1;

    Map<BinaryTree<Integer>, BinaryTree<Integer>> node0Parents = new HashMap<>();
    Map<BinaryTree<Integer>, BinaryTree<Integer>> node1Parents = new HashMap<>();
    node0Parents.put(node0, node0.left);
    node1Parents.put(node1, node1.left);

    // While none of the nodes has reached to the root
    while (node0.parent != null || node1.parent != null) {
      if (node0.parent != null) {
        if (node1Parents.containsKey(node0.parent)) {
          return node0.parent;
        }

        node0Parents.put(node0.parent, node0);
        node0 = node0.parent;
      }

      if (node1.parent != null) {
        if (node0Parents.containsKey(node1.parent)) {
          return node1.parent;
        }

        node1Parents.put(node1.parent, node1);
        node1 = node1.parent;
      }
    }

    // Both of them have reached to the root
    return node0;
  }

  @Test
  public void testLca() {
    // Given
    BinaryTree<Integer> root = new BinaryTree<>(1);
    BinaryTree<Integer> node0 = new BinaryTree<>(2);
    root.left = node0;
    node0.parent = root;

    BinaryTree<Integer> node1 = new BinaryTree<>(3);
    root.right = node1;
    node1.parent = root;

    // When
    BinaryTree<Integer> result = lca(node0, node1);

    // Then
    assertEquals(root, result);
  }

  @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TimedExecutor executor, BinaryTree<Integer> tree,
                               Integer key0, Integer key1) throws Exception {
    BinaryTree<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BinaryTree<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BinaryTree<Integer> result = executor.run(() -> lca(node0, node1));

    if (result == null) {
      throw new TestFailure("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Q4LowestCommonAncestorCloseAncestor.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
