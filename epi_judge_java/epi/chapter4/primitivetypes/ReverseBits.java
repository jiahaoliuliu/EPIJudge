package epi.chapter4.primitivetypes;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * Write a program that takes a 64-bit unsigned integer and returns the 640bit unsigned integer consistent
 * of the bits of the input in the reverse order
 * Example:
 * - Input:  1110 0000 0000 0000 0001
 * - Output: 1000 0000 0000 0000 0111
 */
public class ReverseBits {

  @EpiTest(testDataFile = "reverse_bits.tsv")
  public static long reverseBits(long number) {
    // Special cases
    if (number == 0) return 0;
    long result = 0;
    int currentPositionRtl = 63;

    while (currentPositionRtl >= 0) {
      int lastBit = (number & 1) > 0 ? 1 : 0;
      result += lastBit;
      result = result << 1;
      number = number >> 1;
      currentPositionRtl --;
      if (number == 0) {
        result = result << currentPositionRtl;
        return result;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReverseBits.java",
             new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
