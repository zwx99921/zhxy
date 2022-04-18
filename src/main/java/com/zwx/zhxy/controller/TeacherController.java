package com.zwx.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwx.zhxy.pojo.Student;
import com.zwx.zhxy.pojo.Teacher;
import com.zwx.zhxy.service.TeacherService;
import com.zwx.zhxy.util.MD5;
import com.zwx.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zwx 
 */
@Api(tags = "教师控制器")
@RestController
@RequestMapping(value = "/sms/teacherController")
public class TeacherController {

    @Resource
    TeacherService teacherService;

    /**
     * 删除一个或者多个教师信息
     * @param ids
     * @return
     */
    @ApiOperation("删除一个或者多个教师信息")
    @DeleteMapping(value = "/deleteTeacher")
    public Result deleteTeacher(@ApiParam("要删除的所有的Teacher的id的JSON集合")@RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 增加或者修改教师信息
     * @param teacher
     * @return
     */
    @ApiOperation("增加或者修改教师信息")
    @PostMapping(value = "/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@ApiParam("JSON格式的教师信息")@RequestBody Teacher teacher){
        Integer id = teacher.getId();
        if (null == id || 0 == id){
            teacher.setPassword(MD5.encrypt(teacher.getPassword())); //如果是新增将密码进行加密
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    /**
     * 查询教师信息,分页带条件
     * @param pageNo
     * @param pageSize
     * @param teacher
     * @return
     */
    @ApiOperation("查询教师信息,分页带条件")
    @GetMapping(value = "/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo, @ApiParam("分页查询的页大小")@PathVariable("pageSize")Integer pageSize, @ApiParam("分页查询的查询条件") Teacher teacher){
        //分页， 待条件查询
        Page<Teacher> page = new Page<>(pageNo,pageSize);
        //通过服务层进行查询
        IPage<Teacher> teacherPage = teacherService.getTeacherByOpr(page,teacher);
        //封装Result对象并返回
        return Result.ok(teacherPage);
    }
}