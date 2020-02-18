package com.cg.po.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.po.model.Accounts;
import com.cg.po.model.Menu;
import com.cg.po.security.JwtAuthenticationResponse;
import com.cg.po.security.JwtTokenProvider;
import com.cg.po.service.PizzaService;

@RestController
public class PizzaController {
	
	@Autowired
	PizzaService pizzaService;

	@Autowired
	PasswordEncoder encodePassword;

	@Autowired
	JwtTokenProvider tokenProvider;
	    
	@Autowired
	AuthenticationManager authenticationManager;
	
	public static String pass="";
	
	    
	
	
	@PostMapping(value="/login")
	public ResponseEntity<?> login(@RequestParam String mobile, @RequestParam String password) throws Exception
	{
		
		
		String jwt="";
		
		try {
		Accounts accounts=pizzaService.loginService(mobile, password);
	
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                       accounts.getMobile(),
                      password
                )
        );
		
		
		
        SecurityContextHolder.getContext().setAuthentication(authentication);

        jwt = tokenProvider.generateToken(authentication);
        System.out.println(authentication);
        
		}catch (Exception e) {
			
			 return ResponseEntity.ok("user or password does not match");
			
		}
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		
	}
	
	@PostMapping(value="/createaccount")
	public String createaccount(@RequestBody Accounts accounts)
	{
		String passwordPattern = "[A-Za-z0-9]{8,20}";
		String password = accounts.getPassword();
		boolean passwordMatcher = Pattern.matches(passwordPattern, password);
		
		if(!passwordMatcher)
		{
			return "Minimum length 8, max length 20. can contain upper case, lower case, digits";
		}
		else {
		try {
			  accounts.setPassword(encodePassword.encode(accounts.getPassword()));
			   
			   
			   pass=accounts.getPassword();
			
			return ""+pizzaService.createaccountService(accounts);
		} catch (Exception e) {
			
		return e.getMessage();
		}
		}
	}

	@PreAuthorize("hasAuthority('Customer')")
	@PostMapping(value="/add/{mobile}/{balance}")
	public String addBalance(@PathVariable String mobile, @PathVariable Double balance)
	{
		Accounts accounts=null;
		try {
			accounts=pizzaService.addBalanceService(mobile, balance);
		} catch (Exception e) {
			return e.getMessage();
		}
		return ""+accounts;
		
	}
	
	@PreAuthorize("hasAuthority('Customer')")
	@PostMapping(value="/menu")
	public String price(@RequestBody Menu menu)
	{
		
		try {
			return pizzaService.menuService(menu);
		} catch (Exception e) {
			
			return e.getMessage();
		}
	}
	
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping(value="/showOrders/{mobile}")
	public List<Menu> orders(@PathVariable String mobile)
	{
		List<Menu> orderList=new ArrayList<>();
		try {
		orderList=pizzaService.showOrders(mobile);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return orderList;
		
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping(value="/showTransactions")
	 public List<Menu> showTransactions()
	 {
		return pizzaService.showTransactions(); 
	 }
}
