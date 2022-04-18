package com.zwx.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwx.zhxy.mapper.TeacherMapper;
import com.zwx.zhxy.pojo.LoginForm;
import com.zwx.zhxy.pojo.Teacher;
import com.zwx.zhxy.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author zwx
 */
@Service("teacherService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", loginForm.getPassword());
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherByID(Long userID) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userID);
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        String clazzName = teacher.getClazzName();
        if (!StringUtils.isEmpty(clazzName)){
            queryWrapper.like("clazz_name",clazzName);
        }
        String name = teacher.getName();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByAsc("id");
        Page<Teacher> teacherPage = baseMapper.selectPage(page, queryWrapper);
        return teacherPage;
    }
}
