package com.cg.po.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.po.model.Accounts;
import com.cg.po.model.Menu;
import com.cg.po.repository.AccountRepo;
import com.cg.po.repository.MenuRepo;
import com.cg.po.repository.PizzaRepo;

@Service
public class PizzaServiceImpl implements PizzaService {

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	PizzaRepo pizzaRepo;

	@Autowired
	MenuRepo menuRepo;

	static Accounts sAccount = new Accounts();

	@Override
	public Accounts loginService(String mobile, String password) throws Exception {

		try {
			Accounts accounts = accountRepo.findByMobileAndPassword(mobile);

			if (accounts == null) {
				throw new Exception("Mobile number or password is incorrect");
			}
			sAccount = accounts;
			return accounts;
		} catch (Exception e) {

			throw new Exception("Mobile number or password is incorrect");
		}

	}

	@Override
	public Accounts createaccountService(Accounts accounts) throws Exception {

		String mobilePattern = "[0-9]{10}";
		String mobile = accounts.getMobile();
		boolean mobileMatcher = Pattern.matches(mobilePattern, mobile);

		

		String namePattern = "[A-Z]{1}[a-z]{2,20}";
		String name = accounts.getName();
		boolean nameMatcher = Pattern.matches(namePattern, name);

		String addressPattern = "[A-Z]{1}[a-z]{2,30}";
		String address = accounts.getAddress();
		boolean addressMatcher = Pattern.matches(addressPattern, address);

		if (!mobileMatcher) {
			throw new Exception("Mobile Number should contain 10 digits");
		}
		 else if (!nameMatcher) {
			throw new Exception("Name should start with a capital letter. Minimum length 3, Maximum length 20");
		} else if (!addressMatcher) {
			throw new Exception("Address should start with a capital letter. Minimum length 3, Maximum length 30");
		} else {
			Accounts accounts2 = accountRepo.findById(accounts.getMobile()).orElse(new Accounts());
			if (accounts2.getMobile() != null) {
				throw new Exception("Number already present");
			}
		}
		return accountRepo.save(accounts);
	}

	@Override
	public Accounts addBalanceService(String mobile, Double balance) throws Exception {

		try {
			Accounts accounts = accountRepo.findByMobile(mobile);

			if (balance < 0) {
				throw new Exception("Amount cannot be in negative. Please enter any positive amount");

			} else {
				balance += accounts.getBalance();
				accounts.setBalance(balance);
				return accountRepo.save(accounts);
			}

		} catch (Exception e) {

			if (balance < 0)
				throw new Exception("Amount cannot be in negative. Please enter any positive amount");
			else
				throw new Exception("Mobile number does not exist.");
		}
	}

	@Override
	public String menuService(Menu menu) throws Exception {
		

		menu.setPizzaCrust(menu.getPizzaCrust().toLowerCase());
		menu.setPizzaSize(menu.getPizzaSize().toLowerCase());
		menu.setPizzaTopping(menu.getPizzaTopping().toLowerCase());
		menu.setPizzaType(menu.getPizzaType().toLowerCase());
		System.out.println(menu.getPizzaCrust());
		
		if (!(menu.getPizzaType().equals("greek")
				|| menu.getPizzaType().equals("new york")
				|| menu.getPizzaType().equals("neapolitan"))) {
			
			throw new Exception("Enter a valid pizza type (Greek/Neapolitan/New York)");
		}
		if (!(menu.getPizzaSize().equals("small") || menu.getPizzaSize().equals("medium")
				|| menu.getPizzaSize().equals("large"))){
			
			throw new Exception("Enter valid pizza size (Small/Medium/Large)");
		}
		if (!(menu.getPizzaCrust().equals("regular")
				|| menu.getPizzaCrust().equals("cheese burst"))) {
			
			throw new Exception("Enter valid crust (Regular/Cheese Burst)");
		}
		if (!(menu.getPizzaTopping().equals("pepperoni") || menu.getPizzaTopping().equals("onion")
				|| menu.getPizzaTopping().equals("bacon"))) {
			
			throw new Exception("Enter valid toppings (Pepperoni/Onion/Bacon)");
		}

	
			menu.setMobile(sAccount);
			
			Accounts accounts = accountRepo.findByMobile(menu.getMobile().getMobile());
					Double amount = pizzaRepo.getPizzaTypePrice(menu.getPizzaType()) + pizzaRepo.getPizzaSizePrice(menu.getPizzaSize())
					+ pizzaRepo.getPizzaCrustPrice(menu.getPizzaCrust())
					+ pizzaRepo.getPizzaToppingPrice(menu.getPizzaTopping());
			if (amount < accounts.getBalance()) {
				System.out.println("gandss");
				accounts.setBalance(accounts.getBalance() - amount);
				accountRepo.save(accounts);
				menu.setAmount(amount);
				menuRepo.save(menu);
			} else {
				System.out.println("gand");
				throw new Exception("Insufficient balance...add amount first");
				
			}
			
		return "" + amount;

	}

	@Override
	public List<Menu> showOrders(String mobile) throws Exception {

		Optional<Menu> list = menuRepo.findByMobile(mobile);

		List<Menu> l = list.stream().collect(Collectors.toList());
		if (l.isEmpty()) {
			throw new Exception(mobile + " does not exist");
		}
		return l;

	}

	@Override
	public List<Menu> showTransactions() {

		List<Menu> list = new ArrayList<>();
		list = menuRepo.findAll();
		return list;
	}
}
