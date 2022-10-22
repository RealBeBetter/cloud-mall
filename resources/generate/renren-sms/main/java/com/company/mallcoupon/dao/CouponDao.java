package com.company.mallcoupon.dao;

import com.company.mallcoupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 13:07:28
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
