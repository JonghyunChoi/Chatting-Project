package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IChatRoomDAO {
    public void insertChatRoom(ChatRoomDTO dto);    // 채팅방 생성
    public List<ChatRoomDTO> myRoomList(String id); // 자신이 참여하고 있는 채팅방 불러오기
    public List<ChatRoomDTO> publicRoomList();      // 공개 채팅방 불러오기
    public int getChatRoomID();                     // 최신 채팅방 번호 가져오기
}
