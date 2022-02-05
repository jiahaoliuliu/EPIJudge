package epi.chapter10.heaps;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * You want to compute the running median of a sequence of numbers. The sequence is presented
 * to you in streaming fashion - you cannot back up to read an earlier value, and you need to
 * output the median after reading in each new element.
 * For example, if the input is 1, 0, 3, 5, 2, 0, 1 the output is 1, 0.5, 1, 2, 2, 1.5, 1
 *
 * Design an algorithm for computing the running median of a sequence.
 *
 * Hint: Avoid looking at all values each time you read a new value.
 */
public class Q10OnlineMedian {

  /**
   * Initial analysis
   *  The median is the middle number in a sorted, ascending or descending,
   *  - If there is an odd amount of numbers, the median value is the number that is in the middle
   *  - If there is an even amount of numbers in the list, the middle pair must be determined, added together,
   *  -- and divided by two to find the median value.
   *  As the number is added, the list is sorted and the median is calculated again.
   *  If the added number is not the one in the middle or one of the two in the middle, it does not matters
   *  Heap could be used to find
   *  - The maximum number of a list
   *  - The minimum number of a list
   *  Insertion into the heap could have the elements sorted, but the one exposed is not.
   *
   *  The basic operation to know the median is
   *  1. Insert
   *  2. Sort -> Use the heap to sort? What is the
   *  3. if even, get the value in the middle
   *     if odd, get 2 values in the middle and find the average of them
   *
   *  The important thing here is the values in the middle.
   *  - We can keep 2 heaps
   *  -- 1 will calculate the maximum of the first half
   *  -- 1 will calculate the minimum of the second half
   * Keep both heaps even
   * @param sequence
   * @return
   */
  public static List<Double> onlineMedian(Iterator<Integer> sequence) {
    List<Double> result = new ArrayList<Double>();

    PriorityQueue<Integer> firstHalfMaxHeap = new PriorityQueue<>(
            (s1, s2) -> Integer.compare(s2, s1)
    );
    PriorityQueue<Integer> secondHalfMinHeap = new PriorityQueue<>();
    double lastMedian = Double.NaN;

    // For each one of the elements in the list
    while (sequence.hasNext()) {
      Integer newValue = sequence.next();
      // If the list just started, the input number is median
      if (lastMedian == Double.NaN) {
        // This number could be inserted into any of the heaps
        secondHalfMinHeap.add(newValue);
        lastMedian = newValue;
      } else {
        if (newValue < lastMedian) {
          firstHalfMaxHeap.add(newValue);
          // Rebalance the heap
          if (firstHalfMaxHeap.size() - secondHalfMinHeap.size() > 1) {
            secondHalfMinHeap.add(firstHalfMaxHeap.poll());
          }
        } else {
          secondHalfMinHeap.add(newValue);
          // Rebalance the heap
          if (secondHalfMinHeap.size() - firstHalfMaxHeap.size() > 1) {
            firstHalfMaxHeap.add(secondHalfMinHeap.poll());
          }
        }

        // Calculate the median - The values should be balanced
        int totalNumbers = firstHalfMaxHeap.size() + secondHalfMinHeap.size();
        if (totalNumbers % 2 == 0) {
          lastMedian = (firstHalfMaxHeap.peek() + secondHalfMinHeap.peek())/2.0;
        } else {
          if ((totalNumbers + 1) / 2 == firstHalfMaxHeap.size()) {
            lastMedian = firstHalfMaxHeap.peek();
          } else {
            lastMedian = secondHalfMinHeap.peek();
          }
        }
      }
      result.add(lastMedian);
    }

    return result;
  }

  @EpiTest(testDataFile = "online_median.tsv")
  public static List<Double> onlineMedianWrapper(List<Integer> sequence) {
    return onlineMedian(sequence.iterator());
  }

  @Test
  public void testOnlineMedian() {
    // Given
    List<Integer> input = Arrays.asList(1, 0, 3, 5, 2, 0, 1);

    // When
    List<Double> result = onlineMedianWrapper(input);

    // Then 1, 0.5, 1, 2, 2, 1.5, 1
    List<Double> expected = Arrays.asList(1.0, 0.5, 1.0, 2.0, 2.0, 1.5, 1.0);
    assertEquals(expected, result);
  }

  /**
   * Book solution 1
   * Time complexity: O(log n), corresponding to insertion and extraction from a heap.
   * 
   * @param args
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public static List<Double> bookSol1OnlineMedian(Iterator<Integer> sequence) {
    // minHeap stores the larger half seen so far.
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    // maxHeap stores the smaller half seen so far.
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
            DEFAULT_INITIAL_CAPACITY, Collections.reverseOrder());
    List<Double> result = new ArrayList<>();

    while(sequence.hasNext()) {
      int x = sequence.next();
      minHeap.add(x);
      maxHeap.add(minHeap.remove());
      // Ensure minHeap and maxHeap have equal number of elements if
      // an even number of elements is read; otherwise, minHeap must have
      // one more element than maxHeap.
      if (maxHeap.size() > minHeap.size()) {
        minHeap.add(maxHeap.remove());
      }

      result.add(minHeap.size() == maxHeap.size() ?
              0.5 * (minHeap.peek() + maxHeap.peek())
              : (double)minHeap.peek());
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "OnlineMedian.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
