package com.company.mallware.dao;

import com.company.mallware.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 13:00:57
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
