package epi.chapter7.linkedlist;
import epi.ListNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Consider a singly linked list whose nodes are numbered starting at 0.
 * Define the even-odd merge of the list to be the list consisting of the even-numbered
 * nodes followed by the odd-numbered nodes.
 * Write a program that computes the even-odd merge.
 *
 * Hint: Use temporary additional storage.
 */
public class Q10EvenOddListMerge {
  @EpiTest(testDataFile = "even_odd_list_merge.tsv")

  /**
   * First analysis:
   * - Brute force. Going through all the nodes
   * -- 1. Create an odd linked list
   * -- 2. For each one of the nodes
   * -- 2.1 If it is even, do nothing
   * -- 2.2 If it is odd, remove it and append it to the end of the odd linked list
   * -- 3. Until we reach to the end of the list
   * -- 4. Link the end of the list to the beginning of the even linked list
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static ListNode<Integer> evenOddMerge(ListNode<Integer> evenListNode) {
    // Special cases. If the list does not exist or it only contains one element
    // Return itself
    if (evenListNode == null || evenListNode.next == null) return evenListNode;
    ListNode<Integer> oddListNodeHead = null;// Starts with zero elements
    ListNode<Integer> oddListNodeTail = null;
    ListNode<Integer> runner = evenListNode;
    int currentNodeNumber = 1;
    // While it has not reached to the last node
    while(runner.next != null) {
      // If the next node is an odd node
      if (currentNodeNumber % 2 != 0) {
        // append it to the oddListNode
        // If the list has not been initialized
        if (oddListNodeHead == null) {
          oddListNodeHead = runner.next;
          oddListNodeTail = runner.next;
        } else {
          oddListNodeTail.next = runner.next;
          oddListNodeTail = oddListNodeTail.next;
        }
        // Remove it from the normal list
        runner.next = runner.next.next;
        oddListNodeTail.next = null;
      } else {
        runner = runner.next;
      }
      currentNodeNumber++;
    }
    runner.next = oddListNodeHead;
    return evenListNode;
  }

  @Test
  public void testEvenOddMerge1() {
    // Given
    ListNode<Integer> head = null;

    // When
    ListNode<Integer> result = evenOddMerge(head);

    // Then
    assertNull(result);
  }

  @Test
  public void testEvenOddMerge2() {
    // Given
    ListNode<Integer> head = new ListNode<>(0, null);

    // When
    ListNode<Integer> result = evenOddMerge(head);

    // Then
    ListNode<Integer> expected = new ListNode<>(0, null);
    assertEquals(expected, result);
  }

  @Test
  public void testEvenOddMerge3() {
    // Given
    ListNode<Integer> head = new ListNode<>(0, null);
    head.next = new ListNode<>(1, null);
    head.next.next = new ListNode<>(2, null);
    head.next.next.next = new ListNode<>(3, null);
    head.next.next.next.next = new ListNode<>(4, null);

    // When
    ListNode<Integer> result = evenOddMerge(head);

    // Then
    ListNode<Integer> expected = new ListNode<>(0, null);
    expected.next = new ListNode<>(2, null);
    expected.next.next = new ListNode<>(4, null);
    expected.next.next.next = new ListNode<>(1, null);
    expected.next.next.next.next = new ListNode<>(3, null);
    assertEquals(expected, result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "EvenOddListMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
