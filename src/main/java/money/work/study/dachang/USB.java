package money.work.study.dachang;

import java.util.Stack;

/**
 *
 */
public class USB {

    public static void main(String[] args) {

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
        recursionMergeSort(source, start, midRoller);
        recursionMergeSort(source, midRoller + 1, end);
        doMerge(source, start, midRoller, end);
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
    }
}
