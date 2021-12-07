package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatRoomContentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IChatRoomContentDAO {
    public void addChatMessage(ChatRoomContentDTO dto);
    public List<ChatRoomContentDTO> getChatLog(int room_id);
    public void delChatLog(int room_id);
}
