package epi.chapter13.sorting;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * A simple sorting algorithm
 * The element 0 is considered sorted
 * - Repeat
 *      - for elements on the position 1 to the end of the array
 *      - insert the element of position i into the sorted array
 *      - Starting from the last element of the sorted array
 *          - if it is bigger, then stop
 *          - If it is smaller, check the position on the left of the last checked element
 *
 * Time complexity O(n^2)
 * Space complexity O(1)
 */
public class InsertSort {

    public void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            // Insert the element i into the subarray on its left side
            int elementToInsert = array[i];
            int j = i;
            while(j > 0 && elementToInsert < array[j - 1]) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = elementToInsert;
        }
    }

    @Test
    public void test1() {
        // Given
        int[] input = new int[] {2, 4, 7, 1, 3, 6, 5};

        // When
        insertSort(input);

        // Then
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertTrue(Arrays.equals(expected, input));
    }
}
