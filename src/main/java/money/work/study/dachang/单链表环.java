package money.work.study.dachang;

import money.work.study.common.datasource.NodeTestUtil;
import money.work.study.common.datastracture.Node;

import java.util.Objects;

/**
 * 单链表环的计算问题
 * 快慢指针法
 * .             |-----[y]----|
 * .             |            |
 * .             |            •(M)
 * •(H)---[x]----•(E)         |
 * .             |            |
 * .             |-----[z]----|
 * . 推导:
 * . 令环长L = y + z
 * . 第一次相遇时, 令慢指针走过的步长S = x + y, 快指针走过的步长2S = x + y + n • L
 * . 则: S = x + y = n • L ==> z = L - y; x = n • L - y
 * . 进而构建出入口节点长度EH(x), 环长L, 节点长度ME(z)的关系式:
 * . x = (n-1) • L + z
 * . 也就是说:
 * .    确定第一次相遇点M后, 速率一样的情况下: M到E等价于H到E, 这可以用于求出EH(x)
 * .    确定第一次相遇点M后, 慢指针、快指针再相遇一次, 可以用于求出环长L
 * .    确定第一次相遇点M后, EH(x) + 环长L 就可求出链表总长度 x + L
 * . 所以核心就是求出第一次相遇的节点M
 *
 * @author D8GER
 */
public class 单链表环 {
    private static Node<Integer> circleEntranceNode;

    public static void main(String[] args) {
        Node<Integer> head = NodeTestUtil.buildLinkedNodeList();
        Node<Integer> meetNode = corePositionEntrance(head);
        if (Objects.nonNull(meetNode)) {
            System.out.println("链表存在环, 相遇值为: " + meetNode.getData());
        } else {
            System.out.println("该链表没有环");
        }
        int circleLength = easyCircleLength(meetNode);
        int entranceLength = easyEntranceLength(head, meetNode);
        System.out.println("环的长度为: " + circleLength);
        System.out.println("入口环节点为: " + circleEntranceNode.getData());
        System.out.println("入口节点长度为: " + entranceLength);
        int nodeSize = circleLength + entranceLength;
        System.out.println("链表总长度为: " + nodeSize);
    }

    public static Node<Integer> corePositionEntrance(Node<Integer> head) {
        if (head == null) {
            return null;
        }
        // 快指针, 2倍速
        Node<Integer> fast = head;
        // 慢指针
        Node<Integer> slow = head;
        while (true) {
            if (fast.getNext() != null && fast.getNext().getNext() != null && slow.getNext() != null) {
                fast = fast.getNext().getNext();
                slow = slow.getNext();
            } else {
                return null;
            }
            if (fast == slow) {
                // 一旦相遇直接返回相遇的节点
                return fast;
            }
        }
    }

    public static int easyCircleLength(Node<Integer> meet) {
        int circleLength = 0;
        if (meet == null) {
            return circleLength;
        }
        Node<Integer> fast = meet;
        Node<Integer> slow = meet;
        while (true) {
            if (fast.getNext() != null && fast.getNext().getNext() != null && slow.getNext() != null) {
                fast = fast.getNext().getNext();
                slow = slow.getNext();
            }
            circleLength++;
            if (fast == slow) {
                return circleLength;
            }
        }
    }

    public static int easyEntranceLength(Node<Integer> head, Node<Integer> meet) {
        int entranceLength = 0;
        if (head == null || meet == null) {
            return entranceLength;
        }
        Node<Integer> slow = head;
        Node<Integer> fast = meet;
        while (true) {
            if (slow.getNext() != null && fast.getNext() != null) {
                slow = slow.getNext();
                fast = fast.getNext();
            }
            entranceLength++;
            if (slow == fast) {
                circleEntranceNode = fast;
                return entranceLength;
            }
        }
    }

}
