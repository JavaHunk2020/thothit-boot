package com.techtech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
	//	throw new DogNotFoundException("Hey! it seems like this dog does not exist!");
	@ExceptionHandler(DogNotFoundException.class)
	public ResponseEntity<ErrorMessage> generateIt(DogNotFoundException ex){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setMessage(ex.getMessage());
		errorMessage.setCode("C0192");
		return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> accessDenied(AccessDeniedException ex){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setMessage(ex.getMessage());
		errorMessage.setCode("C403");
		return new ResponseEntity<>(errorMessage,HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> generateIt(Exception ex){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setMessage(ex.getMessage());
		errorMessage.setCode("C500");
		return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
