package com.company.mallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 *
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 13:00:57
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

