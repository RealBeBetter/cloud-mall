package com.company.mallproduct.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.mallproduct.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:04
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
