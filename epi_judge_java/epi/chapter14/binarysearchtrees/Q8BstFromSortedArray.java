package epi.chapter14.binarysearchtrees;
import epi.BstNode;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestUtils;
import epi.test_framework.TimedExecutor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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

  /**
   * Initial analysis
   *
   * The BST which has less height is the one which has both children. Full balanced,
   * perfect and complete
   *
   * @param array
   * @return
   */
  public static BstNode<Integer> buildMinHeightBSTFromSortedArray(List<Integer> array) {
    return buildMinHeightBSTFromSortedArrayHelper(array, 0, array.size() - 1);
  }

  public static BstNode<Integer> buildMinHeightBSTFromSortedArrayHelper(List<Integer> array, int start, int finish) {
    if (start > finish) {
      return null;
    }

    int middle = start + (finish - start)/2;
    return new BstNode<>(array.get(middle),
            buildMinHeightBSTFromSortedArrayHelper(array, start, middle -1),
            buildMinHeightBSTFromSortedArrayHelper(array, middle+1, finish));
  }

  public static BstNode<Integer> bookSol1BuildMinHeightBSTFromSortedArray(List<Integer> A) {
    return buildMinHeightBSTFromSortedSubarray(A, 0, A.size());
  }

  // Build a min-height BST over the entries in A.subList(start, end - 1)
  private static BstNode<Integer> buildMinHeightBSTFromSortedSubarray(List<Integer> A, int start, int end) {
    if (start >= end) {
      return null;
    }

    int mid = start + ((end - start) / 2);
    return new BstNode<>(A.get(mid),
            buildMinHeightBSTFromSortedSubarray(A, start, mid),
            buildMinHeightBSTFromSortedSubarray(A, mid+1, end));
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

  @Test
  public void test1() {
    // Given
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

    // When
    BstNode<Integer> root = buildMinHeightBSTFromSortedArray(list);

    // Then
    assertNotNull(root);
    assertEquals(4, (int)root.getData());
    //  Left subtree
    assertNotNull(root.getLeft());
    assertEquals(2, (int)root.getLeft().getData());
    assertNotNull(root.getLeft().getLeft());
    assertEquals(1, (int)root.getLeft().getLeft().getData());
    assertNull(root.getLeft().getLeft().getLeft());
    assertNull(root.getLeft().getLeft().getRight());
    assertNotNull(root.getLeft().getRight());
    assertEquals(3, (int)root.getLeft().getRight().getData());

    // right subtree
    assertNotNull(root.getRight());
    assertEquals(6, (int)root.getRight().getData());
    assertNotNull(root.getRight().getLeft());
    assertEquals(5, (int)root.getRight().getLeft().getData());
    assertNull(root.getRight().getLeft().getLeft());
    assertNull(root.getRight().getLeft().getRight());
    assertNotNull(root.getRight().getRight());
    assertEquals(7, (int)root.getRight().getRight().getData());
    assertNull(root.getRight().getRight().getLeft());
    assertNull(root.getRight().getRight().getRight());
  }
}
