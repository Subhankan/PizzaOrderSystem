package com.cg.po.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	@ManyToOne
	@JoinColumn(referencedColumnName = "mobile",name = "mobile")
	private Accounts mobile;
	private String pizzaType; 
    private String pizzaSize;
    private String pizzaCrust;
    private String pizzaTopping;
    private Double amount;
    
    public Menu(Accounts mobile, String pizzaType, String pizzaSize, String pizzaCrust, String pizzaTopping) {
		super();
		this.mobile = mobile;
		this.pizzaType = pizzaType;
		this.pizzaSize = pizzaSize;
		this.pizzaCrust = pizzaCrust;
		this.pizzaTopping = pizzaTopping;
	}
    
    public Menu() {
    	
    }
}
