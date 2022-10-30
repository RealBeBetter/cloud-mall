package com.company.mallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallproduct.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:10
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 以树形结构查询出商品的分类
     *
     * @return List结构
     */
    List<CategoryEntity> listWithTree();
}

