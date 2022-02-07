package epi.chapter12.hashtables;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>When you type keywords in a search engine, the search engine will return results, and each result
 * contains a digest of the web page, ie. a highlighting within that page of the keywords that you
 * searched for. For example, a search for the keywords "Union" and "save" on a page with the text
 * of the emancipation Proclamation should return the result shown in Figure 12.1
 * </p>
 * <p>
 * Figure 12.1
 * "My paramount object in this struggle is to <b><u>save</u></b> the <b><u>Union</u></b>, and is not
 * either to save or to destroy slavery. If I could save the Union without freeing any slave I would do it,
 * and if I could save it by freeing all th slaves I would do it; and if I could save it by freeing some and
 * leaving others alone I would also do that."
 * </p>
 * <p>
 * The digest for this page is the text in boldface, with the keywords underlined for emphasis. It is the
 * shortest substring of the page which contains all the keywords in the search. the problem of computing
 * the digest is abstracted as follows.
 * </p> <p>
 * Write a program which takes an array of strings and a set of strings, and return the indices of
 * the starting and ending index of a shortest subarray of the given array that "covers" the set.
 * i.e., contains all strings in the set.
 * </p><p>
 * Hint: What is the maximum number of minimal subarrays that can cover the query?
 * </p>
 */
public class Q6SmallestSubarrayCoveringSet {

  // Represent subarray by starting and ending indices, inclusive.
  private static class Subarray {
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Subarray)) return false;
      Subarray subarray = (Subarray) o;
      return start.equals(subarray.start) && end.equals(subarray.end);
    }

    @Override
    public int hashCode() {
      return Objects.hash(start, end);
    }

    @Override
    public String toString() {
      return "Subarray{" +
              "start=" + start +
              ", end=" + end +
              '}';
    }
  }

  /**
   * Initial analysis. We have two collections: a list of words and a set of keywords.
   * The preconditions is that those words must contain all the keywords and they
   * contain something else. Here the key is they might contain something else.
   * So the keywords could appear in any order inside the list of words.
   * The returning subarray contains not only the list of keywords, but also some other
   * words that are not inside of the set. See "Save the Union". Here the keywords are
   * save and union, but "the" is not part of the keywords. Since subarray only points
   * from a specific position of the array to another specific position, "the" is also
   * included in the subarray.
   *
   * Now, we can create a hash table based on the list of words, where the key is the
   * word itself (for searching for keywords later), and the value the list of index
   * that it appears on the list (index because it could be used for subarray, and list
   * because the word might appear more than once).
   *
   * Once this list is ready, we can start with a subarray from position 0 to the size
   * of the list -1, then reducing it. -> Not applicable because the first element will
   * invalidate this and convert the subarray into the one which only contains itself.
   * We should initialize the subarray with the position of the first keyword, and expand
   * from there
   *
   * If the first keyword has more than one occurrence in the paragraph, which one to choose?
   *
   * What about first to filter the paragraph hashmap to a hashmap that contains all the keywords,
   * then build the possible combinations from there?
   *
   * Building the subarray
   * - looping through the dictionary of keywords
   * - Per each one of the elements,
   * Time complexity = O(n*m*k), where n is the number of keywords, m is the number of words in the paragraph, and
   *    k is the number of single possibilities of subarray
   * @param paragraph
   * @param keywords
   * @return
   */
  public static Subarray findSmallestSubarrayCoveringSet(List<String> paragraph,
                                                         Set<String> keywords) {
    Map<String, List<Integer>> wordsDictionary = new HashMap<>();
    // Building the hashmap
    for (int i = 0; i < paragraph.size(); i++) {
      String word = paragraph.get(i);
      wordsDictionary.putIfAbsent(word, new ArrayList<>());
      wordsDictionary.get(word).add(i);
    }

    // Checking the contents
    Map<String, List<Integer>> keywordsDictionary = new HashMap<>();
    for (String keyword: keywords) {
      // If a specific keyword is not in the dictionary, there is no way to cover
      // the keywords
      if (!wordsDictionary.containsKey(keyword)) {
        return new Subarray(0, 0);
      }
      keywordsDictionary.put(keyword, wordsDictionary.get(keyword));
    }

    // Build the subarray.
    Set<Subarray> results = new HashSet<>();
    Set<Subarray> tmpResult = new HashSet<>();
    for (String keyword: keywordsDictionary.keySet()) {
      tmpResult.clear();
      for (Integer position: keywordsDictionary.get(keyword)) {
        if (results.isEmpty()) {
          // If it is the first subarray, initialize it
          Subarray newSubArray = new Subarray(position, position);
          tmpResult.add(newSubArray);
        } else {
          for (Subarray subarray: results) {
            // If the subarray just been initialized once, initialize it again
            if (subarray.start == subarray.end) {
              int newEnd = Math.max(subarray.start, position);
              int newStart = Math.min(subarray.start, position);
              Subarray newSubarray = new Subarray(newStart, newEnd);
              tmpResult.add(newSubarray);
            } else {
              int newStart = Math.min(subarray.start, position);
              int newEnd = Math.max(subarray.end, position);
              Subarray newSubarray = new Subarray(newStart, newEnd);
              tmpResult.add(newSubarray);
            }
          }
        }
      }
      results.clear();
      results.addAll(tmpResult);
    }

    // Find the minimum result
    int minimum = Integer.MAX_VALUE;
    Subarray finalResult = new Subarray(Integer.MIN_VALUE, Integer.MAX_VALUE);
    for (Subarray subarray: results) {
      int distance = subarray.end - subarray.start;
      if (distance < minimum) {
        minimum = distance;
        finalResult = subarray;
      }
    }

    return finalResult;
  }

  @Test
  public void testFindSmallestSubarrayCoveringSet1() {
    // Given
    List<String> words = Arrays.asList("g", "V", "z", "K", "t", "K", "J", "H", "d", "b", "C", "K", "O", "q", "m", "o");
    Set<String> keywords = Set.copyOf(Arrays.asList("t", "q", "C", "b", "H", "J"));

    // When
    Subarray result = findSmallestSubarrayCoveringSet(words, keywords);

    // Then
    Subarray expected = new Subarray(4, 13);
    assertEquals(expected, result);
  }

  @Test
  public void testFindSmallestSubarrayCoveringSet2() {
    // Given
    List<String> words = Arrays.asList("L", "n", "S", "Q", "w", "K", "d", "J", "Y", "M", "j", "y", "n", "j", "N", "J", "h", "g", "N", "C", "D");
    Set<String> keywords = Set.copyOf(Arrays.asList("g", "K", "n", "C", "N", "D", "Q", "d"));

    // When
    Subarray result = findSmallestSubarrayCoveringSet(words, keywords);

    // Then
    Subarray expected = new Subarray(3, 20);
    assertEquals(expected, result);
  }

  @Test
  public void testFindSmallestSubarrayCoveringSet3() {
    // Given
    List<String> words = Arrays.asList("i", "j", "H", "r", "R", "X", "Z", "s", "Z", "F", "e", "m", "g", "G", "R", "f", "t", "f", "S", "p", "f", "D", "O", "T", "c", "b", "O", "v", "S", "V", "r", "M", "T", "t", "W", "y", "D", "N", "o", "w", "F", "x", "t", "P", "E", "e", "H", "Y", "X", "a", "q", "W", "a", "F", "J", "U", "c", "e", "w", "g", "A", "K", "d", "o", "J", "v", "u", "A", "e", "o", "S", "K", "U", "i", "Z", "a", "n", "W", "u", "f", "f", "Q", "o", "o", "m", "e", "v", "i", "y", "C", "s", "M", "t", "k", "i", "w", "n", "z", "L", "l", "c", "c", "M", "s", "a", "D", "p", "A", "T", "b", "R", "o", "w", "E", "v", "W", "W", "p", "G", "z", "T", "e", "K", "E", "c", "J", "K", "o", "a", "Z", "X", "D", "x", "K", "p", "Z", "x", "I", "X", "C", "g", "a", "T", "d", "A");
    Set<String> keywords = Set.copyOf(Arrays.asList("g", "v", "Y", "y", "k", "E", "S", "U", "j", "x", "F", "H", "T", "o", "M", "z", "J", "e", "m", "f", "s"));

    // When
    Subarray result = findSmallestSubarrayCoveringSet(words, keywords);

    // Then
    Subarray expected = new Subarray(1, 97);
    assertEquals(expected, result);
  }

  @Test
  public void testFindSmallestSubarrayCoveringSet4() {
    // Given
    List<String> words = Arrays.asList("R", "L", "G");
    Set<String> keywords = Set.copyOf(List.of("G"));

    // When
    Subarray result = findSmallestSubarrayCoveringSet(words, keywords);

    // Then
    Subarray expected = new Subarray(2, 2);
    assertEquals(expected, result);
  }

  /**
   * O(n)
   * @param paragraph
   * @param keywords
   * @return
   */
  public static Subarray bookSol1FindSmallestSubarrayCoveringSet(List<String> paragraph,
                                                                 Set<String> keywords) {
    Map<String, Long> keywordsToCover = keywords.stream().collect(
            Collectors.groupingBy(Function.identity(), Collectors.counting()));

    Subarray result = new Subarray(-1, -1);
    int remainingToCover = keywords.size();
    for (int left = 0, right = 0; right  < paragraph.size(); ++right) {
      // If the right position of the paragraph is one of the keywords,
      // then decrease the occurrence of that keyword in the keywordsToCover
      // If as result of that decrease the there were 1 or more than 1 keywords, decrease the remaining to cover
      if (keywordsToCover.containsKey(paragraph.get(right)) &&
              keywordsToCover.put(paragraph.get(right), keywordsToCover.get(paragraph.get(right)) - 1) >= 1) {
        --remainingToCover;
      }

      // Keeps advancing left until it reaches end or keywordsToCover does not have all the keywords.
      while (remainingToCover == 0) {
        // If we just started or if we have started but the difference between current pointers is smaller than the
        // difference on the result
        if ((result.start == -1 && result.end == -1) || right - left < result.end - result.start) {
          // Update the result
          result.start = left;
          result.end = right;
        }

        // If the left pointer is pointing a keyword
        // Then increase the number of occurrences of the keyword
        // If the occurrence was equal or bigger than 0, then increase the cover
        if (keywordsToCover.containsKey(paragraph.get(left)) &&
            keywordsToCover.put(paragraph.get(left),
                    keywordsToCover.get(paragraph.get(left)) + 1) >= 0) {
          ++ remainingToCover;
        }
        // Advance the left pointer (the left pointer will be more advanced than the right one?)
        // Advance the left pointer until there is no item remain to cover
        ++left;
      }
    }
    return result;
  }

  @Test
  public void testBookSol1FindSmallestSubarrayCoveringSet1() {
    // Given
    List<String> words = Arrays.asList("g", "V", "z", "K", "t", "K", "J", "H", "d", "b", "C", "K", "O", "q", "m", "o");
    Set<String> keywords = Set.copyOf(Arrays.asList("t", "q", "C", "b", "H", "J"));

    // When
    Subarray result = bookSol1FindSmallestSubarrayCoveringSet(words, keywords);

    // Then
    Subarray expected = new Subarray(4, 13);
    assertEquals(expected, result);
  }

  @EpiTest(testDataFile = "smallest_subarray_covering_set.tsv")
  public static int findSmallestSubarrayCoveringSetWrapper(
      TimedExecutor executor, List<String> paragraph, Set<String> keywords)
      throws Exception {
    Set<String> copy = new HashSet<>(keywords);

    Subarray result = executor.run(
        () -> findSmallestSubarrayCoveringSet(paragraph, keywords));

    if (result.start < 0 || result.start >= paragraph.size() ||
        result.end < 0 || result.end >= paragraph.size() ||
        result.start > result.end)
      throw new TestFailure("Index out of range");

    for (int i = result.start; i <= result.end; i++) {
      copy.remove(paragraph.get(i));
    }

    if (!copy.isEmpty()) {
      throw new TestFailure("Not all keywords are in the range");
    }
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SmallestSubarrayCoveringSet.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
