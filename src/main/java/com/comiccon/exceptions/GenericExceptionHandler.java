package com.comiccon.exceptions;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.comiccon.exceptions.ErrorResponse.ErrorResponseBuilder;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GenericExceptionHandler {
	
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> handleGenericEx(AppException exception,HttpServletRequest request){
		return buildResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception,HttpServletRequest request){
		return buildResponse(exception, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	public ResponseEntity<ErrorResponse> handleInvalidEx(InvalidOperationException exception,HttpServletRequest request){
		return buildResponse(exception, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleResourceAlredyExistsEx(ResourceAlreadyExistsException exception,HttpServletRequest request){
		return buildResponse(exception, HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundEx(ResourceNotFoundException exception,HttpServletRequest request){
		return buildResponse(exception, HttpStatus.NOT_FOUND, request);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationEx(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Invalid input data")
                .path(request.getRequestURI()) // 3. Added Path
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericEx(Exception ex,HttpServletRequest request){
		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(ex.getMessage())
				.error("Internal Server Error")
				.path(request.getRequestURI())
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	
	
	
	//method for building error response
	private ResponseEntity<ErrorResponse> buildResponse(AppException exception,HttpStatus status,HttpServletRequest request){
		
		ErrorResponse errorResponse = ErrorResponse.builder()
					.timestamp(LocalDateTime.now())
					.status(status.value())
					.error(status.getReasonPhrase())
					.message(exception.getMessage())
					.details(exception.getDeatil())
					.path(request.getRequestURI())
					.build();
		
		
		return ResponseEntity.status(status).body(errorResponse);
	}
}
