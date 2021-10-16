package kr.ac.uc.webchatting.auth;

import kr.ac.uc.webchatting.dto.UserAccountDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class MyDetails implements UserDetails {
	
	private UserAccountDTO userAccountDTO;
	
	public MyDetails(UserAccountDTO userAccountDTO) {
		this.userAccountDTO = userAccountDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add( ()-> {
			return userAccountDTO.getAuthority();
		});
		
		return collectors;
	}

	@Override
	public String getPassword() {
		return userAccountDTO.getPassword();
	}

	@Override
	public String getUsername() {
		return userAccountDTO.getId();
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
}
