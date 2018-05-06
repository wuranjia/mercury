package com.hy.lang.mercury.common;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class Constants {
    public static final int ZERO_INT = 0;
    public static final int ONE_INT = 1;
    public static final boolean 需要登录 = false;
    public static final boolean 已登录 = true;
    public static final String PASSWORD_SALT = "SALT";

    public static final String NVL = "--";
    public static final String LOGIN_OUT_URL = "loginOut";
    public static final String USER_ID = "userId";
    public static final String 分号 = ";";
    public static final String 冒号 = ":";
    public static final int NUM3000_INT = 3000;
    public static final Long L_负_1 = -1L;
    public static final String ONLINE_FLAG = "onlineFlag";
    public static final String SYS = "sys";
    public static final String cookie = "cookie";
    public static final String 逗号 = ",";
    public static final Long SELLER = 1L;//admin
    public static final String 中国移动 = "中国移动";

    public static Long getUserId(HttpServletRequest request) {
        Long userId = null;
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String headerName = e.nextElement();//透明称
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                String value = headerValues.nextElement();
                if (Constants.cookie.equals(headerName)) {
                    String[] array = value.split(";");
                    for (String tmp : array) {
                        System.out.println(tmp);
                        if (tmp.indexOf(Constants.USER_ID) >= 0) {
                            String[] array2 = tmp.split("=");
                            if (array2[1] != null && "null" != array2[1]) {
                                userId = Long.valueOf(array2[1]);
                            }
                        }
                    }
                }
            }
        }
        return userId;
    }
}
