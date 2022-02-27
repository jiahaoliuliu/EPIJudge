package epi.chapter16.dynamicprogramming;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Suppose you are given a 2D array of integers(the "grid"), and a 1D array of integers
 * (the "pattern"). We say the pattern occurs in the grid if it is possible to start from some
 * entry in the grid and traverse adjacent entries in the order specified by the pattern till
 * all entries in the pattern have been visited.
 *
 * The entries adjacent to an entry are the ones directly above, below, to the left, and to the
 * right, assuming they exist. For example, the entries adjacent to (3, 4) are (3, 3), (3, 5)
 * (2, 4) and (4,4). It is acceptable to visit an entry in the grid more than once.
 *
 * As an example, if the grid is
 *            | 1   2   3|
 *            | 3   4   5|
 *            | 5   6   7|
 * and the pattern is <1, 3, 4, 6>, then the pattern occurs in the grid consider the entries
 * <(0,0), (1,0), (1,1), (2,1)>. However, <1, 2, 3, 4> does not occur in the grid.
 *
 * Write a program that takes as argument a 2D array and a 1D array, and check whether the 1D
 * array occurs in the 2D array.
 *
 * Hint: Start with length 1 prefixes of the 1D array, then move on to length 2, 3, ... prefixes.
 *
 * Variant: Solve the same problem when you cannot visit an entry in A more than once
 *
 * Variant: Enumerate all solutions when you cannot visit an entry in A more than once.
 */
public class Q5IsStringInMatrix {

  /**
   * Corner cases
   * - the 1D array is empty -> return true
   * - the patter is bigger than the size of the grid -> Return false
   * Initial analysis. We can go through all the elements of the array
   * Suppose all the elements of the grid are distinct
   * Let's start thinking that the pattern is of size 1.
   * - Then we need to find it in the grid
   * TODO: The elements could be repeated
   * @param grid
   * @param pattern
   * @return
   */
  @EpiTest(testDataFile = "is_string_in_matrix.tsv")
  public static boolean isPatternContainedInGrid(List<List<Integer>> grid,
                                                 List<Integer> pattern) {
    if (pattern.isEmpty()) {
      return true;
    }

    int row = grid.size();
    int column = grid.get(0).size();
    if (pattern.size() > row * column) {
      return false;
    }

    Point firstPatternItemPoint = null;
    OUTSIDE_LOOP:for (int i = 0; i < grid.size(); i++) {
      List<Integer> currentRow = grid.get(i);
      for (int j = 0; j < currentRow.size(); j++) {
        if (Objects.equals(currentRow.get(j), pattern.get(0))) {
          firstPatternItemPoint = new Point(i, j);
          break OUTSIDE_LOOP;
        }
      }
    }

    // If the first element of the pattern cannot be found, then the pattern cannot be in the grid
    if (firstPatternItemPoint == null) {
      return false;
    }

    Set<Point> visitedPoints = new HashSet<>();
    visitedPoints.add(firstPatternItemPoint);
    return isPatternContainedInGrid(grid, pattern, 0, firstPatternItemPoint, visitedPoints);
  }

  private static boolean isPatternContainedInGrid(List<List<Integer>> grid, List<Integer> pattern,
                                                  int currentPatternPosition, Point currentPosition, Set<Point> visitedPoints) {
    // If we found all the elements in the pattern in the grip without problem, then return true;
    if (currentPatternPosition == pattern.size()) {
      return true;
    }

    // If so, check for the left
    Point leftPoint = new Point(currentPosition.row - 1, currentPosition.col);
    if (isValid(leftPoint, grid, visitedPoints)) {
        visitedPoints.add(leftPoint);
        if (Objects.equals(grid.get(leftPoint.row).get(leftPoint.col), pattern.get(currentPatternPosition + 1))) {
          // Check for next node
          return isPatternContainedInGrid(grid, pattern, currentPatternPosition + 1, leftPoint, visitedPoints);
        }
    }

    // If so, check for the right
    Point rightPoint = new Point(currentPosition.row + 1, currentPosition.col);
    if (isValid(rightPoint, grid, visitedPoints)) {
      visitedPoints.add(rightPoint);
      if (Objects.equals(grid.get(rightPoint.row).get(rightPoint.col), pattern.get(currentPatternPosition + 1))) {
        // Check for next node
        return isPatternContainedInGrid(grid, pattern, currentPatternPosition + 1, rightPoint, visitedPoints);
      }
    }

    // If so, check for the up
    Point upPoint = new Point(currentPosition.row + 1, currentPosition.col);
    if (isValid(upPoint, grid, visitedPoints)) {
      visitedPoints.add(upPoint);
      if (Objects.equals(grid.get(upPoint.row).get(upPoint.col), pattern.get(currentPatternPosition + 1))) {
        // Check for next node
        return isPatternContainedInGrid(grid, pattern, currentPatternPosition + 1, upPoint, visitedPoints);
      }
    }

    // If so, check for the down
    Point downPoint = new Point(currentPosition.row + 1, currentPosition.col);
    if (isValid(downPoint, grid, visitedPoints)) {
      visitedPoints.add(downPoint);
      if (Objects.equals(grid.get(downPoint.row).get(downPoint.col), pattern.get(currentPatternPosition + 1))) {
        // Check for next node
        return isPatternContainedInGrid(grid, pattern, currentPatternPosition + 1, downPoint, visitedPoints);
      }
    }

    return false;
  }

  public static boolean isValid(Point point, List<List<Integer>> grid, Set<Point> visitedPoints) {
    if (visitedPoints.contains(point)) {
      return false;
    }

    if (point.row >= grid.size() || point.row < 0) {
      return false;
    }

    if (point.col >= grid.get(0).size() || point.col < 0) {
      return false;
    }

    return true;
  }

  // We can cache the visited point to optimization
  static class Point {
    public final int row;
    public final int col;

    public Point(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Point)) return false;
      Point point = (Point) o;
      return row == point.row && col == point.col;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col);
    }

    @Override
    public String toString() {
      return "Point{" +
              "row=" + row +
              ", col=" + col +
              '}';
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringInMatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }

  /**
   * Book solution 1
   */
  private static class Attempt {
    public Integer x;
    public Integer y;
    public Integer offset;

    public Attempt(Integer x, Integer y, Integer offset) {
      this.x = x;
      this.y = y;
      this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Attempt cacheEntry = (Attempt) o;
      if (x != null ? !x.equals(cacheEntry.x) : cacheEntry.x != null) {
        return false;
      }
      if (y != null ? !y.equals(cacheEntry.y) : cacheEntry.y != null) {
        return false;
      }
      if (offset != null ? !offset.equals(cacheEntry.offset) : cacheEntry.offset != null) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y, offset);
    }
  }

  /**
   * Book solution 1.
   * Time complexity: O(nml)
   *  where n and m are the dimensions of A,
   *  l is the length of S
   * Space complexity: O(nml)
   * @param grid
   * @param pattern
   * @return
   */
  public static boolean bookSol1IsPatternContainedInGrid(List<List<Integer>> grid, List<Integer> pattern) {
    for (int i = 0; i < grid.size(); ++i) {
      for (int j = 0; j < grid.get(i).size(); ++j) {
        if (isPatternSuffixContainedStartingAtXY(grid, i, j, pattern, /*offset=*/ 0, new HashSet<>())) {
          return true;
        }
      }
    }
    return false;
  }

  // Each entry in previousAttempt is a point in the grid and suffix of pattern
  // (identified by its offset). Presence in previousAttempts indicates the
  // suffix is not contained in the grid starting from that point.
  private static boolean isPatternSuffixContainedStartingAtXY(
          List<List<Integer>> grid, int x, int y, List<Integer> pattern, int offset,
          Set<Attempt> previousAttempts) {
    if (pattern.size() == offset) {
      // Nothing left to complete
      return true;
    }
    // Early return if (x, y) lies outside the grid or the character does not
    // match or we have already tried this combination.
    if (x < 0 || x >= grid.size() || y < 0 || y >= grid.get(x).size() ||
        previousAttempts.contains(new Attempt(x, y, offset)) ||
        !grid.get(x).get(y).equals(pattern.get(offset))) {
      return false;
    }

    for (List<Integer> nextXY: List.of(List.of(x - 1, y), List.of(x + 1, y), List.of(x, y - 1), List.of(x, y + 1))) {
      if (isPatternSuffixContainedStartingAtXY(grid, nextXY.get(0), nextXY.get(1), pattern, offset + 1, previousAttempts)) {
        return true;
      }
    }

    previousAttempts.add(new Attempt(x, y, offset));
    return false;
   }
}
