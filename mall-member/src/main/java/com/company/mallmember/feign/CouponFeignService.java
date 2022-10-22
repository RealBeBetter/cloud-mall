package com.company.mallmember.feign;

import com.company.mallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Real
 * Date: 2022/10/22 14:13
 */
@Component
@FeignClient(name = "mall-coupon")
@RequestMapping("/mallcoupon/coupon")
public interface CouponFeignService {

    @RequestMapping("/member/list")
    R memberCoupons();

}
