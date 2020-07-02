package cn.liujson.comm4j.text;

import java.util.Arrays;

/**
 * 数字转汉语读法
 *
 * 读数规则：
 * 1. 四位以内的数可以顺着位次，从最高位读起，例如 1987 读作一千九百八十七
 * 2. 四位以上的数，先从右向左四位分级，然后从高级起，顺次读出各级里的数和它们的级名，例如
 * 38 4375 9001 读作三十八亿四千三百七十五万九千零一。
 * 3. 一个数末尾有0，不论有几个都可不读，分级后任一级末尾有零，也可不读，在需要读出时，不论有几个0，均只读一个零，中间有0的，也不论连续有几个0，需要读出时只读一个零，例如
 * 4003 0005 读作四千零三万零五 | 9 0050 0800读作九亿零五十万八千
 * @author Liujs
 * @date 2019/9/29
 */
public class NumToCnNumUtils {


    /**
     * 汉字计数符号
     */
    private final static char[] DIGIT = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    /**
     * 单位
     */
    private final static char[] UNIT = {'千','百','十','\0'};
    /**
     * 小组单位
     */
    private final static char[] GROUP_UNIT = {'亿','万','\0'};
    /**
     * 负
     */
    private final static char NEGATIVE = '负';


    /**
     * 处理整数数字
     *
     * @param number
     * @return
     */
    public static String convert(long number) {
        boolean isNegative = false;
        if (number == 0) {
            //零直接返回
            return String.valueOf(DIGIT[0]);
        }else if(number<0){
            //负整数处理成正整数
            number = -number;
            isNegative = true;
        }
        //对零进行处理
        char[] wipeZeroNumberChar = wipeZero(number);
        //分组
        char[][] numberGroup = numberGroup(wipeZeroNumberChar);
        //处理
        StringBuilder strGroup = dispose(numberGroup);
        //负数处理并返回
        return isNegative?strGroup.insert(0,NEGATIVE).toString():strGroup.toString();
    }

    /**
     * 处理成汉字读法
     * @param numberGroup
     * @return
     */
    private static StringBuilder dispose(char[][] numberGroup) {
        StringBuilder strGroup = new StringBuilder();
        for (int i = 0; i <numberGroup.length ; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean isAdd = false;
            for (int j = numberGroup[i].length-1; j >=0 ; j--) {
                if(numberGroup[i][j] == '\0'){
                    continue;
                }
                int groupUnitIndex = GROUP_UNIT.length-numberGroup.length+i;
                if(numberGroup[i][j] == '0'){
                    stringBuilder.append(DIGIT[numberGroup[i][j]-48]);
                    if(!isAdd&&j == numberGroup.length-1){
                        stringBuilder.append(groupUnitIndex!=GROUP_UNIT.length-1?GROUP_UNIT[groupUnitIndex]:"");
                        isAdd = true;
                    }
                    continue;
                }
                int unitIndex = UNIT.length-numberGroup[i].length+j;
                if(!isAdd){
                    stringBuilder.append(groupUnitIndex!=GROUP_UNIT.length-1?GROUP_UNIT[groupUnitIndex]:"");
                    isAdd = true;
                }
                stringBuilder.append(unitIndex!=UNIT.length-1?UNIT[unitIndex]:"");
                stringBuilder.append(DIGIT[numberGroup[i][j]-48]);
            }
            strGroup.append(stringBuilder.reverse());
        }
        return strGroup;
    }

    /**
     * 分组
     *
     * @param numbers
     * @return
     */
    private static char[][] numberGroup(char[] numbers) {
        char[][] numberGroup = new char[numbers.length / 4 + (numbers.length % 4 != 0 ? 1 : 0)][];
        int index = numberGroup.length-1;
        for (int i = numbers.length; i >0 ; i -= 4) {
            numberGroup[index--] = Arrays.copyOfRange(numbers,i-4>=0?i-4:0,i);
        }
        return numberGroup;
    }

    /**
     * 专注于整型数据消除 0
     *
     * @param number
     */
    private static char[] wipeZero(long number) {
        //长整型转化为 char 数组
        char[] charArr = String.valueOf(number).toCharArray();
        for (int i = charArr.length - 1, record = 0; i > 0; i--) {
            if (charArr[i] == '0') {
                record++;
                if (record >= 2) {
                    charArr[i] = '\0';
                }
            } else {
                record = 0;
            }
        }
        if (charArr[charArr.length - 1] == '0') {
            charArr[charArr.length - 1] = '\0';
        }
        return charArr;
    }
}
