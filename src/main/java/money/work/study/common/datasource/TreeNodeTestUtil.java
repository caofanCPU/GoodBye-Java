package money.work.study.common.datasource;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.constant.SymbolConstantUtil;
import com.xyz.caofancpu.core.CollectionUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import money.work.study.common.datastracture.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 树节点工具类
 * 构建二叉树数据源, 分层次打印遍历结果, 方便调试
 *
 * @author D8GER
 */
@Data
@Accessors(chain = true)
public class TreeNodeTestUtil {
    public static TreeNode<Integer> root;
    /**
     * 原始节点值列表
     */
    public static List<Integer> originNodeValveList = Lists.newArrayList();

    /**
     * 深度遍历结果存储容器, 法号 备忘录
     */
    public static List<Integer> deepResult = new ArrayList<>();

    /**
     * 层次遍历结果存储容器
     */
    public static List<List<Integer>> levelResult = new ArrayList<>();

    public static Integer count = 0;

    /**
     * 打印二叉树
     */
    public static void showOriginNodeData() {
        // 计数器清零, 递归、迭代次数清零
        count = 0;
        root = null;
        originNodeValveList = new ArrayList<>();
        loadTree();
        System.out.println("原始二叉树节点数据: " + CollectionUtil.show(originNodeValveList));
    }

    /**
     * 清除数据
     */
    public static void clear() {
        count = 0;
        deepResult.clear();
        levelResult.clear();
    }

    /**
     * 栈调试
     */
    public static <T> String debuggerView(int n, String msg, List<T> source) {
        return view(n) + "第" + n + "次[" + msg + ", " + "-> UPDATE[" + CollectionUtil.show(source) + "]";
    }

    /**
     * tab递进, 很适合调试循环、递归
     */
    private static String view(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < n; i++) {
            result.append(SymbolConstantUtil.TAB);
        }
        return result.toString();
    }

    public static void deepAddValueAndPrintResult(String msg, TreeNode<Integer> node) {
        deepResult.add(node.getValue());
        System.out.println(debuggerView(++count, msg, deepResult));
    }

    public static void levelAddValueAndPrintResult(String msg, List<Integer> currentLevelList) {
        levelResult.add(currentLevelList);
        System.out.println(debuggerView(++count, msg, levelResult));
    }

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
            addNode(new TreeNode<Integer>(values[i]), root);
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

}
