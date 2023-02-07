package com.company.mallproduct.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.mallproduct.entity.AttrAttrgroupRelationEntity;
import com.company.mallproduct.vo.AttrGroupRelationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:10
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    /**
     * 批处理删除关系
     *
     * @param attrGroupRelations attr分组关系
     */
    void deleteBatchRelations(@Param("relations") List<AttrGroupRelationVO> attrGroupRelations);
}
