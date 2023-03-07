package com.tandaima.tool.config.exception;

/**
 * 描述：功能描述
 * 创建时间：2023/3/6
 *
 * @author zbrcel@gmail.com
 */
public class AssertException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public AssertException()
    {
    }

    public AssertException(String message)
    {
        this.message = message;
    }

    public AssertException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }
}
