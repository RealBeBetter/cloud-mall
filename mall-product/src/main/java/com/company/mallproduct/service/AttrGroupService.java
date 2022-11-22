package com.company.mallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallproduct.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:10
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);
}

