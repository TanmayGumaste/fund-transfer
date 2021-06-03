package com.apps.fundtransfer.users.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, String>{
	
	Balance findByEmail(String email);

}
