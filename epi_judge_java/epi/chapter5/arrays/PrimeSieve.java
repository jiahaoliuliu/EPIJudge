package epi.chapter5.arrays;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
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
   *
   *  Complexity: O(n * sqrt(n)/(log n)^2) = O(n^(3/2)/(log n)^2)
   *  Space complexity: O(n)
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

  /**
   * Book solution 1 - Using array of booleans
   * Complexity: O(n/2 + n/3 + n/5 + n/7 + n/11 + ) = O(n log log n)
   * Space complexity O(n)
   * @param args
   */
  private static List<Integer> bookSol1GeneratePrimes(int n) {
    List<Integer> primes = new ArrayList<>();
    // isPrime.get(p) represents if p is prime or not. Initially, set each
    // to true, excepting 0 and 1. Then use sieving to eliminate nonprimes.
    List<Boolean> isPrime = new ArrayList<>(Collections.nCopies(n+1, true));
    isPrime.set(0, false);
    isPrime.set(1, false);
    for (int p = 2; p <=n; ++p) {
      if(isPrime.get(p)) {
        primes.add(p);
        // sieve p's multiplies
        for (int i = p * 2; i <= n; i+=p) {
          isPrime.set(i, false);
        }
      }
    }
    return primes;
  }

  /**
   * Book solution 2 - Sierving from p^2 instead of p
   * @param args
   */
  private static List<Integer> bookSol2GeneratePrimes(int n) {
    if (n < 2) {
      return Collections.emptyList();
    }

    final int size = (int)Math.floor(0.5 * (n - 3)) + 1;
    List<Integer> primes = new ArrayList<>();
    primes.add(2);
    // isPrime.get(i) represents whether (2i + 3) is prime or not.
    // For example, isPrime.get(0) represents 3 is prime or not,
    // isPrime.get(1) represents 5, isPrime.get(2) represents 7, etc
    // Initially, set each to true. Then use sierving to eliminate nonprimes.
    List<Boolean> isPrime = new ArrayList<>(Collections.nCopies(size, true));
    for (long i = 0; i < size; ++i) {
      if (isPrime.get((int)i)) {
        int p = (((int)i * 2) + 3);
        primes.add(p);
        // Sieving from p^2, whose value is (4i^2 + 12i + 9). The index of this
        // value in isPrime is (2i^2 + 6i + 3) because isPrime.get(i) represents
        // 2i + 3
        //
        // Note that we need to use long type for j because p^2 might overflow.
        for (long j = ((i * i) * 2) + 6 * i + 3; j < size; j += p) {
          isPrime.set((int)j, false);
        }
      }
    }
    return primes;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PrimeSieve.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
