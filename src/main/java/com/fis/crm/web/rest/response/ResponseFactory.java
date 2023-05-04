package com.fis.crm.web.rest.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ResponseFactory {

    /**
     * Format of response returned to client in case success
     *
     * @param data response data
     * @param clazz java class
     * @return ResponseEntity
     * @version 1.0
     */
    public ResponseEntity<Object> success(Object data, Class<?> clazz) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCode.SUCCESS);
        responseObject.setStatus(responseStatus);
        if (data instanceof Collection) {
            responseObject.setData(((Collection) data).stream().collect(Collectors.toList()));
        } else {
            responseObject.setData(clazz.cast(data));
        }
        return ResponseEntity.ok(responseObject);
    }

    public ResponseEntity<Object> failure(Object data, Class<?> clazz) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCode.FAILURE);
        responseObject.setStatus(responseStatus);
        if (data instanceof Collection) {
            responseObject.setData(((Collection) data).stream().collect(Collectors.toList()));
        } else {
            responseObject.setData(clazz.cast(data));
        }
        return ResponseEntity.ok(responseObject);
    }







    /**
     * successMessage Format of response returned to client in case success
     * @param responseStatusCode response status code
     * @param data response data
     * @param clazz class
     * @return ResponseEntity
     * @version 1.0
     */
    public ResponseEntity<Object> successMessage(ResponseStatusCode responseStatusCode ,Object data, Class<?> clazz) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus(responseStatusCode);
        responseObject.setStatus(responseStatus);
        if (data instanceof Collection) {
            responseObject.setData(((Collection) data).stream().collect(Collectors.toList()));
        } else {
            responseObject.setData(clazz.cast(data));
        }
        return ResponseEntity.ok(responseObject);
    }

    /**
     * Format of response returned to client in case fail
     *
     * @param data response data
     * @param clazz class
     * @return ResponseEntity
     * @version 1.0
     */


    /**
     * Format of response returned to client in case success
     *
     * @param clazz class
     * @return ResponseEntity
     * @version 1.0
     */
    public ResponseEntity<Object> success(Class<?> clazz) {
        ResponseNoBody<Object> responseObject = new ResponseNoBody<>();
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCode.SUCCESS);
        responseObject.setStatus(responseStatus);
        return ResponseEntity.ok(responseObject);
    }

    /**
     * Format of response returned to client in case fail
     *
     * @param clazz class
     * @return ResponseEntity
     * @version 1.0
     */
    public ResponseEntity<Object> fail(Class<?> clazz, ResponseStatusCode responseStatusEnum) {
        ResponseNoBody<Object> responseObject = new ResponseNoBody<>();
        ResponseStatus responseStatus = new ResponseStatus(responseStatusEnum);
        responseObject.setStatus(responseStatus);
        return ResponseEntity.status(responseStatusEnum.getHttpCode()).body(responseObject);
    }

    /**
     * Format of response returned to client in case fail
     *
     * @return ResponseEntity
     * @version 1.0
     */
    public ResponseEntity<Object> failBusiness(String code, String message) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(code);
        responseStatus.setMessage(message);
        responseObject.setStatus(responseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    public ResponseEntity<Object> failBusinessWithData(Object data, Class<?> clazz, String code, String message) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(code);
        responseStatus.setMessage(message);
        responseObject.setStatus(responseStatus);

        if (data instanceof Collection) {
            responseObject.setData(((Collection) data).stream().collect(Collectors.toList()));
        } else {
            responseObject.setData(clazz.cast(data));
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

}
