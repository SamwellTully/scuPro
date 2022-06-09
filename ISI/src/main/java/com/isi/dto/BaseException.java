package com.isi.dto;

public class BaseException extends RuntimeException{
    public BaseException(){}

    public BaseException(String msg){
        super(msg);
    }

    public BaseException(String msg,Throwable cause){
        super(msg,cause);
    }

    public BaseException(Throwable cause){
        super(cause);
    }

    public BaseException(String message, Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
