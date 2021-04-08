package money.work.study.dachang;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.core.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树遍历, 递归和非递归有助于思考用栈替换递归, 以及探明递归究竟在干啥
 * . 深度遍历, (前序, 反_后序)互逆, (中序, 反_中序)互逆, (后序, 反_前序)互逆,
 * .    前序遍历, 递归和非递归
 * .    反_后序遍历, 递归和非递归
 * .    中序遍历, 递归和非递归
 * .    反_中序遍历, 递归和非递归
 * .    后序遍历, 递归和非递归
 * .    反_前序遍历, 递归和非递归
 * . 层次遍历
 * .    全部元素层次遍历, 非递归
 * .    部分元素层次遍历, 左视图、右视图、根俯视图(搞心态的), 非递归
 * .    花样玩法, 之字型遍历, 非递归
 * 要点:
 * . 从深度遍历中可以发现, 某些数据操作在两次递归之前, 在两次递归之间, 在两次递归之后
 * . 对于这种情况, 考虑用1个或多个栈来替代递归, 技巧: 抄3遍+默写4遍就有感觉了
 *
 * @author D8GER
 */
public class 二叉树遍历算法 {
    
    /**
     * 深度遍历结果存储容器, 法号 备忘录
     */
    private static List<Integer> deepResult = new ArrayList<>();

    /**
     * 层次遍历结果存储容器
     */
    private static List<List<Integer>> levelResult = new ArrayList<>();

    private static int count = 0;
    
    public static void main(String[] args) {
        showOriginNodeData();
        TreeNode<Integer> root = TreeNodeTestUtil.root;
        preOrder(root);
        clear();

        stackPreOrder(root);
        clear();

        _postOrder(root);
        clear();

        stack_PostOrder(root);
        clear();

        showOriginNodeData();
        centerOrder(root);
        clear();

        stackCenterOrder(root);
        clear();

        _centerOrder(root);
        clear();

        stack_CenterOrder(root);
        clear();

        showOriginNodeData();
        postOrder(root);
        clear();

        stackPostOrder(root);
        clear();

        _preOrder(root);
        clear();

        stack_PreOrder(root);
        clear();

        showOriginNodeData();
        levelOrder(root);
        clear();

        levelLeftOrder(root);
        clear();

        levelRightOrder(root);
        clear();

        levelZigZagOrder(root);
        clear();


    }

    private static void showOriginNodeData() {
        // 计数器清零, 递归、迭代次数清零
        count = 0;
        TreeNodeTestUtil.root = null;
        TreeNodeTestUtil.originNodeValveList = new ArrayList<>();
        TreeNodeTestUtil.loadTree();
        System.out.println("原始二叉树节点数据: " + CollectionUtil.show(TreeNodeTestUtil.originNodeValveList));
    }

    private static void clear() {
        count = 0;
        deepResult.clear();
        levelResult.clear();
    }

    /**
     * 栈调试
     */
    private static <T> String debuggerView(int n, String msg, List<T> source) {
        return view(n) + "第" + n + "次[" + msg + ", " + "-> UPDATE[" + CollectionUtil.show(source) + "]";
    }

    /**
     * tab递进, 很适合调试循环、递归
     */
    private static String view(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < n; i++) {
            result.append("    ");
        }
        return result.toString();
    }

    /**
     * 前序遍历, 5行代码: 根->左->右
     * 根, 就是看做当前, 遍历就是收集当前值
     * 左, 就是取左节点再遍历
     * 右, 就是取右节点再遍历
     */
    public static void preOrder(TreeNode<Integer> node) {
        if (node == null) {
            // 递归结束条件
            return;
        }
        deepResult.add(node.getValue());
        System.out.println(debuggerView(++count, "递归前序遍历", deepResult));
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    /**
     * 前序遍历, 非递归, 直接用栈代替递归
     * 分析前序递归形式的代码, 添加节点是在两次递归之前
     * 其递归添加形式: 当前节点->当前节点的左节点->当前节点的右节点
     */
    public static void stackPreOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                // 添加节点
                stack.push(node);
                // 前序: 根左右
                deepResult.add(node.getValue());
                System.out.println(debuggerView(++count, "栈迭代前序遍历", deepResult));
                // 取左节点
                node = node.getLeft();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getRight();
            }
        }
    }

    /**
     * 反_后序遍历, 5行代码: 右->左->根
     */
    public static void _postOrder(TreeNode<Integer> node) {
        if (node == null) {
            // 递归结束条件
            return;
        }
        _postOrder(node.getRight());
        _postOrder(node.getLeft());
        deepResult.add(node.getValue());
        System.out.println(debuggerView(++count, "递归反_后序遍历", deepResult));
    }

    public static void stack_PostOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 反_后序遍历: 右左根 ==> 根左右=前序, 逆序
                // 取右节点
                deepResult.add(node.getValue());
                System.out.println(debuggerView(++count, "栈迭代反_后序遍历", deepResult));
                node = node.getLeft();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getRight();
            }
        }
        Collections.reverse(deepResult);
        System.out.println(debuggerView(count, "(最终)栈迭代反_后序遍历", deepResult));
    }

    /**
     * 中序遍历, 5行代码: 左->根->右
     */
    public static void centerOrder(TreeNode<Integer> node) {
        if (node == null) {
            // 递归结束条件
            return;
        }
        centerOrder(node.getLeft());
        deepResult.add(node.getValue());
        System.out.println(debuggerView(++count, "递归中序遍历", deepResult));
        centerOrder(node.getRight());
    }

    /**
     * 中序遍历, 非递归, 直接用栈代替递归
     * 分析前序递归形式的代码, 添加节点是在两次递归中间
     * 其递归添加形式: 当前节点的左节点->当前节点->当前节点的右节点
     */
    public static void stackCenterOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                // 添加节点
                stack.push(node);
                // 中序: 左根右
                // 取左节点
                node = node.getLeft();
            } else {
                TreeNode<Integer> pop = stack.pop();
                deepResult.add(pop.getValue());
                System.out.println(debuggerView(++count, "栈迭代中序遍历", deepResult));
                node = pop.getRight();
            }
        }
    }

    /**
     * 反_中序遍历, 5行代码: 右->根->左
     */
    public static void _centerOrder(TreeNode<Integer> node) {
        if (node == null) {
            // 递归结束条件
            return;
        }
        _centerOrder(node.getRight());
        deepResult.add(node.getValue());
        System.out.println(debuggerView(++count, "递归反_中序遍历", deepResult));
        _centerOrder(node.getLeft());

    }

    public static void stack_CenterOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 反_中序遍历: 右根左
                // 取右节点
                node = node.getRight();
            } else {
                TreeNode<Integer> pop = stack.pop();
                deepResult.add(pop.getValue());
                System.out.println(debuggerView(++count, "栈迭代反_中序遍历", deepResult));
                node = pop.getLeft();
            }
        }
    }

    /**
     * 后序遍历, 5行代码: 左->右->根
     */
    public static void postOrder(TreeNode<Integer> node) {
        if (node == null) {
            // 递归结束条件
            return;
        }
        postOrder(node.getLeft());
        postOrder(node.getRight());
        deepResult.add(node.getValue());
        System.out.println(debuggerView(++count, "递归后序遍历", deepResult));
    }

    public static void stackPostOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 后序遍历: 右左根
                // 取右节点
                deepResult.add(node.getValue());
                System.out.println(debuggerView(++count, "栈迭代后序遍历", deepResult));
                node = node.getRight();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getLeft();
            }
        }
        // 逆序
        Collections.reverse(deepResult);
        System.out.println(debuggerView(count, "(最终)栈迭代后序遍历", deepResult));
    }

    /**
     * 反_前序遍历, 5行代码: 根->右->左
     */
    public static void _preOrder(TreeNode<Integer> node) {
        if (node == null) {
            // 递归结束条件
            return;
        }
        deepResult.add(node.getValue());
        System.out.println(debuggerView(++count, "递归反_前序遍历", deepResult));
        _preOrder(node.getRight());
        _preOrder(node.getLeft());
    }

    public static void stack_PreOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 反_前序遍历: 根右左
                // 取右节点
                deepResult.add(node.getValue());
                System.out.println(debuggerView(++count, "栈迭代反_前序遍历", deepResult));
                node = node.getRight();
            } else {
                TreeNode<Integer> pop = stack.pop();
                node = pop.getLeft();
            }
        }
    }

    public static void levelOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            List<Integer> currentLevelNodeValueList = new ArrayList<>(currentLevelSize);
            // 循环遍历
            int i = 0;
            while (i++ < currentLevelSize) {
                TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
                currentLevelNodeValueList.add(poll.getValue());
            }
            levelResult.add(currentLevelNodeValueList);
            System.out.println(debuggerView(++count, "层次遍历-ALL", levelResult));
        }
    }

    public static void levelLeftOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            List<Integer> currentLevelNodeValueList = new ArrayList<>(currentLevelSize);
            // 循环遍历
            int i = 0;
            while (i++ < currentLevelSize) {
                TreeNode<Integer> poll = queue.poll();
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
                    // 左视图: 第一次, 首个元素
                    currentLevelNodeValueList.add(poll.getValue());
                }
            }
            levelResult.add(currentLevelNodeValueList);
            System.out.println(debuggerView(++count, "层次遍历-LEFT", levelResult));
        }
    }

    public static void levelRightOrder(TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            List<Integer> currentLevelNodeValueList = new ArrayList<>(currentLevelSize);
            // 循环遍历
            int i = 0;
            while (i++ < currentLevelSize) {
                TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
                if (i == currentLevelSize) {
                    // 右视图: 最后一个元素
                    currentLevelNodeValueList.add(poll.getValue());
                }
            }
            levelResult.add(currentLevelNodeValueList);
            System.out.println(debuggerView(++count, "层次遍历-RIGHT", levelResult));
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
            int currentLevelSize = queue.size();
            List<Integer> currentLevelNodeValueList = new ArrayList<>(currentLevelSize);
            // 循环遍历
            int i = 0;
            while (i++ < currentLevelSize) {
                TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
                currentLevelNodeValueList.add(poll.getValue());
            }
            if (((level++) & 1) == 0) {
                // 反序
                Collections.reverse(currentLevelNodeValueList);
            }
            levelResult.add(currentLevelNodeValueList);
            System.out.println(debuggerView(++count, "层次遍历-ZIGZAG", levelResult));
        }
    }


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

    /**
     * 树节点工具类
     * 构建二叉树数据源
     */
    @Data
    @Accessors(chain = true)
    public static class TreeNodeTestUtil {
        private static TreeNode<Integer> root;
        /**
         * 原始节点值列表
         */
        private static List<Integer> originNodeValveList = Lists.newArrayList();

        private TreeNodeTestUtil() {

        }

        public static TreeNode<Integer> loadTree() {
            buildTree(new int[]{5, 3, 7, 1, 4, 6, 8, 0, 2, 9});
            return root;
        }

        /**
         * ⓪①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
         * 层次遍历结果: 5,3,7,1,4,6,8,0,2,9
         * .             ⑤
         * .       ③••••••••••⑦
         * .    ①••••④••••⑥••••⑧
         * . ⓪••••②•••••••••••••••⑨
         */
        public static TreeNode<Integer> buildTree(int[] values) {
            root = new TreeNode<>(values[0]);
            originNodeValveList.add(root.getValue());
            for (int i = 1; i < values.length; i++) {
                originNodeValveList.add(values[i]);
                addNode(new TreeNode<>(values[i]), root);
            }
            System.out.println(
                    "                      ⑤\n" +
                            "                ③••••••••••⑦\n" +
                            "             ①••••④••••⑥••••⑧\n" +
                            "          ⓪••••②•••••••••••••••⑨"
            );
            return root;
        }

        /**
         * ⓪①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
         * .                     ⑩
         * .                   ⑨••⑪
         * .                 ⑧••••••⑫
         * .               ⑦••••••••••⑬
         * .             ⑥••••••••••••••⑭
         * .           ⑤••••••••••••••••••⑮
         * .         ④••••••••••••••••••••••⑯
         * .       ③••••••••••••••••••••••••••⑰
         * .     ②••••••••••••••••••••••••••••••⑱
         * .   ①••••••••••••••••••••••••••••••••••⑲
         * . ⓪••••••••••••••••••••••••••••••••••••••⑳
         */
        public static void build8Tree(int rootValue) {
            root = new TreeNode<>(rootValue);
            originNodeValveList.add(root.getValue());
            for (int i = rootValue - 1; i >= 0; i--) {
                originNodeValveList.add(i);
                addNode(new TreeNode<>(i), root);
            }
            for (int i = rootValue + 1; i <= 2 * rootValue; i++) {
                originNodeValveList.add(i);
                addNode(new TreeNode<>(i), root);
            }
        }

        public static void addNode(TreeNode<Integer> current, TreeNode<Integer> refer) {
            if (Objects.isNull(current) || Objects.isNull(refer)) {
                return;
            }
            if (current.getValue() <= refer.getValue()) {
                if (Objects.isNull(refer.getLeft())) {
                    refer.setLeft(current);
                } else {
                    addNode(current, refer.getLeft());
                }
            } else {
                if (Objects.isNull(refer.getRight())) {
                    refer.setRight(current);
                } else {
                    addNode(current, refer.getRight());
                }
            }
        }

        public static Object[] reverseArray(Object[] array) {
            int i = 0;
            int j = array.length - 1;
            Object tmp;

            while (j > i) {
                tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j--;
                i++;
            }
            return array;
        }
    }
    

}
