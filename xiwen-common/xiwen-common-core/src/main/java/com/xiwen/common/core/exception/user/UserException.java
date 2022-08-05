package com.xiwen.common.core.exception.user;

import com.xiwen.common.core.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author xiwen
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
