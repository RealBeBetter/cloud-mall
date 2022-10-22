package com.company.mallmember.dao;

import com.company.mallmember.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 12:48:10
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
