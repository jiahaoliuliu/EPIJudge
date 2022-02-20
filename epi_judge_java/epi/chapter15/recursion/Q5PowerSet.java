package epi.chapter15.recursion;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;
import epi.test_framework.LexicographicalListComparator;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The power set of a set S is the set of all subsets of S, including both the empty set Empty and S
 * itself. The power set of {0, 1, 2} is graphically illustrated in Figure 15.6
 * -> The power set of {0, 1, 2} is {Empty, {0}, {1}, {2}, {0, 1}, {1, 2}, {0, 2}, {0, 1, 2}}
 *
 * Write a function that takes as input a set and returns its power set.
 *
 * Hint: There are 2^n subsets for a given set S of size n. There are 2^k k-bit words.
 *
 * Variant: Solve this problem when the input array may have duplicates. i.e. denotes a multi set.
 * You should not repeat any multiset. For example, if A = <1, 2, 3, 2>, then you should return
 * <<>, <1>, <2>, <3>, <1, 2>, <1, 3>, <2, 2>, <2,3>, <1, 2, 2>, <1, 2, 3>, <2, 2, 3>, <1, 2, 2, 3>>
 */
public class Q5PowerSet {
  @EpiTest(testDataFile = "power_set.tsv")

  public static List<List<Integer>> generatePowerSet(List<Integer> inputSet) {

    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());
    return generatePowerSet(inputSet, result);
  }

  /**
   * when to stop?
   * how to reduce item?
   * Or it is better adding items?
   * from {0, 1, 2} -> Generate {{0, 1}, {0, 2}, {1, 2}}
   * Time complexity: O(n!), where n is the size of the input set
   * space complexity: O (2^n), where n is the size of the input set
   * @param inputSet
   * @param result
   * @return
   */
  public static List<List<Integer>> generatePowerSet(List<Integer> inputSet, List<List<Integer>> result) {
    if (inputSet.isEmpty()) {
      return result;
    }

    // If itself is not in the result, add itself
    if (!result.contains(inputSet)) {
      result.add(inputSet);
    } else {
      return result;
    }
    for (int i = 0; i < inputSet.size(); i++) {
      List<Integer> currentList = new ArrayList<>();
      for (int j = 0; j < inputSet.size(); j++) {
        if (j != i) currentList.add(inputSet.get(j));
      }

      generatePowerSet(currentList, result);
    }
    return result;
  }

  @Test
  public void testGeneratePowerSet() {
    // Given
    List<Integer> input = List.of(0, 1, 2);

    // When
    List<List<Integer>> result = generatePowerSet(input);

    // Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertTrue(result.contains(Collections.emptyList()));
    assertTrue(result.contains(List.of(0)));
    assertTrue(result.contains(List.of(1)));
    assertTrue(result.contains(List.of(2)));
    assertTrue(result.contains(List.of(0, 1)));
    assertTrue(result.contains(List.of(0, 2)));
    assertTrue(result.contains(List.of(1, 2)));
    assertTrue(result.contains(List.of(0, 1, 2)));
    assertEquals(8, result.size());
  }

  /**
   * Book solution 1
   * Brute force
   * Time complexity: O(n2^n) Because each set takes 2^n time to compute, and there is n sets
   * Space complexity: O(n2^n)
   * @param inputSet
   * @return
   */
  public static List<List<Integer>> bookSol1GeneratePowerSet(List<Integer> inputSet) {
    List<List<Integer>> powerSet = new ArrayList<>();
    directedPowerSet(inputSet, 0, new ArrayList<>(), powerSet);
    return powerSet;
  }

  // Generate all subsets whose intersection with inputSet[0] ...
  // inputSet[toBeSelected - 1] is exactly selectedSoFar
  private static void directedPowerSet(List<Integer> inputSet, int toBeSelected,
                                       List<Integer> selectedSoFar,
                                       List<List<Integer>> powerSet) {
    if (toBeSelected == inputSet.size()) {
      powerSet.add(new ArrayList<>(selectedSoFar));
      return;
    }

    // Generate all subsets that contain inputSet[toBeSelected]
    selectedSoFar.add(inputSet.get(toBeSelected));
    directedPowerSet(inputSet, toBeSelected + 1, selectedSoFar, powerSet);

    // Generate al subsets that do not contain inputSet[toBeSelected]
    selectedSoFar.remove(selectedSoFar.size() - 1);
    directedPowerSet(inputSet, toBeSelected + 1, selectedSoFar, powerSet);
  }

  @Test
  public void testBookSol1GeneratePowerSet() {
    // Given
    List<Integer> input = List.of(0, 1, 2);

    // When
    List<List<Integer>> result = bookSol1GeneratePowerSet(input);

    // Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertTrue(result.contains(Collections.emptyList()));
    assertTrue(result.contains(List.of(0)));
    assertTrue(result.contains(List.of(1)));
    assertTrue(result.contains(List.of(2)));
    assertTrue(result.contains(List.of(0, 1)));
    assertTrue(result.contains(List.of(0, 2)));
    assertTrue(result.contains(List.of(1, 2)));
    assertTrue(result.contains(List.of(0, 1, 2)));
    assertEquals(8, result.size());
  }

  /**
   * Using an array of bits.
   * Time complexity: O(n2^n)
   * Space complexity: O(n)
   * @param inputSet
   * @return
   */
  public static List<List<Integer>> bookSol2GeneratePowerSet(List<Integer> inputSet) {
    List<List<Integer>> powerSet = new ArrayList<>();
    for (int intForSubset = 0; intForSubset < (1 << inputSet.size()); ++intForSubset) {
      int bitArray = intForSubset;
      List<Integer> subset = new ArrayList<>();
      while (bitArray != 0) {
        subset.add(inputSet.get(Integer.numberOfTrailingZeros(bitArray)));
        bitArray &= bitArray - 1;
      }
      powerSet.add(subset);
    }
    return powerSet;
  }

  @Test
  public void testBookSol2GeneratePowerSet() {
    // Given
    List<Integer> input = List.of(0, 1, 2);

    // When
    List<List<Integer>> result = bookSol2GeneratePowerSet(input);

    // Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertTrue(result.contains(Collections.emptyList()));
    assertTrue(result.contains(List.of(0)));
    assertTrue(result.contains(List.of(1)));
    assertTrue(result.contains(List.of(2)));
    assertTrue(result.contains(List.of(0, 1)));
    assertTrue(result.contains(List.of(0, 2)));
    assertTrue(result.contains(List.of(1, 2)));
    assertTrue(result.contains(List.of(0, 1, 2)));
    assertEquals(8, result.size());
  }

  @EpiTestComparator
  public static boolean comp(List<List<Integer>> expected,
                             List<List<Integer>> result) {
    if (result == null) {
      return false;
    }
    for (List<Integer> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<Integer> l : result) {
      Collections.sort(l);
    }
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PowerSet.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }


}
