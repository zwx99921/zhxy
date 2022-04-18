package com.zwx.zhxy.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析request请求中的 token口令的工具AuthContextHolder
 * @author zwx
 */
public class AuthContextHolder {

    /**
     * 从请求头token获取userid
     * @param request
     * @return
     */
    public static Long getUserIdToken(HttpServletRequest request) {
        //从请求头token
        String token = request.getHeader("token");
        //调用工具类
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }

    /**
     * 从请求头token获取name
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取username
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}
