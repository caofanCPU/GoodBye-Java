package money.work.study.dachang;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    
    public static void main(String[] args) {
        
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
        preOrder(node.getLeft());
        preOrder(node.getRight());
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
        centerOrder(node.getRight());
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
        _centerOrder(node.getLeft());

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
        _postOrder(node.getRight());
        _postOrder(node.getLeft());
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
