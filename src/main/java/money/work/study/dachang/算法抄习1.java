package money.work.study.dachang;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.core.CollectionUtil;
import money.work.study.caofancpu.SuanFaHaHaHa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 第一次练习
 *
 * @author D8GER
 */
public class 算法抄习1 {

    public static List<Integer> traverseResultList = Lists.newArrayList();

    public static List<List<Integer>> levelTraverseResultList = Lists.newArrayList();

    // 二叉树基础遍历
    public static void main(String[] args) {
        二叉树基础遍历练习();
    }

    /**
     * 二叉树基础遍历
     * 递归遍历, 前中后, 反前反中反后, 都是5行代码, null就返回, 根据顺序, 根的地方添加到结果容器, 其他位置递归
     * 栈迭代遍历, 前中后, 都是~8行代码, 1个栈, 1个while循环, while循环条件是节点非null或者栈非空,
     * .    然后就是内部if节点非null, 非null节点首先入栈, 根的地方添加到结果容器, else条件出栈
     * .    栈迭代后序遍历, 利用栈迭代反_前序遍历, 最后将结果逆序局得到
     * 层次遍历, 所有、左视图、右视图、之字型, ~16行代码, 1个队列, 1个临时层次结果容器, 1个最终嵌套结果容器,
     * .    2层while循环, 外层循环就是遍历层, 条件就是队列非空, 内层循环首先获取当前队列大小, 再遍历
     * .    遍历时, poll出队列获取元素, 如果为null, continue跳过, 非null且left非null, offer入队列, 非null且right非null, offer入队列
     * .    然后将节点值加入临时层次结果容器, 左视图就是只有第一个元素while循环i == 1; 右视图就是只有最后一个元素while循环i == 队列size()
     * .    外层循环最后添加临时层次结果容器, 如果是zigzag, 就利用奇偶位运算+翻转临时层次结果容器解决
     */
    public static void 二叉树基础遍历练习() {
        SuanFaHaHaHa.TreeNode<Integer> root = new SuanFaHaHaHa.基础二叉树().loadTree();

        preOrder(root);
        System.out.println("前序遍历节点值" + CollectionUtil.show(traverseResultList));
        traverseResultList.clear();

        _postOrder(root);
        System.out.println("(反)_后序遍历节点值" + CollectionUtil.show(traverseResultList));
        System.out.println("(反)_后序遍历, 再翻转节点值" + CollectionUtil.showArray(reverseArray(traverseResultList.toArray())));
        traverseResultList.clear();

        stackPreOrder(root);
        System.out.println("栈前序遍历节点值" + CollectionUtil.show(traverseResultList));
        traverseResultList.clear();

        System.out.println();

        centerOrder(root);
        System.out.println("中序遍历节点值" + CollectionUtil.show(traverseResultList));
        traverseResultList.clear();

        _centerOrder(root);
        System.out.println("(反)_中序遍历节点值" + CollectionUtil.show(traverseResultList));
        System.out.println("(反)_中序遍历, 再翻转节点值" + CollectionUtil.showArray(reverseArray(traverseResultList.toArray())));
        traverseResultList.clear();

        stackCenterOrder(root);
        System.out.println("栈中序遍历节点值" + CollectionUtil.show(traverseResultList));
        traverseResultList.clear();

        System.out.println();

        postOrder(root);
        System.out.println("后序遍历节点值" + CollectionUtil.show(traverseResultList));
        traverseResultList.clear();

        _preOrder(root);
        System.out.println("(反)_前序遍历节点值" + CollectionUtil.show(traverseResultList));
        System.out.println("(反)_前序遍历, 再翻转节点值" + CollectionUtil.showArray(reverseArray(traverseResultList.toArray())));
        traverseResultList.clear();

        stackPostOrder(root);
        System.out.println("栈后序遍历节点值" + CollectionUtil.show(traverseResultList));
        traverseResultList.clear();

        System.out.println();

        levelOrder(root);
        System.out.println(SuanFaHaHaHa.基础二叉树.TraverseTypeEnum.LEVEL_ALL.getName() + "节点值" + CollectionUtil.show(levelTraverseResultList));
        levelTraverseResultList.clear();

        levelLeftOrder(root);
        System.out.println(SuanFaHaHaHa.基础二叉树.TraverseTypeEnum.LEVEL_LEFT.getName() + "节点值" + CollectionUtil.show(levelTraverseResultList));
        levelTraverseResultList.clear();

        levelRightOrder(root);
        System.out.println(SuanFaHaHaHa.基础二叉树.TraverseTypeEnum.LEVEL_RIGHT.getName() + "节点值" + CollectionUtil.show(levelTraverseResultList));
        levelTraverseResultList.clear();

        levelZigzagOrder(root);
        System.out.println(SuanFaHaHaHa.基础二叉树.TraverseTypeEnum.LEVEL_ZIGZAG.getName() + "节点值" + CollectionUtil.show(levelTraverseResultList));
        levelTraverseResultList.clear();

        System.out.println();
    }

    public static void preOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        // 根左右
        if (node == null) {
            return;
        }
        traverseResultList.add(node.getValue());
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    public static void postOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        // 左右根
        postOrder(node.getLeft());
        postOrder(node.getRight());
        traverseResultList.add(node.getValue());
    }

    public static void centerOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        // 左根右
        centerOrder(node.getLeft());
        traverseResultList.add(node.getValue());
        centerOrder(node.getRight());
    }

    public static void _preOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        // 前序=根左右, 反_前序=根右左
        traverseResultList.add(node.getValue());
        _preOrder(node.getRight());
        _preOrder(node.getLeft());
    }

    public static void _centerOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        // 中序=左根右, 反_中序=右根左
        _centerOrder(node.getRight());
        traverseResultList.add(node.getValue());
        _centerOrder(node.getLeft());
    }

    public static void _postOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        // 后序=左右根, 反_后序=右左根
        _postOrder(node.getRight());
        _postOrder(node.getLeft());
        traverseResultList.add(node.getValue());
    }

    public static void stackCenterOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        Stack<SuanFaHaHaHa.TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 中序=左根右
                node = node.getLeft();
            } else {
                SuanFaHaHaHa.TreeNode<Integer> pop = stack.pop();
                traverseResultList.add(pop.getValue());
                node = pop.getRight();
            }
        }
    }

    public static void stackPreOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        Stack<SuanFaHaHaHa.TreeNode<Integer>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 前序=根左右
                traverseResultList.add(node.getValue());
                node = node.getLeft();
            } else {
                SuanFaHaHaHa.TreeNode<Integer> pop = stack.pop();
                node = pop.getRight();
            }
        }
    }

    public static void stackPostOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        Stack<SuanFaHaHaHa.TreeNode<Integer>> stack = new Stack<>();
        // 后序=左右根 <==> 反_前序的逆序 <==> 根右左的逆序
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                // 根右左
                traverseResultList.add(node.getValue());
                node = node.getRight();
            } else {
                SuanFaHaHaHa.TreeNode<Integer> pop = stack.pop();
                node = pop.getLeft();
            }
        }
        Collections.reverse(traverseResultList);
    }

    public static void levelOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        // 层序遍历, 需要队列, 每一层获取即可
        Queue<SuanFaHaHaHa.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            List<Integer> currentLevelValueList = new ArrayList<>(currentLevelSize);
            int i = 0;
            while (i++ < currentLevelSize) {
                SuanFaHaHaHa.TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
                currentLevelValueList.add(poll.getValue());
            }
            levelTraverseResultList.add(currentLevelValueList);
        }
    }

    public static void levelLeftOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        Queue<SuanFaHaHaHa.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            List<Integer> currentLevelValueList = new ArrayList<>(currentLevelSize);
            int i = 0;
            // 每层第一个
            while (i++ < currentLevelSize) {
                SuanFaHaHaHa.TreeNode<Integer> poll = queue.poll();
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
                    currentLevelValueList.add(poll.getValue());
                }
            }
            levelTraverseResultList.add(currentLevelValueList);
        }
    }

    public static void levelRightOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        Queue<SuanFaHaHaHa.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            List<Integer> currentLevelValueList = new ArrayList<>(currentLevelSize);
            int i = 0;
            while (i++ < currentLevelSize) {
                SuanFaHaHaHa.TreeNode<Integer> poll = queue.poll();
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
                    currentLevelValueList.add(poll.getValue());
                }
            }
            levelTraverseResultList.add(currentLevelValueList);
        }
    }

    public static void levelZigzagOrder(SuanFaHaHaHa.TreeNode<Integer> node) {
        Queue<SuanFaHaHaHa.TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        int level = 0;
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            List<Integer> currentLevelValueList = new ArrayList<>(currentLevelSize);
            int i = 0;
            while (i++ < currentLevelSize) {
                SuanFaHaHaHa.TreeNode<Integer> poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.getLeft() != null) {
                    queue.offer(poll.getLeft());
                }
                if (poll.getRight() != null) {
                    queue.offer(poll.getRight());
                }
                currentLevelValueList.add(poll.getValue());
            }
            if ((level++ & 1) == 0) {
                // 偶数层逆序, 这样就和奇数层构成之字型
                Collections.reverse(currentLevelValueList);
            }
            levelTraverseResultList.add(currentLevelValueList);
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
