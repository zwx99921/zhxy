package com.zwx.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwx.zhxy.pojo.Grade;
import com.zwx.zhxy.service.GradeService;
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
@Api(tags = "年级控制器")
@RestController
@RequestMapping(value = "/sms/gradeController")
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * 获取全部班级信息
     * @return
     */
    @ApiOperation("获取全部年级信息")
    @GetMapping(value = "/getGrades")
    public Result getGrades(){

       List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }

    /**
     * 删除一个或者多个grade信息
     * @param ids
     * @return
     */
    @ApiOperation("删除一个或者多个grade信息")
    @DeleteMapping(value = "/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除的所有的Grade的id的JSON集合") @RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 添加或者修改年级信息
     * @param grade
     * @return
     */
    @ApiOperation("添加或者修改年级信息")
    @PostMapping(value = "/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("JSON格式的Grade对象，有ID修改，无ID新增")@RequestBody Grade grade){
        //接收参数
        //调用服务层方法完成增减或者修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    /**
     * 查询年级信息,分页带条件
     * @param pageNo
     * @param pageSize
     * @param gradeName
     * @return
     */
    @ApiOperation("查询年级信息,分页带条件")
    @GetMapping(value = "/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo,@ApiParam("分页查询的页大小")@PathVariable("pageSize")Integer pageSize,@ApiParam("分页查询模糊匹配的名称") String gradeName){
        //分页， 待条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //通过服务层进行查询
        IPage<Grade> gradeIPage = gradeService.getGradeByOpr(page,gradeName);
        //封装Result对象并返回
        return Result.ok(gradeIPage);
    }
}
