package www.manager.leke.com.lekemanager.utils;

import android.text.TextUtils;

public class StringUtils {
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        return !(value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim()));
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    /**
     * 按照给出的最大字符串长度裁剪字符串,如果超出最大长度,则裁剪后添加省略号,否则原样返回
     *
     * @param str      要裁剪的字符串
     * @param maxWords 最大长度
     * @return 裁剪后的字符串
     */
    public static String cutString(String str, int maxWords) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() > maxWords) {
            return str.substring(0, maxWords) + "...";
        } else {
            return str;
        }
    }

    public static String getadeCode(String gradeCode){
        if(!TextUtils.isEmpty(gradeCode)) {
            switch (gradeCode) {
                case "SE01":
                    gradeCode = "一年级";
                    break;
                case "SE02":
                    gradeCode = "二年级";
                    break;
                case "SE03":
                    gradeCode = "三年级";
                    break;
                case "SE04":
                    gradeCode = "四年级";
                    break;
                case "SE05":
                    gradeCode = "五年级";
                    break;
                case "SE06":
                    gradeCode = "六年级";
                    break;
                case "SE07":
                    gradeCode = "七年级";
                    break;
                case "SE08":
                    gradeCode = "八年级";
                    break;
                case "SE09":
                    gradeCode = "九年级";
                    break;
                case "SE10":
                    gradeCode = "高一";
                    break;
                case "SE11":
                    gradeCode = "高二";
                    break;
                case "SE12":
                    gradeCode = "高三";
                    break;
            }

        }
        return gradeCode;
    }
}