package com.company.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallcommon.utils.Query;
import com.company.mallproduct.dao.BrandDao;
import com.company.mallproduct.dao.CategoryBrandRelationDao;
import com.company.mallproduct.dao.CategoryDao;
import com.company.mallproduct.entity.BrandEntity;
import com.company.mallproduct.entity.CategoryBrandRelationEntity;
import com.company.mallproduct.entity.CategoryEntity;
import com.company.mallproduct.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryBrandRelationEntity> queryPageByBrandId(Long brandId) {
        return this.list(Wrappers.<CategoryBrandRelationEntity>lambdaQuery()
                .eq(CategoryBrandRelationEntity::getBrandId, brandId));
    }

    @Override
    public void saveRelation(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        // 保存对应的关系
        categoryBrandRelation.setBrandName(brandEntity.getName()).setCatelogName(categoryBrandRelation.getCatelogName());
        // 入库操作
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        // 更新品牌的名称
        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
        relationEntity.setBrandId(brandId).setBrandName(name);
        // 根据ID更新品牌的名称
        LambdaUpdateWrapper<CategoryBrandRelationEntity> updateWrapper = Wrappers.<CategoryBrandRelationEntity>lambdaUpdate()
                .eq(CategoryBrandRelationEntity::getBrandId, brandId);
        this.update(relationEntity, updateWrapper);
    }

    @Override
    public void updateCategory(Long catId, String name) {
        // 根据ID更新分类的名称
        this.baseMapper.updateCategory(catId, name);
    }

}
