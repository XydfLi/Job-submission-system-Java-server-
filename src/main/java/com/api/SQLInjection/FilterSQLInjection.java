package com.api.SQLInjection;

import com.api.exception.InfoCode;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预防SQL注入
 * 本工程已使用prepareStatement，并对输入的数据进行合法性判断，故不用此两种方法
 *
 * @author 李星源
 * @version 1.0
 */
public class FilterSQLInjection {
    //日志信息
    private static Logger logger=Logger.getLogger(FilterSQLInjection.class.getName());

    /**
     * 法二：过滤非法字符串（已通过测试，可用）
     *
     * @param s 需要过滤字符串
     * @return 过滤后字符串
     */
    public final static String sqlInjection(String s) {
        if (s == null || "".equals(s)) {
            return "";
        }
        try {
            s = s.trim().replaceAll("</?[s,S][c,C][r,R][i,I][p,P][t,T]>?", "");//script
            s = s.trim().replaceAll("[a,A][l,L][e,E][r,R][t,T]\\(", "").replace("\"", "");// alert
            s = s.trim().replace("\\.swf", "").replaceAll("\\.htc", "");
            s = s.trim().replace("\\.php\\b", "").replaceAll("\\.asp\\b", "");
            s = s.trim().replace("document\\.", "").replaceAll("[e,E][v,V][a,A][l,L]\\(", "");
            s = s.trim().replaceAll("'", "").replaceAll(">", "");
            s = s.trim().replaceAll("<", "").replaceAll("=", "");
            s = s.trim().replaceAll(" [o,O][r,R]", "");
            s = s.trim().replaceAll("etc/", "").replaceAll("cat ", "");
            s = s.trim().replaceAll("/passwd ", "");
            s = s.trim().replaceAll("sleep\\(", "").replaceAll("limit ", "").replaceAll("LIMIT ", "");
            s = s.trim().replaceAll("[d,D][e,E][l,L][e,E][t,T][e,E] ", "");// delete
            s = s.trim().replaceAll("[s,S][e,E][l,L][e,E][c,C][t,T] ", "");// select;
            s = s.trim().replaceAll("[u,U][p,P][d,D][a,A][t,T][e,E] ", "");// update
            s = s.trim().replaceAll("[d,D][e,E][l,L][a,A][y,Y] ", "").replaceAll("waitfor ", "");
            s = s.trim().replaceAll("print\\(", "").replaceAll("md5\\(", "");
            s = s.trim().replaceAll("cookie\\(", "").replaceAll("send\\(", "");
            s = s.trim().replaceAll("response\\.", "").replaceAll("write\\(", "")
                    .replaceAll("&", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * 法三：过滤非法字符串（使用正则表达式）（已通过测试，可用）
     *
     */
    static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    //忽略大小写
    static Pattern sqlPattern=Pattern.compile(reg,Pattern.CASE_INSENSITIVE);

    /**
     * 过滤字符串
     *
     * @param str 需要过滤的字符串
     * @return InfoCode对象
     */
    public static InfoCode isSQLValid(String str) {
        Matcher matcher=sqlPattern.matcher(str);
        if (matcher.find()) {
            System.out.println("非法字符："+matcher.group());
            return InfoCode.ILLEGAL_CHARACTERS;
        }
        return InfoCode.OK;
    }

}
