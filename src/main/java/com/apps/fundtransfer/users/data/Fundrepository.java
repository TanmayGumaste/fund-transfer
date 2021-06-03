package com.apps.fundtransfer.users.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Fundrepository extends JpaRepository<UserEntity, Long>  {
	
	UserEntity findByEmail(String email);
	
	//UserEntity findByEmailAndPassword(String userName,String password);

}
