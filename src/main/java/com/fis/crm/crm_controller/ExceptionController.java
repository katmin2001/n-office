package com.fis.crm.crm_controller;


import com.fis.crm.crm_entity.DTO.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointer(Exception ex){
        ex.printStackTrace();
        return new Result("404","NOT FOUND","");
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return new Result("500","Unknow error","");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDenied(Exception e){
        e.printStackTrace();
        return new Result("402","AccessDeniedException","");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public Result fileNotFound(Exception e){
        e.printStackTrace();
        return new Result("404","Không tìm thấy file yêu cầu","");
    }

    @ExceptionHandler(SQLException.class)
    public Result SQLException(Exception e){
        e.printStackTrace();
        return new Result("400","Lỗi khi truy vấn dữ liệu","");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalArgument(Exception e){
        e.printStackTrace();
        return new Result("400","Đối tượng đã tồn tại","");
    }
}
