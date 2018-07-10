package com.schedular.app.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice  //shared among controllers
@RestController
public class CustomizedResponseEntityExceptionHandler 
	extends ResponseEntityExceptionHandler{

	//use for all exception calls
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptions(Exception ex,
			WebRequest request) throws Exception {
		
		ExceptionResponse  exceptionResponse = new ExceptionResponse(new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Use for Resource not found Exceptions
		@ExceptionHandler(ResourceNotFoundException.class)
		public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex,
				WebRequest request) throws Exception {
			
			ExceptionResponse  exceptionResponse = new ExceptionResponse(new Date(),
					ex.getMessage(),
					request.getDescription(false));
			
			return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);
		}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	
			ExceptionResponse  exceptionResponse = new ExceptionResponse(new Date(),
					"Validation failed",
					ex.getBindingResult().toString());//ex.getBindingResult():you can customize to get only the message;
			
			return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
}
