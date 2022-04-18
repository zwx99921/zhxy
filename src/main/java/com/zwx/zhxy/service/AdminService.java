package com.zwx.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwx.zhxy.pojo.Admin;
import com.zwx.zhxy.pojo.LoginForm;

/**
 * @author zwx
 */
public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminByID(Long userID);

    IPage<Admin> getAllAdmin(Page<Admin> page, String adminName);
}
