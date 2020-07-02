package cn.liujson.comm4j.validate;


import cn.liujson.comm4j.text.StringUtils;

import java.util.Collection;
import java.util.Map;


/**
 * description
 *
 * @author Liujs
 * @date 2019/9/16
 */
public class Validate {

    /**
     * 默认的，验证不通过的提示信息
     */
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
    /**
     * 默认的，验证对象为空时的提示信息
     */
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";

    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";

    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";

    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE ="The validated character sequence is empty";

    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";

    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";


    /**
     * 如果 expression == false 抛出异常
     *
     * @param expression 需要检验的 boolean 值
     * @param message the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values the optional values for the formatted exception message, null array not recommended
     */
    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (expression == false) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    /**
     * 如果 expression == false 抛出异常
     * @param expression 需要检验的 boolean 值
     */
    public static void isTrue(final boolean expression) {
        if (expression == false) {
            throw new IllegalArgumentException(DEFAULT_IS_TRUE_EX_MESSAGE);
        }
    }

    /**
     * 验证一个对象为空时抛出异常
     * @param object 需要验证的对象
     * @param <T> the object type
     * @return the validated object (never {@code null} for method chaining)
     */
    public static <T> T notNull(final T object) {
        return notNull(object, DEFAULT_IS_NULL_EX_MESSAGE);
    }

    /**
     * 验证一个对象为空时抛出异常
     * @param object 需要验证的对象
     * @param message the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values the optional values for the formatted exception message
     * @param <T> the object type
     * @return the validated object (never {@code null} for method chaining)
     */
    public static <T> T notNull(final T object, final String message, final Object... values) {
        if (object == null) {
            throw new NullPointerException(String.format(message, values));
        }
        return object;
    }

    public static <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
        if (array == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (array.length == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return array;
    }
    public static <T> T[] notEmpty(final T[] array) {
        return notEmpty(array, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
    }
    public static <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... values) {
        if (collection == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return collection;
    }
    public static <T extends Collection<?>> T notEmpty(final T collection) {
        return notEmpty(collection, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
    }
    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
        if (map == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return map;
    }
    public static <T extends Map<?, ?>> T notEmpty(final T map) {
        return notEmpty(map, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
    }
    public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (chars.length() == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }
    public static <T extends CharSequence> T notEmpty(final T chars) {
        return notEmpty(chars, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE);
    }

    public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if(StringUtils.isBlank(chars)){
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }
    public static <T extends CharSequence> T notBlank(final T chars) {
        return notBlank(chars, DEFAULT_NOT_BLANK_EX_MESSAGE);
    }
}
