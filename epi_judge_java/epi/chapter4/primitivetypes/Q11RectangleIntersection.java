package epi.chapter4.primitivetypes;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This problem is concerned with rectangles whose sides are parallel to the X-axis and Y-axis.
 * Write a program which tests if two rectangles have a nonempty intersection. If the intersection
 * is nonempty, return the rectangle formed y their intersection.
 *
 * Hint: Think of the X and Y dimensions independently.
 */
public class Q11RectangleIntersection {
  @EpiUserType(ctorParams = {int.class, int.class, int.class, int.class})
  public static class Rect {
    int x, y, width, height;

    public Rect(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Rect rectangle = (Rect)o;

      if (x != rectangle.x) {
        return false;
      }
      if (y != rectangle.y) {
        return false;
      }
      if (width != rectangle.width) {
        return false;
      }
      return height == rectangle.height;
    }

    @Override
    public int hashCode() {
      int result = x;
      result = 31 * result + y;
      result = 31 * result + width;
      result = 31 * result + height;
      return result;
    }

    @Override
    public String toString() {
      return "[" + x + ", " + y + ", " + width + ", " + height + "]";
    }
  }
  @EpiTest(testDataFile = "rectangle_intersection.tsv")
  public static Rect intersectRectangle(Rect r1, Rect r2) {
    if (hasIntersection(r1.x, r1.x + r1.width, r2.x, r2.x + r2.width) &&
            hasIntersection(r1.y, r1.y + r1.height, r2.y, r2.y + r2.height)) {
       int x = calculateIntersectionPoint(r1.x, r1.x + r1.width, r2.x, r2.x + r2.width);
       int width = calculateIntersectionDifference(r1.x, r1.x + r1.width, r2.x, r2.x + r2.width);
       int y = calculateIntersectionPoint(r1.y, r1.y + r1.height, r2.y, r2.y + r2.height);
       int height = calculateIntersectionDifference(r1.y, r1.y + r1.height, r2.y, r2.y + r2.height);
       return new Rect(x, y, width, height);
    }
    return new Rect(0, 0, -1, -1);
  }

  private static boolean hasIntersection(int rect1Origin, int rect1Destination, int rect2Origin, int rect2Destination) {
    return (rect1Origin <= rect2Origin && rect2Origin <= rect1Destination) ||
            (rect2Origin <= rect1Origin && rect1Origin <= rect2Destination);
  }

  private static int calculateIntersectionPoint(
          int rect1XOrigin, int rect1XDestination, int rect2XOrigin, int rect2XDestination) {
        // If rect 2 X origin is between rect 1 X origin and rect 1 X destination
        if (rect1XOrigin <= rect2XOrigin && rect2XOrigin <= rect1XDestination) {
          return rect2XOrigin;
        }

        // If rect 1 X origin is between rect 2 X origin and rect 2 X destination
        if (rect2XOrigin <= rect1XOrigin && rect1XOrigin <= rect2XDestination) {
          return rect1XOrigin;
        }
    //If it is not any of the cases, return 0
    return 0;
  }

  private static int calculateIntersectionDifference(
          int rect1XOrigin, int rect1XDestination, int rect2XOrigin, int rect2XDestination) {
          // If the final point of rect 2 is between rect 1 origin and rect 1 destination
          // return the difference between rect2X destination and
          if (rect1XOrigin <= rect2XDestination && rect2XDestination <= rect1XDestination) {
            // If the second rectangle is included inside the first rectangle
            if (rect1XOrigin <= rect2XOrigin && rect2XOrigin <= rect1XDestination) {
              return rect1XDestination - rect1XOrigin;
            }
            return rect2XDestination - rect1XOrigin;
          }

          // If the final point of rect 1 is between rect 2 origin and rect 2 destination
          if (rect2XOrigin <= rect1XDestination && rect1XDestination <= rect2XDestination) {
            // If the rect 1 is included inside rect 2
            if (rect2XOrigin <= rect1XOrigin && rect1XOrigin <= rect2XDestination) {
              return rect1XDestination - rect1XOrigin;
            }
            return rect1XDestination - rect2XOrigin;
          }

      return 0;
  }

  @Test
  public void test1() {
    // Given
    Rect rect1 = new Rect(1, 1, 3, 4);
    Rect rect2 = new Rect(2,3,4, 8);

    // When
    Rect result = intersectRectangle(rect1, rect2);

    // Then
    Rect expected = new Rect(2, 3, 2, 2);
    assertEquals(expected, result);
  }

  /**
   * Book solution 1
   * @param args
   */
  public static Rect BookSol1intersectRectangle(Rect r1, Rect r2) {
    if (!isIntersect(r1, r2)) {
      return new Rect(0, 0, -1, -1);
    }

    return new Rect(
            Math.max(r1.x, r2.x), Math.max(r1.y, r2.y),
            Math.min(r1.x + r1.width, r2.x + r2.width) - Math.max(r1.x, r2.x),
            Math.min(r1.y + r1.height, r2.y + r2.height) - Math.max(r1.y, r2.y));
  }

  private static boolean isIntersect(Rect r1, Rect r2) {
    return r1.x <= r2.x + r2.width && r1.x + r1.width >= r2.x &&
            r1.y <= r2.y + r2.height && r1.y + r1.height >= r2.y;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "RectangleIntersection.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
