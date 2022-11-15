package com.penta.aiwmsbackend.model.bean;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @JsonFormat( pattern = "yyyy-mm-dd")
    private Date timestamp;
    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String reason;
    private String message;
}
