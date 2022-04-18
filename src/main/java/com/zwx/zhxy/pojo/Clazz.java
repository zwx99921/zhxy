package com.zwx.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级
 * @author zwx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_clazz")
public class Clazz {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; // 主键
    private String name; // 班级名称
    private Integer number; // 班级总人数
    private String introducation; // 班级描述
    private String headmaster; // 班主任
    private String email; // 班主任邮箱
    private String telephone; // 班主任电话
    private String gradeName; // 所属年级
}
