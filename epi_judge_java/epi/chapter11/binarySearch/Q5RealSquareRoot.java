package epi.chapter11.binarySearch;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Square root computations can be implemented using sophisticated numerical techniques involving
 * iterative methods and logarithms.
 *
 * However, if you were asked to implement a square root function, you would not be expected to
 * know these techniques.
 *
 * Implement a function which takes as input a floating point value and returns its square root.
 *
 * Hint: Iteratively compute a sequence of intervals, each contained in the previous interval,
 * that contain the result.
 *
 */
public class Q5RealSquareRoot {

  /**
   * Initial analysis. Try and error. Create a interval from 0 to x and each one of the iteractions
   * reduce the size of the interval
   *
   * But this interval does not have to exist in real life, because it is a double. It is good enough
   * setting the constraints
   *
   * @param x
   * @return
   */
  @EpiTest(testDataFile = "real_square_root.tsv")
  public static double squareRoot(double x) {
    if (x == 1.0) return 1.0; // Corner case

    double lowerLimit;
    double upperLimit;
    // Extra
    if (x < 1.0) {
      lowerLimit = x;
      upperLimit = 1.0;
    } else {
      lowerLimit = 1.0;
      upperLimit = x;
    }

    while (lowerLimit < upperLimit) {
      double middlePoint = lowerLimit + (upperLimit - lowerLimit) / 2; // To avoid overflow
      double square = middlePoint * middlePoint; // This might overflow...
      if (Math.abs(square - x) < 0.000001)  {
        return middlePoint;
      } else if (square > x) {
        upperLimit = middlePoint;
      } else {
        lowerLimit = middlePoint;
      }
    }

    return Double.NaN;
  }

  @Test
  public void testSquareRoot1() {
    // Given
    double x = 9.0;

    // When
    double result = squareRoot(x);

    // Then
    assertEquals(3.0, result, 0.00001);
  }

  @Test
  public void testSquareRoot2() {
    // Given
    double x = 1.0;

    // When
    double result = squareRoot(x);

    // Then
    assertEquals(1.0, result, 0.00001);
  }

  /**
   * Book solution 1
   * @param args
   */
  public static double bookSol1SquareRoot(double x) {
    // Decides the search range according to x's value relative to 1.0.
    // We cannot start with [0, x] because the square root may be larger than x
    // e.g square root of 1/4 = 1/2.
    // - If x < 1.0, the interval is [x, 1.0]
    // - If x >= 1.0, the interval is [1.0, x]
    double left, right;
    if (x < 1.0) {
      left = x;
      right = 1.0;
    } else { // x >= 1.0
      left = 1.0;
      right = x;
    }

    // Keep searching as long as left != right, within tolerance.
    while (compare(left, right) != Ordering.EQUAL) {
      double mid = left + 0.5 * (right - left);
      double midSquare = mid * mid;
      if (compare(midSquare, x) == Ordering.LARGER) {
        right = mid;
      } else {
        left = mid;
      }
    }
    return left;
  }

  private enum Ordering { SMALLER, EQUAL, LARGER }

  private static Ordering compare(double a, double b) {
    final double EPSILON  = 0.000001;
    // Uses normalization for precision problem
    double diff = (a - b)/ Math.max(Math.abs(a), Math.abs(b));
    return diff < -EPSILON
            ? Ordering.SMALLER
            : (diff > EPSILON ? Ordering.LARGER : Ordering.EQUAL);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Q5RealSquareRoot.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
