package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IChatRoomDAO {
    public void saveChatRoom(ChatRoomDTO dto);
    public List<ChatRoomDTO> publicRoomList();
}
