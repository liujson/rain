package cn.liujson.comm4j.random;


import cn.liujson.comm4j.validate.Validate;

import java.util.Random;


/**
 * 生成随机数工具类
 *
 * @author Apache Commons lang
 * @date 2019/9/18
 */
public class RandomUtils {

    /**
     * Random object used by random method. This has to be not local to the
     * random method so as to not return the same value in the same millisecond.
     */
    private static final Random RANDOM = new Random();

    /**
     * <p>
     * {@code RandomUtils} instances should NOT be constructed in standard
     * programming. Instead, the class should be used as
     * {@code RandomUtils.nextBytes(5);}.
     * </p>
     *
     * <p>
     * This constructor is public to permit tools that require a JavaBean
     * instance to operate.
     * </p>
     */
    public RandomUtils() {
        super();
    }

    /**
     * 创建一个随机字节数组
     * <p>
     * Creates an array of random bytes.
     * </p>
     *
     * @param count the size of the returned array
     * @return the random byte array
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public static byte[] nextBytes(final int count) {
        Validate.isTrue(count >= 0, "Count cannot be negative.");

        final byte[] result = new byte[count];
        RANDOM.nextBytes(result);
        return result;
    }

    /**
     * 返回指定范围内的随机整数
     * <p>
     * Returns a random integer within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the random integer
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
        Validate.isTrue(endExclusive >= startInclusive,
                "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    /**
     * 返回指定范围内的随机长整型数
     * <p>
     * Returns a random long within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the random long
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static long nextLong(final long startInclusive, final long endExclusive) {
        Validate.isTrue(endExclusive >= startInclusive,
                "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return (long) nextDouble(startInclusive, endExclusive);
    }


    /**
     * 返回指定范围内的随机双精度浮点数
     * <p>
     * Returns a random double within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the random double
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static double nextDouble(final double startInclusive, final double endInclusive) {
        Validate.isTrue(endInclusive >= startInclusive,
                "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        float diff = 1e-6f;
        if (Math.abs(startInclusive-endInclusive) < diff) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
    }

    /**
     * 返回指定范围内的随机浮点数
     * <p>
     * Returns a random float within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the random float
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static float nextFloat(final float startInclusive, final float endInclusive) {
        Validate.isTrue(endInclusive >= startInclusive,
                "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        float diff = 1e-6f;
        if (Math.abs(startInclusive-endInclusive) < diff) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextFloat());
    }
}
