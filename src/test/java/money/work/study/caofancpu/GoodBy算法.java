package money.work.study.caofancpu;

import com.xyz.caofancpu.core.CollectionUtil;
import org.junit.Test;

/**
 * 2023-09-21, 备忘复习
 *
 * @author D8GER
 */
public class GoodBy算法 {

    @Test
    public void fftTest() {
        int[] arr = new int[]{3, 7, 6, 8, 3, 2};
        fft(arr, 0, arr.length - 1);
        System.out.println(CollectionUtil.showArray(arr));
    }

    public static void fft(int[] arr, int start, int end) {
        if (start > end) {
            return;
        }
        // 找中轴值
        int midRoller = doFFT(arr, start, end);
        // 排左边
        if (start < midRoller - 1) {
            fft(arr, start, midRoller - 1);
        }
        // 排右边
        if (end > midRoller + 1) {
            fft(arr, midRoller + 1, end);
        }
    }

    public static int doFFT(int[] source, int start, int end) {
        int referredValue = source[start];
        while (start < end) {
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

}
