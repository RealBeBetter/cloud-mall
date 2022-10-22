package com.company.mallorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallorder.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 12:55:17
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

