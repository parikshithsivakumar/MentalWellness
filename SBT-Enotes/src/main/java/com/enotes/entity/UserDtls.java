package com.enotes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "full_name")
	private String name;
	private String email;
	private String password;
	private String about;
	private String role;
	
	public UserDtls() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
