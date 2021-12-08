package kr.ac.uc.webchatting.auth;

import kr.ac.uc.webchatting.dao.IUserAccountDAO;
import kr.ac.uc.webchatting.dto.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyDetailsService implements UserDetailsService {
	
	@Autowired
	IUserAccountDAO userAccountDAO;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		List<UserAccountDTO> list = userAccountDAO.getUserID(id);
		
		UserAccountDTO dto;
		
		if(list.size() != 0) {
			dto = list.get(0);
			// System.out.println("회원맞음");
		} else {
			dto = new UserAccountDTO();
			// System.out.println("회원아님");
		}
		
		MyDetails pd = new MyDetails(dto);
		return pd;
	}
	
}
