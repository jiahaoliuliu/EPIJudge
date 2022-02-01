package epi.chapter5.arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Giving an array of integers, sort the array such the even entries appears first
 * even % 2 = 0
 * You cannot use O(n) space
 */
public class Bootcamp {

    private int[] orderEvents(int[] array) {
        if(array.length == 0) return new int[]{};
        int leftPosition = 0;
        int rightPosition = array.length - 1;
        while (leftPosition < rightPosition) {
            // We found an even number on the left
            if (array[leftPosition] % 2 == 0) {
                leftPosition++;
            } else { // We found an odd number on the left
                while (array[rightPosition] % 2 == 1 && rightPosition > leftPosition) {
                    rightPosition--;
                }

                // If all the numbers on the right are odd, we have all the even numbers on the left
                if (rightPosition == leftPosition) {
                    return array;
                }

                // Swap the elements
                int tmp = array[rightPosition];
                array[rightPosition] = array[leftPosition];
                array[leftPosition] = tmp;

                leftPosition++;
                rightPosition--;
            }
        }

        return array;
    }

    @Test
    public void test() {
        // Given
        int[] data = new int[] {1,2,3,4,5,6};

        // When
        int[] result = orderEvents(data);

        // Then
        int[] expected = new int[] {6, 2, 4, 3, 5, 1 };
        assertEquals(expected.length, result.length);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

}
