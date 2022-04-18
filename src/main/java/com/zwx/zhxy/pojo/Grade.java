package com.zwx.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 年级
 * @author zwx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_grade")
public class Grade {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; // 主键
    private String name; // 年级名称
    private String manager; // 年级主任
    private String email; // 年级主任邮箱
    private String telephone; // 年级主任电话
    private String introducation; // 年级描述
}
