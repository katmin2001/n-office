package com.fis.crm.config;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fis.crm.commons.Translator;
import com.fis.crm.service.dto.ExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * author: tamdx
 */
@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);


    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionMessage> handleBindExceptionMethod(Exception ex, WebRequest requset) {
        //Lay duoc danh sach cac truong bi loi
        BindingResult bindingResult = ex instanceof BindException ? ((BindException) ex).getBindingResult()
            : ((MethodArgumentNotValidException) ex).getBindingResult();
        FieldError fieldError = bindingResult.getFieldErrors().get(0);

        ExceptionMessage except = new ExceptionMessage();
        except.setStatus(HttpStatus.BAD_REQUEST.value());
        except.setEntityName(fieldError.getObjectName());
        except.setParams(fieldError.getObjectName());
        except.setErrorKey(fieldError.getObjectName() + "." + fieldError.getField() + fieldError.getCode());
        except.setMessage(fieldError.getDefaultMessage());
        except.setTitle(fieldError.getDefaultMessage());
        return new ResponseEntity<>(except, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidFormatException.class, JsonMappingException.class})
    public ResponseEntity<ExceptionMessage> handleInvalidFormatExceptionMethod(Exception ex, WebRequest requset) {
        //Lay duoc danh sach cac truong bi loi
        ExceptionMessage except = new ExceptionMessage();
        try {
            String objectName = null;
            String fieldName = null;
            if (ex instanceof InvalidFormatException) {
                InvalidFormatException except1 = (InvalidFormatException) ex;
                objectName = except1.getPath().get(0).getFrom().getClass().getSimpleName();
                fieldName = except1.getPath().get(0).getFieldName();
            } else if (ex instanceof JsonMappingException) {
                JsonMappingException except2 = (JsonMappingException) ex;
                objectName = except2.getPath().get(0).getFrom().getClass().getSimpleName();
                fieldName = except2.getPath().get(0).getFieldName();
            }

            except.setStatus(HttpStatus.BAD_REQUEST.value());
            except.setEntityName(objectName);
            except.setParams(objectName);
            except.setErrorKey(objectName + "." + fieldName + "InvalidFormat");
            except.setMessage(fieldName + " " + Translator.toLocale("common.invalidFormat"));
            except.setTitle(fieldName + " " + Translator.toLocale("common.invalidFormat"));
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            except.setTitle(Translator.toLocale("common.haveSomeInvalidFormat"));
            except.setStatus(HttpStatus.BAD_REQUEST.value());
            except.setMessage(Translator.toLocale("common.haveSomeInvalidFormat"));
            except.setErrorKey("error.haveSomeInvalidFormat");
        }
        return new ResponseEntity<>(except, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<ExceptionMessage> handleNumberFormatException(NumberFormatException ex, WebRequest requset) {
        //Lay duoc danh sach cac truong bi loi
        ExceptionMessage except = new ExceptionMessage();
        except.setStatus(HttpStatus.BAD_REQUEST.value());
        except.setEntityName("param");
        except.setParams("param");
        except.setErrorKey("param.mustBeNumber");
        except.setMessage(Translator.toLocale("error.param.mustBeNumber"));
        except.setTitle(Translator.toLocale("error.param.mustBeNumber"));
        return new ResponseEntity<>(except, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}
