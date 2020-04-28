package com.android.sharedemo.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * <p>Author:MrIcefox
 * <p>Email:extremetsa@gmail.com
 * <p>Description:
 * <p>Date:2017/11/29
 */

public class MathUtils {
    private MathUtils() {
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double 相减
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 除法
     * @param scale 四舍五入 小数点位数
     */
    public static double div(double d1, double d2, int scale) {
        // 当然在此之前，你要判断分母是否为0，
        // 为0你可以根据实际需求做相应的处理
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //add 2018-10-12 8.1.7
    /**
     * double类型 如
     * 1234567.22 --> 1234567.22
     * StringUtil.subZeroAndDot()方法中
     * 1234567.22 --> 1234567.22
     * 若double的值是12345678 超过7位时(不包含小数点)
     * 12345678.22 --> 12345678.22
     * 12345678.22 --> 1.234567833E7
     */
    public static String double2StrFormat(double mDouble) {
        //%.2f %. 表示 小数点前任意位数   2 表示两位小数 格式后的结果为f 表示浮点型  四舍五入
        if (mDouble <= 0) return "0";
        String result = String.format(Locale.US, "%.2f", mDouble);
        if (result.indexOf(".") > 0) {
            result = result.replaceAll("0+?$", "");// 去掉多余的0
            result = result.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return result;
    }

    public static double string2Double(String mString) {
        if (TextUtils.isEmpty(mString))
            return 0d;
        try {
            return Double.parseDouble(mString);
        } catch (Exception e) {
            return 0d;
        }
    }

    public static String double2String(double mDouble) {
        if (mDouble <= 0) return "0";
        return String.valueOf(mDouble);
    }

    public static int string2Int(String mString) {
        if (TextUtils.isEmpty(mString))
            return 0;
        try {
            return Integer.valueOf(mString);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String int2String(int mInt) {
        return String.valueOf(mInt);
    }

    private static BigDecimal bigDecimal(double mDouble) {
        return new BigDecimal(mDouble + "");
    }

    //乘法
    public static double bigMultiply(double one, double two) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        return bigOne.multiply(bigTwo).doubleValue();
    }

    //除法
    public static double bigDivide2(double one, double two) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        return bigOne.divide(bigTwo, 2).doubleValue();
    }

    public static double bigAdd2(double one, double two) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        return bigOne.add(bigTwo).doubleValue();
    }

    public static double bigSubtract2(double one, double two) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        return bigOne.subtract(bigTwo).doubleValue();
    }

    public static double bigSubtract3(double one, double two, double three) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        BigDecimal bigThree = bigDecimal(three);
        return bigOne.subtract(bigTwo).subtract(bigThree).doubleValue();
    }

    public static double bigSubtract4(double one, double two, double three, double four) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        BigDecimal bigThree = bigDecimal(three);
        BigDecimal bigFour = bigDecimal(four);
        return bigOne.subtract(bigTwo).subtract(bigThree).subtract(bigFour).doubleValue();
    }

    public static double bigSubtract5(double one, double two, double three, double four, double five) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        BigDecimal bigThree = bigDecimal(three);
        BigDecimal bigFour = bigDecimal(four);
        BigDecimal bigFive = bigDecimal(five);
        return bigOne.subtract(bigTwo).subtract(bigThree).subtract(bigFour).subtract(bigFive).doubleValue();
    }

    public static double bigSubtract6(double one, double two, double three, double four,
                                      double five, double six) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        BigDecimal bigThree = bigDecimal(three);
        BigDecimal bigFour = bigDecimal(four);
        BigDecimal bigFive = bigDecimal(five);
        BigDecimal bigSix = bigDecimal(six);
        return bigOne.subtract(bigTwo).subtract(bigThree).subtract(bigFour).subtract(bigFive)
                .subtract(bigSix).doubleValue();
    }

    public static double bigSubtract7(double one, double two, double three, double four,
                                      double five, double six, double seven) {
        BigDecimal bigOne = bigDecimal(one);
        BigDecimal bigTwo = bigDecimal(two);
        BigDecimal bigThree = bigDecimal(three);
        BigDecimal bigFour = bigDecimal(four);
        BigDecimal bigFive = bigDecimal(five);
        BigDecimal bigSix = bigDecimal(six);
        BigDecimal bigSeven = bigDecimal(seven);
        return bigOne.subtract(bigTwo).subtract(bigThree).subtract(bigFour).subtract(bigFive)
                .subtract(bigSix).subtract(bigSeven).doubleValue();
    }
}