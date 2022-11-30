package com.company.mallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallproduct.entity.AttrEntity;
import com.company.mallproduct.vo.AttrRespVO;
import com.company.mallproduct.vo.AttrVO;

import java.util.Map;

/**
 * 商品属性
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:10
 */
public interface AttrService extends IService<AttrEntity> {

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

    AttrRespVO getAttrInfo(Long attrId);

    void updateAttr(AttrVO attr);
}

