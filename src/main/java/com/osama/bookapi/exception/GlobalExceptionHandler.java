package com.osama.bookapi.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.osama.bookapi.dto.ResponseStructure;
import com.osama.bookapi.entity.Book;

//Global Exception Handler → handles all exceptions in one place
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

// ===================== HANDLE ID NOT FOUND =====================

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException exception) {

        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value()); // 404
        res.setMessage(exception.getMessage());
        res.setData("Failure");

        // Return response with HTTP status
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

// ===================== HANDLE NO RECORD FOUND =====================

    @ExceptionHandler(NoRecordAvailableException.class)
    public ResponseEntity<ResponseStructure<List<Book>>> handleNRAE(NoRecordAvailableException exc) {

        ResponseStructure<List<Book>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(exc.getMessage());
        res.setData(null);

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    
 // ===================== HANDLE INVALID INPUT =====================
    
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidInput(InvalidInputException ex) {

        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(ex.getMessage());
        res.setData("Validation Failed");

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}