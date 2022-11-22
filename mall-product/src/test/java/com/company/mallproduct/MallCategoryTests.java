package com.company.mallproduct;

import com.company.mallproduct.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Real
 * Date: 2022/11/23 1:19
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MallCategoryTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void categoryPathTest() {
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("Complete path is {}", catelogPath);
    }

}
