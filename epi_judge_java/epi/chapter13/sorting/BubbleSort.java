package epi.chapter13.sorting;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * A simple sorting algorithm
 * Repeat: from the element 0 till end of the array
 *      Swap the biggest element to the end
 *
 * Bubble means the element that is "floating" to the surface (its corresponding position)
 * Only one bubble is floating each time.
 * Time complexity O(n^2)
 * Space complexity O(1)
 */
public class BubbleSort {

    public void bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j+1]) {
                    // Swap
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                }
            }
        }
    }

    @Test
    public void test1() {
        // Given
        int[] input = new int[] {2, 4, 7, 1, 3, 6, 5};

        // When
        bubbleSort(input);

        // Then
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertTrue(Arrays.equals(expected, input));
    }
}
