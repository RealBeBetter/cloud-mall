package com.company.mallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallproduct.entity.AttrEntity;
import com.company.mallproduct.vo.AttrGroupRelationVO;
import com.company.mallproduct.vo.AttrRespVO;
import com.company.mallproduct.vo.AttrVO;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:10
 */
public interface AttrService extends IService<AttrEntity> {

    /**
     * 查询页面
     *
     * @param params 参数个数
     * @return {@link PageUtils}
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存Attr属性
     *
     * @param attr attr属性VO对象
     */
    void saveAttr(AttrVO attr);

    /**
     * 查询基本属性的分页
     *
     * @param params    参数
     * @param catelogId 分类ID
     * @param type      查询类型，分为 Sale 和 Base
     * @return 分页对象
     */
    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    /**
     * 得到attr信息
     *
     * @param attrId attr id
     * @return {@link AttrRespVO}
     */
    AttrRespVO getAttrInfo(Long attrId);

    /**
     * 更新attr
     *
     * @param attr attr
     */
    void updateAttr(AttrVO attr);

    /**
     * 获取关系属性表
     *
     * @param attrGroupId 分组ID
     * @return {@link List}<{@link AttrEntity}>
     */
    List<AttrEntity> getRelationAttr(Long attrGroupId);

    /**
     * 删除attr分组关系
     *
     * @param attrGroupRelations attr分组关系
     */
    void deleteAttrGroupRelations(List<AttrGroupRelationVO> attrGroupRelations);
}

