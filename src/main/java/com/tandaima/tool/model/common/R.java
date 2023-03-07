package com.tandaima.tool.model.common;

import com.tandaima.tool.config.enums.ResultEnum;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author zbrcel@gmail.com
 */
public class R<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = ResultEnum.SUCCESS.getCode();

    /** 失败 */
    public static final int FAIL = ResultEnum.FAIL.getCode();

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok()
    {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data)
    {
        return restResult(data, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail()
    {
        return restResult(null, FAIL, "操作失败");
    }

    public static <T> R<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data)
    {
        return restResult(data, FAIL, "操作失败");
    }

    public static <T> R<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg)
    {
        return restResult(null, code, msg);
    }

    public static  <T>R<T> ofRow(int row) {
    return row>0?R.success():R.error();
}
    public static  <T>R<T> ofBoolean(boolean flag) {
        return flag?R.success():R.error();
    }
    public static  <T> R<T> success(T data) {
        return result(data, ResultEnum.SUCCESS);
    }
    public static  <T> R<T> notData() {
        return result(null, ResultEnum.SUCCESS_NOT_DATA);
    }
    public static  <T> R<T> ofSuccess(String msg) {
        return result(null, ResultEnum.SUCCESS.getCode(),msg);
    }
    public static  <T> R<T> ofSuccess(String msg,T data) {
        return result(data,ResultEnum.SUCCESS.getCode(),msg);
    }
    public static  <T> R<T> success() {
        return result(ResultEnum.SUCCESS);
    }
    public static  <T>R<T> error() {
        return result(ResultEnum.FAIL);
    }
    public static  <T>R<T> error(T data) {
        return result(data,ResultEnum.FAIL);
    }
    public static  <T>R<T> of(ResultEnum result) {
        return result(result);
    }
    public static  <T>R<T> of(Object baseResultEnum) {
        return result(baseResultEnum);
    }
    public static  <T>R<T> ofError(String msg) {
        return result(null,ResultEnum.FAIL.getCode(),msg);
    }
    public static  <T>R<T> of(T data, ResultEnum result) {
        return result(data, result);
    }
    public static  <T>R<T> of(int code,String msg, T data) {
        return result(data,code,msg);
    }

    public static  <T>R<T> of(int code,String msg) {
        return result(null,code,msg);
    }


    private static <T> R<T> restResult(T data, int code, String msg)
    {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private static <T> R<T> result(T data, int code, String msg)
    {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private static <T> R<T> result(ResultEnum resultEnum)
    {
        R<T> apiResult = new R<>();
        apiResult.setCode(resultEnum.getCode());
        apiResult.setData(null);
        apiResult.setMsg(resultEnum.getMsg());
        return apiResult;
    }

    private static <T> R<T> result(Object baseResultEnum)
    {
        ResultEnum resultEnum = ResultEnum.SUCCESS;
        BeanUtils.copyProperties(baseResultEnum,resultEnum);
        R<T> apiResult = new R<>();
        apiResult.setCode(resultEnum.getCode());
        apiResult.setData(null);
        apiResult.setMsg(resultEnum.getMsg());
        return apiResult;
    }

    private static <T> R<T> result(T data,ResultEnum resultEnum)
    {
        R<T> apiResult = new R<>();
        apiResult.setCode(resultEnum.getCode());
        apiResult.setData(data);
        apiResult.setMsg(resultEnum.getMsg());
        return apiResult;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public static <T> Boolean isError(R<T> ret)
    {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret)
    {
        return R.SUCCESS == ret.getCode();
    }
}
