package epi.chapter14.binarysearchtrees;
import epi.BstNode;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestUtils;
import epi.test_framework.TimedExecutor;

import java.util.List;

/**
 * Given a sorted array, the number of BSTs that can be build on the entries in the array grows
 * enormously with its size. Some of these trees are skewed, and are closer to lists; other are more
 * balanced. See Figure 14.3 on Page 229 for an example.
 *
 * How would you build a BST of minimum possible height from a sorted array?
 *
 * Hint: Which element should be root. => the one in the middle
 */
public class Q8BstFromSortedArray {

  public static BstNode<Integer>
  buildMinHeightBSTFromSortedArray(List<Integer> A) {
    // TODO - you fill in here.
    return null;
  }
  @EpiTest(testDataFile = "bst_from_sorted_array.tsv")
  public static int
  buildMinHeightBSTFromSortedArrayWrapper(TimedExecutor executor,
                                          List<Integer> A) throws Exception {
    BstNode<Integer> result =
        executor.run(() -> buildMinHeightBSTFromSortedArray(A));

    List<Integer> inorder = BinaryTreeUtils.generateInorder(result);

    TestUtils.assertAllValuesPresent(A, inorder);
    BinaryTreeUtils.assertTreeIsBst(result);
    return BinaryTreeUtils.binaryTreeHeight(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BstFromSortedArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
