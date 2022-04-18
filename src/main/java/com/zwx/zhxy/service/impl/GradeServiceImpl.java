package com.zwx.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwx.zhxy.mapper.GradeMapper;
import com.zwx.zhxy.pojo.Grade;
import com.zwx.zhxy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zwx
 */
@Service("gradeService")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        queryWrapper.orderByAsc("id");
        Page<Grade> gradePage = baseMapper.selectPage(page, queryWrapper);
        return gradePage;
    }

    @Override
    public List<Grade> getGrades() {
        return baseMapper.selectList(null);
    }
}
