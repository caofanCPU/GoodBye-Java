package money.work.study.caofancpu;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.constant.IEnum;
import com.xyz.caofancpu.core.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class SuanFaHaHaHa {

    public static void main(String[] args) {
        new 环球旅行(6, 3).test();
//        new 最长公共子串("234787bfsjh", "878346bdhbhj").test();
    }

    public static abstract class Base {
        abstract void test();
    }

    /**
     * @see 最长公共子串
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 最长公共子数组 extends Base {
        private int[] arrA = new int[]{1, 3, 4, 6, 1, 2};
        private int[] arrB = new int[]{4, 6, 1, 2};

        @Override
        void test() {
            int[] lcs = lcs(arrA, arrB);
            for (int e : lcs) {
                System.out.print(e + "  ");
            }
        }

        public int[] lcs(int[] shortA, int[] longB) {
            int[] none = {};
            if (shortA == null || shortA.length == 0 || longB == null || longB.length == 0) {
                return none;
            }
            if (shortA.length > longB.length) {
                return lcs(longB, shortA);
            }
            int repeatNum = 0;
            int lastIndex = 0;
            int[][] table = new int[shortA.length][longB.length];
            for (int i = 0; i < shortA.length; i++) {
                for (int j = 0; j < longB.length; j++) {
                    if (shortA[i] != longB[j]) {
                        table[i][j] = 0;
                        continue;
                    }
                    if (i == 0 || j == 0) {
                        table[i][j] = 1;
                    } else {
                        table[i][j] = table[i - 1][j - 1] + 1;
                    }
                    if (repeatNum < table[i][j]) {
                        repeatNum = table[i][j];
                        lastIndex = i;
                    }
                }
            }
            viewTable(shortA, longB, table);
            if (repeatNum == 0 || lastIndex + 1 < repeatNum) {
                return none;
            }
            int[] result = new int[repeatNum];
            for (int i = 0; i < repeatNum; i++) {
                result[i] = shortA[lastIndex + 1 - repeatNum + i];
            }
            return result;
        }

        /**
         * 打印二维匹配矩阵表
         */
        private void viewTable(int[] shortA, int[] longB, int[][] table) {
            //    s h o r t A
            // l  row1
            // o  row2
            // n  row3
            // g  row4
            // B  row5
            String separator = "  ";
            System.out.print(" " + separator);
            for (int i : longB) {
                System.out.print(i + separator);
            }
            System.out.println();
            int[] arrA = shortA;
            for (int i = 0; i < table.length; i++) {
                System.out.print(arrA[i] + separator);
                for (int j = 0; j < table[i].length; j++) {
                    System.out.print(table[i][j] + separator);
                }
                System.out.println();
            }
        }
    }

    /**
     * 基本思路: 两个字符串构建二维矩阵, 相同的置为1, 不同的置为0
     * 优化: 两个字符串长短不一, 公共子串肯定不超过较短的字符串, 以它为外循环, 依次匹配长字符串
     * .    字符串为空情况处理, 长串包含子串特殊情况
     * 要点: 尽量构建横向(行少列多)二维矩阵时, 这样比较符合视觉
     * .    因此: 二维表table[m][n], m是行数, n是列数, m应<=n
     * .    即: m对应短串, n对应长串
     * 重要代码点: 借助两个变量, 重复字符数repeatNum, 最后相等字符的短串最长索引lastIndex
     * .         匹配时字符不等, table[i][j]都为0
     * .         只要匹配时字符相等, table[i][j]初始化要么为1, 要么左上角字符匹配结果+1: table[i-1][j-1]
     * .         最后一起判断如果重复字符数repeatNum小于table[i][j], 那么更改repeatNum和lastIndex
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Deprecated
    public static class 最长公共子串 extends Base {
        private String sourceA = "abcd";
        private String sourceB = "abc";

        @Override
        void test() {
            String repeatChars = lcs(sourceA, sourceB);
            System.out.println(repeatChars);
        }

        /**
         * 最长公共子串, 构造二维表, 长>=宽, 短串匹配长串
         * .   a  b  c  d
         * .a  1  0  0  0
         * .b  0  2  0  0
         * .c  0  0  3  0
         */
        public String lcs(String shortA, String longB) {
            String none = "无公共子串";
            if (shortA == null || shortA.length() == 0 || longB == null || longB.length() == 0) {
                return none;
            }
            if (shortA.length() > longB.length()) {
                return lcs(longB, shortA);
            }
            if (longB.contains(shortA)) {
                return shortA;
            }
            // 重复字符数量
            int repeatNum = 0;
            // 最后一个重复字符的索引
            int lastIndex = 0;
            int[][] table = new int[shortA.length()][longB.length()];
            for (int i = 0; i < shortA.length(); i++) {
                for (int j = 0; j < longB.length(); j++) {
                    if (shortA.charAt(i) != longB.charAt(j)) {
                        table[i][j] = 0;
                        continue;
                    }
                    // 相同
                    if (i == 0 || j == 0) {
                        table[i][j] = 1;
                    } else {
                        table[i][j] = table[i - 1][j - 1] + 1;
                    }
                    if (repeatNum < table[i][j]) {
                        repeatNum = table[i][j];
                        lastIndex = i;
                    }
                }
            }
            viewTable(shortA, longB, table);
            return repeatNum == 0 || (lastIndex + 1 < repeatNum) ? none : shortA.substring(lastIndex + 1 - repeatNum, lastIndex + 1);
        }

        /**
         * 打印二维匹配矩阵表
         */
        private void viewTable(String shortA, String longB, int[][] table) {
            //    s h o r t A
            // l  row1
            // o  row2
            // n  row3
            // g  row4
            // B  row5
            String separator = "  ";
            System.out.print(" " + separator);
            for (char c : longB.toCharArray()) {
                System.out.print(c + separator);
            }
            System.out.println();
            char[] charA = shortA.toCharArray();
            for (int i = 0; i < table.length; i++) {
                System.out.print(charA[i] + separator);
                for (int j = 0; j < table[i].length; j++) {
                    System.out.print(table[i][j] + separator);
                }
                System.out.println();
            }
        }
    }

    /**
     * 基本思路: 边界条件, n=0, k=0
     * .  k步代表0~k, 所以构建二维矩阵表时table[k+1][n]
     * .  行为步数step, 列为节点索引index
     * .  初始条件: table[0][0]=1
     * .  动态规划函数式: table[step][i]=左斜上角+右斜上角
     * .  考虑到索引边界问题: table[step][i]也需要=左上角+右上角
     * .  公式: table[step][i] = table[step-1][(i-1+n)%n] + table[step-1][(i+1)%n]
     * .  得到的二维表能代表从【0】出发经过【k】步到达节点【n】的所有情况
     * 优化: 处理特殊情况, 尽早返回
     * 示例:
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Deprecated
    public static class 环球旅行 extends Base {
        /**
         * 点的个数
         */
        private int circleN = 10;
        /**
         * 允许使用步数
         */
        private int stepK = 3;

        @Override
        void test() {
            int[][] dp = calculateSteps(this.stepK, this.circleN);
            viewTable(dp);
        }

        public int[][] calculateSteps(int k, int n) {
            int[][] dp = new int[k + 1][n];
            // 特殊情况可快速处理
//            if (n == 0) {
//                return dp;
//            }
//            if (n == 2) {
//                if ((k & 1) == 0) {
//                    dp[k-1][0] = 1;
//                }
//                return dp;
//            }
            // 初始值
            dp[0][0] = 1;
            for (int step = 1; step <= k; step++) {
                for (int index = 0; index < n; index++) {
                    dp[step][index] = dp[step - 1][(index - 1 + n) % n] + dp[step - 1][(index + 1) % n];
                    System.out.println("(" + step + ", " + index + ")=(" + (step - 1) + ", " + ((index - 1 + n) % n) + ") + (" + (step - 1) + ", " + ((index + 1) % n) + ")");
                }
            }
            return dp;
        }

        /**
         * 打印二维匹配矩阵表
         * k步作为Row, 节点n作为列
         */
        private void viewTable(int[][] table) {
            String separator = "  ";
            System.out.print("Match[x]" + separator);
            for (int i = 0; i < table[0].length; i++) {
                System.out.print("Index[" + i + "]" + separator);
            }
            System.out.println();
            for (int step = 0; step < table.length; step++) {
                System.out.print(".Step[" + step + "]" + separator);
                for (int index = 0; index < table[step].length; index++) {
                    System.out.print(separator + separator + separator + table[step][index] + " " + separator);
                }
                System.out.println();
            }
        }
    }

    /**
     * 基本思路: 区分短串和长串, 以长串为标准进行一次循环, 循环中与短串求和并确定进位、结果值
     * 优化: 将短串拼接到长串后面, 长串长度即为分界点, 循环长串时可以计算到短串索引, 不存在的则为0
     * 要点: 两数相加结果比长串可能多一位
     * .    indexA = maxLength - 1, 长串倒着遍历
     * .    indexB = indexA + shortB.length(), 短串也倒着遍历, 索引推导
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 两数求和 extends Base {
        private String numberA = "9";
        private String numberB = "10";

        @Override
        void test() {
            int[] result = add(this.numberA, this.numberB);
            System.out.println("输入A: " + this.numberA);
            System.out.println("输入B: " + this.numberB);
            System.out.print("两数和: ");
            for (int i = 0; i < result.length; i++) {
                if (i == 0 && result[i] == 0) {
                    continue;
                }
                System.out.print(result[i]);
            }
            System.out.println();
        }

        public int[] add(String longA, String shortB) {
            if (longA.length() < shortB.length()) {
                return add(shortB, longA);
            }
            int maxLength = longA.length();
            String source = longA + shortB;
            char[] charArray = source.toCharArray();
            // 结果, 两数相加结果最多比最大数多一位
            int[] result = new int[maxLength + 1];
            // 来自低位的进位
            int carry = 0;
            int indexB, a, b, sum;
            for (int indexA = maxLength - 1; indexA >= 0; indexA--) {
                // 确定数据B的索引
                indexB = indexA + shortB.length();
                // 数据A的数值
                a = charArray[indexA] - 48;
                // 数据B的数值
                b = indexB < maxLength ? 0 : charArray[indexB] - 48;
                // A+B并加上来自低位的进位
                sum = a + b + carry;
                // 确定进位
                carry = sum / 10;
                // 结果存入数组
                result[indexA + 1] = sum % 10;
            }
            if (carry != 0) {
                // 边界条件
                result[0] = carry;
            }
            return result;
        }
    }

    /**
     * 基本思路: 一个合法的数值字符串, 除第一个字符可以是 数字||'+'||'-' 外, 其余字符都必须为数字,
     * .       所以考虑遍历字符串, 对第一个字符做限定判断: 不满足 数字||'+'||'-' 就直接返回
     * .       对于合法的数字字符, 可以使用数字字符的十进制数值×权值, 权值与字符索引的关系式: Math(10, 字符长度-1-索引)
     * .       考虑边界条件: 当字符数组足够长时, 其数值范围就超过int数值范围了, [-2147483648, 2147483647]
     * .                   假定输入数据在long范围内, 用long型存储求和结果
     * .                   边界条件: -2147483648, -2147483649, 2147483647, 2147483648
     * 优化: 数字0在任何位置不影响求和结果, 故计算条件为 '0'<字符<='9'
     * 时间复杂度: 一次循环, O(n)
     * 空间复杂度: 符号标志位和求和两个变量, O(1)
     * TODO: 能否去掉输入数据在long范围内的假设, 在一次循环内, 既能完成求和又能做出正确的边界判断?
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class 整数解析 extends Base {

        @Override
        void test() {
            edgeValueTest();
            randomValueTest();
        }

        /**
         * @param source
         * @return
         * @see Integer#valueOf(String)
         */
        public String integerValueOf(String source) {
            final String ILLEGAL_RESULT = "-1";
            if (source == null || source.isEmpty()) {
                return ILLEGAL_RESULT;
            }
            char[] chars = source.toCharArray();
            // 默认正数
            boolean isPositive = true;
            long result = 0L;
            for (int i = 0; i < chars.length; i++) {
                if (i == 0) {
                    if (chars[0] == '-') {
                        if (chars.length == 1) {
                            return ILLEGAL_RESULT;
                        }
                        isPositive = false;
                        continue;
                    } else if (chars[0] == '+') {
                        if (chars.length == 1) {
                            return ILLEGAL_RESULT;
                        }
                        continue;
                    } else if (chars[0] < '0' || chars[0] > '9') {
                        return ILLEGAL_RESULT;
                    }
                }
                if (chars[i] > '0' && chars[i] <= '9') {
                    // 合法字符, 123数值为1*10^(3-1-0) + 2*10^(3-1-1) + 3*10^(3-1-2)
                    result = result + (chars[i] - '0') * (long) Math.pow(10, chars.length - 1 - i);
                    continue;
                }
                if (chars[i] == '0') {
                    // 字符'0'不参与计算, 但是是合法字符
                    continue;
                }
                // 其他皆为非法字符
                return ILLEGAL_RESULT;
            }
            if (isPositive) {
                return "" + Math.min(result, Integer.MAX_VALUE);
            }
            return "-" + Math.min(result, (long) Integer.MAX_VALUE + 1);
        }

        /**
         * 随机值测试
         */
        private void randomValueTest() {
            for (int i = 0; i < 1000; i++) {
                String s = random(i);
                System.out.println("输入: " + s + ",\t输出: " + integerValueOf(s));
            }
        }

        /**
         * 边界值测试
         */
        private void edgeValueTest() {
            String[] edgeValueTest = new String[]{
                    "" + Integer.MAX_VALUE,
                    "+" + Integer.MAX_VALUE,
                    "" + ((long) (Integer.MAX_VALUE) + 1),
                    "+" + ((long) (Integer.MAX_VALUE) + 1),
                    "" + Integer.MIN_VALUE,
                    "" + ((long) (Integer.MIN_VALUE) - 1),
                    // 非法值1
                    "-" + Integer.MIN_VALUE,
                    // 非法值2
                    "-" + ((long) (Integer.MIN_VALUE) - 1),
                    "+",
                    "-",
                    "0",
                    "9",
                    "000",
                    "001"
            };
            for (String s : edgeValueTest) {
                System.out.println("输入: " + s + ",\t输出: " + integerValueOf(s));
            }
        }

        /**
         * 随机值测试
         *
         * @param length
         * @return
         */
        private String random(int length) {
            char[] chars = new char[]{
                    '+', '-',
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    '@', '#', '&', '^', '<', '>', '?', ':', '[', ']'
            };
            length = Math.min(length, chars.length / 2);
            StringBuilder source = new StringBuilder(length);
            Random indexGenerator = new Random();
            for (int i = 0; i < length - 1; i++) {
                source.append(chars[indexGenerator.nextInt(length)]);
            }
            return source.toString();
        }
    }

    /**
     * 基本思路: 将原字符串翻转, 利用最长公共子串解决
     * TODO: 优化: Manacher算法, 时间复杂度缩减到n, 减少不必要的判断和计算
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Deprecated
    public static class 最长回文子串 extends Base {
        private String source = "abcbadddddddddd";

        @Override
        void test() {
            int maxLcpsLength = maxLcpsLength(this.source);
            System.out.println("输入字符串: " + this.source + "; 最大回文串长度为: " + maxLcpsLength);
        }

        // 处理字符串
        public char[] manacherString(String str) {
            char[] chars = str.toCharArray();
            char[] res = new char[chars.length * 2 + 1];
            int index = 0;
            for (int i = 0; i < res.length; i++)
                // 偶数位加@, 奇数位不变
                res[i] = (i & 1) == 0 ? '@' : chars[index++];
            return res;
        }

        // 马拉车算法
        public int maxLcpsLength(String str) {
            char[] chars = manacherString(str);
            int[] len = new int[chars.length]; // 记录每个回文字符串的长度
            int right = -1; // 当前回文的右边界
            int cen = -1; // 当前回文的中心
            int max = -1; // 记录回文的最大值
            for (int i = 0; i < chars.length; i++) {
                // 2*cen-i是i点关于cen的对称点
                // right>i时, Math.min(len[2*cen-i], right-i), 两种情况取最小, 往外扩
                // right<=i时, 以i为中心的回文没有被访问过, 所以当前回文字符串只有i, len[i]=1
                len[i] = right > i ? Math.min(len[2 * cen - i], right - i) : 1;

                // 以i为中心, 左右扩
                while (i - len[i] > -1 && i + len[i] < chars.length) {
                    if (chars[i - len[i]] == chars[i + len[i]]) {
                        len[i]++; // 左右字符相等, 符合回文, len++
                    } else {
                        break;
                    }
                }
                // 更新当前回文的右边界以及中心
                if (i + len[i] > right) {
                    right = i + len[i];
                    cen = i;
                }
                max = Math.max(max, len[i]);
            }
            return max - 1;
        }

        /**
         * 动态规划
         * 默认单字符是回文子串，构建 动态二维数组dp 存的是字符串2个下标对应的是否回文子串
         * 从2个长度开始
         * 由后向前进行判断，只要前index和后index中间的是回文子串则当前也是回文子串
         *
         * @param A
         * @param n
         * @return
         */
        public int getLongestPalindrome(String A, int n) {
            if (n == 0 || n == 1) {
                return 0;
            }
            char[] arr = A.toCharArray();
            int max = 1;
            boolean[][] dp = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                dp[i][i] = true;
            }
            for (int i = 0; i < n; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (i - j == 1) {
                        dp[j][i] = arr[i] == arr[j];
                        if (dp[j][i]) {
                            max = Math.max(max, i - j + 1);
                        }
                    } else {
                        if (dp[j + 1][i - 1] && arr[i] == arr[j]) {
                            dp[j][i] = true;
                            max = Math.max(max, i - j + 1);
                        }
                    }
                }
            }
            return max;
        }
    }

    /**
     * 基本思路: 边界值, n=0, n=1时, 进而发现是递归调用, 再使用迭代改进递归
     * .  相当于应用数学归纳法
     * .  记住, 1 + 2 + 3 + 5 + 8, 后一项等于前两项之和
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Deprecated
    public static class 跳台阶 extends Base {
        private int n = 3;

        @Override
        void test() {
            System.out.println(_jump1Or2(this.n));
            System.out.println(jump1Or2(this.n));
            System.out.println(_jumpN(this.n));
            System.out.println(_jumpNOptimized(this.n));
        }

        /**
         * 递归
         * 1.边界条件确定可举例的初始值
         * 2.递归函数式
         * 3.首尾思考, n
         *
         * @param value
         */
        @Deprecated
        public int _jump1Or2(int value) {
            if (value <= 2) {
                return value;
            }
            return _jump1Or2(value - 1) + _jump1Or2(value - 2);
        }

        public int jump1Or2(int value) {
            if (value <= 2) {
                return value;
            }
            int a1 = 1;
            int a2 = 2;
            int result = 0;
            for (int i = 3; i <= value; i++) {
                result = a1 + a2;
                System.out.println("a1=" + a1 + ", a2=" + a2 + ", result=" + result);
                // 迭代推进
                a1 = a2;
                a2 = result;
            }
            return result;
        }

        @Deprecated
        public int _jumpN(int value) {
//            System.out.println(value + ", ");
            if (value <= 1) {
                return 1;
            }
            int result = 0;
            for (int i = value; i >= 1; i--) {
                result += _jumpN(i - 1);
            }
            return result;
        }

        @Deprecated
        public int _jumpNOptimized(int value) {
            // f(n)=f(n-1)+f(n-2)+...+f(2)+f(1)
            // f(n-1)=     f(n-2)+...+f(2)+f(1)
            // f(n) - f(n-1) = f(n-1)
            // f(n) = 2 * f(n-1)
            // cause: f(1)=1=2^0, f(2)=2=2^1
            // so: f(n)=2^(n-1)
            return (int) Math.pow(2, value - 1);
        }

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Deprecated
    public static class 二叉树最大路径和 extends Base {
        private int maxSum = Integer.MIN_VALUE;

        @Override
        void test() {
            TreeNode<Integer> root = new 基础二叉树().loadTree();
            getMax(root);
            System.out.println("最大路径和为: " + maxSum);
        }

        /**
         * 二叉树最大路径和
         * 6行代码
         * null节点为0递归返回
         * left, right
         * 历史值与root.value + left + right比较
         * 返回root.value + Max(left, right)
         *
         * @param node
         * @return
         */
        public int getMax(TreeNode<Integer> node) {
            if (Objects.isNull(node)) {
                return 0;
            }
            int left = Math.max(0, getMax(node.getLeft()));
            int right = Math.max(0, getMax(node.getRight()));
            maxSum = Math.max(maxSum, node.getValue() + left + right);
            return node.getValue() + Math.max(left , right);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Deprecated
    public static class 平衡二叉树 extends Base {
        private boolean balance;
        private TreeNode<Integer> root;
        private int height = 1;

        public int getDepth(TreeNode<Integer> root) {
            if (root == null) {
                return 0;
            }
            // 左子树
            int left = getDepth(root.left) + 1;
            // 右子树
            int right = getDepth(root.right) + 1;
            balance = Math.abs(left - right) <= 1;
            height = Math.max(left, right);
            return height;
        }

        @Override
        void test() {
            root = new 基础二叉树().loadTree();
            getDepth(root);
            System.out.println("二叉树深度: " + height + ", 是否平衡: " + balance);
        }
    }

    /**
     * 二叉树基础:
     * <p>
     * .深度优先遍历: 前序(根左右)、中序(左根右)、后序遍历(左右根), 站在根节点的视角, 并且始终先左后右
     * .广度优先(层次)遍历:
     * 递归的边界, 转迭代, 解题时用外部变量收集结果
     * <p>
     * 思考: 左、右、根, 三个元素的完全排列是6种
     * 根左右: 前序遍历; 根右左: _前序遍历
     * 左根右: 中序遍历; 右根左: _中序遍历
     * 左右根: 后序遍历; 右左根: _后序遍历
     * 可以得到有趣的对称形式:
     * (根左右: 前序遍历)与(右左根: _中序遍历)正好逆序
     * (左根右: 中序遍历)与(右根左: _中序遍历)正好逆序
     * (左右根: 后序遍历)与(根右左: _前序遍历)正好逆序
     * So, 我们试试_前序遍历=前序遍历交换左右, _中序遍历=中序遍历交换左右, _后序遍历=后序遍历交换左右
     * 启示及结论:
     * 前序、中序的递归和栈迭代好写, 记住关键字
     * 但是后序的栈迭代就可以这么干了:
     * 左根右(中序) <==> 右根左逆序, 也就是中序遍历 <==>_中序遍历逆序, 这个结论没啥用, 但是有仪式感
     * 根左右(前序) <==> 右左根逆序
     * 根右左 <==> 左右根逆序, 左右根(后序) <==> 根左右逆序, 这不就是: 后序遍历 <==> _前序遍历逆序麽?
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Deprecated
    public static class 基础二叉树 extends Base {
        /**
         * 根节点
         */
        private TreeNode<Integer> root;
        /**
         * 原始节点值列表
         */
        private List<Integer> originNodeValveList = Lists.newArrayList();
        /**
         * 遍历节点值结果列表
         */
        private List<Integer> traverseNodeValueList = Lists.newArrayList();

        /**
         * 层次遍历时元素分隔数字
         */
        private Integer LEVEL_SPIT_MAGIC_NUMBER = -1;

        @Override
        void test() {
            buildTree(new int[]{5, 3, 7, 1, 4, 6, 8, 0, 2, 9});
            System.out.println("原始节点值" + CollectionUtil.show(originNodeValveList));
            System.out.println();

            preOrder(root);
            System.out.println("前序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            _postOrder(root);
            System.out.println("(反)_后序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            System.out.println("(反)_后序遍历, 再翻转节点值" + CollectionUtil.showArray(reverseArray(traverseNodeValueList.toArray())));
            traverseNodeValueList.clear();

            stackPreOrder(root);
            System.out.println("栈前序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            System.out.println();

            centerOrder(root);
            System.out.println("中序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            _centerOrder(root);
            System.out.println("(反)_中序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            System.out.println("(反)_中序遍历, 再翻转节点值" + CollectionUtil.showArray(reverseArray(traverseNodeValueList.toArray())));
            traverseNodeValueList.clear();

            stackCenterOrder(root);
            System.out.println("栈中序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            System.out.println();

            postOrder(root);
            System.out.println("后序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            _preOrder(root);
            System.out.println("(反)_前序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            System.out.println("(反)_前序遍历, 再翻转节点值" + CollectionUtil.showArray(reverseArray(traverseNodeValueList.toArray())));
            traverseNodeValueList.clear();

            stackPostOrder(root);
            System.out.println("栈后序遍历节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            System.out.println();

            levelOrder(root, TraverseTypeEnum.LEVEL_ALL);
            System.out.println(TraverseTypeEnum.LEVEL_ALL.getName() + "节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            levelOrder(root, TraverseTypeEnum.LEVEL_LEFT);
            System.out.println(TraverseTypeEnum.LEVEL_LEFT.getName() + "节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            levelOrder(root, TraverseTypeEnum.LEVEL_RIGHT);
            System.out.println(TraverseTypeEnum.LEVEL_RIGHT.getName() + "节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            levelOrder(root, TraverseTypeEnum.LEVEL_ZIGZAG);
            System.out.println(TraverseTypeEnum.LEVEL_ZIGZAG.getName() + "节点值" + CollectionUtil.show(traverseNodeValueList));
            traverseNodeValueList.clear();

            System.out.println();
        }

        public enum TraverseTypeEnum implements IEnum {
            PRE_ORDER(1, "前序遍历"),
            CENTER_ORDER(2, "中序遍历"),
            POST_ORDER(3, "后序遍历"),
            STACK_PRE_ORDER(10, "栈前序遍历"),
            STACK_CENTER_ORDER(20, "栈中序遍历"),
            STACK_POST_ORDER(30, "栈后序遍历"),

            OPPOSITE_PRE_ORDER(-1, "反_前序遍历(后序遍历逆序)"),
            OPPOSITE_CENTER_ORDER(-2, "反_中序遍历(中序遍历逆序)"),
            OPPOSITE_POST_ORDER(-3, "反_后序遍历(后序遍历逆序)"),

            LEVEL_ALL(4, "层次遍历"),
            LEVEL_LEFT(5, "左视图层次遍历"),
            LEVEL_RIGHT(6, "右视图层次遍历"),
            LEVEL_ZIGZAG(7, "之字型层次遍历"),
            ;

            @Getter
            private Integer value;
            @Getter
            private String name;

            TraverseTypeEnum(Integer value, String name) {
                this.value = value;
                this.name = name;
            }
        }

        /**
         * 前序遍历: "51"job
         * 5行代码
         * 1个外部变量收集结果, 或者不需要该变量, 直接打印
         * 根左右, 根就是当前输入参数, 所以先当前值, 再left, 再right
         * 为null值就返回, 递归出口
         *
         * @param node
         */
        public void preOrder(TreeNode<Integer> node) {
            if (Objects.isNull(node)) {
                return;
            }
            traverseNodeValueList.add(node.getValue());
            preOrder(node.left);
            preOrder(node.right);
        }

        /**
         * 反后序遍历, 与前序遍历结果互逆, "51"job
         * 5行代码, 1个外部变量, 右左根, null值返回为递归出口
         *
         * @param node
         */
        public void _postOrder(TreeNode<Integer> node) {
            if (Objects.isNull(node)) {
                return;
            }
            _postOrder(node.right);
            _postOrder(node.left);
            traverseNodeValueList.add(node.getValue());
        }

        /**
         * 栈迭代前序遍历, "84消毒液", 8+(1+1+1+1)
         * 8行代码
         * 1个栈
         * 1个外部变量收集结果, 或者不需要直接打印
         * 1个while循环, 条件节点非NULL || 栈非空
         * 1个节点非NULL判断
         * if分支先push, 前序是根左右, 所以先添加值, 再变为left; else分支pop出来, 再变为right
         *
         * @param node
         */
        public void stackPreOrder(TreeNode<Integer> node) {
            Stack<TreeNode<Integer>> stack = new Stack<>();
            while (Objects.nonNull(node) || CollectionUtil.isNotEmpty(stack)) {
                if (Objects.nonNull(node)) {
                    stack.push(node);
                    traverseNodeValueList.add(node.getValue());
                    node = node.getLeft();
                } else {
                    TreeNode<Integer> pop = stack.pop();
                    node = pop.getRight();
                }
            }
        }

        /**
         * 中序遍历: "51"job
         * 5行代码
         * 1个外部变量收集结果, 或者不需要直接打印
         * 左根右, 根就是当前值, 所以先left, 再当前值, 再right
         * 为null值就返回, 递归出口
         *
         * @param node
         */
        public void centerOrder(TreeNode<Integer> node) {
            if (Objects.isNull(node)) {
                return;
            }
            centerOrder(node.left);
            traverseNodeValueList.add(node.getValue());
            centerOrder(node.right);
        }

        /**
         * 反中序遍历, 与中序遍历结果互逆, "51"job
         * 5行代码, 1个外部变量, 右根左, null值返回为递归出口
         *
         * @param node
         */
        public void _centerOrder(TreeNode<Integer> node) {
            if (Objects.isNull(node)) {
                return;
            }
            _centerOrder(node.right);
            traverseNodeValueList.add(node.getValue());
            _centerOrder(node.left);
        }

        /**
         * 栈迭代中序遍历: "84消毒液", 8+(1+1+1+1)
         * 8行代码
         * 1个栈
         * 1个外部变量收集结果, 后者不需要直接打印
         * 1个while循环, 条件为节点非NULL || 栈非空
         * 1个节点非NULL判断
         * if分支先push, 中序是左根右, 所以先变为left; else分支再pop出来添加值, 再变为right
         *
         * @param node
         */
        public void stackCenterOrder(TreeNode<Integer> node) {
            Stack<TreeNode<Integer>> stack = new Stack<>();
            while (Objects.nonNull(node) || CollectionUtil.isNotEmpty(stack)) {
                if (Objects.nonNull(node)) {
                    stack.push(node);
                    node = node.getLeft();
                } else {
                    TreeNode<Integer> pop = stack.pop();
                    traverseNodeValueList.add(pop.getValue());
                    node = pop.getRight();
                }
            }
        }

        /**
         * 后序遍历: "51"job
         * 5行代码
         * 1个外部变量收集结果, 或者不需要直接打印
         * 左右根, 根就是当前值, 所以先left, 再right, 再当前值
         * 为null值就返回, 递归出口
         *
         * @param node
         */
        public void postOrder(TreeNode<Integer> node) {
            if (Objects.isNull(node)) {
                return;
            }
            postOrder(node.left);
            postOrder(node.right);
            traverseNodeValueList.add(node.getValue());
        }

        /**
         * 反前序遍历, 与后序遍历结果互逆, "51"job
         * 5行代码, 1个外部变量, 根右左, null值返回为递归出口
         *
         * @param node
         */
        public void _preOrder(TreeNode<Integer> node) {
            if (Objects.isNull(node)) {
                return;
            }
            traverseNodeValueList.add(node.getValue());
            _preOrder(node.getRight());
            _preOrder(node.getLeft());
        }

        /**
         * 栈迭代后序遍历: "85后", 8+(1+1+1+1)+1
         * 利用 后序遍历 <==> 反_前序遍历的逆序, 反_前序遍历就是前序遍历, left和right交换
         * 1个栈
         * 1个外部变量收集结果
         * 1个while循环, 条件为节点非null || 栈非空
         * 1个if判断节点非null
         * if分支先push, 反_前序是根右左, 所以先添加值, 再变为right; else分支先pop, 再变为left
         * 最后1步就是结果集逆序
         *
         * @param node
         */
        public void stackPostOrder(TreeNode<Integer> node) {
            Stack<TreeNode<Integer>> stack = new Stack<>();
            while (Objects.nonNull(node) || CollectionUtil.isNotEmpty(stack)) {
                if (Objects.nonNull(node)) {
                    stack.push(node);
                    traverseNodeValueList.add(node.getValue());
                    node = node.getRight();
                } else {
                    TreeNode<Integer> pop = stack.pop();
                    node = pop.getLeft();
                }
            }
            // 翻转一下就可以咯
            Collections.reverse(traverseNodeValueList);
        }

        /**
         * 层次(广度优先)遍历:
         * 1个队列, 添加元素offer, 取元素poll
         * 2个while循环, 外层循环是树的层遍历, 条件是队列非空, 内层循环是每层的元素遍历, 条件是int currentLevelSize = queue.size(); i++ < currentLevelSize
         * 3个if判断, 1个元素非空, 1个元素left非空就加入队列, 1个元素right非空就加入队列
         * 4个枚举情况, 层次遍历ALL, 左视图LEFT, 右视图RIGHT, 之字型ZIGZAG
         * .    左视图就是每层只取最左边, 条件为i == 0才收集结果
         * .    右视图就是每层只取最右边, 条件为i == currentLevelSize - 1才收集结果(添加元素时也可以先右后左, 这样也转换为i==0)
         * .    之字型就是普通的层次遍历, 增加层的变量level, 用中间变量currentLevelValueList收集每层结果后, 判断 level++ & 1 == 0的奇偶性, 满足时反转currentLevelValueList结果, 再加入最终结果
         */
        public void levelOrder(TreeNode<Integer> node, TraverseTypeEnum traverseType) {
            if (Objects.isNull(node)) {
                return;
            }
            if (Objects.isNull(traverseType)) {
                traverseType = TraverseTypeEnum.LEVEL_ALL;
            }
            Queue<TreeNode<Integer>> queue = new LinkedList<>();
            queue.offer(node);
            int level = 1;
            while (CollectionUtil.isNotEmpty(queue)) {
                int currentLevelSize = queue.size();
                List<Integer> currentLevelValueList = new ArrayList<>(currentLevelSize);
                int i = 0;
                while (i++ < currentLevelSize) {
                    // 遍历同层
                    TreeNode<Integer> poll = queue.poll();
                    if (Objects.isNull(poll)) {
                        continue;
                    }
                    if (Objects.nonNull(poll.getLeft())) {
                        queue.offer(poll.getLeft());
                    }
                    if (Objects.nonNull(poll.getRight())) {
                        queue.offer(poll.getRight());
                    }
                    if (Objects.equals(TraverseTypeEnum.LEVEL_ALL, traverseType)
                            || Objects.equals(TraverseTypeEnum.LEVEL_ZIGZAG, traverseType)
                            // 左视图, 注意while循环获取第一个元素后i为1
                            || (Objects.equals(TraverseTypeEnum.LEVEL_LEFT, traverseType) && i == 1)
                            // 右视图, 注意while循环获取最后一个元素i后为currentLevelSize
                            || (Objects.equals(TraverseTypeEnum.LEVEL_RIGHT, traverseType) && (i == currentLevelSize))) {
                        // 全部; 左视图; 右视图; 之字型
                        currentLevelValueList.add(poll.getValue());
                    }
                }
                if (Objects.equals(TraverseTypeEnum.LEVEL_ZIGZAG, traverseType) && (level++ & 1) == 0) {
                    Collections.reverse(currentLevelValueList);
                }
                traverseNodeValueList.addAll(currentLevelValueList);
                // 添加分隔符
                traverseNodeValueList.add(LEVEL_SPIT_MAGIC_NUMBER);
            }
            // 移除最后一个分隔符
            traverseNodeValueList.remove(traverseNodeValueList.size() - 1);
        }

        /**
         * ⓪①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
         * .            ①
         * .       ②        ③
         * .    ④    ⑤        ⑥
         * .       ⑦   ⑧
         *
         * @see #buildTree(int[])
         * @see #build8Tree(int)
         */
        @Deprecated
        private void buildTree() {
            TreeNode<Integer> node1 = new TreeNode<>(1);
            TreeNode<Integer> node2 = new TreeNode<>(2);
            TreeNode<Integer> node3 = new TreeNode<>(3);
            TreeNode<Integer> node4 = new TreeNode<>(4);
            TreeNode<Integer> node5 = new TreeNode<>(5);
            TreeNode<Integer> node6 = new TreeNode<>(6);
            TreeNode<Integer> node7 = new TreeNode<>(7);
            TreeNode<Integer> node8 = new TreeNode<>(8);
            jet(node1, node2, node3);
            jet(node2, node4, node5);
            jet(node3, null, node6);
            jet(node5, node7, node8);
            originNodeValveList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
            root = node1;
        }

        @Deprecated
        private void jet(TreeNode<Integer> root, TreeNode<Integer> left, TreeNode<Integer> right) {
            if (Objects.isNull(root)) {
                return;
            }
            if (Objects.nonNull(left)) {
                root.left = left;
            }
            if (Objects.nonNull(right)) {
                root.right = right;
            }
        }

        public TreeNode<Integer> loadTree() {
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
        private TreeNode<Integer> buildTree(int[] values) {
            root = new TreeNode<>(values[0]);
            originNodeValveList.add(root.getValue());
            for (int i = 1; i < values.length; i++) {
                originNodeValveList.add(values[i]);
                addNode(new TreeNode<>(values[i]), root);
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
        private void build8Tree(int rootValue) {
            this.root = new TreeNode<>(rootValue);
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

        private void addNode(TreeNode<Integer> current, TreeNode<Integer> refer) {
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class TreeNode<T> {
        private T value;
        private TreeNode<T> left;
        private TreeNode<T> right;

        public TreeNode(T value) {
            this.value = value;
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

    public static void bitSkill() {
        System.out.println("1.异或^         : a^b = b^a");
        System.out.println("2.异或^         : a^a = 0; a^0 = a; a^1 = a的反码 ==> 应用: 将一个变量快速清零: a = a^a");
        System.out.println("3.异或^         : a^b^b = a; a^b^a = b ==> 应用: 快速交换两个整数: int x = x^y; int y = x^y; int x = x^y;");
        System.out.println("4.与&           : ==> 应用: 快速判断奇数偶数: a&1 == 0 ? \"偶数\" : \"奇数\"");
        System.out.println("5.异或^和右移>>  : ==> 应用: 快速求绝对值: int i = a >> 31; int abs = (a^i) - i; 注意小于或等于Integer.MIN_VALUE求绝对值会溢出得到错误结果");
    }

}
