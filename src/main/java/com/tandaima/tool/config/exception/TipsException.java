package com.tandaima.tool.config.exception;

/**
 * 提示异常
 *
 * @author zbrcel@gmail.com
 */
public final class TipsException extends RuntimeException
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
    public TipsException()
    {
    }

    public TipsException(String message)
    {
        this.message = message;
    }

    public TipsException(String message, Integer code)
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
