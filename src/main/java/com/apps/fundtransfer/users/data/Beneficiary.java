package com.apps.fundtransfer.users.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="Beneficiary")
@Data
public class Beneficiary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8755759789754103136L;
	@Id
	@Column(nullable=false)
	private String userId;
	@Column(nullable=false)
	private String beneficaryName;
	@Column(nullable=false)
	private String email;
	@Column(nullable=false)
	private int accountNo;

}
