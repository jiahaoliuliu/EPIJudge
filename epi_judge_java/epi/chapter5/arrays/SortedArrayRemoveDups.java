package epi.chapter5.arrays;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This problem is concerned with deleting repeated elements from a sorted array. For example,
 * for the array (2, 3, 5, 5, 7, 11, 11, 11, 13), then after deletion, the array is
 * (2, 3, 5, 7, 11, 11, 11, 13). After deleting repeated elements, there are 6 valid entries
 * (i.e. (2, 3, 5, 7, 11, 13)). There are no requirements as to the values stored beyond the last valid
 * element.
 *
 * Write a program which takes as input a sorted array and updates it so that all duplicated have been
 * removed and the remaining elements have been shifted left to fill the emptied indies. Return the
 * number of valid elements. May language have library functions for performing this operation.
 * You cannot use these functions
 *
 * Hint: There are an O(n) time and O(1) space solution.
 */
public class SortedArrayRemoveDups {

  // Returns the number of valid entries after deletion.
  // Cases
  // (2, 3, 5, 7, 11, 11, 11, 13) -> (2, 3, 5, 7, 11, 13, 11, 11)
  // (2, 3, 5, 5, 7, 11, 11, 13) -> (2, 3, 5, 7, 11, 13, 5, 11)
  //  Once found 5, what should it do:
  //    - Swap with 7? (2, 3, 5, 7, 5, 11, 11, 13) ?
  //      It is a sorted array, All the contents tht comes from behind are bigger
  //      It cannot be placed in a valid array place -> Move it to invalid array
  //        -- Delete
  //        -- Move it to back bit by bit?
  //    - Comparison
  //      - Compare with the next candidate?
  //      - Compare with the previous candidate? --> Better. We always need to know the last known value
  // (2, 3, 5, 5, 7, 11, 11, 13)
  // (2, 3, 5, 7, 5, 11, 11, 13)
  // (2, 3, 5, 7, 11, 5, 11, 13)
  // (2, 3, 5, 7, 11, 13, 11, 5)

  public static int deleteDuplicates(List<Integer> array) {
    // Corner case
    if (array.isEmpty()) return 0;
    if (array.size() == 1) return 1;

    for (int currentPosition = 1; currentPosition < array.size(); currentPosition++) {
      int currentItem = array.get(currentPosition);
      // If we found the right case, continue
      if (currentItem > array.get(currentPosition -1)) {
        continue;
      }

      // The current number is equal or smaller than the previous number, time to swap
      // Find the next available candidate
      // - A number that is bigger than the current one
      // - A number that is bigger than previous one
      int nextCandidatePosition = currentPosition + 1;
      while (nextCandidatePosition < array.size() && array.get(nextCandidatePosition) <= array.get(currentPosition - 1)) {
        nextCandidatePosition++;
      }

      if (nextCandidatePosition == array.size()) {
        return currentPosition;
      } else {
        // We found a suitable candidate to swap
        Collections.swap(array, nextCandidatePosition, currentPosition);
      }
    }

    return array.size();
  }
  @EpiTest(testDataFile = "sorted_array_remove_dups.tsv")
  public static List<Integer> deleteDuplicatesWrapper(TimedExecutor executor,
                                                      List<Integer> A)
      throws Exception {
    int end = executor.run(() -> deleteDuplicates(A));
    return A.subList(0, end);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedArrayRemoveDups.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }

  @Test
  public void testDeleteDuplicates1() {
    // Given
    List<Integer> array = new ArrayList<>();

    // When
    int numberOfValidElements = deleteDuplicates(array);

    // Then
    assertEquals(0, numberOfValidElements);
  }

  @Test
  public void testDeleteDuplicates2() {
    // Given
    List<Integer> array = new ArrayList<>();
    array.add(1);

    // When
    int numberOfValidElements = deleteDuplicates(array);

    // Then
    List<Integer> expectedResult = new ArrayList<>();
    expectedResult.add(1);

    assertEquals(1, numberOfValidElements);
    assertTrue(expectedResult.equals(array));
  }

  @Test
  public void testDeleteDuplicates3() {
    // Given
    List<Integer> array = new ArrayList<>();
    array.add(2);
    array.add(3);
    array.add(5);
    array.add(5);
    array.add(7);
    array.add(11);
    array.add(11);
    array.add(13);

    // When
    int numberOfValidElements = deleteDuplicates(array);

    // Then
    List<Integer> expectedResult = new ArrayList<>();
    expectedResult.add(2);
    expectedResult.add(3);
    expectedResult.add(5);
    expectedResult.add(7);
    expectedResult.add(11);
    expectedResult.add(13);
    expectedResult.add(11);
    expectedResult.add(5);
    assertEquals(6, numberOfValidElements);
    assertTrue(expectedResult.equals(array));
  }

  /**
   * Book solution. Set a index from where to write, which only updates if needed,
   * and move the index to find the next suitable candidate.
   * Since the array is sorted, one single loop is enough
   */
  public static int bookSol1DeleteDuplicates(List<Integer> A) {
    if (A.isEmpty()) {
      return 0;
    }

    // Write index is the position to write. Since we don't care about the written item, we use set instead
    // of swap
    int writeIndex = 1;
    for (int i = 1; i < A.size(); ++i) {
      // find all the elements that is not equal, starting from the position 1
      if (!A.get(writeIndex - 1).equals(A.get(i))) {
        A.set(writeIndex++, A.get(i));
      }
    }
    return writeIndex;
  }

  @Test
  public void testBookSol1DeleteDuplicates1() {
    // Given
    List<Integer> array = new ArrayList<>();
    array.add(2);
    array.add(3);
    array.add(5);
    array.add(5);
    array.add(7);
    array.add(11);
    array.add(11);
    array.add(13);

    // When
    int numberOfValidElements = bookSol1DeleteDuplicates(array);

    // Then
    List<Integer> expectedResult = new ArrayList<>();
    expectedResult.add(2);
    expectedResult.add(3);
    expectedResult.add(5);
    expectedResult.add(7);
    expectedResult.add(11);
    expectedResult.add(13);
    expectedResult.add(11);
    expectedResult.add(5);
    assertEquals(6, numberOfValidElements);
    assertTrue(expectedResult.equals(array));
  }
}
