package com.cg.po.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cg.po.model.Accounts;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails {


	private String mobile;

	private String password;

	private String name;

	private String address;

	private String role;

	private Double balance;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(String mobile, String password, String name, String address, String role, Double balance,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.mobile = mobile;
		this.password = password;
		this.name = name;
		this.address = address;
		this.role = role;
		this.balance = balance;
		this.authorities = authorities;
	}
	

	 public static UserPrincipal create(Accounts accounts)
	 {
		 List<GrantedAuthority> authorities=new ArrayList<>();
		 authorities.add(new SimpleGrantedAuthority(accounts.getRole()));
		 return new UserPrincipal(
				 accounts.getMobile(),
				 accounts.getPassword(),
				 accounts.getName(),
				 accounts.getAddress(),
				 accounts.getRole(),
				 accounts.getBalance(),
				 authorities
				 );
				 
	 }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return mobile;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(mobile, that.mobile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mobile);
    }
}
