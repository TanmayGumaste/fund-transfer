package com.apps.fundtransfer.users.shared;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 5637851392227343060L;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String userId;

	private String encryptedPassword;

}