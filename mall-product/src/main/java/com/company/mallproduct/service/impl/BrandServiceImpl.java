package com.company.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallcommon.utils.Query;
import com.company.mallproduct.dao.BrandDao;
import com.company.mallproduct.entity.BrandEntity;
import com.company.mallproduct.service.BrandService;
import com.company.mallproduct.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 添加模糊查询功能
        String key = (String) params.get("key");
        // 模糊查询名称或者 id 搜索
        Wrapper<BrandEntity> wrapper = Wrappers.<BrandEntity>lambdaQuery().eq(BrandEntity::getBrandId, key).or().like(BrandEntity::getName, key);
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params), wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateBrand(BrandEntity brandEntity) {
        // 更新Brand时实现参数更新，在业务层中操作
        this.updateById(brandEntity);
        // 需要更新关联表中的数据，保证冗余字段的数据一致性
        if (StringUtils.isNotEmpty(brandEntity.getName())) {
            // 更新关联表中的品牌信息
            categoryBrandRelationService.updateBrand(brandEntity.getBrandId(), brandEntity.getName());
        }
    }

}
