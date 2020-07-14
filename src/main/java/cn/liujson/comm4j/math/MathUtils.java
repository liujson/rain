package cn.liujson.comm4j.math;

/**
 * 数学工具类
 * Created by liujson on 2020/5/19.
 */
public class MathUtils {


    /**
     * 碾转相除法求两个数的最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcd(int a, int b) {
        //交换两个数
        if (a < b) {
            int temp = a;
            a = b;
            b = temp;
        }
        return a % b == 0 ? b : gcd(b, a % b);
    }

    /**
     * 求两个数的最小公倍数
     * a*b/最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * 计算两点之间的距离
     * 勾股定理：a^2+b^2=c^2
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double pointsDistance(int x1, int y1, int x2, int y2) {
        int a = Math.abs(x1 - x2);
        int b = Math.abs(y1 - y2);
        return Math.sqrt(a * a + b * b);
    }

    /**
     * 判断是否是素数
     *
     * @param num 需要被验证的数
     * @return
     */
    public static boolean isPrime(int num) {
        if (num <= 3) {
            return num > 1;
        }
        // 不在6的倍数两侧的一定不是质数
        if (num % 6 != 1 && num % 6 != 5) {
            return false;
        }
        int sqrt = (int) Math.sqrt(num);
        for (int i = 5; i <= sqrt; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 取下整
     *
     * @param num
     * @return
     */
    public static int floor(double num) {
        return (int) Math.floor(num);
    }

    /**
     * 取上整
     *
     * @param num
     * @return
     */
    public static int ceil(double num) {
        return (int) Math.ceil(num);
    }

}
