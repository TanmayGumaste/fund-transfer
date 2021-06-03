package com.apps.fundtransfer.users.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class LoginResponseModel implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3369635853784043402L;
	String firstName;
	String lastName;
	String email;	
	String accountId;
	Double Balance;

}
