package com.backend_api.money_manager.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
public class ResponseHandler {
    private Integer code;
    private HttpStatus status;
    private String message;
    private Object data;
    private Object error;


    public static ResponseEntity<Object> generateResponseSuccess(Object responseObj) {
        var data = new ResponseHandler();
        data.setCode(HttpStatus.OK.value());
        data.setData(responseObj);
        data.setMessage("success");
        data.setStatus(HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }



    public static ResponseEntity<Object> generateResponseError(HttpStatus status,Object error,String message) {
        var data = new ResponseHandler();
        data.setCode(status.value());
        data.setStatus(status);
        data.setMessage(message);
        data.setError(error);

        return ResponseEntity.status(status).body(data);
    }

}
