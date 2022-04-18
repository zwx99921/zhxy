package com.zwx.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwx.zhxy.pojo.Admin;
import com.zwx.zhxy.service.AdminService;
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
@Api(tags = "用户控制器")
@RestController
@RequestMapping(value = "/sms/adminController")
public class AdminController {

    @Resource
    AdminService adminService;


    /**
     * 删除一个或者多个用户信息
     * @param ids
     * @return
     */
    @ApiOperation("删除一个或者多个用户信息")
    @DeleteMapping(value = "/deleteAdmin")
    public Result deleteAdmin(@ApiParam("要删除的所有的Admin的id的JSON集合")@RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 增加或者修改用户信息
     * @param admin
     * @return
     */
    @ApiOperation("增加或者修改用户信息")
    @PostMapping(value = "/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("JSON格式的教师信息")@RequestBody Admin admin){
        Integer id = admin.getId();
        if (null == id || 0 == id){
            admin.setPassword(MD5.encrypt(admin.getPassword())); //如果是新增将密码进行加密
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    /**
     * 查询用户信息,分页带条件
     * @param pageNo
     * @param pageSize
     * @param adminName
     * @return
     */
    @ApiOperation("查询用户信息,分页带条件")
    @GetMapping(value = "/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo, @ApiParam("分页查询的页大小")@PathVariable("pageSize")Integer pageSize, @ApiParam("分页查询的查询条件") String adminName){
        //分页， 待条件查询
        Page<Admin> page = new Page<>(pageNo,pageSize);
        //通过服务层进行查询
        IPage<Admin> teacherPage = adminService.getAllAdmin(page,adminName);
        //封装Result对象并返回
        return Result.ok(teacherPage);
    }


}
