package com.android.sharedemo.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /*
     * 比较字符串是否为Null或者为空.
     */
    public static boolean equalsNullOrEmpty(String res) {
        if (res == null || res.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * zzf 判断字符串是否为Null或者为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s != null && !s.equals("null") ? TextUtils.isEmpty(s.trim()) : true;
    }

    public static boolean notNullOrEmpty(String string) {
        return !equalsNullOrEmpty(string);
    }

    public static String getNotNullString(String string) {
        return string == null ? "" : string;
    }

    public static boolean isIdCard(String idNumber) {
        if (idNumber.length() == 18) {
            String pattern = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62" +
                    "|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|" +
                    "(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|" +
                    "(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(idNumber);
            return m.matches();
        } else {
            String pattern = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62" +
                    "|63|64|65|71|81|82|91)\\d{4})((((([02468][048])|([13579][26]))0229))|([0-9][0-9])((((0[1-9])|" +
                    "(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))" +
                    "(\\d{3})";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(idNumber);
            return m.matches();
        }
    }

    /**
     * 是否是护照的格式
     *
     * @param strPassport
     * @return
     */
    public static boolean isPassportCard(String strPassport) {
        String pattern = "^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$";
        Pattern p = Pattern.compile(pattern);
        return p.matcher(strPassport).matches();
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    //判断是否为手机号码
    public static boolean isPhone(String strPhone) {
        if (StringUtil.equalsNullOrEmpty(strPhone)) {
            return false;
        }
        String strPattern = "^((?:13\\d|14[\\d]|15[\\d]|16[\\d]|17[\\d]|18[\\d]|19[\\d])-?\\d{5}(\\d{3}|\\*{3}))$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strPhone);
        return m.matches();
    }

    /**
     * @param @param  phone
     * @param @return
     * @return String
     * @throws
     * @Title: formatPhone
     * @Description: 格式化手机号码
     */
    public static String formatPhone(String phone) {
        if (StringUtil.equalsNullOrEmpty(phone)) {
            return phone;
        }
        if (phone.startsWith("+86")) {
            phone = phone.substring(3);
        }
        phone = phone.replaceAll(" ", "").replaceAll("-", "");
        return phone;
    }

    //判断是否为英文字母
    public static boolean isEnglish(String strEnglish) {
        if (StringUtil.equalsNullOrEmpty(strEnglish)) {
            return false;
        }
        return strEnglish.matches("[a-zA-Z]+");
    }

    /**
     * 隐藏手机号码中间四位
     */
    public static String _hideMobile(String phonestr) {
        if (!StringUtil.equalsNullOrEmpty(phonestr) && phonestr.length() == 11) {
            phonestr = phonestr.substring(0, 3) + "****" + phonestr.substring(7, phonestr.length());
        }
        return phonestr;
    }

    /**
     * 统计字符数
     */
    public static int countChar(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int count = 0;
        char[] chs = str.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            count += (chs[i] > 0xff) ? 2 : 1;
        }
        return count;
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串中是否仅包含字母数字和汉字
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    public static boolean isLetterOrNum(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    public static String getNumber(String str) {
        String text = TextUtils.isEmpty(str) ? "" : str;
        Pattern pattern = Pattern.compile("[^0-9.-]");
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("").trim();
    }

//    public static boolean isChinese(String str) {
//        String regex = "[\u4e00-\u9fa5]+$";
//        Pattern pattern = Pattern.compile(regex);
//        return pattern.matcher(str).matches();
//    }

    /**
     * @param chineseStr
     * @return 是否含有中文
     */
    public static final boolean isChineseCharacter(String chineseStr) {
        char[] charArray = chineseStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算位数
     *
     * @param str
     * @return
     */
    public static int calculatePlaces(String str) {
        int m = 0;
        char arr[] = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if ((c >= 0x0000 && c <= 0x00FF) || (c >= '0' && c <= '9')) { // 英文字符或数字
                m = m + 1;
            } else { // 中文字符 或其他
                m = m + 2;
            }
        }
        float n = (float) m / (float) 2;

        return Math.round(n);// 四舍五入后取整
    }

    /**
     * 小数点后面为0时，显示整数
     *
     * @return
     * @author yangyang DateTime 2013-6-10 下午6:38:09
     */
    public static String subZeroAndDot(String s) {
        if (equalsNullOrEmpty(s)) {
            return "";
        }
        double price = 0;
        try {
            price = MathUtils.div(Double.parseDouble(s), 1, 2);
        } catch (Exception e) {
        }
        if (price != 0) {
            s = price + "";
        }
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * GB 2312-80 把收录的汉字分成两级。第一级汉字是常用汉字，计 3755 个， 置于 16～55
     * 区，按汉语拼音字母／笔形顺序排列；第二级汉字是次常用汉字， 计 3008 个，置于 56～87 区，按部首／笔画顺序排列，所以本程序只能查到
     * 对一级汉字的声母。同时对符合声母（zh，ch，sh）只能取首字母（z，c，s）
     */

    // 国标码和区位码转换常量
    static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600};
    static final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
            'y', 'z'};

    public static String getSpells(String characters) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < characters.length(); i++) {
            char ch = characters.charAt(i);
            if ((ch >> 7) != 0) {
                // 判断是否为汉字，如果右移7位为0就不是汉字，否则是汉字
                char spell = getFirstLetter(ch);
                buffer.append(String.valueOf(spell));
            } else if (ch > 0 && ch < 128) {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    // 获取一个汉字的首字母
    public static Character getFirstLetter(char ch) {
        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
            return null;
        } else {
            return convert(uniCode);
        }
    }

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * <p/>
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * <p/>
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
    static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }

    // 去除字符串中的空格、回车、换行符、制表符;
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    //验证邮政编码
    public static boolean checkPost(String post) {
        if (post.matches("[1-9]\\d{5}(?!\\d)")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkEnglishName(String post) {
        if (post.matches("[A-Za-z]{1,20}+$")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 首字母转大写
     *
     * @param name
     * @return
     */
    public static String toUpperCaseFirstOne(String name) {
        if (equalsNullOrEmpty(name)) {
            return name;
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;

    }

    // 完整的判断中文汉字和符号
    @SuppressWarnings("unused")
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }


    /**
     * 8.1.6 校验收件人姓名
     * 不包含连续的：小姐、女士、先生字样
     * 并且字符要大于1
     */

    public static boolean isCorrectAddressName(String name) {
        String checkName = name.replace(" ", "").trim();
        if (checkName.length() <= 1 || checkName.contains("小姐")
                || checkName.contains("女士") || checkName.contains("先生")
                || StringUtil.containsEmoji(checkName)) {
            return false;
        }
        return true;
    }

    //过滤emoji 或者 其他非文字类型的字符
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return null;
        }
        return buf.toString();
    }

    /**
     * 关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return 结果SpannableString
     */
    public static SpannableString matcherSearchTitle(Context context, int color, String text, String keyword) {
//        keyword = escapeExprSpecialWord(keyword);
//        text = escapeExprSpecialWord(text);
        SpannableString s = new SpannableString(text);
        char[] chars = keyword.toCharArray();
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(keyword)) try {
            for (char c : chars) {
                Pattern p = Pattern.compile(c + "");
                Matcher m = p.matcher(s);
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

        } catch (Exception e) {
            L.e(e.toString());
        }
        return s;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param str
     * @return str
     */
    public static String escapeExprSpecialWord(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (str.contains(key)) {
                    str = str.replace(key, "\\" + key);
                }
            }
        }
        return str;
    }
}