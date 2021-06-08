package com.apps.fundtransfer.users.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apps.fundtransfer.users.data.Balance;
import com.apps.fundtransfer.users.data.BalanceRepository;
import com.apps.fundtransfer.users.data.Beneficiary;
import com.apps.fundtransfer.users.data.BeneficiaryRepository;
import com.apps.fundtransfer.users.data.Fundrepository;
import com.apps.fundtransfer.users.data.UserEntity;
import com.apps.fundtransfer.users.model.CreateUserRequestModel;
import com.apps.fundtransfer.users.model.FundException;
import com.apps.fundtransfer.users.model.LoginRequestModel;
import com.apps.fundtransfer.users.model.TransferBalanceRequest;
import com.apps.fundtransfer.users.shared.UserDto;

@ExtendWith(MockitoExtension.class)
public class FundServiceImplTest {

	@Mock
	Fundrepository fundrepository;
	@Mock
	BalanceRepository balanceRepository;
	@Mock
	BeneficiaryRepository beneficiaryRepository;

	@InjectMocks
	FundServiceImpl fundService;

	static UserDto userDto;
	static UserEntity userEntity;
	static CreateUserRequestModel userDetails;
	static LoginRequestModel loginModel;
	static TransferBalanceRequest transferBalanceRequest;
	static BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

	static Balance balance;

	@BeforeAll
	public static void setup() {
		userDetails = new CreateUserRequestModel();
		userEntity = new UserEntity();
		userDto = new UserDto();
		balance = new Balance();
		transferBalanceRequest=new TransferBalanceRequest();

		loginModel = new LoginRequestModel();
		userDetails.setFirstName("abc");
		userDetails.setLastName("def");
		userDetails.setEmail("abc@test.com");
		userDetails.setPassword("123456789");

		userEntity.setEmail("abc@test.com");
		userEntity.setFirstName("abc");
		userEntity.setLastName("def");
		userEntity.setEncryptedPassword("hashedPassword");
		userEntity.setUserId("abcd");
		balance.setEmail("abc@test.com");
		balance.setAmount(0.0);
		when(bCryptPasswordEncoder.encode(userDetails.getPassword())).thenReturn("hashedPassword");

	}

	@Test
	@DisplayName("createUser: Positive Scenario")
	public void createUserTest() {
		when(fundrepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
		UserEntity userEntity = fundService.createUser(userDetails);
		// verify(fundrepository.save(userEntity));
		assertNotNull(userEntity);
	}

	@Test
	@DisplayName("createUser: Negative Scenario")
	public void createUserNegativeTest() {
		userEntity = new UserEntity();
		when(fundrepository.save(ArgumentMatchers.any())).thenReturn(null);
		fundService.createUser(userDetails);
		assertNull(userEntity.getEmail());
	}

	@Test
	@DisplayName("login: Positive Scenario")
	public void loginTest() throws FundException {
		loginModel.setEmail("abc@test.com");
		loginModel.setPassword("123456789");
		when(fundrepository.findByEmail("abc@test.com")).thenReturn(userEntity);
		when(bCryptPasswordEncoder.matches(loginModel.getPassword(), userEntity.getEncryptedPassword()))
				.thenReturn(true);
		when(balanceRepository.findByEmail("abc@test.com")).thenReturn(balance);
		fundService.login(loginModel);
		assertNotNull(userEntity);
	}

	@Test
	@DisplayName("login: Negative Scenario")
	public void loginTestNegative() throws FundException {
		loginModel.setEmail("abc@test.com");
		loginModel.setPassword("123456789");
		when(fundrepository.findByEmail("abc@test.com")).thenReturn(null);

		// assertThrows(FundException.class, executable)
		Assertions.assertThrows(FundException.class, () -> {
			fundService.login(loginModel);
		});
	}

	@Test
	@DisplayName("login: Negative Scenario")
	public void loginIncorrectPassword() throws FundException {
		loginModel.setEmail("abc@test.com");
		loginModel.setPassword("123456789");
		when(fundrepository.findByEmail("abc@test.com")).thenReturn(userEntity);
		when(bCryptPasswordEncoder.matches(loginModel.getPassword(), userEntity.getEncryptedPassword()))
				.thenReturn(false);
		Assertions.assertThrows(FundException.class, () -> {
			fundService.login(loginModel);
		});
	}

	@Test
	@DisplayName("Beneficiary: Positive Scenario")
	public void addBeneficiary() {
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setAccountNo(401);
		when(beneficiaryRepository.save(ArgumentMatchers.any(Beneficiary.class))).thenReturn(beneficiary);
		beneficiary = beneficiaryRepository.save(beneficiary);
		assertNotNull(beneficiary);
	}

	@Test
	@DisplayName("Beneficiary: Negative Scenario")
	public void addBeneficiaryNegavtive() {
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setAccountNo(401);
		when(beneficiaryRepository.save(ArgumentMatchers.any(Beneficiary.class))).thenReturn(beneficiary);
		beneficiary = beneficiaryRepository.save(beneficiary);
		assertNotNull(beneficiary);
	}

	@Test
	@DisplayName("Fund Transfer: Positive Scenario")
	public void fundTransferTest() throws FundException {
		balance.setAmount(200.00);
		transferBalanceRequest.setAmount(20.00);
		transferBalanceRequest.setEmail("abc@test.com");
		when(balanceRepository.save(balance)).thenReturn(balance);
		when( balanceRepository.findByEmail("abc@test.com")).thenReturn(balance);
		fundService.fundTransfer(transferBalanceRequest);
	}
	
	@Test
	@DisplayName("Fund Transfer: Negative Scenario")
	public void fundTransferNegativeTest() {
		transferBalanceRequest.setAmount(500.00);
		transferBalanceRequest.setEmail("abc@test.com");
		when(balanceRepository.findByEmail("abc@test.com")).thenReturn(balance);
		Assertions.assertThrows(FundException.class,()->{
			fundService.fundTransfer(transferBalanceRequest);
		});
	}

}
