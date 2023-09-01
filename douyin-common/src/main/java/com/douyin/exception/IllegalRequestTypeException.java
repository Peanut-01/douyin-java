package com.douyin.exception;

/**
 * 登录失败
 */
public class IllegalRequestTypeException extends BaseException {
    public IllegalRequestTypeException(String msg){
        super(msg);
    }
}
