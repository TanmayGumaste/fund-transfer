package com.apps.fundtransfer.users.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
@Entity
@Table(name="userentity")
@Data
public class UserEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5238536520569535515L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private long id;
	@Column(nullable=false,length=50)
	private String firstName;
	@Column(nullable=false,length=50)
	private String lastName;
	@Column(nullable=false,length=120,unique=true)
	private String email;
	

	@Column(nullable=false,unique=true)
	private String userId;
	@JsonBackReference
	@Column(nullable=false)
	private String encryptedPassword;

}
