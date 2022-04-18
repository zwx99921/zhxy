package com.zwx.zhxy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录表单信息
 * @author zwx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    private String username; // 用户名
    private String password; // 用户密码
    private String verifiCode; // 验证码
    private Integer userType; // 用户类型
}
