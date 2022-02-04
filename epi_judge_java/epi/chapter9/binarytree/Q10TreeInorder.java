package epi.chapter9.binarytree;
import epi.BinaryTree;
import epi.BinaryTreeNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * The direct implementation of an inorder traversal using recursion has O(h) space complexity,
 * where h is the height of the tree. Recursion can be removed with an explicit stack, but the space
 * complexity remains O(h)
 *
 * Write a nonrecursive program for computing the inorder traversal sequence for a binary tree.
 * Assume nodes have parent fields.
 *
 * Hint: How can you tell whether a node is a left child or right child of its parent?
 *
 * Variant: How would you preform preorder and postorder traversals iteratively using O(1) additional space?
 * Your algorithm cannot modify the tree. Nodes have an explicit parent field.
 */
public class Q10TreeInorder {

  private static class NodeAndState {
    public BinaryTreeNode<Integer> node;
    public Boolean leftSubtreeTraversed;

    public NodeAndState(BinaryTreeNode<Integer> node) {
      this.node = node;
      this.leftSubtreeTraversed = false;
    }

    public NodeAndState(BinaryTreeNode<Integer> node,
                        Boolean leftSubtreeTraversed) {
      this.node = node;
      this.leftSubtreeTraversed = leftSubtreeTraversed;
    }
  }

  /**
   * Initial analysis: In order means: Left, root, right.
   *
   * We can use a data structure to store the data temporally.
   * We can use stack for this
   * Time complexity: O(k+n) where n is the number of nodes and leafs and k is the number of nodes which contains
   *    left node.
   *    This is because for each node that has left node, we need to push that node back along with the left node.
   * Space complexity: O(h), where h is the height of the tree
   *
   * @param tree
   * @return
   */
  @EpiTest(testDataFile = "tree_inorder.tsv")
  public static List<Integer> inorderTraversal(BinaryTreeNode<Integer> tree) {
    List<Integer> nodesList = new ArrayList<>();
    if (tree == null) return nodesList;
    // Use a stack?
    Deque<NodeAndState> stack = new ArrayDeque<>();
    stack.push(new NodeAndState(tree));
    while (!stack.isEmpty()) {
      // Current node cannot be null
      NodeAndState currentNodeAndState = stack.pop();
      // If the left node has not been visited
      if (!currentNodeAndState.leftSubtreeTraversed) {
        // 1. insert itself back to the stack
        // 2. insert the left node
        if (currentNodeAndState.node.left != null) {
          currentNodeAndState.leftSubtreeTraversed = true;
          stack.push(currentNodeAndState);
          stack.push(new NodeAndState(currentNodeAndState.node.left));
        } else { // But if the left node is empty (null)
          nodesList.add(currentNodeAndState.node.getData());
          // check the right node
          if (currentNodeAndState.node.getRight() != null) {
            stack.push(new NodeAndState(currentNodeAndState.node.getRight()));
          }
        }
      } else {
        nodesList.add(currentNodeAndState.node.getData());
        // check the right node
        if (currentNodeAndState.node.getRight() != null) {
          stack.push(new NodeAndState(currentNodeAndState.node.getRight()));
        }
      }
    }
    return nodesList;
  }

  /**
   * Book solution 1
   * Time complexity: O(n)
   * Space complexity: O(1)
   *
   * But all the nodes need to know who is their parent, so it makes the data a bit harder to maintain.
   *
   * @param args
   */
  public static List<Integer> inorderTraversal(BinaryTree<Integer> tree) {
    BinaryTree<Integer> prev = null, curr = tree;
    List<Integer> result = new ArrayList<>();

    while (curr != null) {
      BinaryTree<Integer> next;
      if (curr.parent == prev) {
        // We came down to curr from prev.
        if (curr.left != null) { // keep going left
          next = curr.left;
        } else {
          result.add(curr.data);
          // Done with left, so go right if right is not empty.
          // Otherwise, go up.
          next = (curr.right != null) ? curr.right : curr.parent;
        }
      } else if (curr.left == prev) {
        result.add(curr.data);
        // Done with left, so go right if right is not empty. Otherwise, go up
        next = (curr.right != null) ? curr.right : curr.parent;
      } else { // Done with both children, so move on
        next = curr.parent;
      }

      prev = curr;
      curr = next;
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
