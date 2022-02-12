package epi.chapter13.sorting;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * A simple sorting algorithm
 * It is based on the linear search to find the smallest element in the array
 * and swap it with the first part
 * - UnsortedArrayFirstPosition = 0
 * - Repeat
 *      - for all positions from UnsortedArrayFirstPosition to end
 *          - find the smallest element
 *             - Linear search
 *          - Replace it with the UnsortedArrayFirstPosition
 *          - UnsortedArrayFirstPosition + 1 *
 * Time complexity O(n^2)
 * Space complexity O(1)
 */
public class SelectSort {

    public void selectSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int smallestElementPosition = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[smallestElementPosition]) {
                    smallestElementPosition = j;
                }
            }

            // Swap
            int tmp = array[i];
            array[i] = array[smallestElementPosition];
            array[smallestElementPosition] = tmp;
        }
    }

    @Test
    public void test1() {
        // Given
        int[] input = new int[] {2, 4, 7, 1, 3, 6, 5};

        // When
        selectSort(input);

        // Then
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertTrue(Arrays.equals(expected, input));
    }
}
