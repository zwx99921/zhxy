package com.zwx.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师
 * @author zwx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_teacher")
public class Teacher {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; // 主键
    private String tno; // 教师工号
    private String name; // 教师姓名
    private Character gender; // 教师性别
    private String password; // 教师密码
    private String email; // 教师邮箱
    private String telephone; // 教师电话
    private String address; // 教师地址
    private String portraitPath; // 教师头像上传地址
    private String clazzName; // 教师所管理班级
}
