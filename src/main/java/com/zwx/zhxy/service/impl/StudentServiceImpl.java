package com.zwx.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwx.zhxy.mapper.StudentMapper;
import com.zwx.zhxy.pojo.LoginForm;
import com.zwx.zhxy.pojo.Student;
import com.zwx.zhxy.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author zwx
 */
@Service("studentService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password",loginForm.getPassword());
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student getStudentByID(Long userID) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userID);
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        String clazzName = student.getClazzName();
        if (!StringUtils.isEmpty(clazzName)){
            queryWrapper.like("clazz_name",clazzName);
        }
        String name = student.getName();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByAsc("id");
        Page<Student> studentPage = baseMapper.selectPage(page, queryWrapper);
        return studentPage;
    }
}
