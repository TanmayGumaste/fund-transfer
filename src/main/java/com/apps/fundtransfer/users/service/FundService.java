package com.apps.fundtransfer.users.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apps.fundtransfer.users.data.Beneficiary;
import com.apps.fundtransfer.users.data.UserEntity;
import com.apps.fundtransfer.users.model.CreateUserRequestModel;
import com.apps.fundtransfer.users.model.FundException;
import com.apps.fundtransfer.users.model.LoginRequestModel;
import com.apps.fundtransfer.users.model.LoginResponseModel;
import com.apps.fundtransfer.users.model.TransferBalanceRequest;
import com.apps.fundtransfer.users.shared.UserDto;

@Service
@Component
public interface FundService {
	
	UserEntity createUser(CreateUserRequestModel userDetails);

	LoginResponseModel login(LoginRequestModel loginRequestModel) throws FundException;

	void addBeneficary(Beneficiary addBeneficiaryModel);

	void fundTransfer(TransferBalanceRequest transferBalanceRequest) throws FundException;

}
