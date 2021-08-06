package com.example.Second.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class RecordExitException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public RecordExitException(String message)
    {
        super(message);
    }
    public RecordExitException(String message,Throwable cause) {
        super(message, cause);
    }
    @ExceptionHandler(RecordExitException.class)
    public ResponseEntity<?> handlerRecordExistException (RecordExitException rnfe, HttpServletRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.CONFLICT.value());
        errorDetail.setTitle("Another Record Exit With Same Title");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.CONFLICT);
    }
}
