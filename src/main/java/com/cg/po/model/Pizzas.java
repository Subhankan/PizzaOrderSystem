package com.cg.po.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity (name= "Pizzas")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Pizzas {
	
	@Id
	private String pid;
	private String type;
	@Column(name = "price")
	private Double price;
	
	

}
