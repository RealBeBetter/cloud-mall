package com.company.mallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallproduct.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 13:11:56
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

