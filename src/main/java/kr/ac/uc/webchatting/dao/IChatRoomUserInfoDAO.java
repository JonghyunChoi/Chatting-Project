package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatRoomUserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IChatRoomUserInfoDAO {
    public void addChatRoomUserInfo(ChatRoomUserInfoDTO dto);    // 채팅방
    public Integer checkUserInChatRoom(String room_id, String id);
}
