package com.isi.config;

import com.isi.dto.BaseException;
import com.isi.dto.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public R serviceErrorHandler(BaseException e){
        return R.serviceError(e.getMessage());
    }
}
