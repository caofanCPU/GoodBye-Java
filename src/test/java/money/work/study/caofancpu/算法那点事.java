package money.work.study.caofancpu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import money.work.study.dachang.二叉树遍历算法;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 2021-04-13, 备忘复习
 *
 * @author D8GER
 */
public class 算法那点事 {

    @Test
    public void fftTest() {
        int[] arr = new int[] {3,7, 6,8,3,2};
    }

    public static void fftSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = fftPartition(source, start, end);
        if (start < mid - 1) {
            fftSort(source, start, mid - 1);
        }
        if (mid + 1 < end) {
            fftSort(source, mid + 1, end);
        }
    }

    public static void stackFFTSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        stack.push(start);
        while (!stack.isEmpty()) {
            start = stack.pop();
            end = stack.pop();
            int mid = fftPartition(source, start, end);
            if (start < mid - 1) {
                stack.push(mid - 1);
                stack.push(start);
            }
            if (mid + 1 < end) {
                stack.push(end);
                stack.push(mid + 1);
            }
        }
    }

    public static int fftPartition(int[] source, int start, int end) {
        int rv = source[start];
        while (start < end) {
            while (source[end] >= rv && end > start) {
                end--;
            }
            if (end != start) {
                source[start] = source[end];
            }

            while (source[start] <= rv && start < end) {
                start++;
            }
            if (start != end) {
                source[end] = source[start];
            }
        }
        source[start] = rv;
        return start;
    }

    public static void mergeSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) >> 1;
        if (start < mid) {
            mergeSort(source, start, mid);
        }
        if (mid + 1 < end) {
            mergeSort(source, mid + 1, end);
        }
        merge(source, start, mid, end);
    }

    public static void stackMergeSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        Stack<Integer> splitStack = new Stack<>();
        Stack<Integer> mergeStack = new Stack<>();
        splitStack.push(end);
        splitStack.push(start);
        while (!splitStack.isEmpty()) {
            start = splitStack.pop();
            end = splitStack.pop();
            mergeStack.push(end);
            mergeStack.push(start);
            int mid = (start + end) >> 1;
            if (start < mid) {
                splitStack.push(mid);
                splitStack.push(start);
            }
            if (mid + 1 < end) {
                splitStack.push(end);
                splitStack.push(mid + 1);
            }
        }
        while (!mergeStack.isEmpty()) {
            start = mergeStack.pop();
            end = mergeStack.pop();
            int mid = (start + end) >> 2;
            merge(source, start, mid, end);
        }
    }

    public static void merge(int[] source, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end) {
            if (source[i] < source[j]) {
                temp[k++] = source[i++];
            } else {
                temp[k++] = source[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = source[i++];
        }

        while (j <= end) {
            temp[k++] = source[j++];
        }
        System.arraycopy(temp, 0, source, start, temp.length);
    }

    // 二叉树
    /**
     * 深度遍历结果存储容器, 法号 备忘录
     */
    public static List<Integer> deepResult = new ArrayList<>();

    /**
     * 层次遍历结果存储容器
     */
    public static List<List<Integer>> levelResult = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class TreeNode<T> {
        private T value;
        private TreeNode<T> left;
        private TreeNode<T> right;

        public TreeNode(T value) {
            this.value = value;
        }
    }

    public static void stackPreOrder(TreeNode<Integer> node) {
        // 根左右
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                node = node.getLeft();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getRight();
            }
        }
    }

    public static void stackCenterOrder(TreeNode<Integer> node) {
        // 左根右
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 左
                node = node.getLeft();
            } else {
                TreeNode<Integer> pop = stack.pop();
                // 中
                deepResult.add(pop.getValue());
                // 右
                node = pop.getRight();
            }
        }
    }

    public static void stackPostOrder(TreeNode<Integer> node) {
        // 左右根 ==> 根右左逆序 ==> 反前序逆序
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                node = node.getRight();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getLeft();
            }
        }
        Collections.reverse(deepResult);
    }

    public static void stack_preOrder(TreeNode<Integer> node) {
        // 根右左
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                node = node.getRight();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getLeft();
            }
        }
    }

    public static void stack_centerOrder(TreeNode<Integer> node) {
        // 右根左
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.getRight();
            } else {
                TreeNode<Integer> pop = stack.pop();
                deepResult.add(pop.getValue());
                node = pop.getLeft();
            }
        }
    }

    public static void stack_postOrder(TreeNode<Integer> node) {
        // 右左根 ==> 根左右逆序 ==> 前序逆序
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                node = node.getLeft();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getRight();
            }
        }
        Collections.reverse(deepResult);
    }

    public static void levelAllOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                clvList.add(poll.getValue());
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
            }
            levelResult.add(clvList);
        }
    }

    public static void levelLeftOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (i == 1) {
                    clvList.add(poll.getValue());
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
            }
            levelResult.add(clvList);
        }
    }

    public static void levelRightOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (i == clSize) {
                    clvList.add(poll.getValue());
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
            }
            levelResult.add(clvList);
        }
    }

    public static void levelZigZagOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        int level = 0;
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                clvList.add(poll.getValue());
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
            }
            if (((level++) & 1) == 0) {
                Collections.reverse(clvList);
            }
            levelResult.add(clvList);
        }
    }



}
