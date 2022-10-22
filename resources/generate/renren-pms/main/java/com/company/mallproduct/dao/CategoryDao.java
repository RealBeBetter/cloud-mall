package com.company.mallproduct.dao;

import com.company.mallproduct.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 13:11:56
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
