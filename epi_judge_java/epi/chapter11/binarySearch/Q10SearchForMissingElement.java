package epi.chapter11.binarySearch;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * If an array contains n-1 integers, each between 0 and n-1, inclusive, and all numbers in the array
 * are distinct, then it must be the case that exactly one number between 0 and -1 is absent.
 *
 * We can determine the missing number in O(n) time and O(1) space by computing the sum of the elements
 * in the array. Since the sum of all the numbers from 0 to n-1, inclusive is (n-1)*n/2, we can subtract
 * the sum of the numbers in the array from (n-1)*n/2 to get the missing number.
 *
 * For example, if the array is (5, 3, 0, 1, 2), the n = 6. We substract (5 + 3 + 0 + 1 + 2) = 11 from
 * 5 * 6/2 = 15, and the result, 4 is the missing number.
 *
 * Similarly, if the array contains n + 1  integers, each between 0 and n-1, inclusive, with exactly
 * one element appearing twice, the duplicate integer will be equal to the sum of the elements of the
 * array minus (n-1)n/2.
 *
 * Alternatively, for the first problem, we can compute the missing number by computing the XOR of all
 * the integers from 0 to n - 1, inclusive, and XORing that with XOR of all the element sin the array.
 * Every element in the array, except for the missing element, cancels out with an integer from the first set.
 * Therefore, the resulting XOR equals the missing element. The same approach works in binary is
 * <(101), (011), (000), (001), (010)>. The XOR of these entries is (101). The XOR of all numbers from
 * 0 to 5, inclusive, is (001). The XOR of (101) and (001) is (100) = 4, which is the missing number.
 *
 * We now turn to a related, though harder problem.
 *
 * We are given an array of n integers, each between 0 and n - 1, inclusive. Exactly one element appears
 * twice, implying that exactly one number between 0 and n-1 is missing from the array. How would you
 * compute the duplicate and missing numbers?
 *
 * Hint: Consider performing multiple passes through the array.
 */
public class Q10SearchForMissingElement {
  @EpiUserType(ctorParams = {Integer.class, Integer.class})

  public static class DuplicateAndMissing {
    public Integer duplicate;
    public Integer missing;

    public DuplicateAndMissing(Integer duplicate, Integer missing) {
      this.duplicate = duplicate;
      this.missing = missing;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      DuplicateAndMissing that = (DuplicateAndMissing)o;

      if (!duplicate.equals(that.duplicate)) {
        return false;
      }
      return missing.equals(that.missing);
    }

    @Override
    public int hashCode() {
      int result = duplicate.hashCode();
      result = 31 * result + missing.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return "duplicate: " + duplicate + ", missing: " + missing;
    }
  }

  /**
   * Initial analysis. (Summary)
   * Given an array between 0 and n-1, both inclusive
   * - If one is missing
   * -- Use sum. 0 + 1 + ... + n-1 = (n-1)*n /2
   * -- (n-1)*n/2 - sum array = missing element
   * -- Alternative: Use XOR - XOR all the numbers from 0 to n-1. Then XOR the sum
   *
   * - If one is duplicated
   * -- Use sum. 0 + 1 + ... + n-1 = (n-1)*n/2
   * -- sum array - (n-1)*n/2 = duplicated
   *
   * Given an array with one element duplicated and one element missing, find the duplicated and the missing
   * element.
   *
   * Deductions:
   * What is the relationship between the duplicated one and the missing one?
   *  duplicated != missing one
   *  - if (duplicated > missing) => sum(array) > (n-1)*n/2
   *  - if (duplicated < missing) => sum(array) < (n-1)*n/2
   *
   *  Given the array <0, 1, 3, 2, 3, 5>
   *      - sum(array) = 14
   *      - 5 * 6 /2 = 15
   *      - 14 < 15 => The missing # is bigger than the duplicated number
   *      - 15 - 14 = 1 => The missing number - duplicated number = 1
   *  The list is not sorted
   *  We can use hashmap, but this unit is about search...
   *  Searching for the missing element?
   *
   * Brute force -> using an array of booleans
   * Time complexity: O(n), where n is the size of the array
   * Space complexity: O(n), where n is the size of the array
   *
   * @param A
   * @return
   */
  @EpiTest(testDataFile = "find_missing_and_duplicate.tsv")
  public static DuplicateAndMissing findDuplicateMissing(List<Integer> A) {
    boolean[] occurrences = new boolean[A.size()];
    Integer duplicated = null;
    for (Integer number: A) {
      if (occurrences[number]) {
        duplicated = number;
        break;
      } else {
        occurrences[number] = true;
      }
    }

    if (duplicated == null) {
      // Not duplicated found, so no missing found
      return new DuplicateAndMissing(-1, -1);
    }

    int sum = duplicated;
    for (Integer number: A) {
      if (!number.equals(duplicated)) {
        sum += number;
      }
    }

    // Calculate (n-1) * n / 2
    int properSum = A.size() * (A.size()-1) /2;

    return new DuplicateAndMissing(duplicated, properSum - sum);
  }

  /**
   * Book solution 1 - Based on XOR instead
   * @param args
   */
  public static DuplicateAndMissing bookSol1FindDuplicateMissing(List<Integer> A) {
    // Compute the XOR of all numbers from 0 to |A| - 1 and all entries in A.
    int missXorDup = 0;
    for (int i = 0; i < A.size(); ++i) {
      missXorDup ^= i ^ A.get(i);
    }

    // We need to find a bit that's set to 1 in missXorDup. Such a bit
    // must exist if there is a single missing number and a single duplicated number in A

    // The bit-fiddling assignment below sets all bits in differentBit to 0
    // except for the least significant bit in missXorDup that's 1
    int differBit = missXorDup & (~(missXorDup - 1));
    int missOrDup = 0;
    for (int i = 0; i < A.size(); ++i) {
      // Focus on entries and numbers in which the differBit-th bit is 1
      if ((i & differBit) != 0) {
        missOrDup ^= i;
      }
      if ((A.get(i) & differBit) != 0) {
        missOrDup ^= A.get(i);
      }
    }

    // missOrDup is either the missing value or the duplicated entry. If
    // missOrDup is in A, missOrDup is the duplicate; otherwise, missOrDup is
    // the missing value
    return A.contains(missOrDup)
            ? new DuplicateAndMissing(missOrDup, missOrDup ^ missXorDup)
            : new DuplicateAndMissing(missOrDup ^ missXorDup, missOrDup);
  }

  @Test
  public void testBookSol1FindDuplicateMissing() {
    // Given
    List<Integer> input = Arrays.asList(5, 3, 0, 3, 1, 2);

    // When
    DuplicateAndMissing result = bookSol1FindDuplicateMissing(input);

    // Then
    DuplicateAndMissing expected = new DuplicateAndMissing(3, 4);
    assertEquals(expected, result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchForMissingElement.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
