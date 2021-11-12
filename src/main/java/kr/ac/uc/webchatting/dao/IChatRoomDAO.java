package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IChatRoomDAO {
    public void insertChatRoom(ChatRoomDTO dto);
    public List<ChatRoomDTO> myRoomList(String id);
    public List<ChatRoomDTO> publicRoomList();
    public int getChatRoomID();
}
