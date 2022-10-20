package com.penta.aiwmsbackend.model.bean;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {
    private LocalDate timestamp;
    private HttpStatus httpStatus;
    private int httpStatusCode;
    private String message;
    private String reason;
    private boolean ok;
    private T data;

}
