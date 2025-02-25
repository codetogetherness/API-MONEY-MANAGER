package com.backend_api.money_manager.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
public class ResponseHandlerWithPagination {
    private Integer code;
    private HttpStatus status;
    private String message;
    private Data data;
    private Object error;

    @Setter
    @Getter
    public static class Data {
        private Object data;
        private InfoPage page;
    }


    @Setter
    @Getter
    public static class InfoPage {
        private int totalPage;
        private int offset;
        private int limit;
        private int currentPage;

        public int getCurrentPage() {
            return currentPage + 1;
        }


    }

    public static ResponseEntity<Object> generateResponseSuccess(Data value) {
        var data = new ResponseHandlerWithPagination();

        data.setCode(HttpStatus.OK.value());
        data.setData(value);
        data.setMessage("success");
        data.setStatus(HttpStatus.OK);


        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
