package epi.chapter13.sorting;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * A sorting algorithm based on heap (min, max), which is a binary tree
 * - The root element is the smallest or the biggest element
 *
 * - Repeat
 *      - from element of position 0 to the last element
 * 	    - Insert the element into the binary heap tree
 * 		    - Insert one by one, left to right (per level)
 * 			    - sort if needed
 * 				    - The child need to be smaller than the parent
 * - Repeat
 * 	    - from the root of the tree till the last element
 * 		    - Extract the root
 * 			    - Insert it to the first position available in the array.
 * 		    - Replace the root by the first leaf, from right to left
 * 		    - If the leaf is bigger than any of the child nodes
 *   			- replace the root by the biggest child node
 * Time complexity of heapify is O(Log n). Time complexity of createAndBuildHeap()
 *      is O(n) and the overall time complexity of Heap Sort is O(nLogn).
 * Space complexity O(1) because it could be arranged in place using the same array
 */
public class HeapSort {

    /**
     * From https://www.geeksforgeeks.org/heap-sort/
     * @param arr
     */
    public void heapSort(int arr[]) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    void heapify(int arr[], int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    @Test
    public void test1() {
        // Given
        int[] input = new int[] {2, 4, 7, 1, 3, 6, 5};

        // When
        heapSort(input);

        // Then
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertTrue(Arrays.equals(expected, input));
    }
}
