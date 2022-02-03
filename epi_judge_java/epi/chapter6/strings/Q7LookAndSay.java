package epi.chapter6.strings;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The look-and-say sequence starts with 1.
 * Subsequent numbers are derived by describing the previous number in terms of consecutive
 * digits. Specially, to generate an entry of the sequence from the previous entry, read off
 * the digits of the previous entry, counting the number of digits in groups of the same digit.
 * For example:
 * - 1:                                         1
 * - one 1:                                    11
 * - two 1s:                                   21
 * - one 2 then one 1:                        1211
 * - one 1, then one 2, then two 1s:         111221
 * - three ones, then two 2s, then one 1:    312211
 * - one 3, one 1, two 2s, two 1s:          13112221
 * - one 1, one 3, two 1s, three 2s, one 1:1113213211
 *
 * Time complexity = O(k)
 *  - Where k = 1 + length of parameter when n = 2 + length of parameter when n = 3 until the last n
 *
 * Space complexity = O(n)
 *
 */
public class Q7LookAndSay {
  @EpiTest(testDataFile = "look_and_say.tsv")

  public static String lookAndSay(int n) {
    String result = "1";
    while (n > 1) {
      result = lookAndSay(result);
      n--;
    }

    return result;
  }

  public static String lookAndSay(String init) {
    StringBuilder stringBuilder = new StringBuilder();
    int currentNumber = -1; // This could be char
    int occurrences = -1;
    for (char c: init.toCharArray()) {
      int value = Integer.parseInt(String.valueOf(c)); // Not need to parse int. This could be char
      // If a different number has been found
      if (value != currentNumber) {
        // If it is the first number of the series
        if (currentNumber != -1) {
          stringBuilder.append(occurrences);
          stringBuilder.append(currentNumber);
        }
        currentNumber = value;
        occurrences = 1;
      } else {
        occurrences++;
      }
    }
    // Append the last value // Is it possible that this value is never initialized?
    stringBuilder.append(occurrences);
    stringBuilder.append(currentNumber);

    return stringBuilder.toString();
  }

  @Test
  public void testLookAndSay1() {
    // Given
    int n = 1;

    // When
    String result = lookAndSay(n);

    // Then
    assertEquals("1", result);
  }

  @Test
  public void testLookAndSay2() {
    // Given
    int n = 2;

    // When
    String result = lookAndSay(n);

    // Then
    assertEquals("11", result);
  }

  @Test
  public void testLookAndSay3() {
    // Given
    int n = 3;

    // When
    String result = lookAndSay(n);

    // Then
    assertEquals("21", result);
  }

  @Test
  public void testLookAndSay4() {
    // Given
    int n = 4;

    // When
    String result = lookAndSay(n);

    // Then
    assertEquals("1211", result);
  }

  @Test
  public void testLookAndSay5() {
    // Given
    int n = 8;

    // When
    String result = lookAndSay(n);

    // Then
    assertEquals("1113213211", result);
  }

  /**
   * Book solution 1
   *
   * The precise time complexity is a function of the length of the terms, which is extremely hard to analyze.
   * Each successive number can have at most twice as many digits as the previous number
   *  - This happens when all digits are different
   *  - This means the maximum length number has length no more than 2^n
   *  Since there are n iterations and the word in each iteration is proportional to the length of the number
   *  computed in teh iteration, a simple bound on the time complexity is O(n2^n)
   * @param args
   */
  private static String bookSol1LookAndSay(int n) {
    String s = "1";
    for (int i = 1; i < n; ++i) {
      s = nextNumber(s);
    }
    return s;
  }

  private static String nextNumber(String s) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < s.length(); ++i) {
      int count = 1;
      while (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
        ++i; // Advance i manually. This is quite dangerous.
        ++count;
      }
      result.append(count).append(s.charAt(i));
    }
    return result.toString();
  }

  @Test
  public void testBookSol1LookAndSay1() {
    // Given
    int n = 1;

    // When
    String result = bookSol1LookAndSay(n);

    // Then
    assertEquals("1", result);
  }

  @Test
  public void testBookSol1LookAndSay2() {
    // Given
    int n = 2;

    // When
    String result = bookSol1LookAndSay(n);

    // Then
    assertEquals("11", result);
  }

  @Test
  public void testBookSol1LookAndSay3() {
    // Given
    int n = 3;

    // When
    String result = bookSol1LookAndSay(n);

    // Then
    assertEquals("21", result);
  }

  @Test
  public void testBookSol1LookAndSay4() {
    // Given
    int n = 4;

    // When
    String result = bookSol1LookAndSay(n);

    // Then
    assertEquals("1211", result);
  }

  @Test
  public void testBookSol1LookAndSay5() {
    // Given
    int n = 8;

    // When
    String result = bookSol1LookAndSay(n);

    // Then
    assertEquals("1113213211", result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LookAndSay.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
