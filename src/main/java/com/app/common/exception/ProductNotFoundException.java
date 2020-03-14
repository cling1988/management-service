package com.app.common.exception;

public class ProductNotFoundException extends RuntimeException  {

    public ProductNotFoundException(Long id){
        super("Could not found product for "+id);
    }

}
