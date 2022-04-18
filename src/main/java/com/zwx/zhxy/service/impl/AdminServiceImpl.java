package com.zwx.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwx.zhxy.mapper.AdminMapper;
import com.zwx.zhxy.pojo.Admin;
import com.zwx.zhxy.pojo.LoginForm;
import com.zwx.zhxy.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author zwx
 */
@Service("adminService")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password",loginForm.getPassword());
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public Admin getAdminByID(Long userID) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userID);
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public IPage<Admin> getAllAdmin(Page<Admin> page, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)){
            queryWrapper.like("name",adminName);
        }
        queryWrapper.orderByAsc("id");
        Page<Admin> adminPage = baseMapper.selectPage(page, queryWrapper);
        return adminPage;
    }
}
