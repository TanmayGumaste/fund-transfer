package com.apps.fundtransfer.users.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apps.fundtransfer.users.model.FundException;
import com.apps.fundtransfer.users.model.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FundException.class)
	public ResponseEntity<?> handleCustomException(FundException exp){
		return new ResponseEntity<>(new Response(exp.getMessage()),exp.getStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exp){
		return new ResponseEntity<>(new Response("Oops...Something went wrong"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
