package com.company.mallproduct;

import com.company.mallproduct.entity.BrandEntity;
import com.company.mallproduct.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setDescript("华为-Brand-Description").setName("华为");
        brandService.save(brandEntity);
        log.info("保存成功");
    }

}
