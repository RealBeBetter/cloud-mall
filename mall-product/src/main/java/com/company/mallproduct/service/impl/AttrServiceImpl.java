package com.company.mallproduct.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.mallcommon.constant.ProductConstant;
import com.company.mallcommon.utils.PageUtils;
import com.company.mallcommon.utils.Query;
import com.company.mallproduct.dao.AttrAttrgroupRelationDao;
import com.company.mallproduct.dao.AttrDao;
import com.company.mallproduct.dao.AttrGroupDao;
import com.company.mallproduct.dao.CategoryDao;
import com.company.mallproduct.entity.AttrAttrgroupRelationEntity;
import com.company.mallproduct.entity.AttrEntity;
import com.company.mallproduct.entity.AttrGroupEntity;
import com.company.mallproduct.entity.CategoryEntity;
import com.company.mallproduct.service.AttrAttrgroupRelationService;
import com.company.mallproduct.service.AttrService;
import com.company.mallproduct.service.CategoryService;
import com.company.mallproduct.vo.AttrGroupRelationVO;
import com.company.mallproduct.vo.AttrRespVO;
import com.company.mallproduct.vo.AttrVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Real
 * @date 2023/02/06 20:40
 */
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Resource
    private AttrGroupDao attrGroupDao;
    @Resource
    private AttrDao attrDao;
    @Resource
    private CategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    private static final String BASE = "base";

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttr(AttrVO attr) {
        // ??????????????????????????????
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // ???????????????
        this.save(attrEntity);

        if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() == attr.getAttrType()) {
            // ??????????????????????????????????????????????????????
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId()).setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        LambdaQueryWrapper<AttrEntity> queryWrapper = Wrappers.<AttrEntity>lambdaQuery()
                .eq(AttrEntity::getAttrType, BASE.equals(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        // ??????????????????
        if (catelogId != 0) {
            queryWrapper.eq(AttrEntity::getCatelogId, catelogId);
        }
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key);
        }
        // ????????????????????????
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        List<AttrEntity> attrEntities = page.getRecords();
        // ???????????????????????????
        List<AttrRespVO> attrRespVOList = attrEntities.stream().map(attrEntity -> {
            AttrRespVO attrRespVO = new AttrRespVO();
            BeanUtils.copyProperties(attrEntity, attrRespVO);
            // ??????????????????????????????
            if (BASE.equalsIgnoreCase(type)) {
                AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(Wrappers.<AttrAttrgroupRelationEntity>lambdaQuery()
                        .eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
                if (ObjectUtils.isNotNull(relationEntity)) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    attrRespVO.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (ObjectUtils.isNotNull(categoryEntity)) {
                attrRespVO.setCatelogName(categoryEntity.getName());
            }
            return attrRespVO;
        }).collect(Collectors.toList());
        // ?????????????????????
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrRespVOList);
        return pageUtils;
    }

    @Override
    public AttrRespVO getAttrInfo(Long attrId) {
        AttrRespVO attrRespVO = new AttrRespVO();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrRespVO);
        // ?????????????????????????????????????????????????????????????????????
        if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() == attrEntity.getAttrType()) {
            // ??????????????????????????????
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(Wrappers.<AttrAttrgroupRelationEntity>lambdaQuery().eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
            if (ObjectUtils.isNotNull(relationEntity)) {
                attrRespVO.setAttrGroupId(relationEntity.getAttrGroupId());
                AttrGroupEntity groupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                attrRespVO.setGroupName(ObjectUtils.isNull(groupEntity) ? null : groupEntity.getAttrGroupName());
            }
        }
        // ??????????????????
        Long cateLogId = attrEntity.getCatelogId();
        Long[] cateLogPath = categoryService.findCatelogPath(cateLogId);
        attrRespVO.setCatelogPath(cateLogPath);
        // ??????????????????
        CategoryEntity categoryEntity = categoryService.getById(cateLogId);
        attrRespVO.setCatelogName(ObjectUtils.isNull(categoryEntity) ? null : categoryEntity.getName());
        return attrRespVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttr(AttrVO attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);
        if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() == attrEntity.getAttrType()) {
            // ????????????????????????
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId()).setAttrId(attr.getAttrId());
            Wrapper<AttrAttrgroupRelationEntity> updateWrapper = Wrappers.<AttrAttrgroupRelationEntity>lambdaUpdate()
                    .eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId());
            attrAttrgroupRelationService.saveOrUpdate(relationEntity, updateWrapper);
        }
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.getBaseMapper().selectList(Wrappers.<AttrAttrgroupRelationEntity>lambdaQuery()
                .eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId));

        if (CollectionUtil.isEmpty(relationEntities)) {
            return Collections.emptyList();
        }

        List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        return attrDao.selectBatchIds(attrIds);
    }

    @Override
    public void deleteAttrGroupRelations(List<AttrGroupRelationVO> attrGroupRelations) {
        if (CollectionUtil.isEmpty(attrGroupRelations)) {
            return;
        }

        attrAttrgroupRelationDao.deleteBatchRelations(attrGroupRelations);
    }

}
