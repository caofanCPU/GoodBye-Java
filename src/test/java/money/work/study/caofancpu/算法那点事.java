package money.work.study.caofancpu;

import com.xyz.caofancpu.core.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import money.work.study.common.datastracture.Node;
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
        int[] arr = new int[]{3, 7, 6, 8, 3, 2};
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

    @Test
    public void lcs() {
        System.out.println(longCommonStr("danding", "danteng"));
    }

    /**
     * 最长公共子串, 构造二维表, 长>=宽, 短串匹配长串
     * .   a  b  c  d
     * .a  1  0  0  0
     * .b  0  2  0  0
     * .c  0  0  3  0
     */
    public static String longCommonStr(String shortA, String longB) {
        String none = "无公共子串";
        if (shortA == null || shortA.length() == 0 || longB == null || longB.length() == 0) {
            return none;
        }
        if (shortA.length() > longB.length()) {
            return longCommonStr(longB, shortA);
        }
        // 比对二维表, 短串为行, 长串为列, 横向矩形
        int[][] tab = new int[shortA.length()][longB.length()];
        // 重复字符数
        int repeatNum = 0;
        // 最后一个重复字符的索引
        int lastIndex = 0;
        for (int i = 0; i < shortA.length(); i++) {
            for (int j = 0; j < longB.length(); j++) {
                if (shortA.charAt(i) != longB.charAt(j)) {
                    tab[i][j] = 0;
                } else {
                    // 相等, 首位为1
                    if (i == 0 || j == 0) {
                        tab[i][j] = 1;
                    } else {
                        tab[i][j] = tab[i - 1][j - 1] + 1;
                    }
                }
                if (repeatNum < tab[i][j]) {
                    // 更新
                    repeatNum = tab[i][j];
                    lastIndex = i;
                }
            }
        }
        return repeatNum == 0 || (lastIndex + 1 < repeatNum) ? none : shortA.substring(lastIndex + 1 - repeatNum, lastIndex + 1);
    }


    public static int[][] circleEarth(int k, int n) {
        int[][] tab = new int[k + 1][n];
        tab[0][0] = 1;
        for (int i = 1; i < k; i++) {
            for (int j = 0; j < n; j++) {
                tab[i][j] = tab[i - 1][(j - 1 + n) % n] + tab[i - 1][(j + 1) % n];
            }
        }
        return tab;
    }

    private static int maxPath;

    public static int maxPathSum(TreeNode<Integer> node) {
        if (node == null) {
            return 0;
        }
        int left = maxPathSum(node.getLeft());
        int right = maxPathSum(node.getRight());
        maxPath = Math.max(maxPath, node.getValue() + left + right);
        return node.getValue() + Math.max(left, right);
    }

    private static boolean balance;
    private static int height;

    public static int balanceBinary(TreeNode<Integer> node) {
        if (node == null) {
            return 0;
        }
        int left = balanceBinary(node.getLeft()) + 1;
        int right = balanceBinary(node.getRight()) + 1;
        balance = Math.abs(left - right) <= 1;
        height = Math.max(left, right);
        return height;
    }

    public static Node<Integer> meet(Node<Integer> head) {
        if (head == null) {
            return null;
        }
        Node<Integer> fast = head;
        Node<Integer> slow = head;
        while (fast.getNext() != null && fast.getNext().getNext() != null && slow.getNext() != null) {
            fast = fast.getNext().getNext();
            slow = slow.getNext();
            if (fast == slow) {
                return fast;
            }
        }
        return null;
    }

    private static Node<Integer> entranceNode = null;
    public static int entranceNode(Node<Integer> head, Node<Integer> meet) {
        int entranceLength = 0;
        Node<Integer> slow = head;
        Node<Integer> fast = meet;
        while (slow.getNext() != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext();
            entranceLength++;
            if (slow == fast) {
                entranceNode = slow;
                break;
            }
        }
        return entranceLength;
    }

    public static int circleL(Node<Integer> meet) {
        int length = 0;
        Node<Integer> fast = meet;
        Node<Integer> slow = meet;
        while (fast.getNext() != null && fast.getNext().getNext() != null && slow.getNext() != null) {
            fast = fast.getNext().getNext();
            slow = slow.getNext();
            length++;
            if (fast == slow) {
                break;
            }
        }
        return length;
    }

    private static List<List<Integer>> result = new ArrayList<>();
    @Test
    public void combineSumN() {
        dfsSum(3, new ArrayList<>(), 1, 12);
        System.out.println(CollectionUtil.show(result));
    }

    private void dfsSum(int maxElementSize, List<Integer> tempList, int fromValue, int targetSum) {
        if (tempList.size() == maxElementSize || targetSum <= 0) {
            if (tempList.size() == maxElementSize && targetSum == 0) {
                // 恰好求和
                result.add(new ArrayList<>(tempList));
            }
            // 无法构成目标和, 返回
            return;
        }
        for (int i = fromValue; i <= 9; i++) {
            //选择当前值
            tempList.add(i);
            //递归, 拿下一个值去试
            dfsSum(maxElementSize, tempList, i + 1, targetSum - i);
            // 试用了的值, 要么满足, 要么不满足, 去掉该值
            tempList.remove(tempList.size() - 1);
        }
    }


}
