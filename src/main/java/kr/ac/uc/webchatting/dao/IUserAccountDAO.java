package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.UserAccountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserAccountDAO {
    public List<UserAccountDTO> getUserID(String id);
    public void saveUserAccount(UserAccountDTO dto);
}
