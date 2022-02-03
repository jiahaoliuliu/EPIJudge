package epi.chapter8.stacksandqueues;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Queue insertion and deletion follows first-in, first-out semantics.
 * Stack insertion and deletion is last-in, first-out
 *
 * How would you implement a queue given a library implementing stack?
 *
 * Hint: It is impossible to solve this problem with a single stack
 */
public class Q8QueueFromStacks {

  /**
   * Initial analysis: It is not possible to implement it with 1 stack, but with 2 stacks
   * - s1: To enqueue
   * - s2: To dequeue
   *
   * To enqueue: push it to s1
   * To dequeue: if s2 is not empty, pull from s2
   *             if s2 is empty, extract all the elements from s1 to s2
   *             pull from s2
   */
  public static class Queue {
    private final Deque<Integer> stack1 = new ArrayDeque<>();
    private final Deque<Integer> stack2 = new ArrayDeque<>();

    public void enqueue(Integer x) {
      stack1.push(x);
    }

    public Integer dequeue() {
      if (stack2.isEmpty()) {
        if (stack1.isEmpty()) {
          throw new NoSuchElementException("The queue is empty");
        }

        while (!stack1.isEmpty()) {
          stack2.push(stack1.poll());
        }
      }
      return stack2.poll();
    }
  }
  @EpiUserType(ctorParams = {String.class, int.class})
  public static class QueueOp {
    public String op;
    public int arg;

    public QueueOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }
  }

  @EpiTest(testDataFile = "queue_from_stacks.tsv")
  public static void queueTester(List<QueueOp> ops) throws TestFailure {
    try {
      Queue q = new Queue();

      for (QueueOp op : ops) {
        switch (op.op) {
        case "QueueWithMax":
          q = new Queue();
          break;
        case "enqueue":
          q.enqueue(op.arg);
          break;
        case "dequeue":
          int result = q.dequeue();
          if (result != op.arg) {
            throw new TestFailure("Dequeue: expected " +
                                  String.valueOf(op.arg) + ", got " +
                                  String.valueOf(result));
          }
          break;
        }
      }
    } catch (NoSuchElementException e) {
      throw new TestFailure("Unexpected NoSuchElement exception");
    }
  }

  /**
   * Book solution 1
   * @param args
   */
  public static class BookSol1Queue {
    private Deque<Integer> enqueue = new ArrayDeque<>();
    private Deque<Integer> dequeue = new ArrayDeque<>();

    public void enqueue(Integer x) {
      enqueue.addFirst(x);
    }

    public Integer dequeue() {
      if (dequeue.isEmpty()) {
        // Transfers the elements from enqueue to dequeue
        while (!enqueue.isEmpty()) {
          dequeue.addFirst(enqueue.removeFirst());
        }
      }
      return dequeue.removeFirst();
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "QueueFromStacks.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
