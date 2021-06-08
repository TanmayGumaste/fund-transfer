package com.apps.fundtransfer.users.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apps.fundtransfer.users.data.Beneficiary;
import com.apps.fundtransfer.users.data.UserEntity;
import com.apps.fundtransfer.users.model.CreateResponseModel;
import com.apps.fundtransfer.users.model.CreateUserRequestModel;
import com.apps.fundtransfer.users.model.FundException;
import com.apps.fundtransfer.users.model.LoginRequestModel;
import com.apps.fundtransfer.users.model.LoginResponseModel;
import com.apps.fundtransfer.users.model.Response;
import com.apps.fundtransfer.users.model.TransferBalanceRequest;
import com.apps.fundtransfer.users.service.FundService;

@RestController
@RequestMapping("/funds")
public class FundController {
	
	Logger logger = LoggerFactory.getLogger(FundController.class);
	
	@Autowired
	FundService fundService;
	
	@PostMapping("/signup")
	public ResponseEntity<CreateResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetail) {
		logger.info("FundController : createUser : Start");
		UserEntity createUser =fundService.createUser(userDetail);
		CreateResponseModel returnValue=new CreateResponseModel();
		BeanUtils.copyProperties(createUser, returnValue);
		logger.info("FundController : createUser : End");
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
		
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponseModel> userLogin(@RequestBody LoginRequestModel loginRequestModel) throws FundException{
		logger.info("FundController : login : Start : email : {}",loginRequestModel.getEmail());
		LoginResponseModel loginResponseModel=fundService.login(loginRequestModel);
		logger.info("FundController : login : End");
		return ResponseEntity.status(HttpStatus.OK).body(loginResponseModel);
		
	}

	@PutMapping("/fundTransfer")
	public ResponseEntity<?> fundTransfer(@RequestBody TransferBalanceRequest transferBalanceRequest) throws  FundException {
		fundService.fundTransfer(transferBalanceRequest);
		return new ResponseEntity<>(new Response("Amount transferred successfully to user "+transferBalanceRequest.getAccountNo()),HttpStatus.OK);
	}
	
	@PostMapping("/beneficiary")
	public ResponseEntity<? >addBeneficiary (@RequestBody Beneficiary addBeneficiaryModel){
		fundService.addBeneficary(addBeneficiaryModel);
		return new ResponseEntity<>(new Response("Beneficiary Added successfully"),HttpStatus.OK);
	}
}
