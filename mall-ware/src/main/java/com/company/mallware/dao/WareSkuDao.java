package com.company.mallware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.mallware.entity.WareSkuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 13:00:57
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

}
