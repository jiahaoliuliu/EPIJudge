package epi.chapter5.arrays;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void testOrderEvents() {
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

    /**
     * Book solution 1
     */
    private static void evenOdd(List<Integer> myList) {
        int nextEven = 0;
        int nextOdd = myList.size() - 1;
        while (nextEven < nextOdd) {
            if (myList.get(nextEven) % 2 == 0) {
                nextEven ++;
            } else {
                Collections.swap(myList, nextEven, nextOdd--);
            }
        }
    }

    @Test
    public void testEvenOdd() {
        // Given
        List<Integer> data = new ArrayList<>(6);
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        data.add(6);

        // When
        evenOdd(data);

        // Then
        List<Integer> expected = new ArrayList<>(6);
        expected.add(6);
        expected.add(2);
        expected.add(4);
        expected.add(5);
        expected.add(3);
        expected.add(1);
        assertEquals(expected.size(), data.size());
        for (int i = 0; i < data.size(); i++) {
            assertEquals(expected.get(i), data.get(i));
        }
    }
}
