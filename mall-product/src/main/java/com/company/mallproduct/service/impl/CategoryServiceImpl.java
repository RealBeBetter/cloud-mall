package com.company.mallproduct.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallcommon.utils.Query;
import com.company.mallproduct.dao.CategoryDao;
import com.company.mallproduct.entity.CategoryEntity;
import com.company.mallproduct.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 查询出所有的分类实体
        List<CategoryEntity> allCategories = baseMapper.selectList(null);
        // 组装成树形结构，找出所有的一级菜单并且排序，且找出子菜单
        return allCategories.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .peek(categoryEntity -> categoryEntity.setChildren(getCategoryChildren(categoryEntity, allCategories)))
                .sorted(Comparator.comparingInt(CategoryEntity::getSort)).collect(Collectors.toList());
    }

    @Override
    public void deleteBatchIds(List<Long> catIds) {
        // 执行逻辑删除，对应于 show_status
        baseMapper.deleteBatchIds(catIds);
    }


    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        // 找到所有的父节点
        List<Long> parentCatePath = this.findParentCatePath(catelogId, paths);
        CollectionUtil.reverse(parentCatePath);
        return parentCatePath.toArray(new Long[0]);
    }

    private List<Long> findParentCatePath(Long cateId, List<Long> paths) {
        paths.add(cateId);
        CategoryEntity category = this.getById(cateId);
        // 递归查找父节点，返回逆序父节点
        if (category.getParentCid() != 0) {
            findParentCatePath(category.getParentCid(), paths);
        }
        return paths;
    }


    /**
     * 递归查找当前菜单的所有子菜单
     * 每一步三件事：找出当前子菜单、找出下级子菜单、下级子菜单排序
     *
     * @return 当前菜单
     */
    private List<CategoryEntity> getCategoryChildren(CategoryEntity root, List<CategoryEntity> all) {
        // 递归查出子菜单，并且在同级菜单之间排序
        return all.stream().filter(categoryEntity -> root.getCatId().equals(categoryEntity.getParentCid()))
                .peek(categoryEntity -> categoryEntity.setChildren(getCategoryChildren(categoryEntity, all)))
                .sorted(Comparator.comparingInt(CategoryEntity::getSort)).collect(Collectors.toList());
    }

}
