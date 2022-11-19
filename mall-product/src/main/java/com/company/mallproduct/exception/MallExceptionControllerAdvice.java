package com.company.mallproduct.exception;

import com.company.mallcommon.exception.BizCodeEnum;
import com.company.mallcommon.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Real
 * Date: 2022/11/18 0:35
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.company.mallproduct.controller")
public class MallExceptionControllerAdvice {

    @ExceptionHandler(value = Throwable.class)
    public R handlerException(Throwable throwable) {
        log.error("系统出现异常：{}，异常类型：{}", throwable.getMessage(), throwable.getClass());
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handlerValidException(MethodArgumentNotValidException exception) {
        log.error("数据校验出现异常：{}，异常类型：{}", exception.getMessage(), exception.getClass());
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(fieldError -> errorMap.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put(errorMap);
    }

}
