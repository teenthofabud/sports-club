package org.footballclub.footballclubmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClubDetailsValidationExceptions.class)
    public ResponseEntity<ErrorResponse> handleClubValidations(ClubDetailsValidationExceptions e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClubNotFound(ClubNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),404);
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalExceptions(Exception e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 500);
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
