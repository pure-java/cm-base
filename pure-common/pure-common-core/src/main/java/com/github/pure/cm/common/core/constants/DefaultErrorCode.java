package com.github.pure.cm.common.core.constants;

/**
 * @author bairitan
 * @date 2020/8/16
 */
public enum DefaultErrorCode implements ExceptionCode {
    /**
     * success_200<br>
     * 成功
     */
    SUCCESS_200("成功", 200),

    /**
     * 错误 500
     */
    ERROR_500("成功", 500),

    /**
     * NOT_FOUND_404<br>
     * 未找到
     */
    NOT_FOUND_404("Not Found", 404),

    /**
     * system_error_10500<br>
     * 系统错误:10500
     */
    SYSTEM_ERROR_10500("系统错误", 10500),

    /**
     * param_valid_error_10501<br>
     * 参数错误:10501
     */
    PARAM_VALID_ERROR_10501("参数错误", 10501),
    ;
    /**
     * 值
     */
    private final int code;
    /**
     * 名称
     */
    private final String desc;

    DefaultErrorCode(String desc, int code) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
