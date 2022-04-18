package com.zwx.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwx.zhxy.pojo.LoginForm;
import com.zwx.zhxy.pojo.Teacher;

/**
 * @author zwx 
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherByID(Long userID);

    IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher);
}
