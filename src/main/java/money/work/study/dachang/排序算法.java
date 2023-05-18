package money.work.study.dachang;

import com.xyz.caofancpu.core.CollectionUtil;

import java.util.Stack;

/**
 * 排序算法有多种, 常见的快排、归并是高频
 * TODO: 堆排序、插入排序有一定取巧性质, 可以研究
 * 要点:
 * . 常见快排思路, 中轴分区排序, 选定一个元素作为参考元素, 将小值和大值分到中轴两边, 并且可以原地排序, 特性: 不稳定, 时间最好O(nlog•n), 时间最坏O(n2), 空间O(nlog•n)
 * . JDK内部#Arrays.sort()则是根据一些情况融合多种排序算法, 6P的人总是相似的
 * . 归并排序思路, 分治后再合并, 将源数据不断拆分, 然后再两两合并排序, 排序过程需要开辟新空间, 特性: 稳定, 时间都是O(nlog•n), 空间O(n)
 * 核心方法:
 * . 快排核心是中轴分区排序方法
 * . 归并核心是合并排序方法
 * 递归:
 * . 快排和归并, 都有一分为二的思路, 所以确定中轴值、确定区间, 递归即可
 * 非递归:
 * . 分析快排的递归, 就是只改变了区间值, 考虑用1个栈处理
 * . 分析归并的递归, 发现分治方法改变了区间值, 但是该区间值是为后面的合并方法做铺垫的, 所以用2个栈处理
 *
 * @author D8GER
 */
public class 排序算法 {
    // 递归或迭代次数, 方便调试
    private static int count = 0;

    /**
     * 快排调试
     */
    private static String fftView(int n, int start, int end, int[] source){
        String msg = "第" + n + "次[" + start + "|" + end + "]";
        return view(n) + msg + ", " + "-> UPDATE" + CollectionUtil.showArray(source);
    }

    /**
     * 归并调试
     */
    private static String mergeView(int n, int start, int midRoller, int end, int[] source) {
        String msg = "第" + n + "次[" + start + "|" + midRoller + "|" + end + "]";
        return view(n) + msg + ", " + "-> UPDATE" + CollectionUtil.showArray(source);
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

    private static void showOriginData(String msg, int[] source) {
        System.out.println(msg + CollectionUtil.showArray(source));
    }

    public static void main(String[] args){
        int[] source = initSource();
        // 之所以是3个参数, 一是递归需要, 二是提示可以对数组指定区间排序, 不一定非要全排, 三是形式统一
        showOriginData("递归快排", source);
        fftSort(source, 0, source.length - 1);
        // 还原数据
        source = initSource();

        showOriginData("栈迭代(非递归)快排", source);
        nonFFTSort(source, 0, source.length -1);
        source = initSource();

        showOriginData("递归归并", source);
        mergeSort(source, 0, source.length - 1);
        source = initSource();

        showOriginData("栈迭代(非递归)归并", source);
        nonMergeSort(source, 0, source.length - 1);
        source = initSource();
    }

    /**
     * 快排, 递归
     */
    public static void fftSort(int[] source, int start, int end) {
        if (start >= end) {
            // 递归结束条件
            return;
        }
        int midRoller = doFFTPartition(source, start, end);
        if (start < midRoller - 1) {
            // 这里的判断可以减少一层函数调用
            // 左边
            fftSort(source, start, midRoller - 1);
        }
        if (midRoller + 1 < end) {
            // 这里的判断可以减少一层函数调用
            // 右边
            fftSort(source, midRoller + 1, end);
        }
    }

    /**
     * 快排, 非递归, 用1个栈迭代处理
     * 分析递归方法, 排序核心是在{@link #doFFTPartition(int[], int, int)}完成的
     * 递归左边、递归右边这两个递归没有顺序依赖, 其递归本质只是改变了start, end参数值,
     * 把这两个值放入栈Stack(或其他容器如队列Queue)取出来即可
     */
    public static void nonFFTSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        stack.push(start);
        while (!stack.isEmpty()) {
            start = stack.pop();
            end = stack.pop();
            int midRoller = doFFTPartition(source, start, end);
            // 下一个左区间、右区间
            if (start < midRoller - 1) {
                stack.push(midRoller - 1);
                stack.push(start);
            }
            if (midRoller + 1 < end) {
                stack.push(end);
                stack.push(midRoller + 1);
            }
        }
    }

    /**
     * 快排算法核心, 分区排序
     * 原数组source, 排序的区间[start, end]
     * 返回中轴值midRoller
     */
    private static int doFFTPartition(int[] source, int start, int end) {
        // 首先选定参考值, 把数据分到中轴值两边, 小值在左, 大值在右
        int rv = source[start];
        // 首尾一起移动, 相遇的就是中轴
        while (start < end) {
            while (source[end] >= rv && end > start) {
                // end边的大值不需要动
                end--;
            }
            if (end != start) {
                // 到这说明end边的是小值, 要往start边移动
                source[start] = source[end];
            }

            // 想一想镜像对称性
            while (source[start] <= rv && start < end) {
                start++;
            }
            if (start != end) {
                source[end] = source[start];
            }
        }
        // 排完后, start就是中轴值的索引, 把中轴值rv还原
        source[start] = rv;
        // 返回中轴值
        System.out.println(fftView(++count, start, end, source));
        return start;
    }

    /**
     * 归并, 递归
     */
    public static void mergeSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        // 中轴
        int midRoller = (start + end) >> 1;
        if (start < midRoller) {
            // 左边递归拆分
            mergeSort(source, start, midRoller);
        }
        if (midRoller + 1 < end) {
            // 右边递归拆分
            mergeSort(source, midRoller + 1, end);
        }
        // 合并
        doMerge(source, start, midRoller, end);
    }

    /**
     * 归并, 非递归, 用2个栈迭代处理
     * 分析归并排序的递归方法, 两次递归拆分都只是改变了区间范围
     * 递归到最后的区间, 才开始依次执行合并, 而且两次递归没有顺序依赖关系
     * 重点: 因为前面的递归都是为了构建区间参数, 且这些参数还要被后续{@link #doMerge(int[], int, int, int)}使用
     * .    所以考虑用2个栈, 第1个栈是为了构建参数栈, 第2个栈就是为了执行后面的合并排序
     */
    public static void nonMergeSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        Stack<Integer> rangeStack = new Stack<>();
        rangeStack.push(end);
        rangeStack.push(start);
        Stack<Integer> mergeStack = new Stack<>();
        while (!rangeStack.isEmpty()) {
            // 构建区间
            start = rangeStack.pop();
            end = rangeStack.pop();
            // 该参数保存到mergeStack, 后续使用
            mergeStack.push(end);
            mergeStack.push(start);
            // 中轴值
            int midRoller = (start + end) >> 1;
            if (start < midRoller) {
                rangeStack.push(midRoller);
                rangeStack.push(start);
            }
            if (midRoller + 1 < end) {
                rangeStack.push(end);
                rangeStack.push(midRoller + 1);
            }
        }
        while (!mergeStack.isEmpty()) {
            start = mergeStack.pop();
            end = mergeStack.pop();
            // 中轴值, 分割两个区间
            int midRoller = (start + end) >> 1;
            doMerge(source, start, midRoller, end);
        }
    }

    /**
     * 归并算法核心, 合并排序
     * 原数组source, 合并有两个区间, [start, midRoller], [midRoller, end]
     */
    private static void doMerge(int[] source, int start, int midRoller, int end) {
        // 开辟要排序的新区间, 合并排序结果
        int[] temp = new int[end - start + 1];
        int i = start;
        int j = midRoller + 1;
        int k = 0;
        // 两个区间[i, midRoller]、[j, end]
        while (i <= midRoller && j <= end) {
            if (source[i] < source[j]) {
                // 复制小值
                temp[k++] = source[i++];
            } else {
                temp[k++] = source[i++];
            }
        }
        // 区间[i, midRoller]剩余元素
        while (i <= midRoller) {
            temp[k++] = source[i++];
        }

        // 区间[j, end]剩余元素
        while (j <= end) {
            temp[k++] = source[j++];
        }
        // 将结果复制到原数组
        System.arraycopy(temp, 0, source, start, temp.length);
        // 打印
        System.out.println(mergeView(++count, start, midRoller, end, source));
    }


    /**
     * 初始化数组数据
     */
    private static int[] initSource(){
        count = 0;
        return new int[]{3, 6, 1, 2, 4, 7, 9, 0, 5};
    }


}