package epi.chapter13.sorting;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * A sorting algorithm based splitting the array into smaller subarrays
 *  the merge them together and sort them while merge
 * - MergeSort(arr[], l,  r)
 * 	- If r > l
 * 		- 1. Find the middle point to divide the array into two halves:
 * 			- middle m = l+ (r-l)/2
 * 		- 2. Call mergeSort for first half:
 * 			- Call mergeSort(arr, l, m)
 * 		- 3. Call mergeSort for second half:
 * 			- Call mergeSort(arr, m+1, r)
 * 		- 4. Merge the two halves sorted in step 2 and 3:
 * 			- Call merge(arr, l, m, r)
 * Time complexity O(n log n)
 * Space complexity O(n)
 */
public class MergeSort {

    /**
     * From https://www.geeksforgeeks.org/merge-sort/
     * Main function that sorts arr[l..r] using
     *  merge()
     * @param arr
     */
    public void mergeSort ( int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    void merge (int[] arr, int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int[] L = new int[n1];
        int[] R = new int[n2];

        /*Copy data to temp arrays*/
        System.arraycopy(arr, l, L, 0, n1);
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    @Test
    public void test1() {
        // Given
        int[] input = new int[] {2, 4, 7, 1, 3, 6, 5};

        // When
        mergeSort(input, 0, input.length - 1);

        // Then
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertArrayEquals(expected, input);
    }
}
