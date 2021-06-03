package com.apps.fundtransfer.users.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="AccountBalance")
@Data
public class Balance {
	@Column(nullable=false)
	private Double Amount;
	@Column(nullable=false,length=120,unique=true)
    private String email;
	@Id
	@Column(nullable=false)
    private String userId;
}
