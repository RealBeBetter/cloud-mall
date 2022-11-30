package com.company.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallcommon.utils.Query;
import com.company.mallproduct.dao.AttrAttrgroupRelationDao;
import com.company.mallproduct.dao.AttrDao;
import com.company.mallproduct.dao.AttrGroupDao;
import com.company.mallproduct.dao.CategoryDao;
import com.company.mallproduct.entity.AttrAttrgroupRelationEntity;
import com.company.mallproduct.entity.AttrEntity;
import com.company.mallproduct.entity.AttrGroupEntity;
import com.company.mallproduct.entity.CategoryEntity;
import com.company.mallproduct.service.AttrService;
import com.company.mallproduct.service.CategoryService;
import com.company.mallproduct.vo.AttrRespVO;
import com.company.mallproduct.vo.AttrVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationDao relationDao;
    @Resource
    private AttrGroupDao groupDao;
    @Resource
    private CategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttr(AttrVO attr) {
        // 多表操作，涉及到事务
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // 保存属性值
        this.save(attrEntity);
        // 保存关系值
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId()).setAttrId(attrEntity.getAttrId());
        relationDao.insert(relationEntity);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId) {
        LambdaQueryWrapper<AttrEntity> queryWrapper = Wrappers.<AttrEntity>lambdaQuery();
        // 根据条件查询
        if (catelogId != 0) {
            queryWrapper.eq(AttrEntity::getCatelogId, catelogId);
        }
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key);
        }
        // 获得分页查询结果
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        List<AttrEntity> attrEntities = page.getRecords();
        // 添加分组和分类信息
        List<AttrRespVO> attrRespVOS = attrEntities.stream().map(attrEntity -> {
            AttrRespVO attrRespVO = new AttrRespVO();
            BeanUtils.copyProperties(attrEntity, attrRespVO);
            // 设置分类和分组的名字
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(Wrappers.<AttrAttrgroupRelationEntity>lambdaQuery()
                    .eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
            if (ObjectUtils.isNotNull(relationEntity)) {
                AttrGroupEntity attrGroupEntity = groupDao.selectById(relationEntity.getAttrGroupId());
                attrRespVO.setGroupName(attrGroupEntity.getAttrGroupName());
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (ObjectUtils.isNotNull(categoryEntity)) {
                attrRespVO.setCatelogName(categoryEntity.getName());
            }
            return attrRespVO;
        }).collect(Collectors.toList());
        // 更改查询结果集
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrRespVOS);
        return pageUtils;
    }

    @Override
    public AttrRespVO getAttrInfo(Long attrId) {
        AttrRespVO attrRespVO = new AttrRespVO();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrRespVO);
        // 查询其他需要的完整信息，包括分组信息和分类信息
        AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(Wrappers.<AttrAttrgroupRelationEntity>lambdaQuery().eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
        if (ObjectUtils.isNotNull(relationEntity)) {
            attrRespVO.setAttrGroupId(relationEntity.getAttrGroupId());
            AttrGroupEntity groupEntity = groupDao.selectById(relationEntity.getAttrGroupId());
            attrRespVO.setGroupName(ObjectUtils.isNull(groupEntity) ? null : groupEntity.getAttrGroupName());
        }
        // 设置分类路径
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        attrRespVO.setCatelogPaths(catelogPath);
        // 设置分类名字
        CategoryEntity categoryEntity = categoryService.getById(catelogId);
        attrRespVO.setCatelogName(ObjectUtils.isNull(categoryEntity) ? null : categoryEntity.getName());
        return attrRespVO;
    }

}
