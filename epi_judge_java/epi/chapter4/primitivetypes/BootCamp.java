package epi.chapter4.primitivetypes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Write a program to count the number of bits that are set to 1
 */
public class BootCamp {

    private int numberOfBitsSetTo1(int positiveNumber){
        if (positiveNumber < 0) throw new IllegalArgumentException("The number must be bigger or equal than zero");
        int result = 0;
        while (positiveNumber > 0) {
            int isSetTo1 = positiveNumber & 1;
            if (isSetTo1 > 0) {
                result++;
            }
            positiveNumber = positiveNumber >> 1; // It should be >>>
        }

        return result;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberOfBitsSetTo11() {
        // Given
        int number = -1;

        // When
        numberOfBitsSetTo1(number);

        // Then
    }

    @Test
    public void testNumberOfBitsSetTo12() {
        // Given
        int number = 0;

        // When
        int result = numberOfBitsSetTo1(number);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void testNumberOfBitsSetTo13() {
        // Given
        int number = 1;

        // When
        int result = numberOfBitsSetTo1(number);

        // Then
        assertEquals(1, result);
    }

    @Test
    public void testNumberOfBitsSetTo14() {
        // Given
        int number = 2;

        // When
        int result = numberOfBitsSetTo1(number);

        // Then
        assertEquals(1, result);
    }

    @Test
    public void testNumberOfBitsSetTo15() {
        // Given
        int number = 4;

        // When
        int result = numberOfBitsSetTo1(number);

        // Then
        assertEquals(1, result);
    }

    @Test
    public void testNumberOfBitsSetTo16() {
        // Given
        int number = 5;

        // When
        int result = numberOfBitsSetTo1(number);

        // Then
        assertEquals(2, result);
    }

    /**
     * Book solution 1
     */
    private short bookSol1CountBits(int x) {
        short numberBits = 0;
        while (x != 0) {
            numberBits += (x & 1);
            x >>>= 1;
        }
        return numberBits;
    }

    @Test
    public void testBookSol1CountBits1() {
        // Given
        int number = 0;

        // When
        int result = bookSol1CountBits(number);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void testBookSol1CountBits2() {
        // Given
        int number = 1;

        // When
        int result = bookSol1CountBits(number);

        // Then
        assertEquals(1, result);
    }

    @Test
    public void testBookSol1CountBits3() {
        // Given
        int number = 2;

        // When
        int result = bookSol1CountBits(number);

        // Then
        assertEquals(1, result);
    }

    @Test
    public void testBookSol1CountBits4() {
        // Given
        int number = 4;

        // When
        int result = bookSol1CountBits(number);

        // Then
        assertEquals(1, result);
    }

    @Test
    public void testBookSol1CountBits5() {
        // Given
        int number = 5;

        // When
        int result = bookSol1CountBits(number);

        // Then
        assertEquals(2, result);
    }

}
