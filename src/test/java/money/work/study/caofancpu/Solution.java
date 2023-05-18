package money.work.study.caofancpu;

import org.junit.Test;

/**
 * N个有序数组, 求最大值, 最小值, 中位树, 上中位数
 * 交集、差集、并集
 */
public class Solution {

    @Test
    public void byteCry() {
        int[] a = new int[]{1, 3, 5, 7, 9};
        int[] b = new int[]{2, 4, 6, 8};
        System.out.println(findMedianTwoSortedArray(a, b));
    }

    @Test
    public void byteCryCry() {
        int[] a = new int[]{2, 2, 2, 2};
        // write code here
        int maxValue = Integer.MIN_VALUE;
        int maxIndex = -1;
        boolean isOk = false;
        for (int i = 0; i < a.length; i++) {
            if (i == 0 && a[i] >= a[i + 1]) {
                // 第一个元素
                isOk = true;
            }
            if (i == (a.length - 1) && a[i] >= a[i - 1]) {
                // 最后一个元素
                isOk = true;
            }
            if (i > 0 && i < (a.length - 1) && a[i] >= a[i - 1] && a[i] >= a[i + 1]) {
                isOk = true;
            }
            if (isOk && a[i] >= maxValue) {
                maxValue = a[i];
                maxIndex = i;
                // 峰值的右邻居肯定没机会成为峰值, 故跳过
                i++;
            }
        }
        System.out.println(maxIndex);
    }

    /**
     * find median in two sorted array
     *
     * @param arr1 int整型一维数组 the array1
     * @param arr2 int整型一维数组 the array2
     * @return int整型
     */
    public int findMedianTwoSortedArray(int[] arr1, int[] arr2) {
        int[] source = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, source, 0, arr1.length);
        System.arraycopy(arr2, 0, source, arr1.length, arr2.length);
        // 归并排序
        merge(source, 0, source.length - 1);
        return ((source.length & 1) == 0) ? source[source.length / 2 - 1] : source[source.length / 2];
    }

    public void merge(int[] source, int start, int end) {
        if (start >= end) {
            return;
        }
        // 中轴
        int mid = (start + end) / 2;
        // 左边
        merge(source, start, mid);
        // 右边
        merge(source, mid + 1, end);
        // 合并
        doMerge(source, start, mid, end);
    }

    public void doMerge(int[] source, int start, int mid, int end) {
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
        // 剩余左边的
        while (i <= mid) {
            temp[k++] = source[i++];
        }
        // 剩余右边的
        while (j <= end) {
            temp[k++] = source[j++];
        }
        System.arraycopy(temp, 0, source, start, temp.length);
    }


}