package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.FriendInfoDTO;
import kr.ac.uc.webchatting.dto.UserAccountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IFriendInfoDAO {
    public List<UserAccountDTO> getMyFriendsInfo(String id);    // 친구 정보 가져오기
    public List<FriendInfoDTO> chkFollow(String id, String friend_id); // 팔로우 체크
}
