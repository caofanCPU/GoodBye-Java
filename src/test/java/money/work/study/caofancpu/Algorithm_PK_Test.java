package money.work.study.caofancpu;

import com.xyz.caofancpu.core.CollectionUtil;
import money.work.study.dachang.二叉树遍历算法;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 为什么算法常含泪水, 因为菜得真实
 * .
 * 快排关键点: 分区函数,
 * .    保存初始参考值rv
 * .    两层while循环, 首尾双指针
 * .    外层 start < end
 * .    内层end端while: [end] >= rv && end > start; end--
 * .                   end != start; [start] = [end]
 * .    内层start端while, 与内层start端while成镜像对称
 * .              [start] <= rv && start < end; start++
 * .              start ！= end; [end] = [start]
 * .    最后start就是中轴索引, 还原rv: [start] = rv
 * .    返回start
 * .    因为快排是选定中轴, 那么中轴确定后左右区间就是[start, mid - 1], [mid + 1, end]
 * 归排关键点: 合并函数
 * .    创建新数组temp, 用于存储[start, end]区间的排序结果
 * .    为与快排相对应, 多设置了索引区间中间值mid参数
 * .    mid = (start + end) >> 1; 可以看出mid是向下取整, 偏向于start端
 * .    所以拆分的两个区间是[start, mid], [mid + 1, end],
 * .
 * .    3个单层while循环, 3个循环控制变量 i = start; j = mid + 1; k =0
 * .    第一层: i <= mid && j <= end; [i] < [j]; [k++] = [i++] 否则 [k++] = [j++]
 * .         [i] < [j];是增序, [i] > [j];是降序, 处于形式考虑, 既然循环条件都是区间比较 < 那就记住 <
 * .    第二层: i <= mid; [k++] = [i++];
 * .    第三层: j <= end; [k++] = [j++];
 * .    3个while循环形式很统一的
 * 递归转迭代关键点:
 * 快排和归排在递归中都是改版了区间值, start, end不一样而已, 都会分左右两区间递归
 * .    快排因为排序是在分区中完成的, 所以1个栈即可
 * .    归排先是拆分区间, 到最后还需要使用来合并, 所以需要2个栈(1各栈也可以但是加上判断不易理解)
 * .    归排中两个栈, A栈记录递归区间功能和快排一致, B栈记录A栈每次出栈的结果, 相当于把A栈的进栈数据backup下, 用于后续合并使用
 * .
 * 二叉树16种遍历花样玩法
 * .    二叉树深度遍历(12种):
 * .                 基础3种: 前序(根左右)、中序(左根右)、后序(左右根), 3个元素的全排列应该是6种,
 * .                 于是引入变种: 反_前序(根右左)、反_中序(右根左)、反_后序(右左根)
 * .                 上述6种遍历可以分别使用递归、栈迭代消除递归, 就得到12种玩法
 * .        关键点:
 * .                 递归没啥好说的, 把'根'看做当前元素, 遍历就是不断打印'当前元素'
 * .                 栈迭代, while循环判断, 精髓在于while的判断条件: node != null || !stack.isEmpty()
 * .                 while循环内部, node != null; 节点不为空, 那么首先入栈, 接下来有烧脑环节:
 * .                 因为'根'就是当前元素, 所以对于前序、反_前序好理解, 遍历到节点, 就把节点值存起来
 * .                 但是对于中序、反_中序, 遍历到当前元素时不能打印值, 得存起来, 换其左节点递归;
 * .                 但是对于后序、反_后序, 遍历到当前元素时不能打印值, 得存起来, 换其左节点、右节点之后递归;
 * .                 所以选取前序作为参考点, 非空节点的if分支, 将节点入栈, 立马存值, 再node = node.getLeft();
 * .                                          其else分支, 出栈1个元素pop, node = pop.getRight();
 * .                 然后记忆式处理中序, 非空节点的if分支, 将节点入栈, 直接node = node.getLeft();
 * .                                          其else分支, 出栈1个元素pop, 出栈后存下pop的值, 再node = pop.getRight();
 * .                 最后处理后序, 利用后序 与 反_前序 互逆的关系, 反_前序, 就是把前序中的左右互换, 再把结果翻转, 就OK了
 * .    二叉树广度遍历(4种):
 * .                 按层依次从左到右遍历所有元素
 * .                 左视图
 * .                 右视图
 * .                 之字型(掌握这1种, 其他3种都是小菜)
 *
 * @author D8GER
 */
public class Algorithm_PK_Test {

    /**
     * 深度遍历结果存储容器, 法号 备忘录
     */
    private static List<Integer> deepResult = 二叉树遍历算法.deepResult;

    /**
     * 层次遍历结果存储容器
     */
    private static List<List<Integer>> levelResult = 二叉树遍历算法.levelResult;

    private static Integer count = 0;
    /**
     * 初始化数组数据
     */
    private static int[] initSource(){
        int[] source = {3, 6, 1, 2, 4, 7, 9, 0, 5};
        System.out.println("原始数组数据: " + CollectionUtil.showArray(source));
        return source;
    }

    private static void showSort(String msg, int[] source) {
        System.out.println(msg+ CollectionUtil.showArray(source));
    }

    private static void clear() {
        count = 0;
        deepResult = new ArrayList<>();
        levelResult = new ArrayList<>();
    }

    @Test
    public void No_Sort() {
        // 快排相关
        No_4();
        No_2();

        // 归排相关
        No_1();
        No_3();
    }

    @Test
    public void No_binarySearchTree() {
        // 前序相关
        No_5();
        clear();
        No_15();
        clear();
        No_9();
        clear();
        No_20();
        clear();

        // 中序相关
        No_7();
        clear();
        No_17();
        clear();
        No_10();
        clear();
        No_18();
        clear();

        // 后序相关
        No_6();
        clear();
        No_19();
        clear();
        No_8();
        clear();
        No_16();
        clear();

        // 层次相关
        No_12();
        clear();
        No_14();
        clear();
        No_13();
        clear();
        No_11();
        clear();
    }


    @Test
    public void No_1() {
        // 归并排序-递归版
        int[] source = initSource();
        _1_merge(source, 0, source.length - 1);
        showSort("递归归排结果数据", source);
    }

    private static void _1_merge(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) >> 1;
        if (start < mid) {
            _1_merge(source, start, mid);
        }
        if (mid + 1 < end) {
            _1_merge(source, mid + 1, end);
        }
        _1_doMerge(source, start, mid, end);
    }

    private static void _1_doMerge(int[] source, int start, int mid, int end) {
        if (start >= end) {
            return;
        }
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

    @Test
    public void No_2() {
        // 快排-非递归
        int[] source = initSource();
        _2_nonFFT(source, 0, source.length - 1);
        showSort("栈迭代快排结果数据", source);
    }

    private static void _2_nonFFT(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        int mid;
        stack.push(end);
        stack.push(start);
        while (!stack.isEmpty()) {
            start = stack.pop();
            end = stack.pop();
            mid = _2_doFFTPartition(source, start, end);
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

    private static int _2_doFFTPartition(int[] source, int start, int end) {
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

    @Test
    public void No_3() {
        // 归并-非递归
        int[] source = initSource();
        _3_nonMerge(source, 0, source.length - 1);
        showSort("栈迭代归排结果数据", source);
    }

    private static void _3_nonMerge(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid;
        // 先拆, 再合并
        Stack<Integer> splitStack = new Stack<>();
        Stack<Integer> mergeStack = new Stack<>();
        splitStack.push(end);
        splitStack.push(start);
        while (!splitStack.isEmpty()) {
            start = splitStack.pop();
            end = splitStack.pop();
            mergeStack.push(end);
            mergeStack.push(start);
            mid = (start + end) >> 1;

            if (start < end) {
                _1_doMerge(source, start, mid, end);
            }

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
            mid = (start + end) >> 1;
            _1_doMerge(source, start, mid, end);
        }
    }

    @Test
    public void No_4() {
        // 快排-递归
        int[] source = initSource();
        _4_fft(source, 0, source.length - 1);
        showSort("递归快排结果数据", source);
    }

    private static void _4_fft(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = _2_doFFTPartition(source, start, end);
        if (start < mid - 1) {
            _4_fft(source, start, mid - 1);
        }
        if (mid + 1 < end) {
            _4_fft(source, mid + 1, end);
        }
    }


    @Test
    public void No_5() {
        // 二叉树-前序, 递归
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _5_preOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _5_preOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        deepResult.add(node.getValue());
        System.out.println(二叉树遍历算法.debuggerView(++count, "递归前序遍历", deepResult));
        _5_preOrder(node.getLeft());
        _5_preOrder(node.getRight());
    }


    @Test
    public void No_6() {
        // 二叉树-后序, 递归
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _6_postOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _6_postOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        _6_postOrder(node.getLeft());
        _6_postOrder(node.getRight());
        deepResult.add(node.getValue());
        System.out.println(二叉树遍历算法.debuggerView(++count, "递归后序遍历", deepResult));
    }


    @Test
    public void No_7() {
        // 二叉树-中序, 递归
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _7_centerOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _7_centerOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        _7_centerOrder(node.getLeft());
        deepResult.add(node.getValue());
        System.out.println(二叉树遍历算法.debuggerView(++count, "递归中序遍历", deepResult));
        _7_centerOrder(node.getRight());
    }


    @Test
    public void No_8() {
        // 二叉树-反_前序, 递归
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _8_antiPreOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _8_antiPreOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        deepResult.add(node.getValue());
        System.out.println(二叉树遍历算法.debuggerView(++count, "递归反_前序遍历", deepResult));
        _8_antiPreOrder(node.getRight());
        _8_antiPreOrder(node.getLeft());
    }

    @Test
    public void No_9() {
        // 二叉树-反_后序, 递归
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _9_antiPostOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _9_antiPostOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        _9_antiPostOrder(node.getRight());
        _9_antiPostOrder(node.getLeft());
        deepResult.add(node.getValue());
        System.out.println(二叉树遍历算法.debuggerView(++count, "递归反_后序遍历", deepResult));
    }

    @Test
    public void No_10() {
        // 二叉树-反_中序, 递归
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _10_antiCenterOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _10_antiCenterOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        _10_antiCenterOrder(node.getRight());
        deepResult.add(node.getValue());
        System.out.println(二叉树遍历算法.debuggerView(++count, "递归反_中序遍历", deepResult));
        _10_antiCenterOrder(node.getLeft());
    }


    @Test
    public void No_11() {
        // 二叉树-ZigZag层次遍历, 非递归, 队列
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _11_levelZigZagOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _11_levelZigZagOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<二叉树遍历算法.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        int level = 0;
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                二叉树遍历算法.TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }

                clvList.add(poll.getValue());
            }
            if (((level++) & 1) == 0) {
                Collections.reverse(clvList);
            }
            levelResult.add(clvList);
            System.out.println(二叉树遍历算法.debuggerView(++count, "层次遍历-ZIGZAG", levelResult));
        }
    }

    @Test
    public void No_12() {
        // 二叉树-层次遍历, 非递归, 队列
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _12_levelOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _12_levelOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<二叉树遍历算法.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                二叉树遍历算法.TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }

                clvList.add(poll.getValue());
            }
            levelResult.add(clvList);
            System.out.println(二叉树遍历算法.debuggerView(++count, "层次遍历-ALL", levelResult));
        }
    }

    @Test
    public void No_13() {
        // 二叉树-层次遍历-右视图, 非递归, 队列
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _13_levelRightOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _13_levelRightOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<二叉树遍历算法.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                二叉树遍历算法.TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }

                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }

                if (i == clSize) {
                    clvList.add(poll.getValue());
                }
            }
            levelResult.add(clvList);
            System.out.println(二叉树遍历算法.debuggerView(++count, "层次遍历-RIGHT", levelResult));
        }
    }

    @Test
    public void No_14() {
        // 二叉树-层次遍历-左视图, 非递归, 队列
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _14_levelLeftOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _14_levelLeftOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<二叉树遍历算法.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int clSize = queue.size();
            List<Integer> clvList = new ArrayList<>(clSize);
            int i = 0;
            while (i++ < clSize) {
                二叉树遍历算法.TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }

                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }

                if (i == 1) {
                    clvList.add(poll.getValue());
                }
            }
            levelResult.add(clvList);
            System.out.println(二叉树遍历算法.debuggerView(++count, "层次遍历-LEFT", levelResult));
        }
    }


    @Test
    public void No_15() {
        // 二叉树-前序遍历, 非递归, 栈
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _15_nonPreOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _15_nonPreOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<二叉树遍历算法.TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                System.out.println(二叉树遍历算法.debuggerView(++count, "栈迭代前序遍历", deepResult));
                node = node.getLeft();
            } else {
                二叉树遍历算法.TreeNode<Integer> pop = stack.pop();
                node = pop.getRight();
            }
        }
    }

    @Test
    public void No_16() {
        // 二叉树-反_前序遍历, 非递归, 栈
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _16_nonAntiPreOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _16_nonAntiPreOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<二叉树遍历算法.TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                System.out.println(二叉树遍历算法.debuggerView(++count, "栈迭代反_前序遍历", deepResult));
                node = node.getRight();
            } else {
                二叉树遍历算法.TreeNode<Integer> pop = stack.pop();
                node = pop.getLeft();
            }
        }
    }

    @Test
    public void No_17() {
        // 二叉树-中序遍历, 非递归, 栈
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _17_nonCenterOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _17_nonCenterOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<二叉树遍历算法.TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.getLeft();
            } else {
                二叉树遍历算法.TreeNode<Integer> pop = stack.pop();
                deepResult.add(pop.getValue());
                System.out.println(二叉树遍历算法.debuggerView(++count, "栈迭代中序遍历", deepResult));
                node = pop.getRight();
            }
        }
    }

    @Test
    public void No_18() {
        // 二叉树-反_中序遍历, 非递归, 栈
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _18_nonAntiCenterOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _18_nonAntiCenterOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<二叉树遍历算法.TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.getRight();
            } else {
                二叉树遍历算法.TreeNode<Integer> pop = stack.pop();
                deepResult.add(pop.getValue());
                System.out.println(二叉树遍历算法.debuggerView(++count, "栈迭代反_中序遍历", deepResult));
                node = pop.getLeft();
            }
        }
    }

    @Test
    public void No_19() {
        // 二叉树-后序遍历, 非递归, 栈
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _19_nonPostOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _19_nonPostOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<二叉树遍历算法.TreeNode<Integer>> stack = new Stack<>();
        // 后序: 左右根 与反_前序: 根右左 互逆
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                System.out.println(二叉树遍历算法.debuggerView(++count, "栈迭代后序遍历", deepResult));
                node = node.getRight();
            } else {
                二叉树遍历算法.TreeNode<Integer> pop = stack.pop();
                node = pop.getLeft();
            }
        }
        Collections.reverse(deepResult);
        System.out.println(二叉树遍历算法.debuggerView(count, "栈迭代后序遍历最终结果", deepResult));
    }

    @Test
    public void No_20() {
        // 二叉树-反_后序遍历, 非递归, 栈
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        _20_nonAntiPostOrder(root);
        二叉树遍历算法.clear();
    }

    private static void _20_nonAntiPostOrder(二叉树遍历算法.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<二叉树遍历算法.TreeNode<Integer>> stack = new Stack<>();
        // 反_后序: 右左根 与 前序: 根左右 互逆
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                deepResult.add(node.getValue());
                System.out.println(二叉树遍历算法.debuggerView(++count, "栈迭代反_后序遍历", deepResult));
                node = node.getLeft();
            } else {
                二叉树遍历算法.TreeNode<Integer> pop = stack.pop();
                node = pop.getRight();
            }
        }
        Collections.reverse(deepResult);
        System.out.println(二叉树遍历算法.debuggerView(count, "栈迭代反_后序遍历最终结果", deepResult));
    }

    @Test
    public void No_二叉树最大路径和() {
        // 二叉树最大路径和, 递归
        二叉树遍历算法.showOriginNodeData();
        二叉树遍历算法.TreeNode<Integer> root = 二叉树遍历算法.TreeNodeTestUtil.root;
        dfs(root);
        System.out.println(maxPathSum);
    }

    private static int maxPathSum = 0;

    private static int dfs(二叉树遍历算法.TreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }
        int left = Math.max(0, dfs(root.getLeft()));
        int right = Math.max(0, dfs(root.getRight()));
        maxPathSum = Math.max(maxPathSum, root.getValue() + left + right);
        return root.getValue() + Math.max(left, right);
    }

}
