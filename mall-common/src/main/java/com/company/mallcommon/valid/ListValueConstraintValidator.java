package com.company.mallcommon.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Real
 * Date: 2022/11/19 17:22
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {


    private final Set<Integer> VALUES = new HashSet<>();

    /**
     * 初始化数据信息
     *
     * @param constraintAnnotation 表示注解
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        // 获取注解使用时配置的 values 值
        int[] values = constraintAnnotation.values();
        for (int value : values) {
            VALUES.add(value);
        }
    }

    /**
     * 判断是否合法
     *
     * @param integer                    整数值
     * @param constraintValidatorContext 约束验证上下文
     * @return 是否通过验证
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return VALUES.contains(integer);
    }
}
