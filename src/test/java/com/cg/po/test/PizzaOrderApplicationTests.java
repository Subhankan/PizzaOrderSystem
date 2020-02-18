package com.cg.po.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.po.model.Accounts;
import com.cg.po.model.Menu;
import com.cg.po.model.Pizzas;
import com.cg.po.repository.AccountRepo;
import com.cg.po.repository.MenuRepo;
import com.cg.po.repository.PizzaRepo;
import com.cg.po.service.PizzaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PizzaOrderApplicationTests {
	Accounts accounts=new Accounts("8479810234","Debu1223","Debapriyo","Dankuni,westBengal");
	Optional<Accounts> optional=Optional.of(accounts);
	Menu menu=new Menu(accounts,"NonVeg","Large","Cheesy","Chicken");
	Optional<Menu> optional2=Optional.of(menu);
	Pizzas pizza=new Pizzas("P5","Type",500.0);
	
	@Autowired
	private PizzaService pizzaService;
	
	@MockBean
	private AccountRepo accountRepo;
	
	@MockBean
	private MenuRepo menuRepo;
	
	@MockBean
	private PizzaRepo pizzaRepo;
	
	@Test
	public void login() throws Exception {
		
		when(accountRepo.findByMobileAndPassword("8479810234")).thenReturn(accounts);
		assertEquals(accounts, pizzaService.loginService("8479810234", "Debu1234"));
		
	}
	
	
//	@Test
//	public void createAccount() throws Exception {
//		
//		when(accountRepo.findById("8479810234")).thenReturn(optional);
//		assertEquals(optional, pizzaService.createaccountService(accounts));
//	}
	
	@Test
	public void addBalance() throws Exception {
		
		when(accountRepo.findByMobile("8479810234")).thenReturn(accounts);
		when(accountRepo.save(accounts)).thenReturn(accounts);
		assertEquals(accounts, pizzaService.addBalanceService("8479810234", 500d));
		
	}
	
	@Test
	public void showOrders() throws Exception {
	
		when(menuRepo.findByMobile("8479810234")).thenReturn(optional2);
		assertEquals(optional2.stream().collect(Collectors.toList()), pizzaService.showOrders("8479810234"));
	}
	
	@Test
	public void showTransactions() {
		
		when(menuRepo.findAll()).thenReturn(new ArrayList<Menu>());
		assertEquals(new ArrayList<Menu>(), pizzaService.showTransactions());
	}

}
