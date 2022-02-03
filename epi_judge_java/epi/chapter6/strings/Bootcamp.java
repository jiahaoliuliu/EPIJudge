package epi.chapter6.strings;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A palindromic string is one which reads the same when it is reversed. Create a program to check
 * if a string is palindrome.
 * Time complexity = O(n)
 * Space complexity = O(1)
 *
 * Example: tacocat = tacocat
 */
public class Bootcamp {

    public static boolean isPalindromic(String s) {
        if (s.isEmpty() || s.length() == 1) return true;

        int leftPointer = 0;
        int rightPointer = s.length() - 1;
        while (leftPointer < rightPointer) {
            if (s.charAt(leftPointer) != s.charAt(rightPointer)){
                return false;
            }
            rightPointer--;
            leftPointer++;
        }
        return true;
    }

    @Test
    public void testIsPalindromic1() {
        // Given
        String s = "a";

        // When
        boolean result = isPalindromic(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testIsPalindromic2() {
        // Given
        String s = "ab";

        // When
        boolean result = isPalindromic(s);

        // Then
        assertFalse(result);
    }

    @Test
    public void testIsPalindromic3() {
        // Given
        String s = "aba";

        // When
        boolean result = isPalindromic(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testIsPalindromic4() {
        // Given
        String s = "taccat";

        // When
        boolean result = isPalindromic(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testIsPalindromic5() {
        // Given
        String s = "tacocat";

        // When
        boolean result = isPalindromic(s);

        // Then
        assertTrue(result);
    }

    /**
     * Book solution 1
     * Time complexity = O(n)
     * Space complexity = O(1)
     */
    public static boolean bookSol1IsPalindromic(String s) {
        for (int i = 0, j = s.length() - 1; i < j; ++i, --j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testBookSol1IsPalindromic1() {
        // Given
        String s = "a";

        // When
        boolean result = bookSol1IsPalindromic(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testBookSol1IsPalindromic2() {
        // Given
        String s = "ab";

        // When
        boolean result = bookSol1IsPalindromic(s);

        // Then
        assertFalse(result);
    }

    @Test
    public void testBookSol1IsPalindromic3() {
        // Given
        String s = "aba";

        // When
        boolean result = bookSol1IsPalindromic(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testBookSol1IsPalindromic4() {
        // Given
        String s = "taccat";

        // When
        boolean result = bookSol1IsPalindromic(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testBookSol1IsPalindromic5() {
        // Given
        String s = "tacocat";

        // When
        boolean result = bookSol1IsPalindromic(s);

        // Then
        assertTrue(result);
    }


}
