package com.company.mallproduct.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空")
    private String name;
    /**
     * 品牌logo地址
     */
    @URL(message = "Logo 必须为合法的 URL 地址")
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty
    @Pattern(regexp = "/^[a-zA-Z]$/", message = "检索时首字母必须为字母")
    private String firstLetter;
    /**
     * 排序
     */
    @NotBlank
    @Min(value = 0, message = "排序必须 >= 0")
    private Integer sort;

}
