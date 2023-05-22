package com.fis.crm.crm_controller;


import com.fis.crm.crm_entity.DTO.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointer(Exception ex){
        ex.printStackTrace();
        return new Result("404","NOT FOUND","");
    }

}
