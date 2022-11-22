package com.company.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallcommon.utils.Query;
import com.company.mallproduct.dao.AttrGroupDao;
import com.company.mallproduct.entity.AttrGroupEntity;
import com.company.mallproduct.service.AttrGroupService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        // 为 0 查出所有的分类信息
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), new QueryWrapper<AttrGroupEntity>());
            return new PageUtils(page);
        }
        // 使用Key关键字检索关键字内容
        String key = (String) params.get("key");
        // select * from pms_attr_group where catelog_id = ? and (attr_group_id = key or attr_group_name like '%key%')
        LambdaQueryWrapper<AttrGroupEntity> wrapper = Wrappers.<AttrGroupEntity>lambdaQuery().eq(AttrGroupEntity::getCatelogId, catelogId);
        if (StringUtils.isNotBlank(key)) {
            // 添加构造条件
            wrapper.and(queryWrapper -> queryWrapper.eq(AttrGroupEntity::getAttrGroupId, key).or().like(AttrGroupEntity::getAttrGroupName, key));
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

}
