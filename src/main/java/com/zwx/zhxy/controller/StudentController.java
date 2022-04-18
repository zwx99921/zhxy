package com.zwx.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwx.zhxy.pojo.Student;
import com.zwx.zhxy.service.StudentService;
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
@Api(tags = "学生控制器")
@RestController
@RequestMapping(value = "/sms/studentController")
public class StudentController {

    @Resource
    StudentService studentService;

    /**
     * 删除一个或者多个学生信息
     * @param ids
     * @return
     */
    @ApiOperation("删除一个或者多个学生信息")
    @DeleteMapping(value = "/delStudentById")
    public Result delStudentById(@ApiParam("要删除的所有的Student的id的JSON集合")@RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 增加或者修改学生信息
     * @param student
     * @return
     */
    @ApiOperation("增加或者修改学生信息")
    @PostMapping(value = "/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("JSON格式的学生信息")@RequestBody Student student){
        Integer id = student.getId();
        if (null == id || 0 == id){
            student.setPassword(MD5.encrypt(student.getPassword()));//如果是新增将密码进行加密
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    /**
     * 查询学生信息,分页带条件
     * @param pageNo
     * @param pageSize
     * @param student
     * @return
     */
    @ApiOperation("查询学生信息,分页带条件")
    @GetMapping(value = "/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo, @ApiParam("分页查询的页大小")@PathVariable("pageSize")Integer pageSize, @ApiParam("分页查询的查询条件") Student student){
        //分页， 待条件查询
        Page<Student> page = new Page<>(pageNo,pageSize);
        //通过服务层进行查询
        IPage<Student> teacherPage = studentService.getStudentByOpr(page,student);
        //封装Result对象并返回
        return Result.ok(teacherPage);
    }
}