package epi.chapter10.heaps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Write a program that takes a sequence of strings that you can't back up to read an earlier value.
 * - Your program must compute the k-longest strings in the sequence.
 * -- Store k strings. All the strings must be the one with longest length
 * -- The method used is adding the list of strings to a heap, and remove when needed.
 * -- The min-heap is efficient on removing the shortest one, that's why it is chosen
 *
 * Time complexity, per each string added
 * - it needs go to the leaf O(log k)
 * - and the heap size needs exceeds k, so the head need to removed. O(1)
 *
 * Given n strings, the final complexity will be O(n log k)
 *
 * Space complexity: O(k)
 *
 * Optimization: inserting string only if it is longer than the shortest one.
 *
 */
public class BootCamp {

    public static List<String> topK(int k, Iterator<String> iter) {
        PriorityQueue<String> minHeap = new PriorityQueue<>(
                k, (s1, s2) -> Integer.compare(s1.length(), s2.length())
        );
        while(iter.hasNext()) {
            minHeap.add(iter.next());
            if (minHeap.size() > k) {
                // Remove the shortest string (on the head). Note that the comparison function above
                // will order the strings by length.
                minHeap.poll();
            }
        }

        return new ArrayList<>(minHeap);
    }
}
