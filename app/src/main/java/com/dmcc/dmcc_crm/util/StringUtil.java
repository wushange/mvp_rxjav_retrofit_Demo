package com.dmcc.dmcc_crm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class StringUtil {

    public static final String URL_REG_EXPRESSION = "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*";
    public static final String EMAIL_REG_EXPRESSION = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";

    public static boolean isUrl(String s) {
        if (s == null) {
            return false;
        }
        return Pattern.matches(URL_REG_EXPRESSION, s);
    }

    public static boolean isEmail(String s) {
        if (s == null) {
            return true;
        }
        return Pattern.matches(EMAIL_REG_EXPRESSION, s);
    }

    public static String join(String spliter, Object[] arr) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        if (spliter == null) {
            spliter = "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                break;
            }
            if (arr[i] == null) {
                continue;
            }
            builder.append(arr[i].toString());
            builder.append(spliter);
        }
        return builder.toString();
    }

    public static String fromFile(File f) throws IOException {
        InputStream is = new FileInputStream(f);
        byte[] bs = new byte[is.available()];
        is.read(bs);
        is.close();
        return new String(bs);
    }

    private static String TAG = "StringUtil";

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (str == "") {
            return true;
        }
        if (str == "null") {
            return true;
        }
        return str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return str != null && str.trim().length() > 0 && !"null".equals(str)
                && str != "";
    }

    public static boolean isNotBlank(Object obj) {

        if (obj instanceof String) {
            String str = (String) obj;
            return str != null && str.trim().length() > 0;
        } else {

            if (obj != null) {
                return true;
            }
        }

        return false;
    }

    public static List<Integer> getNumsFromStr(String str) {

        String[] ary = str.replaceAll("[^\\d]", " ").split("\\s+");

        List<Integer> list = new ArrayList<Integer>();

        for (String num : ary) {
            if (!num.trim().equals("")) {
                list.add(Integer.valueOf(num.trim()));
            }
        }

        return list;
    }

    public static Integer getFirstNumFromStr(String str) {

        String[] ary = str.replaceAll("[^\\d]", " ").split("\\s+");

        List<Integer> list = new ArrayList<Integer>();

        for (String num : ary) {
            if (!num.trim().equals("")) {
                list.add(Integer.valueOf(num.trim()));
            }
        }
        if (list.size() > 0)
            return list.get(0);
        return 0;
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 字符串去重
     *
     * @param st
     * @return
     * @author FuLS
     * @date 2012-2-13
     */
    public static String removeSameString(String st) {
        Map<String, Object> mLinkedSet = new HashMap<String, Object>();
        String str = st.replaceAll(" ", "");
        String[] strArray = str.split(",");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < strArray.length; i++) {
            if (!mLinkedSet.containsKey(strArray[i])) {
                mLinkedSet.put(strArray[i], strArray[i]);
                sb.append(strArray[i] + ",");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * @param s      要截取的字符串
     * @param length 要截取字符串的长度->是字节一个汉字2个字节 return 返回length长度的字符串（含汉字）
     * @author cn
     */
    public static String getLimitStr(String s, int length) {
        if (s == null) {
            return "";
        }
        byte[] bytes = null;
        try {
            bytes = s.getBytes("Unicode");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        if (null != bytes)
            for (; i < bytes.length && n < length; i++) {
                // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
                if (i % 2 == 1) {
                    n++; // 在UCS2第二个字节时n加1
                } else {
                    // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                    if (bytes[i] != 0) {
                        n++;
                    }
                }

            }
        // 如果i为奇数时，处理成偶数
        /*
		 * if (i % 2 == 1){ // 该UCS2字符是汉字时，去掉这个截一半的汉字 if (bytes[i - 1] != 0) i =
		 * i - 1; // 该UCS2字符是字母或数字，则保留该字符 else i = i + 1; }
		 */
        // 将截一半的汉字要保留
        if (i % 2 == 1) {
            i = i + 1;
        }
        try {
            return new String(bytes, 0, i, "Unicode");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    // 出现次数
    public static int countNumber(String key, String content) {
        int count = 0;
        // String cont = content;
        while (content.indexOf(key) > -1) {

            count = count + 1;
            content = content.substring(content.indexOf(key) + key.length());
        }
        return count;
    }


    /**
     * Title: reverseStr<br>
     * Description: TODO 逆序<br>
     * Depend : TODO <br>
     *
     * @param orig
     * @return
     * @author 朱紫春
     * @Modified by
     * @CreateDate 2014-1-21
     * @Version
     */
    public static String reverseStr(String orig) {
        char[] s = orig.toCharArray();
        int n = s.length - 1;
        int halfLength = n / 2;
        for (int i = 0; i <= halfLength; i++) {
            char temp = s[i];
            s[i] = s[n - i];
            s[n - i] = temp;
        }
        return new String(s);
    }

    /**
     * Title: isNumber<br>
     * Description: 判断是否为数字<br>
     * Depend : TODO <br>
     *
     * @param str_num
     * @return
     * @author mst
     * @Modified by
     * @CreateDate 2014-6-11
     * @Version
     */
    public static boolean isNumber(String str_num) {
        boolean isNum = false;
        if (isNotBlank(str_num))
            isNum = str_num.matches("[0-9]+");
        return isNum;
    }

    public static boolean isDouble(String str_num) {
        boolean isNum = false;
        if (isNotBlank(str_num))
            isNum = str_num.matches("([1-9]+[0-9]*|0)(\\.[\\d]+)?");
        return isNum;
    }

    /**
     * Html-encode the string.
     *
     * @param s the string to be encoded
     * @return the encoded string
     */
    public static String htmlEncode(String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    sb.append("&apos;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    // String
    // regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    // Pattern p = Pattern.compile(regEx);
    // Matcher m = p.matcher(name);
    // if( m.find()){
    // Toast.makeText(InputRegisterInfoActivity.this, "姓名不允许输入特殊符号！",
    // Toast.LENGTH_LONG).show();
    // }
}
