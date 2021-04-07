package money.work.study.dachang;

import com.xyz.caofancpu.annotation.WarnDoc;
import com.xyz.caofancpu.core.CollectionUtil;
import org.springframework.util.StopWatch;

import java.util.Stack;

/**
 * 为什么眼里常含泪水, 因为菜得深沉
 * https://segmentfault.com/a/1190000023647227
 */
public class USB {
    private static final StopWatch watch = new StopWatch("ByteCry");

    private static int count = 0;

    public static void main(String[] args) {
        int[] source = initSource();
        myNonRecursionMergeSort(source, 0, source.length - 1);
        out("[X]栈迭代归并", source);
        source = initSource();
        System.out.println();

        recursionMergeSort(source, 0, source.length - 1);
        out("递归归并", source);
        source = initSource();
        System.out.println();

        hisNonRecursionMergerSort(source, 0, source.length - 1);
        out("迭代归并", source);
        source = initSource();
//
//        recursionFFTSort(source, 0, source.length - 1);
//        out("递归快排", source);
//        source = initSource();
//
//        nonRecursionFFTSort(source, 0, source.length - 1);
//        out("栈迭代快排", source);
//        source = initSource();

    }

    public static int[] initSource() {
        count = 0;
        return new int[]{3, 6, 1, 2, 4, 7, 9};
    }

    public static void out(String msg, int[] arr) {
        System.out.println(msg + ": " + CollectionUtil.showArray(arr));
    }

    public static void nonRecursionFFTSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        stack.push(start);
        while (!stack.isEmpty()) {
            start = stack.pop();
            end = stack.pop();
//            int midRoller = doFFTPartition(source, start, end);
            int midRoller = _doNonRecursionFFTPartition(source, start, end);
            if (start < midRoller - 1) {
                stack.push(midRoller - 1);
                stack.push(start);
            }
            if (end > midRoller + 1) {
                stack.push(end);
                stack.push(midRoller + 1);
            }
        }
    }

    public static int _doNonRecursionFFTPartition(int[] source, int start, int end) {
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

    /**
     * 递归主函数5行代码
     * 将数组分为两边, 分别排序
     * 需要确定中轴函数
     * 递归调用自身, 参数要排序的数组源, 排序的起点, 排序的终点
     *
     * @param source
     * @param start
     * @param end
     */
    public static void recursionFFTSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int midRoller = doFFTPartition(source, start, end);
        recursionFFTSort(source, start, midRoller - 1);
        recursionFFTSort(source, midRoller + 1, end);
    }

    /**
     * 分区及排序
     * 12行代码, 两层3个while循环
     * 保存start元素作为比较参考值rV
     * 外层 start < end
     * 内层end循环, [end] >= rV && end > start, 那就移动 end--
     * .    不满足, 判断end != start, 二者还未相遇, 那就用[end]覆盖[start]
     * 内层start循环, [start] <= rv && start < end, 那就移动 start++
     * .    不满足, 判断start != end, 二者还未相遇, 那就用[start]覆盖[end]
     * 最后start的位置就是中轴的位置, 参考值就在中间, 还原到原数组中去, [start] = rv
     *
     * @param source
     * @param start
     * @param end
     * @return
     */
    private static int doFFTPartition(int[] source, int start, int end) {
        int referredValue = source[start];
        while (start < end) {
            // 从end处移动
            while (source[end] >= referredValue && end > start) {
                end--;
            }
            if (end != start) {
                source[start] = source[end];
            }

            while (source[start] <= referredValue && start < end) {
                start++;
            }
            if (start != end) {
                source[end] = source[start];
            }
        }
        source[start] = referredValue;
        return start;
    }



    public static void _1_recursionFFTSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        // 中轴两边分类
        int midRoller = _1_doFFTPartition(source, start, end);
        // 中轴左边
        _1_recursionFFTSort(source, start, midRoller - 1);
        // 中轴右边排序
        _1_recursionFFTSort(source, midRoller, end);
    }

    public static int _1_doFFTPartition(int[] source, int start, int end) {
        int rv = source[start];
        // 两边移动
        while (start < end) {
            // end移动
            while (source[end] >= rv && end > start) {
                end--;
            }
            if (end != start) {
                // 小值从右边跑到左边
                source[start] = source[end];
            }

            // start移动
            while (source[start] <= rv && start < end) {
                start++;
            }

            if (start != end) {
                // 大值从左边跑到右边
                source[end] = source[start];
            }
        }
        // 排完时, start值一定在索引中间, 中间值还原
        source[start] = rv;
        return start;
    }


    public static void _2_recursionFFTSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        // 中轴排序, 大值小值分两边
        int midRoller = _2_doFFTPartition(source, start, end);
        // 递归左边
        _2_recursionFFTSort(source, start, midRoller - 1);
        // 递归右边
        _2_recursionFFTSort(source, midRoller, end);
    }


    public static int _2_doFFTPartition(int[] source, int start, int end) {
        // 参考值
        int rv = source[start];
        // 两端移动
        while (start < end) {
            // 右边移动
            while (source[end] >= rv && end > start) {
                end--;
            }
            if (end != start) {
                // 大值从右边跑到左边
                source[start] = source[end];
            }

            // 左边移动
            while (source[start] <= rv && start < end) {
                start++;
            }
            if (start != end) {
                // 小值从左边跑到右边
                source[end] = source[start];
            }
        }
        // 排完后, start值位于索引中间, 将参考值还原
        source[start] = rv;
        return start;
    }


    public static int _x_doFFTPartition(int[] source, int start, int end) {
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

    public static void _x_recursionFFTSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int midRoller = _x_doFFTPartition(source, start, end);
        _x_recursionFFTSort(source, start, midRoller - 1);
        _x_recursionFFTSort(source, midRoller, end);
    }


    public static void nonFFT(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        stack.push(start);
        int midRoller = 0;
        while (!stack.isEmpty()) {
            start = stack.pop();
            end = stack.pop();
            midRoller = fftPartition(source, start, end);
            if (start < midRoller - 1) {
                stack.push(midRoller - 1);
                stack.push(start);
            }
            if (midRoller + 1 > end) {
                stack.push(end);
                stack.push(midRoller + 1);
            }
        }
    }

    // 快排, 中轴排序
    public static void fft(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        int midRoller = fftPartition(source, start, end);
        fft(source, start, midRoller - 1);
        fft(source, midRoller + 1, end);
    }

    /**
     * 分区排序是快排的核心, 递归和非递归都要使用
     *
     * @param source
     * @param start
     * @param end
     * @return
     */
    public static int fftPartition(int[] source, int start, int end) {
        // 选取参考值
        int rv = source[start];
        // 双向移动
        while (start < end) {
            while (source[end] >= rv && end > start) {
                end--;
            }
            if (end != start) {
                // 大值从右边往左边移动
                source[start] = source[end];
            }

            while (source[start] >= rv && start > end) {
                start++;
            }
            if (start != end) {
                // 大值从右边往左边移动
                source[end] = source[start];
            }
        }
        // 排完后start就是索引中值, rv还原
        source[start] = rv;
        return start;
    }


    public static void recursionMergeSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        // 分一半
        int midRoller = (start + end) >> 1;
        if (start < midRoller) {
            recursionMergeSort(source, start, midRoller);
        }
        if (midRoller + 1 < end) {
            recursionMergeSort(source, midRoller + 1, end);
        }
        doMerge(source, start, midRoller, end);
    }

    public static void myNonRecursionMergeSort(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        // 拆分的参数栈
        Stack<Integer> splitStack = new Stack<>();
        splitStack.push(end);
        splitStack.push(start);
        // 合并的参数栈
        Stack<Integer> mergeStack = new Stack<>();
        while (!splitStack.isEmpty()) {
            start = splitStack.pop();
            end = splitStack.pop();
            mergeStack.push(end);
            mergeStack.push(start);
            int midRoller = (start + end) >> 1;
            if (start < midRoller) {
                splitStack.push(midRoller);
                splitStack.push(start);
            }
            if (midRoller + 1 < end) {
                splitStack.push(end);
                splitStack.push(midRoller + 1);
            }
        }
        while (!mergeStack.isEmpty()) {
            start = mergeStack.pop();
            end = mergeStack.pop();
            doMerge(source, start, (start + end) >> 1, end);
        }
    }

    @Deprecated
    @WarnDoc("不易懂, 取巧型")
    public static void hisNonRecursionMergerSort(int[] originArray, int start, int end) {
        if (start >= end) {
            return;
        }
        // 循环一致化-while循环, 注意 + - 与 位运算的优先级
        int subLength = 1;
        while (subLength <= originArray.length) {
            int i = start;
            while (i + subLength <= end) {
                doMerge(originArray, i, i + subLength - 1, Math.min(i + (subLength << 1) - 1, originArray.length - 1));
                i += (subLength << 1);
            }
            subLength <<= 1;
        }
    }

    public static void doMerge(int[] source, int start, int midRoller, int end) {
        int[] temp = new int[end - start + 1];
        int i = start;
        int j = midRoller + 1;
        int k = 0;
        // 复制小值
        while (i <= midRoller && j <= end) {
            if (source[i] < source[j]) {
                temp[k++] = source[i++];
            } else {
                temp[k++] = source[j++];
            }
        }
        // 左边剩余元素
        while (i <= midRoller) {
            temp[k++] = source[i++];
        }
        // 右边剩余数组
        while (j <= end) {
            temp[k++] = source[j++];
        }
        System.arraycopy(temp, 0, source, start, temp.length);
        System.out.println(tab(++count, start, midRoller, end) + ", " + "-> UPDATE" + CollectionUtil.showArray(source));
    }

    private static String tab(int n, int start, int midRoller, int end) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < n; i++) {
            result.append("    ");
        }
        return result.toString() + "第" + n + "次[" + start + "|" + midRoller + "|" + end + "]";
    }


    public static void fk_recursion_fft(int[] source, int start, int end) {
        // 快排, 中轴排序, 根据中轴分别排两边
        if (start >= end) {
            return;
        }
        // 确定中轴, 并将数据分散到中轴两边
        int midRoller = fk_partition(source, start, end);
        if (start < midRoller - 1) {
            // 中轴左边递归排序处理
            fk_recursion_fft(source, start, midRoller - 1);
        }
        if (midRoller + 1 < end) {
            // 中轴右边递归排序处理
            fk_recursion_fft(source, midRoller + 1, end);
        }
    }


    public static void fk_nonRecursion_fft(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        // 递归的快排改变的信息是什么?
        // 数组原地排序, 就是start和end值
        // 找中轴, 然后中轴作为左区间的end值, 作为右区间的起始值
        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        stack.push(start);
        while (!stack.isEmpty()) {
            start = stack.pop();
            end = stack.pop();
            int midRoller = fk_partition(source, start, end);
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

    public static int fk_partition(int[] source, int start, int end) {
        // 选定参考值
        int rv = source[start];
        // 假定增序, 左边为小于参考值的, 右边为大于参考值的
        while (start < end) {
            // 左边大值往右边跑
            while (source[end] >= rv && end > start) {
                end--;
            }
            if (end != start) {
                // 不相等说明是小值, 要跑到左边去
                source[start] = source[end];
            }

            // 对称性: 右边小值往左边跑
            while (source[start] <= rv && start < end ) {
                start++;
            }
            if (start != end) {
                // 不相等说明是大值, 要跑到右边去
                source[end] = source[start];
            }
        }
        // 处理完后, start一定是索引中轴, 再把中轴值rv回写进去
        source[start] = rv;
        return start;
    }




}
