package com.zwx.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwx.zhxy.mapper.ClazzMapper;
import com.zwx.zhxy.pojo.Clazz;
import com.zwx.zhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zwx
 */
@Service("clazzService")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        String gradeName = clazz.getGradeName();
        if (!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("grade_name",gradeName);
        }
        String clazzName = clazz.getName();
        if (!StringUtils.isEmpty(clazzName)){
            queryWrapper.like("name",clazzName);
        }
        queryWrapper.orderByAsc("id");
        Page<Clazz> clazzPage = baseMapper.selectPage(page, queryWrapper);
        return clazzPage;
    }

    @Override
    public List<Clazz> getClazzs() {
        return baseMapper.selectList(null);
    }
}
