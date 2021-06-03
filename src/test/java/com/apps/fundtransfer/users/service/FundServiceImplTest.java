package com.apps.fundtransfer.users.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apps.fundtransfer.users.data.Fundrepository;
import com.apps.fundtransfer.users.data.UserEntity;
import com.apps.fundtransfer.users.model.CreateUserRequestModel;
import com.apps.fundtransfer.users.shared.UserDto;
@ExtendWith(MockitoExtension.class)
public class FundServiceImplTest {
	

	@Mock
	Fundrepository  fundrepository;
	
	@InjectMocks
	FundServiceImpl fundService;
	
	static UserDto userDto;
	static UserEntity userEntity;
	static CreateUserRequestModel userDetails;
    static BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
 

	
	@BeforeAll
	public static void setup() {
		userDetails=new CreateUserRequestModel();
		userEntity=new UserEntity();
		userDto=new UserDto();
		userDetails.setFirstName("abc");
		userDetails.setLastName("def");
		userDetails.setEmail("abc@test.com");
		userDetails.setPassword("123456789");
		
		userEntity.setEmail("abc@test.com");
		userEntity.setFirstName("abc");
		userEntity.setLastName("def");
		userEntity.setEncryptedPassword("hashedPassword");
		userEntity.setUserId("abcd");
		
	    when(bCryptPasswordEncoder.encode(userDetails.getPassword())).thenReturn("hashedPassword");
	  
	    }
	
    @Test
    @DisplayName("createUser: Positive Scenario")
    public void createUserTest(){
        when(fundrepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
    	UserEntity userEntity=fundService.createUser(userDetails);
    	//verify(fundrepository.save(userEntity));
    	assertNotNull(userEntity);
    }

}
