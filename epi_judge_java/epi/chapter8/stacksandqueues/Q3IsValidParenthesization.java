package epi.chapter8.stacksandqueues;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A string over the characters "{,},(,),[,]" is said to be well-formed if the different types of
 * brackets match in the correct order.
 *
 * For example, "([]){()}" is well-formed, as is "[()[]{()()}]". However, "{)", "{(})", and "[()[]{()()}"
 * are not well-formed
 *
 * Write a program that tests if a string made up of the characters '(', ')', '[', ']', '{' and '}' is
 * well-formed.
 *
 * Hint: Which left parenthesis does a right parenthesis match with?
 */
public class Q3IsValidParenthesization {

  /**
   * Initial analysis: Add the left part of the parenthesis to the queue.
   * When a right part of parenthesis is found, check if the first element of the stack
   * is the one which matches to the right parenthesis
   *
   * @param s The string with parenthesis
   * @return False if the right part of parenthesis does not matches with the element in the queue
   *         false if the length is even
   *         True if given all the chars in the string, the stack is empty
   */
  @EpiTest(testDataFile = "is_valid_parenthesization.tsv")
  public static boolean isWellFormed(String s) {
    if (s == null || s.equals("")) return true;
    if (s.length() % 2 != 0) return false;
    Deque<Character> stack = new ArrayDeque<>();
    for (char c: s.toCharArray()) {
      if (isLeftParenthesis(c)) {
        stack.push(c);
      } else {
        if (stack.isEmpty()) {
          return false;
        }
        char tmp = stack.poll();
        if (!areTheyMatching(tmp,c)) {
          return false;
        }
      }
    }

    return stack.isEmpty();
  }

  private static boolean isLeftParenthesis(char c) {
    return c == '(' || c == '{' || c == '[';
  }

  private static boolean areTheyMatching(char left, char right) {
    if (left == '(') return right == ')';
    if (left == '{') return right == '}';
    if (left == '[') return right == ']';
    return false;
  }

  @Test
  public void testIsWellFormed1() {
    // Given
    String word = null;

    // When
    boolean result = isWellFormed(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsWellFormed2() {
    // Given
    String word = "";

    // When
    boolean result = isWellFormed(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsWellFormed3() {
    // Given
    String word = "([]){()}";

    // When
    boolean result = isWellFormed(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsWellFormed4() {
    // Given
    String word = "[()[]{()()}]";

    // When
    boolean result = isWellFormed(word);

    // Then
    assertTrue(result);
  }

  @Test
  public void testIsWellFormed5() {
    // Given
    String word = "{)";

    // When
    boolean result = isWellFormed(word);

    // Then
    assertFalse(result);
  }

  @Test
  public void testIsWellFormed6() {
    // Given
    String word = "{(})";

    // When
    boolean result = isWellFormed(word);

    // Then
    assertFalse(result);
  }

  @Test
  public void testIsWellFormed7() {
    // Given
    String word = "[()[]{()()}";

    // When
    boolean result = isWellFormed(word);

    // Then
    assertFalse(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsValidParenthesization.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
