package com.apps.fundtransfer.users.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundException extends Exception {

	private String message;
	private HttpStatus status;
}
