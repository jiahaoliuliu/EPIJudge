package epi.chapter12.hashtables;
import epi.BinaryTree;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

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
 * Extra: Problem 9.4
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
 */
public class Q4LowestCommonAncestorCloseAncestor {

  public static BinaryTree<Integer> lca(BinaryTree<Integer> node0,
                                        BinaryTree<Integer> node1) {
    // TODO - you fill in here.
    return null;
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
            .runFromAnnotations(args, "LowestCommonAncestorCloseAncestor.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
