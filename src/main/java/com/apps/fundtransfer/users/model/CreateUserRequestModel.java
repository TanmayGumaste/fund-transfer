package com.apps.fundtransfer.users.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class CreateUserRequestModel {
	
	  @NotNull(message="First Name Cannot be Null")
	    @Size(min=2,message="First Name Must not less than 2 characters")
		private String firstName;
	    @NotNull(message="Last Name Cannot be Null")
	    @Size(min=2,message="Last Name Must not less than 2 characters")
		private String lastName;
	    @Email(message = "Email should be valid")
	    @NotNull(message="Email Cannot be Null")
		private String email;
		@NotNull(message="Password Cannot be Null")
		@Size(min=8,max=16,message="Password must be equal or greater than 8 character and less than 16 characters")
		private String password;

}
