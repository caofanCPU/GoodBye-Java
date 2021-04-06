package money.work.study;

import com.xyz.caofancpu.core.CollectionUtil;
import money.work.study.caofancpu.SortAlgorithmUtil;
import org.junit.Test;
import org.springframework.util.StopWatch;

/**
 * 排序测试
 *
 * @author D8GER
 */
public class SortTest {

    @Test
    public void executeSort() {
        StopWatch watch = new StopWatch("排序");
        Integer[] originArray = SortAlgorithmUtil.getInitRandomArray(1000000);
        originArray = new Integer[]{3, 4, 5, 2, 1};
        Integer[] originArrayCopy = new Integer[originArray.length];
        System.arraycopy(originArray, 0, originArrayCopy, 0, originArray.length);
        Integer[] originArrayCopy2 = new Integer[originArray.length];
        System.arraycopy(originArray, 0, originArrayCopy2, 0, originArray.length);
        Integer[] originArrayCopy3 = new Integer[originArray.length];
        System.arraycopy(originArray, 0, originArrayCopy3, 0, originArray.length);

        watch.start();
        SortAlgorithmUtil.recursionFFTSort(originArray);
        SortAlgorithmUtil.out(CollectionUtil.showArray(originArray));
        watch.stop();

        watch.start();
        SortAlgorithmUtil.nonRecursionFFTSort(originArrayCopy);
        watch.stop();

        watch.start();
        SortAlgorithmUtil.recursionMergerSort(originArrayCopy2);
        watch.stop();

        watch.start();
        SortAlgorithmUtil.nonRecursionMergerSort(originArrayCopy3);
        watch.stop();
        SortAlgorithmUtil.out("递归快排\n非递归快排\n递归归并排序\n非递归归并排序\n" + watch.prettyPrint());
    }


}
