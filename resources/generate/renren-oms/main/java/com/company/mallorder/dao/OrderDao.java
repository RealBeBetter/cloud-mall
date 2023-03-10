package com.company.mallorder.dao;

import com.company.mallorder.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 12:55:17
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
