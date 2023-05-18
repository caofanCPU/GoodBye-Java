package money.work.study.common.datasource;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.core.CollectionUtil;
import money.work.study.common.datastracture.Node;

import java.util.List;

/**
 * 链表工具类
 *
 * @author D8GER
 */
public class NodeTestUtil {
    /**
     * 头结点
     */
    public static Node<Integer> head;

    /**
     * 原始节点值列表
     */
    public static List<Integer> originNodeValveList = Lists.newArrayList();

    private NodeTestUtil() {

    }

    /**
     * ⓪①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
     * .   ①  -->  ④  -->  ③  -->  ⑤
     * .                    |        |
     * .                    |        |
     * .                    ⑨  <--  ②
     * @return
     */
    public static Node<Integer> buildLinkedNodeList() {
        head = new Node<>(1);
        Node<Integer> four = new Node<>(4);
        Node<Integer> three = new Node<>(3);
        Node<Integer> five = new Node<>(5);
        Node<Integer> two = new Node<>(2);
        Node<Integer> nine = new Node<>(9);
        head.setNext(four);
        four.setNext(three);
        three.setNext(five);
        five.setNext(two);
        two.setNext(nine);
        nine.setNext(three);
        showNodeList();
        return head;
    }

    public static void showNodeList() {
        Node<Integer> current = head;
        int i = 0;
        while (current != null && i++ < 6) {
            originNodeValveList.add(current.getData());
            current = current.getNext();
        }
        System.out.println(
                ".   ①  -->  ④  -->  ③  -->  ⑤\n" +
                ".                    |        |\n" +
                ".                    |        |\n" +
                ".                    ⑨  <--  ②");
        System.out.println(CollectionUtil.show(originNodeValveList));
    }
}
