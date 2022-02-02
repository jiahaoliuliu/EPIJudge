package epi.chapter5.arrays;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A natural number is called a prime if it is bigger than 1 and has no divisors other than 1 and itself.
 * Prime numbers play a central role in modern cryptographic algorithms. For example, in the RSA
 * encryption system, all arithmetic is done module n, where n is the product of two large primes.
 *
 * Write a program that takes an integer argument and returns all the primes between 1 and that integer.
 * For example, if the input is 18, you should return (2, 3, 5, 7, 11, 13, 17)
 *
 * Hint: Exclude the multiple of primes
 */
public class PrimeSieve {

  /**
   * Analysis. We can generate all the number from 2 to n and do elimination
   * - How to eliminate?
   * - What to eliminate?
   *
   * Brute force: For all the number from 2 to n, check if it is prime
   * - Prime only can divide by itself and 1
   *    - The iteration need to include itself
   *      - from 2
   *      - while n <= sqrt(itself)
   *      - n++
   * Optimization: Once found a prime, remove all the multiplications of the prime
   *               from the list
   *               - Set a list of booleans to mark the values
   *               - The list of booleans will have the same size as the list of n
   *               - The default value will be false (it is not checked)
   * @param n
   * @return
   */
  @EpiTest(testDataFile = "prime_sieve.tsv")
  // Given n, return all primes up to and including n.
  public static List<Integer> generatePrimes(int n) {
    List<Integer> result = new ArrayList<>();
    boolean[] checkedList = new boolean[n];
    int currentPrime = 2;
    while (currentPrime <= n) {
      if (!checkedList[currentPrime-1]) {
        checkedList[currentPrime-1] = true;
        if (isPrime(currentPrime)) {
          result.add(currentPrime);
          // Mark the rest of the primes
          for (int i = currentPrime+currentPrime; i <= checkedList.length; i+=currentPrime) {
            checkedList[i-1] = true;
          }
        }
      }
      currentPrime++;
    }

    return result;
  }

  /**
   * Check if a number is prime. A prime number is only divisible by 1 and itself.
   * We do not have to check for all the numbers, but from 1 to sqrt(n) because we are dealing with
   * multiplications
   * @param number
   * @return
   */
  private static boolean isPrime(int number) {
    for (int i = 2; i <= Math.sqrt(number); i++) {
      if (number % i == 0) return false;
    }

    return true;
  }

  @Test
  public void testIsPrime() {
    // Given
    int number = 121;

    // When
    boolean result = isPrime(number);

    // Then
    assertFalse(result);
  }

  @Test
  public void testIsPrime2() {
    // Given
    int number = 2;

    // When
    boolean result = isPrime(number);

    // Then
    assertTrue(result);
  }

  @Test
  public void testGeneratePrimes() {
    // Given
    int n = 2;

    // When
    List<Integer> result = generatePrimes(n);

    // Then
    List<Integer> expectedList = new ArrayList<>();
    expectedList.add(2);

    assertTrue(expectedList.equals(result));
  }

  @Test
  public void testGeneratePrimes3() {
    // Given
    int n = 3;

    // When
    List<Integer> result = generatePrimes(n);

    // Then
    List<Integer> expectedList = new ArrayList<>();
    expectedList.add(2);
    expectedList.add(3);

    assertTrue(expectedList.equals(result));
  }

  @Test
  public void testGeneratePrimes4() {
    // Given
    int n = 11;

    // When
    List<Integer> result = generatePrimes(n);

    // Then
    List<Integer> expectedList = new ArrayList<>();
    expectedList.add(2);
    expectedList.add(3);
    expectedList.add(5);
    expectedList.add(7);
    expectedList.add(11);

    assertTrue(expectedList.equals(result));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PrimeSieve.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
