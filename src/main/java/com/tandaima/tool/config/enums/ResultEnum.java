package com.tandaima.tool.config.enums;

public enum ResultEnum {
    /** 系统相关 */
    SUCCESS(200, "成功"),
    SUCCESS_NOT_DATA(201, "成功"),
    FAIL(500, "失败"),
    /** 请求加载中 */
    REQ_LOADING(500, "正在请求中,请稍后再试"),

    /** 账号相关 */
    LOGIN_EXCEED(206,"登录过期,请重新登录"),
    ;
    private Integer code;
    private String msg;
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //重新toString方法，默认的toString方法返回的就是枚举变量的名字，和name()方法返回值一样
    @Override
    public String toString()
    {
        return this.code+":"+this.msg;
    }
}
