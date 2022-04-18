package com.zwx.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwx.zhxy.pojo.Admin;
import com.zwx.zhxy.pojo.LoginForm;
import com.zwx.zhxy.pojo.Student;
import com.zwx.zhxy.pojo.Teacher;
import com.zwx.zhxy.service.AdminService;
import com.zwx.zhxy.service.StudentService;
import com.zwx.zhxy.service.TeacherService;
import com.zwx.zhxy.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zwx
 */
@Api(tags = "系统控制器")
@RestController
@RequestMapping(value = "/sms/system")
public class SystemController {

    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;


    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@ApiParam("旧密码")@PathVariable("oldPwd") String oldPwd,@ApiParam("新密码")@PathVariable("newPwd") String newPwd,@ApiParam("当前登录用户的token")@RequestHeader("token")String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            //token过期
            return Result.fail().message("token失效，请重新登录后修改密码");
        }
        // 获取用户ID和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
                adminQueryWrapper.eq("id",userId.intValue());
                adminQueryWrapper.eq("password",oldPwd);
                Admin admin = adminService.getOne(adminQueryWrapper);
                if (admin != null){
                    //修改
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
            case 2:
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("id",userId.intValue());
                studentQueryWrapper.eq("password",oldPwd);
                Student student = studentService.getOne(studentQueryWrapper);
                if (student != null){
                    //修改
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
            case 3:
                QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
                teacherQueryWrapper.eq("id",userId.intValue());
                teacherQueryWrapper.eq("password",oldPwd);
                Teacher teacher = teacherService.getOne(teacherQueryWrapper);
                if (teacher != null){
                    //修改
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok();
    }


    /**
     * 文件上传统入口
     * @param multipartFile
     * @param request
     * @return
     */
    @ApiOperation("文件上传统入口")
    @PostMapping(value = "/headerImgUpload")
    public Result headerImgUpload(@ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile,HttpServletRequest request){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();//获取UUID,以保证文件名不重复
        String originalFilename = multipartFile.getOriginalFilename();//获取到文件名
        int i = originalFilename.lastIndexOf(".");//将文件名后缀截取到
        String newFileName = uuid.concat(originalFilename.substring(i));//文件名用uuid进行拼接，生成新的文件名，保证文件名不重复
        //保存文件   将文件发送到第三方/独立的图片服务器
//        request.getServletContext().getRealPath("public/upload"); //在SpringBoot项目中使用的内置TomCat，上传地址和实际地址会有不符
        String portraitPath = "E:/Works/zhxy/target/classes/public/upload/".concat(newFileName); //此处写的绝对路径,为项目编译后的文件保存地址
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应图片的路径
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @ApiOperation("获取用户信息")
    @GetMapping(value = "/getInfo")
    public Result getInfo(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出 用户id 和用户的类型
        Long userID = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String,Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminByID(userID);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentByID(userID);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherByID(userID);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    /**
     * 登录验证
     * @param loginForm
     * @param request
     * @return
     */
    @ApiOperation("登录验证")
    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //将密码加密
        loginForm.setPassword(MD5.encrypt(loginForm.getPassword()));
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode().toLowerCase();
        System.out.println();
        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode){
            return Result.fail().message("验证码失效，请刷新重试");
        }
        if (!sessionVerifiCode.equals(loginVerifiCode)){
            return Result.fail().message("验证码有误，请重新输入");
        }
        //从session中移除现有验证码
        session.removeAttribute("verifiCode");
        //分用户类型进行校验
        Map<String,Object> map = new LinkedHashMap<>();//准备一个Map,用于存放响应的数据
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        //用户的类型和用户的id转换成一个密文，以token的名称向客户端反馈
                        map.put("token",JwtHelper.createToken(admin.getId().longValue(), 1));
                    }else{
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (null != student) {
                        //用户的类型和用户的id转换成一个密文，以token的名称向客户端反馈
                        map.put("token",JwtHelper.createToken(student.getId().longValue(), 2));
                    }else{
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (null != teacher) {
                        //用户的类型和用户的id转换成一个密文，以token的名称向客户端反馈
                        map.put("token",JwtHelper.createToken(teacher.getId().longValue(), 3));
                    }else{
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail("查无此用户");
    }

    /**
     * 获取图片验证码
     * @param request
     * @param response
     */
    @ApiOperation("获取验证码图片")
    @GetMapping(value = "/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request,HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode =  new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode.toLowerCase());
        //将验证码图片返回响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
