package com.apps.fundtransfer.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.apps.fundtransfer.users.data.Balance;
import com.apps.fundtransfer.users.data.BalanceRepository;
import com.apps.fundtransfer.users.data.Beneficiary;
import com.apps.fundtransfer.users.data.BeneficiaryRepository;
import com.apps.fundtransfer.users.data.Fundrepository;
import com.apps.fundtransfer.users.data.UserEntity;
import com.apps.fundtransfer.users.model.CreateUserRequestModel;
import com.apps.fundtransfer.users.model.FundException;
import com.apps.fundtransfer.users.model.LoginRequestModel;
import com.apps.fundtransfer.users.model.LoginResponseModel;
import com.apps.fundtransfer.users.model.TransferBalanceRequest;
import com.apps.fundtransfer.users.shared.UserDto;

@Service

public class FundServiceImpl implements FundService {
	
	Logger logger = LoggerFactory.getLogger(FundServiceImpl.class);

	Fundrepository fundRepository;

	BalanceRepository balanceRepository;

	BeneficiaryRepository beneficiaryRepository;

	BCryptPasswordEncoder bCryptPasswordEncoder;

	public FundServiceImpl(Fundrepository fundRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			BalanceRepository balanceRepository, BeneficiaryRepository beneficiaryRepository) {
		this.fundRepository = fundRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.balanceRepository = balanceRepository;
		this.beneficiaryRepository = beneficiaryRepository;
	}

	@Override
	public UserEntity createUser(CreateUserRequestModel userDetails) {
		logger.info("FundServiceImpl : createUser : Start");
		UserEntity userEntity=new UserEntity();
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		BeanUtils.copyProperties(userDetails, userEntity);
		userEntity=fundRepository.save(userEntity);
		//BeanUtils.copyProperties(userEntity, userDetails);
		logger.info("FundServiceImpl : createUser : End");
		return userEntity;
	}

	@Override
	public LoginResponseModel login(LoginRequestModel loginRequestModel) throws FundException {
		logger.info("FundServiceImpl : login : Start : Email : {}",loginRequestModel.getEmail());
		UserEntity userEntity = fundRepository.findByEmail(loginRequestModel.getEmail());
		if (ObjectUtils.isEmpty(userEntity)) {
			logger.warn("FundServiceImpl : login : User not found");
			throw new FundException("User not found..Please SignUp",HttpStatus.NOT_FOUND);
		}
		boolean flag=bCryptPasswordEncoder.matches(loginRequestModel.getPassword(), userEntity.getEncryptedPassword());
		if (flag) {
			logger.info("FundServiceImpl : login : Valid user");
			LoginResponseModel model = new LoginResponseModel();
			model.setAccountId(userEntity.getUserId());
			model.setBalance(getbalance(loginRequestModel.getEmail()));
			model.setEmail(userEntity.getEmail());
			model.setFirstName(userEntity.getFirstName());
			model.setLastName(userEntity.getLastName());
			logger.info("FundServiceImpl : login : End : Email : {}",loginRequestModel.getEmail());
			return model;
		} else {
			logger.error("FundServiceImpl : login : password is incorrect");
			throw new FundException("Password is incorrect",HttpStatus.UNAUTHORIZED);
		}

	}

	private Double getbalance(String email) {
		logger.info("FundServiceImpl : getbalance : Start : email : {}",email);
		Balance balance = balanceRepository.findByEmail(email);
		logger.info("FundServiceImpl : getbalance : End : balance : {}",balance);
		if(ObjectUtils.isEmpty(balance)) {
			return 0.0;
		}
		return balance.getAmount();

	}

	@Override
	public void addBeneficary(Beneficiary addBeneficiaryModel) {
		logger.info("FundServiceImpl : getbalance : addBeneficary : email : {}",addBeneficiaryModel.getEmail());
		beneficiaryRepository.save(addBeneficiaryModel);
	}

	@Override
	public void fundTransfer(TransferBalanceRequest transferBalanceRequest) throws FundException {
		logger.info("FundServiceImpl : fundTransfer : Start");
		Balance balance = balanceRepository.findByEmail(transferBalanceRequest.getEmail());
		if(balance.getAmount()<transferBalanceRequest.getAmount()) {
			logger.error("FundServiceImpl : fundTransfer : You don't have sufficient balance");
			throw new FundException("You don't have sufficient balance",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		balance.setAmount(balance.getAmount()-transferBalanceRequest.getAmount());
		balanceRepository.save(balance);
		logger.info("FundServiceImpl : fundTransfer : End");
	}
}
