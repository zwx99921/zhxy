package com.zwx.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生
 * @author zwx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_student")
public class Student {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; // 主键
    private String sno; // 学生编号
    private String name; // 学生姓名
    private Character gender; //学生性别
    private String password; // 学生密码
    private String email; // 学生邮箱
    private String telephone; // 学生电话
    private String address; // 学生地址
    private String introducation; // 学生介绍
    private String portraitPath; // 学生头像上传地址
    private String clazzName; // 学生所在班级
}
