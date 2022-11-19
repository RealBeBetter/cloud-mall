package com.company.mallcommon.exception;

/**
 * @author Real
 * Date: 2022/11/19 13:29
 */
public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "系统未知错误"),
    VALID_EXCEPTION(10001, "参数格式校验错误"),
    ;

    private final int code;
    private final String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public BizCodeEnum bizCode(int code) {
        BizCodeEnum[] bizCodeEnums = values();
        for (BizCodeEnum bizCodeEnum : bizCodeEnums) {
            if (code == bizCodeEnum.code) {
                return bizCodeEnum;
            }
        }
        return null;
    }
}
