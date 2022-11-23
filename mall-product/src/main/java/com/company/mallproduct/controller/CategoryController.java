package com.company.mallproduct.controller;

import com.company.mallcommon.utils.R;
import com.company.mallproduct.entity.CategoryEntity;
import com.company.mallproduct.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 商品三级分类
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:10
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类，以树形列表显示
     */
    @RequestMapping("/list/tree")
    // @RequiresPermissions("mallproduct:category:list")
    public R list(@RequestParam Map<String, Object> params) {
        List<CategoryEntity> list = categoryService.listWithTree();
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    // @RequiresPermissions("mallproduct:category:info")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);
        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("mallproduct:category:save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("mallproduct:category:update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCategory(category);
        return R.ok();
    }

    /**
     * 删除商品分类
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("mallproduct:category:delete")
    public R delete(@RequestBody Long[] catIds) {
        // 判断是否能执行删除，调用删除方法
        // categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.deleteBatchIds(Arrays.asList(catIds));
        return R.ok();
    }

}
