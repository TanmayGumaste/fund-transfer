package com.apps.fundtransfer.users.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apps.fundtransfer.users.data.Beneficiary;
import com.apps.fundtransfer.users.data.UserEntity;
import com.apps.fundtransfer.users.model.CreateResponseModel;
import com.apps.fundtransfer.users.model.CreateUserRequestModel;
import com.apps.fundtransfer.users.model.FundException;
import com.apps.fundtransfer.users.model.LoginRequestModel;
import com.apps.fundtransfer.users.model.LoginResponseModel;
import com.apps.fundtransfer.users.model.TransferBalanceRequest;
import com.apps.fundtransfer.users.service.FundService;
import com.apps.fundtransfer.users.shared.UserDto;

@ExtendWith(MockitoExtension.class)
public class FundControllerTest {
	
	@Mock 
	FundService fundService;
	
	@InjectMocks
	FundController fundController;
	static CreateUserRequestModel userdetails;
	static LoginRequestModel login;
	static TransferBalanceRequest transferBalanceRequest;
	static Beneficiary beneficiary;
	
	static UserDto userDto;
	static UserEntity userEntity;
	
	static LoginResponseModel loginResponse;
	@BeforeAll
	public static void setup() {
		userdetails=new CreateUserRequestModel();
		userDto=new UserDto();
		userEntity=new UserEntity();
		loginResponse=new LoginResponseModel();
		transferBalanceRequest=new TransferBalanceRequest();
		beneficiary=new Beneficiary();
		userdetails.setFirstName("abc");
		userdetails.setLastName("def");
		userdetails.setEmail("abc@test.com");
		userdetails.setPassword("123456789");
		
		userEntity.setEmail("abc@test.com");
		userEntity.setFirstName("abc");
		userEntity.setLastName("def");
		
		
		userDto.setFirstName("abc");
		userDto.setLastName("def");
		userDto.setUserId("237488");
		userDto.setEncryptedPassword("jdbdshbfy4374#$");
		userDto.setPassword("sdfdsfds");
		login=new LoginRequestModel();
		login.setEmail("test@abc.com");
		login.setPassword("ggfft678@");
		loginResponse.setEmail("test@abc.com");
		
		transferBalanceRequest.setEmail("test@abc.com");
		beneficiary.setAccountNo(101);
		beneficiary.setEmail("test@abc.com");
		
	}
    @Test
    @DisplayName("Sign Up Function: Positive Scenario")
	public void createUserTest() {
		// given or context
		// when(userService.saveUserDetails(userRequestDto)).thenReturn(true);
		when(fundService.createUser(userdetails)).thenReturn(userEntity);
		//doNothing().when(fundService).login(loginRequestModel);
		ResponseEntity<CreateResponseModel> result = fundController.createUser(userdetails);
		verify(fundService).createUser(userdetails);
		assertNotNull(result);
		assertEquals("abc", result.getBody().getFirstName());

	}
    @Test
    @DisplayName("Sign Up Function: Negative Scenario")
	public void createUserTestNegative() {
		// given or context
		// when(userService.saveUserDetails(userRequestDto)).thenReturn(true);
    	UserEntity userEntityn=new UserEntity();
		when(fundService.createUser(userdetails)).thenReturn(userEntityn);
		//doNothing().when(fundService).login(loginRequestModel);
		ResponseEntity<CreateResponseModel> result = fundController.createUser(userdetails);
		verify(fundService).createUser(userdetails);
		assertNull(result.getBody().getEmail());

	}
	@Test
	@DisplayName("Login Function: Positive Scenario")
	public void loginTest() throws FundException {
		when(fundService.login(login)).thenReturn(loginResponse);
		ResponseEntity<LoginResponseModel> returnLogin=fundController.userLogin(login);
		verify(fundService).login(login);
		assertEquals("test@abc.com", returnLogin.getBody().getEmail());
	}
	
	@Test
	@DisplayName("Login Function: Negative Scenario")
	public void loginTestFailure() throws FundException {
		when(fundService.login(login)).thenReturn(loginResponse);
		ResponseEntity<LoginResponseModel> returnLogin=fundController.userLogin(login);
		verify(fundService).login(login);
		assertNotEquals("testui@abc.com", returnLogin.getBody().getEmail());
}
	@Test
	@DisplayName("Fund Transfer: Positive Scenario")
	public void fundTransferTest() throws FundException {
		doNothing().when(fundService).fundTransfer(transferBalanceRequest);
		ResponseEntity<?>returnFundTransfer=fundController.fundTransfer(transferBalanceRequest);
		verify(fundService).fundTransfer(transferBalanceRequest);
		assertEquals(200, returnFundTransfer.getStatusCode().value());
	}
	
	@Test
	@DisplayName("Fund Transfer: Negative Scenario")
	public void fundTransferTestNegative() throws FundException {
		FundException fundException = new FundException("something Went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		doThrow(fundException).when(fundService).fundTransfer(transferBalanceRequest);
		try {
			fundController.fundTransfer(transferBalanceRequest);
		} catch (FundException ex) {
			assertNotEquals(HttpStatus.OK, ex.getStatus());
		}
	}
	@Test
	@DisplayName("Add Beneficaiary: Positive Scenario")
	public void addBeneficiaryTest() throws FundException {
		doNothing().when(fundService).addBeneficary(beneficiary);
		ResponseEntity<?> returnFundTransfer=fundController.addBeneficiary(beneficiary);
		verify(fundService).addBeneficary(beneficiary);
		assertEquals(200, returnFundTransfer.getStatusCodeValue());
		
	}
	

	
}
