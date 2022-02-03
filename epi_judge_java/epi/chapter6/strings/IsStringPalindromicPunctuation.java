package epi.chapter6.strings;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * For the purpose of this problem, define a palindromic string to be a string which when
 * all the nonalphanumeric are removed it reads the same front to back ignoring case.
 * For example, "A man a plan, a canal, Panama." and "Able was I, ere I saw Elba!". are
 * palindromic, but "Ray a Ray" is not.
 *
 * Implement a function which takes as input a string s and returns true if s is a palindromic
 * string.
 *
 * Hint: Use two indices
 *
 */
public class IsStringPalindromicPunctuation {

  /**
   * Basic analysis
   * - Uppercase == lowercase!
   * - Remove any char tht is not
   * -- Upper case char
   * -- Lower case char
   * -- Numbers
   * ---- Use a method called isValidChar(char)
   * - Use String.charAt(i) to discard
   * - Use two pointers
   * -- Left pointer:
   * -- Right pointer:
   * -- Because the char on the left are different char on the right, they need to advance in
   * -- different speed
   * -- Until right < left
   * - How to change lower case?
   * -- 1. Transform the String since beginning String.toLowercase()
   * -- 2. Transform the char one by one
   * ---- This seems to reduce the complexity
   *
   * Time complexity = O(n)
   * Space complexity = O(1)
   *
   * @param s
   * @return
   */
  @EpiTest(testDataFile = "is_string_palindromic_punctuation.tsv")
  public static boolean isPalindrome(String s) {
    if (s.isEmpty() || s.length() == 1) return true;

    for (int left = 0, right = s.length()-1; left < right;) {
      // If the left char is not alphaNumeric, then advance left
      if (!isAlphaNumeric(s.charAt(left))) {
        left++;
      } else if (!isAlphaNumeric(s.charAt(right))) {
        right--;
      } else { // Left and right are alphanumeric
        if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase( s.charAt(right))) {
          return false;
        } else {
          left++;
          right--;
        }
      }
    }

    return true;
  }

  /**
   * Check if the char is alphanumeric. This could be checked using the int property
   * on chars.
   * c is alphaNumeric when
   *   || It belongs to ‘A’ to ‘Z’
   *   || It belongs to ‘a’ to ‘z’
   *   || It belongs to ‘0’ to ‘9’
   */
  private static boolean isAlphaNumeric(char c) {
    if (c - 'a' >= 0 && 'z' - c >= 0) return true;
    if (c - 'A' >= 0 && 'Z' - c >= 0) return true;
    return c - '0' >= 0 && '9' - c >= 0;
  }

  @Test
  public void testIsAlphaNumeric1() {
    // Given
    char c = 'a';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsAlphaNumeric2() {
    // Given
    char c = 'z';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsAlphaNumeric3() {
    // Given
    char c = 'A';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsAlphaNumeric4() {
    // Given
    char c = 'Z';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsAlphaNumeric5() {
    // Given
    char c = '0';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsAlphaNumeric6() {
    // Given
    char c = '9';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsAlphaNumeric7() {
    // Given
    char c = ' ';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertFalse(result);
  }

  @Test
  public void testIsAlphaNumeric8() {
    // Given
    char c = ',';

    // When
    boolean result = isAlphaNumeric(c);

    // Then
    assertFalse(result);
  }

  @Test
  public void testIsPalindrome() {
    // Given
    String word = "taco cat";

    // When
    boolean result = isPalindrome(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsPalindrome2() {
    // Given
    String word = "A man a plan, a canal, Panama.";

    // When
    boolean result = isPalindrome(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsPalindrome3() {
    // Given
    String word = "Able was I, ere I saw Elba!";

    // When
    boolean result = isPalindrome(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsPalindrome4() {
    // Given
    String word = "Ray a Ray";

    // When
    boolean result = isPalindrome(word);

    // Then
    assertFalse(result);
  }

  /**
   * Book solution 1 - Using a while
   * @param args
   */
  private static boolean bookSol1IsPalindrome(String s) {
    // i moves forward, and j moves backward
    int i = 0, j = s.length() - 1;
    while (i < j) {
      // i and j both skip non-alphanumeric characters.
      while (!Character.isLetterOrDigit(s.charAt(i)) && i < j) {
        ++i;
      }

      while (!Character.isLetterOrDigit(s.charAt(j)) && i < j) {
        --j;
      }

      if (Character.toLowerCase(s.charAt(i++)) != Character.toLowerCase(s.charAt(j--))) {
        return false;
      }
    }
    return true;
  }

  @Test
  public void testBookSol1IsPalindrome1() {
    // Given
    String word = "taco cat";

    // When
    boolean result = bookSol1IsPalindrome(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testBookSol1IsPalindrome2() {
    // Given
    String word = "A man a plan, a canal, Panama.";

    // When
    boolean result = bookSol1IsPalindrome(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testBookSol1IsPalindrome3() {
    // Given
    String word = "Able was I, ere I saw Elba!";

    // When
    boolean result = bookSol1IsPalindrome(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testBookSol1IsPalindrome4() {
    // Given
    String word = "Ray a Ray";

    // When
    boolean result = bookSol1IsPalindrome(word);

    // Then
    assertFalse(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringPalindromicPunctuation.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
