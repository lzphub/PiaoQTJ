package cn.dankal.basiclib.util;


import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.dankal.basiclib.DankalApplication;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

/**
 * Created by AD on 2016/6/2.
 */
public class StringUtil {
    private static String TAG = "StringUtil";
    /**
     * 正则：身份证号码15位
     */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";


    /**
     * 判断有效字符串
     *
     * @param str
     * @return
     */
    public static boolean isValid(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static String safeString(String s, String safeS) {
        return s == null || s.isEmpty() || "0".equals(s) ? safeS : s;
    }

    public static String safeString2(String s, String safeS) {
        return s == null || s.isEmpty() ? safeS : s;
    }

    public static String safeString(Object o) {
        if (o == null) {
            return "";
        }
        return String.valueOf(o);
    }

    /**
     * 这个方法会提示变量是null还是 empty
     */
    public static boolean isValid(String variableName, String str) {
        if (str != null && !str.isEmpty()) {
            return true;
        } else if (str == null) {
            return false;
        } else {
            return false;
        }
    }


    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regx = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * 正则，手机号码是否正确
     *
     * @param phone 手机号
     * @return true:通过测试;false:不通过
     */
    public static boolean checkPhone(String phone) {//判断手机号格式
        Pattern phonenumber;
        Matcher phone_num;
//        phonenumber = Pattern.compile("^[1][3-8][0-9]{9}$");
        phonenumber = Pattern.compile("^[1][0-9]{10}$");
        phone_num = phonenumber.matcher(phone);
        return phone_num.matches();
    }

    /**
     * 校验密码
     *
     * @param pass
     * @return
     */
    public static boolean checkPasswd(String pass) {

        // 6-20数字、字母、符号，不允许特殊符号`'\“ 空格等
        return !(pass.contains("`") || pass.contains("'") || pass.contains("\\")
                || pass.contains("\"") || pass.contains(" "));
    }

    /**
     * 读取asset文件
     *
     * @param context
     * @param path
     * @return
     */
    public static String readAssetsFileString(Context context, String path) {

        String str = null;
        try {
            InputStream is = context.getApplicationContext().getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            str = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 身份证号码 校验 15或者18位
     *
     * @param idNum
     * @return
     */
    public static boolean isIDCard(String idNum) {
        return isIDCard15(idNum) || isIDCard18(idNum);
    }


    /**
     * 验证身份证号码15位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(CharSequence input) {
        return isMatch(REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码18位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(CharSequence input) {
        return isMatch(REGEX_ID_CARD18, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }


    /**
     * string转成HTML
     *
     * @param string
     * @return
     */
    public static Spanned fromHtml(String string) {
        Spanned htmlSpan = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            htmlSpan = Html.fromHtml(string,FROM_HTML_MODE_LEGACY, null, null);
        }else{
            htmlSpan = Html.fromHtml(string);
        }
        return htmlSpan;
    }

    public static Spanned fromHtml(int stringResource) {
        Spanned htmlSpan = Html.fromHtml(DankalApplication.getContext().getString(stringResource));
        return htmlSpan;
    }

    /**
     * 去除时间的毫秒
     *
     * @param acceptTime
     * @return
     */
    public static String getTimeString(String acceptTime) {
        if (acceptTime.contains("."))
            return acceptTime.substring(0, acceptTime.length() - 4);
        return acceptTime;
    }


    /**
     * 正则分割字符串为数组
     *
     * @param source 原有数据
     * @param reg    分割规则
     * @return 结果
     */
    public static String[] spiltToArray(String source, String reg) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return source.split(reg);
    }

    /**
     * 正则分割字符串为集合
     *
     * @param source 原有数据
     * @param reg    分割规则
     * @return 结果
     * 注:该数组不支持添加数据的操作
     */
    public static List<String> spiltToList(String source, String reg) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        String[] split = source.split(reg);
        return Arrays.asList(split);
    }

    /**
     * 判断是否为整数
     * @param str
     * @return
     */
    public static boolean isDigits(String str) {
        return str.matches("[-+]?[0-9]*");
    }

}
