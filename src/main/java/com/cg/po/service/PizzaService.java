package com.cg.po.service;

import java.util.List;

import com.cg.po.model.Accounts;
import com.cg.po.model.Menu;

public interface PizzaService {
	
	public Accounts loginService(String mobile, String password) throws Exception;

	public Accounts createaccountService(Accounts accounts) throws Exception ;

	public Accounts addBalanceService(String mobile, Double balance) throws Exception;

	public String menuService(Menu menu) throws Exception;

	public List<Menu> showOrders(String mobile) throws Exception;

	public List<Menu> showTransactions();

}
