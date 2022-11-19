package com.company.mallproduct.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.company.mallcommon.valid.AddGroup;
import com.company.mallcommon.valid.ListValue;
import com.company.mallcommon.valid.UpdateGroup;
import com.company.mallcommon.valid.UpdateStatusGroup;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 * ① 添加注解校验数据，参考 {@link javax.validation.constraints.NotBlank}
 * ② 使用 {@link javax.validation.Valid} 注解标注入参
 * ③ 使用 {@link org.springframework.validation.BindingResult} 获取校验的结果，进行处理
 *
 * @author Real
 * @email swrely@qq.com
 * @date 2022-10-22 01:43:10
 */
@Data
@Accessors(chain = true)
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @Null(message = "新增不能指定品牌Id", groups = {AddGroup.class})
    @NotNull(message = "修改必须指定品牌Id", groups = {UpdateGroup.class})
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotBlank(groups = {AddGroup.class})
    @URL(message = "Logo 必须为合法的 URL 地址", groups = {AddGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
    @ListValue(values = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索时首字母必须为字母", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0, message = "排序必须 >= 0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}
