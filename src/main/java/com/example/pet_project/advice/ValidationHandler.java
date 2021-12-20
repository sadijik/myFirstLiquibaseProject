package com.example.pet_project.advice;

import com.example.pet_project.exeprion.DataStartAfterDataEnsException;
import com.example.pet_project.exeprion.ElementNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.*;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {





		@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

	Map<String,String> errors=new HashMap<>();

	ex.getBindingResult().getAllErrors().forEach((error)->{

		String fiedName=((FieldError)error).getField();
		String message=error.getDefaultMessage();
		errors.put(fiedName,message);

	});
	return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		}



	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noSuchElementException () {
		return new ResponseEntity<>("  такого элемента нету", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ElementNotFoundException.class)
	public ResponseEntity<String>  elementUpdateNotFoundException( ElementNotFoundException exp) {
		return new ResponseEntity<>(exp.getMessage(), HttpStatus.BAD_REQUEST);
	}
@ExceptionHandler({DataStartAfterDataEnsException.class})
	public ResponseEntity<String> dataStartAfterDataEnsException(DataStartAfterDataEnsException exp) {
	return new ResponseEntity<>(exp.getMessage(), HttpStatus.BAD_REQUEST);
}
}