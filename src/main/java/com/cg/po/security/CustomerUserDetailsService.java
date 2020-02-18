package com.cg.po.security;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.po.model.Accounts;
import com.cg.po.repository.AccountRepo;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
	
	@Autowired
	AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
		Accounts accounts=accountRepo.findById(mobile).orElseThrow(
				
				() -> new UsernameNotFoundException("user not found with this mobile number" +mobile)
				);
		
		return UserPrincipal.create(accounts);
	}

	@Transactional
	public UserDetails loadUserById(String mobile) throws Exception
	{
	
	Accounts accountss=accountRepo.findById(mobile).orElseThrow(
				
				() -> new UsernameNotFoundException("user not found with this mobile number" +mobile)
				);
		
		return UserPrincipal.create(accountss);
	}
}
