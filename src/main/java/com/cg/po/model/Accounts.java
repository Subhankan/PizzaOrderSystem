package com.cg.po.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Accounts {

	@Id
	private String mobile;
	
	private String password;

	@NotBlank
	@Size(min=5,max=20)
	private String name;
	
	@NotBlank
	@Size(min=5,max=40)
	private String address;
	
	
	private Double balance=500.0;
	
	private String role="Customer";


	public Accounts(@Size String mobile, @NotBlank @Size(min = 8, max = 20) String password,
			@NotBlank @Size(min = 5, max = 20) String name, @NotBlank @Size(min = 5, max = 40) String address) {
		super();
		this.mobile = mobile;
		this.password = password;
		this.name = name;
		this.address = address;
		
	}
	
	public Accounts() {
		
	}
	
	
}
