package com.zwx.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwx.zhxy.pojo.Clazz;
import com.zwx.zhxy.service.ClazzService;
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
@Api(tags = "班级控制器")
@RestController
@RequestMapping(value = "/sms/clazzController")
public class ClazzController {

    @Resource
    ClazzService clazzService;

    /**
     * 获取所有班级信息
     * @return
     */
    @ApiOperation("获取所有班级信息")
    @GetMapping(value = "/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzs = clazzService.getClazzs();
        return Result.ok(clazzs);
    }

    /**
     * 删除一个或者多个班级信息
     * @param ids
     * @return
     */
    @ApiOperation("删除一个或者多个班级信息")
    @DeleteMapping(value = "/deleteClazz")
    public Result deleteClazz(@ApiParam("要删除的所有的Clazz的id的JSON集合")@RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 增加或者修改班级信息
     * @param clazz
     * @return
     */
    @ApiOperation("增加或者修改班级信息")
    @PostMapping(value = "/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("JSON格式的班级信息")@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    /**
     * 查询班级信息,分页带条件
     * @param pageNo
     * @param pageSize
     * @param clazz
     * @return
     */
    @ApiOperation("查询班级信息,分页带条件")
    @GetMapping(value = "/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo, @ApiParam("分页查询的页大小")@PathVariable("pageSize")Integer pageSize, @ApiParam("分页查询的查询条件") Clazz clazz){
        //分页， 待条件查询
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        //通过服务层进行查询
        IPage<Clazz> clazzPage = clazzService.getClazzsByOpr(page,clazz);
        //封装Result对象并返回
        return Result.ok(clazzPage);
    }
}
