package com.zwx.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwx.zhxy.pojo.LoginForm;
import com.zwx.zhxy.pojo.Student;

/**
 * @author zwx 
 */
public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentByID(Long userID);

    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
