package com.app.config;

import com.app.common.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BaseExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFound(ProductNotFoundException ex){
        return ex.getMessage();
    }

//    @ResponseBody
//    @ExceptionHandler(TokenException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public String jwtException(TokenException ex){
//        System.out.println(ex.getMessage());
//        return ex.getMessage();
//    }

}
